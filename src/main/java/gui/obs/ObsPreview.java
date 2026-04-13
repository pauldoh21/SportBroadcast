package gui.obs;

import gui.Layout.PanelNodeFX;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nu.pattern.OpenCV;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Future work:
// Use websockets to automatically start OBS virtual camera
// Auto detect OBS virtual camera to avoid having to manually select it

public class ObsPreview extends PanelNodeFX {

    private int cameraIndex = -1;

    private ComboBox<String> cameraSelector;
    
    private final ImageView imageView = new ImageView();
    private VideoCapture capture;
    private ScheduledExecutorService executor;
    private final ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();
    private final Object captureLock = new Object();
    private WritableImage writableImage;

    public ObsPreview() {
        super();

        OpenCV.loadLocally();
        capture = new VideoCapture();
        setupCombobox();
        discoverCamerasAsync();
        openCameraAsync(0);

        this.setBordered(true);
        this.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, null, null)));

        imageView.setPreserveRatio(true);

        // Bind to parent size once added to the scene
        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(this.widthProperty());
                imageView.fitHeightProperty().bind(this.heightProperty());
            }
        });

        StackPane stack = new StackPane(imageView, cameraSelector);
        StackPane.setAlignment(cameraSelector, Pos.TOP_RIGHT);

        HBox container = new HBox(stack);
        container.setAlignment(Pos.CENTER);

        this.setContentNode(container);
    }

    public void start() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::grabFrame, 0, 33, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
        if (capture != null) {
            synchronized (captureLock) {
                if (capture.isOpened()) {
                    capture.release();
                }
            }
        }
    }

    private void grabFrame() {
        Mat frame = new Mat();
        synchronized (captureLock) {
            if (capture == null || !capture.isOpened()) {
                frame.release();
                return;
            }
            if (!capture.read(frame) || frame.empty()) {
                frame.release();
                return;
            }
        }

        // OpenCV gives us BGR — convert to RGB for JavaFX
        Mat rgb = new Mat();
        Imgproc.cvtColor(frame, rgb, Imgproc.COLOR_BGR2RGB);

        int width  = rgb.cols();
        int height = rgb.rows();
        int channels = (int) rgb.elemSize();
        byte[] buffer = new byte[width * height * channels];
        rgb.get(0, 0, buffer);

        Platform.runLater(() -> {
            // Reuse the WritableImage if dimensions haven't changed
            if (writableImage == null
                    || (int) writableImage.getWidth()  != width
                    || (int) writableImage.getHeight() != height) {
                writableImage = new WritableImage(width, height);
                imageView.setImage(writableImage);
            }

            PixelWriter pw = writableImage.getPixelWriter();
            pw.setPixels(0, 0, width, height,
                    PixelFormat.getByteRgbInstance(),
                    ByteBuffer.wrap(buffer), width * channels);
        });

        frame.release();
        rgb.release();
    }

    private void setupCombobox() {
        cameraSelector = new ComboBox<>();
        cameraSelector.setPromptText("Loading cameras...");
        cameraSelector.setDisable(true);

        cameraSelector.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
            }
        });

        cameraSelector.setMaxWidth(5);
        cameraSelector.setPrefWidth(5);
        cameraSelector.setStyle("-fx-padding: 0; -fx-background-insets: 0;");

        cameraSelector.setOnAction(e -> {
            int selectedIndex = cameraSelector.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex != cameraIndex) {
                cameraIndex = selectedIndex;
                cameraSelector.setDisable(true);
                switchCameraAsync(cameraIndex);
            }
        });
    }

    private void discoverCamerasAsync() {
        CompletableFuture.supplyAsync(this::getAvailableCameras, cameraExecutor)
                .thenAccept(cameraLabels -> Platform.runLater(() -> {
                    cameraSelector.getItems().setAll(cameraLabels);
                    cameraSelector.setDisable(false);
                    cameraSelector.setPromptText("Select camera");
                    if (!cameraLabels.isEmpty()) {
                        cameraSelector.getSelectionModel().select(0);
                    }
                }));
    }

    private void switchCameraAsync(int index) {
        cameraExecutor.submit(() -> {
            VideoCapture newCapture = new VideoCapture();
            boolean opened = newCapture.open(index);
            if (opened) {
                newCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
                newCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);
                synchronized (captureLock) {
                    VideoCapture oldCapture = capture;
                    capture = newCapture;
                    if (oldCapture != null && oldCapture.isOpened()) {
                        oldCapture.release();
                    }
                }
            } else {
                newCapture.release();
            }

            Platform.runLater(() -> {
                cameraSelector.setDisable(false);
                if (!opened) {
                    showCameraError(index);
                }
            });
        });
    }

    private void openCameraAsync(int index) {
        cameraExecutor.submit(() -> {
            VideoCapture newCapture = new VideoCapture();
            if (newCapture.open(index)) {
                newCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
                newCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);
                synchronized (captureLock) {
                    if (capture != null && capture.isOpened()) {
                        capture.release();
                    }
                    capture = newCapture;
                }
            } else {
                newCapture.release();
                Platform.runLater(() -> showCameraError(index));
            }
        });
    }

    private void showCameraError(int index) {
        System.err.println("Could not open camera " + index + ". Check OBS virtual camera is running.");
    }

    private List<String> getAvailableCameras() {
        List<String> cameraLabels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VideoCapture test = new VideoCapture(i);
            if (test.isOpened()) {
                cameraLabels.add("Camera " + i);
                test.release();
            }
        }
        return cameraLabels;
    }

    
}

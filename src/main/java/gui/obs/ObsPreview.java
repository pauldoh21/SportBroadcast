package gui.obs;

import gui.Layout.PanelNodeFX;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import nu.pattern.OpenCV;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ObsPreview extends PanelNodeFX {

    private final ImageView imageView = new ImageView();
    private VideoCapture capture;
    private ScheduledExecutorService executor;
    private WritableImage writableImage;

    public ObsPreview() {
        super();
        OpenCV.loadLocally();
        capture = new VideoCapture(2);

        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);

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

        HBox container = new HBox(imageView);
        container.setAlignment(Pos.CENTER);

        this.setContentNode(container);
    }

    public void start() {
        if (!capture.isOpened()) {
            System.err.println("Could not open camera. Check OBS virtual camera is running.");
            return;
        }

        // Grab a frame at ~30fps
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::grabFrame, 0, 33, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
        if (capture != null) {
            capture.release();
        }
    }

    private void grabFrame() {
        Mat frame = new Mat();
        if (!capture.read(frame) || frame.empty()) return;

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

    
    
}

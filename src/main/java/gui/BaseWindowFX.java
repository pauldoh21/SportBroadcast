package gui;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.CustomCaption;

public class BaseWindowFX extends Stage {
    private final BorderPane root;

    public BaseWindowFX(String title) {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        setTitle(title);
        setWidth(1000);
        setHeight(700);
        centerOnScreen();

        root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        try {
            var is = getClass().getResourceAsStream("/ico192.png");
            if (is != null) {
                // load full-resolution image and let ImageView scale it - produces crisper results
                Image icon = new Image(is);
                ImageView iv = new ImageView(icon);
                iv.setFitWidth(16);
                iv.setFitHeight(16);
                iv.setPreserveRatio(true);
                iv.setSmooth(true);

                // use an empty-text Menu for the icon and remove extra padding
                Menu iconMenu = new Menu("");
                iconMenu.setGraphic(iv);
                iconMenu.setMnemonicParsing(false);
                iconMenu.setStyle("-fx-padding: 0; -fx-background-insets: 0;");
                menuBar.getMenus().add(iconMenu);
            }
        } catch (Exception ignored) {
        }

        // apply customizations
        CustomCaption.useForStage(this, new CaptionConfiguration()
                .setIconColor(Color.BLACK)  // set the icon/foreground color to black
                .setCaptionDragRegion(menuBar)  // set the MenuBar as DragRegion to exclude the
                                            // buttons automatically
        );

        menuBar.getMenus().addAll(new Menu("File"), new Menu("Edit"), new Menu("View"));
        root.setTop(menuBar);
        setScene(new Scene(root));
    }

    public void setContentNode(javafx.scene.Node node) {
        AnchorPane contentHolder = new AnchorPane();
        contentHolder.getChildren().add(node);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        root.setCenter(contentHolder);
    }

    public javafx.scene.Node getContentNode() {
        return root.getCenter();
    }
}

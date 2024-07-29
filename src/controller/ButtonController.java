package controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonController extends Button {

    private String style_normal = "-fx-background-color: gainsboro; -fx-padding: 2, 2, 2, 2; -fx-cursor: hand;";
    private String style_pressed = "-fx-background-color: gainsboro; -fx-padding: 3 1 1 3; -fx-cursor: hand;"; 

    public ButtonController(Image originalImage, double h, double w) {

        ImageView image = new ImageView(originalImage);
        image.setFitHeight(h);
        image.setFitHeight(w);
        image.setPreserveRatio(true);
        setGraphic(image);
        setStyle(style_normal);

        setOnMousePressed(event -> setStyle(style_pressed));
        setOnMouseReleased(event -> setStyle(style_normal));

       
    }

}

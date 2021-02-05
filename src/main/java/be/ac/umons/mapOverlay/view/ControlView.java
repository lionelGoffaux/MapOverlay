package be.ac.umons.mapOverlay.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ControlView extends VBox {
    Button button1 = new Button("button 1");
    Button button2 = new Button("button 2");

    public ControlView() {
        super();
        this.getChildren().addAll(button1, button2);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }
}

package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ControlView extends VBox {
    Button[] buttons = {new Button("open"), new Button("new"), new Button("save"), new Button("start"),
            new Button("step"), new Button("detect")};

    public ControlView() {
        super();
        setWidth(100);

        getChildren().addAll(buttons);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        setPadding(new Insets(10));
        setSpacing(10);
    }

    public void setButtonController(ButtonController buttonController){
        for (Button button: buttons){
            button.setPrefWidth(80);
            button.setOnAction(buttonController);
        }
    }
}

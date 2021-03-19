package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ButtonView extends VBox {
    // TODO: command pattern ?
    Button[] buttons = {new Button("open"), new Button("new"), new Button("save"), new Button("restart"),
            new Button("step"), new Button("detect")};

    public ButtonView() {
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

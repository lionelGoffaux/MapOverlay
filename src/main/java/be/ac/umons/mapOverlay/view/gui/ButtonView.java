package be.ac.umons.mapOverlay.view.gui;

import be.ac.umons.mapOverlay.controller.ButtonController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ButtonView extends VBox {
    final Button[] buttons = {new Button("open"), new Button("new"), new Button("save"),
            new Button("step"), new Button("find all")};

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

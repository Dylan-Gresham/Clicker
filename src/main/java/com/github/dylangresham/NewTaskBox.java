package com.github.dylangresham;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewTaskBox extends Clicker
{    
    private static MouseButton newTaskButton = MouseButton.PRIMARY;

    public static void display()
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Task");
        window.setMinWidth(400);
        window.setMinHeight(600);
        window.setResizable(false);

        BorderPane pane = new BorderPane();
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();

        Label nameLab = new Label("Name: ");
        Label xPosLab = new Label("X Coord: ");
        Label yPosLab = new Label("Y Coord: ");
        Label buttonLab = new Label("Button: ");
        Label keyLab = new Label("Key: ");
        Label descripLab = new Label("Description: ");
        leftBox.getChildren().addAll(nameLab, xPosLab, yPosLab, buttonLab, keyLab, descripLab);
        pane.setLeft(leftBox);

        TextField nameField = new TextField();
        nameField.setPromptText("Name...");
        TextField xField = new TextField();
        xField.setPromptText("0.0");
        TextField yField = new TextField();
        yField.setPromptText("0.0");
        
        ObservableList<MouseButton> buttonList = FXCollections.observableArrayList(
            MouseButton.PRIMARY, MouseButton.SECONDARY, MouseButton.MIDDLE,
            MouseButton.FORWARD, MouseButton.BACK, MouseButton.NONE
        );
        ChoiceBox<MouseButton> buttonBox = new ChoiceBox<MouseButton>(buttonList);
        buttonBox.setTooltip(new Tooltip("Select button to click"));
        buttonBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
            {
                newTaskButton = buttonList.get((int)arg2);
            }
        });
        TextField keyField = new TextField();
        keyField.setMaxWidth(18.0);
        keyField.setPromptText("Enter character...");
        TextArea descripArea = new TextArea();
        descripArea.setMaxSize(150, 100);
        descripArea.setPromptText("Enter description...");
        rightBox.getChildren().addAll(nameField, xField, yField, buttonBox, keyField, descripArea);
        pane.setRight(rightBox);

        HBox bottomBar = new HBox();
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            Task newTask = null;
            if(keyField.getText().length() != 0)
            {
                newTask = new Task(KeyCode.getKeyCode(String.valueOf(keyField.getCharacters().charAt(0))));
            } else {
                newTask = new Task(Double.parseDouble(xField.getText()), Double.parseDouble(yField.getText()), newTaskButton);
            }

            if(nameField.getText().length() != 0)
            {
                newTask.setName(nameField.getText());
            }

            if(descripArea.getText().length() != 0)
            {
                newTask.setDescription(descripArea.getText());
            }

            list.add(newTask);
            window.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());
        bottomBar.getChildren().addAll(doneButton, cancelButton);
        pane.setBottom(bottomBar);

        Scene scene = new Scene(pane);
        window.setScene(scene);
        window.show();
    }    
}

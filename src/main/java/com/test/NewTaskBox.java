package com.test;

import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewTaskBox extends Test
{    
    private static MouseButton newTaskButton = MouseButton.PRIMARY;

    protected static double inX, inY;

    /**
     * Displays new Task generation Window
     */
    public static void display()
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Task");
        window.setMinWidth(400);
        window.setMinHeight(600);
        window.setResizable(false);

        BorderPane pane = new BorderPane();
        VBox mainBox = new VBox();
        HBox box0 = new HBox();
        box0.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box01 = new VBox();
        VBox box02 = new VBox();
        box0.getChildren().addAll(box01, box02);
        HBox box1 = new HBox();
        box1.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box11 = new VBox();
        VBox box12 = new VBox();
        box1.getChildren().addAll(box11, box12);
        HBox box2 = new HBox();
        box2.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box21 = new VBox();
        VBox box22 = new VBox();
        box2.getChildren().addAll(box21, box22);
        HBox box3 = new HBox();
        box3.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box31 = new VBox();
        VBox box32 = new VBox();
        box3.getChildren().addAll(box31, box32);
        HBox box4 = new HBox();
        box4.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box41 = new VBox();
        VBox box42 = new VBox();
        box4.getChildren().addAll(box41, box42);
        HBox box5 = new HBox();
        box5.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box51 = new VBox();
        VBox box52 = new VBox();
        box5.getChildren().addAll(box51, box52);
        HBox box6 = new HBox();
        box6.setPadding(new Insets(20.0, 10.0, 4.0, 5.0));
        VBox box61 = new VBox();
        VBox box62 = new VBox();
        box6.getChildren().addAll(box61, box62);

        Label nameLab = new Label("Name: ");
        box01.getChildren().add(nameLab);
        Label xPosLab = new Label("X Coord: ");
        box11.getChildren().add(xPosLab);
        Label yPosLab = new Label("Y Coord: ");
        box21.getChildren().add(yPosLab);
        Label buttonLab = new Label("Button: ");
        box31.getChildren().add(buttonLab);
        Label keyLab = new Label("Key: ");
        box41.getChildren().add(keyLab);
        Label descripLab = new Label("Description: ");
        box51.getChildren().add(descripLab);
        Label delayLab = new Label("Delay: ");
        box61.getChildren().add(delayLab);

        TextField nameField = new TextField();
        nameField.setPromptText("Name...");
        box02.getChildren().add(nameField);
        TextField xField = new TextField();
        xField.setPromptText("0.0");
        box12.getChildren().add(xField);
        TextField yField = new TextField();
        yField.setPromptText("0.0");
        box22.getChildren().add(yField);
        TextField delayField = new TextField();
        delayField.setPromptText("0");
        box62.getChildren().add(delayField);
        
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
        box32.getChildren().add(buttonBox);
        TextField keyField = new TextField();
        keyField.setMaxWidth(25.0);
        keyField.setPromptText("A");

        // Courtesy of https://stackoverflow.com/a/33218556/18649233
        UnaryOperator<TextFormatter.Change> modifyChange = c -> {
            if (c.isContentChange()) {
                int newLength = c.getControlNewText().length();
                if (newLength > 1) {
                    // replace the input text with the last len chars
                    String tail = c.getControlNewText().substring(newLength - 1, newLength);
                    c.setText(tail.toUpperCase());
                    // replace the range to complete text
                    // valid coordinates for range is in terms of old text
                    int oldLength = c.getControlText().length();
                    c.setRange(0, oldLength);
                }
            }
            return c;
        };
        keyField.setTextFormatter(new TextFormatter<String>(modifyChange));

        box42.getChildren().add(keyField);
        TextArea descripArea = new TextArea();
        descripArea.setMaxSize(150, 100);
        descripArea.setPromptText("Enter description...");
        descripArea.setWrapText(true);
        box52.getChildren().add(descripArea);
        mainBox.getChildren().addAll(box0, box1, box2, box3, box4, box5, box6);
        pane.setCenter(mainBox);

        HBox bottomBar = new HBox();
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            Task newTask = null;
            if(keyField.getText().length() != 0)
            {
                newTask = new Task(KeyCode.getKeyCode((String.valueOf(keyField.getCharacters().charAt(0))).toUpperCase()));
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
            
            if(delayField.getText().length() != 0)
            {
                newTask.setDelay(Long.valueOf(delayField.getText()));
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

    /**
     * Displays new Task generation Window
     */
    public static void display(String name, String description, double x,double y, String code, MouseButton button, Long delay, int index)
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Task");
        window.setMinWidth(400);
        window.setMinHeight(600);
        window.setResizable(false);

        BorderPane pane = new BorderPane();
        VBox mainBox = new VBox();
        HBox box0 = new HBox();
        box0.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box01 = new VBox();
        VBox box02 = new VBox();
        box0.getChildren().addAll(box01, box02);
        HBox box1 = new HBox();
        box1.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box11 = new VBox();
        VBox box12 = new VBox();
        box1.getChildren().addAll(box11, box12);
        HBox box2 = new HBox();
        box2.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box21 = new VBox();
        VBox box22 = new VBox();
        box2.getChildren().addAll(box21, box22);
        HBox box3 = new HBox();
        box3.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box31 = new VBox();
        VBox box32 = new VBox();
        box3.getChildren().addAll(box31, box32);
        HBox box4 = new HBox();
        box4.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box41 = new VBox();
        VBox box42 = new VBox();
        box4.getChildren().addAll(box41, box42);
        HBox box5 = new HBox();
        box5.setPadding(new Insets(20.0, 10.0, 0.0, 5.0));
        VBox box51 = new VBox();
        VBox box52 = new VBox();
        box5.getChildren().addAll(box51, box52);
        HBox box6 = new HBox();
        box6.setPadding(new Insets(20.0, 10.0, 4.0, 5.0));
        VBox box61 = new VBox();
        VBox box62 = new VBox();
        box6.getChildren().addAll(box61, box62);

        Label nameLab = new Label("Name: ");
        box01.getChildren().add(nameLab);
        Label xPosLab = new Label("X Coord: ");
        box11.getChildren().add(xPosLab);
        Label yPosLab = new Label("Y Coord: ");
        box21.getChildren().add(yPosLab);
        Label buttonLab = new Label("Button: ");
        box31.getChildren().add(buttonLab);
        Label keyLab = new Label("Key: ");
        box41.getChildren().add(keyLab);
        Label descripLab = new Label("Description: ");
        box51.getChildren().add(descripLab);
        Label delayLab = new Label("Delay: ");
        box61.getChildren().add(delayLab);

        TextField nameField = new TextField();
        nameField.setPromptText("Name...");
        nameField.setText(name);
        box02.getChildren().add(nameField);
        TextField xField = new TextField();
        xField.setPromptText("0.0");
        xField.setText(String.valueOf(x));
        box12.getChildren().add(xField);
        TextField yField = new TextField();
        yField.setPromptText("0.0");
        yField.setText(String.valueOf(y));
        box22.getChildren().add(yField);
        TextField delayField = new TextField();
        delayField.setPromptText("0");
        delayField.setText(String.valueOf(delay));
        box62.getChildren().add(delayField);
        
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
        buttonBox.setValue(button);
        box32.getChildren().add(buttonBox);
        TextField keyField = new TextField();
        keyField.setMaxWidth(25.0);
        keyField.setPromptText("A");
        keyField.setText(code);

        // Courtesy of https://stackoverflow.com/a/33218556/18649233
        UnaryOperator<TextFormatter.Change> modifyChange = c -> {
            if (c.isContentChange()) {
                int newLength = c.getControlNewText().length();
                if (newLength > 1) {
                    // replace the input text with the last len chars
                    String tail = c.getControlNewText().substring(newLength - 1, newLength);
                    c.setText(tail.toUpperCase());
                    // replace the range to complete text
                    // valid coordinates for range is in terms of old text
                    int oldLength = c.getControlText().length();
                    c.setRange(0, oldLength);
                }
            }
            return c;
        };
        keyField.setTextFormatter(new TextFormatter<String>(modifyChange));

        box42.getChildren().add(keyField);
        TextArea descripArea = new TextArea();
        descripArea.setMaxSize(150, 100);
        descripArea.setPromptText("Enter description...");
        descripArea.setWrapText(true);
        descripArea.setText(description);
        box52.getChildren().add(descripArea);
        mainBox.getChildren().addAll(box0, box1, box2, box3, box4, box5, box6);
        pane.setCenter(mainBox);

        HBox bottomBar = new HBox();
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            Task newTask = null;
            if(keyField.getText().length() != 0)
            {
                newTask = new Task(KeyCode.getKeyCode(String.valueOf(keyField.getCharacters().charAt(0)).toUpperCase()));
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

            if(delayField.getText().length() != 0)
            {
                newTask.setDelay(Long.valueOf(delayField.getText()));
            }

            list.set(index, newTask);
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

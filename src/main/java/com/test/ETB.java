package com.test;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class ETB extends Controller
{
    private static MouseButton newTaskButton = MouseButton.PRIMARY;
    private ObservableList<MouseButton> buttonList = FXCollections.observableArrayList(
        MouseButton.PRIMARY, MouseButton.SECONDARY, MouseButton.MIDDLE,
        MouseButton.FORWARD, MouseButton.BACK, MouseButton.NONE
    );
    
    protected static double inX, inY;
    
    @FXML private Label nameLabel, xLabel, yLabel, buttonLabel, keyLabel, descriptionLabel, delayLabel;
    @FXML private TextField nameField, xField, yField, keyField, delayField;
    @FXML private TextArea descriptionArea;
    @FXML private ChoiceBox<MouseButton> buttonBox;
    @FXML private Button doneButton, cancelButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        nameField.setText(editTask.getName());
        xField.setText(String.valueOf(editTask.getX()));
        yField.setText(String.valueOf(editTask.getY()));
        keyField.setText(editTask.getCode());
        delayField.setText(String.valueOf(editTask.getDelay()));
        buttonBox.getSelectionModel().select(editTask.getButton());
        descriptionArea.setText(editTask.getDescription());
        
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

        buttonBox.setTooltip(new Tooltip("Select button to click"));
        buttonBox.setItems(buttonList);
        buttonBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
            {
                newTaskButton = buttonList.get((int)arg2);
            }
        });
    }

    @FXML private void doneButtonOnA()
    {
        neTask = null;
        if(keyField.getText().length() != 0)
        {
            neTask = new Task(KeyCode.getKeyCode(String.valueOf(keyField.getCharacters().charAt(0)).toUpperCase()));
        } else {
            neTask = new Task(Double.parseDouble(xField.getText()), Double.parseDouble(yField.getText()), newTaskButton);
        }

        if(nameField.getText().length() != 0)
        {
            neTask.setName(nameField.getText());
        }

        if(descriptionArea.getText().length() != 0)
        {
            neTask.setDescription(descriptionArea.getText());
        }

        if(delayField.getText().length() != 0)
        {
            neTask.setDelay(Long.valueOf(delayField.getText()));
        }

        newStage.close();
    }

    @FXML private void cancelButtonOnA()
    {
        newStage.close();
    }
}

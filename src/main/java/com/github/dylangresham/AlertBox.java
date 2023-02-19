package com.github.dylangresham;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox extends Clicker
{
    /**
     * Prompts user to select the Task # to delete
     */
    public static void display()
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete Task");
        window.setMinWidth(400);
        window.setMinHeight(400);
        window.setResizable(false);

        VBox box = new VBox();
        Label prompt = new Label("Enter Order # to be deleted:");
        TextField orderField = new TextField();
        orderField.setPromptText("1");

        HBox bottomBar = new HBox();
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            if(orderField.getText().length() != 0)
            {
                int idx = Integer.parseInt(orderField.getText()) - 1;
                if(idx < list.size())
                {
                    list.remove(idx);
                    window.close();
                } else {
                    display("Error", "Index is out of bounds of the list. Please fix and click 'Done' again");
                }
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());
        bottomBar.getChildren().addAll(doneButton, cancelButton);

        box.getChildren().addAll(prompt, orderField, bottomBar);

        Scene scene = new Scene(box);
        window.setScene(scene);
        window.show();
    }

    /**
     * TheNewBoston's AlertBox class
     * @param title Title of AlertBox to display
     * @param message Message to display in AlertBox
     */
    public static void display(String title, String message)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(250);

        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * TheNewBoston's AlertBox class with the option to be non-modal
     * @param title Title of AlertBox to display
     * @param message Message to display in AlertBox
     */
    public static void display(String title, String message, boolean modal)
    {
        Stage window = new Stage();

        if(modal) {window.initModality(Modality.APPLICATION_MODAL);}
        window.setTitle(title);
        window.setMinHeight(250);

        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

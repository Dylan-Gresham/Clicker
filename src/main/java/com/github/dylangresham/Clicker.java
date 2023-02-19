package com.github.dylangresham;

import javafx.scene.robot.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;


public class Clicker extends Application
{
    @FXML
    private static Scene primScene;

    @Override
    public void start(Stage primStage) throws Exception
    {
        primStage.setTitle("Clicker");
        primStage.setMaximized(true);

        BorderPane mainPane = new BorderPane();

        primScene = new Scene(mainPane);
        primScene.getStylesheets().add(getClass().getResource("Clicker.css").toExternalForm());

        /* Scrollable table with Name of task, x and y locations if it's a mouse task, keys pressed
         * if it's a keyboard task, order #
         */
        ObservableList<Task> list = FXCollections.observableArrayList();
        
        list.add(new Task(0.0, 0.0, MouseButton.PRIMARY));
        list.get(0).setName("Click Corner");
        list.get(0).setDescription("Clicks to top left corner of the screen");
        
        TableView<Task> table = new TableView<Task>(list);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setId("table");

        TableColumn<Task, Integer> orderCol = new TableColumn<Task, Integer>("Order");
        TableColumn<Task, String> nameCol = new TableColumn<Task, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        TableColumn<Task, String> descriptionCol = new TableColumn<Task, String>("Description");
        descriptionCol.setCellValueFactory((new PropertyValueFactory<Task, String>("description")));
        TableColumn<Task, Double> xCol = new TableColumn<Task, Double>("X");
        xCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("x"));
        TableColumn<Task, Double> yCol = new TableColumn<Task, Double>("Y");
        yCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("y"));
        TableColumn<Task, MouseButton> buttonCol = new TableColumn<Task, MouseButton>("Button");
        buttonCol.setCellValueFactory(new PropertyValueFactory<Task, MouseButton>("button"));
        TableColumn<Task, KeyCode> keyCol = new TableColumn<Task, KeyCode>("Key");
        keyCol.setCellValueFactory(new PropertyValueFactory<Task, KeyCode>("keyCode"));
        table.getColumns().addAll(orderCol, nameCol, xCol, yCol, buttonCol, keyCol, descriptionCol);

        table.setMinSize(100.0, 100.0);

        mainPane.setCenter(table);

        
        /* New task, detect task until key combo is pressed, delete task,
        * re-order tasks (move up/move to top, move down/move to bottom)
        */
        
        /* Run tasks, stop tasks, clear tasks, save task, open task */
        /* Save to txt file as a config, open a config file */



        primStage.setScene(primScene);
        primStage.show();
    }

    private 

    static void setRoot(String fxml) throws IOException
    {
        primScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Clicker.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }
}

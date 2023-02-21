package com.github.dylangresham;

import javafx.scene.robot.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.FileChooser;


public class Clicker extends Application
{
    @FXML
    private static Scene primScene;

    @FXML
    protected static ObservableList<Task> list;

    @FXML
    public boolean run;

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
        list = FXCollections.observableArrayList();
        
        list.add(new Task(100.0, 300.0, MouseButton.PRIMARY));
        list.get(0).setName("Click Corner");
        list.get(0).setDescription("Clicks to top left corner of the screen");

        list.add(new Task(KeyCode.A));
        list.get(1).setName("Type a");
        list.get(1).setDescription("Types the letter 'a' once");
        
        TableView<Task> table = new TableView<Task>(list);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setId("table");

        TableColumn<Task, Integer> orderCol = new TableColumn<Task, Integer>("Order");
        orderCol.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(table.getItems().indexOf(column.getValue()) + 1)); // https://stackoverflow.com/questions/16384879/auto-numbered-table-rows-javafx
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
        TableColumn<Task, String> keyCol = new TableColumn<Task, String>("Key Code");
        keyCol.setCellValueFactory(new PropertyValueFactory<Task, String>("keyCode"));
        table.getColumns().addAll(orderCol, nameCol, xCol, yCol, buttonCol, keyCol, descriptionCol);

        table.setMinSize(100.0, 100.0);

        mainPane.setCenter(table);
        
        /* Detect task until key combo is pressed,
        * re-order tasks (move up/move to top, move down/move to bottom)
        */
        HBox toolBar = new HBox();
        Button newTask = new Button("New");
        newTask.setId("newTask");
        // Button detectTask = new Button("Detect Task");
        // detectTask.setId("detectTask");
        Button deleteTask = new Button("Delete");
        deleteTask.setId("deleteTask");
        toolBar.getChildren().addAll(newTask, deleteTask);
        mainPane.setTop(toolBar);

        VBox moveBar = new VBox();
        Button moveToTop = new Button("Move Top");
        moveToTop.setId("moveToTop");
        Button moveUp = new Button("Move Up");
        moveUp.setId("moveUp");
        Button moveDown = new Button("Move Down");
        moveDown.setId("moveDown");
        Button moveToBottom = new Button("Move Bottom");
        moveToBottom.setId("moveToBottom");
        moveBar.getChildren().addAll(moveToTop, moveUp, moveDown, moveToBottom);
        mainPane.setRight(moveBar);
        
        /* Stop tasks, clear tasks, save task, open task */
        /* Save to txt file as a config, open a config file */
        Button runTasks = new Button("Run");
        runTasks.setId("runTasks");
        Button stopTasks = new Button("Stop");
        stopTasks.setId("stopTasks");
        Label numRunsLab = new Label("Iterations:");
        numRunsLab.setId("numRunsLab");
        TextField numRuns = new TextField();
        numRuns.setId("numRuns");
        numRuns.setMaxWidth(82.0);
        numRuns.setText("1");
        numRuns.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d*")) return;
            numRuns.setText(newValue.replaceAll("[^\\d]", ""));
        });
        Button clearTasks = new Button("Clear Tasks");
        clearTasks.setId("clearTasks");
        Button saveTasks = new Button("Save");
        saveTasks.setId("saveTasks");
        Button openTasks = new Button("Open");
        openTasks.setId("openTasks");
        toolBar.getChildren().addAll(runTasks, stopTasks, numRunsLab, numRuns, clearTasks, saveTasks, openTasks);

        newTask.setOnAction(e -> NewTaskBox.display());
        deleteTask.setOnAction(e -> AlertBox.display());
        clearTasks.setOnAction(e -> {
            list.clear();
            numRuns.setText("1");
        });
        runTasks.setOnAction(e -> {
            for(int i = 0; i < Integer.parseInt(numRuns.getText()); i++)
            {
                if(run)
                {
                    for(int j = 0; j < list.size(); j++)
                    {
                      list.get(j).executeTask();
                    }
                } else break;
            }
        });
        stopTasks.setOnAction(e -> run = false);

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

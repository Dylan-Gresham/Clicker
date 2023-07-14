package com.github.dylangresham;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Clicker extends Application
{
    @FXML
    private static Scene primScene;

    @FXML
    protected static ObservableList<Task> list;

    @Override
    public void start(Stage primStage) throws Exception
    {
        primStage.setTitle("Clicker");
        primStage.setMaximized(true);

        BorderPane mainPane = new BorderPane();

        primScene = new Scene(mainPane);
        primScene.getStylesheets().add(getClass().getResource("Clicker.css").toExternalForm());

        list = FXCollections.observableArrayList();
        
        TableView<Task> table = new TableView<Task>(list);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setId("table");

        TableColumn<Task, Integer> orderCol = new TableColumn<Task, Integer>("Order");
        orderCol.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(table.getItems().indexOf(column.getValue()) + 1)); // https://stackoverflow.com/questions/16384879/auto-numbered-table-rows-javafx
        orderCol.setId("orderCol");
        TableColumn<Task, String> nameCol = new TableColumn<Task, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        nameCol.setId("nameCol");
        TableColumn<Task, String> descriptionCol = new TableColumn<Task, String>("Description");
        descriptionCol.setCellValueFactory((new PropertyValueFactory<Task, String>("description")));
        descriptionCol.setId("descriptionCol");
        TableColumn<Task, Double> xCol = new TableColumn<Task, Double>("X");
        xCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("x"));
        xCol.setId("xCol");
        TableColumn<Task, Double> yCol = new TableColumn<Task, Double>("Y");
        yCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("y"));
        yCol.setId("yCol");
        TableColumn<Task, MouseButton> buttonCol = new TableColumn<Task, MouseButton>("Button");
        buttonCol.setCellValueFactory(new PropertyValueFactory<Task, MouseButton>("button"));
        buttonCol.setId("buttonCol");
        TableColumn<Task, String> keyCol = new TableColumn<Task, String>("Key Code");
        keyCol.setCellValueFactory(new PropertyValueFactory<Task, String>("keyCode"));
        keyCol.setId("keyCol");
        TableColumn<Task, Long> delayCol = new TableColumn<Task, Long>("Delay");
        delayCol.setCellValueFactory(new PropertyValueFactory<Task, Long>("delay"));
        delayCol.setId("delayCol");
        table.getColumns().addAll(orderCol, nameCol, xCol, yCol, buttonCol, keyCol, descriptionCol, delayCol);

        table.setMinSize(100.0, 100.0);

        mainPane.setCenter(table);
        
        // Detect task until key combo is pressed
        HBox toolBar = new HBox();
        Button newTask = new Button("New");
        newTask.setId("newTask");
        // Button detectTask = new Button("Detect Task");
        // detectTask.setId("detectTask");
        Button editTask = new Button("Edit");
        editTask.setId("editTask");
        Button deleteTask = new Button("Delete");
        deleteTask.setId("deleteTask");
        toolBar.getChildren().addAll(newTask, editTask, deleteTask);
        toolBar.setAlignment(Pos.CENTER);
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
        moveBar.setAlignment(Pos.CENTER);
        mainPane.setRight(moveBar);
        
        // Stop tasks
        Button runTasks = new Button("Run");
        runTasks.setId("runTasks");
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
        toolBar.getChildren().addAll(runTasks, numRunsLab, numRuns, clearTasks, saveTasks, openTasks);

        newTask.setOnAction(e -> NewTaskBox.display());
        editTask.setOnAction(e -> {
            Task task = table.getSelectionModel().getSelectedItem();
            int index = list.indexOf(task);
            String name, description, code;
            MouseButton button;
            double x, y;
            Long delay;

            if(task.getName() != null)
            {
                name = task.getName();
            } else {
                name = "";
            }
            if(task.getDescription() != null)
            {
                description = task.getDescription();
            } else {
                description = "";
            }
            if(task.getButton() != null)
            {
                button = task.getButton();
            } else {
                button = MouseButton.NONE;
            }
            if(task.getX() != 0.0)
            {
                x = task.getX();
            } else {
                x = 0.0;
            }
            if(task.getY() != 0.0)
            {
                y = task.getY();
            } else {
                y = 0.0;
            }
            if(task.getCode() != null)
            {
                code = task.getCode();
            } else {
                code = "";
            }
            if(task.getDelay() != null)
            {
                delay = task.getDelay();
            } else {
                delay = Long.valueOf("0");
            }

            NewTaskBox.display(name, description, x, y, code, button, delay, index);
        });
        deleteTask.setOnAction(e -> AlertBox.display());
        clearTasks.setOnAction(e -> {
            list.clear();
            numRuns.setText("1");
        });
        runTasks.setOnAction(e -> {
            int runs = 1;
            try {
                runs = Integer.parseInt(numRuns.getText());
            } catch(NumberFormatException ex) {
                runs = Integer.MAX_VALUE;
            }

            for(int i = 0; i < runs; i++)
            {
               for(int j = 0; j < list.size(); j++)
               {
                    list.get(j).executeTask();
               }
            }
        });

        FileChooser.ExtensionFilter  extFilter = new FileChooser.ExtensionFilter("Text files", "*.txt");
        saveTasks.setOnAction(e -> {
            FileChooser filer = new FileChooser();
            filer.getExtensionFilters().add(extFilter);
            File file = filer.showSaveDialog(primStage);
        
            if(file != null)
            {
                try
                {
                    PrintWriter writer = new PrintWriter(file);
                    for(int i = 0; i < list.size(); i++)
                    {
                        writer.println(list.get(i).getName() + "," + list.get(i).getX() + "," + list.get(i).getY() + ","
                                       + list.get(i).getButton().toString() + "," + list.get(i).getCode() + ","
                                       + list.get(i).getDelay() + "," + list.get(i).getDescription());
                    }
                    writer.close();
                } catch(FileNotFoundException  excp) {
                    System.err.println("File not found");
                }
            }
        });

        openTasks.setOnAction(e -> {
            FileChooser filer = new FileChooser();
            filer.getExtensionFilters().add(extFilter);
            File file = filer.showOpenDialog(primStage);

            if(file != null)
            {
                try
                {
                    list.clear();
                    Scanner fileScan = new Scanner(file);
                    while(fileScan.hasNextLine())
                    {
                        Scanner lineScan = new Scanner(fileScan.nextLine());
                        lineScan.useDelimiter(",");

                        String name = lineScan.next();
                        double x = Double.parseDouble(lineScan.next());
                        double y = Double.parseDouble(lineScan.next());
                        MouseButton button = MouseButton.valueOf(lineScan.next());
                        String keyCode = lineScan.next();
                        long delay = Long.parseLong(lineScan.next());
                        String description = lineScan.next();
                        // While loop for description in case it has comma's in it
                        while(lineScan.hasNext())
                        {
                            description += ", " + lineScan.next();
                        }
                        lineScan.close();

                        Task task = new Task();
                        if(!name.equals("")) task.setName(name);
                        task.setX(x);
                        task.setY(y);
                        task.setButton(button);
                        if(!keyCode.equals("")) task.setKeyCode(KeyCode.getKeyCode(keyCode));
                        if(!description.equals("")) task.setDescription(description);
                        task.setDelay(delay);
                        list.add(task);
                    }
                    fileScan.close();
                } catch(FileNotFoundException excpe) {
                    System.err.println("File not found");
                }
            }
        });

        moveToBottom.setOnAction(e -> {
            Task task = table.getSelectionModel().getSelectedItem();
            list.remove(task);
            list.add(task);
        });

        moveDown.setOnAction(e -> {
            Task task = table.getSelectionModel().getSelectedItem();
            swap(task, list.indexOf(task) + 1);
        });

        moveUp.setOnAction(e -> {
            Task task = table.getSelectionModel().getSelectedItem();
            swap(task, list.indexOf(task) - 1);
        });

        moveToTop.setOnAction(e -> {
            Task task = table.getSelectionModel().getSelectedItem();
            list.remove(task);
            list.add(0, task);
        });

        primStage.setScene(primScene);
        primStage.show();
    }

    private static void setRoot(String fxml) throws IOException
    {
        primScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Clicker.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Swaps two tasks
     * 
     * @param oldTask - The task that is being moved
     * @param newIdx - The index of the task to swap with
     */
    private static void swap(Task oldTask, int newIdx)
    {
        int oldIdx = list.indexOf(oldTask);
        Task tempTask = list.get(newIdx);
        list.set(newIdx, oldTask);
        list.set(oldIdx, tempTask);
    }

    public static void main(String[] args)
    {
        launch();
    }
}

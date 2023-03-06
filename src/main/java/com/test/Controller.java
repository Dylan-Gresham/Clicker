package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;

public class Controller extends Test implements Initializable
{
    @FXML private TableView<Task> table;
    @FXML private TableColumn<Task, Integer> orderCol;
    @FXML private TableColumn<Task, String> nameCol;
    @FXML private TableColumn<Task, Double> xCol;
    @FXML private TableColumn<Task, Double> yCol;
    @FXML private TableColumn<Task, MouseButton> buttonCol;
    @FXML private TableColumn<Task, String> keyCol;
    @FXML private TableColumn<Task, String> descriptionCol;
    @FXML private TableColumn<Task, Long> delayCol;
    @FXML private TextField numRuns;
    
    @Override
    public void initialize(URL url, ResourceBundle resources)
    {   
        list.add(new Task(100.0, 300.0, MouseButton.PRIMARY));
        list.get(0).setName("Click Corner");
        list.get(0).setDescription("Clicks to top left corner of the screen");

        list.add(new Task(KeyCode.A));
        list.get(1).setName("Type a");
        list.get(1).setDescription("Types the letter 'a' once");
        
        numRuns.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d*")) return;
            numRuns.setText(newValue.replaceAll("[^\\d]", ""));
        });
        orderCol.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(table.getItems().indexOf(column.getValue()) + 1));
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        xCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("x"));
        yCol.setCellValueFactory(new PropertyValueFactory<Task, Double>("y"));
        buttonCol.setCellValueFactory(new PropertyValueFactory<Task, MouseButton>("button"));
        keyCol.setCellValueFactory(new PropertyValueFactory<Task, String>("keyCode"));
        descriptionCol.setCellValueFactory((new PropertyValueFactory<Task, String>("description")));
        delayCol.setCellValueFactory(new PropertyValueFactory<Task, Long>("delay"));

        table.getItems().setAll(list);
    }

    @FXML private void newTaskDisplay()
    {
        NewTaskBox.display();
        table.getItems().setAll(list);
    }

    @FXML private void editTasksOnA()
    {
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
        table.getItems().setAll(list);
    }

    @FXML private void deleteTasksOnA()
    {
        AlertBox.display();
    }

    @FXML private void clearTasksOnA()
    {
        list.clear();
        table.getItems().setAll(list);
        
        numRuns.setText("1");
    }

    @FXML private void runTasksOnA()
    {
        for(int i = 0; i < Integer.parseInt(numRuns.getText()); i++)
        {
            for(int j = 0; j < list.size(); j++)
            {
                list.get(j).executeTask(3500);
            }
        }
    }

    @FXML private void saveTasksOnA()
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files", "*.txt");
        FileChooser filer = new FileChooser();
        filer.getExtensionFilters().add(extFilter);
        File file = filer.showSaveDialog(stage);
    
        if(file != null)
        {
            try
            {
                PrintWriter writer = new PrintWriter(file);
                for(int i = 0; i < list.size(); i++)
                {
                    writer.println(list.get(i).getName() + "," + list.get(i).getX() + "," + list.get(i).getY() + ","
                                    + list.get(i).getButton().toString() + "," + list.get(i).getCode() + ","
                                    + list.get(i).getDescription());
                }
                writer.close();
            } catch(FileNotFoundException  excp) {
                System.err.println("File not found");
            }
        }
    }

    @FXML private void openTasksOnA()
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files", "*.txt");
        FileChooser filer = new FileChooser();
        filer.getExtensionFilters().add(extFilter);
        File file = filer.showOpenDialog(stage);

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
                    list.add(task);
                }
                fileScan.close();
                table.getItems().setAll(list);
            } catch(FileNotFoundException excpe) {
                System.err.println("File not found");
            }
        }
    }
    
}

package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller extends Test implements Initializable
{
    @FXML private BorderPane bPane;
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

    private static Scene scene;
    protected static Stage newStage;
    protected static int index;
    protected static Task neTask;
    
    @Override
    public void initialize(URL url, ResourceBundle resources)
    {
        table.getSelectionModel().setCellSelectionEnabled(true);

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
        descriptionCol.setCellFactory(col -> { // Method courtesy of @James_D on stackoverflow.com
            final TableCell<Task, String> cell = new TableCell<Task, String>() {
                private Text text;
    
                {
                    text = new Text();
                    text.getStyleClass().add("cell-text");
                }
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        text.setText(item.toString());
                        text.setWrappingWidth(descriptionCol.getWidth() * 2); // Setting the wrapping width to the Text
                        setGraphic(text);
                    }
                }
            };
            return cell;
        });
        delayCol.setCellValueFactory(new PropertyValueFactory<Task, Long>("delay"));
        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());

        table.getItems().setAll(list);
    }

    @FXML private void newTaskDisplay() throws IOException
    {
        scene = new Scene(loadFXML("NTB"), 640, 480);
        scene.getStylesheets().add(getClass().getResource("NTB.css").toExternalForm());

        newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("New Task");
        newStage.setMinWidth(400);
        newStage.setMinHeight(600);
        newStage.setResizable(false);
        
        newStage.setScene(scene);
        newStage.showAndWait();

        list.add(neTask);

        table.getItems().setAll(list);
    }

    @FXML private void editTasksOnA() throws IOException
    {
        Task task = table.getSelectionModel().getSelectedItem();
        if(task != null)
        {
            index = list.indexOf(task);
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
    
            editTask = new Task();
            editTask.setButton(button);
            editTask.setDelay(delay);
            if(description.length() > 0)
            {
                editTask.setDescription(description);
            }
            if(code.length() > 0)
            {
                editTask.setKeyCode(KeyCode.getKeyCode(code));
            }
            if(name.length() > 0)
            {
                editTask.setName(name);
            }
            editTask.setX(x);
            editTask.setY(y);
    
            scene = new Scene(loadFXML("ETB"), 640, 480);
            scene.getStylesheets().add(getClass().getResource("NTB.css").toExternalForm());
    
            newStage = new Stage();
    
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Edit Task");
            newStage.setMinWidth(400);
            newStage.setMinHeight(600);
            newStage.setResizable(false);
            
            newStage.setScene(scene);
            newStage.showAndWait();
    
            list.set(index, neTask);
    
            table.getItems().setAll(list);
        }
    }

    @FXML private void deleteTasksOnA() throws IOException
    {
        scene = new Scene(loadFXML("DTB"), 640, 480);
        scene.getStylesheets().add(getClass().getResource("NTB.css").toExternalForm());

        newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Delete Task");
        newStage.setMinWidth(400);
        newStage.setMinHeight(400);
        newStage.setResizable(false);
        
        newStage.setScene(scene);
        newStage.showAndWait();

        list.remove(index);

        table.getItems().setAll(list);
    }

    @FXML private void clearTasksOnA()
    {
        list.clear();
        table.getItems().setAll(list);
        
        numRuns.setText("1");
    }

    @FXML private void runTasksOnA()
    {
        if(numRuns.getText().length() > 0)
        {
            int runs = 0;
            try {
                runs = Integer.parseInt(numRuns.getText());

                for(int i = 0; i < runs; i++)
                {
                    for(int j = 0; j < list.size(); j++)
                    {
                        list.get(j).executeTask();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: Couldn't parse numRuns.");
                numRuns.setText("0");
            }  
        } else {
            for(int j = 0; j < list.size(); j++)
            {
                list.get(j).executeTask();
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
                                    + list.get(i).getDescription() + "," + list.get(i).getDelay() / 1000);
                }
                writer.close();
            } catch(FileNotFoundException excp) {
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
                    long delay = Long.parseLong(lineScan.next());
                    // While loop for description in case it has comma's in it
                    while(lineScan.hasNext())
                    {
                        description += ", " + lineScan.next();
                    }
                    lineScan.close();
                    
                    Task task = new Task();
                    if(!name.equals(""))
                    {
                        task.setName(name);
                    } else {
                        task.setName("");
                    }
                    
                    task.setX(x);
                    task.setY(y);
                    task.setButton(button);
                    
                    if(!keyCode.equals(""))
                    {
                        task.setKeyCode(KeyCode.getKeyCode(keyCode));
                    } else {
                        task.setKeyCode(null);
                    }

                    if(!description.equals(""))
                    {
                        task.setDescription(description);
                    } else {
                        task.setDescription("");
                    }

                    task.setDelay(delay * 1000);
                    list.add(task);
                }
                fileScan.close();
                table.getItems().setAll(list);
            } catch(FileNotFoundException excpe) {
                System.err.println("File not found");
            }
        }
    }

    @FXML private void moveToBottomOnA()
    {
        Task task = null;
        try
        {
            task = table.getSelectionModel().getSelectedItem();
            list.remove(task);
            list.add(task);
            table.getItems().setAll(list);
        } catch(Exception e) {
            System.err.println("No task selected");
        }
    }

    @FXML private void moveDownOnA()
    {
        Task task = null;
        try
        {
            task = table.getSelectionModel().getSelectedItem();
            swap(task, list.indexOf(task) + 1);

            table.getItems().setAll(list);
        } catch(Exception e) {
            System.err.println("No task selected");
        }
    }

    @FXML private void moveUpOnA()
    {
        Task task = null;
        try
        {
            task = table.getSelectionModel().getSelectedItem();
            swap(task, list.indexOf(task) - 1);

            table.getItems().setAll(list);
        } catch(Exception e) {
            System.err.println("No task selected");
        }
    }

    @FXML private void moveToTopOnA()
    {
        Task task = null;
        try
        {
            task = table.getSelectionModel().getSelectedItem();
            list.remove(task);
            list.add(0, task);

            table.getItems().setAll(list);
        } catch(Exception e) {
            System.err.println("No task selected");
        }
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
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}

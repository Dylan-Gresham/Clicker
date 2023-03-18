package com.test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    protected static Scene mainScene;
    protected static Task editTask;
    protected static Stage stage;
    protected static ObservableList<Task> list = FXCollections.observableArrayList();
    protected static boolean stopped = true; // True means Tasks will run, false and they won't run

    @Override
    public void start(Stage primStage) throws IOException {
        stage = primStage;
        stage.setTitle("Clicker");
        mainScene = new Scene(loadFXML("Test"), 640, 480);
        mainScene.getStylesheets().add(getClass().getResource("Test.css").toExternalForm());
        mainScene.setOnKeyPressed(e -> {
            if(e.getCharacter().equals("\\"))
            {
                stopped = false;
            }
        });

        stage.setScene(mainScene);
        stage.setMaximized(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
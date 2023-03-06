package com.test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    private static Scene scene;
    protected static Stage stage;

    protected static ObservableList<Task> list = FXCollections.observableArrayList();

    @Override
    public void start(Stage primStage) throws IOException {
        stage = primStage;
        scene = new Scene(loadFXML("Test"), 640, 480);
        scene.getStylesheets().add(getClass().getResource("Test.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
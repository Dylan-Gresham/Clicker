package com.github.dylangresham;

import javafx.application.Application;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

public class MousePositionFinder extends Application {
    @Override
    public void start(Stage arg0) throws Exception
    {
        Robot robot = new Robot();

        while(true) {
            System.out.printf("x: %f, y: %f\n", robot.getMouseX(), robot.getMouseY());
        }
    }

    public static void main(String[] args)  {
        launch();
    }
}

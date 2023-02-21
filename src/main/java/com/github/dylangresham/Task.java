package com.github.dylangresham;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.*;

public class Task
{
    private double x, y;
    private MouseButton button;
    // private KeyCode keyCode;
    private Robot rob;
    private String description, name, keyCode;

    
    public Task(double xPos, double yPos, MouseButton inButton)
    {
        x = xPos;
        y = yPos;
        button = inButton;
        keyCode = "";
        description = null;
        name = null;
        rob = new Robot();
    }

    public Task(KeyCode code)
    {
        keyCode = code.toString();
        x = 0.0;
        y = 0.0;
        button = MouseButton.NONE;
        description = null;
        name = null;
        rob = new Robot();
    }

    public Task()
    {
        keyCode = "";
        x = 0.0;
        y = 0.0;
        button = MouseButton.PRIMARY;
        description = "";
        name = "";
        rob = new Robot();
    }

    public void executeTask()
    {
        if(!keyCode.equals(""))
        {
            rob.keyType(KeyCode.getKeyCode(keyCode));
        } else {
            rob.mouseMove(x, y);
            rob.mouseClick(button);
        }
    }

    public void setName(String s)
    {
        this.name = s;
    }

    public void setDescription(String s)
    {
        this.description = s;
    }

    public void setX(double newX)
    {
        this.x = newX;
    }

    public void setY(double newY)
    {
        this.y = newY;
    }

    public void setKeyCode(KeyCode newCode)
    {
        this.keyCode = newCode.toString();
    }

    public void setButton(MouseButton newButton)
    {
        this.button = newButton;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public MouseButton getButton()
    {
        return button;
    }

    public KeyCode getKeyCode()
    {
        return KeyCode.getKeyCode(keyCode);
    }

    public String getCode()
    {
        return keyCode; 
    }
}

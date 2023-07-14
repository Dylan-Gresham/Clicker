package com.github.dylangresham;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.*;

public class Task
{
    private double x, y;
    private MouseButton button;
    private Robot rob;
    private String description, name, keyCode;
    private Long delay;

    
    public Task(double xPos, double yPos, MouseButton inButton)
    {
        x = xPos;
        y = yPos;
        button = inButton;
        keyCode = "";
        description = null;
        name = null;
        rob = new Robot();
        delay = Long.valueOf(0);
    }

    public Task(double xPos, double yPos, MouseButton inButton, long newDelay)
    {
        x = xPos;
        y = yPos;
        button = inButton;
        keyCode = "";
        description = null;
        name = null;
        rob = new Robot();
        delay = newDelay;
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
        delay = Long.valueOf(0);
    }

    public Task(KeyCode code, long newDelay)
    {
        keyCode = code.toString();
        x = 0.0;
        y = 0.0;
        button = MouseButton.NONE;
        description = null;
        name = null;
        rob = new Robot();
        delay = newDelay;
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
        delay = Long.valueOf(0);
    }

    public void executeTask()
    {
        if(!keyCode.equals(""))
        {
            rob.mouseMove(x, y);
            rob.keyType(KeyCode.getKeyCode(keyCode));
            try
            {
                Thread.sleep(delay);
            } catch(Exception exc) {
                System.exit(1);
            }
        } else if(button == MouseButton.NONE) {
            rob.mouseMove(x, y);
            try
            {
                Thread.sleep(delay);
            } catch(Exception exc) {
                System.exit(1);
            }
        } else {
            rob.mouseMove(x, y);
            rob.mouseClick(button);
            try
            {
                Thread.sleep(delay);
            } catch(Exception exc) {
                System.exit(1);
            }
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

    public void setDelay(Long newDelay)
    {
        this.delay = newDelay;
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

    public Long getDelay()
    {
        return delay;
    }
}

package com.test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

/**
 * The Task class represents a single action to be executed by a robot.
 * A task can be either a mouse click, a mouse move, or a key press.
 * The class provides constructors for creating different types of tasks, 
 * and methods for executing and accessing the details of a task.
 */
public class Task
{
    private double x, y;
    private MouseButton button;
    // private KeyCode keyCode;
    private Robot rob;
    private String description, name, keyCode;
    private Long delay;

    /**
     * Creates a task for a mouse click at the specified xPos and yPos 
     * coordinates with the specified inButton mouse button.
     * 
     * @param xPos - The x-coordinate of this Task
     * @param yPos - The y-coordinate of this Task
     * @param inButton - The mouse button to be clicked
     */
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

    /**
     * Creates a task for a mouse click at the specified xPos and yPos 
     * coordinates with the specified inButton mouse button and newDelay
     * delay in milliseconds.
     * 
     * @param xPos - The x-coordinate of this Task
     * @param yPos - The y-coordinate of this Task
     * @param inButton - The mouse button to be clicked
     * @param newDelay - The delay to occur after this Task is executed
     *                   in milliseconds
     */
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

    /**
     * Creates a task for a key press with the specified code key code.
     * 
     * @param code - The KeyCode for the key to be typed
     */
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

    /**
     * Creates a task for a key press with the specified code key code
     * and newDelay delay in milliseconds.
     * 
     * @param code - The KeyCode for the key to be typed
     * @param newDelay - The delay to occur after this Task is executed
     *                   in milliseconds
     */
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

    /**
     * Creates a default task for a primary mouse click at (0,0)
     * coordinates with no delay.
     */
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

    /**
     * Executes the task with the specified delay delay in milliseconds.
     * 
     * @param delay - The delay after the Task is executed
     */
    public void executeTask(long delay)
    {
        if(!keyCode.equals(""))
        {
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

    /**
     * Executes the task with the specified delay or no delay,
     * depending on the delay attribute of the task.
     */
    public void executeTask()
    {
        if(!keyCode.equals(""))
        {
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

    /**
     * Sets the name of the task to the specified s string.
     * 
     * @param s - The new name of the Task
     */
    public void setName(String s)
    {
        this.name = s;
    }

    /**
     * Sets the description of the task to the specified s string.
     * 
     * @param s - The new description of the Task
     */
    public void setDescription(String s)
    {
        this.description = s;
    }

    /**
     * Sets the x-coordinate of the task to the specified newX value.
     * 
     * @param newX - The new x-coordinate of the Task
     */
    public void setX(double newX)
    {
        this.x = newX;
    }

    /**
     * Sets the y-coordinate of the task to the specified newY value.
     * 
     * @param newY - The new y-coordinate of the Task
     */
    public void setY(double newY)
    {
        this.y = newY;
    }

    /**
     * Sets the key code of the task to the specified newCode value.
     * 
     * @param newCode - The new KeyCode of the Task
     */
    public void setKeyCode(KeyCode newCode)
    {
        this.keyCode = newCode.toString();
    }

    /**
     * Sets the mouse button of the task to the specified newButton value.
     * 
     * @param newButton - The new MouseButton of the Task
     */
    public void setButton(MouseButton newButton)
    {
        this.button = newButton;
    }

    /**
     * Sets the delay of the task to the specified newDelay value.
     * 
     * @param newDelay - The new delay of the Task in milliseconds
     */
    public void setDelay(Long newDelay)
    {
        this.delay = newDelay;
    }

    /**
     * Returns the name of the task.
     * 
     * @return The name of this Task
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the description of the task.
     * 
     * @return The description of this Task
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the x-coordinate of the Task.
     * 
     * @return The x-coordinate of this Task
     */
    public double getX()
    {
        return x;
    }

    /**
     * Returns the y-coordinate of the Task.
     * 
     * @return The y-coordinate of this Task
     */
    public double getY()
    {
        return y;
    }

    /**
     * Returns the MouseButton of the Task.
     * 
     * @return The MouseButton of this Task
     */
    public MouseButton getButton()
    {
        return button;
    }

    /**
     * Returns the KeyCode of the Task.
     * 
     * @return The KeyCode of this Task
     */
    public KeyCode getKeyCode()
    {
        return KeyCode.getKeyCode(keyCode);
    }

    /**
     * Returns the String representation of the KeyCode of the Task.
     * 
     * @return The String representation of the KeyCode of this Task
     */
    public String getCode()
    {
        return keyCode; 
    }

    /**
     * Returns the delay of the Task in milliseconds
     * 
     * @return The delay of this Task in milliseconds 
     */
    public Long getDelay()
    {
        return delay;
    }

    /**
     * Returns the delay of the Task in seconds
     * 
     * @return The delay of this Task in seconds
     */
    public Double getDelayInSeconds()
    {
        return delay / 1000.0;
    }
}

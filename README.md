# Clicker

Clicker is a program that is designed to be a user-programmable auto-clicker.

What this means is that the user will be able to program specific sequences of mouse and keyboard activities and then store them as a .txt file for later use or editing.

The reason that I decided to create this program is because I couldn't find a free program like this and I thought it'd be a fun way to figure out how bots are made (albeit very simple bots).

## How to Download and Run

To use this program, download the latest jar (currently version 1.0.0) on the [GitHub Page](https://github.com/DylanGresham/Clicker).

Once you have the Clicker.jar file downloaded, ensure that you have Java installed and run (double click the icon or use the command `$ java -jar <relative-path-to-Clicker.jar>` in the command line) the Clicker.jar file.

* The jar should run just fine but if it doesn't try installing JavaFX on your machine or updating the Java version you have installed. This program was written using `java 19.0.1 2-22-10-18`

## Using Clicker

Once you have the program downloaded and running, you should be greeted with a blank table and some buttons above and to the right of the blank table. The buttons on top will be used to control what's in the table and running the tasks in the table.

### New Task

To create a new task in the table, click the 'New' button and fill out the new task creation pop-up. Once filled out, click 'Done' and the task will be inserted into the table.
### Edit Task

To edit a task in the table, click the desired task in the table and then click the 'Edit' task button. The same pop-up as the new task will be shown but it will be filled out with the information of the currently selected task. You can modify the details of the selected task as you wish and once you're done, clicking the 'Done' button will update the task in the table.

### Delete Task

To delete a task from the table, click the 'Delete' button and enter the order number of the task that you want to delete. The order number is the spot in the execution queue that the task is located at. For example if the table had three tasks, Tasks A, B, and C and I wanted to delete task B, I'd enter 2 as the order # after clicking the 'Delete' button.

### Run Tasks

To run your tasks simply click the 'Run' button and the tasks will execute one after another in the order of the order # (1 &rarr; 2 &rarr; 3 &rarr; etc...)
#### Iterations box

The iterations box is used to have the tasks loop. For example if you wanted to make tasks A, B, C, and D execute a total of 2 times (A > B > C > D > A > B > C > D), changing the value in the Iterations box will allow this to happen. By default the value of the Iterations box is '1' so the tasks will all be executed 1 time in order. The maximum value for this box is infinite but do be warned that the maximum value that the <strong>program</strong> can execute is 2147483647 (Java's `Integer.MAX_VALUE`).

### Clear Tasks

To fully clear the table, simply click the 'Clear Tasks' button and the task table will be emptied.

### Save Tasks

To save your tasks as a .txt file to be stored or opened back up later, click the 'Save' button, choose your file location, and then enter a name.

### Open a Script

To open a script (whether it's your own or one that someone else sent you), click the 'Open' button and select the .txt file containing the script in the proper format.

### Move Buttons

The move buttons on the right side of the program will move the selected task around the table.

## Current Plans for the Future

* Style New/Edit/Delete task pop-ups
* Add a stop button
* Redo visuals of main table screen
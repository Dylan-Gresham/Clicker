package com.test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DTB extends Controller
{
    @FXML TextField orderField;
    @FXML Button doneButton;
    @FXML Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resources)
    {
        // Nothing to do here
    }

    @FXML private void doneButtonOnA()
    {
        if(orderField.getText().length() != 0)
        {
            int idx = Integer.parseInt(orderField.getText()) - 1;
            if(idx < list.size())
            {
                index = idx;
                newStage.close();
            } else {
                index = -1;
            }
        }
    }

    @FXML private void cancelButtonOnA()
    {
        newStage.close();
    }
}

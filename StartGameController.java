package com.example.t2_hexagon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartGameController {

    @FXML
    private Button back;
    @FXML
    private Button start;
    @FXML
    private Label error;

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        HelloApplication.changeScene("/com/example/t2_hexagon/MyApp.fxml");
    }
    @FXML
    protected void onStartButtonClick(ActionEvent event) {

        if(!inputField.getText().trim().isEmpty())
        {
            Conts.setName(inputField.getText());
            HelloApplication.changeScene("/com/example/t2_hexagon/MyGame.fxml");
        }
        else error.setText("Please write name!!");
    }

    @FXML
    private TextField inputField;
    @FXML
    private void nameBox(){
        Conts.setName(inputField.getText());
        HelloApplication.changeScene("/com/example/t2_hexagon/MyGame.fxml");
    }


}

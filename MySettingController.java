package com.example.t2_hexagon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class MySettingController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private CheckBox music;
    @FXML
    private CheckBox save;
    @FXML Slider sound;



    @FXML
    protected void onBackButtonClick(ActionEvent event)  {
       HelloApplication.changeScene("/com/example/t2_hexagon/MyApp.fxml");
    }
    @FXML
    private void setSound()
    {
        sound.valueProperty().addListener((observable ,oldValue,newValue) -> {
            Conts.setSoundv(newValue.intValue());
        });
    }
    @FXML
    private void checkSave() {
        if (save.isSelected()) Conts.setSave(true);
        else Conts.setSave(false);
    }
    @FXML
    private void checkMusic() {
        if (music.isSelected()) Conts.setMusic(true);
        else Conts.setMusic(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        music.setSelected(Conts.isMusic());
        save.setSelected(Conts.isSave());
        sound.setValue((double) Conts.getSoundv());
    }
}

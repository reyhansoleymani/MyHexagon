package com.example.t2_hexagon;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyAppController implements Initializable {
    @FXML
    private Button start;
    @FXML
    private Button history;
    @FXML
    private Button exit;
    @FXML
    private Button setting;
    @FXML
    private Label score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(String.format("%.1f",Conts.getRecord()));
    }


    @FXML
    protected void onStartButtonClick(ActionEvent event){
        HelloApplication.changeScene("/com/example/t2_hexagon/StartPage.fxml");
    }
    @FXML
    protected void onHistoryButtonClick(ActionEvent event){
       HelloApplication.changeScene("/com/example/t2_hexagon/MyHistory.fxml");
    }
    @FXML
    protected void onExitButtonClick(){
        if(Conts.isSave())makeHistoryFile();
        Platform.exit();
    }
    @FXML
    protected void onSettingButtonClick() {
        HelloApplication.changeScene("/com/example/t2_hexagon/GameSetting.fxml");
    }
    public void makeHistoryFile()
    {

        StringBuilder da = new StringBuilder();

        for (String item : Conts.getHname()) {
            da.append(item);
            da.append(' ');
        }
        da.append('*');
        for (String item : Conts.getHtime()) {
            da.append(item);
            da.append(' ');
        }
        da.append('*');
        for (String item : Conts.getHrecord()) {
            da.append(item);
            da.append(' ');
        }


        File filedata = new File("GameData.txt");
        if(filedata.delete()) {
            String filePath = "GameData.txt";

            try (BufferedWriter filedata1 = new BufferedWriter(new FileWriter(filePath))) {
                filedata1.write(da.toString() + "\n");
                filedata1.write(String.format("%.1f" ,Conts.getRecord()) + "\n");
                filedata1.write(Conts.isMusic() + "\n");
                filedata1.write(Conts.isSave() + "\n");
                filedata1.write(Conts.getSoundv());

            } catch (IOException c) {
                c.printStackTrace();
            }
        }
    }
}
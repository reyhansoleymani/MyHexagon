package com.example.t2_hexagon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.List;

public class MyHistoryController {

    public List<String> name;
    public List<String> record;
    public List<String> time;

    @FXML
    private Button back;

    @FXML
    private ListView<String> historynameList;
    @FXML
    private ListView<String> historytimeList;
    @FXML
    private ListView<String> historyrecordList;

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        HelloApplication.changeScene("/com/example/t2_hexagon/MyApp.fxml");
    }



    public void getGameHistory() {

        name = Conts.getHname();
        time = Conts.getHtime();
        record = Conts.getHrecord();
    }

    @FXML
    public void initialize() {
        getGameHistory();
        historynameList.getItems().addAll(name);
        historyrecordList.getItems().addAll(record);
        historytimeList.getItems().addAll(time);

    }
}


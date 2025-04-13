package com.example.t2_hexagon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HelloApplication extends Application {
    public String data;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;


        readHistoryFile();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        primaryStage.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("R_GAME");

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            makeHistoryFile();
            primaryStage.close();
        });
        if(Conts.isSave())makeHistoryFile();
        primaryStage.show();
    }

    public static void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeHistoryFile()
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
                filedata1.write(String.format( "%.1f" ,Conts.getRecord()) + "\n");
                filedata1.write(Conts.isMusic() + "\n");
                filedata1.write(Conts.isSave() + "\n");
                filedata1.write(Conts.getSoundv() + "\n");

            } catch (IOException c) {
                c.printStackTrace();
            }
        }

    }

    public void readHistoryFile() {
        String filePath = "GameData.txt";
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            data = read.readLine();
            Conts.setRecord(Double.parseDouble(read.readLine()));
            Conts.setMusic(Boolean.parseBoolean(read.readLine()));
            Conts.setSave(Boolean.parseBoolean(read.readLine()));
            Conts.setSoundv(Integer.parseInt(read.readLine()));

        } catch (IOException e) {
            data = "reyn *0 *1 ";
            try (BufferedWriter filedata = new BufferedWriter(new FileWriter(filePath))) {
                filedata.write(data +"\n");
                filedata.write("0.0\n");
                filedata.write("true\n");
                filedata.write("true\n");
                filedata.write("50\n");

            } catch (IOException a) {
                a.printStackTrace();
            }
        }
        String[] bakhsh = data.split("\\*");
////list ghabel taghiiir shodeshh

        List<String> name = new ArrayList<>(Arrays.asList(bakhsh[0].split(" ")));
        List<String> time = new ArrayList<>(Arrays.asList(bakhsh[1].split(" ")));
        List<String> record = new ArrayList<>(Arrays.asList(bakhsh[2].split(" ")));


        Conts.setHname(name);
        Conts.setHrecord(record);
        Conts.setHtime(time);
    }
    public static void main(String[] args) {
        launch();
    }
}
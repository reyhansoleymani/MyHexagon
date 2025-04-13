package com.example.t2_hexagon;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameController implements Initializable {

    @FXML private Button back, resume, pause;
    @FXML private AnchorPane playground;
    @FXML private StackPane pane;
    @FXML private Polygon polygon;

    @FXML private Polygon p1;
    @FXML private Polygon p2;
    @FXML private Polygon p3;
    @FXML private Polygon p4;
    @FXML private Polygon p5;
    @FXML private Polygon p6;

    @FXML private Label bestscore;
    @FXML private Label time;
    @FXML private Label gameover;

    @FXML private Polygon mane1;
    @FXML private  Polygon mane2;
    @FXML private  Polygon mane3;
    @FXML private  Polygon mane4;
    @FXML private  Polygon mane5;
    @FXML private  Polygon mane6;

    private Color temColor;
    private MediaPlayer mediaPlayer;
    private Circle marker;
    private double markerPosition = 0;

    private double speedsafhe = 20;
    private double speedMane = 0.991;

    private int sides = 6;

    private double timer=0;

    private  RotateTransition charkheshSafhe;
    private Timeline maneha;
    private Timeline myTimer;
    private AnimationTimer maneMove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicOn();
        bestscore.setText(String.format("%.1f" ,Conts.getRecord()));
        temColor= chooseColor();
        addPolygan();
        addMarker();
        playground.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(this::setupKeyControlsMarker);
            }
        });
        changeColor();
        maneHarekat();
        startAnimationSafhe();
        startTimer();
    }

    private void startTimer() {
        myTimer = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            time.setText(String.format("%.1f" ,timer));
            timer+=0.02;
        }));
        myTimer.setCycleCount(Timeline.INDEFINITE);
        myTimer.play();
    }

    ////////////////////////
    public int i=0;
    private void maneHarekat() {
        setManeCenter();
        maneha = new Timeline(new KeyFrame(Duration.seconds(3), e -> {

            List<Polygon> mane = chooseMane();
            maneMove(mane);
            if(i%2 == 0)
            {
                temColor = chooseColor();
                changeColor();
                marker.setFill(temColor);
                polygon.setFill(temColor);
                setSpeed();
            }
            i++;
        }));
        maneha.setCycleCount(Timeline.INDEFINITE);
        maneha.play();

    }
    private void changeColor() {

        Color[] colors = {
                Color.rgb(0, 50, 0),
                Color.rgb(34, 64, 64),
                Color.rgb(48, 25, 52),
                Color.rgb(150, 40, 10)
        };
        Color color1= colors[new Random().nextInt(colors.length)];
        p1.setFill(color1);
        p3.setFill(color1);
        p5.setFill(color1);
        if(color1.equals(Color.rgb(34,64,64)))
        {
            p2.setFill(Color.rgb(10, 100, 120));
            p4.setFill(Color.rgb(10, 100, 120));
            p6.setFill(Color.rgb(10, 100, 120));
        }
        if(color1.equals(Color.rgb(48,25,52)))
        {
            p2.setFill(Color.rgb(75,0,130));
            p4.setFill(Color.rgb(75,0,130));
            p6.setFill(Color.rgb(75,0,130));
        }
        if(color1.equals(Color.rgb(0,50,0)))
        {
            p2.setFill(Color.rgb(0,100,0));
            p4.setFill(Color.rgb(0,100,0));
            p6.setFill(Color.rgb(0,100,0));
        }
        if(color1.equals(Color.rgb(150,40,10)))
        {
            p2.setFill(Color.rgb(178,102,51));
            p4.setFill(Color.rgb(178,102,51));
            p6.setFill(Color.rgb(178,102,51));
        }
    }
    private boolean markerCheck(Polygon a) {
        Shape barkhord = Shape.intersect(marker,a);
        if (!barkhord.getBoundsInLocal().isEmpty()) return true;

        return false;
    }
    private void maneMove(List<Polygon> p) {
        maneMove = new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (Polygon a : p) {
                    double[] mxy = new double[8];
                    for (i=0;i<a.getPoints().size();i++)
                    {
                        a.getPoints().set(i,a.getPoints().get(i)*speedMane);
                        mxy[i]=a.getPoints().get(i);
                    }


                    double x1 = mxy[4];
                    double y1 = mxy[5];
                    double x2 = mxy[6];
                    double y2 = mxy[7];

                    if (markerCheck(a)) {
                        maneMove.stop();
                        stopGame();
                    }
                    double distance = Math.abs((x2 - x1) * y1 - (y2 - y1) * x1) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    if (distance < 40) {
                        for (Polygon c : p) playground.getChildren().remove(c);
                    }

                }
            }
        };
        maneMove.start();
    }
    ////////////////////////
    private List<Polygon> chooseMane() {

        List<Polygon> list = Arrays.asList(mane1, mane2,mane3,mane4,mane5,mane6);

        int subsetSize = new Random().nextInt(sides-1) + 1;
        List<Polygon> subset = new ArrayList<>(list);

        ////dari khali mibandi
        Collections.shuffle(subset);////ke biad ye halat jaygasht random bde!
        subset.removeIf(Objects::isNull);

        List<Polygon> newManeList = new ArrayList<>();
        Color color = chooseColor();
        for (int i = 0; i < subsetSize; i++) {
            Polygon original = subset.get(i);
            if (original != null) {
                Polygon copy = new Polygon();

               for (int j=0 ; j< original.getPoints().size();j++)
               {
                   copy.getPoints().add(original.getPoints().get(j));
               }

                copy.setLayoutX(playground.getWidth()/2);
                copy.setLayoutY(playground.getHeight()/2);
                copy.setFill(color);
                copy.setVisible(true);
                playground.getChildren().add(copy);
                newManeList.add(copy);
            }
        }

        return newManeList.subList(0, subsetSize);
    }
    /////////////////////////stage :
    private void centerPolygon() {
        if (polygon != null) {
            polygon.setLayoutX(playground.getWidth() / 2);
            polygon.setLayoutY(playground.getHeight() / 2);

            p1.setLayoutX(playground.getWidth() / 2);
            p1.setLayoutY(playground.getHeight() / 2);

            p2.setLayoutX(playground.getWidth() / 2);
            p2.setLayoutY(playground.getHeight() / 2);

            p3.setLayoutX(playground.getWidth() / 2);
            p3.setLayoutY(playground.getHeight() / 2);

            p4.setLayoutX(playground.getWidth() / 2);
            p4.setLayoutY(playground.getHeight() / 2);

            p5.setLayoutX(playground.getWidth() / 2);
            p5.setLayoutY(playground.getHeight() / 2);

            p6.setLayoutX(playground.getWidth() / 2);
            p6.setLayoutY(playground.getHeight() / 2);

        }
        setManeCenter();
        updateMarkerPosition();
    }
    private void setManeCenter() {
        mane1.setLayoutX(playground.getWidth() / 2);
        mane1.setLayoutY(playground.getHeight() / 2);

        mane2.setLayoutX(playground.getWidth() / 2);
        mane2.setLayoutY(playground.getHeight() / 2);

        mane3.setLayoutX(playground.getWidth() / 2);
        mane3.setLayoutY(playground.getHeight() / 2);

        mane4.setLayoutX(playground.getWidth() / 2);
        mane4.setLayoutY(playground.getHeight() / 2);

        mane5.setLayoutX(playground.getWidth() / 2);
        mane5.setLayoutY(playground.getHeight() / 2);

        mane6.setLayoutX(playground.getWidth() / 2);
        mane6.setLayoutY(playground.getHeight() / 2);

    }
    private void addPolygan() {
        polygon.setFill(temColor);
        playground.widthProperty().addListener((obs, oldVal, newVal) -> centerPolygon());
        playground.heightProperty().addListener((obs, oldVal, newVal) -> centerPolygon());
        polygon.setFill(temColor);
    }
    private void startAnimationSafhe() {
        charkheshSafhe = new RotateTransition(Duration.seconds(speedsafhe), playground);
        charkheshSafhe.setByAngle(360);
        charkheshSafhe.setCycleCount(RotateTransition.INDEFINITE);
        charkheshSafhe.setInterpolator(javafx.animation.Interpolator.LINEAR);
        charkheshSafhe.play();

    }
    //////markers:
    public void addMarker() {
        marker = new Circle(6, temColor);
        playground.getChildren().add(marker);
    }
    private void setupKeyControlsMarker() {
        playground.getScene().getWindow().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                markerPosition = (markerPosition - 0.05 + 1) % 1.0;
            } else if (event.getCode() == KeyCode.LEFT) {
                markerPosition = (markerPosition + 0.05) % 1.0;
            }
            updateMarkerPosition();
        });
    }
    private void updateMarkerPosition() {

        int segmentIndex = (int) (markerPosition * sides);
        double t = (markerPosition * sides) % 1.0;


        ////////////////////////////////////////ykam ajibe vli kar kardesh

        double x1 = polygon.getPoints().get(segmentIndex * 2) + polygon.getLayoutX();
        double y1 = polygon.getPoints().get(segmentIndex * 2 + 1) + polygon.getLayoutY() ;
        double x2 = polygon.getPoints().get((segmentIndex * 2 + 2) % (sides * 2)) + polygon.getLayoutX();
        double y2 = polygon.getPoints().get((segmentIndex * 2 + 3) % (sides * 2)) +  polygon.getLayoutY();

/////فرمول درون‌یابی خطی Linear Interpolation
        double markerX = x1 + t * (x2 - x1);
        double markerY = y1 + t * (y2 - y1);



        marker.setFill(temColor);
        marker.setCenterX(markerX );
        marker.setCenterY(markerY );
    }
    ///changes:
    private void setSpeed() {
        speedsafhe-=1;

        if(speedMane+0.005<1) speedMane+=0.005;
        if (speedsafhe>0)startAnimationSafhe();
    }


    private void musicOn() {
        if (Conts.isMusic()) {
            String musicPath = "src/main/resources/com/example/t2_hexagon/Pink Panther.mp3";
            Media sound = new Media(new File(musicPath).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume((double) Conts.getSoundv());
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }
    private Color chooseColor() {
        Color[] colors = {
                Color.rgb(139, 0, 0), // Dark Red
                Color.rgb(64, 64, 64), // Dark Gray
                Color.rgb(200, 200, 200), // Light Gray
                Color.rgb(100, 0, 0) // Light Red
        };
        return colors[new Random().nextInt(colors.length)];
    }
    private void stopGame() {

        if(maneMove!=null)maneMove.stop();
        if (maneha!=null){
            maneha.stop();
            maneha=null;
        }
        if (myTimer!=null){
            myTimer.stop();
            myTimer=null;
        }
        if (charkheshSafhe!=null)
        {
            charkheshSafhe.stop();
            charkheshSafhe=null;
        }

        if(Conts.isSave())makeHistoryFile();
        if (Conts.isMusic()) mediaPlayer.stop();
        gameover.setVisible(true);
    }
    //////////////////
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
    private boolean pa = false;
    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        stopGame();
        if(Conts.isSave()){
            if(Conts.getRecord() < timer ) Conts.setRecord(timer);
            Conts.addHname(Conts.getName());
            Conts.addHrecord(String.format("%.1f",timer));

            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            Conts.addHtime(LocalDate.now().format(formater));

        }
        ///add score + back page + add history
        if (Conts.isMusic()) mediaPlayer.stop();
        HelloApplication.changeScene("/com/example/t2_hexagon/StartPage.fxml");
    }
    @FXML
    protected void onPauseButtonClick(ActionEvent event) {
        pa = true;
        pauseGame();
    }
    private void pauseGame() {
        if(maneMove != null)maneMove.stop();
        if (maneha!=null)maneha.stop();
        if (myTimer!=null)myTimer.stop();
        if (charkheshSafhe!=null)charkheshSafhe.stop();
        if (Conts.isMusic()) mediaPlayer.stop();
    }
    @FXML
    protected void onResumeButtonClick(ActionEvent event) {
        if(pa)startGame();
        pa=false;
    }
    private void startGame() {
        if (Conts.isMusic()) mediaPlayer.play();
        maneMove.start();
        if (maneha!=null)maneha.play();

        if (myTimer!=null)myTimer.play();
        if (charkheshSafhe!=null)charkheshSafhe.play();
    }

}

package com.example.t2_hexagon;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Conts {

    private static double record = 0;
    private static String name;
    private static int soundv=0;

    public static int getSoundv() {
        return soundv;
    }

    public static void setSoundv(int soundv) {
        Conts.soundv = soundv;
    }

    private static List<String> hname =  new ArrayList<>(List.of("1"));
    private static List<String> hrecord = new ArrayList<>(List.of("1"));
    private static List<String> htime = new ArrayList<>(List.of("1"));

    private static boolean save = true;
    private static boolean music = true;

    public static boolean isSave() {
        return save;
    }
    public static void setSave(boolean save) {
        Conts.save = save;
    }

    public static boolean isMusic() {
        return music;
    }
    public static void setMusic(boolean music) {
        Conts.music = music;
    }

    public static double getRecord()
    {
        return record;
    }
    public static void setRecord(double newRecord)
    {
        record = newRecord;

    }

    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        Conts.name = name;
    }

    public static List<String> getHname() {
        return hname;
    }
    public static void setHname(List<String> hname) {
        Conts.hname = hname;
    }
    public static void addHname(String name){hname.add(name);}

    public static List<String> getHrecord() {
        return hrecord;
    }
    public static void setHrecord(List<String> hrecord) {
        Conts.hrecord = hrecord;
    }
    public static void addHrecord(String record){hrecord.add(record);}

    public static List<String> getHtime() {
        return htime;
    }
    public static void setHtime(List<String> htime) {
        Conts.htime = htime;
    }
    public static void addHtime(String time){htime.add(time);}

}

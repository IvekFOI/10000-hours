package com.example.matija.registration0407;

public class Exampleitem {


    private String catexa;
    private String subcatexa;
    private String timeexa;

    public Exampleitem(String catexast, String subcatexast, String timeexast) {
        catexa = catexast;
        subcatexa = subcatexast;
        int brojmin = Integer.parseInt(timeexast);
        int sati = brojmin/60;
        int minute = brojmin % 60;
        String satii = Integer.toString(sati);
        String minutee = Integer.toString(minute);
        timeexa = satii +" hours and "+ minutee + " minutes";

    }

    public String getCatexa() {
        return catexa;
    }

    public String getSubcatexa() {
        return subcatexa;
    }

    public String getTimeexa() {
        return timeexa;
    }


}

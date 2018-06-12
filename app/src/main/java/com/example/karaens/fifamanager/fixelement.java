package com.example.karaens.fifamanager;

public class fixelement {
    private String team1,team2,date,time,venue;
    private byte[] icon1,icon2;

    public fixelement(String team1, String team2, String date, String time, String venue, byte[] icon1, byte[] icon2) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public byte[] getIcon1() {
        return icon1;
    }

    public void setIcon1(byte[] icon1) {
        this.icon1 = icon1;
    }

    public byte[] getIcon2() {
        return icon2;
    }

    public void setIcon2(byte[] icon2) {
        this.icon2 = icon2;
    }

}

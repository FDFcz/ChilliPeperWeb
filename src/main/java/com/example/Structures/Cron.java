package com.example.Structures;

import java.sql.Time;

public class Cron {
    private int id;
    private Schedule schedule;
    private int startTime,endTime;

    public Cron(int startTime, int endTime) {
        this.id = -1;
        this.schedule = null;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Cron(int id, Schedule schedule, int startTime, int endTime) {
        this.id = id;
        this.schedule = schedule;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {return id;}
    public Schedule getSchedule() {return schedule;}
    public int getStartTime() {return startTime;}
    public int getEndTime() {return endTime;}
}

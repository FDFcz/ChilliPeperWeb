package com.example.Structures;

public class Schedule {
    private int id;
    private float temperature;
    private float light;
    private int humidity;
    public Schedule(int id, float temperature, float light, int humidity)
    {
        this.id = id;
        this.temperature = temperature;
        this.light = light;
        this.humidity = humidity;
    }
    public int getId() {return id;}
    public float getTemperature() {return temperature;}
    public float getLight() {return light;}
    public int getHumidity() {return humidity;}
}

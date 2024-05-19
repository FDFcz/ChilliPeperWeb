package com.example.Structures;

import com.example.Controlers.ChiliPeperApplication;

import java.sql.Date;
import java.sql.Time;
import java.util.Random;

public class Teracota
{
    public static enum PlantTypes {
        Jalapenos,
        Poblano,
        Habareno
    }
    private int id;
    private String nome;
    private Date plantedAt= null;
    private Plant plant;

    public Teracota(int id, String name, PlantTypes type,Date plantedAt)
    {
        this.id = id;
        this.nome = name;
        this.plantedAt = plantedAt;
        plant = ChiliPeperApplication.getPlant(type.ordinal());
    }
    public Teracota(int id, String name, PlantTypes type,int xxxx)
    {
        this.id = id;
        this.nome = name;
        plant = ChiliPeperApplication.getPlant(type.ordinal());
    }
    public Teracota(String name, PlantTypes type)
    {
        this.id = -1;
        this.nome = name;
        plant = ChiliPeperApplication.getPlant(type.ordinal());
    }

    public int getId(){return id;}
    public int getPlantID(){return plant.getPlantTypes().ordinal();}
    public String getNome() {return nome;}
    public Date getPlantedAt() {return plantedAt;}
    public int getGrowDays(){return plant.getGrowDays();}

    public float getActualTemp()
    {
        Random rn = new Random();
        return rn.nextFloat(0,50);
    }
    public float getActuallight()
    {
        Random rn = new Random();
        return rn.nextFloat(0,1);
    }
    public float getActualHumidity()
    {
        Random rn = new Random();
        return rn.nextFloat(0,50);
    }
}

package com.example.medreminder3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medicine  {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String time;
    private String startDate;
    private String endDate;

    private String xhours;

    private int image;

    private int medCap;

    private String refillTime;

    private int pillCount;
    private String Uid;
    private static Medicine instance = null;

    private Medicine() {
    }

    public Medicine(int id, String name, String description, String time, String startDate, String endDate, String xhours ,int image,int medCap, String refillTime,int pillCount , String Uid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
        this.xhours = xhours;
        this.image = image ;
        this.medCap = medCap;
        this.refillTime = refillTime;
        this.pillCount = pillCount;
        this.Uid = Uid;
    }

    public static Medicine getInstance() {
        if (instance == null) {
            instance = new Medicine();
        }
        return instance;
    }


    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getXhours() {return xhours;}

    public void setXhours(String xhours) {this.xhours = xhours;}

    public int getMedCap() {return medCap;}

    public void setMedCap(int medCap) {this.medCap = medCap;}

    public String getRefillTime() {return refillTime;}

    public void setRefillTime(String refillTime) {this.refillTime = refillTime;}

    public int getPillCount() {return pillCount;}

    public void setPillCount(int pillCount) {this.pillCount = pillCount;}

    public int getImage() {
        return image;
    }

    public int setImage(int image) {
        this.image = image;
        return image;
    }


    @Override

    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=' " + description + '\'' +
                ", time='" + time + '\'' +
                ", Xhours='" + xhours + '\'' +
                ", Medication capacity'" + medCap + '\'' +
                ", Refill time'" + refillTime + '\'' +
                ", Pill counter ='" + pillCount + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate +'\'' +
                ", image=" + image +
                '}';
    }



}

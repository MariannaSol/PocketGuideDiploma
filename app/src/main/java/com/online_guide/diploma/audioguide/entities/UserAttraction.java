package com.online_guide.diploma.audioguide.entities;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

import static com.online_guide.diploma.audioguide.constants.BaseField.*;

public class UserAttraction extends SugarRecord {

    @Unique
    private long ID;

    @Column(name = ATTRACTION_NAME)
    private String name;

    @Column(name = "_public")
    private boolean type;

    @Column(name = INFO)
    private String info;

    @Column(name = COORD_X)
    private double coordX;

    @Column(name = COORD_Y)
    private double coordY;

    @Column(name = CITY_ID)
    private int city_Id;

    public UserAttraction(double coordX, double coordY, String info, String name, boolean type) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.info = info;
        this.name = name;
        this.type = type;
    }

    public UserAttraction() {
    }

    public int getCity_Id() {
        return city_Id;
    }

    public void setCity_Id(int city_Id) {
        this.city_Id = city_Id;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public long getID() {
        return ID;
    }

    public void setId(long id) {
        this.ID = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserAttraction{" +
                "city_Id=" + city_Id +
                ", ID=" + ID +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", info='" + info + '\'' +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}

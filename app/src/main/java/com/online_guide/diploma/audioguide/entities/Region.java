package com.online_guide.diploma.audioguide.entities;

import com.online_guide.diploma.audioguide.constants.BaseField;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.HashSet;
import java.util.Set;


public class Region extends SugarRecord {

    @Unique
    private long id;

    @Column(name = BaseField.REGION_NAME)
    private String name;

    @Ignore
    private Set<City> citySet;

    public Region(String name) {
        this.name = name;
        citySet = new HashSet<>();
    }

    public Region() {

    }

    public String getName() {
        return name;
    }


    public long getID() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCity(City city) {
        citySet.add(city);
    }

    public void removeCity(City city) {
        citySet.remove(city);
    }

    public Set<City> getCitySet() {
        return citySet;
    }

    public void setCitySet(Set<City> citySet) {
        this.citySet = citySet;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                '}';
    }
}

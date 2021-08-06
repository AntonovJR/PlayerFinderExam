package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;

@Entity(name = "towns")
public class Town extends BaseEntity {


    private String name;
    @Positive
    private Long population;
    @Column(name = "travel_guide", columnDefinition = "TEXT")
    private String travelGuide;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }

}

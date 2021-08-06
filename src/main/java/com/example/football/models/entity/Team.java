package com.example.football.models.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity(name = "teams")
public class Team extends BaseEntity {

    private String name;
    @Column(name = "stadium_name")
    private String stadiumName;
    @Column(name = "fan_base")
    private Long fanBase;
    @Column(columnDefinition = "TEXT")
    private String history;
    @OneToOne
    private Town town;


    public Team() {
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Long getFanBase() {
        return fanBase;
    }

    public void setFanBase(Long fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}

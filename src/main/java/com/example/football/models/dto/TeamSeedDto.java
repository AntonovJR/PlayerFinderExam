package com.example.football.models.dto;

import com.example.football.models.entity.Town;
import com.google.gson.annotations.Expose;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class TeamSeedDto {
    @Expose
    @Size(min = 3)
    @NotNull
    private String name;
    @Expose
    @Size(min = 3)
    @NotNull
    private String stadiumName;
    @Expose
    @Min(1000)
    @NotNull
    private Long fanBase;
    @Expose
    @Size(min = 10)
    @NotNull
    private String history;
    @Expose
    @NotNull
    private String townName;


    public TeamSeedDto() {
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

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}

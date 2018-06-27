package de.figge.cocktailabend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Statistic {
    @JsonFormat(pattern = "HH:mm", timezone = "CEST+01:00")
    private Date date;
    private int jumboCount;
    private int cocktailCount;

    public Statistic(Date date, int jumboCount, int cocktailCount) {
        this.date = date;
        this.jumboCount = jumboCount;
        this.cocktailCount = cocktailCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getJumboCount() {
        return jumboCount;
    }

    public void setJumboCount(int jumboCount) {
        this.jumboCount = jumboCount;
    }

    public int getCocktailCount() {
        return cocktailCount;
    }

    public void setCocktailCount(int cocktailCount) {
        this.cocktailCount = cocktailCount;
    }
}

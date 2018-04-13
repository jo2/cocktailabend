package de.figge.cocktailabend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"number", "jumbo"})
}, name = "cocktail")
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "jumbo")
    private boolean jumbo;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date date = new Date();

    @Column(name = "called")
    private int called;

    public Cocktail(){

    }

    public Cocktail(int number, boolean jumbo) {
        this.number = number;
        this.jumbo = jumbo;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "number=" + number +
                ", jumbo=" + jumbo +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isJumbo() {
        return jumbo;
    }

    public void setJumbo(boolean jumbo) {
        this.jumbo = jumbo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCalled() {
        return called;
    }

    public void setCalled(int called) {
        this.called = called;
    }

    public void incCalled() {
        this.called++;
    }
}

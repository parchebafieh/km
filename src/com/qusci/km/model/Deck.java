package com.qusci.km.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/16/14
 * Time: 10:30 PM
 */

@DatabaseTable(tableName = "DECK")
public class Deck implements Serializable {


    public Deck(int day, String name, int order) {
        this.day = day;
        this.name = name;
        this.order = order;
    }

    public Deck() {
    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int order;

    @DatabaseField
    private int day;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    @Override
    public int hashCode() {
        return order;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Deck)) {
            return false;
        }
        return this.getOrder() == ((Deck) o).getOrder();
    }
}

package com.qusci.km.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/7/12
 * Time: 2:13 PM
 */
@DatabaseTable(tableName = "CATEGORY")
public class Category implements Serializable{


    public Category(){}
    public Category(String name){
        this.name=name;
    }

    public Category(int id){
        this.id=id;
    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    private String name;

//    @DatabaseField
//    private Category parent;

    @DatabaseField
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Category getParent() {
//        return parent;
//    }

//    public void setParent(Category parent) {
//        this.parent = parent;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

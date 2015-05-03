package com.qusci.km.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 9/14/12
 * Time: 10:38 AM
 */

@DatabaseTable(tableName = "KEY")
public class Key implements Serializable {


    public Key() {

    }

    public Key(int id) {
        this.id = id;

    }

    public Key(String name, String value) {
        this.name = name;
        this.value = value;
    }


    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String value;

    @DatabaseField
    private Date submissionDate;

    @DatabaseField
    private int categoryId;

    @DatabaseField
    private Date lastAnsweredDate;

    @DatabaseField
    private Date deadlineDate;


    @DatabaseField(defaultValue = "0")
    private Integer status;

    @DatabaseField(defaultValue = "0")
    private Integer deck ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public Integer getDeck() {
        return deck;
    }

    public void setDeck(Integer deck) {
        this.deck = deck;
    }

    public Date getLastAnsweredDate() {
        return lastAnsweredDate;
    }

    public void setLastAnsweredDate(Date lastAnsweredDate) {
        this.lastAnsweredDate = lastAnsweredDate;
    }


    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static interface OnKeySelectionListener{

        boolean onSelection(Key key);

    }
}

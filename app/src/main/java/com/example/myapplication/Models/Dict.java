package com.example.myapplication.Models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dict implements Serializable {
    private Integer id;
    private String text;
    private  String Abbrev;

    public Dict() {
    }

    public Dict(Integer id, String text) {
        super();
        this.id = id;
        this.text = text;
    }
    public Dict(Integer id, String text, String abbrev) {
        super();
        this.id = id;
        this.text = text;
        this.Abbrev = abbrev;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbrev() {
        return Abbrev;
    }

    public void setAbbrev(String abbrev) {
        Abbrev = abbrev;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Why should I rewrite toString()?
     *
     * Because the adapter is displaying data, if the object passed to the adapter is not a string, use the object directly to .toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
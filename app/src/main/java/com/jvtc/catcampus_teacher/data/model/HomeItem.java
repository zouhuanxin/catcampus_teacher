package com.jvtc.catcampus_teacher.data.model;

public class HomeItem {
    private int id;
    private int icon;
    private String name;

    public HomeItem(){

    }

    public HomeItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public HomeItem(int id, int icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

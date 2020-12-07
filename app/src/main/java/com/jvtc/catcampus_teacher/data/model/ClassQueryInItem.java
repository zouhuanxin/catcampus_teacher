package com.jvtc.catcampus_teacher.data.model;

import java.util.List;

public class ClassQueryInItem {
    private String week;
    private List<Details> details;

    public ClassQueryInItem(){

    }

    public ClassQueryInItem(String week, List<Details> details) {
        this.week = week;
        this.details = details;
    }

    public static class Details{
        private String node;
        private String text;

        public Details(){

        }

        public Details(String node, String text) {
            this.node = node;
            this.text = text;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "node='" + node + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }
}

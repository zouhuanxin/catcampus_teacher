package com.jvtc.catcampus_teacher.data.model;

public class RosterInItem {
    private String bj;
    private String hmc;
    private String kc;

    public RosterInItem(){

    }

    public RosterInItem(String bj, String hmc, String kc) {
        this.bj = bj;
        this.hmc = hmc;
        this.kc = kc;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getHmc() {
        return hmc;
    }

    public void setHmc(String hmc) {
        this.hmc = hmc;
    }

    public String getKc() {
        return kc;
    }

    public void setKc(String kc) {
        this.kc = kc;
    }
}

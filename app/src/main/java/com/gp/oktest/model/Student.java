package com.gp.oktest.model;

/**
 * 学生对象
 *
 * Created by michael on 2017/9/21.
 */

public class Student {
    private String sNo;
    private String sName;
    private String sSex;
    private String sBirthday;
    private String cLass;

    public String getsNo() {
        return sNo;
    }

    public String getsName() {
        return sName;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public void setsBirthday(String sBirthday) {
        this.sBirthday = sBirthday;
    }

    public void setcLass(String cLass) {
        this.cLass = cLass;
    }

    public String getsBirthday() {
        return sBirthday;

    }

    public String getcLass() {
        return cLass;
    }
}

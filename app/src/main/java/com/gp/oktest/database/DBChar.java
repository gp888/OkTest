package com.gp.oktest.database;

import android.provider.BaseColumns;

/**
 * Created by guoping on 2018/1/2.
 */

public class DBChar {

    /**
     * 学生表对应的表结构
     *
     * 继承自BaseColumns，通过源码可以看到它自带有_ID和_COUNT两个字段
     */
    public interface StudentColumns extends BaseColumns {
        String sNo = "sNo";
        String sName = "sName";
        String sSex = "sSex";
        String sBirthday = "sBirthday";
        String sClass = "sClass";
    }

    public interface CourseColumns extends BaseColumns {
        String cNo = "cNo";
        String cName = "cName";
        String tNo = "tNo";
    }

    public interface TeacherColumns extends BaseColumns {
        String tNo = "tNo";
        String tName = "tName";
        String tSex = "tSex";
        String tBirthday = "tBirthday";
        String prof = "prof";
        String depart = "depart";
    }

    public interface ScoreColumns extends BaseColumns {
        String sNo = "sNo";
        String cNo = "cNo";
        String degree = "degree";
    }

}

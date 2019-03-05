package com.gp.oktest.database;

/**
 * 对Student表的操作进行包装
 *
 * Created by michael on 2017/9/20.
 */

public class CourseContact {
    /**
     * 表名
     * */
    public static final String TABLE_NAME = "course";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            DBChar.CourseColumns.cNo,
            DBChar.CourseColumns.tNo,
            DBChar.CourseColumns.cName,
    };

    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + DBChar.CourseColumns.cNo + " TINYINT UNSIGNED  NOT NULL,"
            + DBChar.CourseColumns.cName + " VARCHAR(20) NOT NULL,"
            + DBChar.CourseColumns.tNo + " VARCHAR(20) NULL,"
            + "PRIMARY KEY(cNo)"
            + ")";
}

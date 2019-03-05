package com.gp.oktest.database;

/**
 * 对Student表的操作进行包装
 *
 * Created by michael on 2017/9/20.
 */

public class ScoreContact {
    /**
     * 表名
     * */
    public static final String TABLE_NAME = "score";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            DBChar.ScoreColumns.sNo,
            DBChar.ScoreColumns.cNo,
            DBChar.ScoreColumns.degree,
    };

    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + DBChar.ScoreColumns.sNo + " TINYINT UNSIGNED  NOT NULL,"
            + DBChar.ScoreColumns.cNo + " TINYINT UNSIGNED NOT NULL,"
            + DBChar.ScoreColumns.degree + " DECIMAL(4, 1)"
            + ")";
}

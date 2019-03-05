package com.gp.oktest.database;

/**
 * Created by guoping on 2018/1/2.
 *
 * UNSIGNED将数字类型无符号化
 * 类型               大小      有符号范围                                   无符号范围   用途
 * TINYINT           1 字节    (-128，127)                                    (0，255) 小整数值
 *
 * SMALLINT        2 字节    (-32 768，32 767)                           (0，65 535) 大整数值
 *
 * MEDIUMINT     3 字节    (-8 388 608，8 388 607)                  (0，16 777 215) 大整数值
 *
 * INT或INTEGER  4 字节    (-2 147 483 648，2 147 483 647)     (0，4 294 967 295) 大整数值
 */

public class TeacherContact {
    /**
     * 表名
     * */
    public static final String TABLE_NAME = "teacher";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            DBChar.TeacherColumns.tNo,
            DBChar.TeacherColumns.tName,
            DBChar.TeacherColumns.tSex,
            DBChar.TeacherColumns.tBirthday,
            DBChar.TeacherColumns.prof,
            DBChar.TeacherColumns.depart,
    };

    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + DBChar.TeacherColumns.tNo + " TINYINT UNSIGNED  NOT NULL,"
            + DBChar.TeacherColumns.tName + " VARCHAR(20) NOT NULL,"
            + DBChar.TeacherColumns.tSex + " DEFAULT 'male',"
            + DBChar.TeacherColumns.tBirthday + " DATE,"
            + DBChar.TeacherColumns.prof + " VARCHAR(20) NULL,"
            + DBChar.TeacherColumns.depart + " VARCHAR(20) NULL,"
            + "PRIMARY KEY(tNo)"
            + ")";
}

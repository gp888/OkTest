package com.gp.oktest.database.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gp.oktest.database.DBChar;
import com.gp.oktest.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 对Student表的操作进行包装
 *
 * Created by michael on 2017/9/20.
 */

public class StudentContact {
    /**
     * 表名
     * */
    public static final String TABLE_NAME = "student";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            DBChar.StudentColumns.sNo,
            DBChar.StudentColumns.sName,
            DBChar.StudentColumns.sSex,
            DBChar.StudentColumns.sBirthday,
            DBChar.StudentColumns.sClass
    };

    /**
     * 创建表的语句
     * DATE 格式是‘YYYY-MM-DD’, 别的格式的东西会被忽略
     * */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + DBChar.StudentColumns.sNo + " TINYINT UNSIGNED  NOT NULL,"
            + DBChar.StudentColumns.sName + " VARCHAR(20) NOT NULL,"
            + DBChar.StudentColumns.sSex + " DEFAULT ('male'),"
            + DBChar.StudentColumns.sBirthday + " DATE,"
            + DBChar.StudentColumns.sClass + " VARCHAR(20) NOT NULL,"
            + "PRIMARY KEY(sno)"
            + ")";


    /**
     * 删除表的语句
     * */
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * 判断下所请求的字段是否都存在，防止出现操作的字段不存在的错误
     */
    private static void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(AVAILABLE_PROJECTION));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(TAG + "checkColumns()-> Unknown columns in projection");
            }
        }
    }

    /**
     * 记录是否存在
     * */
    public static boolean isExist(SQLiteOpenHelper helper, String studentId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, DBChar.StudentColumns.sNo + " =? ", new String[] {studentId}, null, null, null);
        if (cursor.moveToFirst()) {
            Log.d(TAG, "缓存存在");
            cursor.close();
            return true;
        } else {
            Log.d(TAG, "缓存不存在");
            cursor.close();
            return false;
        }
    }

    /**
     * 查询所有的学生
     * */
    public static List<Student> query(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, null, null, null, null, null, null);
        List<Student> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            students.add(getStudentFromCursor(cursor));
        }
        cursor.close();
        return students;
    }

    /**
     * 查询某个学生
     */
    public static Student query(SQLiteOpenHelper helper, String studentId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, DBChar.StudentColumns.sNo + " =? ", new String[] {studentId}, null, null, null, null);
        Student student = null;
        if (cursor != null) {
            cursor.moveToFirst();
            student = getStudentFromCursor(cursor);
        }
        return student;
    }

    /**
     * 更新学生对象
     * */
    public static void update(SQLiteOpenHelper helper, Student student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME, toContentValues(student), DBChar.StudentColumns.sNo + " =? ", new String[] {student.getsNo()});
    }

    /**
     * 插入新数据
     * */
    public static void insert(SQLiteOpenHelper helper, Student student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME, null, toContentValues(student));
    }

    /**
     * 插入新数据，如果已经存在就替换
     * */
    public static void insertOrReplace(SQLiteOpenHelper helper, Student student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insertWithOnConflict(TABLE_NAME, null, toContentValues(student), SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * 删除某条记录
     * */
    public static void delete(SQLiteOpenHelper helper, String studentId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, DBChar.StudentColumns.sNo + " =? ", new String[] {studentId});
    }

    /**
     * 清空学生表
     */
    public static void clear(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    /**
     * 将对象包装成ContentValues
     * */
    private static ContentValues toContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBChar.StudentColumns.sNo, student.getsNo());
        contentValues.put(DBChar.StudentColumns.sName, student.getsName());
        contentValues.put(DBChar.StudentColumns.sSex, student.getsSex());
        contentValues.put(DBChar.StudentColumns.sBirthday, student.getsBirthday());
        contentValues.put(DBChar.StudentColumns.sClass, student.getcLass());
        return contentValues;
    }

    /**
     * 将学生对象从Cursor中取出
     * */
    private static Student getStudentFromCursor(Cursor cursor) {
        Student student = new Student();
        student.setsNo(cursor.getString(cursor.getColumnIndex(DBChar.StudentColumns.sNo)));
        student.setsName(cursor.getString(cursor.getColumnIndex(DBChar.StudentColumns.sName)));
        student.setsSex(cursor.getString(cursor.getColumnIndex(DBChar.StudentColumns.sSex)));
        student.setsBirthday(cursor.getString(cursor.getColumnIndex(DBChar.StudentColumns.sBirthday)));
        student.setcLass(cursor.getString(cursor.getColumnIndex(DBChar.StudentColumns.sClass)));
        return student;
    }
}

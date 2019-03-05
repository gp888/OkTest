package com.gp.oktest.database;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gp.oktest.R;
import com.gp.oktest.adapter.StudentAdapter;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SqliteActivity extends BaseActivity {
    /**
     * ListView对应的数据源适配器
     * */
    private StudentAdapter studentAdapter;
    private InsertTask insertTask;
    private RefreshTask refreshTask;
    private DeleteTask deleteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        iniComponent();
        iniData();
    }

    private void iniComponent() {
        ListView lvList = (ListView) findViewById(R.id.lvList);
        studentAdapter = new StudentAdapter(this, new ArrayList<Student>());
        lvList.setAdapter(studentAdapter);
        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //长按显示一个确认删除的对话框
                showDeleteConfirmDialog(studentAdapter.getItem(position).getsNo());
                return true;
            }
        });
    }

    private void iniData() {
        //注意：可以直接在UI线程中操作数据库，但是不建议这样做
        DBManager.getInstance(this).getStudentManager().clear();

        Random random = new Random();
        //批量插入一些初始数据
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setsNo(String.valueOf(10 + i));
            student.setsSex(i % 2 == 0? "male" : "female");
            student.setsName("Michael" + i);
            student.setcLass(random.nextInt(3) + "");
            students.add(student);
        }
        insert(students);
    }

    private void showDeleteConfirmDialog(final String studentId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("删除该记录？");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(studentId);
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private class InsertTask extends AsyncTask<Void, Void, Void> {
        private List<Student> students;

        InsertTask(List<Student> students)
        {
            this.students = students;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (Student student : students) {
                DBManager.getInstance(SqliteActivity.this).getStudentManager().insert(student);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refresh();
        }
    }

    private class RefreshTask extends AsyncTask<Void, Void, List<Student>> {
        @Override
        protected List<Student> doInBackground(Void... params) {
            return DBManager.getInstance(SqliteActivity.this).getStudentManager().query();
        }

        @Override
        protected void onPostExecute(List<Student> students) {
            super.onPostExecute(students);
            if (students != null) {
                studentAdapter.setAll(students);
            }
        }
    }

    private class DeleteTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... studentIds) {
            DBManager.getInstance(SqliteActivity.this).getStudentManager().delete(studentIds[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refresh();
        }
    }

    /**
     * 删除数据
     */
    private void delete(String studentId) {
        cancelInsertTaskIfRunning();
        deleteTask = new DeleteTask();
        deleteTask.execute(studentId);
    }

    /**
     * 插入数据
     */
    private void insert(List<Student> students) {
        cancelInsertTaskIfRunning();
        insertTask = new InsertTask(students);
        insertTask.execute();
    }

    /**
     * 从数据库中重新获取一遍数据
     */
    private void refresh() {
        cancelRefreshTaskIfRunning();
        refreshTask = new RefreshTask();
        refreshTask.execute();
    }

    private void cancelInsertTaskIfRunning() {
        if (insertTask != null) {
            insertTask.cancel(true);
        }
    }

    private void cancelRefreshTaskIfRunning() {
        if (refreshTask != null) {
            refreshTask.cancel(true);
        }
    }

    private void cancelDeleteTaskIfRunning() {
        if (deleteTask != null) {
            deleteTask.cancel(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelRefreshTaskIfRunning();
        cancelInsertTaskIfRunning();
        cancelDeleteTaskIfRunning();
    }
}

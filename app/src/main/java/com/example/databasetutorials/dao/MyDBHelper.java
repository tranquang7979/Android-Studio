package com.example.databasetutorials.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DBNAME="Mydb.db";
    private static final int DBVERSION=1;

    public MyDBHelper(Context context){
        super(context,DBNAME, null,DBVERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao moi bang tbl_department
        db.execSQL("create table tbl_department(" +
                    "id integer primary key autoincrement, name text, description text)"
                    );
        //insert du lieu mau
        db.execSQL("insert into tbl_department(name,description) values('IT','IT departmemt')");
        db.execSQL("insert into tbl_department(name,description) values('Sales','Sales departmemt')");
        //tao moi bang tbl_account
        db.execSQL("create table tbl_account(" +
                "id integer primary key autoincrement, username text, password text, role text)"
        );

        db.execSQL("insert into tbl_account(username,password,role) values('admin','admin','Administrator')");
        db.execSQL("insert into tbl_account(username,password,role) values('customer01','1','user')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("Drop table if exists tbl_account");
        onCreate(db);
    }
}

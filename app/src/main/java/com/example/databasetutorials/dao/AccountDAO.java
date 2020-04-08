package com.example.databasetutorials.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AccountDAO  {

    private Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    public AccountDAO(Context context){

        this.context = context;
        dbHelper = new MyDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        Cursor c = db.rawQuery("select * from tbl_account",null);
        if(c.moveToFirst()){
            while(c.isAfterLast()==false){
                int id=c.getInt(0);
                String username = c.getString(1);
                String password = c.getString(2);
                String role = c.getString(3);
                //tuong ung 1 hang co trong bang tbl_account
                Account acc =new  Account(id, username,password,role);
                accounts.add(acc);
                c.moveToNext();
            }
        }
        c.close();
        return accounts;
    }

    public  boolean createAccount(String userName, String password, String role ){
        try{
            ContentValues values =new ContentValues();
            values.put("username",userName );
            values.put("password",password);
            values.put("role",role);
            db.insert("tbl_account",null,values);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkExistingUsername(String username){
        //sql =select id, username where username = <username>
      Cursor cursor=   db.query("tbl_account",
                 new String[] {"id", "username"},
                "username=?",
                 new String[]{username},
                null,
                null,
                null);
        if(cursor.moveToFirst()){

            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteAccount(String id){
          try{
                db.delete("tbl_account", "id = ?", new String[]{id});
               return true;
          }catch (Exception ex){
          }
          return false;
    }

}

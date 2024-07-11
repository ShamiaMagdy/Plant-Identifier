package com.example.plantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "plantApp6.db" ;
    public static final int DATABASE_VERSION = 1 ;
    SQLiteDatabase sqLiteDatabase;

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table register (id integer primary key autoincrement , username text not null," +
                "email text not null , password text not null , userphoto blob not null)");
        db.execSQL("create table history (Hid integer primary key autoincrement,userID integer, P_image Blob, P_name text, favourite integer, foreign key(userID) references register (ID))");
        //db.execSQL("create table plants (plantPhoto blob not null,plantName text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists register");
        db.execSQL("drop table if exists history");
        onCreate(db);
    }

    /*public void insertregisterdata(byte[]images)
    {
        /*ContentValues row = new ContentValues();
        row.put("username",username);
        row.put("email",email);
        row.put("password",password);
       row.put("userphoto",images);
        sqLiteDatabase=getWritableDatabase();
       // Long affectedrows= sqLiteDatabase.insert("register",null,row);
        sqLiteDatabase.insert("register",null,row);
        sqLiteDatabase.close();
        //return affectedrows;*/

        /*sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("userID",1);
        contentValues.put("P_name",images);
        contentValues.put("P_image","Acer plamatum");
        contentValues.put("favourite",0);
        sqLiteDatabase.insert("history",null,contentValues);
        sqLiteDatabase.close();
    }*/

    public void insertData(String username, String email, String password  ,  byte[] image){
        sqLiteDatabase  = getWritableDatabase();
        String sql = "INSERT INTO register VALUES (NULL, ?, ?, ? , ?)";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, username);
        statement.bindString(2, email);
        statement.bindString(3, password);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }

    public Bitmap getData(Integer Id){
      /*  sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);*/
      sqLiteDatabase=getReadableDatabase();
      Bitmap bt=null;
      Cursor cursor=sqLiteDatabase.rawQuery("select * from register where id = ? ",new String[]{String.valueOf(Id)});
      if(cursor.moveToNext())
      {
          byte[]img=cursor.getBlob(4);
          bt= BitmapFactory.decodeByteArray(img,0,img.length);
      }
      return bt;

    }

    public Cursor checkuser(String username)
    {
        sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query("register", null, " username=?", new String[]{username}, null, null, null);
        return cursor;
    }

    public Cursor login(String name,String pass)
    {
        sqLiteDatabase=getReadableDatabase();
        String[] arr={name,pass};
        Cursor cursor =sqLiteDatabase.rawQuery("select * from register where username = ? AND password = ?",arr);
        return cursor;


    }

    public int getid(String name , String pass ){
      /*  sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);*/
        sqLiteDatabase=getReadableDatabase();
      //  Bitmap bt=null;
        int result=0;
        String[] arr={name,pass};
        Cursor cursor=sqLiteDatabase.rawQuery("select * from register where username = ? AND password = ?",arr);
        if(cursor.moveToNext())
        {
           /* byte[]img=cursor.getBlob(4);
            bt= BitmapFactory.decodeByteArray(img,0,img.length);*/
           result=cursor.getInt(0);
        }
        return result;

    }

    public void forget_password(String username , String newpassword)
    {
        sqLiteDatabase=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("password",newpassword);
        sqLiteDatabase.update("register" , row ,"username =?" , new String[]{username} );
        sqLiteDatabase.close();
    }
    //Convert Bitmap to byte array
    public  byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void insert_History(int userID,String pName,byte[] img)
    {
        sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("userID",userID);
        contentValues.put("P_image",img);
        contentValues.put("P_name",pName);
        contentValues.put("favourite",0);
        sqLiteDatabase.insert("history",null,contentValues);
        sqLiteDatabase.close();
    }

    public String selectUserName(int ID)
    {
        sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select username from register where userID="+ID+"",null);
        if (cursor!=null)
        {
            return cursor.getString(0);
        }
        return cursor.getString(0);
    }
    public Cursor getData1(Integer Id){
        sqLiteDatabase=getReadableDatabase();
        Bitmap bt=null;
        Cursor cursor=sqLiteDatabase.rawQuery("select * from register where id = ? ",new String[]{String.valueOf(Id)});
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor viewHistory(Integer Id){
        sqLiteDatabase=getReadableDatabase();
        Bitmap bt=null;
        Cursor cursor=sqLiteDatabase.rawQuery("select * from history where userID = ?",new String[]{String.valueOf(Id)});
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }
    public void deleteHistory()
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete("history",null,null);
        sqLiteDatabase.close();
    }
    public void deleteFromHistory(int Id,int img)
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete("history", "Hid='"+img+"'"+"and userID= '"+Id+"'",null);
        sqLiteDatabase.close();
    }
    public void updateFavorite(int Id,int img)
    {
        sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("favourite",1);
        sqLiteDatabase.update("history",contentValues,"Hid='"+img+"'"+"and userID= '"+Id+"'",null);
        sqLiteDatabase.close();
    }
    public Cursor checkFavorite(int Id)
    {
        sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from history where userID = ?"+"and favourite= 1 ",new String[]{String.valueOf(Id)});
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }
    public void deleteFromFavorite(int Id,int img)
    {
        sqLiteDatabase=getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("favourite",0);
        sqLiteDatabase.update("history",contentValues ,"Hid='"+img+"'"+"and userID= '"+Id+"'",null);
        sqLiteDatabase.close();
    }



}

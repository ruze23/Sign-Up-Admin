package com.neva.signup;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "accounts.db";
    public static final String table_name = "user";
    public static final String AccountId = "accID";
    public static final String UserName = "username";
    public static final String Password = "password";
    public static final String FirstName = "firstname";
    public static final String LastName = "lastname";
    public static final String Address = "address";
    public static final String Email = "email";
    public static final String Sex = "sex";
    public static final String ContactNo = "contact";
    public static final String User_type = "userType";

    ContentValues values;
    Cursor rs;
    ArrayList<String> Items;
    ArrayList<Integer> ItemsId;
    public SQLiteDB( Context context){ super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase conn) {
        conn.execSQL("CREATE TABLE " + table_name + "(" + AccountId + " Integer PRIMARY KEY AUTOINCREMENT, " + UserName + " TEXT, " + Password + " TEXT, " + FirstName + " TEXT, " + LastName + " TEXT, " + Address + " TEXT, " + Email + " TEXT, "
                    + Sex + " TEXT, " + ContactNo + " TEXT, " + User_type + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase conn, int oldVersion, int newVersion) {
        conn.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(conn);
    }

    public boolean AddRecord(String UName, String pass, String fName, String lName, String address, String email, String contact, String sex){
        SQLiteDatabase conn = this.getWritableDatabase();
        values = new ContentValues();

        values.put(UserName,UName);
        values.put(Password,pass);
        values.put(FirstName,fName);
        values.put(LastName,lName);
        values.put(Address,address);
        values.put(Email,email);
        values.put(Sex,sex);
        values.put(ContactNo,contact);
        values.put(User_type, "1");
        try{
            conn.insert(table_name, null, values);
            return true;
        }catch(SQLException error){
            Log.e("SQL add record" , "Error" , error);
            return false;
        }
    }
    public boolean RecordExists(String UName){

        SQLiteDatabase conn = this.getReadableDatabase();

        String[] Columns = {UserName};
        String selection = UserName + " = ?";
        String[] selectionArgs = {UName};

        rs = conn.query(table_name, Columns, selection,selectionArgs, null,null,null);
        return rs.moveToFirst();
    }

    public boolean RecordExist(String UName, int ignoreIndex){

        SQLiteDatabase conn = this.getReadableDatabase();

        String[] Columns = {AccountId,UserName};
        String selection = AccountId + " = ? AND " + UserName + " = ?";
        String[] selectionArgs = {String.valueOf(ignoreIndex), UName};

        rs = conn.query(table_name, Columns, selection,selectionArgs, null,null,null);
        return rs.moveToFirst();
    }

    public boolean UpdRecord(int id, String UName, String pass, String fName, String lName, String address, String email, String contact, String sex){
        SQLiteDatabase conn = this.getWritableDatabase();

        values = new ContentValues();
        values.put(UserName,UName);
        values.put(Password,pass);
        values.put(FirstName,fName);
        values.put(LastName,lName);
        values.put(Address,address);
        values.put(Email,email);
        values.put(Sex,sex);
        values.put(ContactNo,contact);

        String selection = AccountId + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rows = conn.update(table_name,values,selection,selectionArgs);

        conn.close();
        return rows > 0;
    }

    public boolean deleteAcc(int index){
        SQLiteDatabase conn = this.getWritableDatabase();
        String selection = AccountId + " = ?";
        String[] selectionArgs = {String.valueOf(index)};

        int rowsDeleted = conn.delete(table_name, selection, selectionArgs);
        conn.close();

        return rowsDeleted > 0;
    }

    public boolean CheckAdmin(String Uname, String pass){
        SQLiteDatabase conn = this.getReadableDatabase();

        String[] Columns = {UserName, Password,User_type};
        String selection = UserName + " = ? AND " + Password + " = ? AND " + User_type + " = 0";
        String[] selectionArgs = {Uname, pass};

        rs = conn.query(table_name, Columns, selection, selectionArgs, null,null,null);
        return rs.moveToFirst();
    }

    public boolean CheckUser(String Uname, String pass){
        SQLiteDatabase conn = this.getReadableDatabase();

        String[] Columns = {UserName, Password,User_type};
        String selection = UserName + " = ? AND " + Password + " = ? AND " + User_type + " = 2";
        String[] selectionArgs = {Uname, pass};

        rs = conn.query(table_name, Columns,selection, selectionArgs,null,null,null);
        return rs.moveToFirst();
    }

    @SuppressLint("Range")
    public ArrayList<String> GetRecords(){
        SQLiteDatabase conn = this.getReadableDatabase();

        Items = new ArrayList<>();
        ItemsId = new ArrayList<>();

        String condition = "userType = 1";
        rs = conn.rawQuery("SELECT * FROM " + table_name + " WHERE " + condition, null );
        rs.moveToFirst();

        while(!rs.isAfterLast()){

            ItemsId.add(rs.getInt(rs.getColumnIndex(AccountId)));
                Items.add(rs.getString(rs.getColumnIndex(AccountId))+ " " +
                        rs.getString(rs.getColumnIndex(UserName))+ " " +
                        rs.getString(rs.getColumnIndex(FirstName)) + " " +
                        rs.getString(rs.getColumnIndex(LastName)) + " " +
                        rs.getString(rs.getColumnIndex(Address)) + " " +
                        rs.getString(rs.getColumnIndex(Email)) + " " +
                        rs.getString(rs.getColumnIndex(ContactNo)) + " " +
                        rs.getString(rs.getColumnIndex(Sex)) + " " +
                        rs.getString(rs.getColumnIndex(User_type)));
                rs.moveToNext();
        }
        rs.close();
        return Items;
    }

    @SuppressLint("Range")
    public String[] GetRecord(String Uname, String pass){
        SQLiteDatabase conn = this.getReadableDatabase();
        String[] accountData = null;

        String[] columns = {AccountId,UserName, Password, FirstName, LastName,Address,Email,Sex,ContactNo};
        String selection = UserName + " = ? AND " + Password + " = ?";
        String[] selectionArgs = {Uname,pass};

        rs = conn.query(table_name, columns,selection,selectionArgs,null,null,null);
        if(rs.moveToFirst()){
            accountData = new String[]
                    {rs.getString(rs.getColumnIndex(AccountId)),rs.getString(rs.getColumnIndex(UserName)),rs.getString(rs.getColumnIndex(Password)),rs.getString(rs.getColumnIndex(FirstName)), rs.getString(rs.getColumnIndex(LastName))
                    ,rs.getString(rs.getColumnIndex(Address)),rs.getString(rs.getColumnIndex(Email)),rs.getString(rs.getColumnIndex(Sex))
                    ,rs.getString(rs.getColumnIndex(ContactNo))};
        }
        rs.close();
        conn.close();

        return accountData;
    }
    @SuppressLint("Range")
    public String[] AdminLog(int index){
        SQLiteDatabase conn = this.getReadableDatabase();
        String[] accountData = null;

        String[] columns = {UserName, FirstName, LastName};
        String selection = AccountId + " = ?";
        String[] selectionArgs = {String.valueOf(index)};

        rs = conn.query(table_name, columns,selection,selectionArgs,null,null,null);
        if(rs.moveToFirst()){
            accountData = new String[]
                    {rs.getString(rs.getColumnIndex(UserName)),rs.getString(rs.getColumnIndex(FirstName)), rs.getString(rs.getColumnIndex(LastName))};
        }
        rs.close();
        conn.close();

        return accountData;
    }

    public boolean UpdateUser(int index){
        SQLiteDatabase conn = this.getWritableDatabase();

        values = new ContentValues();

        values.put(User_type, "2");
        String selection = AccountId + " = ?";

        String[] selectionArgs = {String.valueOf(index)};

        int rows = conn.update(table_name,values,selection,selectionArgs);

        conn.close();
        return rows > 0;
    }
}

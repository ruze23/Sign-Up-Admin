package com.neva.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminLog extends ListActivity {

    SQLiteDB conn;
    ArrayList<String> ItemList;

    void refreshData(){
        conn = new SQLiteDB(this);

        ItemList = conn.GetRecords();

        if(ItemList.size() > 0 ){
            setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ItemList));
        }else{
            Toast.makeText(this, "No accounts exist", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshData();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);

        String[] accountData = conn.AdminLog(conn.ItemsId.get(position));

        Intent adminPage = new Intent(getApplicationContext(),AdminAccess.class);

        adminPage.putExtra(SQLiteDB.AccountId, String.valueOf(conn.ItemsId.get(position)));
        Log.e("ONlick", String.valueOf(conn.ItemsId.get(position)));
        adminPage.putExtra(SQLiteDB.UserName, accountData[0]);
        adminPage.putExtra(SQLiteDB.FirstName, accountData[1]);
        adminPage.putExtra(SQLiteDB.LastName,accountData[2]);

        startActivity(adminPage);
    }
}
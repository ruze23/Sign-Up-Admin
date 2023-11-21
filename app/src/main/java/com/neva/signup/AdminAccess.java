package com.neva.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminAccess extends AppCompatActivity {
    EditText UserName, FirstName, LastName;

    Button Accept, Reject;
    SQLiteDB conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_access);

        conn = new SQLiteDB(this);

        UserName = (EditText) findViewById(R.id.txtUserName);
        FirstName = (EditText) findViewById(R.id.txtFirstName);
        LastName = (EditText) findViewById(R.id.txtLastName);

        Accept = (Button) findViewById(R.id.btnAccept);
        Reject = (Button) findViewById(R.id.btnReject);

        Intent intent = getIntent();
        String id = intent.getStringExtra(SQLiteDB.AccountId);
        String userName = intent.getStringExtra(SQLiteDB.UserName);
        String fname = intent.getStringExtra(SQLiteDB.FirstName);
        String lname = intent.getStringExtra(SQLiteDB.LastName);

        UserName.setText(userName);
        FirstName.setText(fname);
        LastName.setText(lname);

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(conn.UpdateUser(Integer.valueOf(id))){
                        Log.e("Update User", "Success");
                        Toast.makeText(getApplicationContext(),"User Granted Access",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e("Update User", "Error", e);
                }
            }
         });

        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(conn.deleteAcc(Integer.valueOf(id))){
                        Log.e("Delete User", "Success");
                        Toast.makeText(getApplicationContext(),"User deleted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e("Update User", "Error", e);
                }
            }
        });
    }
}
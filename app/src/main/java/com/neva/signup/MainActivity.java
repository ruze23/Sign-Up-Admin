package com.neva.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     EditText UserName, Password;

     Button Login, Signup;
     SQLiteDB conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new SQLiteDB(this);

        UserName = (EditText) findViewById(R.id.txtUserName);
        Password = (EditText) findViewById(R.id.txtPassword);

        Login = (Button) findViewById(R.id.btnLogin);
        Signup = (Button) findViewById(R.id.btnSignUp);

        EditText[] ETarr = {UserName,Password};


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(EditText editText : ETarr){
                    if(editText.getText().toString().equals("")){
                        editText.setError("Field must not be empty!");
                        editText.requestFocus();
                        Log.e("Login", "Empty Field");
                        return;
                    }
                }
                String uName = UserName.getText().toString();
                String pass = Password.getText().toString();

                try{
                    if(conn.CheckAdmin(uName,pass)){
                        Log.e("Login", "Login Successful");
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,AdminLog.class);
                        startActivity(intent);
                    }else if(conn.CheckUser(uName,pass)){
                        Log.e("Login", "Login Successful");

                        try{
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,guestProfile.class);

                            String[] records = conn.GetRecord(uName,pass);
                            intent.putExtra(SQLiteDB.AccountId, records[0]);
                            intent.putExtra(SQLiteDB.UserName, records[1]);
                            intent.putExtra(SQLiteDB.Password, records[2]);
                            intent.putExtra(SQLiteDB.FirstName, records[3]);
                            intent.putExtra(SQLiteDB.LastName, records[4]);
                            intent.putExtra(SQLiteDB.Address, records[5]);
                            intent.putExtra(SQLiteDB.Email, records[6]);
                            intent.putExtra(SQLiteDB.Sex, records[7]);
                            intent.putExtra(SQLiteDB.ContactNo, records[8]);

                            startActivity(intent);
                        }catch(Exception e){
                            Log.e("Login, Get data", "Error", e);
                        }

                    }else{
                        Log.e("Login", "Account does not exist");
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e("Login", "Error", e);
                }
            }
        });
    }
}
package com.neva.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUp extends AppCompatActivity {
    EditText UserName, Password, FirstName,LastName,Address,Email,Contact;
    SQLiteDB conn = new SQLiteDB(this);
    Spinner Sex;
    Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UserName = (EditText) findViewById(R.id.txtUser);
        Password = (EditText) findViewById(R.id.txtPass);
        FirstName = (EditText) findViewById(R.id.firstname);
        LastName = (EditText) findViewById(R.id.lastname);
        Address = (EditText) findViewById(R.id.address);
        Email = (EditText) findViewById(R.id.email);
        Contact = (EditText) findViewById(R.id.contact);
        Sex = (Spinner) findViewById(R.id.sex);

        EditText[] ETarr = {UserName,Password,FirstName,LastName,Address,Email,Contact};

        Register = (Button) findViewById(R.id.btnRegister);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(EditText editText : ETarr){
                        if(editText.getText().toString().equals("")){
                            editText.setError("Field must not be empty!");
                            editText.requestFocus();
                            return;
                        }
                }
                Log.i("Fields" , "All fields filled");

                String uname = UserName.getText().toString();
                String pass = Password.getText().toString();
                String fname = FirstName.getText().toString();
                String lname = LastName.getText().toString();
                String address = Address.getText().toString();
                String email = Email.getText().toString();
                String sex = Sex.getSelectedItem().toString();
                String contact = Contact.getText().toString();
                
                try{
                    if(conn.RecordExists(uname)){
                        Log.e("record exist", "duplicate Record");
                        Toast.makeText(getApplicationContext(),"Username already exists",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch(Exception e){
                    Log.e("record exist", "Error", e);
                }
                try {
                    if(conn.AddRecord(uname,pass,fname,lname,address,email,contact,sex)){

                        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_SHORT).show();

                        for(EditText editText : ETarr){
                            editText.setText("");
                        }
                        Log.e("Add record", "Success");
                    }else {
                        Toast.makeText(getApplicationContext(), "Record not saved!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e("Add record", "error",e);
                }
            }
        });
    }
}
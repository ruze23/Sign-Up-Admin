package com.neva.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PermissionInfoCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class guestProfile extends AppCompatActivity {
    EditText UserName, Password, FirstName,LastName,Address,Email,Contact;
    SQLiteDB conn = new SQLiteDB(this);
    Spinner Sex;
    Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);

        conn = new SQLiteDB(this);

        UserName = (EditText) findViewById(R.id.txtUser);
        Password = (EditText) findViewById(R.id.txtPass);
        FirstName = (EditText) findViewById(R.id.firstname);
        LastName = (EditText) findViewById(R.id.lastname);
        Address = (EditText) findViewById(R.id.address);
        Email = (EditText) findViewById(R.id.email);
        Contact = (EditText) findViewById(R.id.contact);
        Sex = (Spinner) findViewById(R.id.sex);

        Update = (Button) findViewById(R.id.btnUpdate);

        Intent Profile = getIntent();

        String id = Profile.getStringExtra(SQLiteDB.AccountId);
        String User = Profile.getStringExtra(SQLiteDB.UserName);
        String Pass = Profile.getStringExtra(SQLiteDB.Password);
        String fName = Profile.getStringExtra(SQLiteDB.FirstName);
        String lName = Profile.getStringExtra(SQLiteDB.LastName);
        String address = Profile.getStringExtra(SQLiteDB.Address);
        String email  = Profile.getStringExtra(SQLiteDB.Email);
        String sex = Profile.getStringExtra(SQLiteDB.Sex);
        String contact = Profile.getStringExtra(SQLiteDB.ContactNo);

        UserName.setText(User);
        UserName.setEnabled(false);
        Password.setText(Pass);
        FirstName.setText(fName);
        LastName.setText(lName);
        Address.setText(address);
        Email.setText(email);
        Contact.setText(contact);

        EditText[] ETarr = {UserName,Password,FirstName,LastName,Address,Email,Contact};

        Integer[] sexArr = {R.array.OptGender};
        if(sex == "MALE"){
            Sex.setSelection(sexArr[0]);
        }else if(sex =="FEMALE"){
            Sex.setSelection(sexArr[1]);
        }

        Update.setOnClickListener(new View.OnClickListener() {
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
                    if(conn.RecordExist(uname, Integer.valueOf(id))){
                        Log.e("record exist", "duplicate Record");
                    }
                }catch(Exception e){
                    Log.e("record exist", "Error", e);
                }

                try {
                    if(conn.UpdRecord(Integer.valueOf(id),uname,pass,fname,lname,address,email,contact,sex)){

                        Toast.makeText(getApplicationContext(),"Account Updated",Toast.LENGTH_SHORT).show();

                        for(EditText editText : ETarr){
                            editText.setText("");
                        }
                        Log.e("Add record", "Success");
                    }else {
                        Toast.makeText(getApplicationContext(), "Record not saved!", Toast.LENGTH_SHORT).show();
                        Log.e("Add record", "FAIl");
                    }
                }catch(Exception e){
                    Log.e("Add record", "error",e);
                }
            }
        });
    }
}
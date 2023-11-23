package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText name_field, email_field, pass_field, class_field, rollno_field, phone_field;

    Button signupBtn;

    RadioGroup radioGroup;

    RadioButton male,female;

    String name, email, pass, cla_ss, rollNo, phone, gender = "";

    //Firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Widget initialization
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.female:
                gender = "female";
                break;

            case R.id.male:
                gender = "male";
                break;

            case R.id.signUpBtn:

                name = name_field.getText().toString();
                email = email_field.getText().toString();
                pass = pass_field.getText().toString();
                cla_ss = class_field.getText().toString();
                rollNo = rollno_field.getText().toString();
                phone = phone_field.getText().toString();

                if(gender.isEmpty())
                {
                    Toast.makeText(this,"enter gender plz",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user = new User(name,email,pass,cla_ss,rollNo,phone,gender);
                    if(isValid(user))
                    {
                        creatAccount(user);
                    }
                    else
                    {
                        Toast.makeText(this, "Enter valid data!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
    }

    private boolean isValid(User user)
    {
        boolean valid = true;
        if(user.getName().length()<3)
        {
            name_field.setError("Enter valid name");
            valid = false;
        }
        Toast.makeText(this, ""+ Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches(), Toast.LENGTH_SHORT).show();
        if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches())
        {
            email_field.setError("Enter valid email");
            valid = false;
        }
        if (user.getPassword().length() < 6) {
            pass_field.setError("Enter valid password");
            valid = false;
        }
        if (user.getCla_ss().isEmpty()) {
            class_field.setError("Enter valid class");
            valid = false;
        }
        if (user.getRollNo().isEmpty()) {
            rollno_field.setError("Enter valid rollno");
            valid = false;
        }
        if (user.getPhone().isEmpty()) {
            phone_field.setError("Enter valid phone no");
            valid = false;
        }

        return valid;
    }

    private void creatAccount(User user)
    {
        auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                    user.setId(auth.getUid());
                    addToDb(user);
                } else {
                    Toast.makeText(SignUp.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }

            }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void addToDb(User user)
    {
        databaseReference.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                    name_field.setText("");
                    email_field.setText("");
                    pass_field.setText("");
                    class_field.setText("");
                    rollno_field.setText("");
                    phone_field.setText("");
                    goToLoginScreen();
                } else {
                    Toast.makeText(SignUp.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void goToLoginScreen()
    {
        startActivity(new Intent(SignUp.this,LogIn.class));
        finish();
    }

    private void init()
    {
        name_field = findViewById(R.id.name_field);
        email_field = findViewById(R.id.email_field);
        pass_field = findViewById(R.id.password_field);
        class_field = findViewById(R.id.class_field);
        rollno_field = findViewById(R.id.rollno_field);
        phone_field = findViewById(R.id.phone_field);
        female = findViewById(R.id.female);
        radioGroup = findViewById(R.id.radio_grp);
        female.setOnClickListener(this::onClick);
        male = findViewById(R.id.male);
        male.setOnClickListener(this::onClick);
        signupBtn = findViewById(R.id.signUpBtn);
        signupBtn.setOnClickListener(this::onClick);
        //Firebase initialization
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("students");
    }

}
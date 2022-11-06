package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreatAccount extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    EditText email, password;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        get_Controls();

        firebaseAuth = FirebaseAuth.getInstance();

        set_Events();
    }

    private void set_Events() {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatAccount();
            }
        });
    }

    private void creatAccount() {
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CreatAccount.this, "Successful",
                            Toast.LENGTH_SHORT).show();
                    navigateMain();
                }else{
                    Toast.makeText(CreatAccount.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateMain() {
        finish();
        Intent intent = new Intent(CreatAccount.this, MainActivity.class);
        startActivity(intent);
    }

    private void get_Controls() {
        email = this.<EditText>findViewById(R.id.editTextT_EmailAddress_create);
        password = this.<EditText>findViewById(R.id.editText_Password_create);
        create = this.<Button>findViewById(R.id.button_Create);
    }
}
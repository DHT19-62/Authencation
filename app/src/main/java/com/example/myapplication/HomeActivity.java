package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    TextView name, email;
    Button SignOut;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    boolean isNormal;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        get_Controls();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String Email = bundle.getString("Email", "");
            String Name = bundle.getString("Name", "");
            isNormal = bundle.getBoolean("isNormal", true);

            this.name.setText(Name);
            this.email.setText(Email);
        }

        set_Events();
    }

    private void set_Events() {
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });
    }

    private void signout() {
        if(isNormal){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            gsc = GoogleSignIn.getClient(this, gso);

            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void get_Controls() {
        name = this.<TextView>findViewById(R.id.textView_Name);
        email = this.<TextView>findViewById(R.id.textView_Email);
        SignOut = this.<Button>findViewById(R.id.button_Sigout);
    }
}

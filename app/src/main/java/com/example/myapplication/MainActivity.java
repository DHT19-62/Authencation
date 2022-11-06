package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    boolean isNormal = true;
    String Email;
    String Password;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton button_SiginGG;
    Button button_Sigin;

    EditText email, password;
    TextView createAcount;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){

        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_Controls();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();



        set_Events();
    }

    private void set_Events() {

        button_SiginGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigIn_GG();
            }
        });

        createAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAcc();
            }
        });


        button_Sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign_in();
            }
        });
    }

    private void Sign_in() {
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            navigateToHome(user.getEmail(), user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void CreateAcc() {
        Intent intent = new Intent(MainActivity.this, CreatAccount.class);
        startActivity(intent);
    }

    private void SigIn_GG() {
        Intent Singin_intent = gsc.getSignInIntent();
        startActivityForResult(Singin_intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                navigateToHome(account.getDisplayName(), account.getEmail());
                this.isNormal = false;
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            }

        }
    }

    private void navigateToHome(String name, String email) {
        finish();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("Email", email);
        intent.putExtra("Name", name);
        intent.putExtra("isNormal", isNormal);
        startActivity(intent);
    }

    private void get_Controls() {
        email = this.<EditText>findViewById(R.id.editText_EmailAddress);
        password = this.<EditText>findViewById(R.id.editText_Password);
        button_Sigin = this.<Button>findViewById(R.id.button_Sigin);
        button_SiginGG = this.<ImageButton>findViewById(R.id.button_SiginGG);
        createAcount = this.<TextView>findViewById(R.id.textView_CreateAccount);
    }
}
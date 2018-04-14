package shop.kangondiaravind.com.dingoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout loginemail;
    private TextInputLayout loginPassword;
    private Button loginButton;
    private Toolbar loginToolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loginemail = (TextInputLayout) findViewById(R.id.loginEmail);
        loginPassword = (TextInputLayout) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginProgress = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginemail.getEditText().getText().toString();
                String password = loginPassword.getEditText().getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    loginProgress.setTitle("Loggin in");
                    loginProgress.setMessage("Please wait while we check your credentails");
                    loginProgress.setCanceledOnTouchOutside(true);
                    loginProgress.show();
                    loginUser(email, password);
                } else {
                    loginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Please Enter your correct email and password to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginProgress.dismiss();
                    Intent loginUser = new Intent(LoginActivity.this, MainActivity.class);
                    loginUser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginUser);
                    finish();
                } else {
                    loginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Please Enter correct details to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package shop.kangondiaravind.com.dingoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mDisplayname;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button msignup;
    private FirebaseAuth mAuth;
    private Toolbar toolbarRegister;
    private ProgressDialog regProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDisplayname=(TextInputLayout) findViewById(R.id.displayName);
        mEmail=(TextInputLayout)findViewById(R.id.email);
        mPassword=(TextInputLayout)findViewById(R.id.password);
        msignup=(Button)findViewById(R.id.register);
        toolbarRegister=(Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbarRegister);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        regProgress = new ProgressDialog(this);

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = mDisplayname.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(displayName)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    regProgress.setTitle("Registering user");
                    regProgress.setMessage("Please wait we create your account");
                    regProgress.setCanceledOnTouchOutside(false);
                    regProgress.show();
                    registerUser(displayName,email,password);
                }else {
                    Toast.makeText(RegisterActivity.this, "Enter All details to Create Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String displayName, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    regProgress.dismiss();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    regProgress.hide();
                    Toast.makeText(RegisterActivity.this, "You got some Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

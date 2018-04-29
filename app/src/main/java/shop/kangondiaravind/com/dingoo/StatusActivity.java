package shop.kangondiaravind.com.dingoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    DatabaseReference databaseReference;
    EditText statushow;
    Button statusSubmit;
    ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        toolbar = (Toolbar) findViewById(R.id.status_appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dingoo chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        String statusvalue = getIntent().getStringExtra("statusvalue");
        statushow = (EditText) findViewById(R.id.et_status);
        statusSubmit = (Button) findViewById(R.id.btnStatusSubmit);
        statushow.setText(statusvalue);

        //To fetch current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //To fetch current uid
        String uid = currentUser.getUid();
        //Refrencing to database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        statusSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Saving changes");
                progressDialog.setMessage("please wait while we save the changes");
                progressDialog.show();
                String status = statushow.getText().toString();
                databaseReference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(StatusActivity.this, SettingsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(StatusActivity.this, "Their was an error ehile saving your changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}

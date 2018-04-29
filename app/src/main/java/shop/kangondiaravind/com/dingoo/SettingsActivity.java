package shop.kangondiaravind.com.dingoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    TextView dpname;
    TextView ustatus;
    Button changestatus;
    Button changeImage;
    CircleImageView dp;
    private FirebaseUser currentuser;
    private FirebaseDatabase database;
    private DatabaseReference retriveUserdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dp = (CircleImageView) findViewById(R.id.circleImageView);
        dpname = (TextView) findViewById(R.id.tv_dp_name);
        ustatus = (TextView) findViewById(R.id.tv_status);
        changeImage = (Button) findViewById(R.id.btn_change_img);
        changestatus = (Button) findViewById(R.id.btn_change_status);

        //Read from database
        // Fetching the current user details from db
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = currentuser.getUid();
        retriveUserdetails = database.getInstance().getReference().child("Users").child(currentId);
        retriveUserdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                //   String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                // String thumbimage = dataSnapshot.child("thumb_image").getValue().toString();
                dpname.setText(name);
                ustatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = ustatus.getText().toString();
                Intent intent = new Intent(SettingsActivity.this, StatusActivity.class);
                intent.putExtra("statusvalue", status);
                startActivity(intent);
            }
        });
    }
}

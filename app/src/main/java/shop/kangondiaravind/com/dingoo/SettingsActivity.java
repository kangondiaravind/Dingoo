package shop.kangondiaravind.com.dingoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = currentuser.getUid();
        retriveUserdetails = database.getInstance().getReference().child("Users").child(currentId);
        retriveUserdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String image = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();

                dpname.setText(name);
                ustatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

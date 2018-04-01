package shop.kangondiaravind.com.dingoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button need_an_account;
    Button already_have_an_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        need_an_account =(Button)findViewById(R.id.need_account);
        already_have_an_account=(Button)findViewById(R.id.alreadyhaveaccount);
        need_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        already_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }
}

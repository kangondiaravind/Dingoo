package shop.kangondiaravind.com.dingoo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolBar;
    private ViewPager viewpager;
    private SectionPagerAdapter sectionPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mToolBar=(Toolbar)findViewById(R.id.mainpage_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Dingoo chat");
        viewpager=(ViewPager)findViewById(R.id.main_viewpager);
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(sectionPagerAdapter);

        tabLayout=(TabLayout)findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            sendToWelcomeScreen();
        }
    }

    private void sendToWelcomeScreen() {
        Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.main_logout){
             FirebaseAuth.getInstance().signOut();
             sendToWelcomeScreen();
         }
        return true;
    }
}

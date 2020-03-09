package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private androidx.appcompat.widget.Toolbar toolbarjava;
    private DrawerLayout drawerLayoutjava;
    private ActionBarDrawerToggle actionBarDrawerTogglejava;
    private NavigationView navigationViewjava;
    private Button btnsignout,btnchatbotjava;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        init();

        setSupportActionBar(toolbarjava);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbarjava.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        actionBarDrawerTogglejava = new ActionBarDrawerToggle(this,drawerLayoutjava,R.string.open,R.string.close);
        actionBarDrawerTogglejava.setDrawerSlideAnimationEnabled(true);
        drawerLayoutjava.addDrawerListener(actionBarDrawerTogglejava);
        actionBarDrawerTogglejava.syncState();

        navigationViewjava.setNavigationItemSelectedListener(this);
        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
        btnchatbotjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPage.this,_chat_bot.class));
            }
        });
        messaging();
    }

    private void messaging() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Mynotification","kanarase", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("beginner")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Toast.makeText(UserPage.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signout(){
        FirebaseAuth.getInstance().signOut();
        checkuserstatus();
        Intent intent = new Intent(UserPage.this,UserLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void init() {
        toolbarjava = findViewById(R.id.toolbarxml);
        drawerLayoutjava = findViewById(R.id.drawerlayoutxml);
        navigationViewjava = findViewById(R.id.navigationviewxml);
        btnsignout = findViewById(R.id.buttonsignout_successxml);
        btnchatbotjava = findViewById(R.id.buttonchatbotpagexml);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void checkuserstatus(){
        if (firebaseUser != null){
            Toast.makeText(this, "Logged In As : "+firebaseUser.getPhoneNumber()+firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(UserPage.this,UserLogin.class));
            finish();
        }
    }
    @Override
    protected void onStart() {
        checkuserstatus();
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerTogglejava.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.youraccountitem:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyAccount()).commit();
                break;
            }
            case R.id.dailyreportitem:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyDailyReport()).commit();
                break;
            }
            case R.id.yourprogressitem:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyProgress()).commit();
                break;
            }
            case R.id.yourrewardsitem:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyRewards()).commit();
                break;
            }
        }
        drawerLayoutjava.closeDrawer(GravityCompat.START);
        return true;
    }
}

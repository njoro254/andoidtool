package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    String straccessusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Intent useraccessintent = getIntent();
        straccessusername="makonyango";
      // straccessusername= useraccessintent.getStringExtra("metadataassistantusername");
        Toast.makeText(this, "user "+straccessusername, Toast.LENGTH_SHORT).show();
        //////fails////////////////
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", straccessusername);
        fragment.setArguments(bundle);
       // Intent intent = new Intent(MetadataSearchListActivity.this, AuthorSpeakerActivity.class);
      //  startActivity(intent);
       // HomeFragment hmf=g
       // .setArguments(getIntent().getBundleExtra("bundle_key"));
        //startActivity(new Intent(MainActivity.this, HomeFragment.class).putExtra("bundle_key", bundle));
      //  HomeFragment hmf=new HomeFragment();
        
       // hmf.
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}

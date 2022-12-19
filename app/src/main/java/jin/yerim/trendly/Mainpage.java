package jin.yerim.trendly;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Mainpage extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setTitle("");


        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "기프티콘 selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it = new Intent(Mainpage.this, Giftcon.class);
                        startActivity(it);
                        break;
                    case R.id.item2:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "폼 작성 및 업로드 selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it2 = new Intent(Mainpage.this, Form.class);
                        startActivity(it2);
                        break;
                }
                item.setChecked(false);
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }}

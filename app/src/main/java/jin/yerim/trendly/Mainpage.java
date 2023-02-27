package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Mainpage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    String point;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setTitle("");

        point = "";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                point = document.getData().get("point").toString();
                                TextView tv7 = findViewById(R.id.textView7);
                                tv7.setText(point+" point");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Giftcon Shop selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it = new Intent(Mainpage.this, Giftcon.class);
                        startActivity(it);
                        break;
                    case R.id.item2:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Donation Form selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it2 = new Intent(Mainpage.this, Form.class);
                        startActivity(it2);
                        break;
                    case R.id.item3:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Customer Inquiry selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it3 = new Intent(Mainpage.this, Helpform.class);
                        startActivity(it3);
                        break;
                    case R.id.item4:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Information Edit selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it4 = new Intent(Mainpage.this, Info.class);
                        startActivity(it4);
                        break;
                    case R.id.item5:
                        item.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Upcycling Form selected", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent it5 = new Intent(Mainpage.this, Template.class);
                        startActivity(it5);
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

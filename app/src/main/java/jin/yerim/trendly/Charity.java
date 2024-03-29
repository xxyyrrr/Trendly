package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Charity extends AppCompatActivity {

    int clothesnum;
    int quality;
    String charity="";
    Map<String, Object> charity1 = new HashMap<>();

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        // Handle the Intent
                        charity = intent.getStringExtra("charity");
                        Log.d(TAG, charity.toString());
                        charity1.put("charity", charity);
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity);
        clothesnum = getIntent().getIntExtra("clothesnum",0);
        quality = getIntent().getIntExtra("quality",0);

        Button btn = findViewById(R.id.button11);
        ImageButton btn1 = findViewById(R.id.button4);
        ImageButton btn2 = findViewById(R.id.button8);
        Button btn3 = findViewById(R.id.button9);
        Button btn4 = findViewById(R.id.button10);
        Button btn5 = findViewById(R.id.button12);
        Button submit_btn = findViewById(R.id.button13);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        charity1.put("clothesnum", clothesnum);
        charity1.put("quality", quality);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charity = "Cancer";
                Toast.makeText(getApplicationContext(),"Cancer Selected!", Toast.LENGTH_SHORT).show();
                charity1.put("charity", charity);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charity = "Children of Prisoners";
                Toast.makeText(getApplicationContext(),"Children of Prisoners Selected!", Toast.LENGTH_SHORT).show();
                charity1.put("charity", charity);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charity = btn3.getText().toString();
                Toast.makeText(getApplicationContext(),"Orphans Selected!", Toast.LENGTH_SHORT).show();
                charity1.put("charity", charity);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charity = btn4.getText().toString();
                Toast.makeText(getApplicationContext(),"Elder Selected!", Toast.LENGTH_SHORT).show();
                charity1.put("charity", charity);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charity = btn5.getText().toString();
                Toast.makeText(getApplicationContext(),"Poverty Selected!", Toast.LENGTH_SHORT).show();
                charity1.put("charity", charity);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Charity.this, Charitysearch.class);
                mStartForResult.launch(it);

            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("charity")
                        .add(charity1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(),"Success!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(getApplicationContext(),"Failure!", Toast.LENGTH_SHORT).show();
                            }
                        });

                Intent it = new Intent(Charity.this, Mainpage.class);
                startActivity(it);
                finish();
            }
        });

    }

}

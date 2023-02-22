package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity);
        String charity = getIntent().getStringExtra("charity");
        int clothesnum = getIntent().getIntExtra("clothesnum");
        int quality = getIntent().getIntExtra("quality");
        Button btn = findViewById(R.id.button11);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first, middle, and last name

        Map<String, Object> charity1 = new HashMap<>();
        charity1.put("clothesnum", clothesnum);
        charity1.put("quality", quality);
        charity1.put("charity", charity);

// Add a new document with a generated ID

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent(Charity.this, Charitysearch.class);
                db.collection("charity")
                        .add(charity1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                startActivity(it);
            }
        });

    }

}

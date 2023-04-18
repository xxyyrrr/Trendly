package jin.yerim.trendly;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClothingItem {
    private String id;
    private String name;
    private String brand;
    private Style style;
    private int similarityScore;



    public ClothingItem(String id, String name, String brand, Style style) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.style = style;
    }

    public void writeToDatabase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("clothing_items");
        dbRef.child(id).setValue(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ClothingItem", "Clothing item saved successfully to Firebase");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ClothingItem", "Error writing clothing item to Firebase", e);
                    }
                });
    }

    public int getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(int similarityScore) {
        this.similarityScore = similarityScore;
    }

    public Style getStyle() {
        return style;
    }

    public String getName () {
        return name;
    }

}


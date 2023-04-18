package jin.yerim.trendly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendationsFragment extends AppCompatActivity {

    private static final String TAG = "RecommendationsFragment";

    private TextView mRecommendationsTextView;
    private Style mUserPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recommendations);
        mRecommendationsTextView = findViewById(R.id.recommendations_text_view);

        mUserPreference = new Style(true, false, true);
        retrieveClothingItems();
    }

    private StylePreference getUserPreference() {
        // Implement user interface to select preferred style and return user preference as StylePreference object
        return new StylePreference("Red", "Striped", "Cotton");
    }

    private void retrieveClothingItems() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Style> clothingItems = new ArrayList<>();

        db.collection("userPreference")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Style clothingItem = new Style((Boolean) document.getData().get("casual"), (Boolean)document.getData().get("formal"), (Boolean)document.getData().get("sporty"));
                                clothingItems.add(clothingItem);
//                                Log.d(TAG, clothingItems.toString());

                            }
                            for (Style clothingItem : clothingItems) {
                                int similarityScore = calculateSimilarityScore(clothingItem);
                                // Store the similarity score with the clothing item
                                clothingItem.setSimilarityScore(similarityScore);
                            }

                            // Sort the clothing items based on their similarity scores
                            Collections.sort(clothingItems, new Comparator<Style>() {
                                public int compare(Style clothingItem1, Style clothingItem2) {
                                    return Integer.compare(clothingItem2.getSimilarityScore(), clothingItem1.getSimilarityScore());
                                }
                            });

                            // Display the top N clothing items to the user
                            int numRecommendations = 5;
                            List<Style> topClothingItems = clothingItems.subList(0, Math.min(numRecommendations, clothingItems.size()));
                            displayRecommendations(topClothingItems);


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    int similarityScore = 0;
    private int calculateSimilarityScore(Style clothingItemStyle) {

        if (clothingItemStyle.getIsCasual()) {
            similarityScore += 1;
        }
        if (clothingItemStyle.getIsFormal()) {
            similarityScore += 1;
        }
        if (clothingItemStyle.getIsSporty()) {
            similarityScore += 1;
        }
        return similarityScore / 3; // Normalize similarity score to be between 0 and 1
    }

    private void displayRecommendations(List<Style> clothingItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("Top Clothing Recommendations:\n\n");
        for (Style clothingItem : clothingItems) {
//            sb.append(clothingItem.getName()).append("\n");
            sb.append("isCasual: ").append(clothingItem.getIsCasual()).append("\n");
            sb.append("isFormal: ").append(clothingItem.getIsFormal()).append("\n");
            sb.append("isSporty: ").append(clothingItem.getIsSporty()).append("\n");
            sb.append("Similarity Score: ").append(clothingItem.getSimilarityScore()).append("\n\n");
        }
        mRecommendationsTextView.setText(sb.toString());
    }

}

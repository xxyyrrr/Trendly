package jin.yerim.trendly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendationsFragment extends Fragment {

    private static final String TAG = "RecommendationsFragment";

    private TextView mRecommendationsTextView;
    private StylePreference mUserPreference;

    public static RecommendationsFragment newInstance() {
        return new RecommendationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        mRecommendationsTextView = view.findViewById(R.id.recommendations_text_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserPreference = getUserPreference();
        retrieveClothingItems();
    }

    private StylePreference getUserPreference() {
        // Implement user interface to select preferred style and return user preference as StylePreference object
        return new StylePreference("Red", "Striped", "Cotton");
    }

    private void retrieveClothingItems() {
        Query query = FirebaseDatabase.getInstance().getReference("clothing_items");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ClothingItem> clothingItems = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClothingItem clothingItem = snapshot.getValue(ClothingItem.class);
                    clothingItems.add(clothingItem);
                }

                // Calculate the similarity score between the user's preferred style and each clothing item
                for (ClothingItem clothingItem : clothingItems) {
                    int similarityScore = calculateSimilarityScore(mUserPreference, clothingItem.getStyle());

                    // Store the similarity score with the clothing item
                    clothingItem.setSimilarityScore(similarityScore);
                }

                // Sort the clothing items based on their similarity scores
                Collections.sort(clothingItems, new Comparator<ClothingItem>() {
                    public int compare(ClothingItem clothingItem1, ClothingItem clothingItem2) {
                        return Integer.compare(clothingItem2.getSimilarityScore(), clothingItem1.getSimilarityScore());
                    }
                });

                // Display the top N clothing items to the user
                int numRecommendations = 5;
                List<ClothingItem> topClothingItems = clothingItems.subList(0, Math.min(numRecommendations, clothingItems.size()));
                displayRecommendations(topClothingItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
    int similarityScore = 0;
    private int calculateSimilarityScore(StylePreference userPreference, Style clothingItemStyle) {

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

    private void displayRecommendations(List<ClothingItem> clothingItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("Top Clothing Recommendations:\n\n");
        for (ClothingItem clothingItem : clothingItems) {
            sb.append(clothingItem.getName()).append("\n");
            sb.append("isCasual: ").append(clothingItem.getStyle().getisCasual()).append("\n");
            sb.append("isFormal: ").append(clothingItem.getStyle().getisFormal()).append("\n");
            sb.append("isSporty: ").append(clothingItem.getStyle().getisSporty()).append("\n");
            sb.append("Similarity Score: ").append(clothingItem.getSimilarityScore()).append("\n\n");
        }
        mRecommendationsTextView.setText(sb.toString());
    }
}

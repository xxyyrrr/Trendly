package jin.yerim.trendly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class RecommendationsFragment extends AppCompatActivity {

    private static final String TAG = "RecommendationsFragment";

    private TextView mRecommendationsTextView;
    private StylePreference mUserPreference;

//    public static RecommendationsFragment newInstance() {
//        return new RecommendationsFragment();
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
//        mRecommendationsTextView = view.findViewById(R.id.recommendations_text_view);
//        return view;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recommendations);
        ListView listview = findViewById(R.id.listView);

        mRecommendationsTextView = findViewById(R.id.recommendations_text_view);

        mUserPreference = getUserPreference();
        retrieveClothingItems();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

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

                for (ClothingItem clothingItem : clothingItems) {
                    int similarityScore = calculateSimilarityScore(mUserPreference, clothingItem.getStyle());

                    clothingItem.setSimilarityScore(similarityScore);
                }

                Collections.sort(clothingItems, new Comparator<ClothingItem>() {
                    public int compare(ClothingItem clothingItem1, ClothingItem clothingItem2) {
                        return Integer.compare(clothingItem2.getSimilarityScore(), clothingItem1.getSimilarityScore());
                    }
                });

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
            sb.append("isCasual: ").append(clothingItem.getStyle().getIsCasual()).append("\n");
            sb.append("isFormal: ").append(clothingItem.getStyle().getIsFormal()).append("\n");
            sb.append("isSporty: ").append(clothingItem.getStyle().getIsSporty()).append("\n");
            sb.append("Similarity Score: ").append(clothingItem.getSimilarityScore()).append("\n\n");
        }
        mRecommendationsTextView.setText(sb.toString());
    }

}

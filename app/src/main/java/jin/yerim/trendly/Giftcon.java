package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Giftcon extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcon);
        ListView listview = findViewById(R.id.listView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button btn = findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewAdapter adapter = new ListViewAdapter();

                db.collection("giftcon")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("type"));
                                        if (document.getData().get("type").equals("cu")){
                                            Log.d(TAG, document.getData().get("type").toString());
                                            // Create a reference with an initial file path and name

                                            adapter.addItem(new Item(document.getData().get("id").toString(), document.getData().get("name").toString(), R.drawable.bbebbero1));
                                        }
                                    }
                                    listview.setAdapter(adapter);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
        Button btn1 = findViewById(R.id.button5);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewAdapter adapter = new ListViewAdapter();

                //Adapter 안에 아이템의 정보 담기
                db.collection("giftcon")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("type"));
                                        if (document.getData().get("type").equals("7eleven")){
                                            Log.d(TAG, document.getData().get("type").toString());
                                            adapter.addItem(new Item(document.getData().get("id").toString(), document.getData().get("name").toString(), R.drawable.ic_baseline_add_circle_24));
                                        }
                                    }
                                    listview.setAdapter(adapter);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
        Button btn2 = findViewById(R.id.button6);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewAdapter adapter = new ListViewAdapter();

                //Adapter 안에 아이템의 정보 담기
                db.collection("giftcon")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("type"));
                                        if (document.getData().get("type").equals("starbucks")){
                                            Log.d(TAG, document.getData().get("type").toString());
                                            adapter.addItem(new Item("1", document.getData().get("name").toString(), R.drawable.ic_baseline_add_circle_24));
                                        }
                                    }
                                    listview.setAdapter(adapter);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
        Button btn3 = findViewById(R.id.button7);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewAdapter adapter = new ListViewAdapter();

                //Adapter 안에 아이템의 정보 담기
                db.collection("giftcon")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("type"));
                                        if (document.getData().get("type").equals("google")){
                                            Log.d(TAG, document.getData().get("type").toString());
                                            adapter.addItem(new Item("1", document.getData().get("name").toString(), R.drawable.ic_baseline_add_circle_24));
                                        }
                                    }
                                    listview.setAdapter(adapter);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

    /* 리스트뷰 어댑터 */
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<Item> items = new ArrayList<Item>();

        public void addItem(Item item) {
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Item Item = items.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // listitem layout을 inflate 해준다.(memory에 올려준다)
            convertView = inflater.inflate(R.layout.item, viewGroup, false);

            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

            tv_name.setText(Item.getName());
            iv_icon.setImageResource(Item.getResId());
            // Log.d(TAG, "getView() - [ "+position+" ] "+Item.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, Item.getNum()+" 번 - "+Item.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }
}

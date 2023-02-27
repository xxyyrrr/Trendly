package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Form extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        ListView listview = findViewById(R.id.listView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListViewAdapter adapter = new ListViewAdapter();

        db.collection("charity")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("charity") + document.getData().get("clothesnum") + document.getData().get("quality"));
                                // Create a reference with an initial file path and name
                                adapter.addItem(new FormItem(document.getData().get("charity").toString(), document.getData().get("clothesnum").toString(), document.getData().get("quality").toString()));
                            }
                        }
                        listview.setAdapter(adapter);
                    }
                });

        FloatingActionButton btn = findViewById(R.id.add_circle1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Form.this, Formsubmit.class);
                startActivity(it);
            }
        });

    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<FormItem> items = new ArrayList<FormItem>();

        public void addItem(FormItem item) {
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
            final FormItem Item = items.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // listitem layout을 inflate 해준다.(memory에 올려준다)
            convertView = inflater.inflate(R.layout.formitem, viewGroup, false);

            TextView tv_charity = (TextView) convertView.findViewById(R.id.tv_charity);
            tv_charity.setText(Item.getCharity());

            TextView tv_clothesnum = (TextView) convertView.findViewById(R.id.tv_clothesnum);
            tv_clothesnum.setText(Item.getClothesnum());

            TextView tv_quality = (TextView) convertView.findViewById(R.id.tv_quality);
            tv_quality.setText(Item.getQuality());
            //각 아이템 선택 event

            return convertView;
        }
    }
}

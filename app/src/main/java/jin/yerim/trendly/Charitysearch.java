package jin.yerim.trendly;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.List;

public class Charitysearch extends AppCompatActivity {

    private ListView listview;
    private ListViewAdapter adapter;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> arraylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charitysearch); // page2 inflate를 시켜줬기 때문에 이 안에 있는 id를 가져와서 쓸 수 있다.
        listview = findViewById(R.id.listView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        adapter = new ListViewAdapter();
        arraylist = new ArrayList<Item>();

        //Adapter 안에 아이템의 정보 담기
        db.collection("charitysearch")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get("type").equals("charity")) {
                                arraylist.add(new Item("1", document.getData().get("name").toString(), R.drawable.ic_baseline_add_circle_24));
                            }
                        }

                        adapter.addItems(arraylist);
                        listview.setAdapter(adapter);

                        EditText editSearch = (EditText) findViewById(R.id.editSearch);
                        editSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                                // input창에 문자를 입력할때마다 호출된다.
                                // search 메소드를 호출한다.
                                String text = editSearch.getText().toString();
                                search(text);
                            }
                        });
                        listview.setAdapter(adapter);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        items.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            items.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getName().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    Log.d(TAG, "in!!");
                    items.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    /* 리스트뷰 어댑터 */
    public class ListViewAdapter extends BaseAdapter {

        public void addItem(Item item) {
            items.add(item);
        }

        public void addItems(ArrayList<Item> item) {
            items.addAll(item);
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
            final Item item = items.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // listitem layout을 inflate 해준다.(memory에 올려준다)
            convertView = inflater.inflate(R.layout.item, viewGroup, false);

            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

            tv_name.setText(item.getName());
            iv_icon.setImageResource(item.getResId());
            Log.d(TAG, "getView() - [ " + position + " ] " + item.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, item.getNum() + " 번 - " + item.getName() + " 입니당! ", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }
}

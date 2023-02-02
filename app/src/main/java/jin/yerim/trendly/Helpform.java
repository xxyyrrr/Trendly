package jin.yerim.trendly;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Helpform extends AppCompatActivity {
    // onCreate 안쪽에서 adapter 세팅
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.helpform);

        //어댑터 생성
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, R.layout.spinner_layout);
        //드롭다운뷰 연결
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //UI와 연결
        binding.homeSpinner.setAdapter(adapter);
    }

    // onCreate 바깥쪽
//Spinner Listener
    public void spinnerListener() {
        binding.homeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //선택 시 작동기능
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

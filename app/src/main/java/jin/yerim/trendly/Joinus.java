package jin.yerim.trendly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Joinus extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinus);
        EditText Str2 = findViewById(R.id.editTextTextPersonName2);
        EditText Str3 = findViewById(R.id.editTextTextPersonName3);

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Joinus.this, Homepage.class);
                String myInput = Str2.getText().toString();
                String abc = Str3.getText().toString();
                it.putExtra("userInput",myInput);
                it.putExtra("userPassword",abc);
                startActivity(it);
            }
        });
    }
}
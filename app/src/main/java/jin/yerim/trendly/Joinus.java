package jin.yerim.trendly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                startActivity(it);
                EditText Str2 = findViewById(R.id.editTextTextPersonName2);
                EditText Str3 = findViewById(R.id.editTextTextPersonName3);
                String username = Str2.getText().toString();
                String password = Str3.getText().toString();
            }
        });

    }
}
package jin.yerim.trendly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;

public class Formsubmit extends AppCompatActivity {
    private final String TAG= this.getClass().getSimpleName();

    ImageView imageView2;
    Button Button15;
    Button Button16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formsubmit);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        Button15 = (Button) findViewById(R.id.button15);
        Button16 = (Button) findViewById(R.id.button16);
        Button btn = findViewById(R.id.button14);
        TextView tv = (TextView) findViewById(R.id.textView16);
        TextView tv1 = (TextView) findViewById(R.id.textView17);
        final int[] value = {0, 0};
        ImageButton ib = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton ib1 = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton ib2 = (ImageButton)findViewById(R.id.imageButton4);
        ImageButton ib3 = (ImageButton)findViewById(R.id.imageButton5);
        Button15.setOnClickListener(v -> mgetContent.launch("image/*"));
        Button16.setOnClickListener(v -> mgetContent.launch("image/*"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Formsubmit.this, Charity.class);
                startActivity(it);
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value[0] = value[0] +1;
                tv.setText(String.valueOf(value[0]));
            }
        });
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value[0] = value[0] -1;
                tv.setText(String.valueOf(value[0]));
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value[1] = value[1] +1;
                tv1.setText(String.valueOf(value[1]));
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value[1] = value[1] -1;
                tv1.setText(String.valueOf(value[1]));
            }
        });


    }
    ActivityResultLauncher<String> mgetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>(){
                @Override
                public void onActivityResult(Uri result) {
                if (result !=null) {
                    imageView2.setImageURI(result);
                    imageView2.setVisibility(View.VISIBLE);
                }
                }
            }
    );
}
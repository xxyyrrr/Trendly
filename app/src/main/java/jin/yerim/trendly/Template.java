package jin.yerim.trendly;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Template extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Button Button23;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);

        Button23 = (Button) findViewById(R.id.button23);
        Button23.setOnClickListener(v -> mgetContent.launch("image/*"));
        Toast.makeText(getApplicationContext(), "Sucess!", Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<String> mgetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>(){
                @Override
                public void onActivityResult(Uri result) {
                    if (result !=null) {
                        imageView.setImageURI(result);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

    );
}

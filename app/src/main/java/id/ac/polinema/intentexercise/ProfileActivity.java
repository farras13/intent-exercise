package id.ac.polinema.intentexercise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullname;
    private TextView email;
    private TextView Hpage;
    private TextView About;
    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        gambar = findViewById(R.id.image_profile);
        fullname = findViewById(R.id.label_fullname);
        email = findViewById(R.id.label_email);
        Hpage = findViewById(R.id.label_homepage);
        About = findViewById(R.id.label_about);

        findViewById(R.id.button_homepage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSite("http://" + Hpage.getText().toString());
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fn = getIntent().getExtras().getString("fname");
            String em = getIntent().getExtras().getString("email");
            String hp = getIntent().getExtras().getString("hpage");
            String ab = getIntent().getExtras().getString("about");
            Bitmap bmp = extras.getParcelable("imagebitmap");

            gambar.setImageBitmap(bmp);
            fullname.setText(fn);
            email.setText(em);
            Hpage.setText(hp);
            About.setText(ab);
        }
    }


    public void gotoSite(String url) {
        Uri homepage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, homepage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}


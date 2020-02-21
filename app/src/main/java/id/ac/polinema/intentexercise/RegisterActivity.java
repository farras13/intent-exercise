package id.ac.polinema.intentexercise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    public static final String FULLNAME_KEY = "fname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "pass";
    public static final String CPASSWORD_KEY = "cpass";
    public static final String HOMEPAGE_KEY = "hpage";
    public static final String ABOUT_KEY = "about";
    public static final String imagebitmap = "imagebitmap";
    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    private ImageView imageView;
    private ImageView ava;
    private Bitmap bitmap;
    private TextInputEditText fullnameinput;
    private TextInputEditText emailinput;
    private TextInputEditText passinput;
    private TextInputEditText cpassinput;
    private TextInputEditText hpageinput;
    private TextInputEditText aboutinput;

    public static boolean isValidEmail(String email) {
        boolean validate;
        String rule = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String rule2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(rule)) {
            validate = true;
        } else validate = email.matches(rule2);

        return validate;
    }

    public static boolean isValidUrl(String url) {
        boolean validate;
        String rule = "[a-zA-Z0-9._-]+\\.+[a-z]+";
        String rule2 = "[a-zA-Z0-9._-]+\\.+[a-z]+\\.+[a-z]+";

        if (url.matches(rule)) {
            validate = true;
        } else validate = url.matches(rule2);

        return validate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ava = findViewById(R.id.image_profile);
        fullnameinput = findViewById(R.id.text_fullname);
        emailinput = findViewById(R.id.text_email);
        passinput = findViewById(R.id.text_password);
        cpassinput = findViewById(R.id.text_confirm_password);
        hpageinput = findViewById(R.id.text_homepage);
        aboutinput = findViewById(R.id.text_about);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }

        if (requestCode == 1) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ava.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleProfile(View view) {
        String fullname = fullnameinput.getText().toString();
        String email = emailinput.getText().toString();
        String pass = passinput.getText().toString();
        String cpass = cpassinput.getText().toString();
        String Hpage = hpageinput.getText().toString();
        String abt = aboutinput.getText().toString();

        if (fullname.isEmpty() && email.isEmpty() && pass.isEmpty() && cpass.isEmpty() && Hpage.isEmpty() && abt.isEmpty() && bitmap == null) {
            fullnameinput.setError("Username wajib diisi bos!");
            emailinput.setError("Email wajib diisi bos!");
            passinput.setError("Password wajib diisi bos!");
            cpassinput.setError("confrim password wajib diisi bos");
            hpageinput.setError("HomePage wajib diisi bos!");
            aboutinput.setError("About wajib diisi bos!");
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else if (fullname.isEmpty()) {
            fullnameinput.setError("Username diperlukan!");
        } else if (email.isEmpty()) {
            emailinput.setError("Email diperlukan!");
        } else if (pass.isEmpty()) {
            passinput.setError("Password diperlukan!");
        } else if (cpass.isEmpty()) {
            cpassinput.setError("confrim password diperlukan");
        } else if (Hpage.isEmpty()) {
            hpageinput.setError("HomePage diperlukan!");
        } else if (abt.isEmpty()) {
            aboutinput.setError("About diperlukan!");
        } else {
            if (!cpass.equals(pass)) {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
                cpassinput.setError("Isian anda tidak sesuai");
            } else {
                if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Gunakan format email, xxx@xx.xx", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isValidUrl(Hpage)) {
                        Toast.makeText(getApplicationContext(), "Gunakan format url, url.domain", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registrasi Berhasil!",
                                Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(this, ProfileActivity.class);
                        ava.buildDrawingCache();
                        Bitmap image = ava.getDrawingCache();

                        Bundle extras = new Bundle();
                        extras.putParcelable(imagebitmap, image);
                        intent.putExtras(extras);

                        intent.putExtra(FULLNAME_KEY, fullname);
                        intent.putExtra(EMAIL_KEY, email);
                        intent.putExtra(HOMEPAGE_KEY, Hpage);
                        intent.putExtra(ABOUT_KEY, abt);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    public void changePicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

}

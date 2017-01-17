package pt.ismai.hungryme.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import de.hdodenhof.circleimageview.CircleImageView;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.UI.BaseActivity;


public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private TextView nav_user;
    public static final String MyPREFERENCES = "MyPrefs";
    FloatingActionButton btnSelectImage;
    CircleImageView imgView;
    Spinner spinner, spinnerHealth;
    SharedPreferences prefs;
    String verify;
    private FloatingActionButton buttonChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_account);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setupToolbar();

        btnSelectImage = (FloatingActionButton) findViewById(R.id.btnSelectImage);
        imgView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.user_profile_photo);

        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        String img_str = preferences.getString("userphoto2", "");
        if (!img_str.equals("")){
            String base = img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            imgView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
        }
        buttonChoose = (FloatingActionButton) findViewById(R.id.btnSelectImage);
        nav_user = (TextView) findViewById(R.id.user_profile_email);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diet_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.diet_spinner);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.health_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHealth = (Spinner) findViewById(R.id.health_spinner);
        spinnerHealth.setAdapter(adapter2);

        buttonChoose.setOnClickListener(this);

        final EditText name = (EditText) findViewById(R.id.txtName);
        final EditText note = (EditText) findViewById(R.id.txtNote);
        Button submit = (Button) findViewById(R.id.btnSubmit);
        Button cancel = (Button) findViewById(R.id.btnCancel);

        name.setText(prefs.getString("namestring", ""));
        verify = name.getText().toString();

        if (!verify.matches("")) {
            nav_user.setText(prefs.getString("namestring", ""));
        } else {
            nav_user.setText(prefs.getString("EMAIL", ""));
        }

        note.setText(prefs.getString("notestring", ""));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestring = name.getText().toString();
                String dietstring = spinner.getSelectedItem().toString();
                String healthstring = spinnerHealth.getSelectedItem().toString();
                String notestring = note.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("namestring", namestring);
                editor.putString("dietstring", dietstring);
                editor.putString("healthstring", healthstring);
                editor.putString("notestring", notestring);

                verify = name.getText().toString();
                if (!verify.matches("")) {
                    nav_user.setText(name.getText().toString());
                    startActivity(new Intent(getBaseContext(), AccountActivity.class));
                } else {
                    nav_user.setText(prefs.getString("EMAIL", ""));
                }

                setProfileImage(view);
                editor.commit();
                Toast.makeText(getBaseContext(), "Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                note.setText("");
                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_account;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
        }
    }

    public void setProfileImage(View view){

        imgView.buildDrawingCache();
        Bitmap bitmap = imgView.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);

        String base=img_str;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);

        imgView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );

        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userphoto2",img_str);
        editor.commit();
    }
}

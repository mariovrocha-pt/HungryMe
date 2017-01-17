package pt.ismai.hungryme.LoginAndRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.ismai.hungryme.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button reg;
    private TextView tvLogin;
    private EditText etEmail, etPass;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        reg = (Button)findViewById(R.id.btnRegister);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        etEmail = (EditText)findViewById(R.id.emailRegister);
        etPass = (EditText)findViewById(R.id.passwordRegister);
        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                register();
                break;
            case R.id.tvLogin:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            default:

        }
    }

    private void register(){
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if(email.isEmpty() && pass.isEmpty()){
            displayToast("Username/Password field empty");
        }else{
            db.addUser(email,pass);
            displayToast("Account Created");
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

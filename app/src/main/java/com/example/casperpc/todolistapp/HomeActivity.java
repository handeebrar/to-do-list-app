package com.example.casperpc.todolistapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //SQLite Database örneği oluşturuyoruz
        loginDataBaseAdapter = new LoginDatabaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        //Butonların referansını bağlıyoruz
        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);

        //onClick Listener'ını SignUp butonu için ayarlıyoruz
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent oluşturuyoruz(signUp butonuna basınca SignUP activity çalışmasını sağlıyor.
                Intent intentSignUP = new Intent(getApplicationContext(), SignUPActivity.class);
                startActivity(intentSignUP);
            }
        });
    }

    //Sign In butonuna basınca olacakları belirliyoruz.
    public void signIn(View v) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.login); //login.xml'e gider
        dialog.setTitle("Login...");

        //View'ların referanslarını bağlıyoruz
        final EditText editTextUserName = (EditText) dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

        //onClikListener'ı ayarlıyoruz
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Kullanıcı adı ve şifreyi alıyoruz
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                //kullanıcının girdiği şifreyi tutuyoruz
                int storedPassword = LoginDatabaseAdapter.getSingleEntry(userName, password);

                //şifrenin doğru girilip girilmediğini kontrol ediyoruz
                if (password.equals(storedPassword)) {
                    Toast.makeText(HomeActivity.this, "Kullanıcı Girişi Başarılı !", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                } else
                    Toast.makeText(HomeActivity.this, "Kullanıcı adı veya şifre hatalı!", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //db'yi kapat.
        loginDataBaseAdapter.close();
    }

}

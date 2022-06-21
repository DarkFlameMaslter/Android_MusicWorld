package com.example.musicworldfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.musicworldfinal.models.DBHelper;
import com.example.musicworldfinal.models.RESTapi;
import com.example.musicworldfinal.models.RetrofitClient;
import com.example.musicworldfinal.models.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private String TAG = "Login Act";

    private EditText userID, userPassword;
    private Button loginButton;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);

        userID = findViewById(R.id.userLoginIdInput);
        userPassword = findViewById(R.id.userLoginPasswordInput);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+userID.getText()+ userPassword.getText());
                checkUserIn4(userID.getText().toString(), userPassword.getText().toString());
            }
        });

    }

    private void checkUserIn4(String userLoginId, String userLoginPassword){
        //get user name from host
        RESTapi resTapi = RetrofitClient.getRetrofitInstance().create(RESTapi.class);
        Call<List<user>> call = resTapi.Login(userLoginId, userLoginPassword);
        Log.i(TAG, "checkUserIn4: Retrofit call"+call.request().url());

        call.enqueue(new Callback<List<user>>() {
            @Override
            public void onResponse(Call<List<user>> call, Response<List<user>> response) {
                Log.i(TAG, "onResponse: "+response.body());
                List<user> thelist = response.body();
                user theuser = thelist.get(0);
                DBHelper db = new DBHelper(Login.this);
                db.userLogin(theuser);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<user>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.toString());
            }
        });


    }
}
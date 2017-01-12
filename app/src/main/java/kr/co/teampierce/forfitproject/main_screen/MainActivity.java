package kr.co.teampierce.forfitproject.main_screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kr.co.teampierce.forfitproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){

        getSupportActionBar().hide();

        /* 로그인 연동하면 이름과 이메일 주소가 넘어옴
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("email");
        Log.i("TAG","name :" + name + "email :" + email);
        */
    }
}

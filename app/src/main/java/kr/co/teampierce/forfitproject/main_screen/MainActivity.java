package kr.co.teampierce.forfitproject.main_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }
}
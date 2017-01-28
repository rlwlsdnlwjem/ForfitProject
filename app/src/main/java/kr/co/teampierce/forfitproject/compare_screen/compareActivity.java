package kr.co.teampierce.forfitproject.compare_screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import kr.co.teampierce.forfitproject.R;

public class compareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        //status bar removing
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        int leftIndex = intent.getExtras().getInt("left");
        int rightIndex = intent.getExtras().getInt("right");
        Log.i("TAG","left :" + leftIndex + "   right :" + rightIndex);
        init(leftIndex, rightIndex);
    }
    public void init(int leftIndex, int rightIndex){
        //TODO 데이터 불러와서 보여주기


    }
}

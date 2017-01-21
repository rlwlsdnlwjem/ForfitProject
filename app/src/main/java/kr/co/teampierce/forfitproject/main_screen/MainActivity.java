package kr.co.teampierce.forfitproject.main_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.camera_screen.CameraActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){

        /* 로그인 연동하면 이름과 이메일 주소가 넘어옴
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("email");
        Log.i("TAG","name :" + name + "email :" + email);
        */
    }

    public void onCameraButton(View v){

        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);

        startActivity(intent);
    }
}

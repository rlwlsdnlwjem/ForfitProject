package kr.co.teampierce.forfitproject.login_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.main_screen.MainActivity;


public class LoginActivity extends Activity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void gotomain(View v){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("name","testname");
        i.putExtra("email","test@test.com");
        startActivity(i);
        finish();
    }
    public void facebookLoginOnClick(View v){

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {
                //System.out.println("loginSuccess");
                Log.i("TAG", "loginSuccess");

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {
                            Log.i("TAG","cannot get user information");
                            return;

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);
                            String email="",name="";

                            if(user.has("email"))
                                email = user.optString("email");
                            if(user.has("name"))
                                name = user.optString("name");
                            Log.i("TAG", "user email " + email);

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("name",name);
                            i.putExtra("email",email);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("TAG", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                Log.i("TAG", "login failure");
                //finish();
            }
        });
    }


}

package kr.co.teampierce.forfitproject.login_screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kakao.auth.AuthType;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import kr.co.teampierce.forfitproject.R;
import kr.co.teampierce.forfitproject.main_screen.MainActivity;

import static android.R.attr.button;
import static android.R.attr.name;
import static android.os.Build.ID;
import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager facebookCallbackManager; // 페이스북꺼
    private String mFacebookAccessToken;
    kakaoSessionCallback callback; // 카톡꺼

    private OAuthLogin mOAuthLoginModule;    // 네이버 꺼
    private GoogleApiClient mGoogleApiClient; // 구글 꺼

    private static final int RC_SIGN_IN=998;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLoginCheck();
        facebookLoginInit();
        setContentView(R.layout.activity_login);
        // status bar removing
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitializeNaverAPI();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Button buttonGoogleLogin = (Button) findViewById(R.id.button_googleLogin);

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        Log.i("TAG", "loginPage");
        //gotoMain();
        //for debug only



        /**카카오톡 로그아웃 요청**/
        //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출합니다.
        //테스트 하시기 편하라고 매번 로그아웃 요청을 수행하도록 코드를 넣었습니다 ^^
/*
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩 ~
            }
        });
*/

    }
    private void InitializeNaverAPI( )
    {
        mOAuthLoginModule = OAuthLogin.getInstance( );
        mOAuthLoginModule.init(
                LoginActivity.this,
                "z4KvxGG4_zsLI_6mtbBK" ,
                "MMS1oFMeVk" ,
                "ForFit"
        );

        // 네이버 로그인 버튼 리스너 등록
        //OAuthLoginButton naverLoginButton = ( OAuthLoginButton ) findViewById( R.id.button_naverLogin);
        //  Button naverLoginButton=(Button)findViewById(R.id.button_naverLogin);
        //naverLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);


    }


    public void OnClickNaverLoginButton(View v){
        //com.nhn.android.naverlogin.ui.view.OAuthLoginButton
        mOAuthLoginModule.startOauthLoginActivity(this, new OAuthLoginHandler( )
        {
            @Override
            public void run( boolean b )
            {
                if ( b )
                {
                    final String token = mOAuthLoginModule.getAccessToken( LoginActivity.this );
                    new Thread( new Runnable( )
                    {
                        @Override
                        public void run( )
                        {
                            String response = mOAuthLoginModule.requestApi( LoginActivity.this, token, "https://openapi.naver.com/v1/nid/me" );
                            try
                            {
                                JSONObject json = new JSONObject( response );
                                // response 객체에서 원하는 값 얻어오기
                                String email = json.getJSONObject( "response" ).getString( "email" );
                                Log.i("TAG","naverlogin result : " + email);
                                Log.i("TAG","naverlogin json : " + json);
                                setAppPreferences(LoginActivity.this, "ACCESS_TOKEN", "naverLoginSuccess");
                                gotoMain();
                                // 액티비티 이동 등 원하는 함수 호출
                            } catch ( JSONException e )
                            {
                                e.printStackTrace( );
                            }
                        }
                    } ).start( );
                }
                else
                {
                }
            }
        });
    }
    //공통
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // facebookLoginResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            //google 부분
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
                //카톡 부분
                Log.i("TAG","onActivityResult");
                return;
            }
            super.onActivityResult(requestCode, resultCode, data);

            facebookCallbackManager.onActivityResult(requestCode, resultCode, data); // facebook 부분

        }





    }

    //구글꺼
   private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i("TAG","google login result : " + acct.getEmail().toString());
            setAppPreferences(LoginActivity.this, "ACCESS_TOKEN", "googleLoginSuccess");
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            gotoMain();
        } else {
            // Signed out, show unauthenticated UI.
            Log.i("TAG", "google login failure");

            //updateUI(false);
        }
    }

    public void gotoMain(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("name","testname");
        i.putExtra("email","test@test.com");
        startActivity(i);
        finish();
    }

    // app 쉐어드 프레퍼런스에 값 저장 facebook 것만//
    private void setAppPreferences(Activity context, String key, String value)
    {
        SharedPreferences pref = null;
        pref = context.getSharedPreferences("AutoLogin", 0);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    // app 쉐어드 프레퍼런스에서 값을 읽어옴
    private String getAppPreferences(Activity context, String key)
    {
        String returnValue = null;

        SharedPreferences pref = null;
        pref = context.getSharedPreferences("AutoLogin", 0);

        returnValue = pref.getString(key, "");

        return returnValue;
    }


    public void autoLoginCheck(){
        String checkToken = getAppPreferences(this, "ACCESS_TOKEN");

        // 그냥 적절한 토큰 값인지만 확인 (길이가 일정 이상) 한번 로그인 했다면 굉장히 긴 토큰을 가지고 있을 것이기 때문
        if(checkToken.length() > 5 ) {
            Log.i("TAG","checkToken : " + checkToken);
            gotoMain();
        }
    }
    // 페북
    public void facebookLoginInit(){

        FacebookSdk.sdkInitialize(this.getApplicationContext());
     /*   mFacebookAccessToken = getAppPreferences(this, "ACCESS_TOKEN");

        // 그냥 적절한 토큰 값인지만 확인 (길이가 일정 이상) 한번 로그인 했다면 굉장히 긴 토큰을 가지고 있을 것이기 때문
        if(mFacebookAccessToken.length() > 5 ) {
            Log.i("TAG","facebooktoken : " + mFacebookAccessToken);
            gotoMain();
        }*/


    }
    public void facebookLoginResult(int requestCode, int resultCode, Intent data){
    }
    public void facebookLoginOnClick(View v){

        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {
                //System.out.println("loginSuccess");
                Log.i("TAG", "facebookloginSuccess");

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
                            setAppPreferences(LoginActivity.this, "ACCESS_TOKEN", result.getAccessToken().getToken());
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
                Log.i("TAG", "login cancel");
                //finish();
            }
        });
    }

    // 여기까지 페북

    // 카톡 로그인 부분

    public void kakaoLoginOnclick(View v){
        callback = new kakaoSessionCallback();

        Session.getCurrentSession().addCallback(callback);
        if(Session.getCurrentSession().checkAndImplicitOpen()==true)
            gotoMain();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, LoginActivity.this);


    }
    private class kakaoSessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.i("TAG","sessionOpened");
            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Log.i("TAG","fail");
                    Log.i("TAG",message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.i("TAG","sessionclosed");
                }

                @Override
                public void onNotSignedUp() {
                    Log.i("TAG","notsignedup");
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    Log.i("TAG", userProfile.toString());
                    Log.i("TAG","KakaoLoginsuccess");
                    setAppPreferences(LoginActivity.this, "ACCESS_TOKEN", "kakaoLoginSuccess");
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("name",name);
                    // i.putExtra("email",email);
                    startActivity(i);

                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음 ㅜㅜ
            //Log.i("TAG","sessionopenfailed");
        }

    }

    // 여기까지 카톡 로그인 부분
}

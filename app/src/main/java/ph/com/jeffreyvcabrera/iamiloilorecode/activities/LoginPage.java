package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Coupon;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.CustomTextView;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.FBLoginAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LocalAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Md5;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ValidationUtil;

/**
 * Created by Jeffrey on 3/7/2017.
 */


public class LoginPage extends AppCompatActivity implements View.OnClickListener, AsyncTaskListener {

    ProfileTracker mProfileTracker;
//    private static final String MODULE = "LocalLoginActivity";
    private static String TAG = "";

    private Context mContext = null;
    private Activity mActivity = null;

    private LinearLayout mEmailLayout, mPasswordLayout;
    private EditText edtEmail = null;
    private EditText edtPass = null;

    private ImageView backBtn = null;
    private ImageButton fbSignIn = null;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private CustomTextView signInbtn = null, freeSignupbtn = null,
            localLogin_forgotpassword = null;
    private ProgressDialog dialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.login_activity);

        mContext = this;
        mActivity = this;

        initUI();
        setOnClickListener();
        initFacebookSignInButton();
    }

    private void initUI() {

        mEmailLayout = (LinearLayout) findViewById(R.id.linearLayoutLLEmail);
        edtEmail = (EditText) findViewById(R.id.locallogin_edtEmail);
        mPasswordLayout = (LinearLayout) findViewById(R.id.linearLayoutLLPassword);
        edtPass = (EditText) findViewById(R.id.edt_passowrd_login);
        signInbtn = (CustomTextView) findViewById(R.id.locallogin_singin);
//        fbSignIn = (ImageButton) findViewById(R.id.locallogin_fb_signinbtn);
        freeSignupbtn = (CustomTextView) findViewById(R.id.locallogin_free_signinbtn);
        localLogin_forgotpassword = (CustomTextView) findViewById(R.id.localLogin_forgotpassword);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        edtPass.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Antonio-Light.ttf"));
        edtEmail.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Antonio-Light.ttf"));
    }


    private void setOnClickListener() {
        signInbtn.setOnClickListener(this);
//        fbSignIn.setOnClickListener(this);
        freeSignupbtn.setOnClickListener(this);
        localLogin_forgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.locallogin_fb_signinbtn:
//                loginButton.performClick();
//                break;

            case R.id.locallogin_singin:
                isDataValid();
                break;

            case R.id.locallogin_free_signinbtn:
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
                break;
        }
    }

    private void isDataValid() {
        // TODO Auto-generated method stub
        boolean isValid = ValidationUtil.isEmailDataValid(edtEmail, mContext,
                mEmailLayout);
        if (isValid) {
            isValid = ValidationUtil.isPasswordDataValid(edtPass, mContext,
                    mPasswordLayout);
            if (isValid) {
                if (ValidationUtil.isNetworkAvailable(mContext)) {
                    new LiveAPI(LoginPage.this, LoginPage.this, false).execute("POST", "/api_users/login2/1/"+edtEmail.getText()+"/"+ Md5.md5(String.valueOf(edtPass.getText())));

                } else {
                    ValidationUtil.showNoInternetAlert(mContext);
                }
            }
        }
    }

    @Override
    public void onTaskComplete(String result) {
        try {
            JSONObject jObj = new JSONObject(result);
            boolean success = jObj.getBoolean("success");

            if (success) {
                UsersModel um = new UsersModel();

                JSONObject activity_data = jObj.optJSONObject("user_data");

                Integer id = activity_data.optInt("id");
                Integer login_type = activity_data.optInt("login_type");
                String email = activity_data.optString("email");
                String firstname = activity_data.optString("firstname");
                String lastname = activity_data.optString("lastname");
                String mobile = activity_data.optString("mobile");
                String birthdate = activity_data.optString("birthdate");

                um.setId(id);
                um.setLogin_type(login_type);
                um.setEmail(email);
                um.setFirstname(firstname);
                um.setLastname(lastname);
                um.setBirthdate(birthdate);
                um.setMobile(mobile);
                um.setPassword(Md5.md5(String.valueOf(edtPass.getText())));
                um.setLogged(true);

                SharedPrefManager sm = new SharedPrefManager(LoginPage.this);
                sm.userSaverModel(um);

                Intent intent = new Intent(LoginPage.this, SwipeActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                String message = jObj.getString("error");
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){



        }
    }

    void initFacebookSignInButton() {

        ImageButton fbSignIn = (ImageButton) findViewById(R.id.locallogin_fb_signinbtn);
        fbSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        loginButton.setReadPermissions(Arrays.asList(new String[]{"email", "public_profile"}));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Facebook Sign-in", "Facebook Login Canceled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("Facebook Sign-in", "Facebook Login Error " + e.toString());
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                handleFacebookAccessToken(newAccessToken);
            }
        };
        handleFacebookAccessToken(AccessToken.getCurrentAccessToken());
    }

    private void handleFacebookAccessToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    currentAccessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            try {

                                String email = object.getString("email").trim();
                                String firstname = object.getString("first_name").trim();

                                new FBLoginAPI(LoginPage.this, LoginPage.this, false).execute("POST", "/api_users/login2/2/"+email+"/-/"+firstname);

                            } catch (JSONException e) {
                                Log.d("Facebook Sign-in", e.toString());
                            }
                        }

                    });


            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,middle_name,last_name,email");
            request.setParameters(parameters);
            request.executeAsync();

        } else {
            Log.d("Facebook Sign-in", "Facebook Login Failed / Facebook Signed Out / Null access token");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

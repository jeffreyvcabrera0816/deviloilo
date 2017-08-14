package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.AppUtil;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.CustomTextView;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.DateDialog;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LocalAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Md5;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ValidationUtil;

/**
 * Created by Jeffrey on 2/22/2017.
 */

public class RegisterPage extends AppCompatActivity implements
        View.OnClickListener, AsyncTaskListener {
    private static final String MODULE = "Register";
    private static String TAG = "";
    Activity mContext = RegisterPage.this;
    private EditText mFNameText, mLNameText, mMobileNoText, mBirthdayText,
            mEmailText, mPasswordText, mRePasswordText;
    private ImageView backBtn = null;
    private CheckBox termsConditionCheck = null;
    private LinearLayout termsConditionParentView = null;
    private CustomTextView termsConditionText = null;

    private LinearLayout mRegisterLayout;

    private DatePickerDialog mBirthdayDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        InitUI();
    }

    private void InitUI() {
        final String TAG = "InitFragment";

            mRegisterLayout = (LinearLayout) findViewById(R.id.linearLayoutRAURegister);

            mFNameText = (EditText) findViewById(R.id.editTextRUAFirstName);
            mFNameText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            mLNameText = (EditText) findViewById(R.id.editTextRUALastName);
            mLNameText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            mMobileNoText = (EditText) findViewById(R.id.editTextRUAMobileNumber);
            mMobileNoText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            mBirthdayText = (EditText) findViewById(R.id.editTextRUABirthday);
            mBirthdayText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));
            mBirthdayText.setInputType(InputType.TYPE_NULL);

            mEmailText = (EditText) findViewById(R.id.editTextRUAEmailAddress);
            mEmailText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            mPasswordText = (EditText) findViewById(R.id.editTextRUAPassword);
            mPasswordText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            mRePasswordText = (EditText) findViewById(R.id.editTextRUARePassword);
            mRePasswordText.setTypeface(Typeface.createFromAsset(
                    RegisterPage.this.getAssets(), "fonts/Helvetica_Light.ttf"));

            termsConditionCheck = (CheckBox) findViewById(R.id.checkBoxRAUTerms);
            termsConditionParentView = (LinearLayout) findViewById(R.id.linear_termscondition);
            termsConditionText = (CustomTextView) findViewById(R.id.textviewRAUTerms);

                setDateTimeField();
                SetOnClickListeners();

    }

    private void setDateTimeField() {
        mBirthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog dialog = new DateDialog(view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });

    }

    private void SetOnClickListeners() {
        mRegisterLayout.setOnClickListener(this);
        termsConditionText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearLayoutRAURegister:
                isDataValid();
                break;

            case R.id.editTextRUABirthday:
                AppUtil.hideKeyboard(mBirthdayText, mContext);
                mBirthdayDatePickerDialog.show();
                break;
        }
    }

    private void isDataValid() {
        // TODO Auto-generated method stub
        TAG = "isDataValid";
        Log.d(MODULE, TAG + " Called.");

        try {
            boolean isValid = ValidationUtil.isEmptyDataValid(
                    mFNameText,
                    mContext,
                    mContext.getResources().getString(
                            R.string.error_empty_Firstname));
            if (isValid) {
                isValid = ValidationUtil.isEmptyDataValid(
                        mLNameText,
                        mContext,
                        mContext.getResources().getString(
                                R.string.error_empty_Lastname));
                if (isValid) {
					/*
					 * isValid = ValidationUtil.isEmptyDataValid( mMobileNoText,
					 * mContext, mContext.getResources().getString(
					 * R.string.error_empty_mobile));
					 */
                    if (isValid) {
						/*
						 * isValid = ValidationUtil.isEmptyDataValid(
						 * mBirthdayText, mContext,
						 * mContext.getResources().getString(
						 * R.string.error_empty_bithdate));
						 */
                        if (isValid) {
                            isValid = ValidationUtil.isEmailDataValid(
                                    mEmailText, mContext, null);

                            if (isValid) {
                                isValid = ValidationUtil.isPasswordDataValid(
                                        mPasswordText, mContext, null);

                                if (isValid) {
                                    isValid = ValidationUtil
                                            .isPasswordConfirmDataValid(
                                                    mPasswordText,
                                                    mRePasswordText, mContext);

                                    if (isValid) {
                                        isValid = ValidationUtil
                                                .TermsConditionValid(
                                                        termsConditionCheck,
                                                        mContext,
                                                        termsConditionParentView);

                                        if (isValid) {

                                            isValid = ValidationUtil
                                                    .isMobileNoDataValid(
                                                            mMobileNoText,
                                                            mContext);

                                            if (isValid) {
                                                if (ValidationUtil.isNetworkAvailable(mContext)) {
                                                    makeServiceCallForRegister();
                                                } else {
                                                    ValidationUtil
                                                            .showNoInternetAlert(mContext);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(MODULE, TAG + " Exception Occurs : " + e);
        }
    }

    public void makeServiceCallForRegister() {
        // TODO Auto-generated method stub
        TAG = "makeServiceCallForRegister";
        Log.d(MODULE, TAG + " Called.");

        try {

            String strFName = mFNameText.getText().toString().trim();
            String strLname = mLNameText.getText().toString().trim();
            String strEmail = mEmailText.getText().toString().trim();
            String strPassword = mPasswordText.getText().toString().trim();
            String strMobile = mMobileNoText.getText().toString().trim();
            String strBirthdate = mBirthdayText.getText().toString().trim();
            String mobile = strMobile.substring(1);
            String Url = "/api_users/register/"+strEmail+"/"+Md5.md5(strPassword)+"/"+strFName+"/"+strLname+"/"+strBirthdate+"/"+mobile;

            registerWebServiceCall(Url);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(MODULE, TAG + " Exception Occurs : " + e);
        }
    }

    public void registerWebServiceCall(String Url) {
        new LiveAPI(RegisterPage.this, RegisterPage.this, false).execute("POST", Url);
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
                String strFName = mFNameText.getText().toString().trim();
                String strLname = mLNameText.getText().toString().trim();
                String strEmail = mEmailText.getText().toString().trim();
                String strPassword = mPasswordText.getText().toString().trim();
                String strMobile = mMobileNoText.getText().toString().trim();
                String strBirthdate = mBirthdayText.getText().toString().trim();
                String mobile = strMobile.substring(1);

                um.setId(id);
                um.setEmail(strEmail);
                um.setFirstname(strFName);
                um.setLastname(strLname);
                um.setBirthdate(strBirthdate);
                um.setMobile(mobile);
                um.setLogged(true);

                SharedPrefManager sm = new SharedPrefManager(RegisterPage.this);
                sm.userSaverModel(um);

                Intent intent = new Intent(RegisterPage.this, SwipeActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                String message = jObj.getString("error");
                Toast.makeText(RegisterPage.this, message, Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){

        }
    }
}

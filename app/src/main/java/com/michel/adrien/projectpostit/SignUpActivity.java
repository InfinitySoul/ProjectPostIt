package com.michel.adrien.projectpostit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import fragment.ConfirmPasswordFragment;
import complementaryClass.LoggedInCheck;
import settings.StringControl;

/*
    Activity to sign up the user. Uses CallAPISignUp
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Redirect if the user is loggedIn
        Intent intentIfLoggedIn = new Intent(SignUpActivity.this, LoadingActivity.class);
        if(LoggedInCheck.isLoggedIn(getBaseContext())){
            startActivity(intentIfLoggedIn);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hide the keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*Sign up button */
        Button signUp = (Button)findViewById(R.id.sign_up_activity_btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.sign_up_activity_etUsername)).getText().toString();
                String password =
                        ((EditText) findViewById(R.id.sign_up_activity_etPassword)).getText().toString();
                String email = ((EditText) findViewById(R.id.sign_up_activity_etEmail)).getText().toString();
                /* After getting all the informations from the user we check their length.
                If they are good, we show the confirm password fragment */
                if (StringControl.checkUsernameLength(getBaseContext(), username) &&
                        StringControl.checkEmailLength(getBaseContext(), email) &&
                        StringControl.checkPasswordLength(getBaseContext(), password) &&
                         StringControl.is_Valid_Email(getBaseContext(), email))
                {

                    DialogFragment confirmPasswordDialog = ConfirmPasswordFragment.newInstance(username, email, password);
                    confirmPasswordDialog.show(getSupportFragmentManager(), "");
                }
            }
        });

        /*Return to username button */
        Button goToLogin = (Button)findViewById(R.id.sign_up_activity_btnGoToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intentToLogin);
            }
        });
    }
}

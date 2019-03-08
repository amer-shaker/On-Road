package com.android.onroad;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // UI references
    private View mLoginFormView;

    private TextInputLayout mEmailTextInput;
    private TextInputLayout mPasswordTextInput;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;
    private ProgressBar mProgressBar;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Instance variables
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI
        initializeViews();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        setupFirebaseAuth();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        TextView register = (TextView) findViewById(R.id.link_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView resetPassword = (TextView) findViewById(R.id.forgot_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView resendEmailVerification = (TextView) findViewById(R.id.resend_verification_email);
        resendEmailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hideSoftKeyboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void initializeViews() {
        // Login form
        mLoginFormView = findViewById(R.id.login_form);

        // Setup Login form
        mEmailTextInput = (TextInputLayout) findViewById(R.id.email_text_input);
        mPasswordTextInput = (TextInputLayout) findViewById(R.id.password_text_input);
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);

        mEmailEditText.addTextChangedListener(new FormTextWatcher(mEmailEditText));
        mPasswordEditText.addTextChangedListener(new FormTextWatcher(mPasswordEditText));

        // Login progress
        mProgressBar = findViewById(R.id.login_progress);
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        showProgress(true);
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showProgress(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }

    private boolean validateEmail() {
        email = mEmailEditText.getText().toString();

        if (!Validation.isValidEmail(email)) {
            mEmailTextInput.setError(getString(R.string.error_invalid_email));
            requestFocus(mEmailEditText);
            return false;
        } else {
            mEmailTextInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        password = mPasswordEditText.getText().toString();

        if (!Validation.isValidPassword(password)) {
            mPasswordTextInput.setError(getString(R.string.error_invalid_password));
            requestFocus(mPasswordEditText);
            return false;
        } else {
            mPasswordTextInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setupFirebaseAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Check if the user has a verified email address
                    if (user.isEmailVerified()) {
                        Log.d(TAG, "onAuthStateChanged(): Signed in: " + user.getUid());
                        Toast.makeText(LoginActivity.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        redirectHomeScreen();
                    } else {
                        Toast.makeText(LoginActivity.this, "Check you email inbox for a verification link", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged(): Signed out");
                }
            }
        };
    }

    private void redirectHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class FormTextWatcher implements TextWatcher {

        private View view;

        private FormTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email_edit_text:
                    validateEmail();
                    break;
                case R.id.password_edit_text:
                    validatePassword();
                    break;
            }
        }
    }
}
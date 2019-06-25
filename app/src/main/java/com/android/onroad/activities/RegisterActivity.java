package com.android.onroad.activities;

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
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.UserBean;
import com.android.onroad.utils.Utility;
import com.android.onroad.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    // UI references
    private View mRegistrationFormView;

    private TextInputLayout mUsernameTextInput;
    private TextInputLayout mEmailTextInput;
    private TextInputLayout mPasswordTextInput;
    private TextInputLayout mConfirmPasswordTextInput;

    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mRegisterButton;
    private ProgressBar mProgressBar;

    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;

    // Instance variables
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI
        initializeViews();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = Utility.getFirebaseDatabaseInstance();


        mUsersDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.users_database_node));

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        hideSoftKeyboard();
    }

    private void initializeViews() {
        // Registration form
        mRegistrationFormView = findViewById(R.id.registration_form);

        // Setup registration form
        mUsernameTextInput = (TextInputLayout) findViewById(R.id.username_text_input);
        mEmailTextInput = (TextInputLayout) findViewById(R.id.email_text_input);
        mPasswordTextInput = (TextInputLayout) findViewById(R.id.password_text_input);
        mConfirmPasswordTextInput = (TextInputLayout) findViewById(R.id.confirm_password_text_input);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.confirm_password_edit_text);
        mRegisterButton = (Button) findViewById(R.id.register_button);

        mUsernameEditText.addTextChangedListener(new FormTextWatcher(mUsernameEditText));
        mEmailEditText.addTextChangedListener(new FormTextWatcher(mEmailEditText));
        mPasswordEditText.addTextChangedListener(new FormTextWatcher(mPasswordEditText));
        mConfirmPasswordEditText.addTextChangedListener(new FormTextWatcher(mConfirmPasswordEditText));

        // Sign up progress
        mProgressBar = (ProgressBar) findViewById(R.id.sign_up_progress);
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateUsername()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateConfirmationPassword()) {
            return;
        }

        // Create a user account when the form is valid
        createAccount(email, password);
    }

    private boolean validateUsername() {
        username = mUsernameEditText.getText().toString().trim();

        if (username.isEmpty()) {
            mUsernameTextInput.setError(getString(R.string.error_field_required));
            requestFocus(mUsernameEditText);
            return false;
        } else {
            mUsernameTextInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        email = mEmailEditText.getText().toString().trim();

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
        password = mPasswordEditText.getText().toString().trim();

        if (!Validation.isValidPassword(password)) {
            mPasswordTextInput.setError(getString(R.string.error_invalid_password));
            requestFocus(mPasswordEditText);
            return false;
        } else {
            mPasswordTextInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmationPassword() {
        confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        if (!Utility.doStringsMatch(password, confirmPassword)) {
            mConfirmPasswordTextInput.setError(getString(R.string.error_incorrect_password));
            requestFocus(mConfirmPasswordEditText);
            return false;
        } else {
            mConfirmPasswordTextInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Couldn't Send Verification Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void createAccount(final String email, String password) {
        showProgress(true);
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete(): onComplete(): " + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete(): Auth State: " + mFirebaseAuth.getCurrentUser().getUid());

                            sendVerificationEmail();

                            UserBean user = new UserBean();
                            user.setUserId(mFirebaseAuth.getCurrentUser().getUid());
                            user.setName(email.substring(0, email.indexOf("@")));
                            user.setPhone("1");
                            user.setPhotoUrl("");
                            user.setSecurityLevel("1");

                            mUsersDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mFirebaseAuth.signOut();
                                            redirectLoginScreen();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mFirebaseAuth.signOut();
                                    redirectLoginScreen();
                                    Toast.makeText(RegisterActivity.this, "Something, went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
                        }

                        // Hide the progress bar
                        showProgress(false);
                    }
                });
    }

    private void redirectLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Shows the progress UI and hides the registration form
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
                case R.id.username_edit_text:
                    validateUsername();
                    break;
                case R.id.email_edit_text:
                    validateEmail();
                    break;
                case R.id.password_edit_text:
                    validatePassword();
                    break;
                case R.id.confirm_password_edit_text:
                    validateConfirmationPassword();
                    break;
            }
        }
    }
}
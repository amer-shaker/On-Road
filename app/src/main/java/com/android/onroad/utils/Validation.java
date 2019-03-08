package com.android.onroad.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {

    private static final int PASSWORD_LENGTH = 10;

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPassword(String password) {
        return password.length() > PASSWORD_LENGTH;
    }
}

package com.rhcloud.papers.helpers.generic;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rodolfosantana on 27/08/17.
 */

public class hlpValidaDados {
    private final static String EXPRESSAO_REGULAR_SENHA_FORTE = "^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public final static boolean isValidEmail(CharSequence email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public final static boolean isSenhaForte(final String password) {
        Pattern p = Pattern.compile(EXPRESSAO_REGULAR_SENHA_FORTE);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}

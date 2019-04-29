package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences; // kayıtları cekmemizi saglıyor.
    private SharedPreferences.Editor editor; // kayıtları olusturur.


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("gezgin_session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLoginData(String email, String password) {
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("email","");
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}

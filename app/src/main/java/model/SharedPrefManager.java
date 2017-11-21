package model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import projeto.android.sga.sgageo.LoginActivity;

/**
 * Created by Hugo on 04/10/2017.
 */

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_IDUSR = "id";
    private static final String KEY_IDEMP = "idEmpresa";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASS = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Usuario user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_IDUSR, user.getId());
        editor.putInt(KEY_IDEMP, user.getIdEmpresa());
        editor.putString(KEY_USERNAME, user.getUsuario());
        editor.putString(KEY_PASS, user.getSenha());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getInt(KEY_IDUSR, 0),
                sharedPreferences.getInt(KEY_IDEMP, 0),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASS, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}

package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;

/**
 * Created by Jeffrey on 3/14/2017.
 */

public class SharedPrefManager {

    Context c;

    public SharedPrefManager(Context c) {
        this.c=c;
    }

    public void userSaverModel(UsersModel u){
        SharedPreferences s = c.getSharedPreferences("users", Context.MODE_PRIVATE);
        SharedPreferences.Editor x = s.edit();

        x.putInt("id", u.getId());
        x.putString("email", u.getEmail());
        x.putString("firstname",u.getFirstname());
        x.putString("lastname",u.getLastname());
        x.putString("birthdate",u.getBirthdate());
        x.putString("mobile", u.getMobile());
        x.putString("password", u.getPassword());
        x.putBoolean("isLogged", u.getLogged());
        x.commit();
    }

    public UsersModel userGet(){
        UsersModel um = new UsersModel();
        SharedPreferences s = c.getSharedPreferences("users", Context.MODE_PRIVATE);

        um.setId(s.getInt("id", 0));
        um.setEmail(s.getString("email", ""));
        um.setFirstname(s.getString("firstname", ""));
        um.setLastname(s.getString("lastname", ""));
        um.setMobile(s.getString("mobile", ""));
        um.setBirthdate(s.getString("birthdate", ""));
        um.setPassword(s.getString("password", ""));
        um.setLogged(s.getBoolean("isLogged", false));
        return um;
    }

    public void clearUser(){
        SharedPreferences s = c.getSharedPreferences("users",Context.MODE_PRIVATE);
        SharedPreferences.Editor x = s.edit();
        x.clear();
        x.commit();

    }
}

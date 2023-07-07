package com.lamvo.groupproject_flowershop.app_services;

import android.content.Context;
import android.content.SharedPreferences;

import com.lamvo.groupproject_flowershop.constants.AppConstants;

public class CredentialService {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public CredentialService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(AppConstants.DEFAULT_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Return the current userId logged in in this app.
     * Return -1 if user has not log in.
     * @return long
     */
    public long getCurrentUserId(){
        return preferences.getLong(AppConstants.CURRENT_USER_ID, -1);
    }


    /**
     * Set the current logged in userId.
     * @param id
     */
    public void setCurrentUserId(long id) {
        editor.putLong(AppConstants.CURRENT_USER_ID, id);
        editor.commit();

    }
}

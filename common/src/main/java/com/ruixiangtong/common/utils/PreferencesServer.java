package com.ruixiangtong.common.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class PreferencesServer {

    private SharedPreferences sp;
    private Editor sharedEditor;

    private static PreferencesServer wrapper;

    public static PreferencesServer getWrapper(Context cxt) {
        if (wrapper == null)
            wrapper = new PreferencesServer(cxt);
        return wrapper;
    }

    private PreferencesServer(Context aContext) {
        sp = PreferenceManager.getDefaultSharedPreferences(aContext);
        sharedEditor = sp.edit();
    }

    //Public setters

    /**
     * Set a preference string value
     *
     * @param key   the preference key to set
     * @param value the value for this key
     */
    public void setPreferenceStringValue(String key, String value) {
        if (sharedEditor == null) {
            Editor editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
        } else {
            sharedEditor.putString(key, value);
            sharedEditor.commit();
        }
    }

    /**
     * Set a preference boolean value
     *
     * @param key   the preference key to set
     * @param value the value for this key
     */
    public void setPreferenceBooleanValue(String key, boolean value) {
        if (sharedEditor == null) {
            Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } else {
            sharedEditor.putBoolean(key, value);
            sharedEditor.commit();
        }
    }

    /**
     * Set a preference float value
     *
     * @param key   the preference key to set
     * @param value the value for this key
     */
    public void setPreferenceFloatValue(String key, float value) {
        if (sharedEditor == null) {
            Editor editor = sp.edit();
            editor.putFloat(key, value);
            editor.commit();
        } else {
            sharedEditor.putFloat(key, value);
            sharedEditor.commit();
        }
    }

    /**
     * Set a preference int value
     *
     * @param key   the preference key to set
     * @param value the value for this key
     */
    public void setPreferenceIntValue(String key, int value) {
        if (sharedEditor == null) {
            Editor editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
        } else {
            sharedEditor.putInt(key, value);
            sharedEditor.commit();
        }
    }

    //Private static getters
    // For string
    public String gPrefStringValue(String key) {
        return sp.getString(key, "");
    }

    // For boolean
    public Boolean gPrefBooleanValue(String key,boolean defaultValue) {
       return sp.getBoolean(key, defaultValue);
    }
    // For int
    public int gPrefIntValue(String key) {
        return sp.getInt(key,0);
    }
    // For float
    public float gPrefFloatValue(String key) {
        return sp.getFloat(key,0);
    }
}

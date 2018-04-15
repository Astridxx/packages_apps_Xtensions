package com.xtended.xtensions.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import com.android.internal.util.gzosp.GzospUtils;

import java.util.Arrays;
import java.util.HashSet;

import com.android.settings.SettingsPreferenceFragment;

public class MiscSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String SUBS_PACKAGE = "projekt.substratum";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.settings_misc);

        boolean subsInstalled = GzospUtils.isAppInstalled(getActivity().getApplicationContext(), SUBS_PACKAGE);

        boolean enableSmartPixels = getContext().getResources().
                getBoolean(com.android.internal.R.bool.config_enableSmartPixels);
        Preference SmartPixels = findPreference("smart_pixels");

        if (!enableSmartPixels) {
            getPreferenceScreen().removePreference(SmartPixels);
        } else if (enableSmartPixels && subsInstalled) {
            SmartPixels.setSummary(R.string.substratum_detected_summary);
            SmartPixels.setEnabled(false);

            Settings.System.putIntForUser(getContentResolver(), Settings.System.SMART_PIXELS_ENABLE,
                    0, UserHandle.USER_CURRENT);
            Settings.System.putIntForUser(getContentResolver(), Settings.System.SMART_PIXELS_ON_POWER_SAVE,
                    0, UserHandle.USER_CURRENT);
        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.XTENDED;
    }
}

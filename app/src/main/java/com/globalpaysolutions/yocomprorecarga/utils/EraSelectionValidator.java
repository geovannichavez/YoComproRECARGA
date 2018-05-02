package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.ui.activities.EraSelection;

/**
 * Created by Josué Chávez on 22/2/2018.
 */

public class EraSelectionValidator
{
    private static final String TAG = EraSelectionValidator.class.getSimpleName();

    public static void checkMustReselectEra(AppCompatActivity activity, Context context, String destiny)
    {
        try
        {
            double version = Double.valueOf(VersionName.getVersionName(context, TAG));

            //If current version has feature where era reselection is needed
            if(version >= Constants.RESELECT_ERA_IN_VERSION)
            {
                //If it hasn't reselected era
                if(!UserData.getInstance(context).hasReselectedEra())
                {
                    Intent eraSelection = new Intent(activity, EraSelection.class);
                    addFlags(eraSelection);
                    eraSelection.putExtra(Constants.BUNDLE_ERA_SELECTION_INTENT_DESTINY, destiny);
                    eraSelection.putExtra(Constants.BUNDLE_ERA_RESELECTION_ACTION, true);
                    context.startActivity(eraSelection);
                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error checking era re-selection: " + ex.getMessage());
        }
    }

    private static void addFlags(Intent pIntent)
    {
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}

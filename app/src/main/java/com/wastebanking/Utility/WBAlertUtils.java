package com.wastebanking.Utility;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by user1 on 4/22/2018.
 */

public class WBAlertUtils {
        public static void showOKDialog(Context context, String title, String message)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }
}

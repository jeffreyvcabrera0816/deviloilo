package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jeffrey on 2/18/2017.
 */

public class MyToast {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Jeffrey on 3/14/2017.
 */

public class AppUtil {
    public static void hideKeyboard(EditText edittext, Context context) {
        // hide virtual keyboard
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
    }
}

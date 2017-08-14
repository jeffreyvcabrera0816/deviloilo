package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;

/**
 * Created by Jeffrey on 2/20/2017.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        try {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.Font, defStyle, 0);

            String str = a.getString(R.styleable.Font_type);
            a.recycle();
            switch (Integer.parseInt(str)) {

                case 0:
                    str = "fonts/Antonio-Light.ttf";
                    break;

                case 1:
                    str = "fonts/Antonio-Bold.ttf";
                    break;

                case 2:
                    str = "fonts/Antonio-Regular.ttf";
                    break;


                case 3:
                    str = "fonts/HelveticaNeueDeskInterface.ttf";
                    break;

                case 4:
                    str = "fonts/helvetica-neue-bold.ttf";
                    break;

                case 5:
                    str = "fonts/Helvetica_Light.ttf";
                    break;


                default:
                    break;
            }

            setTypeface(FontManager.getInstance(getContext()).loadFont(str));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("unused")
    private void internalInit(Context context, AttributeSet attrs) {

    }

    public void setBoldType()
    {
        setTypeface(FontManager.getInstance(getContext()).loadFont("fonts/Antonio-Bold.ttf"));
    }

    public void setLightType()
    {
        setTypeface(FontManager.getInstance(getContext()).loadFont("fonts/Antonio-Light.ttf"));
    }

    public void setRegularType()
    {
        setTypeface(FontManager.getInstance(getContext()).loadFont("fonts/Antonio-Regular.ttf"));
    }
}

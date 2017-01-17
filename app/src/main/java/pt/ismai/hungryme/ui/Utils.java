package pt.ismai.hungryme.ui;

import android.app.Activity;
import android.content.Intent;

import pt.ismai.hungryme.R;


public class Utils
{
    private static int sTheme;
    public final static int THEME_BLUE = 0;
    public final static int THEME_RED = 1;
    public final static int THEME_GREEN = 2;

    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_BLUE:
                activity.setTheme(R.style.Base_Theme);
                break;
            case THEME_RED:
                activity.setTheme(R.style.Base_Theme2);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.Base_Theme3);
                break;
        }
    }
}
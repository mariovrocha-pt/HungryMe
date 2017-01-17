package pt.ismai.hungryme.SplashAndWelcomeScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragments extends Fragment {

    final static String LAYOUT_ID = "layoutId";

    public static WelcomeFragments newInstance(int layoutId) {
        WelcomeFragments pane = new WelcomeFragments();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(bundle);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return root;
    }
}
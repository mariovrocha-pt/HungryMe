package pt.ismai.hungryme.ui.UI;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

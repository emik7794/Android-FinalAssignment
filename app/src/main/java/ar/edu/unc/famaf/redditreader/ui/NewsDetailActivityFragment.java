package ar.edu.unc.famaf.redditreader.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.unc.famaf.redditreader.R;

public class NewsDetailActivityFragment extends Fragment {

    public NewsDetailActivityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.post_detail, container, false);
    }
}

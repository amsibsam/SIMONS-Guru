package com.monitoringsiswa.monitoringsiswa.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monitoringsiswa.monitoringsiswa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputPelanggaranFragment extends android.support.v4.app.Fragment {


    public InputPelanggaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_pelanggaran, container, false);
    }

}

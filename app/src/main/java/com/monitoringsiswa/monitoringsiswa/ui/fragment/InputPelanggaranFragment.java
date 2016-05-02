package com.monitoringsiswa.monitoringsiswa.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.monitoringsiswa.monitoringsiswa.MonitoringApplication;
import com.monitoringsiswa.monitoringsiswa.databinding.FragmentInputPelanggaranBinding;
import com.monitoringsiswa.monitoringsiswa.module.AccountInfoStore;
import com.monitoringsiswa.monitoringsiswa.network.MonitoringService;
import com.monitoringsiswa.monitoringsiswa.pojo.KategoriPelanggaran;
import com.monitoringsiswa.monitoringsiswa.pojo.PoinPelanggaran;
import com.monitoringsiswa.monitoringsiswa.ui.adapter.KategoriSpinnerAdapter;
import com.monitoringsiswa.monitoringsiswa.ui.adapter.PoinSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputPelanggaranFragment extends android.support.v4.app.Fragment {
    private KategoriSpinnerAdapter kategoriAdapter;
    private PoinSpinnerAdapter poinAdapter;
    private long kategoriId;
    private long poinId;
    private static HomeFragment homeFragmentData;

    @Inject
    MonitoringService monitoringService;

    @Inject
    AccountInfoStore accountInfoStore;

    private FragmentInputPelanggaranBinding binding;



    public InputPelanggaranFragment() {
        // Required empty public constructor
    }

    public static InputPelanggaranFragment newInstance(HomeFragment homeFragment) {
        Bundle args = new Bundle();

        homeFragmentData = homeFragment;

        InputPelanggaranFragment fragment = new InputPelanggaranFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonitoringApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputPelanggaranBinding.inflate(inflater, container, false);

        initKategoriSpinner();

        binding.spinnerKategoriPelanggaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("amsibsam", "id kategori : "+kategoriAdapter.getItemId(position));
                initPoinPelanggaranSpinner(position);
                kategoriId = kategoriAdapter.getItemId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPelanggaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                poinId = poinAdapter.getItemId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guruId = accountInfoStore.getGuruAccount().getId();
                posPelanggaran(binding.etCatatan.getText().toString(), binding.etNomerInduk.getText().toString(), guruId, Integer.parseInt(String.valueOf(poinId)));
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initKategoriSpinner(){
        monitoringService.getKategoriPelanggaran()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Subscriber<List<KategoriPelanggaran>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error getKategori "+e.toString());
                    }

                    @Override
                    public void onNext(List<KategoriPelanggaran> kategoriPelanggaren) {
                        List<KategoriPelanggaran> tempList = new ArrayList<KategoriPelanggaran>();
                        tempList.add(0, new KategoriPelanggaran(0, "Pilih kategori"));
                        tempList.addAll(kategoriPelanggaren);
                        kategoriAdapter = new KategoriSpinnerAdapter(tempList, getContext());
                        binding.spinnerKategoriPelanggaran.setAdapter(kategoriAdapter);
                        kategoriAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initPoinPelanggaranSpinner(int position){
        monitoringService.getPoinPelanggaran((int) kategoriAdapter.getItemId(position))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe(new Subscriber<List<PoinPelanggaran>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PoinPelanggaran> poinPelanggaren) {
                        List<PoinPelanggaran> tempList = new ArrayList<PoinPelanggaran>();
                        tempList.add(0, new PoinPelanggaran(0, "Pilih Pelanggaran", 0, 0));
                        tempList.addAll(poinPelanggaren);

                        poinAdapter = new PoinSpinnerAdapter(tempList, getContext());
                        binding.spinnerPelanggaran.setAdapter(poinAdapter);
                        poinAdapter.notifyDataSetChanged();

                        
                    }
                });
    }

    private void posPelanggaran(String catatan, String nis, int guruId, int poinPelanggaran){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mensubmit..");
        progressDialog.show();
        monitoringService.postPelanggaran(catatan, nis, guruId, poinPelanggaran)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(String s) {
                        progressDialog.dismiss();
                        homeFragmentData.getPelanggaran();
                        resetInput();
                        Toast.makeText(getActivity(), "Berhasil submit", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetInput(){
        binding.etNomerInduk.setText("");
        binding.spinnerKategoriPelanggaran.setSelection(0);
        binding.spinnerPelanggaran.setSelection(0);
        binding.etCatatan.setText("");
    }

}

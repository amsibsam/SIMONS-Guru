package com.monitoringsiswa.monitoringguru.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.monitoringsiswa.monitoringguru.pojo.Pelanggaran;

/**
 * Created by rahardyan on 24/04/16.
 */
public class PelanggaranViewModel extends BaseObservable{
    private Pelanggaran pelanggaran;
    private View.OnClickListener pelanggaranDetailClickListener;

    public PelanggaranViewModel(Pelanggaran pelanggaran, View.OnClickListener pelanggaranDetailClickListener) {
        this.pelanggaran = pelanggaran;
        this.pelanggaranDetailClickListener = pelanggaranDetailClickListener;
    }

    @Bindable
    public String getName() {
        return pelanggaran.getNamaSiswa();
    }

    @Bindable
    public String getTanggal() {
        return pelanggaran.getTanngal();
    }

    @Bindable
    public String getKelas(){
        return pelanggaran.getKelas();
    }

    @Bindable
    public String getPoin(){
        return String.valueOf(pelanggaran.getPoin());
    }

    @Bindable
    public String getNamaPelanggaran(){
        return pelanggaran.getNamaPoinPelanggaran();
    }
}

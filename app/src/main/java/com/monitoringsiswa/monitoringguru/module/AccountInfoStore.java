package com.monitoringsiswa.monitoringguru.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.monitoringsiswa.monitoringguru.pojo.Guru;

/**
 * Created by rahardyan on 24/04/16.
 */
public class AccountInfoStore {
    private static final String TAG = AccountInfoStore.class.getSimpleName();

    private final SharedPreferences sharedPreferences;

    public AccountInfoStore(Context context){
        this.sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public void cacheAccountInfo(Guru guru){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", guru.getId());
        editor.putString("username", guru.getUsername());
        editor.putString("name", guru.getNamaGuru());
        editor.putString("jabatan", guru.getJabatan());
        editor.putString("nip", guru.getNip());
        editor.putString("no_hp", guru.getNomorHp());
        editor.putString("jenis_kelamin", guru.getJenisKelamin());
    }

    public Guru getGuruAccount(){
        int id = sharedPreferences.getInt("id", 1);
        String nip = sharedPreferences.getString("nip", "");
        String name = sharedPreferences.getString("name", "");
        String jabatan = sharedPreferences.getString("jabatan", "");
        String noHp = sharedPreferences.getString("no_hp","");
        String jenisKelamin = sharedPreferences.getString("jenis_kelamin","");
        String username = sharedPreferences.getString("username","");
        return new Guru(id, nip, name, jenisKelamin, noHp, jabatan, username);
    }
}

package com.monitoringsiswa.monitoringguru.pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class Guru {
    private final int id;
    private final String nip;
    private final String namaGuru;
    private final String jenisKelamin;
    private final String nomorHp;
    private final String jabatan;
    private final String username;

    public Guru(int id, String nip, String namaGuru, String jenisKelamin, String nomorHp, String jabatan, String username) {
        this.id = id;
        this.nip = nip;
        this.namaGuru = namaGuru;
        this.jenisKelamin = jenisKelamin;
        this.nomorHp = nomorHp;
        this.jabatan = jabatan;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getUsername() {
        return username;
    }
}

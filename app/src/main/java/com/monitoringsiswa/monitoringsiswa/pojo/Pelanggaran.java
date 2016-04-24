package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class Pelanggaran {
    private final int id;
    private final String tanngal;
    private final int idSiswa;
    private final int nis;
    private final String namaSiswa;
    private final String kelas;
    private final String namaPoinPelanggaran;
    private final int poin;

    public Pelanggaran(int id, String tanngal, int idSiswa, int nis, String namaSiswa, String kelas, String namaPoinPelanggaran, int poin) {
        this.id = id;
        this.tanngal = tanngal;
        this.idSiswa = idSiswa;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.kelas = kelas;
        this.namaPoinPelanggaran = namaPoinPelanggaran;
        this.poin = poin;
    }

    public int getId() {
        return id;
    }

    public String getTanngal() {
        return tanngal;
    }

    public int getIdSiswa() {
        return idSiswa;
    }

    public int getNis() {
        return nis;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public String getKelas() {
        return kelas;
    }

    public String getNamaPoinPelanggaran() {
        return namaPoinPelanggaran;
    }

    public int getPoin() {
        return poin;
    }
}

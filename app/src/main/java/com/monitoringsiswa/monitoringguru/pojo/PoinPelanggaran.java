package com.monitoringsiswa.monitoringguru.pojo;

/**
 * Created by rahardyan on 29/04/16.
 */
public class PoinPelanggaran {
    private final int id;
    private final String namaPoinPelanggaran;
    private final int poin;
    private final int kategoriId;

    public PoinPelanggaran(int id, String namaPoinPelanggaran, int poin, int kategoriId) {
        this.id = id;
        this.namaPoinPelanggaran = namaPoinPelanggaran;
        this.poin = poin;
        this.kategoriId = kategoriId;
    }

    public int getId() {
        return id;
    }

    public String getNamaPoinPelanggaran() {
        return namaPoinPelanggaran;
    }

    public int getPoin() {
        return poin;
    }

    public int getKategoriId() {
        return kategoriId;
    }
}

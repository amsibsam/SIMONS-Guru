package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 29/04/16.
 */
public class KategoriPelanggaran {
    private final int id;
    private final String namaKategoriPelanggaran;

    public KategoriPelanggaran(int id, String namaKategoriPelanggaran) {
        this.namaKategoriPelanggaran = namaKategoriPelanggaran;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamaKategoriPelanggaran() {
        return namaKategoriPelanggaran;
    }
}

package com.jps.kasmart_mobile;

public class SpinnerKegiatan {

    public int id;
    public String kegiatan,jenis;

    public SpinnerKegiatan(int id, String kegiatan, String jenis) {
        this.id = id;
        this.kegiatan = kegiatan;
        this.jenis = jenis;
    }

    @Override
    public String toString() {
        return kegiatan;
    }
}

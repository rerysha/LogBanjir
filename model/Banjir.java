package model;

public class Banjir {
    private String lokasi;
    private String tanggal;
    private int kedalaman;

    public Banjir(String lokasi, String tanggal, int kedalaman) {
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.kedalaman = kedalaman;
    }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public int getKedalaman() { return kedalaman; }
    public void setKedalaman(int kedalaman) { this.kedalaman = kedalaman; }
}
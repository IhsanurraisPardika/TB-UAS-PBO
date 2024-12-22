// Kelas induk
class Pengguna {
    protected String nama;
    protected int umur;
    protected double beratBadan;
    protected double tinggiBadan;

    public Pengguna(String nama, int umur, double beratBadan, double tinggiBadan) {
        this.nama = nama;
        this.umur = umur;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
    }

    public double hitungBMR() {
        // Rumus BMR sederhana untuk contoh
        return 10 * beratBadan + 6.25 * tinggiBadan - 5 * umur + 5;
    }

    @Override
    public String toString() {
        return "Nama: " + nama + ", Umur: " + umur + ", Berat Badan: " + beratBadan + "kg, Tinggi Badan: " + tinggiBadan + "cm";
    }
}

// Kelas turunan
class PenggunaAktif extends Pengguna {
    private double faktorAktivitas;

    public PenggunaAktif(String nama, int umur, double beratBadan, double tinggiBadan, double faktorAktivitas) {
        super(nama, umur, beratBadan, tinggiBadan);
        this.faktorAktivitas = faktorAktivitas;
    }

    public double hitungKebutuhanKalori() {
        return hitungBMR() * faktorAktivitas;
    }
}
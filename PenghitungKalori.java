// Kelas Penghitung Nutrisi

import java.util.ArrayList;
import java.util.List;

class PenghitungKalori {
    private List<String> logMakanan = new ArrayList<>();
    private double totalKalori = 0;

    public void tambahMakanan(String namaMakanan, double kalori) {
        logMakanan.add(namaMakanan + ": " + kalori + " kkal");
        totalKalori += kalori;
    }

    public void tampilkanLogMakanan() {
        System.out.println("Log Makanan:");
        for (String makanan : logMakanan) {
            System.out.println(makanan);
        }
        System.out.println("Total Kalori: " + totalKalori + " kkal");
    }
}
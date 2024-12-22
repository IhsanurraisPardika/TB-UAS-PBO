// Implementasi Interface
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

class PenghitungNutrisii implements NutrisiInterface {
    private List<String> logMakanan = new ArrayList<>();
    private double totalKalori = 0;

    @Override
    public void tambahMakanan(String namaMakanan, double kalori) {
        String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logMakanan.add(waktu + " - " + namaMakanan + ": " + kalori + " kkal");
        totalKalori += kalori;
    }

    @Override
    public void tampilkanLogMakanan() {
        System.out.println("Log Makanan:");
        for (String makanan : logMakanan) {
            System.out.println(makanan);
        }
        System.out.println("Total Kalori: " + totalKalori + " kkal");
    }
}
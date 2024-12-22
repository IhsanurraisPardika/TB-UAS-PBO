# TB-UAS-PBO
Nama: Ihsanurrais Pardika
NIM: 2311523031

// Interface
interface NutrisiInterface {
    void tambahMakanan(String namaMakanan, double kalori);
    void tampilkanLogMakanan();
}

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

// Kelas Penghitung Nutrisi

import java.util.ArrayList;
import java.util.List;

class PenghitungNutrisi {
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

import java.sql.*;
import java.util.Scanner;

public class AplikasiManajemenNutrisi {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrisi";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Username dan password login hardcoded
    private static final String LOGIN_USERNAME = "admin";
    private static final String LOGIN_PASSWORD = "12345";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private static void buatTabelPengguna() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS pengguna (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "nama VARCHAR(50), " +
                         "umur INT, " +
                         "berat_badan DOUBLE, " +
                         "tinggi_badan DOUBLE, " +
                         "faktor_aktivitas DOUBLE)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Kesalahan membuat tabel: " + e.getMessage());
        }
    }

    private static void tambahPengguna(String nama, int umur, double beratBadan, double tinggiBadan, double faktorAktivitas) {
        String sql = "INSERT INTO pengguna (nama, umur, berat_badan, tinggi_badan, faktor_aktivitas) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setInt(2, umur);
            pstmt.setDouble(3, beratBadan);
            pstmt.setDouble(4, tinggiBadan);
            pstmt.setDouble(5, faktorAktivitas);
            pstmt.executeUpdate();
            System.out.println("Pengguna berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Kesalahan menambah pengguna: " + e.getMessage());
        }
    }

    private static void bacaPengguna() {
        String sql = "SELECT * FROM pengguna";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nama: " + rs.getString("nama"));
                System.out.println("Umur: " + rs.getInt("umur"));
                System.out.println("Berat Badan: " + rs.getDouble("berat_badan"));
                System.out.println("Tinggi Badan: " + rs.getDouble("tinggi_badan"));
                System.out.println("Faktor Aktivitas: " + rs.getDouble("faktor_aktivitas"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan membaca pengguna: " + e.getMessage());
        }
    }

    private static void lihatBMR() {
        String sql = "SELECT id, nama, umur, berat_badan, tinggi_badan FROM pengguna";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                int umur = rs.getInt("umur");
                double beratBadan = rs.getDouble("berat_badan");
                double tinggiBadan = rs.getDouble("tinggi_badan");

                Pengguna pengguna = new Pengguna(nama, umur, beratBadan, tinggiBadan);
                double bmr = pengguna.hitungBMR();

                System.out.println("ID: " + id);
                System.out.println("Nama: " + nama);
                System.out.println("BMR: " + bmr + " kkal/hari");
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan menghitung BMR: " + e.getMessage());
        }
    }

    private static void updatePengguna(int id, String nama, int umur, double beratBadan, double tinggiBadan, double faktorAktivitas) {
        String sql = "UPDATE pengguna SET nama = ?, umur = ?, berat_badan = ?, tinggi_badan = ?, faktor_aktivitas = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setInt(2, umur);
            pstmt.setDouble(3, beratBadan);
            pstmt.setDouble(4, tinggiBadan);
            pstmt.setDouble(5, faktorAktivitas);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Pengguna berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Kesalahan memperbarui pengguna: " + e.getMessage());
        }
    }

    private static void hapusPengguna(int id) {
        String sql = "DELETE FROM pengguna WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Pengguna berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Kesalahan menghapus pengguna: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            buatTabelPengguna();

            // Halaman Login
            boolean isLoggedIn = false;
            while (!isLoggedIn) {
                System.out.print("Masukkan username: ");
                String inputUsername = scanner.nextLine();
                System.out.print("Masukkan password: ");
                String inputPassword = scanner.nextLine();

                if (LOGIN_USERNAME.equals(inputUsername) && LOGIN_PASSWORD.equals(inputPassword)) {
                    System.out.println("Login berhasil. Selamat datang, " + "Ihsan" + "!");
                    isLoggedIn = true;
                } else {
                    System.out.println("Login gagal. Username atau password salah. Coba lagi.");
                }
            }

            // Menu utama setelah login berhasil
            boolean berjalan = true;
            while (berjalan) {
                System.out.println("\nMenu Pengelolaan Pengguna");
                System.out.println("1. Tambah Pengguna");
                System.out.println("2. Lihat Pengguna");
                System.out.println("3. Perbarui Pengguna");
                System.out.println("4. Hapus Pengguna");
                System.out.println("5. Lihat Hasil Perhitungan BMR");
                System.out.println("6. Keluar");
                System.out.print("Pilih menu: ");

                int pilihan = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer

                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan nama: ");
                        String nama = scanner.nextLine();
                        System.out.print("Masukkan umur: ");
                        int umur = scanner.nextInt();
                        System.out.print("Masukkan berat badan (kg): ");
                        double beratBadan = scanner.nextDouble();
                        System.out.print("Masukkan tinggi badan (cm): ");
                        double tinggiBadan = scanner.nextDouble();
                        System.out.print("Masukkan faktor aktivitas: ");
                        double faktorAktivitas = scanner.nextDouble();
                        tambahPengguna(nama, umur, beratBadan, tinggiBadan, faktorAktivitas);
                        break;
                    case 2:
                        bacaPengguna();
                        break;
                    case 3:
                        System.out.print("Masukkan ID pengguna yang akan diperbarui: ");
                        int idUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan nama baru: ");
                        String namaBaru = scanner.nextLine();
                        System.out.print("Masukkan umur baru: ");
                        int umurBaru = scanner.nextInt();
                        System.out.print("Masukkan berat badan baru (kg): ");
                        double beratBaru = scanner.nextDouble();
                        System.out.print("Masukkan tinggi badan baru (cm): ");
                        double tinggiBaru = scanner.nextDouble();
                        System.out.print("Masukkan faktor aktivitas baru: ");
                        double faktorBaru = scanner.nextDouble();
                        updatePengguna(idUpdate, namaBaru, umurBaru, beratBaru, tinggiBaru, faktorBaru);
                        break;
                    case 4:
                        System.out.print("Masukkan ID pengguna yang akan dihapus: ");
                        int idHapus = scanner.nextInt();
                        hapusPengguna(idHapus);
                        break;
                    case 5:
                        lihatBMR();
                        break;
                    case 6:
                        berjalan = false;
                        System.out.println("Terima kasih telah menggunakan aplikasi.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Coba lagi.");
                }
            }
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}

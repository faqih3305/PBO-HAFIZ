import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

class Barang {
    protected String kodeBarang;
    protected String namaBarang;
    protected double hargaBarang;

    public Barang(String kodeBarang, String namaBarang, double hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    public double hitungTotal(int jumlahBeli) {
        return jumlahBeli * hargaBarang;
    }
}

// Subclass: Transaksi (Inheritance)
class Transaksi extends Barang {
    private String noFaktur;

    public Transaksi(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang) {
        super(kodeBarang, namaBarang, hargaBarang); // Memanggil constructor superclass
        this.noFaktur = noFaktur;
    }

    public void tampilkanTransaksi(int jumlahBeli, String namaKasir) {
        double total = hitungTotal(jumlahBeli);
        String waktuSekarang = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        System.out.println("\n+----------------------------------------------------+");
        System.out.println("Selamat Datang di Supermarket Sejahtera");
        System.out.println("Tanggal dan Waktu : " + waktuSekarang);
        System.out.println("+----------------------------------------------------+");
        System.out.println("No. Faktur      : " + noFaktur);
        System.out.println("Kode Barang  : " + kodeBarang);
        System.out.println("Nama Barang : " + namaBarang);
        System.out.println("Harga Barang: Rp " + hargaBarang);
        System.out.println("Jumlah Beli     : " + jumlahBeli);
        System.out.println("TOTAL             : Rp " + total);
        System.out.println("+----------------------------------------------------+");
        System.out.println("Kasir : " + namaKasir);
        System.out.println("+----------------------------------------------------+");
    }
}

// Custom Exception
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// Main Class
public class MainProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean loginBerhasil = false;

        try {
            // Fitur Login
            while (!loginBerhasil) {
                System.out.println("+-----------------------------------------------------+");
                System.out.print("Username : ");
                String username = scanner.nextLine().trim();

                System.out.print("Password  : ");
                String password = scanner.nextLine().trim();

                // Generate captcha
                String captcha = String.valueOf(random.nextInt(9000) + 1000); // Generate 4-digit captcha
                System.out.println("Captcha    : " + captcha);
                System.out.print("Masukkan Captcha: ");
                String inputCaptcha = scanner.nextLine().trim();

                if (!captcha.equals(inputCaptcha)) {
                    System.out.println("Captcha salah! Silakan coba lagi.");
                    continue;
                }

                // Validasi login
                if (username.equalsIgnoreCase("admin") && password.equals("admin123")) {
                    System.out.println("Login berhasil!");
                    loginBerhasil = true;
                } else {
                    System.out.println("Username atau Password salah. Silakan coba lagi.");
                }
            }

            // Memulai Transaksi
            System.out.print("Masukkan Nama Kasir: ");
            String namaKasir = scanner.nextLine().trim();
            if (namaKasir.isEmpty()) {
                throw new InvalidInputException("Nama Kasir tidak boleh kosong!");
            }

            System.out.print("Masukkan No Faktur: ");
            String noFaktur = scanner.nextLine().trim();
            if (noFaktur.isEmpty()) {
                throw new InvalidInputException("No Faktur tidak boleh kosong!");
            }

            System.out.print("Masukkan Kode Barang: ");
            String kodeBarang = scanner.nextLine().trim();
            if (kodeBarang.isEmpty()) {
                throw new InvalidInputException("Kode Barang tidak boleh kosong!");
            }

            System.out.print("Masukkan Nama Barang: ");
            String namaBarang = scanner.nextLine().trim();
            if (namaBarang.isEmpty()) {
                throw new InvalidInputException("Nama Barang tidak boleh kosong!");
            }

            System.out.print("Masukkan Harga Barang: ");
            if (!scanner.hasNextDouble()) {
                throw new InvalidInputException("Harga Barang harus berupa angka!");
            }
            double hargaBarang = scanner.nextDouble();
            if (hargaBarang <= 0) {
                throw new InvalidInputException("Harga Barang harus lebih dari 0!");
            }

            // Membersihkan buffer newline setelah nextDouble()
            scanner.nextLine();

            System.out.print("Masukkan Jumlah Beli: ");
            if (!scanner.hasNextInt()) {
                throw new InvalidInputException("Jumlah Beli harus berupa angka!");
            }
            int jumlahBeli = scanner.nextInt();
            if (jumlahBeli <= 0) {
                throw new InvalidInputException("Jumlah Beli harus lebih dari 0!");
            }

            // Membuat objek transaksi
            Transaksi transaksi = new Transaksi(noFaktur, kodeBarang, namaBarang, hargaBarang);
            transaksi.tampilkanTransaksi(jumlahBeli, namaKasir);

        } catch (InvalidInputException e) {
            System.out.println("Input Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

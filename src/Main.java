import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập số giây: ");
        int tongGiay = sc.nextInt();

        int gio = tongGiay / 3600;
        int phut = (tongGiay % 3600) / 60;
        int giay = tongGiay % 60;

        System.out.println("Số Giờ : " + gio);
        System.out.println("Số Phút : " + phut);
        System.out.println("Số Giây : " + giay);

        sc.close();
    }
}
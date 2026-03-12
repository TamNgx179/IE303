package Lab1;

import java.util.Random;
import java.util.Scanner;

public class Bai2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Nhập số lần lấy các điểm bên trong hình vuông: ");
        int n = sc.nextInt();

        int inside = 0; // Lưu số lần các điểm nằm trong hình tròn

        for (int i = 0; i < n; i++) {
            // Sinh ngẫu nhiên tọa độ điểm trong hình vuông
            double x = -1 + 2 * 1 * random.nextDouble();
            double y = -1 + 2 * 1 * random.nextDouble();

            // Kiểm tra điểm có nằm trong hình tròn hay không
            if (x * x + y * y <= 1) {
                inside++;
            }
        }

        double pi = 4.0 * inside / n;
        System.out.println("Giá trị xấp xỉ của PI = " + pi);

        sc.close();
    }
}
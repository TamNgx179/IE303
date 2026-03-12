package Lab1;

import java.util.Random;
import java.util.Scanner;

public class Bai1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Nhập bán kinh r: ");
        double r = sc.nextDouble();

        System.out.print("Nhập số lần lấy các điểm bên trong hình vuông: ");
        int n = sc.nextInt();

        int inside = 0; // Lưu số lần các điểm nằm trong hình tròn

        for (int i = 0; i < n; i++) {
            // Sinh ngẫu nhiên tọa độ điểm trong hình vuông
            double x = -r + 2 * r * random.nextDouble();
            double y = -r + 2 * r * random.nextDouble();

            // Kiểm tra điểm có nằm trong hình tròn hay không
            if (x * x + y * y <= r * r) {
                inside++;
            }
        }

        double squareArea = (2 * r) * (2 * r);
        double circleArea = ((double) inside / n) * squareArea;

        System.out.println("Diện tích hình tròn xấp xỉ = " + circleArea);

        sc.close();
    }
}
package Lab1;

import java.util.Scanner;

public class Bai4 {

    static int n, k;
    static int[] a;

    static int[] current = new int[100];
    static int currentLength = 0;

    static int[] best = new int[100];
    static int bestLength = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();

        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        backtrack(0, 0);

        if (bestLength == 0) {
            System.out.println("Không có dãy con nào");
        } else {
            for (int i = 0; i < bestLength; i++) {
                System.out.print(best[i]);
                if (i < bestLength - 1) {
                    System.out.print(" ");
                }
            }
        }
    }

    static void backtrack(int index, int sum) {
        if (sum == k) {
            if (currentLength > bestLength) {
                bestLength = currentLength;
                for (int i = 0; i < currentLength; i++) {
                    best[i] = current[i];
                }
            }
            return;
        }

        if (index == n || sum > k) {
            return;
        }

        // Chọn a[index]
        current[currentLength] = a[index];
        currentLength++;
        backtrack(index + 1, sum + a[index]);

        // Bỏ chọn a[index]
        currentLength--;
        backtrack(index + 1, sum);
    }
}
package Lab1;

import java.util.Scanner;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Bai3 {
    static Point p0; // điểm gốc
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        int start = findLowestPoint(points, n);
        swap(points, 0, start);

        p0 = points[0];

        sortPoints(points, n);

        Point[] hull = grahamScan(points, n);

        for (int i = 0; i < hullSize; i++) {
            System.out.println(hull[i].x + " " + hull[i].y);
        }
    }

    static int hullSize;

    // Tìm điểm có y nhỏ nhất
    static int findLowestPoint(Point[] p, int n) {
        int min = 0;

        for (int i = 1; i < n; i++) {
            if (p[i].y < p[min].y || (p[i].y == p[min].y && p[i].x < p[min].x)) {
                min = i;
            }
        }

        return min;
    }

    // Sắp xếp theo góc với p0
    static void sortPoints(Point[] p, int n) {

        for (int i = 1; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {

                int c = cross(p0, p[i], p[j]);

                if (c < 0 || (c == 0 && distance(p0, p[i]) > distance(p0, p[j]))) {
                    swap(p, i, j);
                }
            }
        }
    }

    // Graham Scan
    static Point[] grahamScan(Point[] p, int n) {

        Point[] stack = new Point[n];
        int top = -1;

        stack[++top] = p[0];
        stack[++top] = p[1];
        stack[++top] = p[2];

        for (int i = 3; i < n; i++) {

            while (top >= 1 && cross(stack[top - 1], stack[top], p[i]) <= 0) {
                top--;
            }

            stack[++top] = p[i];
        }

        hullSize = top + 1;
        return stack;
    }

    // tích có hướng
    static int cross(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    // khoảng cách bình phương
    static int distance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    // đổi chỗ
    static void swap(Point[] p, int i, int j) {
        Point temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }
}
package Assignment1.JAVA;

import java.util.Scanner;

public class Factorial {
    public static int recursive(int n){
        if (n == 0 || n==1){
            return 1;
        }
        return n * recursive(n-1);
    }

    public static int iterative(int n){
        int fact = 1;
        for (int i=2; i<=n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(recursive(n));
        System.out.println(iterative(n));
    }
}




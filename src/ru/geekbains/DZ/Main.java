package ru.geekbains.DZ;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        final int size = 10000000;
        float[] arr = new float[size];

        Process(arr, size);
        SplitProcess(arr, size);
    }


    public static void Process(float[] arr, int size) {

        long start = System.currentTimeMillis();

        Arrays.fill(arr, 1);
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long itTook = System.currentTimeMillis() - start;
        System.out.println("It took " + itTook + " milliseconds to process the array as one");
    }

    public static void SplitProcess(float[] arr, int size) {
        final int h = size / 2;

        Arrays.fill(arr, 1);

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> {
            calculate(a1);
        });

        Thread t2 = new Thread(() -> {
            calculate(a2);
        });
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {
            //do nothing
        }
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long itTook = System.currentTimeMillis() - start;
        System.out.println("It took " + itTook + " milliseconds to process the array as two halves");
    }

    public static void calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

}

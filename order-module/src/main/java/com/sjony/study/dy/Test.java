package com.sjony.study.dy;

import com.sun.tools.javac.comp.Lower;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import sun.plugin2.main.server.ClientJVMSelectionParameters;

import java.awt.color.CMMException;

public class Test {

    public static void main(String[] args) {
        /*int[] arr = new int[]{3, 1, 5, 4, 2, 7, 8};
        quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }*/

        System.out.println(getAllGroupNum(5));
    }

    public static int getAllGroupNum(int num) {
        int count = 0;
        for(int i=3; i<num; i++) {
            count+=getGroupNum(1, i, num, i, num);
        }
        return count;
    }

    /*
    *@param current
    *
    *
     */
    public static int getGroupNum(int 当前需要减的数, int 分母初始数, int 分子, int 分母, int 分子初始数) {

        int count = 0;
        if(当前需要减的数 < 分母初始数) {
            分子 = 分子 * (分子初始数 - 当前需要减的数);
            分母 = 分母 * (分母初始数 - 当前需要减的数);
            当前需要减的数++;
            count = getGroupNum(当前需要减的数, 分母初始数,  分子, 分母, 分子初始数);
        }
        if(count == 0) {
            count = 分子/分母;
        }
        return count;
    }







    public static int getMax(Integer m, Integer n) {

        int[] arr;

        //入参校验
        if (null == m || null == n) {
            return -1;
        }

        if (m > n) {
            return m;
        } else {
            return n;
        }

    }


    public static void bubbleSort(int[] arr) {

        //入参校验
        if (null == arr || 0 == arr.length) {
            return;
        }

        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void quickSort(int[] arr, int low, int high) {

        //入参校验
        if (null == arr || 0 == arr.length) {
            return;
        }

        if (low > high) {
            return;
        }

        int temp = arr[low];
        int i = low, j = high;
        int t;

        while (i < j) {

            while (arr[j] > temp && j > i) {
                j--;
            }
            while (arr[i] <= temp && j >= i) {
                i++;
            }

            if (i < j) {
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        arr[low] = arr[j];
        arr[j] = temp;

        quickSort(arr, low, j - 1);
        quickSort(arr, j + 1, high);

    }




    //在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。 完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否包含该整数;

    public static int findX(int[] arr, int x) {
        int min = 0;
        int max = arr.length;
        while (min < max) {
            int i = (max + min) / 2;
            if (x > arr[i]) {
                min = i + 1;
            } else if (x < arr[i]) {
                max = i - 1;
            } else {
                return arr[i];
            }
        }
        System.out.println("未找到");
        return -1;
    }
}

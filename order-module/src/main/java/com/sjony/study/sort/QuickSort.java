package com.sjony.study.sort;

import java.util.Arrays;

/**
 * Created by sjony on 2018/2/24.
 */
public class QuickSort {

    public static void main(String []args){
        int []arr = {9,8,7,6,5,4,3,2,1};
        sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr, int start, int end) {

        int portion = partition(arr, 0, arr.length-1);
        sort(arr, 0, portion);
        sort(arr, portion+1, arr.length-1);
    }

    public static int partition(int[] arr, int min, int max) {

        int temp = arr[min];
        int left = min;
        int right = max;

        //循环数组
        while(left < right) {

            //从左向右
            while(arr[left] <= temp && left < right) {
                left++;
            }
            if(arr[left] > temp) {
                swap(arr, left, min);
            }

            //从右向左
            while(arr[right] >= temp && right >= left) {
                right--;
            }

            if(arr[right] < temp) {
                swap(arr, right, min);
            }

        }

        if(left < right) {
            swap(arr, right, min);
        }
        return right;
    }

    /**
     * 交换元素
     * @param arr
     * @param a
     * @param b
     */
    public static void swap(int []arr,int a ,int b){
        int temp=arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}

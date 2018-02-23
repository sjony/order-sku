package com.sjony.study.sort;

import java.util.Arrays;

/**
 * Created by sjony on 2018/2/23.
 */
public class MyHeapSort {


    public static void main(String []args){
        int []arr = {9,8,7,6,1,4,3,2,5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {

        //先进行大跟堆排列
        for(int i=arr.length/2-1; i>=0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        //交换数据，对下面的数，再次进行大根堆排列
        for(int i=arr.length-1; i>=0; i--) {
            swap(arr, i, 0);
            adjustHeap(arr, 0, i);
        }

    }

    public static void adjustHeap(int[] arr, int i, int length) {

        //当前节点数
        int temp = arr[i];

        //比较节点子节点数
        for(int k=i*2+1;k<length;k=k*2+1) {
            if(k+1<length && arr[k] < arr[k+1]) {
                k++;
            }
            if(arr[k] >temp){//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }

        arr[i] = temp;//将temp值放到最终的位置

    }

    /**
     * @Description:交换数组
     * @Create on: 2018/2/23 下午7:47
     * @author jshu
     *
     * @param arr
     * @param i
     * @param j
     *
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}

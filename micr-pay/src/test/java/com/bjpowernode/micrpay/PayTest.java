package com.bjpowernode.micrpay;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Scanner;

public class PayTest {
    @Test
    public void url(){

    }

    public static void main(String[] args) {
        String ps = "19944170403";
        String PassWorld = DigestUtils.md5Hex(ps);
        System.out.println(ps);
        System.out.print(PassWorld);
        //System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序
     */
    public static int[] BubbleSort(int[] arr){
        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i < arr.length - 1 -j; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
        return arr;
    }
    /**
     * 两数之和
     */
    public static int[] towSum(int[] nums, int target){
        for (int i = 0; i <= nums.length; i++){
            for (int j = i+1; j<= nums.length; j++ ){
                if (nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
    /**
     * 买股票的最佳时机（贪心算法）[7,1,5,3,6,4]
     */
    public static int[] stock(int[] prices){
        for (int i = prices.length; i > 0; i--){
            if (prices[i] > prices[i+1]){

            }
        }
        return prices;
    }

    /**
     * 回文数
     */
    public static boolean isPalindrome(int x){
        if (x < 0 ){
            return false;
        }
        int reversed = 0;
        //反转后半部分数字
        while ( x > reversed){
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        return x == reversed || x == reversed %10;
    }
}

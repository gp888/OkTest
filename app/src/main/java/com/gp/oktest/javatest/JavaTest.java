package com.gp.oktest.javatest;


public class JavaTest {

    public static void main(String[] args) {
        String str = new String("乳猪浓缩" + "\n" +"料乳猪浓" + "\n" + "缩料");
        System.out.println("heheh" + str);

        //是否是素数
        double m = 1000;
        double k = Math.sqrt(m);
        for(int i = 2; i <= k; i++) {
            if (m % i == 0) {
                System.out.println(i + "");
                break;
            }
        }

        //冒泡排序
        int [] a = {2, 3, 4, 5, 6, 7, 8, 9};
        for(int j = 0; j < a.length - 1; j ++) {
            for(int i = 0; i < a.length - j - 1; i++) {
                if(a[i] > a[i + 1]) {
                    int t = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = t;
                }
            }
        }

        //斐波那契
        int f1 = 1, f2 = 1;
        for(int i = 3; i < 20; i++) {
            f1 = f1 + f2;
            f2 = f1 + f2;
        }

        //公约数，选择排序，折半查找
        int low = 2,up = 9,x = 5;
        while (low <= up) {
            int mid = (low + up)/2;
            if (x < a[mid]) {
                up = mid - 1;
            }else if (x > a[mid]) {
                low = mid + 1;
            }else {
                x = mid;
            }
        }

        int[] heights = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trappWater(heights));

        //打印乘法口诀
        for (int i = 1,j=1; j <= 9; i++) {
            System.out.printf("%d * %d = %d %s",i,j,i*j,"\t");
            // NSLog(@"%d * %d = %d",i,j,i*j);
            if(i == j){
                i =0;j++;
                // NSLog(@"%@",@"\n");
                System.out.printf("%s","\n");
            }
        }

    }

    //收集雨水
    int[] heights;
    public static int trappWater(int[] heights) {
    int max = 0;
    int volumn = 0;
    int[] leftMax = new int[heights.length];
    int[] rightMax = new int[heights.length];

    for(int i = 0; i < heights.length; i++) {
        leftMax[i] = max = Math.max(heights[i], max);
    }

    max = 0;

    for(int i = heights.length - 1; i >= 0; i--) {
        rightMax[i] = max = Math.max(heights[i], max);
    }

    for(int i = 0; i < heights.length; i++) {
        volumn = volumn +  Math.min(leftMax[i], rightMax[i]) - heights[i];
    }

    return volumn;
    };
}

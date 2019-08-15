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


       System.out.println(Integer.toBinaryString(-2));//负数用补码表示
       System.out.println(NumberOf2(-2));
    }

    //收集雨水，分别算出左右的最高点，减去当前高度
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


    //输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示
    //第一版：
    public static  int NumberOf1(int n) {
        //return Integer.bitCount(n);
        int count =0;
        String str = Integer.toBinaryString(n);
        char[] chararr = str.toCharArray();
        for(int i=0;i<chararr.length;i++){
            if(chararr[i]=='1'){
                count++;
            }
        }
        return count;
    }

    //通过每次将数字n除以2来判断每一位是否为1
    public static int NumberOf2(int n) {
        int numOfOne = 0;
        for(int i = 0; i < 32; i ++){
            if(((n >> i ) & 1) == 1){
                numOfOne ++;
            }
        }
        return numOfOne;
    }

    public static  int NumberOf3(int n) {
        int count = 0;
        if(n < 0) {
            n = n & 0x7FFFFFFF;
            count ++;
        }
        while(n!=0){
            if(n%2!=0){
                count++;
            }
            n/=2;
        }
        return count;
    }

    //一直运算到n等于0时的运算次数即为
    public static int NumberOf4(int n) {
        int count=0;
        while(n!=0){
            count++;
            //n&(n-1)的运算结果会把数字n的二进制数的最右面的1变为0
            n=n&(n-1);
        }
        return count;
    }

    public static  int NumberOf5(int n) {
        return Integer.bitCount(n);
    }

    //判断二进制中0的个数
    public static int findZero(int n) {
        int count = 0;
        while(n != 0) {
            if((n&1)!=1)
                count++;
            n>>>=1;
        }
        return count;
    }


//    二进制高位连续0的个数
    public static int numberOfLeadingZeros0(int i){
        if(i == 0)
            return 32;
        int n = 0;
        int mask = 0x80000000;
        int j = i & mask;
        while(j == 0){
            n++;
            i <<= 1;
            j = i & mask;
        }
        return n;
    }

    //二叉搜索树第k个结点，递归中序遍历方式
    class TreeNode{
        public TreeNode left;
        public TreeNode right;
    }

    int count = 0;
    TreeNode KthNode(TreeNode pRoot, int k) {
        if(pRoot != null) {
            TreeNode leftNode = KthNode(pRoot.left, k);
            if(leftNode != null)
                return leftNode;
            count++;
            if(count == k)
                return pRoot;
            TreeNode rightNode = KthNode(pRoot.right, k);
            if(rightNode != null)
                return rightNode;
        }
        return null;
    }
}

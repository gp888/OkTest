package com.gp.oktest.javatest;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

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

/****************************位运算*************************************************/
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


//    判断二进制高位连续0的个数
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

    /************************二叉树**********************************************/
    class TreeNode{
        int val = 0;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    //二叉搜索树第k小个结点，递归中序遍历方式。5 / \ 3 7 /\ /\ 2 4 6 8，二叉搜索树的中序遍历就是排序的
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

    //栈的方式
    TreeNode KthNode1(TreeNode pRoot, int k) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if(pRoot==null||k==0) return null;
        int t=0;
        while(pRoot!=null ||stack.size()>0){
            while(pRoot!=null){
                stack.push(pRoot);
                pRoot = pRoot.left;
            }
            if(stack.size()>0){
                pRoot= stack.pop();
                t++;
                if(t==k) return pRoot;
                pRoot= pRoot.right;
            }
        }
        return null;
    }

    //从上往下打印二叉树，同层节点从左至右打印
    public class Solution {
        /**
         * 1.根节点放到队列里面，队列不空，就打印队列头，打印这个节点，马上把这个节点的左右子节点放到队列中。
         * 2.再要访问一个节点，把这个节点的左右放入，此时队头是同层的，队尾是打印出来的左右。依次先入先出就可以得到结果。
         * @param root
         * @return
         */
        public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
            ArrayList<Integer> layerList = new ArrayList<Integer>();
            if (root == null)
                return layerList;
            LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                layerList.add(node.val);
                if (node.left != null)
                    queue.addLast(node.left);
                if (node.right != null)
                    queue.addLast(node.right);
            }
            return layerList;
        }
    }
    //二叉树打印成多行，同一层结点从左至右输出。每一层输出一行
    static ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(pRoot == null)
            return res;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        TreeNode last = pRoot;
        TreeNode nlast =null;
        queue.offer(pRoot);
        ArrayList<Integer> tmp = new ArrayList<>();
        while (!queue.isEmpty()) {
            pRoot = queue.poll();
            tmp.add(pRoot.val);//出队列，就把他左右孩子入队列，
            //此时，下一层的最右要跟着更新
            if (pRoot.left!=null) {
                queue.offer(pRoot.left);
                nlast = pRoot.left;
            }
            if (pRoot.right!=null) {
                queue.offer(pRoot.right);
                nlast = pRoot.right;
            }
            //如果到了本层的最右，就把这一层结果放入。注意最后一层时，isempty不成立，
            //最后一层的结果要单独放入。
            if (pRoot == last && !queue.isEmpty()) {
                res.add(new ArrayList<>(tmp));
                last = nlast;
                tmp.clear();
            }
        }
        res.add(new ArrayList<>(tmp));
        return res;
    }

    //数据流中位数
    //创建优先级队列维护大顶堆和小顶堆两个堆，并且小顶堆的值都大于大顶堆的值

    //1.为了保证两个堆的尺寸差距最大为1，采用奇数个时候插到大根堆，偶数个插到小根堆。
    //2.当数据总数为偶数时，新加入的元素，应当进入小根堆（注意不是直接进入小根堆，而是经大根堆筛选后取大根堆中最大元素进入小根堆），要保证小根堆里面所有数都比大根堆的大。
    //3.当数据为奇数个时，按照相应的调整进入大根堆。
    //4.如果个数位奇数个，则大根堆堆顶为中位数，否则就是两个堆顶除以2.比如新加入三个，那么第一个在大，第二个在小，第三个可能在大。所以就是大根堆的堆顶。

    //* 插入有两种思路
    //* 1：直接插入大堆中，之后若两堆尺寸之差大于1(也就是2)，则从大堆中弹出堆顶元素并插入到小堆中
    //* 若两队之差不大于1，则直接插入大堆中即可。

    //* 2：奇数个数插入到大堆中，偶数个数插入到小堆中，
    //* 但是 可能会出现当前待插入的数比大堆堆顶元素大，此时需要将元素先插入到小堆，然后将小堆堆顶元素弹出并插入到大堆中
    //* 对于偶数时插入小堆的情况，一样的道理
    //* 因为要保证最大堆的元素要比最小堆的元素都要小。

    //Java中PriorityQueue通过二叉小顶堆实现，可以用一棵完全二叉树表示
    //优先队列的作用是能保证每次取出的元素都是队列中权值最小的（Java的优先队列每次取最小元素，
    // C++的优先队列每次取最大元素）。元素大小的评判可以通过元素本身的自然顺序（natural ordering），
    // 也可以通过构造时传入的比较器（Comparator，类似于C++的仿函数）。


    int count1 = 0;
    PriorityQueue<Integer> minheap = new PriorityQueue<>();
    PriorityQueue<Integer> maxheap = new PriorityQueue<>(11, new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);//o2大于o1返回1 ，否则返回-1
        }
    });
    public void Insert(Integer num) {
        count1++;
        if (count1 % 2 == 0) {//偶数进入小根堆,这个其实无所谓，定了一个平均分配的规则
            //保证进入小根堆的元素要比大根堆最大的大，所以如果小调整
            if (!maxheap.isEmpty() && num < maxheap.peek()) {
                maxheap.offer(num);
                num = maxheap.poll();
            }
            minheap.offer(num);
        } else {//奇数进入大根堆
            if (!minheap.isEmpty() && num > minheap.peek()) {
                minheap.offer(num);
                num = minheap.poll();
            }
            maxheap.offer(num);
        }
    }

    public Double GetMedian() {
        double median = 0;
        if (count % 2 ==1) {
            median = maxheap.peek();
        }
        else
            median = (minheap.peek()+maxheap.peek())/2.0;
        return median;
    }

    private ArrayList<Integer> list =new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
    /**一个链表记录路径，一个存放这个链表的链表记录最终的结果。前序遍历去访问。先访问根，在递归在左右子树找。注意回退
     * 1.首先将根节点放入链表，target减去这个根节点
     * 2.判断是否target同时是叶子节点，如果是就将当前的链表放在结果连表里
     * 3.如果不是，就递归去访问左右子节点。
     * 4.无论是找到没有，都要回退一步、
     * @param root
     * @param target
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if(root == null)
            return resultList;
        list.add(root.val);
        target = target - root.val;
        if(target == 0 && root.left == null && root.right == null){
            resultList.add(new ArrayList<Integer>(list));
        }
        else {
            FindPath(root.left, target);
            FindPath(root.right, target);

        }
        // 在返回父节点之前，在路径上删除该结点
        list.remove(list.size()-1);
        return resultList;
    }

    //输入某二叉树的前序遍历和中序遍历的结果，重建出该二叉树
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null) {
            return null;
        }
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        if (pre.length != in.length) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);//第一个
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                //pre的0往后数i个是左子树的，copyofrange包含前面的下标，不包含后面的下标
                //in的i往前数i个是左子树的。
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                //注意in是从i+1开始，因为i是现在的根，i+1开始才是右子树
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length),
                        Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return root;
    }

    //树的子结构，递归
    //先从根开始再把左作为根，再把右作为根。把一个为根的时候的具体比对过程由第二个函数决定。
    //从根可以认为是一颗树，从左子树开始又可以认为是另外一颗树，从右子树开始又是另外一棵树。
    //本函数就是判断这一整颗树包不包含树2，如果从根开始的不包含，就从左子树作为根节点开始判断，
    //再不包含从右子树作为根节点开始判断。
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        boolean res = false;
        if (root1 != null && root2 != null) {
            if(root1.val == root2.val){
                res = doesTree1haveTree2(root1,root2);
            }
            if(!res)
            {
                res = HasSubtree(root1.left, root2);
            }
            if(!res)
            {
                res = HasSubtree(root1.right, root2);
            }
        }
        return res;
    }
    //本函数，判断从当前的节点 ，开始两个树能不能对应上，是具体的比对过程
    public boolean doesTree1haveTree2(TreeNode root1,TreeNode root2) {
        if(root2 == null)
            return true;
        if(root1 == null)
            return false;
        if(root1.val != root2.val){
            return false;
        }
        //如果根节点可以对应上，那么就去分别比对左子树和右子树是否对应上
        return doesTree1haveTree2(root1.left, root2.left) && doesTree1haveTree2(root1.right, root2.right);
    }


    //二叉树的镜像
    /**
     * 1.节点为空直接返回
     * 2.如果这个节点的左右子树不为空，就交换。
     * 3.递归对这个节点的左子树进行求镜像。对这个节点的右子树求镜像。
     * @param root
     */
    public void Mirror(TreeNode root){
        if (root == null) {
            return;
        }
        if(root.left != null || root.right != null) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            Mirror(root.left);
            Mirror(root.right);
        }
    }

    //栈的方式
    public void Mirror1(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null || node.right != null) {
                TreeNode temp = node.left;
                node.left = node.right;
                node.right = temp;
            }
            if (node.left != null)
                stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);

        }
    }


    //输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果
    /**二叉搜索树的性质：
     * 所有左子树的节点小于根节点，所有右子树的节点值大于根节点的值。
     * 1.后序遍历的最后一个值为root，在前面的数组中找到第一个大于root值的位置。
     * 2.这个位置的前面是root的左子树，右边是右子树。然后左右子树分别进行这个递归操作。
     * 3.其中，如果右边子树中有比root值小的直接返回false
     * @param sequence
     * @return
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if (sequence == null || sequence.length == 0)
            return false;
        return IsBST(sequence, 0, sequence.length -1);

    }

    public boolean IsBST(int [] sequence, int start, int end) {
        if(start >= end) //注意这个条件的添加，如果对应要处理的数据只有一个或者已经没
            return true;
        int index = start;
        for (; index < end; index++) {//寻找大于root的第一个节点，然后再分左右两部分
            if(sequence[index] > sequence[end])
                break;
        }
        for (int i = index; i < end; i++) {//若右子树有小于根节点的值，直接返回false
            if (sequence[i] < sequence[end]) {
                return false;
            }
        }
        return IsBST(sequence, start, index-1) && IsBST(sequence, index, end-1);
    }

    /*当案例为{4,6,7,5}的时候就可以看到：
    （此时start为0，end为3）
    一开始index的值为1，左边4的是start为0，index-1为0，下一次递归的start和end是一样的，true！
    右边，start为1，end-1为2，是{6,7}元素，下一轮递归是：
    ————7为root，index的值指向7，
    ————所以左边为6，start和index-1都指向6，返回true。
    ————右边index指向7，end-1指向6，这时候end > start！如果这部分还不返回true，下面的数组肯定超了    */


    //输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表
    /**二叉搜索树的中序遍历就是递增的排序，所以就用中序遍历方法来做。

     * 中序遍历的步骤，只不过在递归的中间部分不是输出节点值，而是调整指针指向。
     *     10
     *     /\
     *    5 12
     *   /\
     *  4 7
     *  第一次执行，到4的时候，head和resulthead都指向这个
     *  指针调整的其中一步：4是head 5是pRootOfTree 然后调整head右指向5，5左指向4，然后5变成head就行了。
     * @param pRootOfTree
     * @return
     */
    TreeNode head = null;
    TreeNode resultHead = null; //保存生成链表的头结点，便于程序返回
    public TreeNode Convert(TreeNode pRootOfTree) {
        ConvertSub(pRootOfTree);
        return resultHead;
    }
    public void ConvertSub(TreeNode pRootOfTree) {
        if(pRootOfTree == null)
            return;
        ConvertSub(pRootOfTree.left);
        if(head == null){
            head = pRootOfTree;
            resultHead = pRootOfTree;
        }
        else {
            head.right = pRootOfTree;
            pRootOfTree.left = head;
            head = pRootOfTree;
        }
        ConvertSub(pRootOfTree.right);
    }

    //输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度
    // 注意最后加1，因为左右子树的深度大的+根节点的深度1
    public int TreeDepth(TreeNode root) {
        if(root == null)
            return 0;
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        return left > right? left +1:right+1;
    }

    //输入一棵二叉树，判断该二叉树是否是平衡二叉树
    //描述：如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
    // 注意使用全局变量
    boolean isBalance = true;
    public boolean IsBalanced_Solution(TreeNode root) {
        lengthOfTree(root);
        return isBalance;
    }

    private int lengthOfTree(TreeNode root) {
        if (root == null)
            return 0;
        int left = lengthOfTree(root.left);
        int right = lengthOfTree(root.right);
        if (Math.abs(left - right) > 1)
            isBalance = false;
        return Math.max(left, right) + 1;
    }


    //给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
    // 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针
    public class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;
        TreeLinkNode(int val) {
            this.val = val;
        }
    }
    /** 主要分三种：
     * 1.如果有右孩子，后继节点就是右子树最左边的
     * 2.如果没有右孩子，判断是否是父节点的左孩子，是的话，返回
     * 3.找不到就是null
     * @param pNode
     * @return
     */

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if(pNode == null)
            return null;
        // 如果有右子树，则找右子树的最左节点
        if (pNode.right != null) {
            pNode = pNode.right;
            // 如果此时pNode没有左子树，那么它就是下一个结点 ，就是最左边的了
            //如果有左子树，那就在左子树找最左边的
            while(pNode.left != null)
                pNode = pNode.left;
            return pNode;

        }
        // 非根结点，并且没有右子树
        while(pNode.next != null) {
            // 该结点是其父亲的左孩子,找到就是返回父节点作为后记
            if (pNode.next.left == pNode)
                return pNode.next;
            //找不到这个左孩子的，就继续往上，next其实是parent
            pNode = pNode.next;
        }
        return null;
    }

    //判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的
//利用递归进行判断，
    //若左子树的左孩子等于右子树的右孩子且左子树的右孩子等于右子树的左孩子，
    //并且左右子树节点的值相等，则是对称的。
    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null)
            return true;
        return isCommon(pRoot.left,pRoot.right);
    }
    public boolean isCommon(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null)
            return true;
        if (leftNode != null && rightNode != null) {
            return leftNode.val == rightNode.val &&
                    isCommon(leftNode.left, rightNode.right) &&
                    isCommon(leftNode.right, rightNode.left);
        }
        return false;
    }


    //实现两个函数，分别用来序列化和反序列化二叉树
    //二叉树转文件，文件转二叉树
    //通过先序遍历实现序列化和反序列化，通过层遍历实现这两种

    String Serialize(TreeNode root) {
        if(root == null)
            return "#!";
        String res = root.val + "!";
        res = res + Serialize(root.left);
        res = res + Serialize(root.right);
        return res;
    }

    TreeNode Deserialize(String str) {
        String [] values = str.split("!");
        Queue<String> queue = new LinkedList<String>();
        for (int i = 0; i < values.length; i++) {
            queue.offer(values[i]);
        }
        return reconPre(queue);
    }
    TreeNode reconPre(Queue<String> queue) {
        String value = queue.poll();
        if(value.equals("#"))
            return null;
        TreeNode head = new TreeNode(Integer.valueOf(value));
        head.left = reconPre(queue);
        head.right = reconPre(queue);
        return head;
    }

    //实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）
    /*
      当模式中的第二个字符不是“*”时：
      1、如果字符串第一个字符和模式中的第一个字符相匹配，
      那么字符串和模式都后移一个字符，然后匹配剩余的。
      2、如果字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
      而当模式中的第二个字符是“*”时：
      如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。
      如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
      1、模式后移2字符，相当于x*被忽略；
      2、字符串后移1字符，模式后移2字符； 相当于x*算一次
      3、字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位，相当于算多次
      这里需要注意的是：Java里，要时刻检验数组是否越界。
      */
    public class Zhengze {
        public boolean match(char[] str, char[] pattern) {
            if (str == null || pattern == null) {
                return false;
            }
            int strIndex = 0;
            int patternIndex = 0;
            return matchCore(str, strIndex, pattern, patternIndex);
        }

        public boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
            // 有效性检验：str到尾，pattern到尾，匹配成功
            if (strIndex == str.length && patternIndex == pattern.length)
                return true;
            // pattern先到尾，匹配失败
            if (strIndex != str.length && patternIndex == pattern.length)
                return false;
            // 模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
            if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
                if ((strIndex != str.length && pattern[patternIndex] == str[strIndex])
                        || (pattern[patternIndex] == '.' && strIndex != str.length)) {
                    return // 模式后移2，视为x*匹配0个字符
                            matchCore(str, strIndex, pattern, patternIndex + 2)
                                    // 视为模式匹配1个字符
                                    || matchCore(str, strIndex + 1, pattern, patternIndex + 2)
                                    // *匹配1个，再匹配str中的下一个
                                    || matchCore(str, strIndex + 1, pattern, patternIndex);

                } else {
                    return matchCore(str, strIndex, pattern, patternIndex + 2);
                }
            } // 模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
            if ((strIndex != str.length && pattern[patternIndex] == str[strIndex])
                    || (pattern[patternIndex] == '.' && strIndex != str.length)) {
                return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
            }
            return false;
        }
    }


    //判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
    // 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

    //如果第一位是+或-，就后移一位。
    //
    //如果是数字，索引后移，数字表示1.

    //如果是点，要判断至此点的数量和e的数量是否已经有了，因为java 中e要求后面为整数，如果有了肯定false。索引后移，dotnum增加。

    //如果是e，判断是否重复e，或者前面没有数字返回false。enum++， 索引++，此时还要判断最后一位是不是e或者+或者-，如果是false。

    /*例子：
     * 110   1a1   1.1.1  2.2  12e
     *
     * */
    public  static boolean isNumeric(char[] str) {
        if(str == null)
            return false;
        int length = str.length;
        int dotNum = 0;//记录点的数量
        int index = 0;//索引
        int eNum = 0;//记录e的数量
        int num = 0;//记录数字的数量
        if (str[0] == '+' || str[0] == '-') {
            index++;
        }
        while (index < length) {
            if(str[index]>='0' && str[index]<='9') {
                index++;
                num = 1;
                // .前面可以没有数字,所以不需要判断num是否为0
            }else if(str[index]=='.') {
                // e后面不能有.,e的个数不能大于1.java科学计数要求aeb，b为整数
                if(dotNum >0 || eNum >0)
                    return false;
                dotNum++;
                index++;
            }else if(str[index] == 'e' || str[index] == 'E') {
                // 重复e或者e前面没有数字
                if(eNum > 0 || num ==0)
                    return false;
                eNum++;
                index++;
                // 符号不能在最后一位
                if(index < length &&(str[index]=='+'||str[index]=='-'))
                    index++;
                // 表示e或者符号在最后一位
                if(index == length)
                    return false;
            }else {
                return false;
            }

        }
        return true;
    }

    //第一个只出现一次的字符
    //一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
    public int FirstNotRepeatingChar(String str) {
        //这个hashmap有序，所以用这个
        LinkedHashMap<Character, Integer> map= new LinkedHashMap<>();
        //遍历字符串，第一次设为1 否则就加
        for (int i = 0; i < str.length(); i++) {
            if (!map.containsKey(str.charAt(i))) {
                map.put(str.charAt(i), 1);
            }
            else
                map.put(str.charAt(i),map.get(str.charAt(i))+1);
        }
        //找出现次数为1的
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }


    //字符流中第一个不重复的字符
    //当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，
    // 第一个只出现一次的字符是"l"。如果当前字符流没有存在出现一次的字符，返回#字符

    HashMap<Character, Integer> map = new HashMap<>();//记录字符出现次数
    ArrayList<Character> list1 = new ArrayList<>();//记录当前的所有的字符
    //Insert one char from stringstream
    public void Insert(char ch) {
        if(map.containsKey(ch))
            map.put(ch, map.get(ch)+1);
        else
            map.put(ch,1);
        list1.add(ch);
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        for(char c:list1) {
            if(map.get(c)==1)
                return c;
        }
        return '#';
    }

    //字符数组的方法：

    //char类型和int类型数值在 0-255之内的可以通用

    char [] chars = new char[256];//ascii字符共128，其他字符非中文认为256个，

    StringBuffer sb = new StringBuffer();
    //Insert one char from stringstream
    public void Insert1(char ch) {
        sb.append(ch);
        chars[ch]++;//如果字符是1，那么就是在字符1对应的下标的地方
        //也就是49的下标处，ascii加1.此时如果输出chars[ch],里面存ascii值
        //为1，所以是一个不可显示的字符。
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce1()
    {
        char [] str = sb.toString().toCharArray();
        for(char c:str) {
            if(chars[c] == 1)//判断这个字符数组中在这个字符下标处值是否为1.
                return c;
        }
        return '#';
    }



    //翻转字符串
    //第一种方法，用空格将字符串切分，
    //倒着往stringbuffer里面插入。
    public String ReverseSentence1(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        String[] strs =str.split(" ");//str = "i love you"则strs[0]=i strs[1]=love
        StringBuffer sb = new StringBuffer();
        for (int i = strs.length -1; i >= 0; i--) {
            sb.append(strs[i]);
            if (i>0) {//最后一个不添加空格
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    //第二种思路，先将整个字符串反转，再逐个单词反转
    public String ReverseSentence(String str) {
        if (str == null || str.length() == 0)
            return str;
        if (str.trim().length() == 0)
            return str;
        StringBuilder sb = new StringBuilder();
        String re = reverse(str);
        String[] s = re.split(" ");
        for (int i = 0; i < s.length - 1; i++) {
            sb.append(reverse(s[i]) + " ");
        }
        sb.append(reverse(s[s.length-1]));
        return String.valueOf(sb);
    }

    public String reverse(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0 ; i--) {
            sb.append(str.charAt(i));
        }
        return String.valueOf(sb);
    }

    //循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”

    public String LeftRotateString(String str,int n) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        int len = str.length();
        n = n % len;// 当len=3，n=4，其实相当于左旋转1位，所以需要取余
        char[] charstr = str.toCharArray();
        //先旋转前面的
        reverse(charstr, 0, n-1);
        //再旋转后面的字符串
        reverse(charstr, n, len -1);
        //最后整体反转
        reverse(charstr, 0, len-1);
        return String.valueOf(charstr);
    }
    //实现的是charstrs从i到j的反转，也可以使用上题中stringbuffer的反转方式
    private void reverse(char[] charStrs, int i, int j) {
        while(i<j) {
            char temp = charStrs[i];
            charStrs[i] =charStrs[j];
            charStrs[j] = temp;
            i++;
            j--;
        }
    }

    //将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
    public int StrToInt(String str) {
        if (str == null || str.length() == 0)
            return 0;
        int mark = 0;
        int number = 0;
        char[] chars = str.toCharArray();
        if (chars[0] == '-')
            mark = 1;//第一位如果是-号，则从第二位开始循环
        else if(chars[0] == '+')
            mark = 0;
        for (int i = mark; i < chars.length; i++) {
            if(chars[i]<48 || chars[i]>57)
                return 0;
            number = number * 10 + chars[i] - 48;
        }
        return mark==0?number:-number;//最后根据mark标记的正负号来决定数字正负
    }


    //输入一个字符串,按字典序打印出该字符串中字符的所有排列。
    // 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,

    //输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母

    /**
     * 基于回溯的递归实现
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        List<String> res = new ArrayList<String>();
        if(!TextUtils.isEmpty(str)){
            PermutationHelp(str.toCharArray(),0,res);
            Collections.sort(res); //按字典序 输出字符串数组。
        }

        return (ArrayList)res;
    }
    private void PermutationHelp(char[] chars, int index, List<String> list) {
        if(index == chars.length -1){ //当递归交换到最后一个位置的时候，就看看list有么有这个字符串，没有的话就放进去。
            String val = String.valueOf(chars);
            if (!list.contains(val)) {//如果最后list没有这个string，因为可能交换后有重复的
                list.add(val);
            }
        } else {
            for (int i = index; i < chars.length; i++) { //循环来执行交换操作，先交换，然后固定这个，下一个交换。最后要交换回来不要影响执行
                swap(chars, index, i);
                PermutationHelp(chars, index+1, list);//依次固定一个
                swap(chars, index, i);
            }
        }
    }
    private void swap(char[] chars,int i, int j) {//交换数组中的两个位置中的值
        char temp =chars[i];
        chars[i] = chars[j];
        chars[j] = temp;

    }

    //在一个长度为n的数组里的所有数字都在0到n-
    //数组中的数字为0到n-1的范围内。如果这个数组中没有重复的数字，则对应的i位置的数据也为i。可以重排此数组，

    //比较好的解决方式,时间复杂度O(n),空间复杂度O(1)
    //数组中的数字为0到n-1的范围内。
    //如果这个数组中没有重复的数字，则对应的i位置的数据也为i。可以重排此数组
    public boolean duplicate3(int numbers[],int length,int [] duplication) {
        if(numbers == null || numbers.length ==0) {
            duplication[0] = -1;
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (numbers[i] < 0 || numbers[i] > length - 1) {
                duplication[0] = -1;
                return false;
            }
        }
        for (int i = 0; i < length; i++) {
            while(numbers[i] != i) {
                if(numbers[i] == numbers[numbers[i]]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                else {
                    int tmp = numbers[i];
                    numbers[i] = numbers[tmp];
                    numbers[tmp] = tmp;
                }

            }
        }
        return false;
    }


    //给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。
    //思路：用矩阵的方式，先计算左下三角，再计算右上三角

    // 新建一个新数组B， 对A数组i项左侧自上往下累乘，
    // 对A数组i项右侧自下往上累乘 时间复杂度O(n)
    public int[] multiply(int[] A) {
        // 将B拆分为A[0] *...* A[i-1]和A[n-1]*...*A[i+1] 两部分
        if(A == null || A.length ==0)
            return A;
        int length = A.length;
        int [] B = new int[length];
        B[0] = 1;
        // 先计算左下三角形，此时B[0]只有一个元素，舍为1，
        // B[0]不包括A[0]
        for (int i = 1; i < length; i++) {
            B[i] = B[i-1]*A[i-1];
        }
        int tmp =1;
        //计算右上三角形
        for (int i = length -1; i >=0; i--) {
            //最终的B[i]是之前乘好的再乘以右边的
            B[i] *=tmp;
            tmp *= A[i];
        }
        return B;
    }



    //在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
    // 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数

    //从右上角开始，若小，向下走，删除一行，若大，向左走，删除一列

    public boolean Find(int target, int [][] array) {
        int row = 0;
        int col = array[0].length -1;
        while(row < array.length && col >= 0) {
            if (array[row][col]==target) {
                return true;
            } else if (array[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    //数组中只出现一次的数字
    //一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        int eO = 0,eOne = 0;
        for(int num:array)
            eO ^=num;
        int firstOne = eO &(~eO +1);//求得二进制中第一位1，比如101和011得到010
        for(int cur:array)
            if ((cur&firstOne) !=0) {//把第k位是1的一组找出来进行异或
                eOne ^=cur;
            }//最终结果就是第k位是1且落单的那个
        num1[0] = eOne;
        num2[0] = eOne^eO;//异或结果的运算规则。
    }

    //和为S的两个数
    //输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if(array == null || array.length <2)
            return list;
        int low = 0;
        int high = array.length -1;
        while(low < high) {
            int small = array[low];
            int big = array[high];
            if(small + big == sum) {
                list.add(small);
                list.add(big);
                break;
            }
            else if (small+big < sum)
                low++;
            else
                high--;


        }
        return list;
    }


    //和为S的连续正数序列
    /*
     *初始化small=1，big=2;
     *small到big序列和小于sum，big++;大于sum，small++;
     *当small增加到(1+sum)/2是停止
     */
    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        if(sum < 3) //不重复，最少有两个数字
            return res;
        int small = 1;
        int big = 2;
        //因为是两个数，假设small是这个
        //，big假设也是这个，和为sum+1，所以到此就可以停了，后面肯定更大
        while(small !=(sum+1)/2) {
            int cursum = SumOfList(small, big);
            if(cursum == sum) {
                for (int i = small; i <= big; i++) {
                    list.add(i);
                }
                res.add(list);
                list.clear();//清理掉
                big++;//找到一组，继续big++，找下一组满足要求的。
            } else if (cursum > sum) {
                small++;
            } else {
                big++;
            }
        }
        return res;
    }
    //计算list内的数据的和
    public int SumOfList(int small, int big) {
        int sum = 0;
        for (int i = small; i <= big; i++) {
            sum +=i;
        }
        return sum;
    }

    //调整数组顺序使奇数位于偶数前面
    //实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
    // 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

    public void reOrderArray(int [] array) {
        ArrayList<Integer> jlist = new ArrayList<Integer>();
        ArrayList<Integer> olist = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0)
                olist.add(Integer.valueOf(array[i]));
            else
                jlist.add(Integer.valueOf(array[i]));//valueOf 是将int转换为Integer
            //intValue是反过来

        }
        jlist.addAll(olist);
        int temp = 0;
        for (int i = 0; i < jlist.size(); i++) {
            //System.out.println(jlist.get(i));
            array[temp] = jlist.get(i).intValue();
            temp++;
        }
    }

    //数组中出现次数超过一半的数字

    /**自己的空间复杂度的思路，哈希表记录。
     * 此外：博客，基于快排、基于阵地。此处采用基于防守阵地的方式来进行。
     * 一次再数组中删除两个不同的数，最后剩下的数   有可能    是超过一半的。所以要检验一下。 一个数出现次数大于一半，他肯定会被剩下来，但是剩下来的缺不一定满足。
     * 算法步骤：
     * 如果times为0，就把候选设为当前值。
     * 如果下个数和候选一样，times就++。
     * 如果下个数和候选不一样，times就--。相当于对子，同归于尽。因为超过一半的数肯定超过剩下的所有数。所以和这个数对，这个数肯定会剩下来。
     * 但是剩下的数不一定是，比如 1 2 3 剩下3 比如 1 2 1 3 3 3 2 2 也是剩下3.所以要余外的判断，看是否这个数真的超过。
     * @param array
     * @return
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        int cand = 0;
        int times = 0;
        for (int i = 0; i < array.length; i++) {
            if(times == 0){
                cand = array[i];
                times = 1;
            } else if (array[i] == cand) {
                times++;
            } else {
                times--;
            }
        }
        times = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == cand)
                times++;
        }
        if (times * 2 > array.length) {
            return cand;
        }
        else
            return 0;
    }


    //连续子数组的最大和，数组有负数
    /**
     * 不是看当前的值，而是看当前的累计值，如果当前累计为负数，那么加上现在这个数，
     * 肯定和就小了，所以继续从当前开始累计，如果为正，那就继续加，
     * 但是要时刻保存下最大值来，因为后面的累计有可能小。
     * @param array
     * @return
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if(array.length == 0)
            return 0;
        int total = array[0];//当前的前面的和累计
        int maxsum = array[0];//记录可能的最大值
        for (int i = 1; i < array.length; i++) {
            if(total >= 0)//
                total = total + array[i];
            else
                total = array[i];
            if(total > maxsum)
                maxsum = total;
        }
        return maxsum;
    }

    //把数组排成最小的数

    /**
     * 主要就是规则的制定，将两个数字转换为字符串，防止溢出
     * 假设两个数字m和n，拼接有mn和nm
     * 如果mn>nm, 我们打印nm，此时定义n小于m。（此处小于是我们定义的）
     * 不是m和n的大小关系，
     * 而是看拼了之后的大小，来决定m和n的大小。
     * 如果mn<nm 那么就是m小于n。
     *
     * @param numbers
     * @return
     */
    public String PrintMinNumber(int [] numbers) {
        if(numbers == null || numbers.length == 0)
            return "";
        int len = numbers.length;
        String[] str = new String[len];
        StringBuffer sb = new StringBuffer();
        //将数字型的放在字符串数组中。
        for (int i = 0; i < len; i++) {
            str[i] = String.valueOf(numbers[i]);
        }
        //根据定义的规则重新堆str进行升序排序
        Arrays.sort(str, new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                String c1 = s1 + s2;
                String c2 = s2 + s1;

                return c1.compareTo(c2);
            }

        });
        //根据规则排好序，将结果依次放入stringbuffer中就行了
        for (int i = 0; i < len; i++) {
            sb.append(str[i]);
        }

        return sb.toString();
    }


    //在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
    // 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007

    /**用了归并排序，用count来记录数量
     * 所不同的是用的--而不是++
     * 每一次归并之前，记录当前有的个数，
     * @param array
     * @return
     * 不明白
     */
    int count2 = 0;
    public int InversePairs(int [] array) {
        if(array == null || array.length ==0)
            return 0;
        mergeSort(array, 0, array.length -1);
        return count2;

    }
    public void mergeSort(int []array, int start, int end) {
        if(start < end) {
            int mid = (start + end)/2;
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            merge(array, start, mid, mid+ 1, end);
        }
    }
    public void merge(int []array,int start1,int end1, int start2, int end2) {
        int i = end1;
        int j = end2;
        int k = end2 - start1 ;
        int [] temp = new int[end2- start1 +1];
        while(i >= start1 && j >=start2) {
            if(array[i] > array[j]) {
                //假设此时两个归并的是17 19 22 || 16 18 21
                //那么22大于21，所以可以看出对应22
                //有三个，22 16 22 18 22 21
                temp[k--] = array[i--];
                count2 = count2 + j - start2 +1;
                count2 %= 1000000007;
            }
            else
                temp[k--] = array[j--];
        }
        while(i >= start1)
            temp[k--] = array[i--];
        while(j >= start2)
            temp[k--] = array[j--];

        int m = start1;
        for(int element:temp)
            array[m++] = element;
    }


    //数字在排序数组中出现的次数

    /**
     * 首先用递归二分法找到第一个k和最后一个k，然后得到个数
     * @param array
     * @param k
     * @return
     */
    public int GetNumberOfK(int [] array , int k) {
        int num = 0;
        if (array != null && array.length >0) {
            int firstk = getFirstK(array, k,0, array.length-1);
            int lastk = getLastK(array, k,0, array.length-1);
            if (firstk > -1 && lastk > -1)
                num = lastk -firstk +1;


        }
        return num;
    }
    /*
     * 找到第一个出现的数字的下标
     */
    public int getFirstK(int [] array, int k,int start, int end) {
        if(start > end)
            return -1;
        int midindex = (start + end)/2;
        int middata = array[midindex];
        if (middata == k) {
            //判断是不是第一个K，前一个不等于K，就是第一个K
            if(midindex > 0 && array[midindex - 1]!=k||midindex == 0)
                return midindex;
            else
                end = midindex -1;//如果不是第一个k，那么肯定是在前面找，所以end要往前放

        } else if (middata > k) {
            end = midindex -1; //二分，如果这个大于k，所以要在前面找
        } else
            start = midindex + 1;// 如果小于k，说明要往后找
        return getFirstK(array,k, start, end);
    }

    /*
     * 找到最后一个出现的数字的下标
     */
    public int getLastK(int [] array, int k,int start, int end) {
        if(start > end)
            return -1;
        int midindex = (start + end)/2;
        int middata = array[midindex];
        if(middata == k) {
            //判断是不是最后一个K，后一个不等于K，就是最后一个K
            if(midindex < array.length-1 && array[midindex + 1]!= k||midindex ==array.length -1)
                return midindex;
            else
                start = midindex + 1;
        } else if (middata > k) {
            end = midindex - 1;
        }
        else
            start = midindex +1;
        return getLastK(array, k,start, end);
    }


    //求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。

    //思路：巧用递归，用了短路，使递归结束。

// &&两侧的表达式结果必须为boolean型，

// &&右侧要用一个无关变量a判断是否与result相等，

// 让右侧的表达式返回boolean型

// 我们的目的仅仅是让&&右侧的表达式执行。

// &&连接的表达式，必须要将最终的boolean结果赋给变量，否则编译报错！

//    一直执行递归，当n=0时，短路，不执行，开始执行result+n，一直回溯就把n给加上来。


    public int Sum_Solution(int n) {
        int result =0;
        int temp = 0;
        boolean flag = (n>0) && temp == (result = Sum_Solution(n-1));
        result += n;
        return result;
    }

    //不用加减乘除做加法

    //写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。

    //思路：

    //首先看十进制是如何做的： 5+7=12，三步走

    //第一步：相加各位的值，不算进位，得到2。

    //第二步：计算进位值，得到10. 如果这一步的进位值为0，那么第一步得到的值就是最终结果。

    //第三步：重复上述两步，只是相加的值变成上述两步的得到的结果2和10，得到12。

    //同样我们可以用三步走的方式计算二进制值相加： 5-101，7-111 第一步：相加各位的值，不算进位，得到010，二进制每位相加就相当于各位做异或操作，101^111。

    //第二步：计算进位值，得到1010，相当于各位做与操作得到101，再向左移一位得到1010，(101&111)<<1。

    //第三步重复上述两步， 各位相加 010^1010=1000，进位值为100=(010&1010)<<1。
    //继续重复上述两步：1000^100 = 1100，进位值为0，跳出循环，1100为最终结果。


    public int Add(int num1,int num2) {
        int sum = num1;//保证当num2为0时候返回num1
        int carry = 0;
        while(num2 != 0) {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;

        }
        return sum;
    }

    //递归
    public int Add1(int num1, int num2) {
        if (num2 == 0)
            return num1;
        return Add(num1 ^ num2, (num1 & num2) << 1);
    }




}


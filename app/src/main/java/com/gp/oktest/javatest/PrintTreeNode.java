package com.gp.oktest.javatest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PrintTreeNode {

    //二叉树遍历

    /**递归前序遍历 DLR*/
    private static void frontOrder(TreeNode<String> treeNode){
        if (treeNode == null){
            return;
        }
        System.out.print(treeNode.data);
        frontOrder(treeNode.leftNode);
        frontOrder(treeNode.rightNode);
    }

    /**递归中序遍历 LDR*/
    private static void middleOrder(TreeNode<String> treeNode){
        if(treeNode == null){
            return;
        }
        middleOrder(treeNode.leftNode);
        System.out.print(treeNode.data);
        middleOrder(treeNode.rightNode);
    }

    /**递归后序遍历 LRD*/
    private static void afterOrder(TreeNode<String> treeNode){
        if (treeNode == null){
            return;
        }
        afterOrder(treeNode.leftNode);
        afterOrder(treeNode.rightNode);
        System.out.print(treeNode.data);
    }

    //二叉树非递归遍历


    //多叉树遍历
    List<MultiTreeNode<String>> treeArray = new ArrayList<>();
    // 递归先序遍历
    private void recurDLR(MultiTreeNode<String> node) {
        if (node == null) {
            return;
        }
        treeArray.add(node);
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getName().toLowerCase() == "view") {
                recurDLR(node.getChildren()[i]);
            }
        }
    }
    // 递归后序遍历
    private void recurLRD(MultiTreeNode<String> node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getName().toLowerCase() == "view") {
                recurLRD(node.getChildren()[i]);
            }
        }
        treeArray.add(node);
    }

    // 层序遍历 从根节点一层一层向下遍历
    // 原理就是利用数组的后进先出 存储dom节点
    private void recurLDR(MultiTreeNode<String> node) {
        Stack<MultiTreeNode<String>> stack = new Stack<>();
        stack.push(node);
        MultiTreeNode<String> del = stack.pop();
        while (del.getChildren().length > 0) {
            for (int i = 0; i < del.getChildren().length; i++) {
                if (del.getChildren()[i].getName().toLowerCase() == "view") {
                    stack.push(del.getChildren()[i]);
                }
            }
            treeArray.add(del);
            del = stack.pop();
        }
    }
}

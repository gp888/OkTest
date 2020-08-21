package com.gp.oktest.javatest;


public class TreeNode <T>{
    public T data;
    public TreeNode<T> leftNode;
    public TreeNode<T> rightNode;
    TreeNode(T data){
        this.data = data;
    }
}
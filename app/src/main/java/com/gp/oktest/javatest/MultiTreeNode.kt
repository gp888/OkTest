package com.gp.oktest.javatest

/**
 * 多叉树
 */
class MultiTreeNode<T> constructor(var data: T, var name: String) {
    var children: Array<MultiTreeNode<T>> = arrayOf()
}
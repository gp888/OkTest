package com.gp.oktest.javatest;

import androidx.annotation.NonNull;

public class Father implements Cloneable {
    public String name;
    public int age;
    public ChildClass child;
    public ChildClass1 child1;

    @NonNull
    @Override
    protected Object clone() {
        try{
            //对child1再进行一次clone
            Father cloneF = (Father) super.clone();
            cloneF.child1 = (ChildClass1) child1.clone();
            return cloneF;
        }catch (CloneNotSupportedException e){

        }
        return null;
    }

    static class ChildClass {
        String name;
    }

    static class ChildClass1 implements Cloneable{
        String name;

        @NonNull
        @Override
        protected Object clone() {
            try{
                return super.clone();
            }catch (CloneNotSupportedException e){

            }
            return null;
        }
    }

    public static void main(String[] args) {
        Father fatherA = new Father();
        fatherA.name = "张三";
        fatherA.age = 30;
        fatherA.child = new ChildClass();
        fatherA.child.name = "55";

        fatherA.child1 = new ChildClass1();
        fatherA.child1.name = "66";
        //浅拷贝
        Father fatherB = (Father) fatherA.clone();

        System.out.println("fatherA == fatherB:" + (fatherA == fatherB));
        System.out.println("fatherA hashcode:" + fatherA.hashCode());
        System.out.println("fatherB hashcode:" + fatherB.hashCode());
        System.out.println("fatherA name:" + fatherA.name);
        System.out.println("fatherB name:" + fatherB.name);

        //引用类型依然是在传递引用
        System.out.println("A.child == B.child:" + (fatherA.child == fatherB.child));
        System.out.println("fatherA.child hashcode:" + fatherA.child.hashCode());
        System.out.println("fatherB.child hashcode:" + fatherB.child.hashCode());

        //深拷贝  对 child1 也进行了一次拷贝，这实则是对 ChildClass1 进行的浅拷贝，但是对于 Father 而言，则是一次深拷贝
        System.out.println("A.child1 == B.child1:" + (fatherA.child1 == fatherB.child1));
        System.out.println("fatherA.child1 hashcode:" + fatherA.child1.hashCode());
        System.out.println("fatherB.child1 hashcode:" + fatherB.child1.hashCode());
        //如果一个对象内部只有基本数据类型，那用 clone() 方法获取到的就是这个对象的深拷贝，而如果其内部还有引用数据类型，那用 clone() 方法就是一次浅拷贝的操作。
    }
}

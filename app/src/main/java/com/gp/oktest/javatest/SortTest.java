package com.gp.oktest.javatest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guoping on 2017/9/13.
 */

public class SortTest {
    class Dog {
        public int age;
        public String name;

        public Dog(int age, String name) {
            super();
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog [age=" + age + ", name=" + name + "]";
        }
    }

    public static void main(String[] args) {
        List<Dog> list = new ArrayList<>();
        list.add(new SortTest().new Dog(5, "DogA"));
        list.add(new SortTest().new Dog(6, "DogB"));
        list.add(new SortTest().new Dog(7, "DogC"));
        Collections.sort(list, new Comparator<Dog>() {

            @Override
            public int compare(Dog o1, Dog o2) {
                return o2.age - o1.age;
            }
        });
        System.out.println("给狗狗按照年龄倒序：" + list);
        Collections.sort(list, new Comparator<Dog>() {

            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        System.out.println("给狗狗按名字字母顺序排序：" + list);
    }


}
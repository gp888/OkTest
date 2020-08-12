package com.gp.oktest.jetpack;


enum Season {
    //    SPRING,SUMMER,FALL,WINTER;//默认public static final，这都是Season类的对象。
    SPRING {
        public void show() {
            System.out.println("I'm Spring");
        }
    }, SUMMER, FALL, WINTER;

    private String name;

    //构造器默认且只能是private
    Season() {
        System.out.println("Season run...");//运行四次
    }

    public void setName() {
        //Java5开始,switch方法支持枚举类对象。
        switch (this) {
            case SPRING:
                name = "春";
                break;
            case SUMMER:
                name = "夏";
                break;
            case FALL:
                name = "秋";
                break;
            case WINTER:
                name = "冬";
                break;
        }
    }

    public void setName1(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void show() {
        System.out.println("I'm show");
    }
}
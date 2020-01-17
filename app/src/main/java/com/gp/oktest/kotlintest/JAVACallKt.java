package com.gp.oktest.kotlintest;

public class JAVACallKt {
    public static void main(String[] args) {
        TopLevelClass.Companion.doSomeStuff();
        TopLevelClass.FakeCompanion.INSTANCE.doOtherStuff();
    }
}

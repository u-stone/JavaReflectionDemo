package com.demo.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心目标类，包含丰富的反射场景元素。
 */
@CustomAnnotation(value = "Class Annotation Example", priority = 10)
public class TargetClass implements TargetInterface {

    // 各种修饰符的字段
    @CustomAnnotation("Public Field Annotation")
    public String publicField = "Initial Public Value";

    @CustomAnnotation("Private Field Annotation")
    private String privateField = "Initial Private Value";

    public static String staticField = "Initial Static Value";

    private final String finalField = "Final Value (Should not change easily)";

    // 泛型字段
    public List<String> stringList = new ArrayList<>();

    // 构造函数
    public TargetClass() {
        System.out.println("Default Public Constructor Called");
    }

    private TargetClass(String initialPrivateValue) {
        System.out.println("Private Constructor Called with: " + initialPrivateValue);
        this.privateField = initialPrivateValue;
    }

    // 实现接口方法
    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name + " from TargetClass!");
    }

    @Override
    @CustomAnnotation("Method Annotation")
    public String performAction(String actionName) {
        return "Action '" + actionName + "' performed successfully.";
    }

    // 私有方法
    private String privateMethod(int count, String prefix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(prefix).append(" ").append(i).append("; ");
        }
        return sb.toString();
    }

    // 静态方法
    public static int add(int a, int b) {
        return a + b;
    }

    // 用于展示结果的简单 Getter
    public String getPrivateField() {
        return privateField;
    }
}

package com.demo.reflection;

/**
 * 一个简单的接口，用于演示如何通过反射获取接口信息
 * 以及用于后续的动态代理演示。
 */
public interface TargetInterface {
    void sayHello(String name);
    String performAction(String actionName);
}

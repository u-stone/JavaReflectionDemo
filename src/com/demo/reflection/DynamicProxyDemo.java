package com.demo.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 演示 Java 动态代理 (Dynamic Proxy) 的机制。
 */
public class DynamicProxyDemo {

    public static void main(String[] args) {
        // 1. 创建被代理的真实对象
        TargetInterface realObject = new TargetClass();

        // 2. 创建调用处理器 (InvocationHandler)
        // 我们在这里定义拦截逻辑
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 在目标方法执行前的逻辑 (类似 AOP 前置通知)
                System.out.println("[日志拦截] 准备执行方法: " + method.getName());
                
                long startTime = System.currentTimeMillis();

                // 执行真实对象的方法
                Object result = method.invoke(realObject, args);

                long endTime = System.currentTimeMillis();

                // 在目标方法执行后的逻辑 (类似 AOP 后置通知)
                System.out.println("[日志拦截] 方法执行完毕，耗时: " + (endTime - startTime) + "ms");
                
                return result;
            }
        };

        // 3. 动态生成代理对象
        // 参数：类加载器、要实现的接口数组、调用处理器
        TargetInterface proxyInstance = (TargetInterface) Proxy.newProxyInstance(
                TargetInterface.class.getClassLoader(),
                new Class[]{TargetInterface.class},
                handler
        );

        // 4. 通过代理对象调用方法
        System.out.println("--- 通过代理对象调用 sayHello ---");
        proxyInstance.sayHello("Dynamic Proxy User");

        System.out.println("\n--- 通过代理对象调用 performAction ---");
        String actionResult = proxyInstance.performAction("Reflective Task");
        System.out.println("得到的结果: " + actionResult);

        // 5. 验证代理对象的类名
        System.out.println("\n代理对象的类名: " + proxyInstance.getClass().getName());
    }
}

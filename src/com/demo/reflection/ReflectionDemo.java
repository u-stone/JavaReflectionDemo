package com.demo.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * 全面演示 Java 反射各项核心能力的 Demo 类。
 */
public class ReflectionDemo {

    public static void main(String[] args) {
        try {
            System.out.println("========== 场景一：类元数据信息提取 ==========");
            analyzeClassMetadata();

            System.out.println("\n========== 场景二：构造函数与对象实例化 ==========");
            TargetClass instance = demonstrateInstantiation();

            System.out.println("\n========== 场景三：读写各种类型的字段 ==========");
            demonstrateFieldAccess(instance);

            System.out.println("\n========== 场景四：动态方法调用 ==========");
            demonstrateMethodInvocation(instance);

            System.out.println("\n========== 场景五：注解解析 ==========");
            demonstrateAnnotationReflection(instance);

            System.out.println("\n========== 场景六：泛型信息获取 ==========");
            demonstrateGenericInfo();

            System.out.println("\n========== 场景七：动态数组操作 ==========");
            demonstrateArrayReflection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void analyzeClassMetadata() {
        Class<?> clazz = TargetClass.class;
        System.out.println("类名: " + clazz.getName());
        System.out.println("简易类名: " + clazz.getSimpleName());
        System.out.println("修饰符: " + Modifier.toString(clazz.getModifiers()));
        System.out.println("父类: " + clazz.getSuperclass().getSimpleName());

        Class<?>[] interfaces = clazz.getInterfaces();
        System.out.print("实现接口: ");
        for (Class<?> i : interfaces) {
            System.out.print(i.getSimpleName() + " ");
        }
        System.out.println();
    }

    private static TargetClass demonstrateInstantiation() throws Exception {
        Class<TargetClass> clazz = TargetClass.class;

        // 使用公开无参构造函数
        TargetClass obj1 = clazz.getDeclaredConstructor().newInstance();
        System.out.println("使用公有无参构造实例化成功: " + obj1);

        // 使用私有带参构造函数
        Constructor<TargetClass> privateConstructor = clazz.getDeclaredConstructor(String.class);
        privateConstructor.setAccessible(true); // 突破封装性！
        TargetClass obj2 = privateConstructor.newInstance("Custom Private Value");
        System.out.println("使用私有构造实例化成功，当前私有属性值: " + obj2.getPrivateField());

        return obj2;
    }

    private static void demonstrateFieldAccess(TargetClass obj) throws Exception {
        Class<?> clazz = obj.getClass();

        // 访问公有字段
        Field publicField = clazz.getField("publicField");
        System.out.println("原公有字段值: " + publicField.get(obj));
        publicField.set(obj, "Modified Public Value");
        System.out.println("修改后公有字段值: " + obj.publicField);

        // 访问私有字段
        Field privateField = clazz.getDeclaredField("privateField");
        privateField.setAccessible(true); // 突破封装性！
        System.out.println("原私有字段值: " + privateField.get(obj));
        privateField.set(obj, "Modified Private Value by Reflection");
        System.out.println("通过 Getter 确认修改后私有字段值: " + obj.getPrivateField());

        // 访问静态字段
        Field staticField = clazz.getDeclaredField("staticField");
        System.out.println("原静态字段值: " + staticField.get(null)); // 静态字段 get 可传 null
        staticField.set(null, "New Static Global Value");
        System.out.println("修改后静态字段值: " + TargetClass.staticField);
    }

    private static void demonstrateMethodInvocation(TargetClass obj) throws Exception {
        Class<?> clazz = obj.getClass();

        // 调用公有接口方法
        Method sayHelloMethod = clazz.getMethod("sayHello", String.class);
        sayHelloMethod.invoke(obj, "Reflective User");

        // 调用私有方法并传递多参
        Method privateMethod = clazz.getDeclaredMethod("privateMethod", int.class, String.class);
        privateMethod.setAccessible(true); // 突破封装性！
        Object result = privateMethod.invoke(obj, 3, "Prefix");
        System.out.println("私有方法调用结果: " + result);

        // 调用静态方法
        Method addMethod = clazz.getMethod("add", int.class, int.class);
        Object sum = addMethod.invoke(null, 10, 20); // 静态方法调用可传 null 实例
        System.out.println("静态方法调用结果 (10+20): " + sum);
    }

    private static void demonstrateAnnotationReflection(TargetClass obj) throws Exception {
        Class<?> clazz = obj.getClass();

        // 类级别注解
        if (clazz.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation ann = clazz.getAnnotation(CustomAnnotation.class);
            System.out.println("类注解值: " + ann.value() + ", 优先级: " + ann.priority());
        }

        // 方法级别注解
        Method method = clazz.getMethod("performAction", String.class);
        if (method.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation ann = method.getAnnotation(CustomAnnotation.class);
            System.out.println("方法注解内容: " + ann.value());
        }

        // 字段级别注解
        Field field = clazz.getField("publicField");
        if (field.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation ann = field.getAnnotation(CustomAnnotation.class);
            System.out.println("公有字段注解内容: " + ann.value());
        }
    }

    private static void demonstrateGenericInfo() throws Exception {
        Class<?> clazz = TargetClass.class;
        Field field = clazz.getField("stringList");

        // 获取泛型类型
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType;
            System.out.println("字段全类型: " + pType.getTypeName());
            System.out.println("泛型参数真实类型: " + pType.getActualTypeArguments()[0].getTypeName());
        }
    }

    private static void demonstrateArrayReflection() {
        // 动态创建数组
        int size = 5;
        Object arrayInstance = Array.newInstance(String.class, size);

        // 动态赋值
        Array.set(arrayInstance, 0, "Hello");
        Array.set(arrayInstance, 1, "Reflection");
        Array.set(arrayInstance, 2, "Array");

        // 动态读取
        System.out.print("动态数组内容: ");
        for (int i = 0; i < size; i++) {
            System.out.print(Array.get(arrayInstance, i) + " ");
        }
        System.out.println("\n数组类型确认: " + arrayInstance.getClass().getSimpleName());
    }
}

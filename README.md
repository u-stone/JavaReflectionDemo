# Java Reflection Demo - 运行指南

本项目是一个用于演示 Java 动态反射（Reflection）核心功能及高级场景（如动态代理、注解解析、泛型获取等）的实验性项目。

## 1. 环境准备

在开始之前，请确保您的开发环境满足以下要求：

*   **JDK**: 已安装 JDK 8 或更高版本（建议使用 JDK 11 或 JDK 17）。
*   **环境变量**: `JAVA_HOME` 已配置，且 `java` 和 `javac` 命令在您的系统 `PATH` 中可用。
*   **编辑器**: 推荐使用 **Visual Studio Code**，并安装以下插件：
    *   [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) (微软官方出品)

## 2. 项目结构

```text
JavaReflectionDemo/
├── .vscode/
│   └── launch.json          # VS Code 调试配置
├── src/
│   └── com/demo/reflection/
│       ├── CustomAnnotation.java   # 自定义运行时注解
│       ├── TargetInterface.java     # 演示用的基础接口
│       ├── TargetClass.java         # 核心反射目标类
│       ├── ReflectionDemo.java      # 综合反射功能演示主类
│       └── DynamicProxyDemo.java    # 动态代理机制演示主类
├── bin/                      # 编译后的类文件存放目录（运行脚本后生成）
└── README.md                 # 本说明文档
└── REFLECTION_GUIDE.md       # Java 反射深度原理与指南
```

## 3. 命令行运行方式

如果您习惯使用终端，可以按照以下步骤手动编译并运行：

### 编译项目
在项目根目录下执行：
```bash
mkdir -p bin
javac -d bin src/com/demo/reflection/*.java
```

### 运行综合演示 (ReflectionDemo)
```bash
java -cp bin com.demo.reflection.ReflectionDemo
```

### 运行动态代理演示 (DynamicProxyDemo)
```bash
java -cp bin com.demo.reflection.DynamicProxyDemo
```

## 4. 在 VS Code 中进行可视化调试 (推荐)

本项目已预配置了 `.vscode/launch.json`，您可以直接在编辑器中打断点观察反射的执行过程：

1.  **打开项目**: 使用 VS Code 打开 `JavaReflectionDemo` 文件夹。
2.  **设置断点**: 打开 `src/com/demo/reflection/ReflectionDemo.java`，在您感兴趣的反射调用行（如 `field.setAccessible(true)` 或 `method.invoke(...)`）点击行号左侧设置断点。
3.  **启动调试**:
    *   点击左侧活动栏的 **“运行和调试”** 图标 (Ctrl+Shift+D)。
    *   在顶部的下拉菜单中选择 **"Reflection Demo"** 或 **"Dynamic Proxy Demo"**。
    *   点击绿色的运行按钮或按 **F5**。
4.  **观察变量**: 此时程序会停在断点处。您可以查看“变量”面板，观察反射是如何“无视”封装性直接读取和修改 `TargetClass` 内部私有变量的值。

## 5. 进一步学习
关于 Java 反射的底层实现原理、JVM 内部机制以及具体的编写最佳实践，请参阅本项目中的 [REFLECTION_GUIDE.md](./REFLECTION_GUIDE.md)。

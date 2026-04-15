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
├── src/                     # 源代码目录
├── bin/                     # 编译产物目录
├── .project                 # Eclipse 项目描述文件（用于 VS Code 识别项目）
├── .classpath               # Eclipse 类路径文件（用于 VS Code 识别源码根目录）
├── README.md                # 本运行指南
├── REFLECTION_GUIDE.md      # Java 反射深度原理指南
└── TROUBLESHOOTING.md       # 疑难排查手册（包含环境配置报错解决）
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

### 4.1 设置断点 (Breakpoints)
*   打开 `src/com/demo/reflection/ReflectionDemo.java`。
*   在代码行号左侧点击，设置红色小圆点（断点）。
*   **推荐位置**：
    *   `field.setAccessible(true);`：观察反射如何突破私有权限。
    *   `method.invoke(obj, ...);`：观察私有方法的动态调用。

### 4.2 启动调试会话
1.  **打开项目**: 使用 VS Code 打开 `JavaReflectionDemo` 文件夹。
2.  **选择配置**: 点击左侧活动栏的 **“运行和调试”** 图标 (Ctrl+Shift+D)。
3.  **开始运行**: 在顶部的下拉菜单中选择 **"Reflection Demo"** 或 **"Dynamic Proxy Demo"**，点击绿色的播放按钮或按 **F5**。

### 4.3 核心观察点（反射特有）
程序暂停在断点时，请重点关注：
*   **变量 (Variables) 面板**: 展开 `instance` 对象，观察原本无法直接访问的 `privateField` 如何被反射读取并实时修改。
*   **调试控制台 (Debug Console)**: 您可以直接输入表达式（如 `instance.getPrivateField()`）来实时验证反射修改后的结果。
*   **堆栈跟踪 (Call Stack)**: 观察 `ReflectionDemo` 如何通过 `Method.invoke` 进入目标方法。

### 4.4 动态代理调试
如果您调试的是 **Dynamic Proxy Demo**:
*   在 `InvocationHandler` 的 `invoke` 方法内打断点。
*   当您通过代理对象调用方法时，调试器会立即跳转到处理器中。
*   观察代理对象的实际类型，通常显示为 `$Proxy0`。

## 5. 进一步学习与疑难解答
*   **深度原理解析**: 参阅 [REFLECTION_GUIDE.md](./REFLECTION_GUIDE.md)。
*   **常见问题与报错 (珍贵的资产)**: 参阅 [TROUBLESHOOTING.md](./TROUBLESHOOTING.md)。

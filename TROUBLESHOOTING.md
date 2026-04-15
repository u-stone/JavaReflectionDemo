# 疑难排查手册 (Troubleshooting Guide)

本手册记录了在运行或调试本项目过程中可能遇到的常见问题及其解决方案。**正如开发者所言：“错误是宝贵的资产”，每一次排错都是对底层机制的深入理解。**

---

## 1. VS Code 报错：Configured debug type 'java' is not supported

### 问题描述
在尝试启动调试（按 F5）时，VS Code 弹出错误提示：`Configured debug type 'java' is not supported.`，导致无法进入调试模式。

### 核心原因
这个错误通常意味着 VS Code 的 Java 语言支持（Java Language Server）尚未就绪，或者调试插件未正确加载。

### 解决方案 (按推荐顺序尝试)

#### 方案 A：确保安装了官方插件包 (最常见原因)
VS Code 原生不支持 Java 调试。
1.  进入 **Extensions (Ctrl+Shift+X)**。
2.  搜索并安装 **[Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)** (由 Microsoft 提供)。
3.  它会自动包含 `Language Support for Java™` 和 `Debugger for Java`。

#### 方案 B：等待 Java 语言服务器启动
VS Code 打开 Java 项目后需要一定时间解析类路径。
1.  观察右下角状态栏。
2.  如果显示一个转动的图标或 `Opening Java Projects`，请耐心等待。
3.  只有当状态栏显示 `Java: Ready` 时，调试器才能识别 `java` 类型。

#### 方案 C：清理语言服务器工作区 (强制重启)
如果插件已安装但仍无法工作，可能是缓存损坏。
1.  按 `Ctrl+Shift+P` (Mac: `Cmd+Shift+P`) 调出命令面板。
2.  输入并选择：`Java: Clean Java Language Server Workspace`。
3.  在确认弹窗中选择 `Restart and delete`。

#### 方案 D：以“文件夹”形式打开项目
VS Code 的 Java 调试器依赖于项目的上下文。
1.  请确保您是使用 `File -> Open Folder...` 打开的 **`JavaReflectionDemo`** 根目录。
2.  不要直接打开单个 `.java` 文件，这会导致调试器无法关联 `.vscode/launch.json` 配置。

---

## 2. 编译错误：程序包 com.demo.reflection 不存在

### 问题描述
在执行 `java` 命令时报错：`Error: Could not find or load main class com.demo.reflection.ReflectionDemo`。

### 核心原因
Java 运行时的类路径（Classpath）配置不正确。

### 解决方案
1.  **检查编译目录**：确保执行过 `javac -d bin ...` 命令，且 `bin` 目录下存在包结构（如 `bin/com/demo/reflection/`）。
2.  **指定类路径**：运行命令时必须包含 `-cp bin` 参数：
    ```bash
    java -cp bin com.demo.reflection.ReflectionDemo
    ```

---

## 3. 插件激活失败：Test Runner for Java 依赖缺失

### 问题描述
启动 VS Code 时弹出错误：`Cannot activate the 'Test Runner for Java' extension because it depends on the 'Language Support for Java(TM) by Red Hat' extension from 'Red Hat', which is not installed.`

### 核心原因
VS Code 的 Java 功能是分层加载的。`Test Runner`（测试运行器）属于高级功能，它必须建立在 `Language Support`（核心语法支持）之上。由于某种原因（如手动删除了部分插件或安装中断），核心插件丢失，导致高级功能无法激活。

### 解决方案
1.  **直接安装**：点击报错弹窗右下角的 **Install**。
2.  **手动补全**：
    *   进入 Extensions 搜索并安装 `Language Support for Java(TM) by Red Hat`。
    *   或者直接搜索并安装 **Extension Pack for Java**，它会自动确保所有必需的 6 个核心组件全部就绪。

---

## 结语
记录每一个报错，不仅是为了解决当下的问题，更是为了构建属于自己的知识库。欢迎随时补充您遇到的其他“资产”。

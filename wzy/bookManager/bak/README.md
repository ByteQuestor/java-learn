# 图书管理系统

项目地址：`https://github.com/ByteQuestor/java-learn.git`

## 一、程序入口

```java
package main;

import view.bookManager;

public class Main {
    public static void main(String[] args) {
        // 创建并显示管理系统的UI界面
        new bookManager().createUI();
    }
}
```

## 二、界面框架设计

```java
package view;

import javax.swing.*;
import java.awt.*;

public class bookManager {
	private JFrame frame;
	private JPanel sidePanel; // 左侧导航栏
	private JPanel topPanel; // 顶部工具栏
	private JPanel contentPanel; // 中间内容区

	// 主函数作为程序入口
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new bookManager().createUI());
	}

	// 创建UI界面的方法
	public void createUI() {
		frame = new JFrame("图书管理系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLayout(new BorderLayout());

		// 主面板
		JPanel mainPanel = new JPanel(new BorderLayout());

		// 1. 左侧导航面板
		sidePanel = new JPanel();
		sidePanel.setBackground(Color.LIGHT_GRAY); // 设置背景色
		sidePanel.setPreferredSize(new Dimension(200, 600)); // 设置左侧面板的宽度
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // 垂直排列

		// 添加一个可伸缩的空白区域
		sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域

		// 在左侧导航栏中添加标签
		JLabel navigationLabel = new JLabel("这是导航区域", JLabel.CENTER);
		navigationLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
		navigationLabel.setAlignmentY(Component.CENTER_ALIGNMENT); // 垂直居中
		sidePanel.add(navigationLabel); // 添加标签到左侧导航栏

		// 添加另一个可伸缩的空白区域，保持垂直居中
		sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域

		
		// 2. 顶部工具栏
		topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY); // 设置背景色
		topPanel.setPreferredSize(new Dimension(1000, 50)); // 设置顶部面板的高度
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 水平排列

		// 添加简单的标签到顶部
		JLabel systemTitleLabel = new JLabel("图书管理系统");
		systemTitleLabel.setForeground(Color.WHITE);
		topPanel.add(systemTitleLabel);

		// 3. 中间内容面板（使用CardLayout）
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE); // 设置内容区的背景色
		contentPanel.setLayout(new BorderLayout()); // 用BorderLayout管理内容区

		// 添加简单的标签到内容区
		JLabel contentLabel = new JLabel("这是内容区域", JLabel.CENTER);
		contentPanel.add(contentLabel, BorderLayout.CENTER);

		// 将左侧面板、顶部面板和中间面板加入主面板
		mainPanel.add(sidePanel, BorderLayout.WEST);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// 将主面板添加到框架中
		frame.add(mainPanel);
		frame.setVisible(true);
	}
}
```

效果

![image-20241216204432919](./README.assets/image-20241216204432919.png)

## 三、说明书

`bookManager` 类的代码说明书，包括代码的功能、设计、组件、布局等详细说明。可以参考此文档来了解每个部分的实现。

------

### 1. **项目概述**

该代码实现了一个简单的图书管理系统界面。界面包括了三个主要区域：左侧导航栏、顶部工具栏和中间内容区。界面采用 `JFrame` 作为主窗口，通过 `BorderLayout` 和 `BoxLayout` 布局来组织各个组件的显示，且通过 `CardLayout` 来处理不同内容的切换。

#### 主要功能：

- 左侧导航栏：显示导航标签，具有可伸缩的空白区域，垂直居中显示标签。
- 顶部工具栏：显示系统标题，背景为深色。
- 中间内容区：显示内容区域，使用 `BorderLayout` 布局，并且可以根据需要更新内容。

------

### 2. **代码说明**

#### 2.1. **类声明**

```java
public class bookManager {
    private JFrame frame;
    private JPanel sidePanel; // 左侧导航栏
    private JPanel topPanel; // 顶部工具栏
    private JPanel contentPanel; // 中间内容区
```

- `frame`: 主窗口（`JFrame`）对象，作为图书管理系统的根容器。
- `sidePanel`: 左侧导航面板，包含导航标签和可伸缩的空白区域。
- `topPanel`: 顶部工具栏，显示系统的标题。
- `contentPanel`: 中间内容区，展示系统内容或其他功能的区域。

#### 2.2. **主函数入口**

```java
java复制代码public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new bookManager().createUI());
}
```

- 通过 `SwingUtilities.invokeLater` 确保 UI 创建在事件派发线程上，避免线程安全问题。

#### 2.3. **UI创建方法**

```java
java复制代码public void createUI() {
    frame = new JFrame("图书管理系统");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 600);
    frame.setLayout(new BorderLayout());
```

- `JFrame` 作为应用的主窗口，设置窗口标题为 "图书管理系统"，窗口关闭时退出程序，设置窗口大小为 1000x600。

####  2.4. **左侧导航栏**

```java
java复制代码sidePanel = new JPanel();
sidePanel.setBackground(Color.LIGHT_GRAY); // 设置背景色
sidePanel.setPreferredSize(new Dimension(200, 600)); // 设置左侧面板的宽度
sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // 垂直排列
```

- 使用 `BoxLayout` 布局管理左侧导航面板 (`sidePanel`) 的组件排列，使得它们垂直排列。
- 设置左侧面板的背景色为浅灰色，宽度为 200px，高度为 600px。

##### **添加可伸缩的空白区域**：

```java
sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域
```

- 通过 `Box.createVerticalGlue()` 来添加可伸缩的空白区域，确保导航标签在垂直方向上居中显示。

##### **导航标签**：

```java
JLabel navigationLabel = new JLabel("这是导航区域", JLabel.CENTER);
navigationLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
navigationLabel.setAlignmentY(Component.CENTER_ALIGNMENT); // 垂直居中
sidePanel.add(navigationLabel); // 添加标签到左侧导航栏
```

- 创建一个 `JLabel` 作为导航区域的标题，设置为水平和垂直居中，并添加到左侧面板。

##### **添加可伸缩的空白区域**（保持垂直居中）：

```java
sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域
```

#### 2.5. **顶部工具栏**

```java
topPanel = new JPanel();
topPanel.setBackground(Color.DARK_GRAY); // 设置背景色
topPanel.setPreferredSize(new Dimension(1000, 50)); // 设置顶部面板的高度
topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 水平排列
```

- 顶部工具栏采用 `FlowLayout` 布局，左对齐。
- 设置背景色为深灰色，设置高度为 50px，宽度为 1000px。

##### **添加系统标题标签**：

```java
JLabel systemTitleLabel = new JLabel("图书管理系统");
systemTitleLabel.setForeground(Color.WHITE);
topPanel.add(systemTitleLabel);
```

- 创建一个 `JLabel` 作为系统的标题，文字颜色设置为白色，并添加到顶部工具栏。

#### 2.6. **中间内容区**

```java
contentPanel = new JPanel();
contentPanel.setBackground(Color.WHITE); // 设置内容区的背景色
contentPanel.setLayout(new BorderLayout()); // 用BorderLayout管理内容区
```

- 中间内容区使用 `BorderLayout` 布局管理，使得可以方便地在其中添加不同的组件（如功能面板、信息区域等）。

##### **添加简单标签**：

```java
JLabel contentLabel = new JLabel("这是内容区域", JLabel.CENTER);
contentPanel.add(contentLabel, BorderLayout.CENTER);
```

- 在内容区添加一个标签，居中显示 "这是内容区域"。

#### 2.7. **添加面板到主窗口**

```java
mainPanel.add(sidePanel, BorderLayout.WEST);
mainPanel.add(topPanel, BorderLayout.NORTH);
mainPanel.add(contentPanel, BorderLayout.CENTER);
```

- 将左侧导航面板 (`sidePanel`)、顶部工具栏 (`topPanel`) 和中间内容区 (`contentPanel`) 添加到主面板 `mainPanel` 中，分别放置在 `BorderLayout` 的西侧、北侧和中间。

#### 2.8. **显示界面**

```java
frame.add(mainPanel);
frame.setVisible(true);
```

- 最后将 `mainPanel` 添加到 `frame` 中，并显示界面。

------

### 3. **总结**

#### 界面设计：

- **左侧导航栏**：垂直居中显示导航标签，使用 `BoxLayout` 和 `Box.createVerticalGlue()` 来管理组件布局，确保标签垂直居中。
- **顶部工具栏**：使用 `FlowLayout` 布局，左对齐，显示系统标题。
- **中间内容区**：使用 `BorderLayout` 管理，方便在中间区域显示各种内容。

#### 布局管理：

- 使用 `BorderLayout` 管理主界面和各个面板的位置。
- 左侧导航面板使用 `BoxLayout`，并通过 `Box.createVerticalGlue()` 来控制垂直居中。
- 顶部工具栏使用 `FlowLayout`，使得组件左对齐。

------

### 4. **可以改进的地方**

- 目前内容区域的标签是静态的，可以根据用户的操作来动态切换内容（比如使用 `CardLayout`）。
- 侧边导航栏可以进一步增加按钮，提升交互性。
- 可以为各个区域添加更多功能，如按钮、输入框等，完成更丰富的图书管理功能。
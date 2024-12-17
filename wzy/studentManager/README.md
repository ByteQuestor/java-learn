# 学生管理系统

# 一、数据库设计

**数据库打包在`sql/students.sql`里，运行即可复原**

系统的主要功能是学生信息管理，包括查询、添加、更新和删除学生数据。这里我们设计一个简单的数据库结构，包括一个学生信息表（`students`）

**数据表：`students`**

这个表存储学生的基本信息，如学号、姓名、性别、年龄等。

```sql
CREATE DATABASE students;
USE students;
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `students` VALUES ('1', '蔡青茹', '女', '2000-01-01', '13900000001', '北京市海淀区');
INSERT INTO `students` VALUES ('2', 'wzy', '男', '2004-03-10', '13900000002', '上海市浦东新区');
INSERT INTO `students` VALUES ('3', '王五', '男', '2001-05-15', '13900000003', '广州市天河区');
INSERT INTO `students` VALUES ('4', '赵六', '女', '2000-07-20', '13900000004', '深圳市南山区');
INSERT INTO `students` VALUES ('5', '周七', '男', '1998-08-30', '13900000005', '重庆市渝中区');
```

**字段解释**

1. `student_id`: 学生的唯一标识符（主键），自增长。
2. `name`: 学生姓名。
3. `gender`: 学生的性别（使用枚举类型，可以扩展更多的性别选项）。
4. `age`: 学生的年龄。
5. `grade`: 学生的年级。
6. `major`: 学生的专业。
7. `created_at`: 学生信息的创建时间，默认为当前时间。
8. `updated_at`: 学生信息的最后更新时间，当更新时自动更新时间。

# 二、设计模式

为 MVC（Model-View-Controller）模式，并进行模块化设计，我们可以将界面、逻辑处理、数据操作分开。MVC 模式将帮助实现代码的解耦，使得不同的功能模块可以更容易地更新和维护。

### 1. **MVC 架构概述**

- **Model（模型）**：负责数据的管理、处理和存储，独立于用户界面。
- **View（视图）**：负责显示用户界面，并响应用户的操作，通常由 JFrame、JPanel、JButton 等 Swing 组件构成。
- **Controller（控制器）**：负责处理用户输入并更新视图和模型。它接收来自视图的事件，调用模型中的方法，并将数据返回到视图。

### 2. **重构后的代码结构**

#### 项目结构

```
src/
├── controller/
│   └── StudentController.java
├── model/
│   └── StudentModel.java
└── view/
    ├── StudentManagerView.java
    └── PanelFactory.java
```

- **controller**：负责与模型和视图的交互，处理用户操作的逻辑。
- **model**：管理数据（学生信息），包含对数据的增删查改操作。
- **view**：负责显示界面和用户交互。
  - `StudentManagerView`：负责整个界面的布局和展示。
  - `PanelFactory`：负责生成不同的功能面板，便于模块化管理。

## 三、代码实现

### 1，数据库相关代码

#### 1.1，数据库连接测试



#### 1.2，数据库数据接收




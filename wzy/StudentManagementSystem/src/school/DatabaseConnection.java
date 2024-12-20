package school;

/*
 * 
 * 
 * 在model里已经实现此类，可以参考或者删除这个类
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://localhost:80/student_management";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
	// 数据库连接信息
	private static final String URL = "jdbc:mysql://localhost:3306/student_management"; // 把students替换成你的数据库，数据库默认端口是3306，不知道你是不是改成80了
	private static final String USER = "root"; // 替换为你的数据库用户名
	private static final String PASSWORD = "000000"; // 替换为你的数据库密码

	// 这里去掉了错误写法的配置信息，那些配置信息应该按合适方式使用，而不是直接写在这里

	public static Connection getConnection() throws SQLException {
		try {
			// 加载 MySQL 驱动，在JDBC4.0及以上如果已经添加驱动依赖到类路径，这行可以省略，不过保留也没问题
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// 提供更详细的错误提示，告知可能缺少的依赖等信息
			String errorMsg = "数据库驱动加载失败，请确保已添加MySQL JDBC驱动依赖到项目中，详细错误: " + e.getMessage();
			throw new SQLException(errorMsg, e);
		}

		try {
			// 建立连接
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			// 根据不同的连接错误情况，尝试给出更具体的提示信息
			if (e.getMessage().contains("Access denied")) {
				throw new SQLException("数据库连接被拒绝，请检查用户名和密码是否正确", e);
			} else if (e.getMessage().contains("Communications link failure")) {
				throw new SQLException("数据库连接通信失败，请检查数据库服务是否启动以及网络是否正常", e);
			}
			// 其他未知的连接异常，直接抛出原始异常
			throw e;
		}
	}

	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			System.out.println("数据库连接成功");
			// 这里可以进行后续使用连接的操作，比如执行SQL语句等，此处简单打印表示连接成功
			connection.close(); // 使用完连接记得关闭，释放资源
		} catch (SQLException e) {
			System.err.println("数据库连接失败: " + e.getMessage());
		}
	}
}// package school;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
//    driverClassName=com.mysql.cj.jdbc.Driver
//    		url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
//    		username=root
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            // 加载 MySQL 驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            // 提供更详细的错误提示，告知可能缺少的依赖等信息
//            String errorMsg = "数据库驱动加载失败，请确保已添加MySQL JDBC驱动依赖到项目中，详细错误: " + e.getMessage();
//            throw new SQLException(errorMsg, e);
//        }
//
//        try {
//            // 建立连接
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            // 根据不同的连接错误情况，尝试给出更具体的提示信息
//            if (e.getMessage().contains("Access denied")) {
//                throw new SQLException("数据库连接被拒绝，请检查用户名和密码是否正确", e);
//            } else if (e.getMessage().contains("Communications link failure")) {
//                throw new SQLException("数据库连接通信失败，请检查数据库服务是否启动以及网络是否正常", e);
//            }
//            // 其他未知的连接异常，直接抛出原始异常
//            throw e;
//        }
//    }
//}
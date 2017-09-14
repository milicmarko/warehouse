package helpClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {

	private DbConn() {

	}

	public static Connection getConnection() throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:sqlserver:****;databaseName=****;userName=****;password=****");
		return conn;
	}
}

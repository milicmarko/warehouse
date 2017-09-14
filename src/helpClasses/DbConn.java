package helpClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {

	private DbConn() {

	}

	public static Connection getConnection() throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:sqlserver://slis;databaseName=MarkoTest;userName=marko;password=Euro2017");
		return conn;
	}
}

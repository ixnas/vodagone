package vodagone.data;

import java.sql.*;

public class DataAccess {

	private Connection conn;

	private void showError (SQLException s) {
		System.out.println ("SQLException: " + s.getMessage ());
		System.out.println ("SQLState: " + s.getSQLState ());
		System.out.println ("VendorError: " + s.getErrorCode ());
	}

	public DataAccess () {
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver").newInstance ();
			try {
				conn = DriverManager.getConnection ("jdbc:mysql://127.0.0.1/test?" + "user=root&serverTimezone=UTC");
			} catch (SQLException s) {
				System.out.println ("SQLException: " + s.getMessage ());
				System.out.println ("SQLState: " + s.getSQLState ());
				System.out.println ("VendorError: " + s.getErrorCode ());
			}
		} catch (Exception e) {
			System.out.println (e);
		}
	}

	public ResultSet query (PreparedStatement st) {
		try {
			return st.executeQuery ();
		} catch (Exception s) {
			s.printStackTrace ();
			return null;
		}
	}

	public Connection getConnection () {
		return conn;
	}

}

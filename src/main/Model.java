package main;

import java.sql.*;
import java.util.Random;

public class Model {

	public static final String CREATE_TABLE_PERSON_QUERY = "CREATE TABLE IF NOT EXISTS person(id INT PRIMARY KEY AUTO_INCREMENT, "
			+ " first_name VARCHAR(255), last_name VARCHAR(255), hobby VARCHAR(255), profession varchar(255), job VARCHAR(255), "
			+ "age INT, email varchar(255), gender char(1), location varchar(255))";
	public static final String INSERT_TEST_PERSON = "INSERT INTO person(first_name, age) VALUES (?, ?)";
	public static final String INSERT_PERSON = "INSERT INTO person(first_name, last_name, hobby, profession, job, age, email, gender, "
			+ "location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String DELETE_ROW = "DELETE FROM person WHERE id=(?)";
	public static final String UPDATE_ROW = "UPDATE person SET first_name=(?), age=(?) WHERE id=(?)";
	public static final String SELECT_ALL = "SELECT * FROM person";
	private EnterParameters enterParameters = new EnterParameters();
	private Random random = new Random();

	public void createTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(CREATE_TABLE_PERSON_QUERY);
		statement.close();
		createTestData(connection);
	}

	/**
	 * Creates test data to fill the table if it is empty	 
	 */
	private void createTestData(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
		ResultSet rs = preparedStatement.executeQuery();
		if (!rs.next()) {
			String[] names = { "Mykola", "Petro", "Ivan", "Taras" };
			PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_TEST_PERSON);
			for (int i = 0; i < 10; i++) {
				preparedStatement2.setString(1, names[random.nextInt(names.length)]);
				preparedStatement2.setInt(2, random.nextInt(90) + 10);
				preparedStatement2.executeUpdate();
			}
			preparedStatement2.close();
			preparedStatement.close();
		}
	}

	public void addRow(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(INSERT_PERSON);
		System.out.println("Enter a name:");
		statement.setString(1, enterParameters.stringEnter());
		System.out.println("Enter a last name:");
		statement.setString(2, enterParameters.stringEnter());
		System.out.println("Enter a hobby:");
		statement.setString(3, enterParameters.stringEnter());
		System.out.println("Enter a profession:");
		statement.setString(4, enterParameters.stringEnter());
		System.out.println("Enter a job:");
		statement.setString(5, enterParameters.stringEnter());
		System.out.println("Enter an age:");
		statement.setInt(6, enterParameters.intEnter());
		System.out.println("Enter an email:");
		statement.setString(7, enterParameters.stringEnter());
		System.out.println("Enter a gender:");
		statement.setString(8, enterParameters.stringEnter());
		System.out.println("Enter a location:");
		statement.setString(9, enterParameters.stringEnter());
		statement.executeUpdate();
		statement.close();
	}

	public void updateRow(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(UPDATE_ROW);
		System.out.println("Enter a name:");
		statement.setString(1, enterParameters.stringEnter());
		System.out.println("Enter an age:");
		statement.setInt(2, enterParameters.intEnter());
		statement.setInt(3, enterParameters.rowNumberEnter());
		statement.executeUpdate();
		statement.close();
	}

	public void deleteRow(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETE_ROW);
		statement.setInt(1, enterParameters.rowNumberEnter());
		statement.executeUpdate();
		statement.close();
	}

	/**
	 * Prints all data from the table	 
	 */
	public void selectTable(Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
		ResultSet rs = statement.executeQuery();

		ResultSetMetaData rsmd = rs.getMetaData();
		int numCols = rsmd.getColumnCount();
		for (int i = 1; i <= numCols; i++) {
			System.out.print(rsmd.getColumnLabel(i));
			System.out.print(" ");
		}
		System.out.println("");

		while (rs.next()) {
			System.out.println(addSpase(String.valueOf(rs.getInt("id")), rsmd.getColumnLabel(1).length()) 
							 + addSpase(rs.getString("first_name"), rsmd.getColumnLabel(2).length())
							 + addSpase(rs.getString("last_name"), rsmd.getColumnLabel(3).length()) 
							 + addSpase(rs.getString("hobby"), rsmd.getColumnLabel(4).length())
							 + addSpase(rs.getString("profession"), rsmd.getColumnLabel(5).length())
							 + addSpase(rs.getString("job"), rsmd.getColumnLabel(6).length()) 
							 + addSpase(String.valueOf(rs.getInt("age")), rsmd.getColumnLabel(7).length()) 
							 + addSpase(rs.getString("email"), rsmd.getColumnLabel(8).length()) 
							 + addSpase(rs.getString("gender"), rsmd.getColumnLabel(9).length())
							 + addSpase(rs.getString("location"), rsmd.getColumnLabel(10).length()));
		}
	}

	/**
	 * Improves view of data in console	 
	 */
	private String addSpase(String s, int columnLabelLength) {
		if (s != null) {
			int numberOfSpace = columnLabelLength - s.length();
			for (int j = 0; j <= numberOfSpace; j++) {
				s += " ";
			}
		} else {
			s = "";
			for (int j = 0; j <= columnLabelLength; j++) {
				s += " ";
			}
		}
		return s;
	}
}

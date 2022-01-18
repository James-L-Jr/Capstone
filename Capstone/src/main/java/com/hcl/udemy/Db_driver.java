package com.hcl.udemy;

import java.sql.Connection;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Db_driver {
	static Logger log = Logger.getLogger(Db_driver.class.getName());
	private static final String createTableSQL = "create table users (\r\n" + "  ID int(5) not null AUTO_INCREMENT primary key,\r\n" +
			"  CREATED_AT varchar(11),\r\n" + "  DESCRIPTION varchar(30),\r\n" + "  END_DATE varchar(11),\r\n" +
			"  PROJECT_IDENTIFIER varchar(6)\r\n" + "  PROJECT_NAME varchar(15)\r\n" + " START_DATE varchar(11)\r\n" + "UPDATED_AT varchar(11));";
	private static final String INSERT_USERS_SQL = "INSERT INTO users" +
			"  (name, dob, salary, age) VALUES " +
			" (?, ?, ?, ?);";
	private static final String QUERYALL = "select id, name, dob, salary, age from users;";
	private static final String QUERY = "select id,name,dob,salary,age from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ? where id = ?;";
	private static final String deleteTableSQL = "delete from users where id = ?;";
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception{
		PropertyConfigurator.configure("C:\\Users\\James\\eclipse-workspace\\capstone\\src\\main\\resources\\log4j.properties");
		log.warn("DB accessed from Eclipse..");
		int prompt;
		int done = 0;

		//Table automatically gives ID number incrementally
		//createTable(); //before using db

		while(done == 0) {
			System.out.println("What do you want to do? ");
			System.out.println("0: Quit\n1: Insert record in DB\n2: Retrieve all employee info\n3: Retrieve employee info by Id\n"
					+ "4: Update employee info\n5: Delete select employee info\n6: Select record manually");
			prompt = sc.nextInt();
			sc.nextLine(); //absolve newline
			switch(prompt) {
			case 0:
				done = 1;
				System.out.println("\nGoodbye.");
				break;
			case 1:
				insertRecord();
				break;
			case 2:
				selectAll();
				break;
			case 3:
				select();
				break;
			case 4:
				updateRecord();
				break;
			case 5:
				deleteRecord();
				break;
			case 6:
				queryRecord();
				break;
			default:
				System.out.println("Bad input.");
				break;
			}
		}

		sc.close();
	}

	public static void createTable() throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				Statement statement = connection.createStatement();) {
			statement.execute(createTableSQL);
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void insertRecord() throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			System.out.println("Enter employee name to insert: ");
			preparedStatement.setString(1, sc.nextLine());
			System.out.println("Enter employee DOB to insert: ");
			preparedStatement.setString(2, sc.nextLine());
			System.out.println("Enter employee Salary to insert: ");
			preparedStatement.setString(3, sc.nextLine());
			System.out.println("Enter employee Age to insert: ");
			preparedStatement.setString(4, sc.nextLine());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void selectAll() throws SQLException{
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(QUERYALL);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			printTable("All employees:", rs);
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void select() throws SQLException{
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
			System.out.println("Enter employee ID to select: ");
			preparedStatement.setInt(1, sc.nextInt());
			sc.nextLine(); //absolve newline
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			printTable("The selected employee:", rs);
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void updateRecord() throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
			System.out.println("Enter ID of employee to update: ");
			preparedStatement.setInt(2, sc.nextInt());
			sc.nextLine(); //absolve newline
			System.out.println("Enter employee new name: ");
			preparedStatement.setString(1, sc.nextLine());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void deleteRecord() throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteTableSQL);) {
			System.out.println("Enter ID of employee to update: ");
			preparedStatement.setInt(1, sc.nextInt());
			sc.nextLine(); //absolve newline
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	public static void queryRecord() throws SQLException 
	{
		System.out.println("Enter selection: ");
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sc.nextLine());
				ResultSet resultSet = preparedStatement.executeQuery();){
			printTable("Selected records:", resultSet);

		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	private static void printTable(String title, ResultSet resultSet) throws SQLException 
	{    	
		System.out.printf("%s\n", title);
		System.out.printf("    %-6s %-13s %-10s %-7s %-4s\n"
				, "CustId", "Name", "DOB", "Salary", "Age");

		while (resultSet.next())
		{
			String custIdStr = resultSet.getString("id");
			String nameStr = resultSet.getString("name");
			if (nameStr == null)
				nameStr = "---";
			String dobStr = resultSet.getString("dob");
			if (dobStr == null)
				dobStr = "---";
			String salaryStr = resultSet.getString("salary");
			if (salaryStr == null)
				salaryStr = "---";
			String ageStr = resultSet.getString("age");
			if (ageStr == null)
				ageStr = "---";

			System.out.printf("    %-6s %-13s %-10s %-7s %-4s\n"
					, custIdStr
					, nameStr
					, dobStr
					, salaryStr
					, ageStr);
		}
		System.out.printf("\n"); 
	}
}

package com.hcl.udemy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapstoneApplication {

	public static void main(String[] args) {
		System.out.println("Starting up this bad boy...");
		SpringApplication.run(CapstoneApplication.class, args);
		/*
		try (Connection connection = JDBCUtils.getConnection();) {
			System.out.println("MySQL Database connected...");
			SpringApplication.run(CapstoneApplication.class, args);
			System.out.println("Web app started...");
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		*/
	}
}
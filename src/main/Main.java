package main;

/* Написати консольне меню для управління таблицею в БД, реалізувати всі можливості для додавання, редагування і видалення рядків.
 * Connection має бути в одному екземплярі для всієї програми, відкривається на початку програми, якщо потрібно створює таблицю 
 * і закривається в кінці програми. Таблиця мінімум на 10 стовпців. 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/jdbc_example?useSSL=false";
	public static final String USER = "root";
	public static final String PASSWORD = "1111";
	private Scanner scanner = new Scanner(System.in);
	private Model model = new Model();

	private void start() {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
			model.createTable(connection);
			boolean isRun = true;

			while (isRun) {
				System.out.println("Головне меню \n" 
					+ "Щоб додати рядок, введіть: 1 \n"
					+ "Щоб редагувати рядок, введіть: 2 \n"
					+ "Щоб видалити рядок, введіть: 3 \n"
					+ "Щоб вивести на екран таблицю, введіть: 4 \n"
					+ "Щоб вийти з програми, введіть: 0");

				switch (scanner.next()) {
				case "1":
					model.addRow(connection);
					break;
				case "2":
					model.updateRow(connection);
					break;
				case "3":
					model.deleteRow(connection);
					break;
				case "4":
					model.selectTable(connection);
					break;
				case "0":
					isRun = false;
					break;
				default:
					System.out.println("Неправильний вибір в меню!");
				}
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}

package main;

import java.util.Scanner;

public class EnterParameters {
	private Scanner scanner = new Scanner(System.in);

	public String stringEnter() {
		String name = scanner.next();
		return name;
	}

	public int intEnter() {
		int i = 0;
		try {
			i = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Введіть число!");
			scanner.next();
		}
		return i;
	}

	public int rowNumberEnter() {
		int age = 0;
		try {
			System.out.println("Введіть номер рядка:");
			age = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Введіть число!");
			scanner.next();
		}
		return age;
	}
}

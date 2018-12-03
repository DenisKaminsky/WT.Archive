package by.bsuir.Kaminsky.client.view;

import by.bsuir.Kaminsky.tools.ConsoleReader.ConsoleReader;

public class ArchiveView {
	
	public static void showMessage(String message) {
		System.out.println(message);
	}
	
	public static Object[] getPersonalData(){
		boolean answer;
		String login,password,attribute="";
		
		System.out.print("\nDo you have an account(Y/N): ");
		answer = ConsoleReader.getAnswer();
		if (answer)
			System.out.println("[Authorization]");
		else {
			attribute = "(minimum 5 character)";
			System.out.println("[Registration]");			
		}
		System.out.print("Input login: ");
		login = ConsoleReader.getString();
		System.out.print("Input password"+attribute+": ");
		password = ConsoleReader.getString();		
		return new Object[] {answer,login,password};
	}
	
	public static Object[] getBookData() {
		Boolean answer;
		String title,author;
				
		System.out.print("Input author: ");
		author = ConsoleReader.getString();	
		System.out.print("Input title: ");
		title = ConsoleReader.getString();
		System.out.print("Is Electronic(Y/N): ");
		answer = ConsoleReader.getAnswer();
		return new Object[] {author,title,answer};		
	}
	
	public static String getAction() {		
		System.out.print("\nInput command(/help to see more info): ");
		return ConsoleReader.getString();
	}
	
	public static String getParameter(String parameterName) {
		System.out.print("Input "+parameterName+": ");
		return ConsoleReader.getString();
	}
	
	public static void printAvailableActions() {		
        System.out.println("/exit - Exit");
        System.out.println("/logout - Log out");
        System.out.println("/show -  Show books catalog");
        System.out.println("/findt - Find book by title");
        System.out.println("/finda - Find book by author");
        System.out.println("/finde - Find electronic books");
        System.out.println("/findp - Find paper books");
        System.out.println("/mod - Modify book");
        System.out.println("/add - Add book");
        System.out.println("/delete - Delete book");
        System.out.println("/allusers - Show all users");
        System.out.println("/udelete - Delete user");        
	}
}

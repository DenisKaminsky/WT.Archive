package by.bsuir.Kaminsky.server.ImplemenationLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import by.bsuir.Kaminsky.server.DataAccessLayer.DaoFactory;
import by.bsuir.Kaminsky.client.controller.Controller;
import by.bsuir.Kaminsky.models.User;

/**
 * Provides a set of methods for working with users
 * @author DenisKaminsky
 * @version 1.1
 */
public class UserLogic {

	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	/** Send message */
	private static void sendMessage(Object message) {
        try {
        	out.reset();
            out.writeObject(message);
            out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Receive message */
	@SuppressWarnings("unchecked")
	private static ArrayList<Object> receiveMessage() {
		ArrayList<Object> result = null;
		try {
			result = (ArrayList<Object>)in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/** Log in */
	public static ArrayList<Object> logIn(Object[] answer,int action){	
		ArrayList<Object> message = new ArrayList<Object>();
		User newUser;
		String name;
				
		message.add(action);
		if ( !(boolean)answer[0] ){			
			newUser = new User((String)answer[1], MD5.getHash((String)answer[2]), false);
			if ( ! DaoFactory.getUserDao().save(newUser) ){	
				message.add(false);
				message.add("User with this login already exist!");
				System.out.println("<warning> User with this login already exist!");
				return message;
			}	
			else{
				System.out.println("<action> User "+(String)answer[1]+" was successfully register");
			}
		}
		
		newUser = DaoFactory.getUserDao().getAuthorizeUser((String)answer[1], MD5.getHash((String)answer[2]));		
		if (newUser == null){
			message.add(false);
			message.add("Wrong login or password!");
			System.out.println("<warning> Wrong login or password!");
        } else{   
        	message.add(true);
        	message.add(newUser);
        	name = (newUser.getIsAdministrator())?"Administrator ":"User ";
        	System.out.println("<action> "+name+newUser.getLogin()+" log in");
        }	
		return message;
	}
	
	/** Choose action */
	private static void chooseAction() {
		
		ArrayList<Object> message,result=null;
		int action;
		
		while (true) {
			message = receiveMessage();
			action = (int)message.get(0);
			
			switch (action) {
				case 0:	
					result = logIn((Object[])message.get(1),action);
					break;
				case 1:	
					result = logOut((String)message.get(1),action);
					break;
				case 2:	
					//BookLogic.getBooks();
					break;
				case 3:		
					//BookLogic.findBooksByTitle();
					break;
				case 4:	
					//BookLogic.findBooksByAuthor();
					break;
				case 5:	
					//BookLogic.findElectonicBooks();
					break;
				case 6:
					//BookLogic.findPaperBooks();
					break;
				case 7:	
					//BookLogic.modifyBook();
					break;
				case 8:		
					//BookLogic.addBook();
					break;
				case 9:		
					//BookLogic.deleteBook();
					break;
				case 10:		
					//showUsers();
					break;
				case 11:		
					//deleteUser();
					break;				
			}
			sendMessage((Object)result);			
		}		
	}
	
	/** Log out */
	private static ArrayList<Object> logOut(String message,int action){
		ArrayList<Object> result;
		
		System.out.println(message);
		result = new ArrayList<Object>();
		result.add(action);
		result.add("true");
		return result;
	}
	
	/** Show all users */
	private static void showUsers() {
		ArrayList<Object> users = new ArrayList<Object>(DaoFactory.getUserDao().getUsers());
		
		Controller.printListRequest(users);	
	}
	
	/** Delete user */
	private static void deleteUser() {
		String answer = Controller.getParameterRequest("login of user, you want to delete");
		
		if (answer != null) {
			if (!DaoFactory.getUserDao().delete(answer)) {
				Controller.notifyUserRequest("User not found or administrator");
			}else {
				Controller.notifyUserRequest("User "+answer+" was deleted");
			}
		}		
	}
}


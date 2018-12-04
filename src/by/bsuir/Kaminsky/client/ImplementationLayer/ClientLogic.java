package by.bsuir.Kaminsky.client.ImplementationLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import by.bsuir.Kaminsky.client.controller.Controller;
import by.bsuir.Kaminsky.models.User;
import by.bsuir.Kaminsky.server.DataAccessLayer.DaoFactory;

public class ClientLogic {
	
	private static User user = null;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	/** Send message */
	public static void sendMessage(Object message) {
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
	
	public static void logIn(ObjectInputStream inpStream,ObjectOutputStream outStream){	
		Object[] answer = Controller.authorizeRequest();
		ArrayList<Object> message,result;
		
		in = inpStream;
		out = outStream;		
		if (answer != null){
			message = new ArrayList<Object>();
			message.add(0);
			message.add(answer);
			sendMessage((Object)message);
			result = receiveMessage();
			if ((boolean)result.get(1)) {
				user = (User)result.get(2);
				Controller.notifyUserRequest("User "+user.getLogin()+" was successfully register");
				chooseAction();
			}else
				Controller.notifyUserRequest((String)result.get(3));				
		}
	}
	
	/** Choose action */
	private static void chooseAction() {
		boolean flag = true;
		
		while (flag) {
			int action = Controller.chooseActionRequest(user.getIsAdministrator());
			switch (action) {
				case 0:		
					logOut(action+1);
					System.exit(0);
					break;
				case 1:	
					flag = false;
					logOut(action);
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
					showUsers(action);
					break;
				case 11:		
					//deleteUser();
					break;
			}
		}		
	}
	
	/** Log out */
	private static void logOut(int action){
		ArrayList<Object> message;
		String name = (user.getIsAdministrator())?"Administrator ":"User ";
		
		Controller.notifyUserRequest(name+user.getLogin()+" log out");
		message = new ArrayList<Object>();
		message.add(action);
		message.add(name+user.getLogin()+" log out");
		sendMessage((Object)message);
		receiveMessage();
		user = null;
	}
	
	/** Show all users */
	@SuppressWarnings("unchecked")
	private static void showUsers(int action) {
		ArrayList<Object> result,message = new ArrayList<Object>();
		
		message.add(action);
		sendMessage((Object)message);
		result = receiveMessage();
		Controller.printListRequest((ArrayList<Object>)result.get(2));	
	}
	
	/** Delete user */
	private static void deleteUser(int action) {
		String login = Controller.getParameterRequest("Login of user, you want to delete");
		
		if (login != null) {
			if (!DaoFactory.getUserDao().delete(answer)) {
				Controller.notifyUserRequest("User not found or administrator");
			}else {
				Controller.notifyUserRequest("User "+answer+" was deleted");
			}
		}else {
			Controller.notifyUserRequest("ERROR!");
		}
	}
}

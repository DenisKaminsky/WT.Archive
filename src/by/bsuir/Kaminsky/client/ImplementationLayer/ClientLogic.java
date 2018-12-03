package by.bsuir.Kaminsky.client.ImplementationLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import by.bsuir.Kaminsky.client.controller.Controller;
import by.bsuir.Kaminsky.models.User;

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
		
		in = inpStream;
		out = outStream;		
		if (answer != null){
			ArrayList<Object> message = new ArrayList<Object>();
			message.add(0);
			message.add(answer);
			sendMessage((Object)message);
			receiveMessage();
		}
	}
	
	/** Choose action */
	private static void chooseAction() {
		boolean flag = true;
		
		while (flag) {
			int action = Controller.chooseActionRequest(user.getIsAdministrator());
			switch (action) {
				case 0:			
					System.exit(0);
					break;
				case 1:	
					flag = false;
					logOut();
					break;
				case 2:	
					BookLogic.getBooks();
					break;
				case 3:		
					BookLogic.findBooksByTitle();
					break;
				case 4:	
					BookLogic.findBooksByAuthor();
					break;
				case 5:	
					BookLogic.findElectonicBooks();
					break;
				case 6:
					BookLogic.findPaperBooks();
					break;
				case 7:	
					BookLogic.modifyBook();
					break;
				case 8:		
					BookLogic.addBook();
					break;
				case 9:		
					BookLogic.deleteBook();
					break;
				case 10:		
					showUsers();
					break;
				case 11:		
					deleteUser();
					break;
			}
		}		
	}
	
	/** Log out */
	private static void logOut(){	
		String name = (user.getIsAdministrator())?"Administrator ":"User ";
		
		Controller.notifyUserRequest(name+user.getLogin()+" log out");
		user = null;
	}	
}

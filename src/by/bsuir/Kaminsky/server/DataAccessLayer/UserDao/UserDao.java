package by.bsuir.Kaminsky.server.DataAccessLayer.UserDao;

import java.io.*;
import java.util.ArrayList;
import by.bsuir.Kaminsky.models.User;

public class UserDao implements IUserDao {
	
	private String fileName;
		
	public UserDao()
	{
		fileName = getDatabasePath();
	}
	
	public boolean delete(String login){
		int counter = 0;
		boolean flag = false;
		ArrayList<User> users = getUsers();
		
		for (User currentUser : users) {
			if ( (currentUser.getLogin()).equals(login) && !currentUser.getIsAdministrator() ){	
				flag = true;
				break;
			}
			counter++;
		}
		if (flag)
		{
			users.remove(counter);
			try {
				SerializeUsers(users, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
    public boolean save(User user) {
    	boolean flag = false;
		File f = new File(fileName);	
		User searchUser = null;
		ArrayList<User> users = getUsers();		
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		for (User currentUser : users) {
			if (currentUser.equals(user))
			{
				searchUser = currentUser;
				break;
			}
		}
		if (searchUser == null)
		{
			users.add(user);
			flag = true;
				
			try {
				SerializeUsers(users, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return flag;
    }
    
    public User getAuthorizeUser(String login, String password) {
    	ArrayList<User> users = getUsers();
		User result = null;
		
		for (User user : users) {
			if ( (user.getPassword().equals(password)) && (user.getLogin().equals(login)) )
			{
				result = user;
				break;
			}
		}
		return result;
    }
    
    public ArrayList<User> getUsers(){
    	File f = new File(fileName);
		ArrayList<User> users = new ArrayList<User>();
					
		if(f.exists() && !f.isDirectory()) {			
			try {
				users = DeserializeUsers(fileName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
    	return users;
    }
    
    private void SerializeUsers(ArrayList<User> users,String fileName) throws IOException {
		FileOutputStream fileStream = new FileOutputStream(fileName);
	    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
	    
	    objectStream.writeObject(users);
	    objectStream.close();
	    fileStream.close();
	}	
	
	@SuppressWarnings("unchecked")
	private ArrayList<User> DeserializeUsers(String fileName) throws IOException, ClassNotFoundException{
		ArrayList<User> result;
		FileInputStream fileStream = new FileInputStream(fileName);
		ObjectInputStream objectStream;
		
		try {
			objectStream = new ObjectInputStream(fileStream);	    
	    
			result = (ArrayList<User>)objectStream.readObject();
			objectStream.close();
		}
		catch (EOFException e) {
			result = new ArrayList<User>(); 
		}
	    fileStream.close();
	    return result;
	}
    
    private String getDatabasePath(){
    	return new File("").getAbsolutePath()+"src\\by\\bsuir\\Kaminsky\\server\\database\\UsersDatabase.xml";
	}    
}


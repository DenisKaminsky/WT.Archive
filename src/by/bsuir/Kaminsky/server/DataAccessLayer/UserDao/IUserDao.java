package by.bsuir.Kaminsky.server.DataAccessLayer.UserDao;

import java.util.ArrayList;
import by.bsuir.Kaminsky.models.User;

public interface IUserDao {
	
	boolean delete(String login);
    boolean save(User user);
    User getAuthorizeUser(String login, String password);
    ArrayList<User> getUsers();
}

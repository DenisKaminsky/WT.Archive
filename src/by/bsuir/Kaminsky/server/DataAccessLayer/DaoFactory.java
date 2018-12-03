package by.bsuir.Kaminsky.server.DataAccessLayer;

import by.bsuir.Kaminsky.server.DataAccessLayer.BookDao.BookDao;
import by.bsuir.Kaminsky.server.DataAccessLayer.UserDao.UserDao;

public class DaoFactory {
	
	private static UserDao userDao = new UserDao();
    private static BookDao bookDao = new BookDao();
    
    UserDao getUserDao()
    {
    	return userDao;
    }
    
    public static BookDao getBookDao()
    {
    	return bookDao;
    }
}

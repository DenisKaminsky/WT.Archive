package by.bsuir.Kaminsky.server.ImplemenationLayer;

import java.util.ArrayList;
import by.bsuir.Kaminsky.server.DataAccessLayer.DaoFactory;
import by.bsuir.Kaminsky.models.Book;

/**
 * Provides a set of methods for working with books
 * @author DenisKaminsky
 * @version 1.1
 */
public class BookLogic {
		
	/** Get books by author */
	public static void findBooksByAuthor(String author){
		 ArrayList<Object> books;
		 
		 if (author != null) {			 
			 books = new ArrayList<Object>(DaoFactory.getBookDao().getBooksByAuthor(author));				
			 //Controller.printListRequest(books);	
		 }			 
    }
	
	/** Get books by title */
	public static void findBooksByTitle(String title){
		 ArrayList<Object> books;
		 
		 if (title != null) {			 
			 books = new ArrayList<Object>(DaoFactory.getBookDao().getBooksByTitle(title));				
			 //Controller.printListRequest(books);	
		 }			 
    }
	
	/** Get paper books*/
	public static void findPaperBooks(){
		ArrayList<Object> books = new ArrayList<Object>(DaoFactory.getBookDao().getPaperBooks());
		
		//Controller.printListRequest(books);	
    }
	
	/** Get electronic books */
	public static void findElectonicBooks(){
		ArrayList<Object> books = new ArrayList<Object>(DaoFactory.getBookDao().getElectonicBooks());
		
		//Controller.printListRequest(books);	
    }
	
	/** Get all books */
	public static void getBooks(){	
		ArrayList<Object> books = new ArrayList<Object>(DaoFactory.getBookDao().getBooks());
		
		//Controller.printListRequest(books);		
	}
	
	/** Add book */
	public static void addBook() {
		Book book;
		Object[] answer = Controller.getBookRequest();
		
		if (answer != null) {
			book = new Book((String)answer[0],(String)answer[1],(Boolean)answer[2]);
			if (!DaoFactory.getBookDao().save(book))
				Controller.notifyUserRequest("Book already exists");
			else
				Controller.notifyUserRequest("Book was added");
		}			
		
	}
	
	/** Delete book */
	public static void deleteBook() {
		Book book;
		Object[] answer = Controller.getBookRequest();
		
		if (answer != null) {
			book = new Book((String)answer[0],(String)answer[1],(Boolean)answer[2]);
			if (!DaoFactory.getBookDao().delete(book))
				Controller.notifyUserRequest("Book not found");
			else
				Controller.notifyUserRequest("Book was deleted");
		}			
	}
	
	/** Modify book */
	public static void modifyBook() {
		Book bookOld,bookNew;
		Object[] answer = Controller.replaceBookRequest();
		
		if (answer != null) {
			bookOld = new Book((String)answer[0],(String)answer[1],(Boolean)answer[2]);
			bookNew = new Book((String)answer[3],(String)answer[4],(Boolean)answer[5]);
			if (!DaoFactory.getBookDao().replace(bookOld, bookNew))
				Controller.notifyUserRequest("Book not found");
			else
				Controller.notifyUserRequest("Book was modified");
		}
	}
	
}

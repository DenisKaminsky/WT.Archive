package by.bsuir.Kaminsky.server.DataAccessLayer.BookDao;

import java.io.*;
import java.util.ArrayList;
import by.bsuir.Kaminsky.models.Book;

public class BookDao implements IBookDao{
	
	private String fileName;
	
	public BookDao()
	{
		fileName = getDatabasePath();
	}
		
	public boolean delete(Book book) {
		int counter = 0;
		boolean flag = false;
		ArrayList<Book> books = getBooks();
		
		for (Book currentBook : books) {
			if (currentBook.equals(book)){	
				flag = true;
				break;
			}
			counter++;
		}
		if (flag)
		{			
			books.remove(counter);
			try {
				SerializeBooks(books, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean save(Book book) {
		boolean flag = false;
		File f = new File(fileName);	
		Book searchBook = null;
		ArrayList<Book> books = getBooks();		
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		for (Book currentBook : books) {
			if (currentBook.equals(book))
			{
				searchBook = currentBook;
				break;
			}
		}
		if (searchBook == null)
		{
			books.add(book);
			flag = true;
				
			try {
				SerializeBooks(books, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return flag;
	}
	
	public boolean replace(Book bookOld,Book bookNew) {
		int counter = 0;
		boolean flag = false;
		ArrayList<Book> books = getBooks();
		
		for (Book currentBook : books) {
			if (currentBook.equals(bookOld)){	
				flag = true;
				break;
			}
			counter++;
		}
		if (flag)
		{			
			books.remove(counter);
			books.add(counter, bookNew);
			try {
				SerializeBooks(books, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public ArrayList<Book> getBooks(){
		File f = new File(fileName);
		ArrayList<Book> books = new ArrayList<Book>();
					
		if(f.exists() && !f.isDirectory()) {			
			try {
				books = DeserializeBooks(fileName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
		return books;
	}
	
	public ArrayList<Book> getBooksByTitle(String title){
		ArrayList<Book> books = getBooks();
		ArrayList<Book> result= new ArrayList<Book>();
		
		for (Book book : books) {
			if ((book.getTitle().toLowerCase()).equals(title.toLowerCase()))
				result.add(book);
		}
		return result;
	}
	
	public ArrayList<Book> getBooksByAuthor(String author){
		ArrayList<Book> books = getBooks();
		ArrayList<Book> result= new ArrayList<Book>();
		
		for (Book book : books) {
			if ((book.getAuthor().toLowerCase()).equals(author.toLowerCase()))
				result.add(book);
		}
		return result;
	}
		
	public ArrayList<Book> getPaperBooks(){
		ArrayList<Book> books = getBooks();
		ArrayList<Book> result= new ArrayList<Book>();
		
		for (Book book : books) {
			if (!book.getIsElectronic())
				result.add(book);
		}
		return result;
	}
		
	public ArrayList<Book> getElectonicBooks(){
		ArrayList<Book> books = getBooks();
		ArrayList<Book> result= new ArrayList<Book>();
		
		for (Book book : books) {
			if (book.getIsElectronic())
				result.add(book);
		}
		return result;
	}
	
	private void SerializeBooks(ArrayList<Book> books,String fileName) throws IOException {
		FileOutputStream fileStream = new FileOutputStream(fileName);
	    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
	    
	    objectStream.writeObject(books);
	    objectStream.close();
	    fileStream.close();
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Book> DeserializeBooks(String fileName) throws IOException, ClassNotFoundException{
		ArrayList<Book> result;
		FileInputStream fileStream = new FileInputStream(fileName);
		ObjectInputStream objectStream;
		
		try {
			objectStream = new ObjectInputStream(fileStream);	    
	    
			result = (ArrayList<Book>)objectStream.readObject();
			objectStream.close();
		}
		catch (EOFException e) {
			result = new ArrayList<Book>(); 
		}
	    fileStream.close();
	    return result;
	}

	private String getDatabasePath(){
		return new File("").getAbsolutePath()+"src\\by\\bsuir\\Kaminsky\\server\\database\\BooksDatabase.xml";
	}	
}


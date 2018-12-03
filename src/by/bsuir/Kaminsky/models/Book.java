package by.bsuir.Kaminsky.models;

import java.io.Serializable;

public class Book implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String author;
	
	private String title;
	
	private boolean isElectronic;
	
	public Book(String author,String title,boolean isElectronic){
		this.author = author;
		this.title = title;
		this.isElectronic = isElectronic;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String value) {
		author = value;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String value) {
		title = value;
	}
	
	public boolean getIsElectronic(){
		return isElectronic;
	}
		
	public void setIsElectronic(Boolean value) {
		isElectronic = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		Book book;
		
		if (obj == this) 
			return true;
		if (obj == null || obj.getClass() != this.getClass())
            return false;
		book = (Book)obj;
        return (isElectronic == book.isElectronic) && (title == book.title ||
            (title != null && (title.toLowerCase()).equals(book.getTitle().toLowerCase())))
                && (author == book.author || (author != null && (author.toLowerCase()).equals(book.getAuthor().toLowerCase())));
	}
	
	@Override
	public String toString() {
		String type = (isElectronic)?"Electronic":"Paper";
		return  "Title: " + title +
                "\nAuthor: " + author +
                "\nType: "+ type;
	}
}


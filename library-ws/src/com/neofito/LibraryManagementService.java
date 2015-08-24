package com.neofito;

public class LibraryManagementService 
{
    public boolean addBook(String isbn, String titulo, String autor) throws Exception
    {
	Book book = new Book(isbn, titulo, autor);
	Database database = new Database();
		
	try {
            database.addBook(book);
            return true;
	} catch (Exception e) { 
            throw new Exception(e.getMessage());
        }
    }
	
    public Book getBookInfo(String isbn) throws Exception
    {
	Database database = new Database();
                
	try {
            return database.getBookInfo(isbn);
	} catch (Exception e) { 
            throw new Exception(e.getMessage());
        }
    }
	
    public boolean deleteBook(String isbn) throws Exception
    {
	Database database = new Database();
	        
	try {
            database.deleteBook(isbn);
            return true;
	} catch (Exception e) { 
            throw new Exception(e.getMessage());
        }
    }
}

package com.neofito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database 
{
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultset = null;
	
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "library";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "t3st1ng";
	
    private Connection doConnect() throws Exception
    {		
	try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionuri = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
            connection = DriverManager.getConnection(connectionuri, DB_USER, DB_PASS);
            connection.setAutoCommit(true);
	} catch (Exception e) {
            throw new Exception("Problema al conectar con la base de datos");
        }
        
        return connection;		
    }
	
    public boolean addBook(Book book) throws Exception
    {
	try {
            connection = doConnect();
            statement = connection.createStatement();
			
            String sqlcommand = "INSERT INTO books VALUES ('"+ book.getIsbn() + "','"
            		+ book.getTitulo() + "','" + book.getAutor()  + "')";
			
            int result = statement.executeUpdate(sqlcommand);
            if (result == 1) return true;
            else throw new Exception("El isbn ya esta dado de alta");
        } catch (SQLException e) {
            throw new Exception("No se puede ejecutar la consulta");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {				
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
	
    public Book getBookInfo(String isbn) throws Exception
    {
	try {
            connection = doConnect();
            statement = connection.createStatement();
            
            String sqlcommand = "SELECT * FROM books WHERE isbn = '" + isbn + "'";
            resultset = statement.executeQuery(sqlcommand);
            while (resultset.next()) {
                String bookIsbn = resultset.getString("isbn");
		String bookTitulo = resultset.getString("titulo");
		String bookAutor = resultset.getString("autor");
		
                Book book = new Book();
		book.setIsbn(bookIsbn);
		book.setTitulo(bookTitulo);
		book.setAutor(bookAutor);
                
                return book;
            }
            
            throw new Exception("El isbn no esta dado de alta");
	} catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }        
    }
	
    public boolean deleteBook(String isbn) throws Exception
    {
	try {
            connection = doConnect();
            statement = connection.createStatement();
            
            String sqlcommand = "DELETE FROM books WHERE isbn = '" + isbn + "'";
            int result = statement.executeUpdate(sqlcommand);
            if (result == 1) return true;
            else throw new Exception("El isbn no esta dado de alta");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }        
    }
}

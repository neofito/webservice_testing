package com.neofito;

public class Book 
{
    private String isbn;
    private String titulo;
    private String autor;
	
    public Book() {}
	
    public Book(String isbn, String titulo, String autor)
    {
	this.isbn = isbn;
	this.titulo = titulo;
	this.autor = autor;
    }
	
    public String getIsbn()
    {
	return this.isbn;
    }
	
    public void setIsbn(String isbn)
    {
	this.isbn = isbn;
    }
	
    public String getTitulo()
    {
	return this.titulo;
    }
	
    public void setTitulo(String titulo)
    {
	this.titulo = titulo;
    }
	
    public String getAutor()
    {
	return this.autor;
    }
	
    public void setAutor(String autor)
    {
	this.autor = autor;
    }

}

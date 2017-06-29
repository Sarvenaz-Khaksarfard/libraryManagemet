package library;

public class Book {
	int id;
	String title ="";
	String author ="";
	int edition;
	int year;
	String isbn ="";
	String availability ="";
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String author){
		this.author=author;
	}
	public int getEdition(){
		return edition;
	}
	
	public void setEdition(int edition){
		this.edition=edition;
	}
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year=year;
	}
	public String getIsbn(){
		return isbn;
	}
	
	public void setIsbn(String isbn){
		this.isbn=isbn;
	}
	public String getAvailability(){
		return availability;
	}
	
	public void setAvailability(String availability){
		this.availability= availability;
	}
}

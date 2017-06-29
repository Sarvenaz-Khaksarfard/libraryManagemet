package library;

import java.io.File;

public class Student {

	int id;
	String firstName = "";
	String lastName = "";
	String program = "";
	String username = "";
	String password ="";
	String email = "";
	String telephone;
	String address = "";
	String plainPassword ="";
	File image = null;

	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getfname() {
		return firstName;
	}
	
	public void setfname(String firstName){
		this.firstName = firstName;
	}
	
	public String getlname() {
		return lastName;
	}
	
	public void setlname(String lastName){
		this.lastName = lastName;
	}
	public String getProgram() {
		return program;
	}
	
	public void setProgram(String program){
		this.program = program;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	public String getTel() {
		return telephone;
	}
	
	public void setTel(String telephone){
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public String getPlainPassword(){
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword){
		this.plainPassword = plainPassword;
	}
	
	public File getImage(){
		return image;
	}
	
	public void setImage(File image){
		this.image = image;
	}
}

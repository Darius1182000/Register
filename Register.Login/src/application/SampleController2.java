package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SampleController2{
	
	private static ResultSet res=null;
	public TextField usernameU;
	public PasswordField passwordP;
	public Text textC;
	boolean check=false;
	private String userU;
	private String passP;

  public void buttonB(ActionEvent event) throws SQLException {
	  String query="SELECT username,password FROM users";
	  
	  ConnectionClass connectionClass=new ConnectionClass();
	  try( Connection connection=connectionClass.getConnection();
	       Statement stmt=connection.createStatement();		   			 
  ){
		  res=stmt.executeQuery(query);
		  
		  while(res.next()) {			  
			  String users=res.getString("username");
			  System.out.println(users);
			  String pass=res.getString("password");
			  System.out.println(pass);
		      String userU=usernameU.getText();
		      String passwordU=passwordP.getText();
			  if(users.equals(userU)) {
				 if(pass.equals(passwordU)) {
					 check=true;
					 break;
				 }
					 }else {
						 check=false;
					 }
		  }
		  if(check==true) {
			  textC.setText("Connected");
		  }else {
			 textC.setText("Invalid username or password"); 
		  }
		  	  
	  }catch(SQLException e) {
		System.err.println(e);  
	  }finally {
		  if(res!=null)res.close();
		 
	  }
  }
}

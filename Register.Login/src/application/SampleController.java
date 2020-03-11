package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SampleController{

	public TextField firstNameText,lastNameText,userText,passText,retypePassText;
	public DatePicker birthDateText;
	public TextArea adressText;
	public Button registerButton;
	public Text tText;
	private static ResultSet res;
    private int count;
	private boolean exist=false;
    private String users1;
		
	public void initialize() {
		registerButton.setDisable(true);	
	}
	
	public void isEmpty() {
		try {
		String fName=firstNameText.getText();
		String lName=lastNameText.getText();
		String uName=userText.getText();
		String pText=passText.getText();
		String rpText=retypePassText.getText();          
    	String bdText= birthDateText.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String aText=adressText.getText();
		boolean isDisabled=fName.isEmpty()||lName.isEmpty()||uName.isEmpty()||pText.isEmpty()||rpText.isEmpty()||bdText.isEmpty()||aText.isEmpty();
		registerButton.setDisable(isDisabled);
		}catch(NullPointerException e) {
			System.err.println("BirthDate field is empty \n"+e);
		}
	}
//=============================================================================================================================================================================================
	public void button(ActionEvent actionEvent) throws SQLException {	
		
		String query1="SELECT id FROM users";
		String query2="SELECT username FROM users";
				
		ConnectionClass connectionClass=new ConnectionClass();
		try( Connection connection=connectionClass.getConnection();
		     Statement stmt=connection.createStatement();	
		     ){	
			
			res=stmt.executeQuery(query1);
			while(res.next()) {
				int id=res.getInt("id");				
				count=id+1;		
			}
			
			res=stmt.executeQuery(query2);
			while(res.next()) {
			   String users=res.getString("username");
				System.out.println(users);
				users1=userText.getText();
				if(users.equals(users1)) {
					exist=true;
					break;
				}else {
					exist=false;
				}				
			}
		
			String query="INSERT INTO register.users (id,first_name,last_name,username,password,retype_pass,birth_date,adress) \n" +
			             "VALUES ('"+count+"','"+firstNameText.getText()+"','"+lastNameText.getText()+"','"+userText.getText()+"','"+passText.getText()+"','"+retypePassText.getText()+"',"
			             		+ "'"+birthDateText.getValue()+"','"+adressText.getText()+"')";	
		  String retypeP=retypePassText.getText();
		  if(exist==true) {
			 tText.setText("Username already exists...");
		  }else if(!passText.getText().equals(retypeP)) {
			  tText.setText("Passwords do not match...");
		  }else if(exist==false||passText.getText().equals(retypeP)) {
			  stmt.executeUpdate(query);
			  tText.setText("You have succesfully registered");
		  }else {
			  tText.setText("Someting went wrong");
		  }		  		  	

		}catch(SQLException e) {
              System.err.println(e);
		}finally {
			res.close();
		}
	}
//=============================================================================================================================================================================================	
	public void fNameKey (KeyEvent keyEvent) {
		firstNameText.setOnKeyTyped(event->{
			if(firstNameText.getText().length()>20)event.consume();	
		});
	}
	public void lNameKey(KeyEvent keyEvent) {	
		lastNameText.setOnKeyTyped(event->{
			if(lastNameText.getText().length()>20)event.consume();
		});	
	}
	public void userKey(KeyEvent keyEvent) {	
		userText.setOnKeyTyped(event->{
			if(userText.getText().length()>20)event.consume();
		});	
	}
	public void passKey(KeyEvent keyEvent) {	
		passText.setOnKeyTyped(event->{
			if(passText.getText().length()>20)event.consume();
		});	
	}
	public void rPassKey(KeyEvent keyEvent) {	
		retypePassText.setOnKeyTyped(event->{
			if(retypePassText.getText().length()>20)event.consume();
		});	
	}
	public void aKey(KeyEvent keyEvent) {	
		adressText.setOnKeyTyped(event->{
			if(adressText.getText().length()>100)event.consume();
		});	
	}	
//==============================================================================================================================================================================================		
	public void changeScreen(ActionEvent event) throws IOException {	
		Parent newScreenParent=FXMLLoader.load(getClass().getResource("Sample2.fxml"));
		Scene newScreenScene=new Scene(newScreenParent,350,200);
		newScreenScene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		Stage window= new Stage();
		window.setTitle("Login");
		window.setResizable(false);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setScene(newScreenScene);
		window.showAndWait();	
  }	
}



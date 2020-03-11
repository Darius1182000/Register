package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {

	private static final String USERNAME="root";
	private static final String PASSWORD="";
	private static final String CONN="jdbc:mysql://localhost/register";
    private static Connection connection;
		
    	    public  Connection getConnection(){
    	    	
    	        try {    	   	        
    	        connection= DriverManager.getConnection(CONN,USERNAME,PASSWORD);
                System.out.println("Connected...");
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	        return connection;
    	    }
    	}
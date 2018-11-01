package forestmanagementsys;

import java.sql.*;



public class Driver
{
    
    String driverName="com.mysql.jdbc.Driver";
    String serverName="//127.0.0.1";
    String portNumber="3308";
    String dbName="forestMDB";
    String URL="jdbc:mysql:"+serverName+":"+portNumber+"/"+dbName+"?autoReconnect=true&amp;allowMultiQueries=true";
    String username="admrkm";
    String password="Rem@17kim";
    Connection connection=null;
    
    public Driver()
    {
        try
        {
            Connection connection=DriverManager.getConnection(URL,username,password);
           
        }
        catch(SQLException ex)
        {
            System.out.println("CONNECTION ERROR : "+ex.getMessage());                  
            
        }
    }

    public boolean doConnection()
    {
        try
        {
            Class.forName(driverName);

            connection=DriverManager.getConnection(URL,username,password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Connection class not found: "+e.getMessage());
            return false;
        }
        catch (SQLException e )
        {
            System.out.println("Connection FAILURE: "+e.getMessage());
            return false;
        }
        return  true;
    }
    
    }


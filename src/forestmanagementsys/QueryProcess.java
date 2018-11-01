package forestmanagementsys;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class QueryProcess 
{
   
    public QueryProcess()
            {
                
            }
    
    public ResultSet exQuery(String query)
    {
    Driver driver=new Driver();
        driver.doConnection();
        Statement stmt;
        ResultSet rs = null;
        try
        {
        stmt=driver.connection.createStatement();
        
       rs=stmt.executeQuery(query);
        
        }
        catch(Exception ex)
        {
            System.out.println("UPDATE ERROR: "+ex.getMessage());
                    JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }   
        return rs;
    }
    
    public void upQuery(String query)
    {
        Driver driver=new Driver();
        driver.doConnection();
        Statement stmt;
        ResultSet rs = null;
        try
        {
        stmt=driver.connection.createStatement();
        
       stmt.executeUpdate(query);
        
        }
        catch(Exception ex)
        {
            System.out.println("UPDATE ERROR: "+ex.getMessage());
                    JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }   
        
    }
    
    public void initializeID()
    {
       
        
    }
            
}

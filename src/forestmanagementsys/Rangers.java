package forestmanagementsys;

import java.util.Date;


public class Rangers {
    private int rangerID;
     private String rangerName;
    private Date DOB;
    private String pass;
    
    public Rangers(int rangerID,String rangerName,Date DOB,String pass)
    {
        this.rangerID=rangerID;
        this.rangerName=rangerName;
        this.DOB=DOB;
        this.pass=pass;
                
    }
    public int getRangerID()
    {
        return rangerID;
    }
    public Date getDOB()
    {
        return DOB;
               
    }
    public String getRangerName()
    {
        return rangerName;
    }
    public String getPass()
    {
        return pass;
    }
    
    
    
}

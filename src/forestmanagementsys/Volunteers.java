
package forestmanagementsys;

import java.util.Date;


public class Volunteers {
    private int volNum,treesPlanted,rangID;
    private String volName,userPass;
    private Date DOB;
    public Volunteers(int volNum,String volName,Date DOB,int treesPlanted,int rangID,String userPass)
    {
               this.volNum=volNum;
               this.treesPlanted=treesPlanted;
               this.rangID=rangID;
               this.volName=volName;
               this.userPass=userPass;
               this.DOB=DOB;
    }
               
               public int getvolNum()
               {
                   return volNum;
               }
               public int gettreesPlanted()
               {
                   return treesPlanted;
               }
               public int getrangID()
               {
                   return rangID;
               }
               public String getvolName()
               {
                   return volName;
               }
               public String getuserPass()
               {
                   return userPass;
               }
               public Date getDOB()
               {
                   return DOB;
               }

    }


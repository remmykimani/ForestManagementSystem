
package forestmanagementsys;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import static java.nio.file.Files.list;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form MainPage
     */
    public MainPage() {
        initComponents();
        btnStatisticsPanel.doClick();
       
        //PROMT FOR DATABASE CONNECTION
        Driver driver = new Driver();
        Connection conn=driver.connection;

        if (driver.doConnection())
        {
            lblconnectionStatus.setText("CONNECTION ACTIVE");
            lblconnectionStatus.setForeground(new Color(12, 140, 1));
        } else
        {
            lblconnectionStatus.setText("OFFLINE");
            lblconnectionStatus.setForeground(new Color(109, 1, 1));
        }
    }
    public void initializeVolunteerForm()
    {
         //INTITIALIZE ADD VOLUNTEER FROM PK TO PREVENT REPEATED RECORD
        txtVolName.setText("");
        txtAddTreesPlanted.setText("");
        txtVolPass.setText("");
        lbltreesPlanted.setText("N/A");
        
       QueryProcess volPanelQry =new QueryProcess();
       ResultSet vrs= volPanelQry.exQuery("SELECT MAX(Vol_Num)FROM volunteer");
       try
       {
       while (vrs.next())
      {
          txtVolID.setText(Integer.toString(vrs.getInt("MAX(Vol_Num)")+1));
      }
       }catch(SQLException e)
       {
           System.out.println("SQL ERROR:: "+e.getMessage());
       }
    }
    public void initializeRangerForm()
    {
        
         //INTITIALIZE ADD Ranger FROM PK TO PREVENT REPEATED RECORD
       QueryProcess rangPanelQry =new QueryProcess();
       ResultSet vrs= rangPanelQry.exQuery("SELECT MAX(Ranger_ID)FROM ranger");
       try
       {
       while (vrs.next())
      {
          txtAddRangerID.setText(Integer.toString(vrs.getInt("MAX(Ranger_ID)")+1));
          txtAddRangerID.setEditable(false);
          txtAddRangerName.setText("");
          txtAddRangerPass.setText("");
      }
       }catch(SQLException e)
       {
           System.out.println("SQL ERROR:: "+e.getMessage());
       }
    
   }
            
    public void fillRangerCombox()
    {
        try
        {
            
            QueryProcess qry=new QueryProcess();
            ResultSet rs;
            rs=qry.exQuery("SELECT * FROM `ranger`");
            while(rs.next())
            {
                comboxRanger.addItem(Integer.toString(rs.getInt("Ranger_ID")));
            }          
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());
        }
    }
    
    public void btnAddVolSubmit()
{
try
        {
            QueryProcess qp=new QueryProcess();  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String VolID=txtVolID.getText().trim();
        String VolName=txtVolName.getText().trim();
        String VolDOB=sdf.format(volDateChooser.getDate());
        String rangerID=comboxRanger.getSelectedItem().toString();
        String pass=txtVolPass.getText().trim();        
        String query="INSERT INTO `volunteer` (`Vol_Num`, `Vol_Name`, `DOB`, `Number_of_trees_planted`, `Ranger_ID`, `userPass`) VALUES('"+VolID+"', '"+VolName+"', '"+VolDOB+"','0', '"+rangerID+"', '"+pass+"')";
        qp.upQuery(query);
        initializeVolunteerForm();
        submitionStatus.setText("SUBMITTION SUCESSFUL");
        submitionStatus.setForeground(new Color(12, 140, 1));
        
        DefaultTableModel model=(DefaultTableModel)tblVolunteerDetails.getModel();
        model.setRowCount(0);
        fillVolunteerTable();
        }
        catch(Exception ex)
        {
            submitionStatus.setText("SUBMITTION FAILED!!");
        submitionStatus.setForeground(new Color(109, 1, 1));
            System.out.println("UPDATE ERROR: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }        
}
    public void btnAddRangerSubmit()
    {
        try
        {
            QueryProcess qp2=new QueryProcess();  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String RanID=txtAddRangerID.getText().trim();
        String RanName=txtAddRangerName.getText().trim();
        String RanDOB=sdf.format(txtAddRangerDOB.getDate());
        String Ranpass=txtAddRangerPass.getText().trim();        
        String query="INSERT INTO `ranger` (`Ranger_ID`, `Ranger_Name`, `DOB`, `userPass`) VALUES('"+RanID+"', '"+RanName+"', '"+RanDOB+"', '"+Ranpass+"')";
        qp2.upQuery(query);
        initializeRangerForm();
        lblSubimitionStatus.setText("SUBMITTION SUCESSFUL");
        lblSubimitionStatus.setForeground(new Color(12, 140, 1));
        
        DefaultTableModel model=(DefaultTableModel)tblRangerDetails.getModel();
        model.setRowCount(0);
        fillRangerTable();
        
        }catch(Exception e)
                {
                    lblSubimitionStatus.setText("SUBMITTION FAILED!!");
        lblSubimitionStatus.setForeground(new Color(109, 1, 1));
            System.out.println("UPDATE ERROR: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());                    
                }
        
    }
    
    public ArrayList<Rangers> rangList()
    {
        ArrayList <Rangers> rangersList=new ArrayList<>();
        try
        {
            QueryProcess qry2=new QueryProcess();
            ResultSet rs;
            rs=qry2.exQuery("SELECT * FROM `ranger`");
            Rangers ranger;
            
            while(rs.next())
            {
                ranger=new Rangers(rs.getInt("Ranger_ID"),rs.getString("Ranger_Name"),rs.getDate("DOB"),rs.getString("userPass"));
                rangersList.add(ranger);
            }          
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());
        }
        return rangersList;
        
    }
    
   public ArrayList<Volunteers> volList()
    {
        ArrayList <Volunteers> volsList=new ArrayList<>();
        try
        {
            QueryProcess qry3=new QueryProcess();
            ResultSet rs;
            rs=qry3.exQuery("SELECT * FROM `volunteer`");
            Volunteers volunteer;
            
            while(rs.next())
            {
                volunteer=new Volunteers(rs.getInt("Vol_Num"),rs.getString("Vol_Name"),rs.getDate("DOB"),rs.getInt("Number_of_trees_planted"),rs.getInt("Ranger_ID"),rs.getString("userPass"));
                volsList.add(volunteer);
            }          
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());
        }
        return volsList;
        
    }
    
    public void fillRangerTable()
    {
        ArrayList<Rangers> rangerList= rangList();
        DefaultTableModel model=(DefaultTableModel)tblRangerDetails.getModel();
        Object []row=new Object[6];
        
        for (int i=0;i<rangerList.size();i++)
        {
            row[0]=rangerList.get(i).getRangerID();
            row[1]=rangerList.get(i).getRangerName();
            row[2]=rangerList.get(i).getDOB();
            row[3]=rangerList.get(i).getPass();
            model.addRow(row);
        }
        
    }
    
    public void fillVolunteerTable()
    {
        ArrayList<Volunteers> volunteerList= volList();
        DefaultTableModel model=(DefaultTableModel)tblVolunteerDetails.getModel();
        Object []row=new Object[6];
        
        for (int i=0;i<volunteerList.size();i++)
        {
            row[0]=volunteerList.get(i).getvolNum();
            row[1]=volunteerList.get(i).getvolName();
            row[2]=volunteerList.get(i).getDOB();
            row[3]=volunteerList.get(i).gettreesPlanted();
            row[4]=volunteerList.get(i).getrangID();
            row[5]=volunteerList.get(i).getuserPass();
            model.addRow(row);
        }
        
    }
    
    public void homePagePopulate()
    {
        try
        {
            QueryProcess homeQry=new QueryProcess();
            ResultSet rs;
            rs=homeQry.exQuery("SELECT * FROM `forest`");
            while(rs.next())
            {
               lblForestCoverage.setText(rs.getString("Coverage"));
               lblTreesPlanted.setText(Integer.toString(rs.getInt("Number_Of_Trees")));
            }

        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());
        }
        try
        {
            QueryProcess homeQry2=new QueryProcess();
            ResultSet rs;
            rs=homeQry2.exQuery("SELECT COUNT(*) FROM `ranger`");//SELECT COUNT(*)

            while(rs.next())
            {
              
               lblRangerNumber.setText(Integer.toString(rs.getInt("COUNT(*)")));
            }
            
        }catch(Exception ex)
        {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }
        
        try
        {
            QueryProcess homeQry3=new QueryProcess();
            ResultSet rs;
            rs=homeQry3.exQuery("SELECT COUNT(*) FROM `volunteer`");

            while(rs.next())
            {
              
               lblVolunteerEnrolled.setText(Integer.toString(rs.getInt("COUNT(*)")));
            }
            
        }catch(Exception ex)
        {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }
              
     
    }
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuPanel = new javax.swing.JPanel();
        btnStatisticsPanel = new javax.swing.JButton();
        btnAddVolunteer = new javax.swing.JButton();
        btnAddRanger = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        activityPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblForestCoverage = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTreesPlanted = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblVolunteerEnrolled = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblRangerNumber = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        addRangerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblRangerName = new javax.swing.JLabel();
        lblRangerDOB = new javax.swing.JLabel();
        lblRangerPass = new javax.swing.JLabel();
        txtAddRangerID = new javax.swing.JTextField();
        txtAddRangerName = new javax.swing.JTextField();
        txtAddRangerDOB = new com.toedter.calendar.JDateChooser();
        txtAddRangerPass = new javax.swing.JPasswordField();
        lblSubimitionStatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRangerDetails = new javax.swing.JTable();
        btnRangerUpdate = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnRangerDelete = new javax.swing.JButton();
        addVolunteerPanel = new javax.swing.JPanel();
        lblVolName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblVolPass = new javax.swing.JLabel();
        txtVolID = new javax.swing.JTextField();
        txtVolName = new javax.swing.JTextField();
        txtVolPass = new javax.swing.JPasswordField();
        btnAddVolSubmit = new javax.swing.JButton();
        submitionStatus = new javax.swing.JLabel();
        volDateChooser = new com.toedter.calendar.JDateChooser();
        comboxRanger = new javax.swing.JComboBox<>();
        lblRangerIncharge = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVolunteerDetails = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtAddTreesPlanted = new javax.swing.JTextField();
        lbltreesPlanted = new javax.swing.JLabel();
        lblPlus = new javax.swing.JLabel();
        btnVolunteerUpdate = new javax.swing.JButton();
        btnNewVolunteer = new javax.swing.JButton();
        btnVolDelete = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        lblconnectionStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(950, 750));
        setPreferredSize(new java.awt.Dimension(600, 650));
        setSize(new java.awt.Dimension(600, 600));

        menuPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "MENU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), java.awt.Color.lightGray)); // NOI18N
        menuPanel.setMinimumSize(new java.awt.Dimension(850, 100));
        menuPanel.setPreferredSize(new java.awt.Dimension(800, 100));

        btnStatisticsPanel.setText("HOME");
        btnStatisticsPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsPanelActionPerformed(evt);
            }
        });

        btnAddVolunteer.setText("MANAGE VOLUNTEERS");
        btnAddVolunteer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVolunteerActionPerformed(evt);
            }
        });

        btnAddRanger.setText("MANAGE RANGERS");
        btnAddRanger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRangerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStatisticsPanel)
                .addGap(18, 18, 18)
                .addComponent(btnAddVolunteer)
                .addGap(18, 18, 18)
                .addComponent(btnAddRanger)
                .addContainerGap(502, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStatisticsPanel)
                    .addComponent(btnAddVolunteer)
                    .addComponent(btnAddRanger))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(menuPanel, java.awt.BorderLayout.PAGE_START);

        activityPanel.setLayout(new java.awt.CardLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("FOREST MANAGEMENT SYSTEM");

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("LICENCED TO: MWANGAZA FORESTRY CONSERVATION");

        jLabel7.setText("FOREST:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("MOUNT KENYA FOREST RESERVE");

        jLabel9.setText("COVERAGE: ");

        lblForestCoverage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblForestCoverage.setText("N/A");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("NUMBER OF TREES PLANTED");

        lblTreesPlanted.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblTreesPlanted.setForeground(new java.awt.Color(0, 153, 51));
        lblTreesPlanted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTreesPlanted.setText("N/A");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("VOLUNTEERS ENROLLED");

        lblVolunteerEnrolled.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblVolunteerEnrolled.setForeground(new java.awt.Color(0, 153, 0));
        lblVolunteerEnrolled.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVolunteerEnrolled.setText("N/A");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("RANGERS AVAILABLE");

        lblRangerNumber.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRangerNumber.setForeground(new java.awt.Color(51, 153, 0));
        lblRangerNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRangerNumber.setText("N/A");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Hectare");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(jLabel5))
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                        .addComponent(lblTreesPlanted, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblForestCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblVolunteerEnrolled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(185, 185, 185))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(167, 167, 167))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(194, 194, 194))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(lblRangerNumber)
                        .addGap(252, 252, 252))))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(16, 16, 16)
                .addComponent(jLabel10)
                .addGap(15, 15, 15)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(lblTreesPlanted))
                .addGap(48, 48, 48)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblForestCoverage)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(lblVolunteerEnrolled)
                .addGap(50, 50, 50)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(lblRangerNumber)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        activityPanel.add(homePanel, "card5");

        addRangerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("ADD RANGER"));

        jLabel1.setText("RANGER ID");

        lblRangerName.setText("RANGER NAME");

        lblRangerDOB.setText("DOB");

        lblRangerPass.setText("PASSWORD");

        txtAddRangerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddRangerNameActionPerformed(evt);
            }
        });

        lblSubimitionStatus.setText("AWAITING SUBMITION");

        jButton1.setText("SUBMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblRangerDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ranger ID", "Ranger Name", "DOB", "Pass"
            }
        ));
        tblRangerDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRangerDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblRangerDetails);

        btnRangerUpdate.setText("UPDATE");
        btnRangerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRangerUpdateActionPerformed(evt);
            }
        });

        jButton2.setText("NEW");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnRangerDelete.setText("DELETE");
        btnRangerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRangerDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addRangerPanelLayout = new javax.swing.GroupLayout(addRangerPanel);
        addRangerPanel.setLayout(addRangerPanelLayout);
        addRangerPanelLayout.setHorizontalGroup(
            addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addRangerPanelLayout.createSequentialGroup()
                .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addRangerPanelLayout.createSequentialGroup()
                        .addContainerGap(255, Short.MAX_VALUE)
                        .addComponent(lblSubimitionStatus))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addRangerPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblRangerName)
                            .addComponent(lblRangerDOB)
                            .addComponent(lblRangerPass))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAddRangerPass)
                            .addComponent(txtAddRangerDOB, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtAddRangerName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddRangerID)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addRangerPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRangerDelete)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRangerUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        addRangerPanelLayout.setVerticalGroup(
            addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addRangerPanelLayout.createSequentialGroup()
                .addGap(0, 54, Short.MAX_VALUE)
                .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addRangerPanelLayout.createSequentialGroup()
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtAddRangerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRangerName)
                            .addComponent(txtAddRangerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRangerDOB)
                            .addComponent(txtAddRangerDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRangerPass)
                            .addComponent(txtAddRangerPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(addRangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(btnRangerUpdate)
                            .addComponent(jButton2)
                            .addComponent(btnRangerDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubimitionStatus))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        activityPanel.add(addRangerPanel, "card2");

        addVolunteerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray), "ADD VOLUNTEER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 13))); // NOI18N

        lblVolName.setText("VOLUNTEER ID:");

        jLabel2.setText("VOLUNTEER NAME:");

        jLabel3.setText("DATE OF BIRTH");

        lblVolPass.setText("PASSWORD");

        txtVolID.setEditable(false);
        txtVolID.setBackground(java.awt.Color.lightGray);
        txtVolID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVolIDActionPerformed(evt);
            }
        });

        txtVolPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtVolPassKeyPressed(evt);
            }
        });

        btnAddVolSubmit.setText("SUBMIT");
        btnAddVolSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVolSubmitActionPerformed(evt);
            }
        });
        btnAddVolSubmit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddVolSubmitKeyPressed(evt);
            }
        });

        submitionStatus.setText("AWAITING SUBMITION");

        comboxRanger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxRangerActionPerformed(evt);
            }
        });

        lblRangerIncharge.setText("RANGER INCHARGE");

        tblVolunteerDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Volunteer Num", "Volunteer Name", "DOB", "Trees Planted", "Supervisor", "Pass"
            }
        ));
        tblVolunteerDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVolunteerDetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVolunteerDetails);

        jLabel4.setText("TREES PLANTED");

        lbltreesPlanted.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbltreesPlanted.setText("N/A");

        lblPlus.setText("PLUS");

        btnVolunteerUpdate.setText("UPDATE");
        btnVolunteerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolunteerUpdateActionPerformed(evt);
            }
        });

        btnNewVolunteer.setText("NEW");
        btnNewVolunteer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVolunteerActionPerformed(evt);
            }
        });

        btnVolDelete.setText("DELETE ");
        btnVolDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addVolunteerPanelLayout = new javax.swing.GroupLayout(addVolunteerPanel);
        addVolunteerPanel.setLayout(addVolunteerPanelLayout);
        addVolunteerPanelLayout.setHorizontalGroup(
            addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                                .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRangerIncharge)
                                    .addComponent(lblVolPass))
                                .addGap(6, 6, 6)
                                .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVolPass, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(comboxRanger, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                                .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblVolName)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                                                .addComponent(lbltreesPlanted, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblPlus)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                                .addComponent(txtAddTreesPlanted, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtVolName)
                                            .addComponent(txtVolID)
                                            .addComponent(volDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addComponent(submitionStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewVolunteer)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolunteerUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddVolSubmit)))
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        addVolunteerPanelLayout.setVerticalGroup(
            addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addVolunteerPanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(addVolunteerPanelLayout.createSequentialGroup()
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVolName)
                            .addComponent(txtVolID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtVolName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(volDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtAddTreesPlanted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbltreesPlanted)
                            .addComponent(lblPlus))
                        .addGap(45, 45, 45)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboxRanger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRangerIncharge))
                        .addGap(40, 40, 40)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVolPass)
                            .addComponent(txtVolPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddVolSubmit)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addVolunteerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnVolunteerUpdate)
                                .addComponent(btnNewVolunteer)
                                .addComponent(btnVolDelete)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(submitionStatus)
                        .addGap(24, 24, 24))))
        );

        activityPanel.add(addVolunteerPanel, "card4");

        getContentPane().add(activityPanel, java.awt.BorderLayout.CENTER);

        statusPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray), "STATUS"));

        lblconnectionStatus.setText("AWAITING CONNECTION STATUS");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblconnectionStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(681, Short.MAX_VALUE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblconnectionStatus)
                .addGap(18, 18, 18))
        );

        getContentPane().add(statusPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddVolunteerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVolunteerActionPerformed
       
        activityPanel.removeAll();
        initializeVolunteerForm();
        comboxRanger.removeAllItems();
        fillRangerCombox();
        activityPanel.add(addVolunteerPanel);
        activityPanel.revalidate();
        activityPanel.repaint();
        
        DefaultTableModel model=(DefaultTableModel)tblVolunteerDetails.getModel();
        model.setRowCount(0);
        fillVolunteerTable();
    }//GEN-LAST:event_btnAddVolunteerActionPerformed

    private void txtVolIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVolIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVolIDActionPerformed

    private void btnAddVolSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVolSubmitActionPerformed
                btnAddVolSubmit();
                
    }//GEN-LAST:event_btnAddVolSubmitActionPerformed

    private void comboxRangerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxRangerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboxRangerActionPerformed

    private void btnAddVolSubmitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddVolSubmitKeyPressed
        
        
    }//GEN-LAST:event_btnAddVolSubmitKeyPressed

    private void txtVolPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVolPassKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
           btnAddVolSubmit.doClick();
           txtVolName.setText("");
           txtVolPass.setText("");
        }
    }//GEN-LAST:event_txtVolPassKeyPressed

    private void txtAddRangerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddRangerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddRangerNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        btnAddRangerSubmit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddRangerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRangerActionPerformed
       
        activityPanel.removeAll();
        activityPanel.add(addRangerPanel);
        activityPanel.revalidate();
        activityPanel.repaint();
        DefaultTableModel model=(DefaultTableModel)tblRangerDetails.getModel();
        model.setRowCount(0);
        fillRangerTable();
    }//GEN-LAST:event_btnAddRangerActionPerformed

    private void tblRangerDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRangerDetailsMouseClicked
        int i=tblRangerDetails.getSelectedRow();
        TableModel model=tblRangerDetails.getModel();
        txtAddRangerID.setText(model.getValueAt(i,0).toString());
        txtAddRangerName.setText(model.getValueAt(i,1).toString());
        txtAddRangerDOB.setDate((Date) model.getValueAt(i,2));
        txtAddRangerPass.setText(model.getValueAt(i,3).toString());
        
    }//GEN-LAST:event_tblRangerDetailsMouseClicked

    private void btnRangerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRangerUpdateActionPerformed
        updateRanger();
    }//GEN-LAST:event_btnRangerUpdateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    initializeRangerForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblVolunteerDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVolunteerDetailsMouseClicked
        int i=tblVolunteerDetails.getSelectedRow();
        TableModel model=tblVolunteerDetails.getModel();
        txtVolID.setText(model.getValueAt(i,0).toString());
        txtVolName.setText(model.getValueAt(i,1).toString());
        volDateChooser.setDate((Date) model.getValueAt(i,2));
        lbltreesPlanted.setText(model.getValueAt(i,3).toString());
        comboxRanger.setSelectedItem(model.getValueAt(i,4).toString());
        txtVolPass.setText(model.getValueAt(i,5).toString());
        txtAddTreesPlanted.setText("0");
    }//GEN-LAST:event_tblVolunteerDetailsMouseClicked

    private void btnVolunteerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolunteerUpdateActionPerformed
        updateVolunteer();
         
    }//GEN-LAST:event_btnVolunteerUpdateActionPerformed

    private void btnNewVolunteerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVolunteerActionPerformed
        initializeVolunteerForm();
        
    }//GEN-LAST:event_btnNewVolunteerActionPerformed

    private void btnVolDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolDeleteActionPerformed
        volunteerDelete();
    }//GEN-LAST:event_btnVolDeleteActionPerformed

    private void btnRangerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRangerDeleteActionPerformed
        rangerDelete();
    }//GEN-LAST:event_btnRangerDeleteActionPerformed

    private void btnStatisticsPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsPanelActionPerformed
        activityPanel.removeAll();
        activityPanel.add(homePanel);
        homePagePopulate();
        activityPanel.revalidate();
        activityPanel.repaint();
                
    }//GEN-LAST:event_btnStatisticsPanelActionPerformed

    public void updateRanger()
    {
        try
        {
            QueryProcess qp2=new QueryProcess();  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String RanID=txtAddRangerID.getText().trim();
        String RanName=txtAddRangerName.getText().trim();
        String RanDOB=sdf.format(txtAddRangerDOB.getDate());
        String Ranpass=txtAddRangerPass.getText().trim();        
        String query="UPDATE `ranger` SET `Ranger_Name`='"+RanName+"',`DOB`='"+RanDOB+"',`userPass`='"+Ranpass+"' WHERE Ranger_ID="+RanID;
        qp2.upQuery(query);
        initializeVolunteerForm();
        lblSubimitionStatus.setText("UPDATE SUCESSFUL");
        lblSubimitionStatus.setForeground(new Color(12, 140, 1));
        
        DefaultTableModel model=(DefaultTableModel)tblRangerDetails.getModel();
        model.setRowCount(0);
        fillRangerTable();
        
        }catch(Exception e)
                {
                    lblSubimitionStatus.setText("UPDATE FAILED!!");
        lblSubimitionStatus.setForeground(new Color(109, 1, 1));
            System.out.println("UPDATE ERROR: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());                    
                }
    }
    
    public void updateVolunteer()
    {
         try
        {
            QueryProcess qp4=new QueryProcess();  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String VolID=txtVolID.getText().trim();
        String VolName=txtVolName.getText().trim();
        String VolDOB=sdf.format(volDateChooser.getDate());
        String rangerID=comboxRanger.getSelectedItem().toString();
        int VolAddTrees=Integer.parseInt(lbltreesPlanted.getText().trim())+Integer.parseInt(txtAddTreesPlanted.getText().trim());
        String Volpass=txtVolPass.getText().trim();        
        String query="UPDATE `volunteer` SET `Vol_Name`='"+VolName+"',`Ranger_ID`='"+rangerID+"',`Number_of_trees_planted`='"+VolAddTrees+"',`DOB`='"+VolDOB+"',`userPass`='"+Volpass+"' WHERE Vol_Num="+VolID;
        
        qp4.upQuery(query);
        //qp4.upQuery(forestValues);
       
        lblSubimitionStatus.setText("UPDATE SUCESSFUL");
        lblSubimitionStatus.setForeground(new Color(12, 140, 1));
       
        }catch(Exception e)
                {
                    lblSubimitionStatus.setText("UPDATE FAILED!!");
        lblSubimitionStatus.setForeground(new Color(109, 1, 1));
            System.out.println("UPDATE ERROR: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());                    
                }
         
        try
         {
             int ForestAdd=Integer.parseInt(txtAddTreesPlanted.getText().trim());
             QueryProcess qp5=new QueryProcess();
             String forestValues="UPDATE `forest` SET `Number_Of_Trees`=`Number_Of_Trees`+'"+ ForestAdd+"'WHERE Forest_ID=1";
             qp5.upQuery(forestValues);
             
              initializeVolunteerForm();
               DefaultTableModel model=(DefaultTableModel)tblVolunteerDetails.getModel();
        model.setRowCount(0);
        fillVolunteerTable();
         txtAddTreesPlanted.setText("");
         }catch
                 (Exception e)
                {
                    lblSubimitionStatus.setText("UPDATE FAILED!!");
        lblSubimitionStatus.setForeground(new Color(109, 1, 1));
            System.out.println("UPDATE ERROR: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "QUERY ERROR: "+e.getMessage());                    
                }                 
                         
    }
    public void volunteerDelete()
    {
    QueryProcess deleteVolQry =new QueryProcess();
      
       try
       {
           JOptionPane.showMessageDialog(null, "Are you Sure You Want to Delete Selected Row? ");
           int i=tblVolunteerDetails.getSelectedRow();
        TableModel delModel=tblVolunteerDetails.getModel();
        String delVolID=(delModel.getValueAt(i,0).toString());
           deleteVolQry.upQuery("DELETE FROM volunteer WHERE Vol_Num="+delVolID);
           JOptionPane.showMessageDialog(null, "Delete Sucessful ");
           DefaultTableModel model=(DefaultTableModel)tblVolunteerDetails.getModel();
        model.setRowCount(0);
        fillVolunteerTable();
       
       }catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Deletion FAILED! ");
           System.out.println("SQL DELETE ERROR:: "+e.getMessage());
       }
    }
    
    public void rangerDelete()
    {
        {
    QueryProcess deleteRanQry =new QueryProcess();
      
       try
       {
           JOptionPane.showMessageDialog(null, "Are you Sure You Want to Delete Selected Row? ");
           
           int i=tblRangerDetails.getSelectedRow();
        TableModel delRanModel=tblRangerDetails.getModel();
        String delRanID=(delRanModel.getValueAt(i,0).toString());
           deleteRanQry.upQuery("DELETE FROM ranger WHERE Ranger_ID="+delRanID);
           JOptionPane.showMessageDialog(null, "Delete Sucessful ");
           DefaultTableModel ranModel=(DefaultTableModel)tblRangerDetails.getModel();
        ranModel.setRowCount(0);
        fillRangerTable();
       
       }catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Deletion FAILED! ");
           System.out.println("SQL DELETE ERROR:: "+e.getMessage());
       }
    }
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activityPanel;
    private javax.swing.JPanel addRangerPanel;
    private javax.swing.JPanel addVolunteerPanel;
    private javax.swing.JButton btnAddRanger;
    private javax.swing.JButton btnAddVolSubmit;
    private javax.swing.JButton btnAddVolunteer;
    private javax.swing.JButton btnNewVolunteer;
    private javax.swing.JButton btnRangerDelete;
    private javax.swing.JButton btnRangerUpdate;
    private javax.swing.JButton btnStatisticsPanel;
    private javax.swing.JButton btnVolDelete;
    private javax.swing.JButton btnVolunteerUpdate;
    private javax.swing.JComboBox<String> comboxRanger;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblForestCoverage;
    private javax.swing.JLabel lblPlus;
    private javax.swing.JLabel lblRangerDOB;
    private javax.swing.JLabel lblRangerIncharge;
    private javax.swing.JLabel lblRangerName;
    private javax.swing.JLabel lblRangerNumber;
    private javax.swing.JLabel lblRangerPass;
    private javax.swing.JLabel lblSubimitionStatus;
    private javax.swing.JLabel lblTreesPlanted;
    private javax.swing.JLabel lblVolName;
    private javax.swing.JLabel lblVolPass;
    private javax.swing.JLabel lblVolunteerEnrolled;
    private javax.swing.JLabel lblconnectionStatus;
    private javax.swing.JLabel lbltreesPlanted;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel submitionStatus;
    private javax.swing.JTable tblRangerDetails;
    private javax.swing.JTable tblVolunteerDetails;
    private com.toedter.calendar.JDateChooser txtAddRangerDOB;
    private javax.swing.JTextField txtAddRangerID;
    private javax.swing.JTextField txtAddRangerName;
    private javax.swing.JPasswordField txtAddRangerPass;
    private javax.swing.JTextField txtAddTreesPlanted;
    private javax.swing.JTextField txtVolID;
    private javax.swing.JTextField txtVolName;
    private javax.swing.JPasswordField txtVolPass;
    private com.toedter.calendar.JDateChooser volDateChooser;
    // End of variables declaration//GEN-END:variables
}

/*
This class is responsible for creating the BORROWER interface.
The interface is responsible for the following
1> Add a new borrower into the database;
2> Gaurantee that no buyer has more than 1 library card
 */

/*
Author : Dhananjay Singh
netId : dxs145530
UTD Id : 2021250625
*/
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
public class Borrower extends JFrame implements ActionListener
{
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Add Borrower");
    JOptionPane op=new JOptionPane();
    public JLabel l1=new JLabel("Card_No :");
    public JLabel l2=new JLabel("FName  :");
    public JLabel l3=new JLabel("LName :");
    public JLabel l4=new JLabel("Email :");
    public JLabel l6=new JLabel("Address:");
    public JLabel l5=new JLabel("Phone :");
    public JButton b1=new JButton("ADD");
    public JButton b2=new JButton("HOME");
    public JTextField tf_cardNo=new JTextField(50);
    public JTextField tf_Fname=new JTextField(50);
    public JTextField tf_Lname=new JTextField(50);
    public JTextField tf_Email=new JTextField(50);
    public JTextField tf_Address=new JTextField(50);
    public JTextField tf_Phone=new JTextField(50);
    public Borrower()
    {
        f.setLayout(null);
        f.setBounds(0,0,900,60);
        l1.setBounds(30,30,110,30);
        l1.setFont(fo);
        l1.setForeground(Color.BLACK);
        l1.setVisible(true);
        tf_cardNo.setBounds(142,30,200,30);
        tf_cardNo.setVisible(true);
        tf_Fname.setBounds(142,70,200,30);
        tf_Fname.setVisible(true);
        tf_Lname.setBounds(142,110,200,30);
        tf_Lname.setVisible(true);
        tf_Email.setBounds(142,150,200,30);
        tf_Email.setVisible(true);
        tf_Phone.setBounds(142,190,200,30);
        tf_Phone.setVisible(true);
        tf_Address.setBounds(142,230,200,30);
        tf_Address.setVisible(true);
        l2.setBounds(30,70,110,30);
        l2.setFont(fo);
        l2.setForeground(Color.BLACK);
        l2.setVisible(true);
        l3.setBounds(30,110,110,30);
        l3.setForeground(Color.BLACK);
        l3.setFont(fo);
        l3.setVisible(true);
        l4.setBounds(30,150,110,30);
        l4.setForeground(Color.BLACK);
        l4.setFont(fo);
        l4.setVisible(true);
        l5.setBounds(30,190,110,30);
        l5.setForeground(Color.BLACK);
        l5.setFont(fo);
        l5.setVisible(true);
        l6.setBounds(30,230,110,30);
        l6.setForeground(Color.BLACK);
        l6.setFont(fo);
        l6.setVisible(true);
        b1.setBounds(542,30,110,30);
        b1.addActionListener(this);
        b1.setVisible(true);
        b2.setBounds(542,70,110,30);
        b2.addActionListener(this);
        b2.setVisible(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.add(b2);
        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(l4);
        f.add(l5);
        f.add(l6);
        f.add(b1);
        f.add(tf_cardNo);
        f.add(tf_Fname);
        f.add(tf_Lname);
        f.add(tf_Address);
        f.add(tf_Phone);
        f.add(tf_Email);
        f.setSize(800,400);
        f.setResizable(false);
        f.setVisible(true);
        setCardNo();
    }
    //We need unique card numbers for each new addition. Hence, we automatically provide a new card number as follows
    public void setCardNo()
    {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password = "";
            String card_no="";
            int card=0;
            int max=0;
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select card_no from borrower"); 
            while (rs.next()) 
            {
                linect++;
		card_no=rs.getString("card_no");
                card=Integer.parseInt(card_no); //Automatically generate a new card number depending on the maximum serial number of existing records
                //Hence the uniqueness of every card number is gauranteed
                if(card>=max)
                {
                    max=card;
                }
            }
            tf_cardNo.setText(""+(card+1));
            rs.close();
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
    }
    public void actionPerformed(ActionEvent e)
    {
        //If addition is requested we need to insert into the borrowers table and also check if no other user with the same name and email address exist
        if(e.getSource()==b1)
        {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password = "";
            String card_no="";
            String fname="";
            String lname="";
            String email="";
            int card=0;
            int flag=0;
            int max=0;
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs=stmt.executeQuery("select fname,lname,email from borrower;");
            while (rs.next()) 
            {
                linect++;
		fname=rs.getString("fname");
                lname=rs.getString("lname");
                email=rs.getString("email");
                if(tf_Fname.getText().equalsIgnoreCase(fname)&&tf_Lname.getText().equalsIgnoreCase(lname)&&tf_Email.getText().equalsIgnoreCase(email))
                {
                    flag=1;
                    break;
                }
            }
            if(flag==0)
            {
                stmt.executeUpdate("insert into borrower (card_no,fname,lname,email,address1,phone) values ('"+tf_cardNo.getText()+"','"+tf_Fname.getText()+"','"+tf_Lname.getText()+"','"+tf_Email.getText()+"','"+tf_Address.getText()+"','"+tf_Phone.getText()+"');");
                tf_Email.setText("");
                tf_Address.setText("");
                tf_Fname.setText("");
                tf_Lname.setText("");
                tf_Phone.setText("");
                setCardNo();
            }
            else
            {
                tf_Email.setText("");
                tf_Address.setText("");
                tf_Fname.setText("");
                tf_Lname.setText("");
                tf_Phone.setText("");
                JOptionPane.showMessageDialog(null,"Data already exists", "Duplicate Entry", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
            conn.close();
            }
            catch(SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
            }
        }
        //Navigate to home page//
        if(e.getSource()==b2)
        {
            HomePage hp=new HomePage();
        }
    }
}

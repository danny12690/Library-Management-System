/*
This class is responsible for the following:
1> Calculate fine for each borrower who has borrowed a book in the background.
2> Fetch fine details for each borrower and populate the table;
3> Pay fine and update in fine table
 */


/**
 *
 * @author Dhananjay Singh
 * Net Id : dxs145530
 * Utd Id : 2021250625
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
import javax.swing.table.DefaultTableModel;
public class Fine extends JFrame implements ActionListener
{
    public JButton b1=new JButton("FETCH RECORD");
    public JButton b2=new JButton("PAY FINE");
    public JButton b3=new JButton("HOME");
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Book Check In");
    JOptionPane op=new JOptionPane();
    public JLabel l2=new JLabel("Card No.");
    public JTextField tf_cardNo=new JTextField(20);
    DefaultTableModel model;
    JTable table;
    String col[] = {"Card No.","Fine"};
    public Fine()
    {
        f.setLayout(null);
        f.setBounds(0,0,900,60);
        l2.setBounds(30,70,110,30);
        tf_cardNo.setBounds(142,70,110,20);
        b1.setBounds(420,30,150,30);
        b1.addActionListener(this);
        b1.setVisible(true);
        b2.setBounds(420,70,150,30);
        b2.addActionListener(this);
        b2.setVisible(true);
        b3.setBounds(420,110,150,30);
        b3.addActionListener(this);
        b3.setVisible(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.setSize(800,400);
        f.add(l2);
        f.add(b1);
        f.add(b3);
        calculateFineForAll();
        f.add(tf_cardNo);
        f.setResizable(false);
        model=new DefaultTableModel(col,0);
        table=new JTable(model){@Override
		public boolean isCellEditable(int arg0, int arg1) {
		
			return true;
		}};
	JScrollPane pane = new JScrollPane(table);
        pane.setVisible(true);
        pane.setBounds(30,190,1400,400);
        pane.setVisible(true);
        f.add(pane);
        f.add(b2);
        f.setSize(1500,800);
        f.setVisible(true);
    }
    //Since calculation of fine is a time dependent utility this will be done in the background every time the class is called
    public void calculateFineForAll()
    {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            String days="";
            String cardNo="";
            int count=1;
            double fine=0.0d;
            int linect = 0;
            try
            {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            Statement stmt2= conn.createStatement();
            stmt.execute("use books;");
            stmt.executeUpdate("update book_loans set fine=(select datediff((select curdate()),date_out))*0.25 where date_in is not null;");
            stmt.executeUpdate("delete from fine;");
            ResultSet rs=stmt.executeQuery("select card_no,sum(fine) as Total from book_loans group by(card_no);");
            while(rs.next())
            {
                stmt2.executeUpdate("insert into fine values ('"+rs.getString("card_no")+"','"+rs.getString("Total")+"');");
            }
            rs.close();
            conn.close();
            }
            catch(Exception ex){System.out.println("Error in connection: " + ex.getMessage());}
        }
    //To populate fine records of the borrower
    //We only fetch the record if fine due is non zero
    public void populateTable()
    {
            model.setRowCount(0);
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            String row[]=new String[2];
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select card_no,fine from fine where card_no='"+tf_cardNo.getText()+"' and fine!=0;");
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("card_no");
                row[1]=rs.getString("fine");
                model.addRow(row);
            }
            rs.close();
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
    }
    public void actionPerformed(ActionEvent e)
    {
        //If the record of a borrower is requested//
        if(e.getSource()==b1)
        {
            populateTable();
        }
        //if fine payment is initiated then the fine table must be updated accordingly//
        if(e.getSource()==b2)
        {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            stmt.executeUpdate("update fine set fine=0 where card_no='"+tf_cardNo.getText()+"';");
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
        }
        //navigate to home page//
        if(e.getSource()==b3)
        {
            HomePage hp=new HomePage();
        }
    }
}

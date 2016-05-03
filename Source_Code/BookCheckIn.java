/*
 This class is responsible for checking in books borrowed by the borrower
 */

/**
 *
 * @author Dhananjay Singh
 * Net Id: dxs145530
 * UTD Id : 2021250625
 */
import static database.design.ConnectionService.conn;
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
public class BookCheckIn extends JFrame implements ActionListener
{
    public JButton b1=new JButton("FETCH RECORD");
    public JButton b2=new JButton("CHECK IN");
    public JButton b3=new JButton("HOME");
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Book Check In");
    JOptionPane op=new JOptionPane();
    public JLabel l2=new JLabel("Card No.");
    public JTextField tf_cardNo=new JTextField(20);
    DefaultTableModel model;
    JTable table;
    String col[] = {"BookId","Branch_Id","Due Date"};
    public BookCheckIn()
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
        f.add(b2);
        f.add(b3);
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
        f.setSize(1500,800);
        f.setVisible(true);
    }
    //We fetch records and populate the table based on card_no specified in the text Field
    //The records populated in the table are selectable and hence provide on screen check in option
    public void fetchRecords()
    {
            model.setRowCount(0);
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            String book_Id = "";
            String branch_Id="";
            String row[]=new String[3];
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select book_id,branch_id,due_date from book_loans where card_no='"+tf_cardNo.getText()+"' and date_in is null;");
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("book_Id");
                row[1]=rs.getString("branch_Id");
                row[2]=rs.getString("due_date");
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
        //Navigate to home page
        if(e.getSource()==b3)
        {
            HomePage hp=new HomePage();
        }
        //If records are requested//
        if(e.getSource()==b1)
        {
            fetchRecords();
        }
        //The selected book has to be checked in against the specified card_no
        //and the no_of_copie in book_copies must increase by 1 and date_in in book_loans must be updated as well
        //Hence, we update book_loans and book_copies accordingly
        if(e.getSource()==b2)
        {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            String noOfLoans="";
            String maxLId="";
            int selectRow=table.getSelectedRow();
            int count=0;
            int maxLoanId=0;
            int flag=0;
            String date_in="";
            String row[]=new String[3];
            row[0]=model.getValueAt(selectRow,0).toString();
            row[1]=model.getValueAt(selectRow,1).toString();
            row[2]=model.getValueAt(selectRow,2).toString();
            int linect = 0;
            try
            {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs=stmt.executeQuery("select curdate();");
            while(rs.next())
            {
            date_in=rs.getString("curdate()");
            }
            stmt.executeUpdate("update book_loans set date_in='"+date_in+"' where book_id='"+row[0]+"' and branch_id='"+row[1]+"' and card_no='"+tf_cardNo.getText()+"';");
            stmt.executeUpdate("update book_copies set no_of_copies=no_of_copies+1 where book_id='"+row[0]+"' and branch_id='"+row[1]+"';");
            rs.close();
            conn.close();
            }
            catch(Exception ex){System.out.println("Error in connection: " + ex.getMessage());}
        }
    }
}
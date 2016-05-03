/*
 This class is responsible for book loaning activities such as :
1> fetching details of already borrowed books;
2> checking if borrow limit is exceeded or not ie no one can borrow more than 3 books;
3> can navigate to home page or check in window;
 */

/**
 *
 * @author Dhananjay Singh
 * Net Id : dxs145530
 * UTD Id: 2021250625
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
public class BookLoans extends JFrame implements ActionListener
{
    public JButton b1=new JButton("BOOK SEARCH");
    public JButton b2=new JButton("CHECK OUT");
    public JButton b3=new JButton("CHECK IN");
    public JButton b4=new JButton("HOME");
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Book Loans");
    JOptionPane op=new JOptionPane();
    public JLabel l1=new JLabel("Book Title");
    public JLabel l2=new JLabel("Card No.");
    public JTextField tf_cardNo=new JTextField(20);
    public JComboBox bookTitle=new JComboBox();
    DefaultTableModel model;
    JTable table;
    String col[] = {"BookId","Branch_Id","No. Of Copies"};
    public BookLoans()
    {
        f.setLayout(null);
        f.setBounds(0,0,900,60);
        l1.setBounds(30,30,110,30);
        l2.setBounds(30,70,110,30);
        tf_cardNo.setBounds(142,70,110,20);
        bookTitle.setBounds(142,30,250,30);
        bookTitle.setVisible(true);
        bookTitle.addActionListener(this);
        b1.setBounds(420,30,150,30);
        b1.addActionListener(this);
        b1.setVisible(true);
        b2.setBounds(420,70,150,30);
        b2.addActionListener(this);
        b2.setVisible(true);
        b3.setBounds(420,110,150,30);
        b3.addActionListener(this);
        b3.setVisible(true);
        b4.setBounds(420,150,150,30);
        b4.addActionListener(this);
        b4.setVisible(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.setSize(800,400);
        f.add(bookTitle);
        f.add(l1);
        f.add(l2);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);
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
        populateDropDown();
    }
    public void populateDropDown() //To populate books which can be selected via a drop down box//
    {
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password = "";
            String title="";
            int card=0;
            int max=0;
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select title from book"); 
            while (rs.next()) 
            {
                linect++;
		title=rs.getString("title");
                bookTitle.addItem(title);
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
        if(e.getSource()==b1) //To populate table showing the book_id, branch_id and number of copies available at the branch//
        {
            model.setRowCount(0);
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password="";
            String book_Id = "";
            String branch_Id="";
            String noOfCopies="";
            String row[]=new String[3];
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select book.book_id,branch_id,no_of_copies from book,book_copies where book.book_id=book_copies.book_id and book.title='"+bookTitle.getSelectedItem().toString()+"' and no_of_copies !=0;"); 
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("book_Id");
                row[1]=rs.getString("branch_Id");
                row[2]=rs.getString("no_of_copies");
                model.addRow(row);
            }
            rs.close();
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
        }
        /*
        We enter the card_no and click check out, then the database tables are updated accordingly
        1> book_loans should have a new entry about this checkout
        2> book_copis must have a reduced number of available copies
        */
        
        if(e.getSource()==b2) //to select the book to be checked out from the table//
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
            String due_date="";
            String date_out="";
            String row[]=new String[3];
            row[0]=model.getValueAt(selectRow,0).toString();
            row[1]=model.getValueAt(selectRow,1).toString();
            row[2]=model.getValueAt(selectRow,2).toString();
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs=stmt.executeQuery("select curdate() as date;");
            while(rs.next())
            {
                date_out=rs.getString("date");
            }
            rs=stmt.executeQuery("select count(*) from book_loans where date_in is NULL group by card_no having card_no='"+tf_cardNo.getText()+"';");
            if(rs.next())
            {
                noOfLoans=rs.getString("count(*)");
                count=Integer.parseInt(noOfLoans);
                if(count>=3)
                {
                    JOptionPane.showMessageDialog(null,"No more loans allowed!!", "Limit reached", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                stmt.executeUpdate("insert into book_loans (book_id,branch_id,card_no,date_out) values ('"+row[0]+"','"+row[1]+"','"+tf_cardNo.getText()+"','"+date_out+"');");
                stmt.executeUpdate("update book_copies set no_of_copies=no_of_copies-1 where book_id='"+row[0]+"' and branch_id='"+row[1]+"';");
                stmt.executeUpdate("update book_loans set due_date=DATE_ADD(date_out, INTERVAL 14 DAY);");
                }
            }
            else
            {
                stmt.executeUpdate("insert into book_loans (book_id,branch_id,card_no,date_out) values ('"+row[0]+"','"+row[1]+"','"+tf_cardNo.getText()+"','"+date_out+"');");
                stmt.executeUpdate("update book_copies set no_of_copies=no_of_copies-1 where book_id='"+row[0]+"' and branch_id='"+row[1]+"';");
                stmt.executeUpdate("update book_loans set due_date=DATE_ADD(date_out, INTERVAL 14 DAY);");
            }
            rs.close();
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
        }
        
        //If a boorower wants to check in a book //
        if(e.getSource()==b3)
        {
            BookCheckIn bci=new BookCheckIn();
        }
        //Navigate to home page //
        if(e.getSource()==b4)
        {
            HomePage hp=new HomePage();
        }
    }
}

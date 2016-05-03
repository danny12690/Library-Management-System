/*
this class is responsible for creating the book search interface;
1> Substring matching is enabled and either of the fields can be used to search for books
2> The book search results are populated in the table with book_id,title,authors,branch_id,Branch_name and number of copies availabe
at the branch
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
public class Library_Interface extends JFrame implements ActionListener
{
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Library Sstem v1");
    JOptionPane op=new JOptionPane();
    public JLabel l1=new JLabel("Book Id :");
    public JLabel l2=new JLabel("Author  :");
    public JLabel l3=new JLabel("Title :");
    public JButton b1=new JButton("SEARCH");
    public JButton b2=new JButton("HOME");
    public JTextField tf_bookId=new JTextField(50);
    public JTextField tf_author=new JTextField(50);
    public JTextField tf_title=new JTextField(50);
    DefaultTableModel model;
    JTable table;
    ConnectionService cs;
    String col[] = {"BookId","Title","Authors","BranchId","BranchName","No. Of Copies"};
    public Library_Interface()
    {
        f.setLayout(null);
        f.setBounds(0,0,900,60);
        l1.setBounds(30,30,110,30);
        l1.setFont(fo);
        l1.setForeground(Color.BLACK);
        l1.setVisible(true);
        tf_bookId.setBounds(142,30,200,30);
        tf_bookId.setVisible(true);
        tf_author.setBounds(542,30,200,30);
        tf_author.setVisible(true);
        tf_title.setBounds(942,30,200,30);
        tf_title.setVisible(true);
        l2.setBounds(830,30,110,30);
        l2.setFont(fo);
        l2.setForeground(Color.BLACK);
        l2.setVisible(true);
        l3.setBounds(430,30,110,30);
        l3.setForeground(Color.BLACK);
        l3.setFont(fo);
        l3.setVisible(true);
        b1.setBounds(1342,30,110,30);
        b1.addActionListener(this);
        b1.setVisible(true);
        b2.setBounds(1342,70,110,30);
        b2.addActionListener(this);
        b2.setVisible(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(b1);
        f.add(b2);
        f.add(tf_bookId);
        f.add(tf_author);
        f.add(tf_title);
        model=new DefaultTableModel(col,0);
        table=new JTable(model){@Override
		public boolean isCellEditable(int arg0, int arg1) {
		
			return true;
		}};
	JScrollPane pane = new JScrollPane(table);
        pane.setVisible(true);
        pane.setBounds(30,100,1400,400);
        pane.setVisible(true);
        f.setSize(1500,800);
        f.add(pane);
        f.setResizable(false);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) //book search clicked//
    {
        //Nothing will be displayed in the table if all search fields are blank//
        if(e.getSource()==b1)
        {
            model.setRowCount(0);
            Connection conn=null;
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String password = "";
            String book_Id="";
            String title="";
            String row[]=new String[6];
            String authors="";String branch_id="";String noOfCopies="";
            int dno;
            int linect = 0;
            try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("use books;");
            ResultSet rs = stmt.executeQuery("select book.book_id,title,authors,book_copies.branch_id,branch_name,no_of_copies from book,book_authors,book_copies,library_branch where book.book_id=book_authors.book_id and book_authors.book_id=book_copies.book_id and library_branch.branch_id=book_copies.branch_Id and Title like '%"+tf_title.getText()+"%';");
            if(!tf_title.getText().equals(""))
            {
            rs = stmt.executeQuery("select book.book_id,title,authors,book_copies.branch_id,branch_name,no_of_copies from book,book_authors,book_copies,library_branch where book.book_id=book_authors.book_id and book_authors.book_id=book_copies.book_id and library_branch.branch_id=book_copies.branch_Id and Title like '%"+tf_title.getText()+"%';");
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("book_Id");
                row[1]=rs.getString("title");
                row[2]=rs.getString("Authors");
                row[3]= rs.getString("branch_Id");
                row[4]= rs.getString("branch_name");
                row[5]= rs.getString("no_of_copies");
                model.addRow(row);
            }
            }
            else if(!tf_author.getText().equals(""))
            {
                rs = stmt.executeQuery("select book.book_id,title,authors,book_copies.branch_id,branch_name,no_of_copies from book,book_authors,book_copies,library_branch where book.book_id=book_authors.book_id and book_authors.book_id=book_copies.book_id and library_branch.branch_id=book_copies.branch_Id and authors like '%"+tf_author.getText()+"%';");
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("book_Id");
                row[1]=rs.getString("title");
                row[2]=rs.getString("Authors");
                row[3]= rs.getString("branch_Id");
                row[4]= rs.getString("branch_name");
                row[5]= rs.getString("no_of_copies");
                model.addRow(row);
            }
            }
            else if(!tf_bookId.getText().equals(""))
            {
                rs = stmt.executeQuery("select book.book_id,title,authors,book_copies.branch_id,branch_name,no_of_copies from book,book_authors,book_copies,library_branch where book.book_id=book_authors.book_id and book_authors.book_id=book_copies.book_id and library_branch.branch_id=book_copies.branch_Id and book_id like '%"+tf_bookId.getText()+"%';");
            while (rs.next()) 
            {
                linect++;
		row[0]=rs.getString("book_Id");
                row[1]=rs.getString("title");
                row[2]=rs.getString("Authors");
                row[3]= rs.getString("branch_Id");
                row[4]= rs.getString("branch_name");
                row[5]= rs.getString("no_of_copies");
                model.addRow(row);
            }
            }
            rs.close();
            conn.close();
            }
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
            }
        //Navigate to home Page//
        if(e.getSource()==b2)
        {
            HomePage hp=new HomePage();
        }
        }
}


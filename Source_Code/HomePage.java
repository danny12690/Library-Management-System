/*
 This class is responsible forcreating the home page from where all other operations will be made possible.

The operations are as follows :
1> Book Search
2> Book Loaning
3> Adding a borrower
4> Caluclation of fine
 */


/**
 *
 * @author Dhananjay Singh
 * Net Id : dxs145530
 * UTD Id : 2021250625
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
public class HomePage extends JFrame implements ActionListener
{
    public JButton b1=new JButton("BOOK SEARCH");
    public JButton b2=new JButton("ADD BORROWER");
    public JButton b3=new JButton("BOOK LOAN");
    public JButton b4=new JButton("FINE CALCULATION");
    Font fo=new Font("Comic Sans MS",Font.ITALIC,16);
    JFrame f=new JFrame("Library System v1");
    JOptionPane op=new JOptionPane();
    public HomePage()
    {
        f.setLayout(null);
        b1.setBounds(270,30,150,30);
        b1.addActionListener(this);
        b1.setVisible(true);
        b2.setBounds(270,70,150,30);
        b2.addActionListener(this);
        b2.setVisible(true);
        b3.setBounds(270,110,150,30);
        b3.addActionListener(this);
        b3.setVisible(true);
        b4.setBounds(270,150,150,30);
        b4.addActionListener(this);
        b4.setVisible(true);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.setSize(700,400);
        f.setResizable(false);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==b1) // book search window//
            {
                Library_Interface li=new Library_Interface();
            }
            if(e.getSource()==b2)
            {
                Borrower borrow=new Borrower(); //Borrower addition Window//
            }
            if(e.getSource()==b3)
            {
                BookLoans loan=new BookLoans(); //Book Loaning Window//
            }
            if(e.getSource()==b4)
            {
                Fine fine=new Fine(); //Fine calculation (in background) and fine retrieval//
            }
        }
    public static void main(String args[])
    {
        HomePage hp=new HomePage();
    }
}

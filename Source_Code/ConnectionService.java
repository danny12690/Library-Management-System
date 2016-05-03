/*
 This is just a reference.
 */


/**
 *
 * @author danny12690
 */
import java.sql.*;

public class ConnectionService 
{
	static Connection conn = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Initialize variables for login creds */
		// String url = "jdbc:mysql://localhost:3306/company"; // direct connect to database in url
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String password = "";

		/* Initialize variables for fields by data type */
		String ssn;
		String firstName;
		String lastName;
		int salary;
		int dno;

		int linect = 0;

		try {
			/* Create a connection to the local MySQL server */
			conn = DriverManager.getConnection(url, user, password);
	
			/* Create a SQL statement object and execute the query */
			Statement stmt = conn.createStatement();
		
			/* Set the current database, if not already set in the getConnection */
			/* Execute a SQL statement */
			stmt.execute("use company;");

			/* Execute a SQL query using SQL as a String object */
			ResultSet rs = stmt.executeQuery("SELECT ssn, fname, lname, salary, dno FROM employee;");

			/*
			// find the column number of a field from its name
			int ssnColumn = rs.findColumn("ssn");
			System.out.println(ssnColumn);
			*/

			/* Iterate through the result set using ResultSet class's next() method */
			while (rs.next()) {

				/* Keep track of the line/tuple count */
				linect++;

				/* Populate Java field variables from MySQL columnLabel */
				// Order of the following statements is not important
				ssn = rs.getString("ssn");
				firstName = rs.getString("fname");
				lastName = rs.getString("lname");
				dno = rs.getInt("dno");
				salary = rs.getInt("salary");

				/* Populate Java field variables from MySQL columnIndex */
				// columnIndex corresponds to the stmt.executeQuery SELECT clause order
				ssn = rs.getString(1);
				firstName = rs.getString(2);
				lastName = rs.getString(3);
				dno = rs.getInt(4);
				salary = rs.getInt(5);
			
				// Display the formatted data to standard out
				System.out.print(linect + ".\t");
				System.out.print(ssn + "\t");
				System.out.print(firstName + "\t");
				System.out.print(lastName + "\t");
				System.out.print(dno + "\t");
				System.out.print(salary + "\t");
				System.out.println();
			} // End while(rs.next())

			// Always close the recordset and connection.
			rs.close();
			conn.close();
			System.out.println("Success!!");
		} 
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}

	/*
	 *
	 */
	static void newln() {
		System.out.println();
	}
}
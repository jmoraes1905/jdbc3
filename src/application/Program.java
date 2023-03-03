package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement ps = null;
		//ResultSet rs = null;
		
		try {
			//Connect to database
			conn = DB.getConnection();
			
			//prepareStatement method receives a string performing a command in sql
			//? WORKS AS PLACE HOLDER FOR VALUES TO be inserted
			/*
			ps = conn.prepareStatement(
				"INSERT INTO seller"+
						"(Name,Email,BirthDate,BaseSalary,DepartmentId)"+
						"VALUES"+
						"(?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS
						);
					
						
				ps.setString(1, "Carl Cohen");
				ps.setString(2, "carlc@gmail.com");
				ps.setDate(3,new java.sql.Date(sdf.parse("10/11/1985").getTime()));
				ps.setDouble(4, 3950.00);
				ps.setInt(5, 4);
			*/
				ps = conn.prepareStatement("insert into department (Name) values('D1'),('D2')",
						Statement.RETURN_GENERATED_KEYS);
				
				int rowsAffected = ps.executeUpdate();
				
				if(rowsAffected>0) {
					ResultSet rs = ps.getGeneratedKeys();
					//rs has a table structure
					//next() returns false when its the last element
					while(rs.next()) {
						int id = rs.getInt(1);
						System.out.println("Done! Id ="+ id);
					}
				}
				else {
					System.out.println("No rows affected");
				}
			//rs = ps.executeQuery("select * from department");
			
			//rs has a table structure
			//next() returns false when its the last element
			//while(rs.next()) {
				//System.out.println(rs.getInt("Id")+", "+ rs.getString("Name"));
			//}
		}
			catch(SQLException e){
				e.printStackTrace();
				
			}
		
		//Adding this clausule is usefull since the sql resources imported arent controlled by the JVM, avoiding memory leaking
		finally {
		
			DB.closeStatement(ps);
			//DB.closeResultSet(rs);
			DB.closeConnection();
		}
		
		
	}

}

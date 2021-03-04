package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcCRUD {
	public static Connection con = null;
	public static String URL = "jdbc:mysql://localhost:3306/clc1";
	public static String USERNAME = "root";
	public static String PASSWORD = "root";
	private boolean flag = Boolean.FALSE;
	Scanner sc = new Scanner(System.in);

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

   public void insertEmployee() throws SQLException {
		Statement st = con.createStatement();
		System.out.println("How many employee do you want to add");
		int noofemp = sc.nextInt();
		for (int i = 0; i < noofemp; i++) {
			Employee e = new Employee();
			System.out.println("Enter name");
			e.setName(sc.next());
			System.out.println("Enter address");
			e.setAddress(sc.next());
			st.executeUpdate(
					"insert into employee(name,address) values('" + e.getName() + "','" + e.getAddress() + "')");
			flag = Boolean.TRUE;
			if (flag == Boolean.TRUE) {
				System.out.println("insert successfully...");
			} else {
				System.out.println("insertion failed...");

			}

		}

	}
   public void selectEmployee() {
	   List<Employee> emp=new ArrayList<Employee>();
	   try(Statement st=con.createStatement();){
	   ResultSet rs=st.executeQuery("select * from employee");
	   while(rs.next()) {
		   Employee e=new Employee();
		   e.setId(rs.getInt(1));
		   e.setName(rs.getString(2));
		   e.setAddress(rs.getString(3));
		   emp.add(e);
	   }
   }catch(Exception e) {
	   e.printStackTrace();
   }
	   for(Employee e : emp) {
		   System.out.println(e.getId()+ "\t" +e.getName()+ "\t\t" +e.getAddress());
	   }
   } 
	public void updateEmployee() {
		try (Statement st = con.createStatement();) {
			System.out.println("Which row do you want to update");
			int id = sc.nextInt();
			System.out.println("Enter name");
			String name = sc.next();
			int result = st.executeUpdate("update Employee set name='" + name + "' where id=" + id + "");
			if (result > 0) {
				System.out.println("successfully update..");
			} else {
				System.out.println("update failed.....");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteEmployee() {
		try (Statement st = con.createStatement();) {
			System.out.println("Which row do you want to delete");
			int id = sc.nextInt();
			int result = st.executeUpdate("delete from employee where id=" + id + "");
			if (result > 0) {
				System.out.println("successfully deleted");
			} else {
				System.err.println("delete failed......");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sumEmployee() {
		try(Statement st=con.createStatement();){
		ResultSet rs=st.executeQuery("select sum(id) from employee");
		if(rs.next()) {
			System.out.println(rs.getInt(1));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void avgEmployee() {
		try(Statement st=con.createStatement();){
			ResultSet rs=st.executeQuery("select avg(id) from employee");
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void maxEmployee() {
		try(Statement st=con.createStatement();){
			ResultSet rs=st.executeQuery("select max(id) from employee");
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void countEmployee() {
		try(Statement st=con.createStatement();){
			ResultSet rs=st.executeQuery("select count(id) from employee");
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException{
		JdbcCRUD crud = new JdbcCRUD();
		// crud.insertEmployee();
		// crud.selectEmployee();
		 //crud.updateEmployee();
		 //crud.deleteEmployee();
		//crud.sumEmployee();
		//crud.avgEmployee();
		crud.maxEmployee();
		//crud.countEmployee();

	}

}

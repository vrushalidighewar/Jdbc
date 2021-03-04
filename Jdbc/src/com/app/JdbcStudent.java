package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcStudent {

	private static Connection con=null;
	private static String url="jdbc:mysql://localhost:3306/clc1";
	private static String username="root";
	private static String password="root";
	Scanner sc=new Scanner(System.in);
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public void createStudent() {
		try(Statement st=con.createStatement();){
	    st.executeUpdate("create table employee (id int auto_increment, name varchar(), address varchar(),primary key(id))");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertStudent() {
		try(Statement st=con.createStatement();){
			System.out.println("How many students do you want to add");
			int noofstud=sc.nextInt();
			for(int i=0;i<noofstud;i++) {
				Student s=new Student();
				
				System.out.println("Enter name");
				s.setName(sc.next());
				System.out.println("Enter address");
				s.setAddress(sc.next());
				
				st.executeUpdate("insert into Student (id,name,address) values("+ s.getId() +",'"+ s.getName() +"','"+ s.getAddress() +"')");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
              JdbcStudent js=new JdbcStudent();
              js.createStudent();
              js.insertStudent();
	}

}

package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcCRUDpreparedStatement {

	private static Connection con = null;
	private static String URL = "jdbc:mysql://localhost:3306/clc1";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
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

	public void insertEmployee() {
		try (PreparedStatement ps = con.prepareStatement("insert into employee(name,address) values(?,?)");) {
			System.out.println("how many employee do you want to add");
			int noofemp = sc.nextInt();
			for (int i = 0; i < noofemp; i++) {
				Employee e = new Employee();
				System.out.println("Enter name");
				e.setName(sc.next());
				System.out.println("Enter address");
				e.setAddress(sc.next());

				ps.setString(1, e.getName());
				ps.setString(2, e.getAddress());

				ps.executeUpdate();
				flag = Boolean.TRUE;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("insert successfully");
		} else {
			System.out.println("insertion failed....");
		}
	}

	public void selectEmployee() {
		List<Employee> emp = new ArrayList<Employee>();
		try (PreparedStatement ps = con.prepareStatement("select * from employee");) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setAddress(rs.getString(3));
				emp.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Employee e : emp) {
			System.out.println(e.getId() + "\t" + e.getName() + "\t " + e.getAddress());
		}
	}

	public void updateEmployee() {
		try(PreparedStatement ps=con.prepareStatement("update employee set name=? where id=?");){
			System.out.println("Which row do you want to update");
			int id=sc.nextInt();
			System.out.println("Enter name");
			String name=sc.next();
			
			ps.setString(1, name);
			ps.setInt(2, id);
			int Result=ps.executeUpdate();
			if(Result > 0) {
				System.out.println("update successfully....");
			}else {
				System.out.println("update failed....");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEmployee() {
		try(PreparedStatement ps=con.prepareStatement("delete from employee where id=?");){
			System.out.println("Which row do you want to delete");
			int id=sc.nextInt();
			ps.setInt(1, id);
			int Result=ps.executeUpdate();
			
			if(Result > 0) {
				System.out.println("deleted successfully..");
			}else {
				System.out.println("delete failed");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void sumEmployee() {
		try(PreparedStatement ps=con.prepareStatement("select sum(id) from employee");){
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void avgEmployee() {
		try(PreparedStatement ps=con.prepareStatement("select avg(id) from employee");){
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			
		}
	}
	
	public void maxEmployee() {
		try(PreparedStatement ps=con.prepareStatement("select max(id) from employee");){
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		JdbcCRUDpreparedStatement jcps = new JdbcCRUDpreparedStatement();
		//jcps.insertEmployee();
		//jcps.selectEmployee();
		//jcps.updateEmployee();
		//jcps.deleteEmployee();
		//jcps.sumEmployee();
		//jcps.avgEmployee();
		jcps.maxEmployee();

	}

}

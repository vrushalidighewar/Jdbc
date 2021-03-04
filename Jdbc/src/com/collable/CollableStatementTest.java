package com.collable;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.Employee;

public class CollableStatementTest {
	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	public void insertEmployee() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{Call insertEmployee(?,?)}");) {
			System.out.println("How many employee do you want to add");
			int noofemp = sc.nextInt();
			for (int i = 0; i < noofemp; i++) {
				Employee e = new Employee(); // import from com.app
				System.out.println("Enter name");
				e.setName(sc.next());
				System.out.println("Enter address");
				e.setAddress(sc.next());

				cs.setString(1, e.getName());
				cs.setString(2, e.getAddress());

				cs.execute();
				flag = Boolean.TRUE;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("insert successfully....");
		} else {
			System.out.println("insertion failed.....");
		}
	}
//___________________________________________________________________________________________________

	public void selectEmployee() {
		List<Employee> emp = new ArrayList<Employee>();
		try (ResultSet rs = JdbcUtility.executeQuery("{Call selectEmployee()}");) {
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setAddress(rs.getString(3));
				emp.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Employee e : emp) {
			System.out.println(e.getId() + "\t" + e.getName() + "\t" + e.getAddress());
		}

	}
//__________________________________________________________________________________________________	

	public void updateEmployee() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{Call updateEmployee(?,?)}");) {
			System.out.println("Which row do you want to update");
			int id = sc.nextInt();

			System.out.println("Enter name");
			String name = sc.next();

			cs.setInt(1, id);
			cs.setString(2, name);
			int result = cs.executeUpdate();

			if (result > 0) {
				System.out.println("update successfully...");
			} else {
				System.out.println("update failed.....");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//__________________________________________________________________________________________________

	public void deleteEmployee() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{Call deleteEmployee(?)}");) {
			System.out.println("Which row do you want to delete");
			int id = sc.nextInt();

			cs.setInt(1, id);
			int result = cs.executeUpdate();

			if (result > 0)
				System.out.println("deleted successfully....");
			else
				System.out.println("delete failed....");
		} catch (SQLException e) {

		}
	}
//__________________________________________________________________________________________________

	public void selectmax() {
		try (ResultSet rs = JdbcUtility.executeQuery("{Call selectmax()}");) {
            /*int id=sc.nextInt();
            cs.setInt(1, id);
			ResultSet rs = cs.executeQuery();*/
			if (rs.next()) {
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//__________________________________________________________________________________________________
	
	public void sumEmployee() {
		try(ResultSet rs=JdbcUtility.executeQuery("{Call sumEmployee()}");){
			
			if(rs.next()) {
				System.out.println(rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
//__________________________________________________________________________________________________
	public static void main(String[] args) {
		CollableStatementTest cst = new CollableStatementTest();
		// cst.insertEmployee();
		// cst.selectEmployee();
		// cst.updateEmployee();
		// cst.deleteEmployee();
		//cst.selectmax();
		cst.sumEmployee();
	}

}

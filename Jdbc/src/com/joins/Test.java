package com.joins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public void insert() {

		Person person = new Person();
		person.setName("abc");
		person.setMobile("34563456");

		Pancard pancard = new Pancard();
		pancard.setPanNo("GSF345A");

		person.setPancard(pancard);

		Connection con = JdbcUtility.getConnection();
		boolean flag = Boolean.FALSE;

		try (PreparedStatement ps = con.prepareCall("insert into Person(name,mobile) values(?,?)");) {
			ps.setString(1, person.getName());
			ps.setString(2, person.getMobile());
			int result = ps.executeUpdate();

			if (result > 0) {
				ResultSet rs = JdbcUtility.executeQuery("select max(pid) from person");
				if (rs.next())
					person.setPid(rs.getInt(1));

				try (PreparedStatement ps1 = con.prepareCall("insert into Pancard(id,pancardNo) values(?,?)");) {
					ps1.setInt(1, person.getPid());
					ps1.setString(2, person.getPancard().getPanNo());
					ps1.executeUpdate();

					flag = Boolean.TRUE;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (flag == Boolean.TRUE) {
				System.out.println("inserted successfully....");
			} else
				System.out.println("failed....");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
//__________________________________________________________________________________

	public void select() {
		List<Person> list = new ArrayList();
		Connection con = JdbcUtility.getConnection();
		try (PreparedStatement ps = con
				.prepareStatement("select * from person p inner join pancard pan on p.pid=pan.id");) {
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				Person person = new Person();
				person.setPid(rs.getInt(1));
				person.setName(rs.getString(2));
				person.setMobile(rs.getString(3));

				Pancard pancard = new Pancard();
				pancard.setPanId(rs.getInt(4));
				pancard.setPanNo(rs.getString(5));

				person.setPancard(pancard);
				list.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Person person : list) {
			System.out.println(person.getPid() + "\t" + person.getName() + "\t" + person.getMobile() + "\t"
					+ person.getPancard().getPanId() + "\t" + person.getPancard().getPanNo());
		}
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.insert();
		test.select();

	}

}

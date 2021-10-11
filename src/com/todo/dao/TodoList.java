package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.DriverManager;
import com.todo.service.Dbconnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" +keyword+ "%";
		try {
			String sql ="Select * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id =rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				pstmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public TodoList()  {
		
		try {
			this.conn =Dbconnect.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.list = new ArrayList<TodoItem>();
	} 


	public int addItem(TodoItem t) {
		String sql = "insert into list (title, category, memo,  due_date,current_date )" + " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count= 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(3,t.getDesc());
			pstmt.setString(2,t.getCategory());
			pstmt.setString(5,t.getCurrent_date());
			pstmt.setString(4,t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql ="Update list set title =?, memo =?, category=?, current_date =?, due_date=?" + " where id =?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql ="SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id =rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs =stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "리스트 목록\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		PreparedStatement pstmt;
		boolean count = false;
		try {
			String sql = "SELECT count(*) FROM list Where title =?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("count(*)")>=1)
					return true;
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}



	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader ( new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date,  due_date)"
					+ " values (?,?,?,?,?);";
		int records =0;
		while((line = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line, "##");
			String category = st.nextToken();
			String title = st.nextToken();
			String description = st.nextToken();
			String due_date = st.nextToken();
			String current_date = st.nextToken();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, description);
			pstmt.setString(3, category);
			pstmt.setString(4, current_date);
			pstmt.setString(5, due_date);
			int count = pstmt.executeUpdate();
			if(count >0) records++;
			pstmt.close();
		}
		System.out.println(records + " records read!!");
		br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql ="SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				
				list.add(category);
			}stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem> ();
		PreparedStatement pstmt;
		try {
			String sql ="Select * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id =rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem> ();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering ==0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id =rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}



}

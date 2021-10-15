package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.sql.DriverManager;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc, category, due_date, teamwork;
		int priority, is_comp;

		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� �߰�]\n" + "������ �Է��ϼ��� >");
		title = sc.next();
		/*
		 * if (list.isDuplicate(title)) { System.out.printf("������ �ߺ��ɼ� �����ϴ�."); return; }
		 */

		System.out.print("ī�װ��� �Է��ϼ��� >");
		category = sc.next();

		sc.nextLine(); // �տ� ���ڸ� �����ֱ� ���� �ѹ� �Է�
		System.out.print("������ �Է��ϼ��� >");
		desc = sc.nextLine().trim(); // �ؽ�Ʈ������ ����� ������ �ް�, Ȥ�ø� ������ ����
		System.out.print("�������ڸ� �Է��ϼ��� >");
		due_date = sc.next();

		System.out.print("����ũ���θ� �Է��ϼ���(team/solo) >");
		teamwork = sc.next();
		System.out.print("�߿䵵�� �Է��ϼ���(1~5) >");
		priority = sc.nextInt();
		System.out.print("�Ϸ�� �׸��Դϱ�?(0:�ƴϿ� 1:��) >");
		is_comp = sc.nextInt();
		TodoItem t = new TodoItem(title, desc, category, due_date, teamwork, priority, is_comp);
		if (list.addItem(t) > 0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		int index;
		do {
			Scanner sc = new Scanner(System.in);

			System.out.print("[�׸� ����]\n" + "������ �׸��� ��ȣ�� �Է��Ͻÿ�(0 �Է½� �ߴ�) > ");

			index = sc.nextInt();
			if (l.deleteItem(index) > 0)
				System.out.println("�����Ǿ����ϴ�.");
		} while (index != 0);

	} // ��ȣ�� �޾Ƽ� ���� �ϵ���

	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void updateItem(TodoList l) {

		String new_title, new_desc, new_category, new_due_date, new_teamwork;
		int new_priority, new_is_comp;
		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� ����]" + "\n ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int index = sc.nextInt();

		System.out.print("�� ������ �Է� �ϼ��� > ");
		new_title = sc.next().trim();
		System.out.print("�� ī�װ��� �Է� �ϼ��� > ");
		new_category = sc.next().trim();
		sc.nextLine();
		System.out.print("���ο� ������ �Է��ϼ��� > ");
		String new_description = sc.next().trim();
		System.out.print("���ο� �������ڸ� �Է��ϼ��� > ");
		String new_duedate = sc.next();
		System.out.print("�� ����ũ���θ� �Է��ϼ���(team/solo) >");
		new_teamwork = sc.next();
		System.out.print("�� �߿䵵�� �Է��ϼ���(1~5) >");
		new_priority = sc.nextInt();
		System.out.print("�Ϸ�� �׸��Դϱ�?(0:�ƴϿ� 1:��) >");
		new_is_comp = sc.nextInt();
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_duedate, new_teamwork, new_priority,
				new_is_comp);
		t.setId(index);
		if (l.updateItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");
	}

	// ��ȣ�� �޾Ƽ� �����ϵ���

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		} // index�� 0 ���� �����ϴϱ� +1 ���ذ��� . ���
	}

	/*
	 * public static void loadList(TodoList l, String filename) { // BufferedReader,
	 * FileReader, StringTokenizer ��� try { BufferedReader br = new
	 * BufferedReader(new FileReader(filename));
	 * 
	 * String oneline; while ((oneline = br.readLine()) != null) { StringTokenizer
	 * st = new StringTokenizer(oneline, "##"); // title + "##" + desc + "##" +
	 * current_date String category = st.nextToken(); String title = st.nextToken();
	 * String desc = st.nextToken(); String due_date = st.nextToken(); String
	 * current_date = st.nextToken();
	 * 
	 * TodoItem i = new TodoItem(category, title, desc, due_date, current_date);
	 * l.addItem(i); // �̷����ϸ� �ҷ��ü� �ִ°ǰ�?! } br.close();
	 * System.out.println("���� �ε� �Ϸ� !!! "); } catch (FileNotFoundException e) { //
	 * e.printStackTrace(); System.out.println("������ �����ϴ�!!"); } catch (IOException
	 * e) { e.printStackTrace(); } catch (NoSuchElementException e) {
	 * 
	 * } }
	 */

	/*
	 * public static void saveList(TodoList l, String filename) { // FileWriter����ؼ�
	 * ����Ʈ�� �������� �����ϴ°� try { Writer w = new FileWriter(filename); for (TodoItem item
	 * : l.getList()) { w.write(item.toSaveString()); } w.close();
	 * System.out.println("���� ����!!! "); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	public static void loadList(TodoList l, String filename) {
		// Json
		Gson gson = new Gson();
		String jsonstr2;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

			while ((jsonstr2 = br.readLine()) != null) {
				List<TodoItem> list1 = gson.fromJson(jsonstr2, new TypeToken<List<TodoItem>>() {}.getType());
				list1.toString();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("������ �����ϴ�!!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
		}
		
		System.out.println("���Ͽ��� �����͸� �����Խ��ϴ�! ");
	}

	public static void saveList(TodoList l, String filename) {
		// FileWriter����ؼ� ����Ʈ�� �������� �����ϴ°�
		List<TodoItem> list= l.getList();
		//System.out.println(list.toString());
		Gson gson = new Gson();
		try {
			FileWriter writer = new FileWriter(filename);
			
			for (TodoItem item : list) {
				String jsonstr = gson.toJson(item);
				writer.write(jsonstr);
			}
			writer.close();
			System.out.println("Json���� ����!!! ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listCateAll(TodoList l) {
		// TODO Auto-generated method stub
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		// TODO Auto-generated method stub
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		// TODO Auto-generated method stub
		System.out.printf("[��ü ���, ��%d��]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

	// �Ϸ�üũ
	public static void completeItem(TodoList l, int id) {
		// �ش���̵��� is_comp�� 1�� �ٲ��.
		if (l.completeItem(id) > 0)
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.");
	}
	public static void uncompleteItem(TodoList l, int id) {
		// �ش���̵��� is_comp�� 1�� �ٲ��.
		if (l.uncompleteItem(id) > 0)
			System.out.println("�Ϸ� üũ �����Ͽ����ϴ�.");
	}

	public static void listComp(TodoList l, int is_comp) { // is_comp���� 1�� ���´�.
		// TODO Auto-generated method stub
		int count = 0;
		for (TodoItem item : l.getListcomp(is_comp)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

}

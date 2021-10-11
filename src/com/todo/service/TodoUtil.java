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
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.sql.DriverManager;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc, category,  due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 추가]\n" + "제목을 입력하세요 >");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될수 없습니다.");
			return;
		}

		System.out.print("카테고리를 입력하세요 >");
		category = sc.next();

		sc.nextLine(); // 앞에 문자를 지워주기 위해 한번 입력
		System.out.print("내용을 입력하세요 >");
		desc = sc.nextLine().trim(); // 넥스트라인을 사용해 문장을 받고, 혹시모를 여백을 제거
		System.out.print("마감일자를 입력하세요 >");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		if (list.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n" + "삭제할 항목의 번호를 입력하시오 > ");
		
		int index=sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.");
	} // 번호를 받아서 삭제 하도록

	public static void findList (TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
				System.out.println(item.toString());
				count ++;
			}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void updateItem(TodoList l) {

		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 수정]" + "\n 수정할 항목의 번호를 입력하세요 > ");
		int index = sc.nextInt();

		System.out.print("새 제목을 입력 하세요 > ");
		new_title = sc.next().trim();
		System.out.print("새 카테고리를 입력 하세요 > ");
		new_category = sc.next().trim();
		sc.nextLine();
		System.out.print("새로운 내용을 입력하세요 > ");
		String new_description = sc.next().trim();
		System.out.print("새로운 마감일자를 입력하세요 > ");
		String new_duedate = sc.next();

		TodoItem t = new TodoItem( new_title,new_description, new_category,   new_duedate);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");
	}
	

	// 번호를 받아서 수정하도록

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		} // index가 0 부터 시작하니까 +1 해준값에 . 찍기
	}

	public static void loadList(TodoList l, String filename) {
		// BufferedReader, FileReader, StringTokenizer 사용
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

			String oneline;
			while ((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				// title + "##" + desc + "##" + current_date
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();

				TodoItem i = new TodoItem(category, title, desc, due_date, current_date);
				l.addItem(i); // 이렇게하면 불러올수 있는건가?!
			}
			br.close();
			System.out.println("정보 로딩 완료 !!! ");
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println("파일이 없습니다!!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {

		}
	}

	public static void saveList(TodoList l, String filename) {
		// FileWriter사용해서 리스트에 아이템을 저장하는거
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("정보 저장!!! ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listCateAll(TodoList l) {
		// TODO Auto-generated method stub
		int count =0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		// TODO Auto-generated method stub
		int count=0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n",count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		// TODO Auto-generated method stub
		System.out.printf("[전체 목록, 총%d개]\n", l.getCount());
		for (TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}



}

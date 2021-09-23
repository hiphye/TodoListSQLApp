package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]" + "\n 제목을 입력하세요 >");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될수 없습니다.");
			return;
		}
		sc.nextLine(); // 앞에 문자를 지워주기 위해 한번 입력 
		System.out.print("내용을 입력하세요 >");
		desc = sc.nextLine().trim(); // 넥스트라인을 사용해 문장을 받고, 혹시모를 여백을 제거
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 제거]" + "\n 제목을 입력하세요 >");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]" + "\n 수정할 제목을 입력하세요 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다!");
			return;
		}

		System.out.print("새 제목을 입력 하세요 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine(); // 한줄 띄워주는거
		System.out.print("새로운 설명을 입력하세요 > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[ " + item.getTitle() +" ] : "+ item.getDesc() +" - "+ item.getCurrent_date());
		}
	}

	public static void loadList(TodoList l, String filename) {
		// BufferedReader, FileReader, StringTokenizer 사용
				try {
					BufferedReader br = new BufferedReader(new FileReader(filename));
				
					String oneline;
					while ((oneline = br.readLine()) != null) {
						StringTokenizer st = new StringTokenizer(oneline, "##");
						//title + "##" + desc + "##" + current_date
						String title = st.nextToken();
						String desc = st.nextToken();
						String current_date = st.nextToken();  
						
						TodoItem i = new TodoItem(title, desc, current_date );
						l.addItem(i); //이렇게하면 불러올수 있는건가?! 
					}
					br.close();
					System.out.println("정보 로딩 완료 !!! ");
				} catch (FileNotFoundException e) {
					//e.printStackTrace();
					System.out.println("파일이 없습니다!!");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchElementException e) {
					
				}
			}
		
	public static void saveList(TodoList l, String filename) {
		// FileWriter사용해서 리스트에 아이템을 저장하는거 
				try {
				Writer w = new FileWriter("todoList.txt");
				for (TodoItem item : l.getList())  {
					w.write(item.toSaveString());
				}
				w.close();
				System.out.println("정보 저장!!! ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}


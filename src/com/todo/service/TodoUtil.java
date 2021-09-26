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
		
		String category,title, desc, due_date ;
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 추가]" + "\n카테고리를 입력하세요 >");
		category = sc.next();
		System.out.print("제목을 입력하세요 >");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될수 없습니다.");
			return;
		}
		sc.nextLine(); // 앞에 문자를 지워주기 위해 한번 입력 
		System.out.print("내용을 입력하세요 >");
		desc = sc.nextLine().trim(); // 넥스트라인을 사용해 문장을 받고, 혹시모를 여백을 제거
		System.out.print("마감일자를 입력하세요 >");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t); //add()
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 제거]" + "\n 삭제할 항목의 번호를 입력하시오 >");
		int num = sc.nextInt();
		
		
		
		for (TodoItem item : l.getList()) {
			if (num ==(l.indexOf(item)+1)) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				System.out.print("위 항목을 삭제하시겠습니까? >");
				String answer = sc.next();
				if(answer.equals("y")) {
				l.deleteItem(num-1); //remove
				System.out.println("삭제되었습니다.");
				} 
				break;
			}
		}
	} //번호를 받아서 삭제 하도록

	public static void findItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("키워드를 입력하세요 >");
		String key = sc.next();
		int i =0;
		for (TodoItem item : l.getList()) {
			if (key.contains(item.getTitle())) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				i++;
				} 
			if (key.contains(item.getDesc())) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				i++;
				} 
			}
		System.out.println("총" +i + "개의 항목을 찾았습니다.");
		}
	

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]" + "\n 수정할 항목의 번호를 입력하세요 > ");
		int num = sc.nextInt();
		for (TodoItem item : l.getList()) {
			if (num ==(l.indexOf(item)+1)) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				System.out.print("새 카테고리를 입력 하세요 > ");
				String new_category = sc.next().trim();
				System.out.print("새 제목을 입력 하세요 > ");
				String new_title = sc.next().trim();
				if (l.isDuplicate(new_title)) {
					System.out.println("제목이 중복됩니다!");
					return;
				}
				System.out.print("새로운 내용을 입력하세요 > ");
				String new_description = sc.next().trim();
				System.out.print("새로운 마감일자를 입력하세요 > ");
				String new_duedate = sc.next().trim();
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category , new_title, new_description, new_duedate);
				l.addItem(t);
				System.out.println("수정되었습니다.");
				break;
			}
		}
		}
		// 번호를 받아서 수정하도록 

	public static void listAll(TodoList l) {
		int i = l.countAll();
		System.out.println("[전체 목록, 총 " + i + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getdue_date()+" - "+item.getCurrent_date());
		} //index가 0 부터 시작하니까 +1 해준값에 . 찍기
	}

	public static void loadList(TodoList l, String filename) {
		// BufferedReader, FileReader, StringTokenizer 사용
				try {
					BufferedReader br = new BufferedReader(new FileReader(filename));
				
					String oneline;
					while ((oneline = br.readLine()) != null) {
						StringTokenizer st = new StringTokenizer(oneline, "##");
						//title + "##" + desc + "##" + current_date
						String category = st.nextToken();
						String title = st.nextToken();
						String desc = st.nextToken();
						String due_date =st.nextToken();
						String current_date = st.nextToken();  
						
						TodoItem i = new TodoItem(category, title, desc, due_date, current_date );
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
				Writer w = new FileWriter(filename);
				for (TodoItem item : l.getList())  {
					w.write(item.toSaveString());
				}
				w.close();
				System.out.println("정보 저장!!! ");
			}  catch (IOException e) {
				e.printStackTrace();
			}
	}

}


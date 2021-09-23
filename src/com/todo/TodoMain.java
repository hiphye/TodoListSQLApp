package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todoList.txt");
		Menu.displaymenu(); //이걸 먼저해줌
		do {
			Menu.prompt(); // 이 메소드에 커맨드입력란 표시
			isList = false; // 다시 세팅한다는건 루프중 true가 되는 과정이있다.
			String choice = sc.next(); //scanner로 문자열 입력 받는다. 이 값에 따른 기능을 수행
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("제목순으로 정렬하였습니다.");
				isList = true; //여기서 플래그 변수로 사용해줬다.
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("제목역순으로 정렬하였습니다.");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("날짜순으로 정렬하였습니다.");
				isList = true;
				break;

			case "exit":
				quit = true;
				break;
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			if(isList) l.listAll(); // true를 세팅한 이유는 트루일때 리스트올을 하게된다. 정렬된후 전체 리스트를 한번 보여라! 각각에 수행후 넣는것 대신 한번에 해결.
		} while (!quit);
		TodoUtil.saveList(l,"todoList.txt");
	}
}

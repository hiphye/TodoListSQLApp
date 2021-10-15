package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {

		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todoList.txt");

		//TodoUtil.loadList(l, "todoList.txt");
		boolean isList = false;
		boolean quit = false;

		Menu.displaymenu(); // �̰� ��������
		do {
			Menu.prompt(); // �� �޼ҵ忡 Ŀ�ǵ��Է¶� ǥ��
			isList = false; // �ٽ� �����Ѵٴ°� ������ true�� �Ǵ� �������ִ�.
			String choice = sc.next(); // scanner�� ���ڿ� �Է� �޴´�. �� ���� ���� ����� ����
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

			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;

			case "ls_name_asc":
				//l.sortByName();
				TodoUtil.listAll(l, "title", 1);
				System.out.println("��������� �����Ͽ����ϴ�.");
				isList = true; // ���⼭ �÷��� ������ ��������.
				break;

			case "ls_name_desc":
				System.out.println("���񿪼����� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 0);
				break;

			case "ls_date":
				System.out.println("��¥������ �����Ͽ����ϴ�.");
				TodoUtil.listAll(l,"due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("��¥�������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l,"due_date", 0);
				break;

			case "ls_name":
				System.out.println("��������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "exit":
				quit = true;
				break;
			case "help":
				Menu.displaymenu();
				break;

			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
			//comp�۾�
			case "comp":
				int id = sc.nextInt();
				TodoUtil.completeItem(l,id);
				break;
			case "uncomp":
				id = sc.nextInt();
				TodoUtil.uncompleteItem(l,id);
				break;
			case "ls_comp":
				TodoUtil.listComp(l,1);
				break;
			default:
				System.out.println("��Ȯ�� ��ɾ �Է��ϼ���. (���� - help)");
				break;
			}

			if (isList)
				l.listAll(); // true�� ������ ������ Ʈ���϶� ����Ʈ���� �ϰԵȴ�. ���ĵ��� ��ü ����Ʈ�� �ѹ� ������! ������ ������ �ִ°� ��� �ѹ��� �ذ�.
		} while (!quit);
		TodoUtil.saveList(l, "todoList.txt");

	}
}

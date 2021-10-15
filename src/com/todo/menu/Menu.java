package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
    	System.out.println("\n");
        System.out.println("<ToDoList 관리 명령어 사용법>");
        System.out.println("[add] - 항목 추가");
        System.out.println("[del] - 항목 삭제");
        System.out.println("[edit] - 항목 수정");
        System.out.println("[ls] - 전체 목록");
        System.out.println("[ls_name_asc] - 제목순 정렬");
        System.out.println("[ls_name_desc] - 제목역순 정렬");
        System.out.println("[ls_date] - 날짜순 정렬");
        System.out.println("[ls_cate] - 등록된 카테고리 정렬");
        System.out.println("[find] - 키워드로 항목검색");
        System.out.println("[find_cate] - 입력한 카테고리를 정렬");
        System.out.println("[comp] - 항목 완료, comp 띄우고 완료할 id입력");
        System.out.println("[uncomp] - 항목 완료해재, comp 띄우고 완료할 id입력");
        System.out.println("[ls_comp] - 완료된 항목 정렬");
        System.out.println("[exit] - 종료");
        
    }

	public static void prompt() {
		System.out.print("\n커맨드를 입력하세요 >");
		
	}
}//인터페이스 수정

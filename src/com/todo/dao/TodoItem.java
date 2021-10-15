package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    private String teamwork;
    private int priority;
    private int is_comp;

    @Override
	public String toString() {
    	String comp = "";
    	if(is_comp==1)
    		comp="[V]";
		return id  + " [" + category + "]" + title + comp + " - " +   desc+ " - "+ due_date + " - "+ current_date +" - " + teamwork + " - "+ priority;
	} //is_comp가 입력되면 1을 반환하고, 1일때 체크표시가 출력되는 구조


	public TodoItem(String title, String desc, String category, String due_date, String teamwork, int priority ,int is_comp){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss"); //날짜 포맷을 작성해주는것.
        this.current_date= f.format( new Date() ); //포맷메소드에 맞춰주는것
        this.due_date = due_date;
        this.teamwork = teamwork;
        this.priority = priority;
        this.is_comp=is_comp;
    }
    
 
	public TodoItem(String category, String title, String desc, String due_date, String current_date) {
		this.category = category;
		this.title=title;
        this.desc=desc;
        this.due_date=due_date;
        this.current_date= current_date ; 
	}


	public String toSaveString() {
    	return category+ "##" +title + "##" + desc + "##" +due_date+ "##" +current_date+ "##" +teamwork + "##" + priority +"\n";
    }
    
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }


	public void setId(int id) {
		this.id = id;
		
	}


	public int getId() {
		return id;
	}
	
	public String getTeamwork() {
        return teamwork;
    }

    public void setTeamwork(String teamwork) {
        this.teamwork = teamwork;
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


	public int getIs_comp() {
		return is_comp;
	}


	public void setIs_comp(int is_comp) {
		this.is_comp = is_comp;
	}
    
    }


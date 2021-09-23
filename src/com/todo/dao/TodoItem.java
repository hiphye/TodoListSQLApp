package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;


    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss"); //날짜 포맷을 작성해주는것.
        this.current_date= f.format( new Date() ); //포맷메소드에 맞춰주는것
    }
    
 
	public TodoItem(String title, String desc, String current_date) {
		this.title=title;
        this.desc=desc;
        
        this.current_date= current_date ; 
	}


	public String toSaveString() {
    	return title + "##" + desc + "##" + current_date + "\n";
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
}

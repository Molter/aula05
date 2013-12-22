package br.com.gabrielmolter.homework03v2;

import java.util.ArrayList;

public class ShopItem {
	
	public static ArrayList<ShopItem> listItens;
	
	public static ArrayList<ShopItem> getList(){
		if(listItens == null){
			listItens = new ArrayList<ShopItem>();
		}
		
		return listItens;
	}
	
	long id;
	String name, description;
	String filePath = "";
	
	boolean isCchecked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isCchecked() {
		return isCchecked;
	}

	public void setCchecked(boolean isCchecked) {
		this.isCchecked = isCchecked;
	}
	
}

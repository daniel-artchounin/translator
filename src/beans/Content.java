package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Content implements Serializable {

	private static final long serialVersionUID = 4266935197208508665L;
	private int id;
	private String name;
	private ArrayList<ContentPart> parts;
	
	public Content() {
	}
	
	public Content(int id, String name, ArrayList<ContentPart> parts) {
		this.id = id;
		this.name = name;
		this.parts = parts;
	}
	
	public Content(String name, ArrayList<ContentPart> parts) {
		this.name = name;
		this.parts = parts;
	}

	public Content(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Content(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ContentPart> getParts() {
		return parts;
	}

	public void setParts(ArrayList<ContentPart> parts) {
		this.parts = parts;
	}
	
}

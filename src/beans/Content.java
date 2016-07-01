package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Content implements Serializable {

	private static final long serialVersionUID = 4266935197208508665L;
	private int id;
	private String name;
	private ArrayList<Part> parts;
	
	public Content() {
	}
	
	public Content(int id, String name, ArrayList<Part> parts) {
		this.id = id;
		this.name = name;
		this.parts = parts;
	}
	
	public Content(String name, ArrayList<Part> parts) {
		this.name = name;
		this.parts = parts;
	}

	public Content(int id, String name) {
		this.id = id;
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

	public ArrayList<Part> getParts() {
		return parts;
	}

	public void setParts(ArrayList<Part> parts) {
		this.parts = parts;
	}
	
}

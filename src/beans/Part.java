package beans;

import java.io.Serializable;
import java.sql.Time;

public class Part implements Serializable {
	
	private static final long serialVersionUID = -8303386072832554187L;
	private int id;
	private int content;
	private Time beginning;
	private Time end;
	
	public Part() {
	}

	public Part(int id, int content, Time beginning, Time end) {
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public Time getBeginning() {
		return beginning;
	}

	public void setBeginning(Time beginning) {
		this.beginning = beginning;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}
	
}

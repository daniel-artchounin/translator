package beans;

import java.io.Serializable;
import java.sql.Time;

public class ContentPart implements Serializable {
	
	private static final long serialVersionUID = -8303386072832554187L;
	private int id;
	private String content;
	private Time beginning;
	private Time end;
	
	public ContentPart() {
	}

	public ContentPart(int id, String content, Time beginning, Time end) {
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
	}
	
	public ContentPart(String content, Time beginning, Time end) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
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

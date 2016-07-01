package beans;

import java.io.Serializable;
import java.sql.Time;

public class ContentPart implements Serializable {
	
	private static final long serialVersionUID = -8303386072832554187L;
	private int id;
	private int content;
	private Time beginning;
	private Time end;
	private String partContent;
	
	public ContentPart() {
	}

	public ContentPart(int id, int content, Time beginning, Time end, String partContent) {
		super();
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
		this.partContent = partContent;
	}
	
	public ContentPart(Time beginning, Time end, String partContent) {
		super();
		this.beginning = beginning;
		this.end = end;
		this.partContent = partContent;
	}
	
	public ContentPart(int id, int content, Time beginning, Time end) {
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
	}
	
	public ContentPart(int content, Time beginning, Time end) {
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

	public String getPartContent() {
		return partContent;
	}

	public void setPartContent(String partContent) {
		this.partContent = partContent;
	}
	
}

package beans;

import java.io.Serializable;
import java.util.regex.Pattern;


/* This bean represents a content's part */
public class ContentPart implements Serializable {
	
	private static final long serialVersionUID = -8303386072832554187L;
	private int id;
	private int content;
	private String beginning;
	private String end;
	private String partContent;
	
	public ContentPart() {
	}

	public ContentPart(int id, int content, String beginning, String end, String partContent) {
		super();
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
		this.partContent = partContent;
	}
	
	public ContentPart(String beginning, String end, String partContent) {
		super();
		this.beginning = beginning;
		this.end = end;
		this.partContent = partContent;
	}
	
	public ContentPart(int id, int content, String beginning, String end) {
		this.id = id;
		this.content = content;
		this.beginning = beginning;
		this.end = end;
	}
		
	public ContentPart(int content, String beginning, String end) {
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

	public String getBeginning() {
		return beginning;
	}

	public void setBeginning(String beginning) throws BeanException {
		if(!Pattern.matches("^[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}$", beginning)){
			throw new BeanException("Données non valides");
		}
		this.beginning = beginning;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) throws BeanException {
		if(!Pattern.matches("^[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}$", beginning)){
			throw new BeanException("Données non valides");
		}
		this.end = end;
	}

	public String getPartContent() {
		return partContent;
	}

	public void setPartContent(String partContent) {
		this.partContent = partContent;
	}
	
}

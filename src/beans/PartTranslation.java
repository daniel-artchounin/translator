package beans;

import java.io.Serializable;

public class PartTranslation implements Serializable {

	private static final long serialVersionUID = 8390740883634542524L;
	private int part;
	private int language;
	private String content;
	
	public PartTranslation() {
		super();
	}

	public PartTranslation(int part, int language, String content) {
		super();
		this.part = part;
		this.language = language;
		this.content = content;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

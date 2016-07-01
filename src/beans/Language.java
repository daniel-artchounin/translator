package beans;

import java.io.Serializable;

public class Language implements Serializable {
	
	private static final long serialVersionUID = 2358908821469816808L;
	private int id;
	private String language;
	
	public Language() {
		super();
	}

	public Language(int id, String language) {
		this.id = id;
		this.language = language;
	}
	
	public Language(String language) {
		this.language = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
		
}

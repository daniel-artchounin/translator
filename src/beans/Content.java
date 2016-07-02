package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Content implements Serializable {

	private static final long serialVersionUID = 4266935197208508665L;
	private int id;
	private String name;
	private ArrayList<ContentPart> parts;
	private boolean hasAtLeastTwoExportableTranslations;
	private ArrayList<Language> languages;

	public Content() {
		this.parts = new ArrayList<ContentPart>();
	}
	
	public Content(int id, String name, ArrayList<ContentPart> parts, boolean hasAtLeastTwoExportableTranslations,
			ArrayList<Language> languages) {
		super();
		this.id = id;
		this.name = name;
		this.parts = parts;
		this.hasAtLeastTwoExportableTranslations = hasAtLeastTwoExportableTranslations;
		this.languages = languages;
	}

	public Content(int id, String name, ArrayList<ContentPart> parts, boolean hasAtLeastTwoExportableTranslations) {
		super();
		this.id = id;
		this.name = name;
		this.parts = parts;
		this.hasAtLeastTwoExportableTranslations = hasAtLeastTwoExportableTranslations;
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
		this.languages = new ArrayList<Language>();
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

	public boolean isHasAtLeastTwoExportableTranslations() {
		return hasAtLeastTwoExportableTranslations;
	}

	public void setHasAtLeastTwoExportableTranslations(boolean hasAtLeastTwoExportableTranslations) {
		this.hasAtLeastTwoExportableTranslations = hasAtLeastTwoExportableTranslations;
	}

	public ArrayList<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(ArrayList<Language> languages) {
		this.languages = languages;
	}
	
}

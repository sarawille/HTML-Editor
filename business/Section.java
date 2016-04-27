package business;

/**
 * 
 * @author MAX-Student
 * The Person class store name, link, image, typeID and personID for entries in the databse table Person.
 *
 */

public class Section {

	
	int sectionID = 0;
	String name = "";
	String link = "";
	String image = "";
	int TypeID = 0;
	
	public Section() {
	}
	
	public Section(int typeID) {
		setTypeID(typeID);
	}
	
	public Section(int sectionID, String name, String link, String image, int typeID) {
		setSectionID(sectionID);
		setName(name);
		setLink(link);
		setImage(image);
		setTypeID(typeID);
	}
	
	public int getSectionID() {
		return sectionID;
	}
	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getTypeID() {
		return TypeID;
	}
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}

}

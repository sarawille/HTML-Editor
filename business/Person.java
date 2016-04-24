package business;

/**
 * 
 * @author MAX-Student
 * The Person class store name, link, image, typeID and personID for entries in the databse table Person.
 *
 */

public class Person {

	
	int personID = 0;
	String name = "";
	String link = "";
	String image = "";
	int TypeID = 0;
	
	public Person() {
	}
	
	public Person(int typeID) {
		setTypeID(typeID);
	}
	
	public Person(int personID, String name, String link, String image, int typeID) {
		setPersonID(personID);
		setName(name);
		setLink(link);
		setImage(image);
		setTypeID(typeID);
	}
	
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
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

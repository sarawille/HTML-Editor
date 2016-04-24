package business;

public class ListItem {

	
	int row;
	int personID;
	String text;
	
	public ListItem() {}
	
	public ListItem(int row, int personID, String text) {
		this.row = row;
		this.personID = personID;
		this.text = text;
	}

	public int getRow() {
		return row;
	}

	public int getPersonID() {
		return personID;
	}

	public String getText() {
		return text;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}

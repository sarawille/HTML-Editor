package business;

public class ListItem {

	
	int rowNum;
	int personID;
	String text;
	
	public ListItem() {}
	
	public ListItem(int row, int personID, String text) {
		this.rowNum = row;
		this.personID = personID;
		this.text = text;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getPersonID() {
		return personID;
	}

	public String getText() {
		return text;
	}

	public void setRow(int row) {
		this.rowNum = row;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}

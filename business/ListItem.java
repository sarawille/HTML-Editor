package business;

public class ListItem {

	
	int rowNum;
	int sectionID;
	String text;
	
	public ListItem() {}
	
	public ListItem(int row, int personID, String text) {
		this.rowNum = row;
		this.sectionID = personID;
		this.text = text;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getSectionID() {
		return sectionID;
	}

	public String getText() {
		return text;
	}

	public void setRow(int row) {
		this.rowNum = row;
	}

	public void setSectionID(int personID) {
		this.sectionID = personID;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}

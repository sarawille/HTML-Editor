package ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.xml.stream.XMLStreamException;

import business.ListItem;
import business.Section;
import db.SectionDB;
import db.DBException;

@SuppressWarnings("serial")
public class ListTableModel extends AbstractTableModel {
    private List<ListItem> bullets;
    private Section activePerson;
    private static final String[] COLUMN_NAMES = {
        "List Item"
    };

    
    public ListTableModel(Section activePerson) {
    	this.activePerson = activePerson;
        try {
            bullets = SectionDB.getListItems(activePerson.getSectionID());
        } catch (DBException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    
    ListItem getItem(int rowIndex) {
        return bullets.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            bullets = SectionDB.getListItems(activePerson.getSectionID());
            fireTableDataChanged();
        } catch (DBException | XMLStreamException e) {
            e.printStackTrace();
        }        
    }

    @Override
    public int getRowCount() {
        return bullets.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bullets.get(rowIndex).getText();
            default:
                return null;
        }
    }    
}
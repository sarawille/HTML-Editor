package ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.xml.stream.XMLStreamException;

import business.ListItem;
import business.Person;
import db.PersonDB;
import db.DBException;

@SuppressWarnings("serial")
public class ListTableModel extends AbstractTableModel {
    private List<ListItem> bullets;
    private Person activePerson;
    private static final String[] COLUMN_NAMES = {
        "List Item"
    };

    
    public ListTableModel() {
        try {
            bullets = PersonDB.getListItems();
        } catch (DBException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    
    ListItem getText(int rowIndex) {
        return bullets.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            bullets = PersonDB.getListItems();
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
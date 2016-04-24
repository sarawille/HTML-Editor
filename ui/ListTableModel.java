package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.xml.stream.XMLStreamException;

import business.Person;
import db.PersonDB;
import db.DBException;

@SuppressWarnings("serial")
public class ListTableModel extends AbstractTableModel {
    private ArrayList<String> bullets;
    private Person activePerson;
    private static final String[] COLUMN_NAMES = {
        "List Item"
    };

    
    public ListTableModel(Person person) {
    	activePerson = person;
        try {
            bullets = PersonDB.getListItems(activePerson.getPersonID());
        } catch (DBException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    
    String getText(int rowIndex) {
        return bullets.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            bullets = PersonDB.getListItems(activePerson.getPersonID());
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
    public String getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return getText(rowIndex);
            default:
                return null;
        }
    }   
}
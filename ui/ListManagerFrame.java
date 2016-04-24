package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import ui.EditListItemForm;
import business.Person;
import db.DBException;
import db.PersonDB;
import ui.ListTableModel;

@SuppressWarnings("serial")
public class ListManagerFrame extends JDialog {
	private JTable listItemTable;
	private ListTableModel listTableModel;
    private JTextField textItems;
    private JButton confirmButton;
    private JButton cancelButton;

    private Person activePerson = new Person();
    
    public ListManagerFrame(java.awt.Frame parent, String title,
            boolean modal, Person person) {
    	super(parent, title, modal);      
        this.activePerson = person;
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        setSize(768, 384);
        setPosition();
        
        add(buildButtonPanel(), BorderLayout.NORTH);
        listItemTable = buildProductTable();
        add(new JScrollPane(listItemTable), BorderLayout.CENTER);
        setVisible(true);
    }

	private void setPosition() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
	}
    
    private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }

    private void cancelButtonActionPerformed() {                                             
        dispose();
    }                                            
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add product");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected product");
        editButton.addActionListener((ActionEvent) -> {
            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected product");
        deleteButton.addActionListener((ActionEvent) -> {
//            doDeleteButton();
        });
        panel.add(deleteButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setToolTipText("Exit table view");
        exitButton.addActionListener((ActionEvent) -> {
            dispose();
        });
        panel.add(exitButton);
        
        return panel;
    }
    
    private void doEditButton() {
    	int selectedRow = listItemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            String text = listTableModel.getText(selectedRow);
            EditListItemForm editListItemForm = 
                    new EditListItemForm(this, "Edit List", true, text);
            editListItemForm.setLocationRelativeTo(this);
            editListItemForm.setVisible(true);
        }
	}

	private void doAddButton() {
		AddListItemForm addListItemForm = 
	                new AddListItemForm(this, "Add List Item", true, activePerson);
        addListItemForm.setLocationRelativeTo(this);
        addListItemForm.setVisible(true);	
	}

	void fireDatabaseUpdatedEvent() {
        listTableModel.databaseUpdated();
    }    
    
    private JTable buildProductTable() {
        listTableModel = new ListTableModel(activePerson);
        JTable table = new JTable(listTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }    
}
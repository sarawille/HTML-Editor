package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import business.Person;
import db.DBException;
import db.PersonDB;
import ui.ListTableModel;

@SuppressWarnings("serial")
public class ListManagerFrame extends JDialog {
	private JTable listItemTable;
	private ListTableModel listTableModel;
    private JTextField codeField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JButton confirmButton;
    private JButton cancelButton;

    private Person activePerson = new Person();
    
    public ListManagerFrame(java.awt.Frame parent, String title,
            boolean modal, Person person) {
    	super(parent, title, modal);      
        this.activePerson = person;
        initComponents();
        
        add(buildButtonPanel(), BorderLayout.NORTH);
        listItemTable = buildProductTable();
        add(new JScrollPane(listItemTable), BorderLayout.CENTER);
        setVisible(true);
    }

    private void initComponents() {
        codeField = new JTextField();
        descriptionField = new JTextField();
        priceField = new JTextField();
        cancelButton = new JButton();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        Dimension shortField = new Dimension(100, 20);
        Dimension longField = new Dimension(300, 20);
        codeField.setPreferredSize(shortField);
        codeField.setMinimumSize(shortField);        
        priceField.setPreferredSize(shortField);
        priceField.setMinimumSize(shortField);        
        descriptionField.setPreferredSize(longField);
        descriptionField.setMinimumSize(longField);
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener((ActionEvent) -> {
            cancelButtonActionPerformed();
        });

        confirmButton.setText("Add");
        confirmButton.addActionListener((ActionEvent) -> {
//            confirmButtonActionPerformed();
        });

        // JLabel and JTextField panel
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridBagLayout());
        productPanel.add(new JLabel("Code:"), 
                getConstraints(0, 0, GridBagConstraints.LINE_END));
        productPanel.add(codeField,
                getConstraints(1, 0, GridBagConstraints.LINE_START));
        productPanel.add(new JLabel("Description:"), 
                getConstraints(0, 1, GridBagConstraints.LINE_END));
        productPanel.add(descriptionField, 
                getConstraints(1, 1, GridBagConstraints.LINE_START));
        productPanel.add(new JLabel("Price:"), 
                getConstraints(0, 2, GridBagConstraints.LINE_END));
        productPanel.add(priceField, 
                getConstraints(1, 2, GridBagConstraints.LINE_START));

        // JButton panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // add panels to main panel
        setLayout(new BorderLayout());
        add(productPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();        
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
//            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected product");
        editButton.addActionListener((ActionEvent) -> {
//            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected product");
        deleteButton.addActionListener((ActionEvent) -> {
//            doDeleteButton();
        });
        panel.add(deleteButton);
        
        return panel;
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
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
        initComponents();
        setSize(768, 384);
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
        
        add(buildButtonPanel(), BorderLayout.NORTH);
        listItemTable = buildProductTable();
        add(new JScrollPane(listItemTable), BorderLayout.CENTER);
        setVisible(true);
    }

    private void initComponents() {
        cancelButton = new JButton();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        Dimension longField = new Dimension(300, 20);    
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener((ActionEvent) -> {
            cancelButtonActionPerformed();
        });

        confirmButton.setText("Add");
        confirmButton.addActionListener((ActionEvent) -> {
//            confirmButtonActionPerformed();
        });

        // JButton panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // add panels to main panel
        setLayout(new BorderLayout());
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
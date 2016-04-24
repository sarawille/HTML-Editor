package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import business.ListItem;
import business.Person;
import db.DBException;
import db.PersonDB;
import ui.ListTableModel;

@SuppressWarnings("serial")
public class EditListItemForm extends JDialog {
	private JTextArea editField;
    private JButton confirmButton, cancelButton;

    ListItem bullet;
    
    public EditListItemForm(ListManagerFrame parent, String title,
            boolean modal, ListItem bullet) {
    	super(parent, title, modal);      
        this.bullet = bullet;
        initComponents();
    }


	private void initComponents() {
    	Dimension buttonDim = new Dimension(80, 20);

        confirmButton = new JButton();
        confirmButton.setText("Save");
        confirmButton.setPreferredSize(buttonDim);
        confirmButton.setMinimumSize(buttonDim);
        confirmButton.addActionListener((ActionEvent e) -> {
        	confirmButtonActionPerformed();
        });
        
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setPreferredSize(buttonDim);
        cancelButton.setMinimumSize(buttonDim);
        cancelButton.addActionListener((ActionEvent e) -> {
        	dispose();
        });

		
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        
        Dimension longField = new Dimension(400, 200);
     
        editField = new JTextArea();
        editField.setPreferredSize(longField);
        editField.setMinimumSize(longField);
        editField.setText(bullet.getText());
        editField.setLineWrap(true);	
        editField.setWrapStyleWord(true);	

        // JLabel and JTextArea panel
        JPanel personPanel = new JPanel();
        personPanel.setLayout(new GridBagLayout());
//        String name = activePerson.getName();
//        personPanel.add(new JLabel(name + ":"), 
//                getConstraints(0, 0, GridBagConstraints.LINE_START));
        personPanel.add(editField, getConstraints(0, 1, GridBagConstraints.LINE_START));
        

        // JButton panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // add panels to main panel
        setLayout(new BorderLayout());
        add(personPanel, BorderLayout.CENTER);
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
                                           

    private void confirmButtonActionPerformed() {
        if (validateData()) {
          try {
        	  String textToUpdate = editField.getText();
        	  int rowNum = bullet.getRow();
	          PersonDB.updateList(rowNum, textToUpdate);
	          dispose();
	          fireDatabaseUpdatedEvent();
	      } catch (DBException e) {
	          e.printStackTrace();
	      } 
        }
    }
    
    private boolean validateData() {
        if (editField.getText().equals("")) {
        	JOptionPane.showMessageDialog(this, "Please add text.",
	            "Missing Fields", JOptionPane.INFORMATION_MESSAGE);
	    return false;
		}
		else
		{
			return true;
		}
	}
    
    void fireDatabaseUpdatedEvent() {
    	ListManagerFrame mainWindow = (ListManagerFrame) getOwner();
        mainWindow.fireDatabaseUpdatedEvent();
    }    

}
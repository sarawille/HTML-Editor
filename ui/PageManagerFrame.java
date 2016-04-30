package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import ui.ParagraphForm;
import db.DBException;
import db.SectionDB;
import business.HTMLCreator;
import business.Section;


public class PageManagerFrame extends JFrame {
	
	JButton createButton, paraEditButton, listEditButton, listAddButton, listDeleteButton;
	JTextArea textArea1;
	JComboBox<String> sectionChoices;
	JRadioButton type1, type2;
//	ProductTableModel personTableModel;
	static int type;
	Section activeSection;
	String userChoice = "";
	ArrayList<Section> dropdownChoices;
	
	public PageManagerFrame() {
		try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        initComponents();
        setTitle("Website Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setSize(300, 200);
        setVisible(true);
	}
	
	public void initComponents() {
		
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		JRadioButton typeChoice1 = new JRadioButton();
		typeChoice1.setText("Experiences");
		typeChoice1.addActionListener((ActionEvent  e) -> {
			type = 1;
			populateDropdown();
		});
		
		JRadioButton typeChoice2 = new JRadioButton();
		typeChoice2.setText("Meals");
		typeChoice2.addActionListener((ActionEvent  e) -> {
			type = 2;
			populateDropdown();
		});
		
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(typeChoice1);
		typeGroup.add(typeChoice2);
		
		//big combo dimensions - for combo list
		GridBagConstraints bc = new GridBagConstraints();
		bc.fill = GridBagConstraints.HORIZONTAL;
		bc.gridwidth = 4;   //2 columns wide
		bc.insets = new Insets(5, 5, 0, 5);
		bc.gridx = 0;
		bc.gridy = 1;
		bc.anchor = GridBagConstraints.CENTER;

		sectionChoices = new JComboBox<String>();
		populateDropdown();
		sectionChoices.addActionListener((ActionEvent e) -> {
			try {
				Object selectedItem = sectionChoices.getSelectedItem();
				String choice = selectedItem.toString();
				activeSection = SectionDB.getSection(choice);
			} catch (Exception e1) {}
		});
				
		//big button dimensions - for HTML button
		GridBagConstraints bb = new GridBagConstraints();
		bb.fill = GridBagConstraints.HORIZONTAL;
		bb.gridwidth = 4;   //2 columns wide
		bb.insets = new Insets(5, 5, 0, 5);
        bb.gridx = 0;
        bb.gridy = 4;
        bb.anchor = GridBagConstraints.CENTER;
		
		createButton = new JButton("HTML");
		createButton.addActionListener((ActionEvent e) -> {
			HTMLCreator program = new HTMLCreator();
			program.runMain(type);
			System.out.println("HTML generated for type " + type);
		});
		
		Dimension buttonDim = new Dimension(80, 20);
		
		paraEditButton = new JButton("Edit");
		paraEditButton.setPreferredSize(buttonDim);
		paraEditButton.addActionListener((ActionEvent e) -> {
			editParagraph(activeSection.getSectionID());
		});
		
		listEditButton = new JButton("Edit");
		listEditButton.setPreferredSize(buttonDim);
		listEditButton.addActionListener((ActionEvent e) -> {
			new ListManagerFrame(this, "Edit List", true, activeSection);
		});
		
		thePanel.add(typeChoice1, getConstraints(0, 0, GridBagConstraints.CENTER));
		thePanel.add(typeChoice2, getConstraints(1, 0, GridBagConstraints.CENTER));
		thePanel.add(sectionChoices, bc);
		
		thePanel.add(new JLabel("Paragraph"), getConstraints(0, 2, GridBagConstraints.WEST));
		thePanel.add(paraEditButton, getConstraints(1, 2, GridBagConstraints.LINE_START));
		
		thePanel.add(new JLabel("List Items"), getConstraints(0, 3, GridBagConstraints.WEST));
		thePanel.add(listEditButton, getConstraints(1, 3, GridBagConstraints.LINE_START));
		
		thePanel.add(createButton, bb);
		
		this.add(thePanel);
		pack();
		
	}

	private void editParagraph(int personID) {
        if (personID == 0) {
            JOptionPane.showMessageDialog(this,
                    "No item is currently selected.", 
                    "No item selected", JOptionPane.ERROR_MESSAGE);
        } else {
            ParagraphForm paraForm = 
                    new ParagraphForm(this, "Edit Paragraph", true, activeSection);
            paraForm.setLocationRelativeTo(this);
            paraForm.setVisible(true);
        }
	}

	private void populateDropdown() {
		dropdownChoices = new ArrayList<>();
		
		try {
			dropdownChoices = (SectionDB.getSection(type));
		} catch (DBException e) {
			e.printStackTrace();
		}
		Section theSection = new Section(0, "Choose One", "", "", type);
		dropdownChoices.add(0, theSection);				//gives an error when selected but no impact to functionality
		sectionChoices.removeAllItems();
		for (Section p : dropdownChoices) {
			sectionChoices.addItem(p.getName());
		}
	}

	private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }
		
}

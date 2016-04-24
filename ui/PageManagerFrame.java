package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;

import ui.ParagraphForm;
import db.DBException;
import db.DBUtil;
import db.PersonDB;
import business.HTMLCreator;
import business.Person;


public class PageManagerFrame extends JFrame {
	
	JButton createButton, paraEditButton, listEditButton, listAddButton, listDeleteButton;
	JTextArea textArea1;
	JComboBox<String> personChoices;
	JRadioButton type1, type2;
//	ProductTableModel personTableModel;
	static int type;
	Person activePerson;
	String userChoice = "";
	ArrayList<Person> dropdownChoices;
	
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
//      setLocationByPlatform(true);
        setVisible(true);
	}
	
	public void initComponents() {
		
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());

		this.setSize(400, 400);
		
		JRadioButton typeChoice1 = new JRadioButton();
		typeChoice1.setText("Hero");
		typeChoice1.addActionListener((ActionEvent  e) -> {
			type = 1;
			populateDropdown();
		});
		
		JRadioButton typeChoice2 = new JRadioButton();
		typeChoice2.setText("Villain");
		typeChoice2.addActionListener((ActionEvent  e) -> {
			type = 2;
			populateDropdown();
		});
		
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(typeChoice1);
		typeGroup.add(typeChoice2);
				
		Dimension comboDim = new Dimension(120, 20);

		personChoices = new JComboBox<String>();
		personChoices.setPreferredSize(comboDim);
		personChoices.setMinimumSize(comboDim);
		populateDropdown();
		personChoices.addActionListener((ActionEvent e) -> {
			try {
				Object selectedItem = personChoices.getSelectedItem();
				String choice = selectedItem.toString();
				activePerson = PersonDB.getPerson(choice);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
				
		Dimension buttonDim = new Dimension(80, 20);
//		Dimension buttonBig = new Dimension(320, 20);
		
		createButton = new JButton("HTML");
//		createButton.setPreferredSize(buttonBig);
		createButton.addActionListener((ActionEvent e) -> {
			HTMLCreator program = new HTMLCreator();
			program.runMain(type);
			System.out.println("HTML generated for type " + type);
		});
		GridBagConstraints bb = new GridBagConstraints();
		bb.fill = GridBagConstraints.HORIZONTAL;
		bb.gridwidth = 4;   //2 columns wide
		bb.insets = new Insets(5, 5, 0, 5);
        bb.gridx = 0;
        bb.gridy = 4;
        bb.anchor = GridBagConstraints.CENTER;
		
		paraEditButton = new JButton("Edit");
		paraEditButton.setPreferredSize(buttonDim);
		paraEditButton.addActionListener((ActionEvent e) -> {
			editParagraph(activePerson.getPersonID());
		});
		
		listEditButton = new JButton("Edit");
		listEditButton.setPreferredSize(buttonDim);
		paraEditButton.addActionListener((ActionEvent e) -> {
			System.out.println("Button clicked!");
		});
		
		listAddButton = new JButton("Add");
		listAddButton.setPreferredSize(buttonDim);
		paraEditButton.addActionListener((ActionEvent e) -> {
			System.out.println("Button clicked!");
		});
		
		listDeleteButton = new JButton("Delete");
		listDeleteButton.setPreferredSize(buttonDim);
		paraEditButton.addActionListener((ActionEvent e) -> {
			System.out.println("Button clicked!");
		});
		
		thePanel.add(typeChoice1, getConstraints(0, 0, GridBagConstraints.CENTER));
		thePanel.add(typeChoice2, getConstraints(1, 0, GridBagConstraints.CENTER));
		thePanel.add(personChoices, getConstraints(2, 0, GridBagConstraints.CENTER));
		
		thePanel.add(new JLabel("Paragraph"), getConstraints(0, 2, GridBagConstraints.WEST));
		thePanel.add(paraEditButton, getConstraints(1, 2, GridBagConstraints.LINE_START));
		
		thePanel.add(new JLabel("List Items"), getConstraints(0, 3, GridBagConstraints.WEST));
		thePanel.add(listEditButton, getConstraints(1, 3, GridBagConstraints.LINE_START));
		thePanel.add(listAddButton, getConstraints(2, 3, GridBagConstraints.LINE_START));
//		thePanel.add(listDeleteButton, getConstraints(3, 3, GridBagConstraints.LINE_START));
		
		thePanel.add(createButton, bb);
		
		this.add(thePanel);
		
		this.setVisible(true);
	}

	private void editParagraph(int personID) {
		System.out.println(activePerson.getName() + " " + activePerson.getPersonID());
        if (personID == 0) {
            JOptionPane.showMessageDialog(this,
                    "No item is currently selected.", 
                    "No item selected", JOptionPane.ERROR_MESSAGE);
        } else {
            ParagraphForm paraForm = 
                    new ParagraphForm(this, "Edit Paragraph", true, activePerson);
            paraForm.setLocationRelativeTo(this);
            paraForm.setVisible(true);
        }
		
	}

	private void populateDropdown() {
		dropdownChoices = new ArrayList<>();
		
		try {
			dropdownChoices = (PersonDB.getPeople(type));
		} catch (DBException e) {
			e.printStackTrace();
		}
		Person thePerson = new Person(0, "Choose One", "", "", type);
		dropdownChoices.add(0, thePerson);				//gives an error when selected but no impact to functionality
		personChoices.removeAllItems();
		for (Person p : dropdownChoices) {
			personChoices.addItem(p.getName());
		}
	}

	private void add() {
//    	ProductForm showPeople = new ProductForm(this, "List Items", true);
//    	listEditForm.setLocationRelativeTo(this);
//    	listEditForm.setVisible(true);
//		
	}

	private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }
	
//	private JTable buildPersonTable() {
//        personTableModel = new ProductTableModel(type);
//        JTable table = new JTable(personTableModel);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setBorder(null);
//        return table;
//    }  

//	private ArrayList<String> getItemsForList() throws DBException {
//		ArrayList<String> dropDownItems = new ArrayList<>();
//		String query = "SELECT PersonName FROM Person WHERE TypeID = ?";
//		
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(query))
//		{
//			ps.setInt(1, type);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				String name = rs.getString("PersonName");
//				dropDownItems.add(name);
//			}
//			return dropDownItems;
//		}
//		catch (SQLException e)
//		{
//			throw new DBException();
//		}
//	}
	
	
}

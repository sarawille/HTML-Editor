package business;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.*;

import db.DBException;
import db.SectionDB;


public class HTMLCreator {
	
	static String style =  "h1 {" +
			"margin-left:50px;" +
			"margin-right:30px;" +
			"text-align:center;" +
			"}h4{" +
			"text-align:center;" +
			"font-size:150%;" +	
			"}p{" +
			"margin-left:50px;" +
			"margin-right:30px;" +
			"text-align:left;" +
			"font-size:105%;" +
			"}ul{" +
			"margin-left:50px;" +
			"margin-right:50px;" +
			"text-align:left;" +
			"font-size:95%;" +
			"}img{" +
			"margin-left:50px;" +	
			"align:center;" +
			"}table {" +
			"border-spacing: 5px;" +
			"text-align:center;" +
			"width:100%;" +
			"}";
			
	static XMLStreamWriter writer;
	static int type;
	
	public void runMain(int type) {
		this.type = type;
		String fileString = "index.html";
		Path filePath = Paths.get(fileString);
		try {
			if (Files.notExists(filePath))
			{
				Files.createFile(filePath);
			}
			FileWriter fileWriter = new FileWriter(filePath.toFile());
			XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
			writer = outputFactory.createXMLStreamWriter(fileWriter);
			
			writer.writeStartElement("html");		
			writer.writeCharacters("\n");
			//head
			writer.writeStartElement("head");		
			writer.writeCharacters("\n");
			writeTitleTags();
			writeStyleTags();
			writeCloseTags();		//closes head
			//body
			writer.writeStartElement("body");
			writer.writeCharacters("\n");
			writeHeaderTags();
			writeBodyTables();
			writeCloseTags();		//closes body
			
			writeCloseTags();		//closes html
			
			writer.flush();
			writer.close();
		} 
		catch (IOException | XMLStreamException e) 
		{
			System.out.println("Error! " + e.getMessage());
		}
	}

	private static void writeCloseTags() throws XMLStreamException {
		writer.writeEndElement();				
		writer.writeCharacters("\n");
	}

	private static void writeBodyTables() throws XMLStreamException {
		try {
			ArrayList<Section> people = SectionDB.getSection(type);
			writer.writeStartElement("table");
			for (Section p : people) 
			{
				setTableContent(p);
			}
			writeCloseTags();				//closes table tag
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
	}

	private static void writeHeaderTags() throws XMLStreamException {
		writer.writeStartElement("h1");
		try {
			writer.writeCharacters(SectionDB.setHeading(type));		
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writeCloseTags();
	}

	private static void writeStyleTags() throws XMLStreamException {
		writer.writeStartElement("style");
		writer.writeCharacters(style);
		writeCloseTags();
	}

	private static void writeTitleTags() throws XMLStreamException {
		writer.writeStartElement("title");
		try {
			writer.writeCharacters(SectionDB.setTitle(type));				
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writeCloseTags();
	}
	
	private static void writeParagraphs(int personID) throws XMLStreamException {
		String paragraph = "";
		try {
			paragraph = SectionDB.getParagraph(personID);
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writer.writeStartElement("p");
		writer.writeCharacters(paragraph);
		writeCloseTags();
		writer.writeCharacters("\n");
	}
	
	public static void writeList(int sectionID) throws XMLStreamException {
		List<ListItem> listItems = new ArrayList<>();
		try {
			listItems = SectionDB.getListItems(sectionID);
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writer.writeStartElement("ul");
		writer.writeCharacters("\n");
		for (ListItem item : listItems){
			writer.writeStartElement("li");
			writer.writeCharacters(item.getText());
			writeCloseTags();
			writer.writeCharacters("\n");
		}
		writeCloseTags();
	}
	
	private static void setTableContent(Section p) throws XMLStreamException, DBException 
	{		
		writer.writeStartElement("tr");
		writer.writeCharacters("\n");
		writer.writeStartElement("td");
		writer.writeAttribute("colspan", "3");
		writer.writeCharacters("\n");
		
		writeCloseTags();			//end td
		
		writer.writeStartElement("td");
		writeCloseTags();			//end td
		writeCloseTags();			//end tr
		
		writer.writeStartElement("tr");
		writer.writeCharacters("\n");
		
		writer.writeStartElement("td");
		writer.writeStartElement("img");
		writer.writeAttribute("src", p.getImage());
		writer.writeAttribute("height", "267");
		writeCloseTags();
		
		writer.writeStartElement("td");
		writer.writeAttribute("rowspan", "2");
		writer.writeStartElement("h4");
		writer.writeStartElement("a");
		writer.writeAttribute("href", p.getLink());
		writer.writeCharacters(p.getName());
		writeCloseTags();			//end a href
		writeCloseTags();			//end h4
		
		writeParagraphs(p.getSectionID());
		writeList(p.getSectionID());
		
		writeCloseTags();
		
		writeCloseTags();
	}

	


	
	
	

}

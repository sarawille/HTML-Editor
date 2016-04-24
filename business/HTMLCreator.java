package business;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;

import javax.xml.stream.*;

import db.DBException;
import db.PersonDB;


public class HTMLCreator {
	
	static String style =  "h1 {" +
			"margin-left:50px;" +
			"margin-right:30px;" +
			"color:hotpink;" +
			"text-align:center;" +
			"}h4{" +
			"text-align:center;" +
			"font-size:150%;" +	
			"}body{"+
			"background-color:black;" +
			"color:white;" +
			"}p{" +
			"margin-left:50px;" +
			"margin-right:30px;" +
			"text-align:left;" +
			"color:white;" +
			"font-size:105%;" +
			"}ul{" +
			"margin-left:50px;" +
			"margin-right:50px;" +
			"text-align:left;" +
			"color:white;" +
			"font-size:95%;" +
			"}strong{" +
			"color:green;" +
			"}em {" +
			"color:orange;" +
			"}img{" +
			"margin-left:50px;" +	
			"align:center;" +
			"}table {" +
			"border-spacing: 5px;" +
			"text-align:center;" +
			"width:100%;" +
			"}" +
			"tr {" +
			"color:white;" +
			"}";

	static XMLStreamWriter writer;
	static int type;
	
	public void runMain(int type) {
		this.type = type;
		String fileString = "indexCopy.html";
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
			ArrayList<Person> people = PersonDB.getPeople(type);
			writer.writeStartElement("table");
			for (Person p : people) 
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
			writer.writeCharacters(PersonDB.setHeading(type));		
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
			writer.writeCharacters(PersonDB.setTitle(type));				
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writeCloseTags();
	}
	
	private static void writeParagraphs(int personID) throws XMLStreamException {
		ArrayList<String> paragraphs = new ArrayList<>();
		try {
			paragraphs = PersonDB.setParagraph(personID);
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		for (String para : paragraphs){
			writer.writeStartElement("p");
			writer.writeCharacters(para);
			writeCloseTags();
			writer.writeCharacters("\n");
		}
	}
	
	public static void writeList(int personID) throws XMLStreamException {
		ArrayList<String> listItems = new ArrayList<>();
		try {
			listItems = PersonDB.setListItems(personID);
		} 
		catch (DBException e) 
		{
			e.printStackTrace();
		}
		writer.writeStartElement("ul");
		writer.writeCharacters("\n");
		for (String item : listItems){
			writer.writeStartElement("li");
			writer.writeCharacters(item);
			writeCloseTags();
			writer.writeCharacters("\n");
		}
		writeCloseTags();
	}
	
	private static void setTableContent(Person p) throws XMLStreamException, DBException 
	{		
		writer.writeStartElement("tr");
		writer.writeCharacters("\n");
		
		writer.writeStartElement("td");
		writeCloseTags();
		
		writer.writeStartElement("td");
		writer.writeCharacters("\n");
		writer.writeStartElement("h4");
		writer.writeAttribute("style", "text-align: center;");
		writer.writeStartElement("a");
		writer.writeAttribute("href", p.getLink());
		writer.writeCharacters(p.getName());
		writeCloseTags();			//end a href
		writeCloseTags();			//end h4
		writeCloseTags();			//end td
		
		writer.writeStartElement("td");
		writeCloseTags();			//end td
		writeCloseTags();			//end tr
		
		writer.writeStartElement("tr");
		writer.writeCharacters("\n");
		
		writer.writeStartElement("td");
		writer.writeStartElement("img");
		writer.writeAttribute("src", p.getImage());
		writer.writeAttribute("width", "200");
		writeCloseTags();
		
		writer.writeStartElement("td");
		writer.writeAttribute("rowspan", "2");
		
		writeParagraphs(p.getPersonID());
		writeList(p.getPersonID());
		
		writeCloseTags();
		
		writeCloseTags();
	}

	


	
	
	

}

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import db.DBException;
import db.DBUtil;
import business.ListItem;
import business.Person;

public class PersonDB {
	
	public static List<ListItem> getListItems() throws XMLStreamException, DBException {
		int personID = 1;
		String query = "SELECT * FROM ListItems WHERE PersonID = ?;";
		List<ListItem> listItems = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setInt(1, personID);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				int row = rs.getInt("RowNum");
				String text = rs.getString("Text");
				ListItem bullet = new ListItem();
				bullet.setRow(row);
				bullet.setText(text);
				bullet.setPersonID(personID);
				System.out.println(text);
				listItems.add(bullet);
			}
		}
		catch (SQLException e) 
		{
			throw new DBException();
		}
		return listItems;
	}
	
	public static String getParagraph(int personID) throws DBException {
		String para = "";
		String query = "SELECT * FROM Paragraph WHERE PersonID = ?;";

		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setInt(1,  personID);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				para = rs.getString("Text");
			}
		}
		catch (SQLException e) 
		{
			throw new DBException();
		}
		return para;
	}

	public static ArrayList<Person> getPeople(int type) throws DBException
	{
		ArrayList<Person> people = new ArrayList<>();
		String query = "SELECT * FROM Person WHERE TypeID = ?;";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) 
		{
			statement.setInt(1,  type);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				int personID = rs.getInt("PersonID");
				String name = rs.getString("PersonName");
				String link = rs.getString("Link");
				String image = rs.getString("Image");
				
				Person thePerson = new Person(personID, name, link, image, type);
				people.add(thePerson);
			}
			return people;
		} 
		catch (SQLException e) {
			throw new DBException();
		}
	}
	
	public static Person getPerson(String name) throws DBException
	{
		String query = "SELECT * FROM Person WHERE PersonName = ?;";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) 
		{
			statement.setString(1,  name);
			ResultSet rs = statement.executeQuery();
			rs.next();
			int personID = rs.getInt("PersonID");
			String link = rs.getString("Link");
			String image = rs.getString("Image");
			int type = rs.getInt("TypeID");
			
			Person thePerson = new Person(personID, name, link, image, type);
			return thePerson;
			
		} 
		catch (SQLException e) {
			throw new DBException();
		}
	}

	public static String getPersonInfo(int personID) throws DBException {
		String query = "SELECT Paragraph.Text FROM Person " +
						"JOIN Paragraph " +
						"ON Person.PersonID = Paragraph.PersonID " +
						"WHERE Person.PersonID = ?;";

		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(query))
		{
			ps.setInt(1, personID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String text = rs.getString("Text");
			return text;
		}
		catch (SQLException e)
		{
			throw new DBException();
		}
	}
	
	public static void updateParagraph(int personID, String text) throws DBException {
		String query = "UPDATE Paragraph SET Text = ? WHERE PersonID = ?;";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, text);
            ps.setInt(2, personID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } 
	}
	

	public static void addList(int personID, String text) throws DBException {
		String query = "INSERT INTO ListItems (PersonID, Text) " + 
						"VALUES (?, ?);";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, personID);
            ps.setString(2, text);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }	
	}
	

	public static void updateList(String originalText, String newText) throws DBException {
		String query = "UPDATE ListItems SET Text = ? WHERE Text = ?;";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newText);
            ps.setString(2, originalText);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } 
	}
	
	public static String setHeading(int type) throws DBException, XMLStreamException {
		String query = "SELECT Heading FROM PageBasics " +
						"WHERE TypeID = ?;";
		
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(query))
		{
			ps.setInt(1, type);
			ResultSet rs = ps.executeQuery();
			rs.next();	
			return rs.getString("Heading");	
		}
		catch (SQLException e) 
		{
			throw new DBException();
		}
	}
	
	public static String setTitle(int type) throws DBException, XMLStreamException {
		String query = "SELECT TabTitle FROM PageBasics " +
						"WHERE TypeID = ?;";

		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(query))
		{
			ps.setInt(1, type);
			ResultSet rs = ps.executeQuery();
			rs.next();		
			return rs.getString("TabTitle");
		}
		catch (SQLException e) 
		{
			throw new DBException();
		}
	}


	
	
}

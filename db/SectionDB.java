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
import business.Section;

public class SectionDB {
	
	public static List<ListItem> getListItems(int sectionID) throws XMLStreamException, DBException {
		String query = "SELECT * FROM ListItems WHERE SectionID = ?;";
		List<ListItem> listItems = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setInt(1, sectionID);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				int row = rs.getInt("RowNum");
				String text = rs.getString("Text");
				ListItem bullet = new ListItem();
				bullet.setRow(row);
				bullet.setText(text);
				bullet.setSectionID(sectionID);
				listItems.add(bullet);
			}
		}
		catch (SQLException e) 
		{
			throw new DBException();
		}
		return listItems;
	}
	
	public static String getParagraph(int sectionID) throws DBException {
		String para = "";
		String query = "SELECT * FROM Paragraph WHERE SectionID = ?;";

		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setInt(1,  sectionID);
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

	public static ArrayList<Section> getSection(int type) throws DBException
	{
		ArrayList<Section> sections = new ArrayList<>();
		String query = "SELECT * FROM Section WHERE TypeID = ?;";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) 
		{
			statement.setInt(1,  type);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				int sectionID = rs.getInt("SectionID");
				String name = rs.getString("Name");
				String link = rs.getString("Link");
				String image = rs.getString("Image");
				
				Section theSection = new Section(sectionID, name, link, image, type);
				sections.add(theSection);
			}
			return sections;
		} 
		catch (SQLException e) {
			throw new DBException();
		}
	}
	
	public static Section getSection(String name) throws DBException
	{
		String query = "SELECT * FROM Section WHERE Name = ?;";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) 
		{
			statement.setString(1,  name);
			ResultSet rs = statement.executeQuery();
			rs.next();
			int sectionID = rs.getInt("SectionID");
			String link = rs.getString("Link");
			String image = rs.getString("Image");
			int type = rs.getInt("TypeID");
			
			Section theSection = new Section(sectionID, name, link, image, type);
			return theSection;
			
		} 
		catch (SQLException e) {
			throw new DBException();
		}
	}

	public static String getPersonInfo(int sectionID) throws DBException {
		String query = "SELECT Paragraph.Text FROM Section " +
						"JOIN Paragraph " +
						"ON Section.SectionID = Paragraph.SectionID " +
						"WHERE Section.SectionID = ?;";

		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(query))
		{
			ps.setInt(1, sectionID);
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
	
	public static void updateParagraph(int sectionID, String text) throws DBException {
		String query = "UPDATE Paragraph SET Text = ? WHERE SectionID = ?;";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, text);
            ps.setInt(2, sectionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } 
	}
	

	public static void addList(int sectionID, String text) throws DBException {
		String query = "INSERT INTO ListItems (SectionID, Text) " + 
						"VALUES (?, ?);";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sectionID);
            ps.setString(2, text);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }	
	}
	

	public static void updateList(int row, String text) throws DBException {
		String query = "UPDATE ListItems SET Text = ? WHERE RowNum = ?;";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, text);
            ps.setInt(2, row);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } 
	}
	
	public static void deleteList(int row) throws DBException {
		String query = "DELETE FROM ListItems WHERE RowNum = ?;";

		Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, row);
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

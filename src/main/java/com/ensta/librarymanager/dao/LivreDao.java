package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao {
	
	////////////////////////
	// SINGLETON LIVREDAO DEBUT
	static LivreDao instance; // pour implémenter singleton cf cours
	
	private LivreDao() {
		
	}
	
	public static LivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDao();
		}
		return instance;
	}
	// SINGLETON FIN
	///////////////////

	public List<Livre> getList() throws DaoException {
		// TODO Auto-generated method stub
		List<Livre> livres = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT id, titre, auteur, isbn FROM livre");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("ISBN");
				livres.add(new Livre(id, titre, auteur, ISBN));
			}

			return livres;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public Livre getById(int id) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT titre, auteur, ISBN FROM Livre WHERE id = ?");
			// permet d'écrire du SQL
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // pour se mettre sur la bonne ligne de valeur (sur table des valeurs de la BDD)
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("ISBN");

			Livre livre = new Livre(id, titre, auteur, ISBN); // permet de faire le lien
			// entre valeurs de la BDD et Java
			return livre;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public int create(Livre livre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
																		// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO livre(titre, auteur, isbn) "
					+ "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			// permet d'écrire du SQL // il faut
			pstmt.setString(1, livre.getTitre()); // signifie au premier ? on met le titre
			pstmt.setString(2, livre.getAuteur());
			pstmt.setString(3, livre.getIsbn());

			pstmt.executeUpdate();
			
			ResultSet resultSet = pstmt.getGeneratedKeys();
			
			if (resultSet.next()) {
				 int id = resultSet.getInt(1);
				 livre.setId(id);
				 return id;
			}
			return 0;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public void update(Livre livre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
																		// lorsqu'on sortira du try

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?");
			// permet d'écrire du SQL // il faut
			pstmt.setString(1, livre.getTitre()); // signifie au premier ? on met le titre
			pstmt.setString(2, livre.getAuteur());
			pstmt.setString(3, livre.getIsbn());
			pstmt.setInt(4, livre.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public void delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM livre WHERE id = ?");
			// permet d'écrire du SQL // il faut
			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM livre");
			// permet d'écrire du SQL // il faut

			ResultSet rs = pstmt.executeQuery();
			rs.next(); // pour se mettre sur la bonne ligne de valeur (sur table des valeurs de la BDD)
			int count = rs.getInt("count");
			
			return count;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}

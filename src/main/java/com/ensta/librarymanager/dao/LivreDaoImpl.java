package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements ILivreDao {

	@Override
	public List<Livre> getList() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Livre getById(int id) throws DaoException {

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT titre, auteur, ISBN FROM Livre WHERE id = ?");
			// permet d'écrire du SQL
			pstmt.setInt(1,  id);
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
		return null;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Livre livre) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int count() throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

}

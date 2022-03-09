package com.ensta.librarymanager.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreService implements ILivreService {

	private LivreDao livreDao = LivreDao.getInstance();

	////////////////////////
	// SINGLETON LIVREDAO DEBUT
	static LivreService instance; // pour implémenter singleton cf cours

	private LivreService() { // on met le constructeur private : pas possible d'init sans passer par
								// getInstance

	}

	public static LivreService getInstance() {
		if (instance == null) {
			instance = new LivreService();
		}
		return instance;
	}
	// SINGLETON FIN
	///////////////////

	public List<Livre> getList() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public Livre getById(int id) throws ServiceException {

		try {
			Connection conn = ConnectionManager.getConnection();
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
			throw new ServiceException();
		}
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Livre livre) throws ServiceException {
		try {
			if (livre.getTitre() == "") {
				throw new ServiceException("Le titre est vide !");
			}

			livreDao.create(livre);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		}
		return 1;

	}

	@Override
	public void update(Livre livre) throws ServiceException {
		try {
			if (livre.getTitre() == "") {
				throw new ServiceException("Le titre est vide !");
			}

			livreDao.update(livre);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public void delete(int id) throws ServiceException {
		try {

			livreDao.delete(id);

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public int count() throws ServiceException {
		try {

			return livreDao.count();

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException();
		}

	}

}

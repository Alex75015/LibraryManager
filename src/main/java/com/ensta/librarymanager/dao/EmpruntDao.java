package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {

	static EmpruntDao instance; // pour implémenter singleton cf cours

	private EmpruntDao() {

	}

	public static EmpruntDao getInstance() {
		if (instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}

	@Override
	public List<Emprunt> getList() throws DaoException {

		List<Emprunt> emprunts = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
					+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre "
					+ "INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				Date dateEmpruntTemp = rs.getDate("dateEmprunt");
				Date dateRetourTemp = rs.getDate("dateRetour");
				LocalDate dateEmprunt, dateRetour;
				if(dateEmpruntTemp != null) {
					dateEmprunt = dateEmpruntTemp.toLocalDate();
				}
				else {
					dateEmprunt = null;
				}
				if(dateRetourTemp != null) {
					dateRetour = dateRetourTemp.toLocalDate();
				}
				else {
					dateRetour = null;
				}
				
				emprunts.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}

			return emprunts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {

		List<Emprunt> emprunts = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
					+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre "
					+ "INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = rs.getDate("dateRetour").toLocalDate();
				emprunts.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}

			return emprunts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {

		List<Emprunt> emprunts = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
					+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre "
					+ "INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?");
			pstmt.setInt(1, idMembre);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idLivre = rs.getInt("idLivre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = rs.getDate("dateRetour").toLocalDate();
				emprunts.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}

			return emprunts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {

		List<Emprunt> emprunts = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
					+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre "
					+ "INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?");
			pstmt.setInt(1, idLivre);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = rs.getDate("dateRetour").toLocalDate();
				emprunts.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}

			return emprunts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Emprunt getById(int id) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
							+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
							+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre "
							+ "INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int idMembre = rs.getInt("idMembre");
			int idLivre = rs.getInt("idLivre");
			LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
			LocalDate dateRetour = rs.getDate("dateRetour").toLocalDate();
			Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);

			return emprunt;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void create(Emprunt emprunt) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, emprunt.getIdMembre()); // signifie au premier ? on met le titre
			pstmt.setInt(2, emprunt.getIdLivre());
			pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));

			pstmt.executeUpdate();
			
			ResultSet resultSet = pstmt.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				emprunt.setId(id);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE emprunt "
					+ "SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? "
					+ "WHERE id = ?",
					PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, emprunt.getIdMembre()); // signifie au premier ? on met le titre
			pstmt.setInt(2, emprunt.getIdLivre());
			pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
			pstmt.setInt(5, emprunt.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public int count() throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM emprunt");

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			
			return count;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}

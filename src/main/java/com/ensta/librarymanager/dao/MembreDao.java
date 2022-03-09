package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDao implements IMembreDao {

	static MembreDao instance; // pour implémenter singleton cf cours

	private MembreDao() {

	}

	public static MembreDao getInstance() {
		if (instance == null) {
			instance = new MembreDao();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();) {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT id, nom, prenom, adresse, email, telephone, abonnement "
							+ "FROM membre ORDER BY nom, prenom");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String abo = rs.getString("abonnement");
				Abonnement abonnement = Abonnement.valueOf(abo);
				membres.add(new Membre(id, nom, prenom, adresse, email, telephone, abonnement));
			}

			return membres;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Membre getById(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?");
			// permet d'écrire du SQL
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // pour se mettre sur la bonne ligne de valeur (sur table des valeurs de la BDD)
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String adresse = rs.getString("adresse");
			String email = rs.getString("email");
			String telephone = rs.getString("telephone");
			String abo = rs.getString("abonnement");
			Abonnement abonnement = Abonnement.valueOf(abo);

			Membre membre = new Membre(id, nom, prenom, adresse, email, telephone, abonnement); // permet de faire le
																								// lien
			// entre valeurs de la BDD et Java
			return membre;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int create(Membre membre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) "
							+ "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
// permet d'écrire du SQL // il faut
			pstmt.setString(1, membre.getNom()); // signifie au premier ? on met le titre
			pstmt.setString(2, membre.getPrenom());
			pstmt.setString(3, membre.getAdresse());
			pstmt.setString(4, membre.getEmail());
			pstmt.setString(5, membre.getTelephone());
			pstmt.setString(6, membre.getAbonnement().toString());

			pstmt.executeUpdate();

			ResultSet resultSet = pstmt.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				membre.setId(id);
				return id;
			}
			return 0;

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Membre membre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, "
							+ "abonnement = ? WHERE id = ?");
// permet d'écrire du SQL // il faut
			pstmt.setString(1, membre.getNom()); // signifie au premier ? on met le titre
			pstmt.setString(2, membre.getPrenom());
			pstmt.setString(3, membre.getAdresse());
			pstmt.setString(4, membre.getEmail());
			pstmt.setString(5, membre.getTelephone());
			pstmt.setString(6, membre.getAbonnement().toString());
			pstmt.setInt(7, membre.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public void delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM membre WHERE id = ?");
			// permet d'écrire du SQL // il faut
			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) { // en mettant la connection là, ça fermera co
			// lorsqu'on sortira du try

			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM membre");
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

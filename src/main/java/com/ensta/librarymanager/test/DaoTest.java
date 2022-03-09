package com.ensta.librarymanager.test;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.LivreService;

public class DaoTest {
	public static void main(String[] args) {

		TestLivreDao();

		TestMembreDao();
		
		TestEmpruntDao();


	}

	public static void TestLivreDao() {
		LivreDao livreDao = LivreDao.getInstance();

		try {
			System.out.println(livreDao.getById(1));
			System.out.println(livreDao.getList());
			System.out.println(livreDao.count());
			Livre livre = new Livre(1, "Sous le vent", "Marcelino", "666");
			System.out.println(livreDao.create(livre));
			System.out.println(livreDao.getById(livre.getId()));
			livre.setTitre("Brasiliano sous le vent");
			livreDao.update(livre);
			System.out.println(livreDao.getById(livre.getId()));
			livreDao.delete(livre.getId());
			System.out.println(livreDao.getList());
			System.out.println(livreDao.count());
			System.out.println();
			System.out.println();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public static void TestMembreDao() {
		MembreDao membreDao = MembreDao.getInstance();

		try {
			System.out.println(membreDao.getById(1));
			System.out.println(membreDao.getList());
//			System.out.println(membreDao.count());
//			Membre membre = new Membre(1, "Marcelino", "Marcelina", "Marceloland", "marcelina@marcelino.land", "666",
//					Abonnement.VIP);
//			System.out.println(membreDao.create(membre));
//			System.out.println(membreDao.getById(membre.getId()));
//			membre.setNom("Brasiliano");
//			membreDao.update(membre);
//			System.out.println(membreDao.getById(membre.getId()));
//			membreDao.delete(membre.getId());
//			System.out.println(membreDao.getList());
//			System.out.println(membreDao.count());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public static void TestEmpruntDao() {
		EmpruntDao empruntDao = EmpruntDao.getInstance();
		MembreDao membreDao = MembreDao.getInstance();
		LivreDao livreDao = LivreDao.getInstance();
		
		try {
			System.out.println(empruntDao.getById(1));
			System.out.println(empruntDao.getList());
			System.out.println(empruntDao.count());
			Emprunt empruntTest = empruntDao.getById(1);
			Emprunt emprunt = new Emprunt(1, membreDao.getById(1).getId(), livreDao.getById(1).getId(), empruntTest.getDateEmprunt(), empruntTest.getDateRetour());
			empruntDao.create(emprunt);
			System.out.println(empruntDao.getById(emprunt.getId()));
			emprunt.setIdLivre(2);
			empruntDao.update(emprunt);
			System.out.println(empruntDao.getById(emprunt.getId()));
			System.out.println(empruntDao.getList());
			System.out.println(empruntDao.count());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}

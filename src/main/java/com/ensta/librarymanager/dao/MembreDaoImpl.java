package com.ensta.librarymanager.dao;

import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Membre;

public class MembreDaoImpl implements IMembreDao {

	@Override
	public List<Membre> getList() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Membre getById(int id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Membre membre) throws DaoException {
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

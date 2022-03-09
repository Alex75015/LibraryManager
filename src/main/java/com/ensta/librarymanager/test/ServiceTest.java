package com.ensta.librarymanager.test;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.LivreService;

public class ServiceTest {
	public static void main(String[] args) {
		
		LivreService livreService = LivreService.getInstance();
		
		try {
			System.out.println(livreService.create(new Livre(1,"Hi","Bruno","322")));
		}catch(ServiceException e) {
			e.printStackTrace();
		}
	}
}

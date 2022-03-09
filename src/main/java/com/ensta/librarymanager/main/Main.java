package com.ensta.librarymanager.main;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreService;

public class Main {
	public static void main(String[] args) {
		
		LivreService livreService = LivreService.getInstance();
		
		try {
			System.out.println(livreService.getById(1));
		}catch(ServiceException e) {
			e.printStackTrace();
		}
	}

}

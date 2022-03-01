package com.ensta.librarymanager.exception;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super("Une erreur s'est produite dans le service");
	  }

	  public ServiceException(String message) {
	    super(message);
	  }
}

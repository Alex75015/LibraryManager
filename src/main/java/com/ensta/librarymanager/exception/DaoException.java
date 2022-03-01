package com.ensta.librarymanager.exception;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoException() {
	  }

	  public DaoException(String message) {
	    super(message);
	  }
}

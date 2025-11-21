package com.example.Evaluation.exception;

public class LoaderServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L; 

	 public LoaderServiceException(String message) {
	        super(message);
	    }

	    public LoaderServiceException(String message, Throwable cause) {
	        super(message, cause);
	    }
	
}

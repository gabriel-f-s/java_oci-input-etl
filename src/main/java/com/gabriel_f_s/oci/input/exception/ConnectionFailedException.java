package com.gabriel_f_s.oci.input.exception;

public class ConnectionFailedException extends RuntimeException {
  public ConnectionFailedException(String message) {
    super(String.format("Failed to connect to the website: '%s'", message));
  }
}

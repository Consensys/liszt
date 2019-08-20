package net.consensys.liszt.server;

public class Response {
  String hash;
  boolean isValid;

  public Response(String hash, boolean isValid) {
    this.hash = hash;
    this.isValid = isValid;
  }
}

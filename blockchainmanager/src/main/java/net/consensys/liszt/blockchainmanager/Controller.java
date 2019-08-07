package net.consensys.liszt.blockchainmanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Controller {
  List<EthAccount> accounts() throws Exception;

  String provider();

  void start() throws Exception;

  boolean isLocalNodeStarted();

  void saveContractAddress(String addr) throws FileNotFoundException;

  String getContractAddress() throws IOException;
}

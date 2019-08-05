package net.consensys.liszt.blockchainmanager;

import java.util.List;

public interface Controller {
  List<EthAccount> accounts();

  String provider();
}

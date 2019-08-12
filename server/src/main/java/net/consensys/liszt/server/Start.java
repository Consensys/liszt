package net.consensys.liszt.server;

// java --add-opens java.base/jdk.internal.loader=ALL-UNNAMED -Xms6000m -Xmx12048m -classpath
// server/build/libs/liszt-ws.jar net.consensys.liszt.server.Start

public class Start {

  public static void main(String[] args) {

    new Controller();
  }
}

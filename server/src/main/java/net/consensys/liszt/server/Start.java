package net.consensys.liszt.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// java --add-opens java.base/jdk.internal.loader=ALL-UNNAMED -Xms6000m -Xmx12048m -classpath
// server/build/libs/liszt-ws.jar net.consensys.liszt.server.Start
@SpringBootApplication(scanBasePackages = {"net.consensys.liszt.server"})
public class Start {

  public static void main(String[] args) {
    SpringApplication.run(Start.class, args);
  }
}

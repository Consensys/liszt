package net.consensys.liszt.server;

// java --add-opens java.base/jdk.internal.loader=ALL-UNNAMED -Xms6000m -Xmx12048m -classpath
// server/build/libs/liszt-ws.jar net.consensys.liszt.server.Start

import static spark.Spark.after;
import static spark.SparkBase.port;

import org.apache.log4j.BasicConfigurator;
import spark.Filter;

public class Start {

  public static void main(String[] args) {
    BasicConfigurator.configure();

    short rollup0 = Short.valueOf(args[0]);
    short rollup1 = Short.valueOf(args[1]);
    int port = Integer.valueOf(args[2]);
    port(port);

    new Controller(rollup0, rollup1);

    after(
        (Filter)
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "GET");
            });
  }
}

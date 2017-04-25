
package main.java.ws;

import main.java.framework.api.Database;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

public class ExampleAction implements RequestHandler {

  @Override
  public void handle(Request request, Response response) throws Exception {
    Database.getMeasures("loc");

    try (JsonWriter json = response.newJsonWriter()) {
      json.beginObject();
      json.prop("what", "wohoo");
      json.endObject();
    }
  }
}

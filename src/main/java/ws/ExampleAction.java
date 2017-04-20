
package main.java.ws;

import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

public class ExampleAction implements RequestHandler {

  @Override
  public void handle(Request request, Response response) throws Exception {
    try (JsonWriter json = response.newJsonWriter()) {
      json.beginObject();
      json.prop("what", "wohoo");
      json.endObject();
    }
  }
}

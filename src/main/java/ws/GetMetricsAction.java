
package main.java.ws;

import main.java.framework.api.Database;
import main.java.framework.api.metrics.MetricsRegister;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

public class GetMetricsAction implements RequestHandler {

  @Override
  public void handle(Request request, Response response) throws Exception {
    MetricsRegister.getFrameworkMetrics();

    try (JsonWriter json = response.newJsonWriter()) {
      json.beginObject();
      json.prop("what", "wohoo");
      json.endObject();
    }
  }
}

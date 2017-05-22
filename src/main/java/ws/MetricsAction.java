
package main.java.ws;

import main.java.framework.api.metrics.MetricsRegister;
import org.sonar.api.measures.Metric;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class MetricsAction implements RequestHandler {

    private static final String METRICS = "metrics";
    public static final String NAME = "name";
    public static final String KEY = "key";

    private String formatMetricDescription(Metric metric) {
        return metric.getDescription() + " (" + metric.getKey().toUpperCase() + ")";
    }

    @Override
    public void handle(Request request, Response response) throws Exception {


        Map<String, String> metrics = new HashMap<>();
        metrics.put(MetricsRegister.LOC_CLASS.getKey(), "Lines of code (" + MetricsRegister.LOC_CLASS.getKey().toUpperCase() + ")" );
        metrics.put(MetricsRegister.NOA.getKey(), formatMetricDescription(MetricsRegister.NOA));
        metrics.put(MetricsRegister.NOM.getKey(), formatMetricDescription(MetricsRegister.NOM));
        metrics.put(MetricsRegister.CYCLO_AVERAGE.getKey(), formatMetricDescription(MetricsRegister.CYCLO_AVERAGE));
        metrics.put(MetricsRegister.CYCLO_MAXIMUM.getKey(), formatMetricDescription(MetricsRegister.CYCLO_MAXIMUM));
        metrics.put(MetricsRegister.CYCLO_TOTAL.getKey(), formatMetricDescription(MetricsRegister.CYCLO_TOTAL));
        metrics.put(MetricsRegister.WMC.getKey(), formatMetricDescription(MetricsRegister.WMC));
        metrics.put(MetricsRegister.ATFD_CLASS.getKey(), formatMetricDescription(MetricsRegister.ATFD_CLASS));
        metrics.put(MetricsRegister.TCC.getKey(), formatMetricDescription(MetricsRegister.TCC));



        try (JsonWriter json = response.newJsonWriter()) {

            json.beginObject();

            json.name(METRICS);
            json.beginArray();
            for (Map.Entry<String, String> metric: metrics.entrySet()) {
                json.beginObject();
                json.prop(KEY, metric.getKey());
                json.prop(NAME, metric.getValue());
                json.endObject();
            }
            json.endArray();

            json.endObject();
        }
    }
}


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
    private static final String NAME = "name";
    private static final String KEY = "key";

    private String formatMetricDescription(Metric metric, String customDescription) {
        return String.format("%s (%s)",
                customDescription != null ? customDescription: metric.getDescription(),
                metric.getKey().toUpperCase()
        );
    }

    private Map<String, String> addMetric(Map<String, String> metrics, Metric metric, String customDescription) {
        metrics.put(metric.getKey(), formatMetricDescription(metric, customDescription));
        return metrics;
    }

    private Map<String, String> addMetric(Map<String, String> metrics, Metric metric) {
        return addMetric(metrics, metric, null);
    }



    @Override
    public void handle(Request request, Response response) throws Exception {

        Map<String, String> metrics = new HashMap<>();

        metrics = addMetric(metrics, MetricsRegister.NOA);
        metrics = addMetric(metrics, MetricsRegister.NOM);
        metrics = addMetric(metrics, MetricsRegister.CYCLO_AVERAGE);
        metrics = addMetric(metrics, MetricsRegister.CYCLO_MAXIMUM);
        metrics = addMetric(metrics, MetricsRegister.CYCLO_TOTAL);
        metrics = addMetric(metrics, MetricsRegister.ATFD_CLASS);
        metrics = addMetric(metrics, MetricsRegister.TCC);
        metrics = addMetric(metrics, MetricsRegister.LOC_CLASS, "Lines of code");


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

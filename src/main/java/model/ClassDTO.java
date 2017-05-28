package main.java.model;

import org.sonar.api.utils.text.JsonWriter;

import java.util.Map;

/**
 * @author Klara Erlebachova
 */
public class ClassDTO {

    private static final String KEY = "key";
    private static final String VALUE = "value";

    private String name;
    private String fileKey;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Map<String, Integer> measures;

    public ClassDTO(String name, String fileKey, Double x, Double y, Double width, Double height, Map<String, Integer> measures) {
        this.name = name;
        this.fileKey = fileKey;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.measures = measures;
    }

    public void toJson(JsonWriter writer, String colorMetric) {
        writer.beginObject();
        writer.prop("name", this.name);
        writer.prop("filekey", this.fileKey);
        writer.prop("x", this.x);
        writer.prop("y", this.y);
        writer.prop("width", this.width);
        writer.prop("height", this.height);
        writer.prop("color", this.measures.getOrDefault(colorMetric, 0));

        writer.name("metrics");
        writer.beginArray();
        for (Map.Entry<String, Integer> metric : measures.entrySet()) {
            writer.beginObject();
            writer.prop(KEY, metric.getKey());
            writer.prop(VALUE, metric.getValue());
            writer.endObject();
        }
        writer.endArray();
        writer.endObject();
    }
}

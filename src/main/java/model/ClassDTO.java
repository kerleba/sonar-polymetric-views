package main.java.model;

import org.sonar.api.internal.google.gson.JsonObject;
import org.sonar.api.utils.text.JsonWriter;

import java.util.Map;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ClassDTO {

    // ToDo: Other info to be shown in box

    private String name;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private Map<String, Integer> measures;

    public ClassDTO(String name, Double x, Double y, Double width, Double height, Map<String, Integer> measures) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.measures = measures;
    }

    public String getName() {
        return name;
    }

    public void toJson(JsonWriter writer, String colorMetric) {
        writer.beginObject();
        writer.prop("name", this.name);
        writer.prop("x", this.x);
        writer.prop("y", this.y);
        writer.prop("width", this.width);
        writer.prop("height", this.height);
        writer.prop("color", this.measures.getOrDefault(colorMetric, 0));
        writer.endObject();
    }
}

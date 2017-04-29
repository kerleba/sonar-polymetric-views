package main.java.model;

import org.sonar.api.internal.google.gson.JsonObject;
import org.sonar.api.utils.text.JsonWriter;

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

    public ClassDTO(String name, Double x, Double y, Double width, Double height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void toJson(JsonWriter writer) {
        writer.beginObject();
        writer.prop("name", this.name);
        writer.prop("x", this.x);
        writer.prop("y", this.y);
        writer.prop("width", this.width);
        writer.prop("height", this.height);
        writer.endObject();
    }
}

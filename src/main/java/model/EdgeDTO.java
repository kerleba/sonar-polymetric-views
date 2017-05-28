package main.java.model;

import org.sonar.api.utils.text.JsonWriter;

/**
 *
 * @author Klara Erlebachova
 */
public class EdgeDTO {

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

    public EdgeDTO(Double startX, Double startY, Double endX, Double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void toJson(JsonWriter writer) {
        writer.beginObject();
        writer.prop("startX", this.startX);
        writer.prop("startY", this.startY);
        writer.prop("endX", this.endX);
        writer.prop("endY", this.endY);
        writer.endObject();
    }
}

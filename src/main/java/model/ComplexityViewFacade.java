package main.java.model;

import main.java.framework.api.Database;
import main.java.framework.api.components.IComponent;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ComplexityViewFacade {

    private String projectId;

    public ComplexityViewFacade(String projectId) {
        this.projectId = projectId;
    }

    // ToDo: will be private
    public Collection<IComponent> getClassesForProject(String projectId) {
        Collection<main.java.framework.api.components.IComponent> components = Database.getClassComponents();
        return components.stream().filter(x -> x.getSonarProjectID().equals(projectId)).collect(Collectors.toList());
    }



}

package main.java.model;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ComplexityViewFacade {

    private String projectId;
    
    private ClassExtentProvider classExtentProvider;
    private  DefaultConfiguration<ClassComponent> configuration;

    private Collection<ClassDTO> classDTOs;
    private Collection<EdgeDTO> edgeDTOs;

    private double leftOffset;

    public ComplexityViewFacade(String projectId) {
        this.projectId = projectId;
        this.classDTOs = new ArrayList<>();
        this.edgeDTOs = new ArrayList<>();
        this.leftOffset = 0;
    }

    public Pair<Collection<ClassDTO>, Collection<EdgeDTO>> getDataFor() {
        TreeFactory treeFactory = new TreeFactory();
        Collection<DefaultTreeForTreeLayout<ClassComponent>> forest = treeFactory.createForestFor(this.projectId);

        // create the NodeExtentProvider for ClassComponent nodes
        this.classExtentProvider = new ClassExtentProvider("loc_class", "loc_class");

        // setup the tree layout configuration
        double gapBetweenLevels = 50;
        double gapBetweenNodes = 10;
        this.configuration = new DefaultConfiguration<>(gapBetweenLevels, gapBetweenNodes);

        for (DefaultTreeForTreeLayout<ClassComponent> tree :
                forest) {
            this.getDataFor(tree);
        }

        return new ImmutablePair<>(this.classDTOs, this.edgeDTOs);
    }

    private void getDataFor(DefaultTreeForTreeLayout<ClassComponent> tree) {
       
        // create the layout
        TreeLayout<ClassComponent> treeLayout = new TreeLayout<>(tree,
                this.classExtentProvider, this.configuration);

        // Generate the classes and edges
        DiagramGenerator generator = new DiagramGenerator(treeLayout,this.leftOffset);
        this.classDTOs.addAll(generator.getClasses());
        this.edgeDTOs.addAll(generator.getEdges());
        this.leftOffset = this.leftOffset + generator.getNewLeftOffset() + 10;
    }


}

package main.java.model;

import main.java.framework.api.MeasurementRepository;
import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.Configuration;
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
    private DefaultConfiguration<ClassComponent> configuration;

    private Collection<ClassDTO> classDTOs;
    private Collection<EdgeDTO> edgeDTOs;

    private double leftOffset;

    private static final double GAP_BETWEEN_LEVELS = 50;
    private static final double GAP_BETWEEN_NODES = 10;

    public ComplexityViewFacade(String projectId) {
        this.projectId = projectId;
        this.classDTOs = new ArrayList<>();
        this.edgeDTOs = new ArrayList<>();
        this.leftOffset = 0;
    }

    public Pair<Collection<ClassDTO>, Collection<EdgeDTO>> getDataFor(String widthMetric, String heightMetric) {
        TreeFactory treeFactory = new TreeFactory();
        Collection<DefaultTreeForTreeLayout<ClassComponent>> forest = treeFactory.createForestFor(this.projectId);
        // create the NodeExtentProvider for ClassComponent nodes
        this.classExtentProvider = new ClassExtentProvider(widthMetric, heightMetric);

        // setup the tree layout configuration
        this.configuration = new DefaultConfiguration<>(GAP_BETWEEN_LEVELS, GAP_BETWEEN_NODES, Configuration.Location.Top, Configuration.AlignmentInLevel.TowardsRoot);

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
        this.leftOffset = this.leftOffset + generator.getNewLeftOffset() + GAP_BETWEEN_NODES;
    }

    public Pair<Integer, Integer> getBoundariesFor(String metric) {
        return MeasurementRepository.getBoundariesFor(this.projectId, metric);
    }


}

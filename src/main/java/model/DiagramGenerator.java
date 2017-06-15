/*
 * [The "BSD license"]
 * Copyright (c) 2011, abego Software GmbH, Germany (http://www.abego.org)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the abego Software GmbH nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package main.java.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;


public class DiagramGenerator {
    private final TreeLayout<ClassComponent> treeLayout;

    private Collection<ClassDTO> classDTOs;
    private Collection<EdgeDTO> edgeDTOs;

    private double leftOffset;

    private boolean cached = false;

    public DiagramGenerator(TreeLayout<ClassComponent> treeLayout, double leftOffset) {
        this.treeLayout = treeLayout;
        this.classDTOs = new ArrayList<>();
        this.edgeDTOs = new ArrayList<>();
        this.leftOffset = leftOffset;
    }

    private TreeForTreeLayout<ClassComponent> getTree() {
        return treeLayout.getTree();
    }

    private Iterable<ClassComponent> getChildren(ClassComponent parent) {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(ClassComponent node) {
        return treeLayout.getNodeBounds().get(node);
    }

    private void generateEdges(ClassComponent parent) {
        if (!getTree().isLeaf(parent)) {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getMaxY();
            for (ClassComponent child: getChildren(parent)) {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                this.edgeDTOs.add(new EdgeDTO(this.leftOffset + x1, y1, this.leftOffset + b2.getCenterX(), b2.getMinY()));
                generateEdges(child);
            }
        }
    }

    private void generateBox(ClassComponent classComponent) {
        Rectangle2D.Double box = getBoundsOfNode(classComponent);
        ClassDTO classDTO = new ClassDTO(
                classComponent.getID(),
                classComponent.getFileKey(),
                this.leftOffset + box.x,
                box.y,
                box.width,
                box.height,
                classComponent.getMeasures()
        );
        this.classDTOs.add(classDTO);
    }

    private void generateDiagram() {
        generateEdges(getTree().getRoot());
        for (ClassComponent ClassComponent: treeLayout.getNodeBounds().keySet()) {
            generateBox(ClassComponent);
        }
    }

    public Collection<ClassDTO> getClasses() {
        if (!this.cached) {
            generateDiagram();
        }
        return Collections.unmodifiableCollection(this.classDTOs);
    }

    public Collection<EdgeDTO> getEdges() {
        if (!this.cached) {
            generateDiagram();
        }
        return Collections.unmodifiableCollection(this.edgeDTOs);
    }

    public double getNewLeftOffset() {
        return this.treeLayout.getBounds().getWidth();
    }
}
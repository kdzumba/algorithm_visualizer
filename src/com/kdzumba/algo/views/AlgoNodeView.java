package com.kdzumba.algo.views;

import com.kdzumba.algo.interfaces.AlgoObserver;
import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * The AlgoNodeView is a visual representation of an AlgoNodeModel's state
 */
public class AlgoNodeView extends JButton implements AlgoObserver {
    private final AlgoNodeModel algoNodeModel;
    private int blockOffset = 0;

    @Override
    public void update() {
        this.updateColor();
    }

    private enum NodeColor {
        PORTAL(Color.BLUE),
        DEFAULT(Color.white)
        ;
        private final Color color;
        NodeColor(Color color) {
            this.color = color;
        }
    }

    AlgoNodeView(AlgoNodeModel algoNodeModel){
        this.algoNodeModel = algoNodeModel;
        this.addActionListener(e -> {
            if(!algoNodeModel.isBoundaryNode()){
                switch(AlgoTerrainPicker.selectedType) {
                    case PORTAL -> algoNodeModel.setPortal(!algoNodeModel.isPortal());
                    case WATER -> algoNodeModel.setWater(!algoNodeModel.isWater());
                    case ROCKY -> algoNodeModel.setRocky(!algoNodeModel.isRocky());
                    case GRASS -> algoNodeModel.setGrass(!algoNodeModel.isGrass());
                    default -> algoNodeModel.setWall(!algoNodeModel.isWall());
                }
                updateColor();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Component source = (Component) e.getSource();
                AlgoBoard parent = (AlgoBoard) source.getParent();
                //This sends the location of the node view on which the event originated
                //and is used as an offset of the event's x-position in the parent
                //mouse dragged handler
                parent.setFocusedLocation((int)getLocation().getX(), (int)getLocation().getY());
                //Mouse dragged events handled by the parent of the node view(to allow for dragging
                //over multiple nodes)
                parent.dispatchEvent(e);
            }
        });
        this.setOpaque(false);
        this.setContentAreaFilled(false);
//        this.setBorderPainted(false);
        this.setBackground(NodeColor.DEFAULT.color);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(AlgoNodeModel.SIZE, AlgoNodeModel.SIZE);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(AlgoNodeModel.SIZE, AlgoNodeModel.SIZE);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(AlgoNodeModel.SIZE, AlgoNodeModel.SIZE);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth() - blockOffset, this.getHeight() - blockOffset);
        super.paintComponent(g);
    }

    public void updateColor(){
        this.blockOffset = 0;
        if(this.algoNodeModel.isWall()){
            this.setBackground(UICommon.WALL_COLOR);
        }
        else if(this.algoNodeModel.isDestination()){
            this.setBackground(UICommon.DEST_COLOR);
        }
        else if(this.algoNodeModel.isStart()){
            this.setBackground(UICommon.START_COLOR);
        }
        else if(this.algoNodeModel.onShortestPath()){
            this.setBackground(UICommon.PATH_COLOR);
        }
//        else if(this.algoNodeModel.isToBeProcessed()){
//            this.blockOffset = 3;
//            this.setBackground(UICommon.GRASS_COLOR);
//        }
        else if(this.algoNodeModel.isProcessed()){
            this.setBackground(UICommon.PROCESSED_COLOR);
        }
        else if(this.algoNodeModel.isPortal()){
            this.setBackground(NodeColor.PORTAL.color);
        }
        else if(this.algoNodeModel.isRocky()){
            this.setBackground(UICommon.ROCK_COLOR);
        }
        else if(this.algoNodeModel.isWater()){
            this.setBackground(UICommon.WATER_COLOR);
        }
        else if(this.algoNodeModel.isGrass()){
            this.setBackground(UICommon.GRASS_COLOR);
        }
        else{
            this.setBackground(NodeColor.DEFAULT.color);
        }
        repaint();
    }
}

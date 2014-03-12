package MapDenmark;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

 
/**
 *
 * @author nith14
 */
public class PointsExample extends JFrame
{
 
    public PointsExample()
    {
        this.add(new DrawPanel());
    }
 
    class DrawPanel extends JPanel
    {
 
        @Override
        public void paintComponent(Graphics g)
        {
 
            super.paintComponent(g);
            try {
                doDrawing(g);
            } catch (IOException ex) {
                System.out.println("Cannot find specified file");
            }
        }
        
        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(800, 600);
        }
 
        private void doDrawing(Graphics g) throws IOException
        {
 
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

 
            String dir = "";
 
            final HashMap<Integer, NodeData> nodes = new HashMap<>();
            final ArrayList<EdgeData> edges = new ArrayList<>();
 
            KrakLoader loader = new KrakLoader()
            {
                @Override
                public void processNode(NodeData nd)
                {
                    nodes.put(nd.KDV, nd);
                }
 
                @Override
                public void processEdge(EdgeData ed)
                {
                    edges.add(ed);
                }
            };
 
            loader.load(dir + "kdv_node_unload.txt",
                    dir + "kdv_unload.txt");
 
 
            
            for (int i = 0; i < 812300; i++) {
                EdgeData edge = edges.get(i);
                
                if(edge.TYP == 8) {
                    g2d.setColor(Color.GREEN);
                }
                
                else if(edge.TYP == 3){
                    g2d.setColor(Color.BLUE);
                }
                else if(edge.TYP == 1){
                    g2d.setColor(Color.RED);
                }
                else{
                    g2d.setColor(Color.BLACK);
                }
                
                g2d.draw(new Line2D.Double(((nodes.get(edge.FNODE).X_COORD) - 441000) / 600,
                        (6403000 - (nodes.get(edge.FNODE).Y_COORD)) / 600,
                        ((nodes.get(edge.TNODE).X_COORD) - 441000) / 600,
                        (6403000 - (nodes.get(edge.TNODE).Y_COORD)) / 600));
            }
 
        }
    }
 
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                PointsExample ex = new PointsExample();
                ex.setDefaultCloseOperation(EXIT_ON_CLOSE);
                ex.pack();
                ex.setLocationRelativeTo(null);
                ex.setVisible(true);
 
            }
        });
    }
}
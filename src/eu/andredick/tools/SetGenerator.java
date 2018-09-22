package eu.andredick.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class SetGenerator extends JPanel {

    private int g_size, t_size;
    private ArrayList<Point> elements;
    private ArrayList<Set> sets;
    private Random rGenerator;


    public SetGenerator(int g_size, int t_size) {
        super();
        this.g_size = g_size;
        this.t_size = t_size;
        this.elements = new ArrayList<>();
        this.sets = new ArrayList<>();
        this.rGenerator = new Random();
        this.createRandomElements();

        JFrame frame = new JFrame("Draw Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(this);
        frame.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                //e.getComponent().repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.blue);
        for (Point e : this.elements) {
            g2.fill(new Ellipse2D.Double((e.x * getWidth()), (e.y * getWidth()), 4, 4));
        }

        Color color = new Color(1, 0, 0, 0.1f); //Red
        g2.setPaint(color);
        for (Set s : this.sets) {
            g2.draw(new Ellipse2D.Double((s.x * getWidth() - 4), (s.y * getWidth() - 4), 8, 8));
            g2.fill(new Rectangle2D.Double((s.x - s.dx) * getWidth(), (s.y - s.dy) * getWidth(), s.dx * 2 * getWidth(), s.dy * 2 * getWidth()));
        }
    }

    public boolean[][] getRelations() {
        boolean[][] relations = new boolean[this.g_size][this.t_size];

        for (int i = 0; i < this.elements.size(); i++) {
            for (int j = 0; j < this.sets.size(); j++) {
                if (this.sets.get(j).contains(this.elements.get(i))) {
                    relations[i][j] = true;
                } else {
                    relations[i][j] = false;
                }
            }
        }

        return relations;
    }

    private void createRandomElements() {
        for (int i = 0; i < this.t_size; i++) {
            sets.add(new Set(rGenerator.nextFloat(), rGenerator.nextFloat(), rGenerator.nextFloat() / (float) (0.3 * Math.sqrt(t_size)), rGenerator.nextFloat() / (float) (0.3 * Math.sqrt(t_size))));
        }

        int count_e = 0;
        while (count_e < g_size) {
            Point e = new Point(rGenerator.nextFloat(), rGenerator.nextFloat());
            boolean treffer = false;
            for (int j = 0; j < sets.size(); j++) {
                if (sets.get(j).inBounds(e)) {
                    sets.get(j).add(e);
                    treffer = true;
                }
            }
            if (treffer) {
                this.elements.add(e);
                count_e++;
            }
        }

    }

    class Point {
        float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }


    }

    class Set {
        float x, y;
        float dx, dy;
        ArrayList<Point> elements;

        Set(float x, float y, float dx, float dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.elements = new ArrayList<>();
        }

        public boolean inBounds(Point e) {
            if (e.x <= (this.x + this.dx) && e.x >= (this.x - this.dx)) {
                if (e.y <= (this.y + this.dy) && e.y >= (this.y - this.dy)) {
                    return true;
                }
            }
            return false;
        }

        public void add(Point e) {
            this.elements.add(e);
        }

        public int size() {
            return this.elements.size();
        }

        public boolean contains(Point e) {
            return this.elements.contains(e);
        }
    }
}

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI {

    // checks if p1 lies on the segment of "p2p3"
    static boolean onSameLineSeg(Point p1, Point p2, Point p3) {
        return (p1.x >= Math.min(p2.x, p3.x) && p1.x <= Math.max(p2.x, p3.x) && p1.y >= Math.min(p2.y, p3.y) && p1.y <= Math.max(p2.y, p3.y));
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }


    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(Point p1, Point q1,
                               Point p2, Point q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are colinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSameLineSeg(p1, p2, q1)) {
            return true;
        }

        // p1, q1 and p2 are colinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSameLineSeg(p1, q2, q1)) {
            return true;
        }

        // p2, q2 and p1 are colinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSameLineSeg(p2, p1, q2)) {
            return true;
        }

        // p2, q2 and q1 are colinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSameLineSeg(p2, q1, q2)) {
            return true;
        }

        // Doesn't fall in any of the above cases
        return false;
    }


    public static Color getShadedColour(Color color, double shade) { // Performs the actual shading and sRGB to RGB conversion
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1 / 2.4);
        int green = (int) Math.pow(greenLinear, 1 / 2.4);
        int blue = (int) Math.pow(blueLinear, 1 / 2.4);

        return new Color(red, green, blue);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Container pane = frame.getContentPane();

        pane.setLayout(new BorderLayout());

        JSlider horizontalSlider = new JSlider(0, 360, 180);
        pane.add(horizontalSlider, BorderLayout.SOUTH);

        JSlider verticalSlider = new JSlider(SwingConstants.VERTICAL, -180, 180, 0);
        pane.add(verticalSlider, BorderLayout.EAST);


        JSlider zoomSlider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
        pane.add(zoomSlider, BorderLayout.WEST);

        // Example Shape 1, a tetrahedon:
        ArrayList<Triangle> triangles = new ArrayList<Triangle>(); // List representing a tetrahedon, a shape with four triangles

        triangles.add(new Triangle(new Point(100, 100, 100), new Point(-100, -100, 100), new Point(-100, 100, -100),
                Color.ORANGE));
        triangles.add(new Triangle(new Point(100, 100, 100), new Point(-100, -100, 100), new Point(100, -100, -100),
                Color.CYAN));
        triangles.add(new Triangle(new Point(-100, 100, -100), new Point(100, -100, -100), new Point(100, 100, 100),
                Color.PINK));
        triangles.add(new Triangle(new Point(-100, 100, -100), new Point(100, -100, -100), new Point(-100, -100, 100),
                Color.MAGENTA));


        // Example Shape 2, a cube:
        ArrayList<Square> cube = new ArrayList<Square>();
        cube.add(new Square(new Point(100, 100, 0), new Point(-100, 100, 0), new Point(-100, -100, 0), new Point(100, -100, 0), Color.BLUE));
        cube.add(new Square(new Point(100, 100, 0), new Point(100, 100, -200), new Point(100, -100, -200), new Point(100, -100, 0), Color.YELLOW));
        cube.add(new Square(new Point(-100, 100, -200), new Point(100, 100, -200), new Point(100, -100, -200), new Point(-100, -100, -200), Color.GREEN));
        cube.add(new Square(new Point(-100, 100, -200), new Point(-100, 100, 0), new Point(-100, -100, 0), new Point(-100, -100, -200), Color.RED));
        cube.add(new Square(new Point(-100, -100, -200), new Point(100, -100, -200), new Point(100, -100, 0), new Point(-100, -100, 0), Color.ORANGE));
        cube.add(new Square(new Point(-100, 100, -200), new Point(100, 100, -200), new Point(100, 100, 0), new Point(-100, 100, 0), Color.CYAN));

        // Example Shape 3, a pyramid:
        ArrayList<MyShape> pyramid = new ArrayList<MyShape>();
        pyramid.add(new Square(new Point(-100, -100, -200), new Point(100, -100, -200), new Point(100, -100, 0), new Point(-100, -100, 0), Color.ORANGE));
        pyramid.add(new Triangle(new Point(-100, -100, -200), new Point(100, -100, -200), new Point(0, 20, -100), Color.MAGENTA));
        pyramid.add(new Triangle(new Point(100, -100, -200), new Point(100, -100, 0), new Point(0, 20, -100), Color.CYAN));
        pyramid.add(new Triangle(new Point(100, -100, 0), new Point(-100, -100, 0), new Point(0, 20, -100), Color.GREEN));
        pyramid.add(new Triangle(new Point(-100, -100, 0), new Point(-100, -100, -200), new Point(0, 20, -100), Color.RED));


        JButton resetButton = new JButton();
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verticalSlider.setValue(0);
                horizontalSlider.setValue(180);
                zoomSlider.setValue(0);

            }
        });

        resetButton.setText("Reset");
        pane.add(resetButton, BorderLayout.NORTH);

        JPanel displayPanel = new JPanel() {

            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                double theta_to_rotate_by = Math.toRadians(horizontalSlider.getValue());

                Matrix xz_rotation_matrix = new Matrix(new double[]{ // XZ rotation
                        Math.cos(theta_to_rotate_by), 0, -Math.sin(theta_to_rotate_by),
                        0, 1, 0,
                        Math.sin(theta_to_rotate_by), 0, Math.cos(theta_to_rotate_by)
                });


                double pitch = Math.toRadians(verticalSlider.getValue());
                Matrix yz_rotation_matrix = new Matrix(new double[]{ // YZ rotation
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)
                });


                Matrix final_transform = xz_rotation_matrix.multiply(yz_rotation_matrix);


                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB); // For Colour.
//				g2.translate(getWidth()/2, getHeight()/2); Auto translate for wireframe


                double[] zBuffer = new double[img.getWidth() * img.getHeight()]; // Stores "depth" of every pixel (ex zBuffer[0] is the depth of (0,0) and zBuffer[-1] is depth of bottom right most pixel
                // initialize array with extremely far away depths to overwrite them later
                Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);


                // Replace the ArrayList in the loop header to render different shapes:
                for (MyShape triangle : cube) {

                    Point[] transformed_points = new Point[triangle.corners.length];

                    for (int i = 0; i < triangle.corners.length; i++) {

                        transformed_points[i] = final_transform.transform(triangle.corners[i]);

                        // Manual Translation to center vertices
                        transformed_points[i].x += getWidth() / 2;
                        transformed_points[i].y += getHeight() / 2;
                        if (zoomSlider.getValue() != 0) {
                            int zoomFactor = zoomSlider.getValue();

                            double centerX = getWidth() / 2;
                            double centerY = getHeight() / 2;


                            transformed_points[i].x /= zoomFactor;
                            transformed_points[i].x += getWidth()/4;
                            transformed_points[i].y /= zoomFactor;
                            transformed_points[i].y += getHeight()/4;

//                            if (transformed_points[i].x > centerX){
//                                transformed_points[i].x -= zoomFactor;
//                            } else if (transformed_points[i].x < centerX) {
//                                transformed_points[i].x += zoomFactor;
//                            }
//
//                            if (transformed_points[i].y > centerY){
//                                transformed_points[i].y -= zoomFactor;
//                            } else if (transformed_points[i].y < centerY) {
//                                transformed_points[i].y += zoomFactor;
//                            }
//
//                            if (transformed_points[i].z > 0){
//                                transformed_points[i].z /= zoomFactor;
//                            } else{
//                                transformed_points[i].z *= Math.abs(zoomFactor);
//                            }

                        }

                    }

                    // Wireframe part (not really needed anymore):

//					g2.setColor(Color.WHITE);
//
//				    Path2D path = new Path2D.Double();
//
//
//
//					path.moveTo(transformed_points[0].x, transformed_points[0].y);
//
//					for (int i = 1; i < transformed_points.length; i++) {
//						path.lineTo(transformed_points[i].x, transformed_points[i].y);
//					}
//				    path.closePath();
//				    g2.draw(path);


                    // Compute rectangular bounds for triangle to make rasterization slightly more efficient.
                    // This way we don't look at all the points on the screen but rather around the current polygon.

                    double biggestX = transformed_points[0].x;
                    double smallestX = transformed_points[0].x;
                    double biggestY = transformed_points[0].y;
                    double smallestY = transformed_points[0].y;

                    double maxZ = transformed_points[0].z;

                    for (Point tp : transformed_points) {

                        biggestX = Math.max(biggestX, tp.x);
                        smallestX = Math.min(smallestX, tp.x);

                        biggestY = Math.max(biggestY, tp.y);
                        smallestY = Math.min(smallestY, tp.y);

                        maxZ = Math.max(maxZ, tp.z);


                    }


                    // Lighting portion (note we are assuming the light source is always at (0,0,1)):

                    // Rough calculation of norm and angle made with light source at (0,0,1).
                    Point ab = new Point(transformed_points[1].x - transformed_points[0].x, transformed_points[1].y - transformed_points[0].y, transformed_points[1].z - transformed_points[0].z);
                    Point ac = new Point(transformed_points[2].x - transformed_points[0].x, transformed_points[2].y - transformed_points[0].y, transformed_points[2].z - transformed_points[0].z);


                    Point norm = new Point(ab.y * ac.z - ab.z * ac.y, ab.z * ac.x - ab.x * ac.z, ab.x * ac.y - ab.y * ac.x);

                    double normAbs = Math.sqrt((norm.x * norm.x) + (norm.y * norm.y) + (norm.z * norm.z));

                    norm.x /= normAbs;
                    norm.y /= normAbs;
                    norm.z /= normAbs;

                    double angleCos = Math.abs(norm.z);

                    int minX = (int) Math.max(0, Math.ceil(smallestX));
                    int minY = (int) Math.max(0, Math.ceil(smallestY));

                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(biggestX));
                    int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(biggestY));

                    // Rasterization
                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {

                            Point right_most = new Point(maxX + 1, y, 0);

                            // Checks if the current (x,y) lies in the current polygon, if so then colour it in.
                            int intersections = 0;
                            for (int i = 0; i < transformed_points.length; i++) {

                                Point next_poly_point = transformed_points[(i + 1) % transformed_points.length];

                                if (doIntersect(transformed_points[i], next_poly_point, new Point(x, y, 0), right_most)) {
                                    intersections += 1;
                                }


                            }

                            if ((intersections % 2 == 1)) {

                                // Gives depth perception

                                int zIndex = y * img.getWidth() + x;
                                if (zBuffer[zIndex] < maxZ) {
                                    img.setRGB(x, y, getShadedColour(triangle.colour, angleCos).getRGB());
                                    zBuffer[zIndex] = maxZ;
                                }
                            }
                        }
                    }

                    g2.drawImage(img, 0, 0, null);

                }

            }
        };


        horizontalSlider.addChangeListener(e -> displayPanel.repaint());
        verticalSlider.addChangeListener(e -> displayPanel.repaint());
        zoomSlider.addChangeListener(e -> displayPanel.repaint());

        pane.add(displayPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);

        frame.setTitle("3D Render Side Project");

        frame.setVisible(true);

    }
}

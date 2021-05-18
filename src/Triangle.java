import java.awt.Color;

public class Triangle extends MyShape{
	public Triangle(Point v1, Point v2, Point v3, Color colour) {
		
		this.corners = new Point[] {v1, v2, v3};
		this.colour = colour;
	}
}

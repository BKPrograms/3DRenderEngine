import java.awt.Color;

public class Square extends MyShape{
	
	public Square(Point v1, Point v2, Point v3, Point v4, Color colour) {
		this.corners = new Point[] {v1, v2, v3, v4};
		this.colour = colour;
	}

}

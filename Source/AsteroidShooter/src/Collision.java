//author: Anshul Bhagi

import java.awt.geom.*;  //for class Shape
import java.awt.*;       //for class Point and Rectangle
import java.util.*;      //for class Vector

//a class that is used to detect collision of any two shapes.
//This class is final in order to prevent the creation of any
//subclasses.
final class Collision{
	//detect collision between Shape a and Shape b
	public static boolean detect(Shape a, Shape b){
		//the points in the bounding rectangle of Shape a
		Point[] aPts = getPoints(a);
		//iterate through all the points in the array aPts, and
		//check if that point is included in the actual shape, and
		//not just in the bounding rectangle. If the point is included
		//in Shape a, as well as in Shape b, then there is a collision
		for (int i = 0; i < aPts.length; i++){
				if (a.contains(aPts[i]) && b.contains(aPts[i]))
					return true;
		}
		return false;
	}
	//return an array of all the points in the bounding rectangle of Shape s
	private static Point[] getPoints(Shape s){
		//the Rectangle representing the bounding box of Shape s
		Rectangle r = s.getBounds();
		//a vector which will store the points in Rectangle r
		Vector<Point> pts = new Vector<Point>();
		//the array of the points in Vector pts, this is
		//the array which is returned
		Point[] points;
		//iterate through all points in Rectangle r, and store
		//them in Vector pts
		for (int i = r.x; i <= r.x+r.width; i++){             //go through rows
			for (int j = r.y; j <= r.y+r.height; j++){        //go through columns
				pts.add(new Point(i,j));
			}
		}
		points = new Point[pts.size()];
		//copy the points from Vector pts into Point[] points
		pts.copyInto(points);
		return points;
	}
}
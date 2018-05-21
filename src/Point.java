import java.util.Vector;
import java.lang.Double;

/**
 * A representation of a point in Euclidean space. 
 * @author J. Marcus Hughes
 */
public class Point {
    private Vector<Double> coord;
    private int dimension;

    /** Build a point from arbitrarily many double values */
    Point(Double...  values) {
        this.dimension = values.length;
        this.coord = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            coord.addElement(values[i]);
        }
    }

    /** Built a point from arbitrarily many integer values */
    Point(Integer...  values) {
        this.dimension = values.length;
        this.coord = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            coord.addElement((double) values[i]);
        }
    }

    /** Built a point from a <code> Vector </code> of coordinate values */
    Point(Vector<Double> values) {
        this.coord = values;
        this.dimension = values.size();
    }

    /**
     * Number of coordinates in point
     * <p>
     * For example, (0,0) is dimension 2 and (0,0,0) is dimension 3
     * </p>
     */
    public int dimension() {
        return coord.size();
    }

    /** Returns the point as a vector of doubles */
    public Vector<Double> getCoord() {
        return this.coord;
    }

    /** 
     * Create a string version of a point 
     * <p> 
     * For example (3, 4, 5) 
     * </p>
     */
    public String toString() {
        String s = "(";
        for (Double v : coord) {
            s += v;
            s += ", ";
        }
        s = s.substring(0, s.length()-2);
        s += ")";
        return s;
    }

    /** 
     * Add a point to another point elementwise
     * @param other a point with the same dimension
     * @throws RuntimeException when points are not of the same dimension
     * @return the elementwise sum of points
     */
    public Point add(Point other) {
        if (other.dimension() != this.dimension()) {
            throw new RuntimeException("Points must have same dimension to add");
        }
        Vector<Double> result = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            result.addElement(this.coord.get(i) + other.coord.get(i));
        }
        return new Point(result);
    }

    /**
     * Scales a point by a scalar double 
     * @param k number to scale by
     */
    public Point scale(double k) {
        Vector<Double> result = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            result.addElement(this.coord.get(i) * k);
        }
        return new Point(result);
    }


    /**
     * Subtract other point
     * @param other point to subtract
     * @throws RuntimeException when points are not of the same dimension
     * @return this - other
     */
    public Point subtract(Point other) {
        return this.add(other.scale(-1.0));
    }

    /** 
     * Multiply points
     * @param other point to multiple
     * @throws RuntimeException when points are not of the same dimension
     * @return this * other
     */
    public Point multiply(Point other) {
        if (other.dimension() != this.dimension()) {
            throw new RuntimeException("Points must have same dimension to add");
        }
        Vector<Double> result = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            result.addElement(this.coord.get(i) * other.coord.get(i));
        }
        return new Point(result);
    }

    /** Squares */
    public Point square() {
        return this.multiply(this);
    }

    public double mean() {
        double sum = 0.0;
        for (double v : coord) {
            sum += v;
        }
        return sum / dimension;
    }
    
    /** testing method */
    public static void main(String[] args) {
        System.out.println("Testing for point");
        Point p = new Point(1, 2, 3);
        System.out.println(p);
    }
}

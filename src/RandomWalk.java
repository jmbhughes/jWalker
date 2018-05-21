import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import static java.lang.Math.sqrt;

/**
 * A generic representation of a random walk 
 * @author J. Marcus Hughes
 */
public abstract class RandomWalk {
    int dimension; // the number of coordinates in a point, e.g. (0,0) is dimension 2
    Vector<Point> path; // the collection of points traveled by the random walk

    /**
     * A generic random walk starting from <code> source </code> 
     * @param source where the random walk begins from
     */
    RandomWalk(Point source) {
        this.path = new Vector<Point>();
        this.path.addElement(source);
        this.dimension = source.dimension();
    }

    /** Take one step, i.e. move one iteration */
    abstract public void step();

    /** 
     * Take <code> steps </code> and return the path
     * @param steps number of movements to make
     * @return path of random walk
     */
    public Vector<Point> walk(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
        }
        return this.path;
    }

    /** Return path */
    public Vector<Point> getPath() {
        return this.path;
    }

    /** Number of points in path */
    public int length() {
        return this.path.size();
    }

    /** 
     * Calculates the center of mass
     * @throws RuntimeException if the path is empty
     */
    public Point centerOfMass() {
        if (this.length() == 0) {
            throw new RuntimeException("Path must be populated before calculating");
        }
        Vector<Double> point = new Vector<Double>();
        for (int i = 0; i < this.dimension; i++) {
            point.addElement(0.0);
        }
        Point sum = new Point(point);
        for (Point p : path) {
            sum = sum.add(p);
        }
        return sum.scale(1.0 / (1.0 + length()));
    }

    /**
     * Calculates the radius of gyration
     * @throws RuntimeException if the path is empty
     */
    public double radiusOfGyration() {
        Point center = centerOfMass();
        double sum = 0.0;
        for (Point p : path) {
            sum += p.subtract(center).square().mean();
        }
        return sqrt((1.0 / (length() + 1.0)) * sum);
    }

    /**
     * Number of unique points
     */
    public int numUnique() {
        Set<Point> s = new HashSet<Point>(path);
        return s.size();
    }
}

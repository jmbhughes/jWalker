import java.util.Vector;

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

    /** Perform a complete walk with the desired number of steps */
    abstract public Vector<Point> walk(int steps);

    /** Return path */
    public Vector<Point> getPath() {
        return this.path;
    }
}

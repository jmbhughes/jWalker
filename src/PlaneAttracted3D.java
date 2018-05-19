import java.util.Vector;
import java.util.Random;

public class PlaneAttracted3D extends RandomWalk {

    private double alpha;
    private Random rng; // random number generator
    private double p; // probability of moving when on plane
    private double q; // probability of moving when off plane, p' in paper

    /**
     * Only constructor for <code> PlaneAttracted3D </code>
     * @param alpha attraction parameter
     */
    PlaneAttracted3D(double alpha) {
        super(new Point(0,0,0));
        this.rng = new Random();

        // attraction to plane
        this.alpha = alpha;
        if (this.alpha < 1.0) {
            throw new RuntimeException("alpha must be greater than 1");
        }

        // parameters for movement
        this.p = 1.0 / (alpha + 5.0);
        this.q = 1.0 / (4.0 * alpha + 2.0);
    }

    /** Determine where to move next when on plane */
    private Point onPlane() {
        Point prev = this.path.lastElement(); 
        float p = this.rng.nextFloat(); // random number

        // determine next step based on probability and selection
        // on sum compared to generated p
        if (p < this.q) {
            return prev.add(new Point(0, 0, -1)); // move off plane
        } else if (p < 2.0 * this.q) {
            return prev.add(new Point(0, 0, 1));  // move off plane
        } else if (p < (2.0 + this.alpha) * this.q) {
            return prev.add(new Point(1, 0, 0));  // move in plane
        } else if (p < (2.0 + 2.0 * this.alpha) * this.q) {
            return prev.add(new Point(-1, 0, 0)); // move in plane
        } else if (p < (2.0 + 3.0 * this.alpha) * this.q) {
            return prev.add(new Point(0, 1, 0));  // move in plane
        } else {
            return prev.add(new Point(0, -1, 0)); // move in plane
        }
    }

    /** Determine java documentationwhere to move next when off plane */
    private Point offPlane() {
        Point prev = this.path.lastElement();
        float p = this.rng.nextFloat();

        // determine next step based on probability and selection
        // on sum compared to generated p        
        if (p < this.p) {
            return prev.add(new Point(1, 0, 0));
        } else if(p < 2.0 * this.p) {
            return prev.add(new Point(-1, 0, 0));
        } else if(p < 3.0 * this.p) {
            return prev.add(new Point(0, 1, 0));
        } else if(p < 4.0 * this.p) {
            return prev.add(new Point(0, -1, 0));
        } else if(p < 5.0 * this.p) {
            if (prev.getCoord().lastElement() < 0) {  // z < 0
                return prev.add(new Point(0, 0, -1)); // move away from plane
            } else {                                  // z > 0
                return prev.add(new Point(0, 0, 1));  // move toward plane
            }
        } else {
            if (prev.getCoord().lastElement() < 0) {  // z < 0
                return prev.add(new Point(0, 0, 1));  // move toward plane
            } else {                                  // z > 0
                return prev.add(new Point(0, 0, -1)); // move away from plane
            }
        }
    }

    /** Take one step */
    public void step() {
        Point prev = this.path.lastElement();
        Integer z = prev.getCoord().lastElement().intValue();
        
        if (z == 0) { // on plane
            this.path.addElement(onPlane());
        } else {      // z != 0, so off plane
            this.path.addElement(offPlane());
        }
    }

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

    /** Testing method */
    public static void main(String[] args) {
        PlaneAttracted3D walk = new PlaneAttracted3D(1.5);
        Vector<Point> path = walk.walk(100);
        for(Point p : path) {
            System.out.println(p);
        }        
    }
}

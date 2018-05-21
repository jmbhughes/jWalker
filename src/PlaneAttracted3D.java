import java.util.Vector;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    /** Testing method */
    public static void main(String[] args) {
        Random rng = new Random();
        int iterations = Integer.parseInt(args[0]);
        double alpha   = Double.parseDouble(args[1]);
        
        int iMin = 10000;
        int iMax = 1000000;
        int length = ThreadLocalRandom.current().nextInt(iMin, iMax);

        PlaneAttracted3D rw;
        
        for (int i = 0; i < iterations; i++) {
            length = ThreadLocalRandom.current().nextInt(iMin, iMax);
            rw = new PlaneAttracted3D(alpha);
            rw.walk(length);
            System.out.printf("%d %f \n", rw.numUnique(),  rw.radiusOfGyration());
        }        
    }
}

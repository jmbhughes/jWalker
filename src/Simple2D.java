import java.util.Vector;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Simple2D extends RandomWalk {
    private Random rng;

    /**
     * Only constructor for <code> Simple3D </code>
     * @param alpha attraction parameter
     */
    Simple2D() {
        super(new Point(0,0));
        this.rng = new Random();
    }
    
    /** Take one step */
    public void step() {
        Point prev = this.path.lastElement();
        float p = this.rng.nextFloat();

        // determine next step based on probability and selection
        // on sum compared to generated p        
        if (p < 1.0/4.0) {
            this.path.addElement(prev.add(new Point(1, 0)));
        } else if (p < 2.0/4.0) {
            this.path.addElement(prev.add(new Point(-1, 0)));
        } else if (p < 3.0/4.0) {
            this.path.addElement(prev.add(new Point(0, 1)));
        } else {
            this.path.addElement(prev.add(new Point(0, -1)));
        }
    }

    /** Testing method */
    public static void main(String[] args) {
        int iterations = Integer.parseInt(args[0]);
        
        int iMin = 10000;
        int iMax = 1000000;
        int length = ThreadLocalRandom.current().nextInt(iMin, iMax);

        Simple2D rw;
        
        for (int i = 0; i < iterations; i++) {
            length = ThreadLocalRandom.current().nextInt(iMin, iMax);
            rw = new Simple2D();
            Vector<Point> ww = rw.walk(length);
            // if (i == 0) {
            //     for(Point p : ww) {
            //         System.out.println(p);
            //     }
            //     System.out.println("-----");
            // }
            System.out.printf("%d %f \n", rw.numUnique(),  rw.radiusOfGyration());
        }        
    }
}

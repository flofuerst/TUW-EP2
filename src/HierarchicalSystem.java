import codedraw.CodeDraw;

// A cosmic system that is composed of a central named body (of type 'NamedBodyForcePair')
// and an arbitrary number of subsystems (of type 'CosmicSystem') in its orbit.
// This class implements 'CosmicSystem'.
//
public class HierarchicalSystem implements CosmicSystem, MassiveIterable {
    private String name;
    private NamedBodyForcePair central;
    private CosmicSystem[] planetsInOrbit;

    // Initializes this system with a name and a central body.
    public HierarchicalSystem(NamedBodyForcePair central, CosmicSystem... inOrbit) {
        name = central.getName();
        this.central = central;
        planetsInOrbit = inOrbit;
    }

    // Returns a readable representation of this system.
    public String toString() {
        String output = central.toString();

        for (int i = 0; i < planetsInOrbit.length; i++) {
            if (i == 0) output += " {";
            if (i >= planetsInOrbit.length - 1) {
                output += planetsInOrbit[i] + "}";
            } else {
                output += planetsInOrbit[i] + ", ";
            }
        }

        return output;
    }

    // Returns the mass center of this system.
    public Vector3 getMassCenter() {
        //es müssen daher alle Positionen mit der jeweiligen Masse multipliziert und aufsummiert werden und das Resultat
        // durch die Summe aller Massen dividiert werden.

        //calculate sum of weighted massCenter of all planets
        Vector3 sum = central.getMassCenter().times(central.getMass());
        for (int i = 0; i < planetsInOrbit.length; i++) {
            sum = sum.plus(planetsInOrbit[i].getMassCenter());
        }

        //divide sum through overall mass
        return sum.times(1 / getMass());
    }

    // Returns the overall mass of this system.
    public double getMass() {
        double mass = central.getMass();

        for (int i = 0; i < planetsInOrbit.length; i++) {
            mass += planetsInOrbit[i].getMass();
        }

        return mass;
    }

    // Returns the overall number of bodies contained in this system.
    public int numberOfBodies() {

        //add central Body to numberOfBodies
        int bodyNumber = 1;

        ////loop through planets in orbit and recursively add numberOfBodies
        for (int i = 0; i < planetsInOrbit.length; i++) {
            bodyNumber += planetsInOrbit[i].numberOfBodies();
        }
        return bodyNumber;
    }

    // Returns the distance between the mass centers of 'this' and the specified system.
    //Precondition: cs != null
    public double distanceTo(CosmicSystem cs) {
        return this.getMassCenter().distanceTo(cs.getMassCenter());
    }

    // Adds the force that the specified body exerts on each of this systems bodies to each of this
    // systems bodies.
    // Precondition: b != null
    public void addForceFrom(Body b) {
        central.addForceFrom(b);
        for (int i = 0; i < planetsInOrbit.length; i++) {
            planetsInOrbit[i].addForceFrom(b);
        }
    }

    // Adds the force that this system exerts on each of the bodies of 'cs' to the bodies in 'cs'.
    // For exact computations this means that for each body of 'this' its force on each body of
    // 'cs' is added to this body of 'cs'.
    // Precondition: cs != null
    public void addForceTo(CosmicSystem cs) {
        central.addForceTo(cs);
        for (int i = 0; i < planetsInOrbit.length; i++) {
            planetsInOrbit[i].addForceTo(cs);
        }
    }

    // Returns a list with all the bodies of 'this'. The order is not defined.
    public BodyLinkedList getBodies() {
        //create list with central Body
        BodyLinkedList list = central.getBodies();

        //loop through planets in orbit
        for (int i = 0; i < planetsInOrbit.length; i++) {
            //recursively create tempList
            BodyLinkedList tempList = planetsInOrbit[i].getBodies();

            //'merge' tempList with list
            while (tempList.size() >= 1) {
                list.addLast(tempList.pollLast());
            }
        }
        return list;
    }

    public void makeCentralBodies() {
        name = "Central " + central.getName();
        for (int i = 0; i < planetsInOrbit.length; i++) {
            planetsInOrbit[i].makeCentralBodies();

        }
    }

    // Moves each of the bodies of 'this' according to the previously accumulated forces and
    // resets all forces to zero.
    public void update() {
        central.update();
        for (int i = 0; i < planetsInOrbit.length; i++) {
            planetsInOrbit[i].update();
        }
    }

    //draws the objects into the canvas 'cd'
    //Precondition: cd != null
    public void draw(CodeDraw cd) {
        central.draw(cd);

        for (int i = 0; i < planetsInOrbit.length; i++) {
            planetsInOrbit[i].draw(cd);
        }
    }

    @Override
    public MassiveIterator iterator() {
        return new HierarchicalSystemIterator(this);
    }

    class HierarchicalSystemIterator implements MassiveIterator {

        private HierarchicalSystem hierarchSys;
        private Massive nextElement;
        private int index = 0;
        private MassiveIterator iterator;
        private CosmicSystem nextCosmicSys;

        public HierarchicalSystemIterator(HierarchicalSystem sys) {
            hierarchSys = sys;
            nextElement = sys.central;
        }

        private Massive retrieveNextElement() {
            if (iterator != null) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                iterator = null;
            }

            //check if index is greater than length of array
            if (index >= hierarchSys.planetsInOrbit.length) return null;

            //get next CosmicSystem
            nextCosmicSys = hierarchSys.planetsInOrbit[index++];

            //return nextCosmicSystem
            if (nextCosmicSys instanceof Massive) return (Massive) nextCosmicSys;

            //set iterator of nextCosmicSystem
            iterator = ((HierarchicalSystem) nextCosmicSys).iterator();

            //return next Element if iterator is not null
            if (iterator != null) return iterator.next();
            else return null;
        }

        @Override
        public Massive next() {
            Massive tempNext = nextElement;

            //get nextElement
            nextElement = retrieveNextElement();

            return tempNext;
        }

        @Override
        public boolean hasNext() {
            return nextElement != null;
        }
    }

}



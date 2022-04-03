import codedraw.CodeDraw;

import java.awt.*;
import java.util.Random;

// Simulates the formation of a massive solar system.
//
public class Simulation3 {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9; // meters

    // one light year
    public static final double LY = 9.461e15; // meters

    // some further constants needed in the simulation
    public static final double SUN_MASS = 1.989e30; // kilograms
    public static final double SUN_RADIUS = 696340e3; // meters
    public static final double EARTH_MASS = 5.972e24; // kilograms
    public static final double EARTH_RADIUS = 6371e3; // meters

    // set some system parameters
    public static final double SECTION_SIZE = 2 * AU; // the size of the square region in space
    public static final int NUMBER_OF_BODIES = 22;
    public static final double OVERALL_SYSTEM_MASS = 20 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.


    public static void main(String[] args) {
        // simulation
        CodeDraw cd = new CodeDraw();
        BodyLinkedList bodies = new BodyLinkedList();
        BodyForceTreeMap forceOnBody = new BodyForceTreeMap();

        Random random = new Random(2022);
        for (int i = 0; i < NUMBER_OF_BODIES; i++) {
            bodies.addLast(new Body(Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES, // kg
                    new Vector3(0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU),
                    new Vector3(0 + random.nextGaussian() * 5e3, 0 + random.nextGaussian() * 5e3, 0 + random.nextGaussian() * 5e3)));

        }

        double seconds = 0;

        // simulation loop
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            BodyLinkedList mergedBodies = new BodyLinkedList();
            Body checkColliding;

            //get each body from bodies and merge with colliding bodies and save to new list
            while ((checkColliding = bodies.pollFirst()) != null) {

                //save colliding bodies to new list
                BodyLinkedList mergedList = bodies.removeCollidingWith(checkColliding);
                Body bodyToMerge;

                //merge all colliding bodies from list with according body
                while ((bodyToMerge = mergedList.pollFirst()) != null) {
                    checkColliding = checkColliding.merge(bodyToMerge);
                }
                //add merged body to new list
                mergedBodies.addFirst(checkColliding);
            }
            //save merged body list to bodies
            bodies = mergedBodies;

            BodyLinkedList chosenBodyList = new BodyLinkedList(bodies);
            Body chosenBody;

            //poll body from chosenBodylist and check if it is not null
            while ((chosenBody = chosenBodyList.pollFirst()) != null) {
                //create new Vector3 where forces are added to
                //NOTE: constantly call of forceOnBody was very inefficient before!

                Vector3 force = new Vector3();

                //create new list
                BodyLinkedList otherBodyList = new BodyLinkedList(bodies);
                Body otherBody;

                //poll elements from list
                while ((otherBody = otherBodyList.pollFirst()) != null) {
                    if (chosenBody != otherBody) {
                        //add forces from the bodies to force
                        force = force.plus(chosenBody.gravitationalForce(otherBody));
                    }
                }
                //put calculated force value to according body key
                forceOnBody.put(chosenBody, force);
            }
            //forceOnBody map now holds all forces exterted on their bodies


            //for each body: move it according to the total force exerted on it.
            //reused 'chosenBodyList' and 'chosenBody' for more clarity in code
            chosenBodyList = new BodyLinkedList(bodies);
            while ((chosenBody = chosenBodyList.pollFirst()) != null) {
                chosenBody.move(forceOnBody.get(chosenBody));
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                chosenBodyList = new BodyLinkedList(bodies);
                while ((chosenBody = chosenBodyList.pollFirst()) != null) {
                    chosenBody.draw(cd);
                }
                // show new positions
                cd.show();
            }
        }
    }
}

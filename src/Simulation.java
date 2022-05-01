import codedraw.CodeDraw;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/*
    Antwort auf Zusatzfragen:
    1.) Datenkapselung: Unter Datenkapselung versteht man das Zusammenfassen von Variablen und Methoden zu einem Objekt.
    In der Klasse Vector3 wurden die Koordinaten und deren zugehörigen Methoden zu einer Klasse zusammengefasst

    2.) Data Hiding: Bei Data Hiding existieren unterschiedliche Sichtbarkeiten von Methoden und Variablen.
    Man versucht zwecks Wartbarkeit soviele Methoden und Variablen private zu machen, wie nur möglich (public nur
    wo unbedingt nötig).
    Bei der Klasse Vector3 sind die Daten jetzt private und auf sie wird jetzt nur mehr innerhalb der Klasse
    zugegriffen bzw. werden diese nur mehr durch Objektmethoden verändert.

    3.) Vor dem Punkt steht die Instanz einer Klasse. Falls es sich um statische Methoden(Klassenmethoden)
     oder Attribute handelt, so steht vorm Punkt die Klasse selbst. Bei Objektmethoden steht vor dem Punkt nie
     der Klassenname selbst, sondern ein Variablen- oder Methodenname, welcher grundsätzlich klein geschrieben wird.
 */

// Simulates the formation of a massive solar system.
public class Simulation {

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
    public static final double SECTION_SIZE = 10 * AU; // the size of the square region in space
    public static final int NUMBER_OF_BODIES = 22;
    public static final double OVERALL_SYSTEM_MASS = 20 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.


    public static void main(String[] args) {
        // simulation
        CodeDraw cd = new CodeDraw();
//        Body[] bodies = new Body[NUMBER_OF_BODIES];
        BodyQueue bodies = new BodyQueue(NUMBER_OF_BODIES);
//        Vector3[] forceOnBody = new Vector3[bodies.length];
//        BodyForceMap forceOnBody = new BodyForceMap(NUMBER_OF_BODIES);
        HashMap<Body,Vector3> forceOnBody = new HashMap<Body, Vector3>();

        Random random = new Random(2022);
        for (int i = 0; i < NUMBER_OF_BODIES; i++) {
            bodies.add(new Body(Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES, // kg
                    new Vector3(0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU),
                    new Vector3(0 + random.nextGaussian() * 5e3, 0 + random.nextGaussian() * 5e3, 0 + random.nextGaussian() * 5e3)));

        }

        for (int i = 0; i < bodies.size(); i++) {
            Body check = bodies.poll();
            bodies.add(check);
            System.out.println("Die minimale Distanz ist: " + check.getMinimalDistance(bodies));
        }

        /* Zusatzaufgabe:
        bodies.add(new Body(1.989e30, new Vector3(0, 0, 0), new Vector3(0, 0, 0)));
        bodies.add(new Body(5.972e24, new Vector3(-1.394555e11, 5.103346e10, 0), new Vector3(-10308.53, -28169.38, 0)));
        bodies.add(new Body(3.301e23, new Vector3(-5.439054e10, 9.394878e9, 0), new Vector3(-17117.83, -46297.48, -1925.57)));
        bodies.add(new Body(4.86747e24, new Vector3(-1.707667e10, 1.066132e11, 2.450232e9), new Vector3(-34446.02, -5567.47, 2181.10)));
        bodies.add(new Body(6.41712e23, new Vector3(-1.010178e11, -2.043939e11, -1.591727E9), new Vector3(20651.98, -10186.67, -2302.79)));
         */
        double seconds = 0;

        // simulation loop
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // merge bodies that have collided
            /*
            for (int i = 0; i < bodies.length; i++) {
                for (int j = i + 1; j < bodies.length; j++) {
                    if (bodies[j].distanceTo(bodies[i]) < bodies[j].radius() + bodies[i].radius()) {
                        bodies[i] = bodies[i].merge(bodies[j]);
                        Body[] bodiesOneRemoved = new Body[bodies.length - 1];
                        for (int k = 0; k < bodiesOneRemoved.length; k++) {
                            bodiesOneRemoved[k] = bodies[k < j ? k : k + 1];
                        }
                        bodies = bodiesOneRemoved;

                        // since the body index i changed size there might be new collisions
                        // at all positions of bodies, so start all over again
                        i = -1;
                        j = bodies.length;
                    }
                }
            }
            */

            // for each body: compute the total force exerted on it.
            BodyQueue chosenQueue = new BodyQueue(bodies);
            BodyQueue otherQueue = new BodyQueue(bodies);
            Body chosenBody, otherBody;
            for (int i = 0; i < NUMBER_OF_BODIES; i++) {
                chosenBody = chosenQueue.poll();
                if (chosenBody == null) break;
                forceOnBody.put(chosenBody, new Vector3()); //init value with zero

                // use other queue to cycle through
                otherBody = otherQueue.poll();
                for (int x = 0; x < NUMBER_OF_BODIES; x++) {
                    if(chosenBody!=otherBody){
                        Vector3 forceToAdd = chosenBody.gravitationalForce(otherBody);

                        forceOnBody.put(chosenBody, forceOnBody.get(chosenBody).plus(forceToAdd));
                    }

                    //add otherQueue-body again, because it was removed before loop
                    otherQueue.add(otherBody);
                    //use other body of otherQueue for next iteration
                    otherBody = otherQueue.poll();
                }
                //add otherQueue-body again, because it was removed at the end of the loop
                otherQueue.add(otherBody);
            }
            //forceOnBody map now holds all forces exterted on their bodies

            // for each body: move it according to the total force exerted on it.
            chosenQueue = new BodyQueue(bodies);
            for (int i = 0; i < NUMBER_OF_BODIES; i++) {
                chosenBody = chosenQueue.poll();
                if(chosenBody == null)break;
                chosenBody.move(forceOnBody.get(chosenBody));
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                chosenQueue = new BodyQueue(bodies);
                for (int i = 0; i < NUMBER_OF_BODIES; i++) {
                    chosenBody = chosenQueue.poll();
                    if(chosenBody == null)break;
                    chosenBody.draw(cd);
                }
                // show new positions
                cd.show();
            }
        }
    }
}

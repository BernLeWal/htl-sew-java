package oop.classes;

/**
 * TrainingPlannerDemo shows the first use of an instance of a class
 */
public class TrainingPlannerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // add your own code for the application logic here
        Training t1 = new Training(2019, 1, 3, 0, 1000, 22, 18, "ok");

        System.out.println("Training am " + t1.getDatumTag() + "." + t1.getDatumMonat() + "." + t1.getDatumJahr() +
                " " + t1.getLaenge() + " Meter in " + t1.getDauerMin() + ":" + t1.getDauerSec());
    }

}

package patterns.producerconsumer;

/**
 * CafeteriaSimulatorDemo demonstrates the usage of the producer/consumer-pattern.
 * The Producer (here PeopleProducer) generates tasks (Person) which are put in a queue (Queue).
 * Two consumer Threads (here Cafeteria) pick one person after another and executes the tasks.
 */
public class CafeteriaSimulatorDemo {
    public static void main(String[] args) {
        Queue queue = new Queue(3);

        // create the producers
        PeopleProducer[] classes = {
                new PeopleProducer("4A", queue, new String[]{"Ali","Bea","Ceci","Dora","Emil","Fin","Guy","Helle","Ida","Jana","Kay","Lu","Mia","Nam","Olga","Pia","Qed","Ron","Sue","Tom","Ulli","Vic","Willi","Xaver","Ypsilon","Zed"}),
                new PeopleProducer("3A", queue, new String[]{"Ahmed","Bora","Cyl","Don","Eve","Franz","God","Harry","Ivy","Jon","Karl","Lee","Mat","Norbert","Otto","Paul","Qu","Raul","Sima","Thor","Ulf","Valentin","Wilhelm","Xen","Yps","Zue"}),
                new PeopleProducer("3B", queue, new String[]{"Bauer","Meier","Müller","Schilling","Mayer","Mayr","Meir"}),
        };

        // create the consumer
        Cafeteria[] cafeterias = {
                new Cafeteria("Buffet", queue),
                new Cafeteria("Mensa", queue),
        };

        // start the threads
        for( PeopleProducer cls : classes )
            cls.start();
        for( Cafeteria toi : cafeterias)
            toi.start();


        // wait until all threads are finished
        try {
            for (PeopleProducer cls : classes)
                cls.join();
            for (Cafeteria toi : cafeterias)
                toi.join();
        } catch (InterruptedException e) {
            // IGNORED
        }
    }
}

package core.io;

import core.data.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ObjectStreamDemo shows how to easily transfer complete objects between Object-Streams.
 * Remark: the objects need to implement the Serializable interface in order to be enabled for transferation.
 */
public class ObjectStreamDemo {

    public static void main(String[] args) {
        Person meinObj = new Person(1, "Anton", "Bauer", 25);
        
        try {
            // ObjectoutputStream erzeugen
            ByteArrayOutputStream baos = new ByteArrayOutputStream();   // schreibt in den Speicher
            ObjectOutputStream out = new ObjectOutputStream(baos);
            
            // Mein Objekt schreiben
            out.writeObject(meinObj );
            out.close();
            
            // ObjectInputStream erzeugen
            ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
            ObjectInputStream in = new ObjectInputStream(bais);
            
            // Mein Objekt lesen
            Person meineKopie = (Person)in.readObject();
            System.out.println(meineKopie);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}

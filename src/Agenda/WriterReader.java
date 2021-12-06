/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ferna
 */
public class WriterReader {

    public void escribirObjeto(Object objeto, String file) throws IOException {
        FileOutputStream f = new FileOutputStream(new File(file));
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(objeto);
        o.flush();
        o.close();
        f.close();
    }

    public Object leerObjeto(String file) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(file));
        ObjectInputStream oi = new ObjectInputStream(fi);
        Object objeto = (Object) oi.readObject();
        oi.close();
        fi.close();
        return objeto;
    }
}

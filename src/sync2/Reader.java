/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Reader implements Runnable {

    private Thread thread;
    boolean run = true;
    boolean suspendFlag;
    String currentRow;
    LinkedList list;
    writer writer;
    
    

    public Reader(LinkedList _list) {
        super();
        list = _list;
    
        thread = new Thread(this);
        suspendFlag = false;
    

    }

    public writer getWriter() {
        return writer;
    }

    public void setWriter(writer writer) {
        this.writer = writer;
    }
    
    

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void resumeThread() {
        suspendFlag = false;
        synchronized (list) {
            list.notify();
        }

    }

    public void suspendThread() {
        suspendFlag = true;
    }

    @Override
    public void run() {
        File file = new File("src/sync2/test.csv");
        while (run) {//??

            try {

                try (BufferedReader instream = new BufferedReader(new java.io.FileReader(
                        file))) {
                    while ((currentRow = instream.readLine()) != null) {

                        list.add(currentRow);
                        //??suspendThread();
                        writer.resumeThread();
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                synchronized (list) {
                    if (suspendFlag) {
                        list.wait();
                    }
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}

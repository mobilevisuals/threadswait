/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class writer implements Runnable {

    private Thread thread;
    boolean run = true;
    boolean suspendFlag;
    String currentRow;//??z-  qi
    LinkedList list;
    Reader reader;

    public writer(LinkedList _list, Reader _reader) {
        super();
        list = _list;
        reader=_reader;
        thread = new Thread(this);
        suspendFlag = false;
       

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
        File file = new File("src/sync2/write1.csv");
        while (run) {//??

            try {

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(currentRow);
                    //suspendThread();
                    reader.resumeThread();

                } catch (IOException e) {
                    e.printStackTrace();
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

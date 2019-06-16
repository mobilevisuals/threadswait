/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync2;

import java.util.LinkedList;

/**
 *
 * @author eyvind
 */
public class Sync2 {

    public static void main(String[] args) {
        // TODO code application logic here
        LinkedList list=new LinkedList();
        Reader r=new Reader(list);
        
        writer w=new writer(list,r);
        r.setWriter(w);
        r.getThread().start();
        w.getThread().start();
        
    }
    
}

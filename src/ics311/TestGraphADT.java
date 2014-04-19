/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ics311;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This is the test driver that help developer to 
 * debug and test the graph functions.
 * 
 * 
 * @author      Vinson Gao
 * @version     1.0       
 * @since       2014-04-18
 */
public class TestGraphADT <Key extends Comparable<Key>, Data>{

    /**
     * main method that starts to run the program 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    throws IOException {
        ArcGraph graph = new ArcGraph();
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);  // uncomment this line to get open input for test file.
        
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to Vinson Gao's Graph Testing");
        System.out.println("Testing..... ");
        //print out what i gonna input into the graph
        System.out.println("inserts: c");
        System.out.println("inserts: g");
        System.out.println("inserts: f");
        System.out.println("inserts: h");
        System.out.println("inserts: d");
        System.out.println("inserts: b");
        System.out.println("inserts: a");
        System.out.println("inserts: e");
        System.out.println("inserts arc: a->b  weight:1");
        System.out.println("inserts arc: e->a  weight:1");
        System.out.println("inserts arc: e->f  weight:1");
        System.out.println("inserts arc: b->c  weight:1");
        System.out.println("inserts arc: c->d  weight:1");
        System.out.println("inserts arc: d->c  weight:1");
        System.out.println("inserts arc: b->e  weight:1");
        System.out.println("inserts arc: f->g  weight:1");
        System.out.println("inserts arc: g->f  weight:1");
        System.out.println("inserts arc: c->g  weight:1");
        System.out.println("inserts arc: b->f  weight:1");
        System.out.println("inserts arc: a->b  weight:1");
        

        // input nodes into the ArcGraph object
        graph.insertVertex("c");
        graph.insertVertex("g");
        graph.insertVertex("f");
        graph.insertVertex("h");
        graph.insertVertex("d");
        graph.insertVertex("b");
        graph.insertVertex("a");
        graph.insertVertex("e");
        graph.insertArc("a", "b");
        graph.insertArc("e", "a");
        graph.insertArc("e", "f");
        graph.insertArc("b", "c");
        graph.insertArc("c", "d");
        graph.insertArc("d", "c");
        graph.insertArc("b", "e");
        graph.insertArc("f", "g");
        graph.insertArc("g", "f");
        graph.insertArc("c", "g");
        graph.insertArc("b", "f");
        graph.insertArc("d", "h");
        graph.insertArc("g", "h");
        graph.insertArc("h", "h");
        System.out.println("");
        
        // insert arc weight
        graph.setArcWeight("a", "b", 1.0);
        graph.setArcWeight("e", "a", 1.0);
        graph.setArcWeight("e", "f", 1.0);
        graph.setArcWeight("b", "c", 1.0);
        graph.setArcWeight("c", "d", 1.0);
        graph.setArcWeight("d", "c", 1.0);
        graph.setArcWeight("b", "e", 1.0);
        graph.setArcWeight("f", "g", 1.0);
        graph.setArcWeight("g", "f", 1.0);
        graph.setArcWeight("c", "g", 1.0);
        graph.setArcWeight("b", "f", 1.0);
        graph.setArcWeight("d", "h", 1.0);
        graph.setArcWeight("g", "h", 1.0);
        graph.setArcWeight("h", "h", 1.0);
        System.out.println("");
        System.out.println("Grahic Arc inserted");
        graph.print();
        
        // start to test my methods
        System.out.println("Method Checking......");
        System.out.println("");
        String g = "g";
        String e = "e";
        String a = "a";
        String h = "h";
        
        //test insertion method
        System.out.println("check arcExit: "+e+"->"+a+"  "+ graph.arcExists(g,h));
        System.out.println("check vertexExit: "+g+"   "+graph.vertexExists(g));
        System.out.println("delete vertex: "+g);
        graph.removeVertex(g);
        System.out.println("delete arc: "+e+"->"+a);
        graph.removeArc(e, a);
        System.out.println("check vertexExit: "+g+"   "+graph.vertexExists(g));
        System.out.println("check arcExit: "+e+"->"+a+"  "+ graph.arcExists(e,a));
        
        
        System.out.print("Print out all vertices:  ");
        Iterator iii = graph.vertices();
        while(iii.hasNext())   {  System.out.print(iii.next()+",");}
        System.out.println("");
        
        // test adj list methods
        System.out.print("check inAdjacentVertices of b :  is/are ");
        Iterator i = graph.inAdjacentVertices("b");
        while(i.hasNext())   {  System.out.print(i.next());}
        System.out.println("");
        
        
        System.out.print("check outAdjacentVertices of b:  is/are ");
        Iterator ii = graph.outAdjacentVertices("b");
        while(ii.hasNext())   {  System.out.print(ii.next()+",");}
        System.out.println("");
        
        // test annotation methods
        System.out.println("Insert a annotation of b: color = white ");
        graph.setAnnotation("b","color", "white");
        System.out.println("print the inserted annotation: "+graph.getAnnotation("b","color"));
        
        System.out.println("clear all annotation with color");
        graph.clearAnnotations("color");
        System.out.println("print the annotation color of b: "+graph.getAnnotation("b","color"));
          
        System.out.println("");
       
    
        System.exit(0);
 
    }
   
}

          //ArcGraph graph = new ArcGraph(file);
          

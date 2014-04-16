/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ics311;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author vin
 */
public class Ics311project2 <Key extends Comparable<Key>, Data>{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    throws IOException {
        // TODO Auto-generated method stub
        //Scanner sc = new Scanner(System.in);  // uncomment this line to get open input for test file.
        
       // System.out.print("Enter graph input file name: ");
       // String file = sc.nextLine();
        //DirectedGraph graph = new DirectedGraph("friendship.txt");
        //DirectedGraph graph1 = new DirectedGraph("celegansneural.vna");
        ArcGraph graph = new ArcGraph();
        //graph.reverseDirection("290", "44");
        //graph.removeVertex("290");
        //System.out.println("arc weight:"+ graph.getArcWeight("290", "44"));
        //graph.setArcWeight("290", "44", 100.00);
        //System.out.println("new arc weight:"+ graph.getArcWeight("290", "44"));
       graph.print();
       // graph.printStaticReport();
        
        System.out.println("Minimum In & Out Degree: "+ graph.minInDegree()+" & "+graph.minOutDegree());
        System.out.println("Average Degree: "+graph.avergeDegree());
        System.out.println("Maximum In & Out Degree: "+ graph.maxInDegree()+" & "+graph.maxOutDegree());
        System.out.println("Network Diameter: "+"");
        System.out.println("Graph Density: "+graph.graphDensity());
        //graph.averagePathLength();
        //graph.test();
 
    }
  


    
    //question: if the ID string or number would be matter?
}

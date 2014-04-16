/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ics311;

import java.io.IOException;
import java.security.Key;
import java.util.Iterator;

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
        //SCC(graph);
        //graph.print();
        System.out.println("Minimum In & Out Degree: "+ graph.minInDegree());
        System.out.println("Average Degree: "+graph.avergeDegree());
        System.out.println("Maximum In & Out Degree: "+ graph.maxInDegree());
        System.out.println("Network Diameter: "+graph.diameter());
        System.out.println("Graph Density: "+graph.graphDensity());
        
       
        
        //graph.averagePathLength();
        //graph.test();
 
    }
    public static void SCC(ArcGraph G){
        DFS(G);
        G.transposeGraph();
        ArcGraph G_prime = G;
        DFS(G_prime);
        
    }
    
    public static int time;
    public static void DFS(ArcGraph G){
        Iterator i =G.vertices();
        while(i.hasNext()){
            String key = (String)i.next();
            G.setAnnotation(key,(Object)"color",(Object)"WHITE");
            G.setAnnotation(key,(Object)"parent",(Object)"");
            //System.out.println(graph.getAnnotation(key, "parent"));
        }
        time=0;
        Iterator ii =G.vertices();
        while(ii.hasNext()){
            String key = (String)ii.next();
            if(G.getAnnotation(key, "parent").equals("WHITE")){
                 DFS_VISIT(G,key);
            }
        }
   
    }
     public static void DFS_VISIT(ArcGraph G, String u){
        time = time+1;
        G.setAnnotation(u,(Object)"discover",(Object)time);
        G.setAnnotation(u,(Object)"color",(Object)"GRAY");
        Iterator i = G.allAdjacentVertices(u);
        while(i.hasNext()){
            String v = (String)i.next();
            if(G.getAnnotation(v,(Object)"color").equals("WHITE")){
                G.setAnnotation(v,(Object)"parent",(Object)u);
                DFS_VISIT(G, v);
            }
        }
        G.setAnnotation(u,(Object)"color",(Object)"BLACK");
        time= time+1;
        G.setAnnotation(u,(Object)"finish",(Object)time);
        
       
    }
  


    
    //question: if the ID string or number would be matter?
}

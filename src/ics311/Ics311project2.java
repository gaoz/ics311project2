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
 *
 * @author vin
 */
public class Ics311project2 <Key extends Comparable<Key>, Data>{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    throws IOException {
        ArcGraph graph = new ArcGraph();
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);  // uncomment this line to get open input for test file.
        
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to Vinson Gao's Graph Implementation");
        System.out.println("");
        System.out.printf("%-1s",  "*******Example Graph List:******\n" );
        System.out.printf("%-5s",  "#1. " );

        System.out.printf("%-1s",  "celegansneural.vna\n" );
         System.out.printf("%-5s",  "#2. " );
        System.out.printf("%-1s",  "political-blogs.vna\n" );
         System.out.printf("%-5s",  "#3. " );
        System.out.printf("%-1s",  "wiki-Vote.vna\n" );
         System.out.printf("%-5s",  "#4. " );
        System.out.printf("%-1s",  "cit-HepTh.vna\n" );
         System.out.printf("%-5s",  "#5. " );
        System.out.printf("%-1s",  "email-Enron.vna\n" );
         System.out.printf("%-5s",  "#6. " );
        System.out.printf("%-1s",  "ti-full.vna\n" );
        System.out.printf("%-1s",  "************Input:***********\n" );
        System.out.println("Enter graph input file name: ");
        
        String file="";
        if(sc.hasNext()){
            file = sc.nextLine();
        }else{
            System.out.print("please enter a file name example: celegansneural.vna");
        }
        String temp=""; 
        File f = new File(file);
        try{
            sc = new Scanner(f);
            //Scanner sc = new Scanner(new File("political-blogs.vna"));
            //Scanner sc = new Scanner(new File("celegansneural.vna"));
            //Scanner sc = new Scanner(new File("test.vna"));
          
            if(sc.nextLine().contains("*")){

                // ArrayList<String> labels = new ArrayList<String>();
                String line = sc.nextLine();
                String[] tokens = stringToArray(line);


                while(sc.hasNext()){
                //  System.out.println("successfully readin file:"+ sc.hasNext());
                    temp = sc.nextLine(); 
                    if(temp.startsWith("*")){
                        break;
                    }else{
                        // ADD VERTEX INTO MY LIST
                        //String[] itr = temp.split(" "); 
                        Hashtable<String, String> attributeTable = new Hashtable<String, String>();
                        String[] tokens2 = stringToArray(temp);
                        for(int i=1; i<tokens.length; i++){
                            attributeTable.put(tokens[i],tokens2[i]);
                            //System.out.println(tokens2[i]);
                        }
                         //(Object)attributeTable;
                        //verticList.put((Key)tokens2[0], new Vertex(tokens2[0],data,new Hashtable<Key,Arc>(),new Hashtable<Key,Arc>()));
                        //verticList.put((Key)itr[0], new Vertex(itr[0],new Hashtable<Key,Arc>()));
                        graph.insertVertex((String)tokens2[0]);
                    }

                }
            }
            if(temp.contains("*")){

                    int num = 0;
                    String[] tokens = stringToArray(sc.nextLine());
                    for(int j=0; j<tokens.length; j++){
                        if(tokens[j].contains("strength")){
                            num= j;
                        }
                    }
                    while(sc.hasNext()){
                        String line = sc.nextLine(); 
                        String[] tokens2 = stringToArray(line);

                        Double weight=1.00;
                        if(num>1){  // check whether there are weight in the input file
                            weight = Double.parseDouble(tokens2[num]);
                        }
                        Hashtable<String, String> attributeTable = new Hashtable<String, String>();

                            for(int i=2; i<tokens.length; i++){
                                attributeTable.put(tokens[i],tokens2[i]);
                            }
                            //Data data = (Data)attributeTable;
                            
                        String source = tokens2[0];
                        String destination = tokens2[1];
                       // Arc arc = new Arc(verticList.get(source), verticList.get(destination), weight, data);
//                        Key key  = (Key)verticList.get(destination).key;
//                        verticList.get(source).outAdjList.put(key, arc);
//                        verticList.get(source).inAdjList.put(key, arc);
                        graph.insertArc(source, destination);
                        graph.setArcWeight(source, destination, weight);


                    }
            }
        }catch(FileNotFoundException e){
            System.out.println("input file path: "+f.getAbsolutePath());
            System.out.println("File not found");
        }
       
   
        
        //format for out put
//        ------------------------------------------------------------
//        Graph <filename>
//        ------------------------------------------------------------
//        |V| = #####
//        |A| = #####
//        Density = #####
//        Degree distribution: minimum  average  maximum
//            inDegree           ####     ####     #####
//            outDegree          ####     ####     #####
//        Number of Strongly Connected Components: ####
//        Percent Vertices in Largest Strongly Connected Component: ####
        
        System.out.println("------------------------------------------------------------");
        System.out.println("Graph <"+file+">");
        System.out.println("------------------------------------------------------------");
        System.out.println("|V| = "+graph.vertexCount());
        System.out.println("|A| = "+graph.arcCount());
        System.out.println("Density: "+graph.graphDensity()); 
        System.out.printf("%-22s",  "Degree distribution:" );
        System.out.printf("%-10s",  "minimum" );
        System.out.printf("%-10s",  "average" );
        System.out.printf("%-10s",  "maximum" );
        System.out.println("");
        System.out.printf("%-4s",  "" );
        System.out.printf("%-18s",  "inDegree:" );
        System.out.printf("%-10s",  graph.minInDegree() );
        System.out.printf("%-10s",  graph.avergeDegree() );
        System.out.printf("%-10s",  graph.maxInDegree() );
        System.out.println("");
        System.out.printf("%-4s",  "" );
        System.out.printf("%-18s",  "outDegree:" );
        System.out.printf("%-10s",  graph.minOutDegree() );
        System.out.printf("%-10s",  graph.avergeDegree() );
        System.out.printf("%-10s",  graph.maxOutDegree() );
        System.out.println("");
        System.out.println("Strongly Connected Components: "+ SCC(graph));
        System.out.println("Percent Vertices in Largest Strongly Connected Component:"+new DecimalFormat("#.##").format((double)largestSCC/(double)graph.vertexCount()*100)+"%");
        System.out.println("");
        //print out the SCC for the small graph
        printSCC();
        
        System.exit(0);
 
    }
    private static String[] stringToArray(String wordString) {
            String[] result;
            int i = 0;     // index into the next empty array element

            //--- Declare and create a StringTokenizer
            StringTokenizer st = new StringTokenizer(wordString);

            //--- Create an array which will hold all the tokens.
            result = new String[st.countTokens()];

            //--- Loop, getting each of the tokens
            while (st.hasMoreTokens()) {
                result[i++] = st.nextToken();
            }

            return result;
    }
        
    public static int SCC(ArcGraph G){
        DFS(G);
        G.clearAnnotations("color");
        G.clearAnnotations("parent");
        G.clearAnnotations("discover");
        G.clearAnnotations("finish");
        G.transposeGraph();
        ArcGraph G_prime = G;
        //System.out.println("DFS2 START");
        return DFS2(G_prime);
        //print(s);
    }
    public static void print(Stack s)
    {
        while(!s.isEmpty())
        {
            System.out.println(s.peek());
            s.pop();
        }

    }
    public static void printSCC()
    {
         System.out.printf("%-5s",  "SCC" );
         System.out.printf("%-5s",  "SIZE" );
         System.out.printf("%-35s",  "Members" );
         System.out.println("");
        for(int i = 0; i< scclist.size(); i++){
            ArrayList<String> s = scclist.get(i);
            System.out.printf("%-5s","#"+(i+1));
            System.out.printf("%-5s",s.size());
            System.out.printf("%-35s",s);
            System.out.println("");
//            
        }
//        for(ArrayList<String> tem: scclist){
//            System.out.printf("%-5s","#");
//            System.out.printf("%-5s",tem.size());
//            System.out.printf("%-35s",tem);
//            System.out.println("");
//        }
        
        

    }
    public static int time;
    public static Stack s;
    public static void DFS(ArcGraph G){
        Iterator i =G.vertices();
        while(i.hasNext()){
            String key = (String)i.next();
            G.setAnnotation(key,(Object)"color",(Object)"WHITE");
            G.setAnnotation(key,(Object)"parent",(Object)"");
            //System.out.println(graph.getAnnotation(key, "parent"));
        }
        time=0;
        s = new Stack();
        scclist= new ArrayList<ArrayList<String>>();
        Iterator ii =G.vertices();
        while(ii.hasNext()){
            String key = (String)ii.next();
            if(G.getAnnotation(key, "color").equals("WHITE")){
                 
                 DFS_VISIT(G,key);
            }
        }
        
    }
     public static void DFS_VISIT(ArcGraph G, String u){
        time = time+1;
        G.setAnnotation(u,(Object)"discover",(Object)time);
        //System.out.println("Key: "+u+" discovered at:"+ G.getAnnotation(u,(Object)"discover"));
        G.setAnnotation(u,(Object)"color",(Object)"GRAY");
        Iterator i = G.outAdjacentVertices(u);
        while(i.hasNext()){
            //String v = (String)i.next(); // used for outAdjacentVertices
            String v = ((ArrayList<String>)i.next()).get(0);
            if(G.getAnnotation(v,(Object)"color").equals("WHITE")){
                G.setAnnotation(v,(Object)"parent",(Object)u);
                DFS_VISIT(G, v);
            }
        }
        G.setAnnotation(u,(Object)"color",(Object)"BLACK");
        time= time+1;
        G.setAnnotation(u,(Object)"finish",(Object)time);
        //System.out.println("Key: "+u+" finished at:"+ G.getAnnotation(u,(Object)"finish"));
        s.add(u);
       
    }
    public static int largestSCC;
    public static int DFS2(ArcGraph G){
        int numberSCC=0;
        largestSCC= Integer.MIN_VALUE;
        Iterator i =G.vertices();
        while(i.hasNext()){
            String key = (String)i.next();
            G.setAnnotation(key,(Object)"color",(Object)"WHITE");
            G.setAnnotation(key,(Object)"parent",(Object)"");
            //System.out.println(graph.getAnnotation(key, "parent"));
        }
        time=0;
         while(!s.isEmpty())
        {
            String key = (String)s.pop();
            //System.out.println("peeking"+ key);
            if(G.getAnnotation(key, "color").equals("WHITE")){
                 //System.out.println("scc-found:1 "+"leader="+key);
                 numberSCC++;
                 temp = new ArrayList<String>();
                 DFS_VISIT2(G,key);
                 scclist.add(temp);
                 //System.out.println("temp size:"+temp.size());
                 int discover = (Integer)G.getAnnotation(key, "discover");
                 int finish = (Integer)G.getAnnotation(key, "finish");
                 int max = (finish-discover+1)/2;
                 
                 if(max>largestSCC){
                     largestSCC=max;
                     
                 }
                 
            }
        }
        //System.out.println("this scc's largest nodes:"+largestSCC);
        return numberSCC;
    }
    public static ArrayList<ArrayList<String>> scclist;
    public static ArrayList<String> temp;
    public static void DFS_VISIT2(ArcGraph G, String u){
        time = time+1;
        
      
        G.setAnnotation(u,(Object)"discover",(Object)time);
        //System.out.println("Key: "+u+" discovered at:"+ G.getAnnotation(u,(Object)"discover"));
        G.setAnnotation(u,(Object)"color",(Object)"GRAY");
        Iterator i = G.outAdjacentVertices(u);
        while(i.hasNext()){
           
            //String v = (String)i.next(); // used for outAdjacentVertices
            String v = ((ArrayList<String>)i.next()).get(0);
            
            if(G.getAnnotation(v,(Object)"color").equals("WHITE")){
                G.setAnnotation(v,(Object)"parent",(Object)u);
                DFS_VISIT2(G, v);
                
            }
        }
        G.setAnnotation(u,(Object)"color",(Object)"BLACK");
        time= time+1;
        G.setAnnotation(u,(Object)"finish",(Object)time);
        //System.out.println("Key: "+u+" finished at:"+ G.getAnnotation(u,(Object)"finish"));
        temp.add(u);
       
    }

}
        //graph.print();
        //graph.printStaticReport();
         //DirectedGraph graph = new DirectedGraph("friendship.txt");
        //DirectedGraph graph1 = new DirectedGraph("celegansneural.vna");
        //ArcGraph graph = new ArcGraph();
        //graph.reverseDirection("290", "44");
        //graph.removeVertex("290");
        //System.out.println("arc weight:"+ graph.getArcWeight("290", "44"));
        //graph.setArcWeight("290", "44", 100.00);
        //System.out.println("new arc weight:"+ graph.getArcWeight("290", "44"));

          //ArcGraph graph = new ArcGraph(file);
          
//        
//        graph.insertVertex("c");
//        graph.insertVertex("g");
//        graph.insertVertex("f");
//        graph.insertVertex("h");
//        graph.insertVertex("d");
//        graph.insertVertex("b");
//        graph.insertVertex("a");
//        graph.insertVertex("e");
//        graph.insertArc("a", "b");
//        graph.insertArc("e", "a");
//        graph.insertArc("e", "f");
//        graph.insertArc("b", "c");
//        graph.insertArc("c", "d");
//        graph.insertArc("d", "c");
//        graph.insertArc("b", "e");
//        graph.insertArc("f", "g");
//        graph.insertArc("g", "f");
//        graph.insertArc("c", "g");
//        graph.insertArc("b", "f");
//        graph.insertArc("d", "h");
//        graph.insertArc("g", "h");
//        graph.insertArc("h", "h");
//        
//        graph.setArcWeight("a", "b", 1.0);
//        graph.setArcWeight("e", "a", 1.0);
//        graph.setArcWeight("e", "f", 1.0);
//        graph.setArcWeight("b", "c", 1.0);
//        graph.setArcWeight("c", "d", 1.0);
//        graph.setArcWeight("d", "c", 1.0);
//        graph.setArcWeight("b", "e", 1.0);
//        graph.setArcWeight("f", "g", 1.0);
//        graph.setArcWeight("g", "f", 1.0);
//        graph.setArcWeight("c", "g", 1.0);
//        graph.setArcWeight("b", "f", 1.0);
//        graph.setArcWeight("d", "h", 1.0);
//        graph.setArcWeight("g", "h", 1.0);
//        graph.setArcWeight("h", "h", 1.0);
//////        
     
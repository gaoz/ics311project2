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
 * This is the analyze driver that report the data of
 * the representation of the directed graph in a nice
 * table.
 * 
 * @author      Vinson Gao
 * @version     1.0       
 * @since       2014-04-18
 */
public class AnalyzeGraph <Key extends Comparable<Key>, Data>{

    /**
     * Main method that starts to run the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // create a  graph object;
        ArcGraph graph = new ArcGraph();
        // TODO Auto-generated method stub
        Scanner sc=null;
        //Scanner sc = new Scanner(System.in);  // uncomment this line to get open input for test file.
        
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to Vinson Gao's Graph Implementation");
        System.out.println("");
        System.out.printf("%-1s",  "*******Example Graph List:******\n" );
        System.out.printf("%-5s",  "#1." );
        System.out.printf("%-1s",  "SCC-Test.vna\n" );
        System.out.printf("%-5s",  "#2. " );
        System.out.printf("%-1s",  "celegansneural.vna\n" );
         System.out.printf("%-5s",  "#3. " );
        System.out.printf("%-1s",  "political-blogs.vna\n" );
         System.out.printf("%-5s",  "#4. " );
        System.out.printf("%-1s",  "wiki-Vote.vna\n" );
         System.out.printf("%-5s",  "#5. " );
        System.out.printf("%-1s",  "cit-HepTh.vna\n" );
         System.out.printf("%-5s",  "#6. " );
        System.out.printf("%-1s",  "email-Enron.vna\n" );
         System.out.printf("%-5s",  "#7. " );
        System.out.printf("%-1s",  "ti-full.vna\n" );
        System.out.printf("%-1s",  "************Input:***********\n" );
        System.out.println("Enter graph input file name: ");
        
//        String file="";
//        if(sc.hasNext()){
//            file = sc.nextLine();
//        }else{
//            System.out.print("please enter a file name example: celegansneural.vna");
//        }
        String temp=""; 
        File f = new File(args[0]);
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
//                        Arc arc = new Arc(verticList.get(source), verticList.get(destination), weight, data);
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
        System.out.println("Graph <"+args[0]+">");
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
    /**
     * helper method for iterating a string
     * @param wordString  wordString  input string
     * @return            return tokens of splited sting from the input line
     */
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
    /**
     * compute the strongly connected component of the input directed graph
     * @param g    directed graph
     * @return     the total of the strongly connected component
     */    
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
    
    /**
     * test method that used to debug the SCC
     * @param s   a stack to be print out 
     */
    public static void print(Stack s)
    {
        while(!s.isEmpty())
        {
            System.out.println(s.peek());
            s.pop();
        }

    }
    /**
     * print out the table of strongly connected<br> 
     * component which is required in ics311 project <br>
     * 2 assignment
     */
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
            
        }

    }
    public static int time;
    public static Stack s;
    /**
     * DFS search algorithm <br>
     * Step one to compute all the vertice 
     * and handle the main outer loop
     * 
     * @param G   arcgraph object
     * @return: none
    */
    
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
     /**
     * DFS hepler algorithm that visite all the vertices 
     * under a leader vertices, and mark all the visited 
     * vertices to black and put all the vertices into a
     * stack that will used by DFS2 later
     * 
     * @param G   arcgraph object
     * @param u   Key for search
     * 
    */
    
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
    /**
     * modified DFS algorithm which is a part of the SCC algorithm
     * that perform another DFS search after the tranposed graph with
     * the order of the vertice stack, it the scc will come out eventually
     * in this method it will will compute the scc number by counting 
     * the white vertices inside the main loop, and also it will computer
     * largest scc and return it.
     *
     * 
     * @param G   arcgraph object
     * @return  the total number of strong connected components
    */
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
    /**
     * DFS2 hepler algorithm that visit all the vertices inside a scc
     * and mark them black and add all those scc vertices into a arraylist
     * that is used to print out the scc table later.
     * 
     * @param G   arcgraph object
     * @param u   Key for search
     * 
    */
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
     
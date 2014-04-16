/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ics311;

/**
 *
 * @author Vinson Gao
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;
class Arc<Key extends Comparable<Key>, Data>{
    
    public Vertex source;
    public Vertex destination;
    public Number weight;
    public Data data;
    public Hashtable<Object,Object> annotation;
    public Arc(Vertex s,Vertex d, Number w, Data da){
       
        source = s;
        destination = d;
        weight = w;
        data= da;
        this.annotation = new Hashtable<Object,Object>();
        
    }
}
 
class Vertex<Key extends Comparable<Key>, Data> {
    public Key key;
    public Data data;
    public Hashtable<Key,Arc> outAdjList;
    public Hashtable<Key,Arc> inAdjList;
    public Hashtable<Object,Object> annotation;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex<Key,Data> previous = null;
    Vertex(Key k,Data d, Hashtable<Key,Arc> outadjList, Hashtable<Key,Arc> inadjList) {
            this.key = k;
            this.data = d;
            this.outAdjList = outadjList;
            this.inAdjList = inadjList;
            this.annotation = new Hashtable<Object,Object>();
    }
}


public class ArcGraph<Key extends Comparable<Key>, Data> implements Graph<Key, Data> {
        private Hashtable<Key, Vertex> verticList;
        private boolean isTransposedGraphy;
        
        public ArcGraph() throws FileNotFoundException {
            
           isTransposedGraphy = false;
           verticList = new Hashtable<Key, Vertex>(); 

           String temp=""; 
           Scanner sc = new Scanner(new File("SCC-Test.vna"));
          // Scanner sc = new Scanner(new File("political-blogs.vna"));
          //Scanner sc = new Scanner(new File("celegansneural.vna"));
          //Scanner sc = new Scanner(new File("test.vna"));
           
         
           if(sc.nextLine().contains("*")){
                
               // ArrayList<String> labels = new ArrayList<String>();
                String line = sc.nextLine();
                String[] tokens = this.stringToArray(line);
                
            
                while(sc.hasNext()){
                   //  System.out.println("successfully readin file:"+ sc.hasNext());
                    temp = sc.nextLine(); 
                    if(temp.startsWith("*")){
                        break;
                    }else{
                        // ADD VERTEX INTO MY LIST
                        //String[] itr = temp.split(" "); 
                        Hashtable<String, String> attributeTable = new Hashtable<String, String>();
                        String[] tokens2 = this.stringToArray(temp);
                        for(int i=1; i<tokens.length; i++){
                            attributeTable.put(tokens[i],tokens2[i]);
                            //System.out.println(tokens2[i]);
                        }
                        Data data = (Data)attributeTable;
                        verticList.put((Key)tokens2[0], new Vertex(tokens2[0],data,new Hashtable<Key,Arc>(),new Hashtable<Key,Arc>()));
                        //verticList.put((Key)itr[0], new Vertex(itr[0],new Hashtable<Key,Arc>()));
                    }
                    
                }
           }
           if(temp.contains("*")){
               
                int num = 0;
                String[] tokens = this.stringToArray(sc.nextLine());
                for(int j=0; j<tokens.length; j++){
                    if(tokens[j].contains("strength")){
                        num= j;
                    }
                }
                while(sc.hasNext()){
                    String line = sc.nextLine(); 
                    String[] tokens2 = this.stringToArray(line);
                    
                    Double weight=1.00;
                    if(num>1){  // check whether there are weight in the input file
                        weight = Double.parseDouble(tokens2[num]);
                    }
                     Hashtable<String, String> attributeTable = new Hashtable<String, String>();
                        
                        for(int i=2; i<tokens.length; i++){
                            attributeTable.put(tokens[i],tokens2[i]);
                        }
                        Data data = (Data)attributeTable;
                    String source = tokens2[0];
                    String destination = tokens2[1];
                    Arc arc = new Arc(verticList.get(source), verticList.get(destination), weight, data);
//                    ArrayList<Key> key =new ArrayList<Key>();
//                    key.add((Key)verticList.get(source).key);
//                    key.add((Key)verticList.get(destination).key);
                    Key dkey  = (Key)verticList.get(destination).key;
                    Key skey  = (Key)verticList.get(destination).key;
                    //Key key = (Key)(tokens2[0]+tokens2[1]);
                    verticList.get(source).outAdjList.put(dkey, arc);
                    verticList.get(source).inAdjList.put(skey, arc);
                    
                  
                }
           }
          
         
        }// end of constructor
        private String[] stringToArray(String wordString) {
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
        
         public void print() {
                
                Enumeration iterator = verticList.elements();
               
                while(iterator.hasMoreElements()) {
                    
                    Vertex temp = (Vertex)iterator.nextElement();
                    System.out.print(temp.key);
                    Enumeration i = temp.outAdjList.elements();
                    
                    while(i.hasMoreElements()) {
                        Arc t = (Arc)i.nextElement();
                       
                        System.out.print(" --> " + t.destination.key);
                        //System.out.println(" Name: " + ((Hashtable<String, String>)t.data).get("strength"));
                    }
                     System.out.print("\n");
                    // System.out.println(" Name: " + ((Hashtable<String, String>)temp.data).get("source"));
                }
//                String s = "10";
//                String d = "11";
//                Hashtable<String, String> a = ( Hashtable<String, String>)getArcData((Key)s,(Key)d);
//                
//                System.out.println(" Strenght: " +a.get("strength"));
                
                
                  String s = "37";
                  String d = "32";
                  System.out.println("check arcExit :"+s+"->"+d+"  "+ arcExists((Key)s,(Key)d));
        }
         
        public void printStaticReport(){
            System.out.println("Statistics:");
            System.out.println( "vertexCount:"+vertexCount() +" arcCount" +arcCount());
            System.out.println("   -    Minimum In & Out Degree:");
            System.out.println("   -    Average Degree:"+ null);
            System.out.println("   -    Maximum In & Out Degree: "+ null);
            System.out.println("   -    Network Diameter:"+ null);
            System.out.println("   -    Avg. Path Length:"+ null);
            System.out.println("   -    Graph Density:"+ null);
            System.out.println("   -    Weakly Connected Components:"+ null);
            System.out.println("   -    Strongly Connected Components: "+ null);
            System.out.println("   -    Modularity:"+ null);
        }                              
         
	@Override
	public int vertexCount() {
		// TODO Auto-generated method stub
		return verticList.size();
	}

	@Override
	public int arcCount() {
		// TODO Auto-generated method stub
                int arcCount =0;       
                Enumeration iterator = verticList.elements();

                while(iterator.hasMoreElements()) {
                    
                    Vertex temp = (Vertex)iterator.nextElement();
                    
                    arcCount += temp.outAdjList.size();
                    

                } 
		return arcCount;
	}

	@Override
	public Iterator<ArrayList<Key>> arcs() {
		Hashtable<ArrayList<Key>,ArrayList<Key>> iter = new Hashtable<ArrayList<Key>,ArrayList<Key>>();
                Enumeration iterator = verticList.elements();
               
                while(iterator.hasMoreElements()) {
                    
                    Vertex temp = (Vertex)iterator.nextElement();
                   
                    Enumeration i = temp.outAdjList.elements();
                    
                    while(i.hasMoreElements()) {
                       Arc t = (Arc)i.nextElement();
                       ArrayList<Key> ak = new ArrayList<Key>();
                       if(this.isTransposedGraphy){
                           ak.add((Key)t.destination.key); 
                           ak.add((Key)t.source.key);
                           
                       }else{
                           ak.add((Key)t.source.key);
                           ak.add((Key)t.destination.key); 
                       }
                          
                       iter.put(ak,ak);
                    }
                }
                
		return iter.keySet().iterator();
	}

	@Override
	public Iterator<Key> vertices() {
		return verticList.keySet().iterator();
	}

	@Override
	public boolean arcExists(Key _sourceKey, Key _destinationKey) {
            Key sourceKey;
            Key destinationKey;
            if(this.isTransposedGraphy){
                sourceKey = _destinationKey;
                destinationKey  = _sourceKey;    
            }else{
                sourceKey = _sourceKey;
                destinationKey  = _destinationKey;    
            }
            
            if(verticList.get(sourceKey).outAdjList.containsKey(destinationKey)){
                     return true;
            }
            return false;
	}

	@Override
	public boolean vertexExists(Key vertexKey) {
          
		if(verticList.containsKey(vertexKey)){
                    return true;
                }
		return false;
	}

	@Override
	public int inDegree(Key vertexKey) {
		if(this.isTransposedGraphy){
                    return  verticList.get(vertexKey).outAdjList.size();
                }
		return  verticList.get(vertexKey).inAdjList.size();
	}

	@Override
	public int outDegree(Key vertexKey) {
		if(this.isTransposedGraphy){
                     return  verticList.get(vertexKey).inAdjList.size();
                }
		return  verticList.get(vertexKey).outAdjList.size();
	}
        /*
        arraylist index 0 store: source key that point to vertexKey(destination key) (return and use this one)
        arraylist index 1 store: destination key
        */
	@Override
	public Iterator<ArrayList<Key>> inAdjacentVertices(Key vertexKey) {
		if(this.isTransposedGraphy){
                    return outAdjacentVertices(vertexKey);
                }
		ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                Enumeration i = verticList.get(vertexKey).inAdjList.elements();
                while(i.hasMoreElements()) {
                       Arc arc = (Arc)i.nextElement();
                      ArrayList<Key> ak = new ArrayList<Key>();
                      ak.add((Key)arc.source.key);
                      ak.add((Key)arc.destination.key);
                      arrayList.add(ak);
                }
		return arrayList.iterator();
	}
         /*
        arraylist index 0 store: source key that point to vertexKey(destination key)
        arraylist index 1 store: destination key (return and use this one)
        */
	@Override
	public Iterator<ArrayList<Key>> outAdjacentVertices(Key vertexKey) {
		if(this.isTransposedGraphy){
                    return inAdjacentVertices(vertexKey);
                }
		ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                Enumeration i = verticList.get(vertexKey).outAdjList.elements();
                while(i.hasMoreElements()) {
                       Arc arc = (Arc)i.nextElement();
                       ArrayList<Key> ak = new ArrayList<Key>();
                       ak.add((Key)arc.source.key);
                       ak.add((Key)arc.destination.key);
                       arrayList.add(ak);
                }
		return arrayList.iterator();
	}

	@Override
	public Data getVertexData(Key vertexKey) {
		return (Data)verticList.get(vertexKey).data;
	}

	@Override
	public Data getArcData(Key _sourceKey, Key _destinationKey) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
		Hashtable<Key, Arc> h = verticList.get(sourceKey).outAdjList;
                Arc a = h.get(destinationKey);
		return (Data)a.data;
	}

	@Override
	public Number getArcWeight(Key _sourceKey, Key _destinationKey) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
		Hashtable<Key, Arc> h = verticList.get(sourceKey).outAdjList;
                Arc a = h.get(destinationKey);
		return a.weight;
	}

	@Override
	public void insertVertex(Key vertexKey) {
		verticList.put(vertexKey, new Vertex(vertexKey, null , new Hashtable<Key,Arc>(),new Hashtable<Key,Arc>()));
		
	}

	@Override
	public void insertVertex(Key vertexKey, Data vertexData) {
               
		verticList.put(vertexKey, new Vertex(vertexKey, vertexData, new Hashtable<Key,Arc>(),new Hashtable<Key,Arc>()));
		
	}

	@Override
	public void insertArc(Key _sourceKey, Key _destinationKey) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
                Arc arc = new Arc(verticList.get(sourceKey), verticList.get(destinationKey), null, null);
		verticList.get(sourceKey).outAdjList.put(destinationKey, arc);
                verticList.get(destinationKey).inAdjList.put(sourceKey, arc);
		
	}

	@Override
	public void insertArc(Key _sourceKey, Key _destinationKey, Data arcData) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
                
		Arc arc = new Arc(verticList.get(sourceKey), verticList.get(destinationKey), null, arcData);
		verticList.get(sourceKey).outAdjList.put(destinationKey, arc);
                verticList.get(destinationKey).inAdjList.put(sourceKey, arc);
		
	}

	@Override
	public void setVertexData(Key vertexKey, Data vertexData) {
            
		verticList.get(vertexKey).data= vertexData;
		
	}

	@Override
	public void setArcData(Key _sourceKey, Key _destinationKey, Data arcData) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
		Arc a = (Arc)verticList.get(sourceKey).outAdjList.get(destinationKey);
		a.data=arcData;
	}

	@Override
	public Data removeVertex(Key vertexKey) {
                Data data = (Data)verticList.get(vertexKey).data;
                Enumeration edges = verticList.get(vertexKey).inAdjList.elements();
                while(edges.hasMoreElements()) {
                      Arc arc = (Arc)edges.nextElement();
                      Vertex v = arc.source;
                      v.outAdjList.remove(vertexKey);
                }
                edges = verticList.get(vertexKey).outAdjList.elements();
                while(edges.hasMoreElements()) {
                      Arc arc = (Arc)edges.nextElement();
                      Vertex v = arc.source;
                      v.inAdjList.remove(vertexKey);
                }
             
		verticList.remove(vertexKey);
                
		return data;
	}
        
	@Override
	public Data removeArc(Key _sourceKey, Key _destinationKey) {
                
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
                Data data = (Data)(Arc)((Vertex)verticList.get(sourceKey).outAdjList.get(destinationKey)).data;
                
		verticList.get(sourceKey).outAdjList.remove(destinationKey);
                verticList.get(destinationKey).inAdjList.remove(sourceKey);
		return data;
	}

	@Override
	public void reverseDirection(Key _sourceKey, Key _destinationKey) {   
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
            
                    Arc arc = new Arc(verticList.get(destinationKey), verticList.get(sourceKey), ((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).weight, ((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).data);
                    verticList.get(destinationKey).outAdjList.put(sourceKey, arc);
                    verticList.get(sourceKey).inAdjList.put(destinationKey, arc);
                    verticList.get(destinationKey).inAdjList.remove(sourceKey);
                    verticList.get(sourceKey).outAdjList.remove(destinationKey);
                    
                
		
	}

	@Override
	public void transposeGraph() {
                if(isTransposedGraphy){
                    isTransposedGraphy = false;
                }else{
                    isTransposedGraphy = true;
                }
		
	}

	@Override
	public void setArcWeight(Key _sourceKey, Key _destinationKey, Number wieght) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
                ((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).weight = wieght;
		
	}


	@Override
	public void setAnnotation(Key vertexKey, Object property, Object value) {
		verticList.get(vertexKey).annotation.put(property, value);
		
	}

	@Override
	public void setAnnotation(Key _sourceKey, Key _destinationKey,
			Object property, Object value) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
            
            
		((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).annotation.put(property, value);
		
	}

	@Override
	public Object getAnnotation(Key vertexKey, Object property) {
		
		return verticList.get(vertexKey).annotation.get(property);
	}

	@Override
	public Object getAnnotation(Key _sourceKey, Key _destinationKey,
			Object property) {
		 Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
		return ((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).annotation.get(property);
	}

	

	@Override
	public Object removeAnnotation(Key _sourceKey, Key _destinationKey,
			Object property) {
                Key sourceKey;
                Key destinationKey;
                if(this.isTransposedGraphy){
                    sourceKey = _destinationKey;
                    destinationKey  = _sourceKey;    
                }else{
                    sourceKey = _sourceKey;
                    destinationKey  = _destinationKey;    
                }
		Object o =((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).annotation.get(property);
                ((Arc)verticList.get(sourceKey).outAdjList.get(destinationKey)).annotation.remove(property);
		return o;
	}

	@Override
	public void clearAnnotations(Object property) {
		Hashtable<ArrayList<Key>,String> iter = new Hashtable<ArrayList<Key>,String>();
                Enumeration iterator = verticList.elements();
               
                while(iterator.hasMoreElements()) {
                    
                    Vertex temp = (Vertex)iterator.nextElement();
                    temp.annotation.remove(property);
                    Enumeration o = temp.outAdjList.elements();
                    Enumeration i = temp.inAdjList.elements();
                    while(i.hasMoreElements()) {
                       Arc t = (Arc)i.nextElement();
                       t.annotation.remove(property);
                    }
                    while(o.hasMoreElements()) {
                       Arc t = (Arc)o.nextElement();
                       t.annotation.remove(property);
                    }
                }
		
	}

	@Override
	public Object removeAnnotation(Key vertexKey, Object property) {
		Object o= verticList.get(vertexKey).annotation.get(property);
                verticList.get(vertexKey).annotation.remove(property);
		return o; 
	}
        
        
        public int minInDegree(){
            int min = Integer.MAX_VALUE;
            Enumeration iterator = verticList.elements();
            while(iterator.hasMoreElements()){
                int current = ((Vertex)iterator.nextElement()).inAdjList.size();
                if(current < min){
                    min = current;
                }
            }
            return min;
                
        }
        public int minOutDegree(){
            int min = Integer.MAX_VALUE;
            Enumeration iterator = verticList.elements();
            while(iterator.hasMoreElements()){
                int current = ((Vertex)iterator.nextElement()).outAdjList.size();
                if(current < min){
                    min = current;
                }
            }
            return min;
        }
        public int maxInDegree(){
            int max = Integer.MIN_VALUE;
            Enumeration iterator = verticList.elements();
            while(iterator.hasMoreElements()){
                int current = ((Vertex)iterator.nextElement()).inAdjList.size();
                if(current > max){
                    max = current;
                }
            }
            return max;
                
        }
        public int maxOutDegree(){
            int max = Integer.MIN_VALUE;
            Enumeration iterator = verticList.elements();
            while(iterator.hasMoreElements()){
                int current = ((Vertex)iterator.nextElement()).outAdjList.size();
                if(current > max){
                    max = current;
                }
            }
            return max;
        }
        public String avergeDegree(){
            String averge = "0.00";
            Double ave=0.00;
            
            try{
                ave = (double)this.arcCount()/(double)this.vertexCount();
                averge = new DecimalFormat("#.##").format(ave);
            }catch(Exception e){
                averge = "Error";
            }
            
            return averge;
        }
//        public String networkDiameter(){
//            this.
//        }
        public void computePaths(Key _source)
        {
            Vertex source = verticList.get(_source);
            source.minDistance = 0.;
            PriorityQueue<Key> vertexQueue = new PriorityQueue<Key>();
            vertexQueue.add((Key)source.key);

            while (!vertexQueue.isEmpty()) {
                Vertex<Key,Data> u = verticList.get(vertexQueue.poll());

                // Visit each edge exiting u
                Enumeration e = u.outAdjList.elements();
                while(e.hasMoreElements())
                {   
                    Arc a = (Arc)e.nextElement();
                    Vertex v = (Vertex<Key,Data>)a.destination;
                    double weight = (double)a.weight;
                    double distanceThroughU = u.minDistance + weight;
                    if (distanceThroughU < v.minDistance) {
                        vertexQueue.remove(v);
                        v.minDistance = distanceThroughU ;
                        //v.previous = u;
                        
                        vertexQueue.add((Key)v.key);
                    }
                }
            }
        }

        public List<Vertex<Key,Data>> getShortestPathTo(Vertex<Key,Data> target)
        {
            List<Vertex<Key,Data>> path = new ArrayList<Vertex<Key,Data>>();
            for (Vertex<Key,Data> vertex = target; vertex != null; vertex = vertex.previous)
                path.add(vertex);
            Collections.reverse(path);
            return path;
        }
        public void test(){
            this.computePaths((Key)verticList.elements().nextElement().key);
            Double diameter = Double.MIN_VALUE;
            
            for (Vertex<Key,Data> v : verticList.values())
            {
                if(v.minDistance!=Double.POSITIVE_INFINITY){
                    
                    if(v.minDistance>diameter){
                        
                        diameter=v.minDistance;
                    }
                }
                System.out.println("Distance to " + v.key + ": " + v.minDistance);
                //List<Vertex<Key,Data>> path = getShortestPathTo(v);
//                for(Vertex<Key,Data> a : path){
//                    System.out.print("  ShortestPathFrom:" + a.key);
//                }
                //System.out.println("  --- done");
            }
            System.out.println("Diameter:"+diameter);
            
        }
        public void averagePathLength(){
            Double avg = 0.;
            Double v = (double)this.vertexCount();
            Double a = 1/v*(v-1);
            Double w = 0.;
            Iterator i = this.arcs();
            while(i.hasNext()){
                Object o = i.next();
                ArrayList<Key> k = (ArrayList<Key>)o;
                w= (Double)this.getArcWeight(k.get(0), k.get(1));
                
            }
            
            avg = a*w;
            System.out.println("Averge Path Length: "+avg);
        }
        public String graphDensity(){
             double E = (double)this.arcCount(); 
             double V = (double)this.vertexCount();
             double f = 2*(E/(V * (V-1)));
             f = f/2;
             return new DecimalFormat("#.###").format(f);
        }
//         public int diameter()
//
//        {
//
//            int n = verticList.size();
//
//            int max = 0;
//
//            int [] dist = new int[n];
//
//
//
//            for (int v=0; v<n; v++)
//
//            {
//
//                int d = maxDistance(G, v, dist);
//
//                max = d > max ? d : max;
//
//                }
//
//            return max;
//
//            }
//       }

//        public void Dijkstra(Key source){
//           
//                for (Vertex v: verticList.values()){                                // Initializations
//                    v.minDistance  = Double.MAX_VALUE;                    // Unknown distance function from 
//                    v.previous = null;                             // Previous node in optimal path
//                }                                                  // from source
//                
//                verticList.get(source).minDistance = 0 ;                    // Distance from source to source
//                Hashtable<Key, Vertex>  Q = verticList;                      // All nodes in the graph are
//                 Enumeration itorator =Q.elements();                                                     // unoptimized – thus are in Q
//                 while(itorator.hasMoreElements()){                                     // The main loop
//                     u := vertex in Q with smallest distance in dist[] ;    // Source node in first case
//                     remove u from Q ;
//                     if dist[u] = infinity:
//                         break ;                                            // all remaining vertices are
//                     end if                                                 // inaccessible from source
//                     
//                     for each neighbor v of u:                              // where v has not yet been 
//                                                                            // removed from Q.
//                         alt := dist[u] + dist_between(u, v) ;
//                         if alt < dist[v]:                                  // Relax (u,v,a)
//                             dist[v]  := alt ;
//                             previous[v]  := u ;
//                             decrease-key v in Q;                           // Reorder v in the Queue (that is, heapify-down) 
//                         end if
//                     end for
//                 }//end while
//                 return dist[], previous[];
//            
//        }

}

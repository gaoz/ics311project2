/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ics311;

/**
 * This is the representation of the directed graph.
 * 
 * @author      Vinson Gao
 * @version     1.0       
 * @since       2014-04-18
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


/**
 * This is the representation of the directed graph.
 * Subclass of the Graph.
 * It is used to create directed edge
 */
class Arc<Key extends Comparable<Key>, Data>{
    
    public Vertex source;
    public Vertex destination;
    public Number weight;
    public Data data;
    public Hashtable<Object,Object> annotation;
    /**
    * constructor
    * @prameter s: edge source vertice
    * @prameter d: edge destination vertice
    * @prameter w: any kind number
    * @prameter w: any kind data
    */
    public Arc(Vertex s,Vertex d, Number w, Data da){
       
        source = s;
        destination = d;
        weight = w;
        data= da;
        this.annotation = new Hashtable<Object,Object>();
        
    }
}
 /**
 * sub class Vertex of Graph
 * 
 */
class Vertex<Key extends Comparable<Key>, Data> {
    public Key key;
    public Data data;
    public Hashtable<Key,Arc> outAdjList;
    public Hashtable<Key,Arc> inAdjList;
    public Hashtable<Object,Object> annotation;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex<Key,Data> previous = null;
    
    /**
    * constructor
    * @param k          hashtable key
    * @param d          vertice properties 
    * @param outadjList out goinog adjacent list of arc wrapped by a hashtable
    * @param inadjList  in going adjacent list of arc wrapped by a hashtable
    */
    Vertex(Key k,Data d, Hashtable<Key,Arc> outadjList, Hashtable<Key,Arc> inadjList) {
            this.key = k;
            this.data = d;
            this.outAdjList = outadjList;
            this.inAdjList = inadjList;
            this.annotation = new Hashtable<Object,Object>();
    }
}

/**
 * This is the main class of the directed graph representation. 
 * 
 */
public class ArcGraph<Key extends Comparable<Key>, Data> implements Graph<Key, Data> {
        private Hashtable<Key, Vertex> verticList;
        private boolean isTransposedGraphy;
        /**
         * constructor takes no parameter;
         * create the variable isTransposedGraphy to false
         * create the verticList hashtable
         */
        public ArcGraph()  {
            
           isTransposedGraphy = false;
           verticList = new Hashtable<Key, Vertex>(); 
        }
        /**
        * this method is to print out all 
        * out adjcent list arcs with a nice looking
        * for debugging purpose
        */
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

	@Override
	public Iterator<ArrayList<Key>> inAdjacentVertices(Key vertexKey) {
		if(this.isTransposedGraphy){
                    ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                    Enumeration i = verticList.get(vertexKey).outAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        ArrayList<Key> ak = new ArrayList<Key>();
                        //ak.add((Key)arc.source.key);
                        ak.add((Key)arc.destination.key);
                        arrayList.add(ak);
                    }
                    return arrayList.iterator();
                }else{
                    ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                    Enumeration i = verticList.get(vertexKey).inAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        ArrayList<Key> ak = new ArrayList<Key>();
                        ak.add((Key)arc.source.key);
                        //ak.add((Key)arc.destination.key);
                        arrayList.add(ak);
                    }
                    return arrayList.iterator();
                }
	}

	@Override
	public Iterator<ArrayList<Key>> outAdjacentVertices(Key vertexKey) {
		if(this.isTransposedGraphy){
                    ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                    Enumeration i = verticList.get(vertexKey).inAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        ArrayList<Key> ak = new ArrayList<Key>();
                        ak.add((Key)arc.source.key);
                        //ak.add((Key)arc.destination.key);
                        arrayList.add(ak);
                    }
                    return arrayList.iterator();
                }else{
                    ArrayList<ArrayList<Key>> arrayList = new ArrayList<ArrayList<Key>>();
                    Enumeration i = verticList.get(vertexKey).outAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        ArrayList<Key> ak = new ArrayList<Key>();
                        //ak.add((Key)arc.source.key);
                        ak.add((Key)arc.destination.key);
                        arrayList.add(ak);
                    }
                    return arrayList.iterator();
                }
	}
         /**
        * return: all the vertice that are adjacent to the key 
        * including in and out adjacent vertices with a search key
        * @param vertexKey a search key
        * @return   return all the vertice that are adjacent to the key
        */
	public Iterator<Key> allAdjacentVertices(Key vertexKey) {
            
                if(this.isTransposedGraphy){
                    ArrayList<Key> arrayList = new ArrayList<Key>();
                    Enumeration i = verticList.get(vertexKey).inAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        arrayList.add((Key)arc.source.key);
                    }
                    return arrayList.iterator();
                }else{
                    ArrayList<Key> arrayList = new ArrayList<Key>();
                    Enumeration i = verticList.get(vertexKey).outAdjList.elements();
                    while(i.hasMoreElements()) {
                        Arc arc = (Arc)i.nextElement();
                        arrayList.add((Key)arc.destination.key);
                    }
                    return arrayList.iterator();
                }
//                Enumeration ii = verticList.get(vertexKey).inAdjList.elements();
//                while(i.hasMoreElements()) {
//                       Arc arc = (Arc)i.nextElement();
//                       arrayList.add((Key)arc.source.key);
//                }
		
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
                Data data =null;
               
                //data = (Data)(Arc)((Vertex)verticList.get(sourceKey).outAdjList.get(destinationKey)).data;
                
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
        
        /**
         * return: the most biggest number of indegree arc that are all pointing to one vertice  
        *    
        * @return    minimized InDegree number
        */
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
        /**
        * return:  the most small number of indegree arc that are all pointing to one vertice 
        * 
        * @return  the degree number
        * 
        */
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
        /**
         * return  the most biggest number of indegree arc that are all pointing to one vertice 
        * 
        * @return the degree number
        */
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
        /**
         * return  the most biggest number of outdegree arc that are all pointing to one vertice 
        * 
        * @return   the degree number
        */
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
        /**
         * return  the average number of outdegree arc that are all pointing to one vertice 
        *
        * @return the degree number
        */
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
        
        /**
         * return  the computed number of graph density
        * 
        * @return the string number of the density
        */
        public String graphDensity(){
             double E = (double)this.arcCount(); 
             double V = (double)this.vertexCount();
             double f = 2*(E/(V * (V-1)));
             f = f/2;
             return new DecimalFormat("#.########").format(f);
        }
   
        

} 
















///*
//        * networkDiameter helper method
//        * @parameter : key
//        * return: none
//        */
//        public void computePaths(Key _source)
//        {
//            Vertex source = verticList.get(_source);
//            source.minDistance = 0.;
//            PriorityQueue<Key> vertexQueue = new PriorityQueue<Key>();
//            vertexQueue.add((Key)source.key);
//
//            while (!vertexQueue.isEmpty()) {
//                Vertex<Key,Data> u = verticList.get(vertexQueue.poll());
//
//                // Visit each edge exiting u
//                Enumeration e = u.outAdjList.elements();
//                while(e.hasMoreElements())
//                {   
//                    Arc a = (Arc)e.nextElement();
//                    Vertex v = (Vertex<Key,Data>)a.destination;
//                    double weight = (double)a.weight;
//                    double distanceThroughU = u.minDistance + weight;
//                    if (distanceThroughU < v.minDistance) {
//                        vertexQueue.remove(v);
//                        v.minDistance = distanceThroughU ;
//                        //v.previous = u;
//                        
//                        vertexQueue.add((Key)v.key);
//                    }
//                }
//            }
//        }
//        /*
//        * networkDiameter helper method
//        * @parameter : key
//        * return: none
//        */
//        public List<Vertex<Key,Data>> getShortestPathTo(Vertex<Key,Data> target)
//        {
//            List<Vertex<Key,Data>> path = new ArrayList<Vertex<Key,Data>>();
//            for (Vertex<Key,Data> vertex = target; vertex != null; vertex = vertex.previous)
//                path.add(vertex);
//            Collections.reverse(path);
//            return path;
//        }
//        /*
//        * networkDiameter helper method
//        * @parameter : key
//        * return: none
//        */
//        public String diameter(){
//            this.computePaths((Key)verticList.elements().nextElement().key);
//            Double diameter = Double.MIN_VALUE;
//            
//            for (Vertex<Key,Data> v : verticList.values())
//            {
//                if(v.minDistance!=Double.POSITIVE_INFINITY){
//                    
//                    if(v.minDistance>diameter){
//                        
//                        diameter=v.minDistance;
//                    }
//                }
//                //System.out.println("Distance to " + v.key + ": " + v.minDistance);
//                //List<Vertex<Key,Data>> path = getShortestPathTo(v);
////                for(Vertex<Key,Data> a : path){
////                    System.out.print("  ShortestPathFrom:" + a.key);
////                }
//                //System.out.println("  --- done");
//            }
//            //System.out.println("Diameter:"+diameter);
//            return new DecimalFormat("#.##").format(diameter);
//        }
//        /*
//        * networkDiameter helper method
//        * @parameter : key
//        * return: none
//        */
//        public void averagePathLength(){
//            Double avg = 0.;
//            Double v = (double)this.vertexCount();
//            Double a = 1/v*(v-1);
//            Double w = 0.;
//            Iterator i = this.arcs();
//            while(i.hasNext()){
//                Object o = i.next();
//                ArrayList<Key> k = (ArrayList<Key>)o;
//                w= (Double)this.getArcWeight(k.get(0), k.get(1));
//                
//            }
//            
//            avg = a*w;
//            //System.out.println("Averge Path Length: "+avg);
//        }
//        /*
//        * 
//        * @parameter : none
//        * return: String of the density number 
//        */
        
        // end of the Class
        
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
      
//        public ArcGraph(String filename)  {
//            
//           isTransposedGraphy = false;
//           verticList = new Hashtable<Key, Vertex>(); 
//
//           String temp=""; 
//           Scanner sc=null;
//           File f = new File("SCC-Test.vna");
//           try{
//                sc = new Scanner(f);
//                
//           
//          //Scanner sc = new Scanner(new File("political-blogs.vna"));
//          //Scanner sc = new Scanner(new File("celegansneural.vna"));
//          //Scanner sc = new Scanner(new File("test.vna"));
//           
//           
//                if(sc.nextLine().contains("*")){
//
//                    // ArrayList<String> labels = new ArrayList<String>();
//                        String line = sc.nextLine();
//                        String[] tokens = this.stringToArray(line);
//
//
//                        while(sc.hasNext()){
//                        //  System.out.println("successfully readin file:"+ sc.hasNext());
//                            temp = sc.nextLine(); 
//                            if(temp.startsWith("*")){
//                                break;
//                            }else{
//                                // ADD VERTEX INTO MY LIST
//                                //String[] itr = temp.split(" "); 
//                                Hashtable<String, String> attributeTable = new Hashtable<String, String>();
//                                String[] tokens2 = this.stringToArray(temp);
//                                for(int i=1; i<tokens.length; i++){
//                                    attributeTable.put(tokens[i],tokens2[i]);
//                                    //System.out.println(tokens2[i]);
//                                }
//                                Data data = (Data)attributeTable;
//                                verticList.put((Key)tokens2[0], new Vertex(tokens2[0],data,new Hashtable<Key,Arc>(),new Hashtable<Key,Arc>()));
//                                //verticList.put((Key)itr[0], new Vertex(itr[0],new Hashtable<Key,Arc>()));
//                            }
//
//                        }
//                }
//                if(temp.contains("*")){
//
//                        int num = 0;
//                        String[] tokens = this.stringToArray(sc.nextLine());
//                        for(int j=0; j<tokens.length; j++){
//                            if(tokens[j].contains("strength")){
//                                num= j;
//                            }
//                        }
//                        while(sc.hasNext()){
//                            String line = sc.nextLine(); 
//                            String[] tokens2 = this.stringToArray(line);
//
//                            Double weight=1.00;
//                            if(num>1){  // check whether there are weight in the input file
//                                weight = Double.parseDouble(tokens2[num]);
//                            }
//                            Hashtable<String, String> attributeTable = new Hashtable<String, String>();
//
//                                for(int i=2; i<tokens.length; i++){
//                                    attributeTable.put(tokens[i],tokens2[i]);
//                                }
//                                Data data = (Data)attributeTable;
//
//                            String source = tokens2[0];
//                            String destination = tokens2[1];
//                            Arc arc = new Arc(verticList.get(source), verticList.get(destination), weight, data);
//                            Key key  = (Key)verticList.get(destination).key;
//                            verticList.get(source).outAdjList.put(key, arc);
//                            verticList.get(source).inAdjList.put(key, arc);
//
//
//                        }
//                }
//           }catch(FileNotFoundException e){
//                System.out.println("input file path: "+f.getAbsolutePath());
//                System.out.println("File not found");
//           }
//         
//        }// end of constructor
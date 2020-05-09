import java.io.*;
class Edge
{ 
	int src, dest, cst;
	
	Edge()
	{
		src=dest=cst=-999;
	}
	
	Edge(int src, int dest, int cst)
	{
		this.src=src;
		this.dest=dest;
		this.cst=cst;
	}
	
	void displayEdge()
	{	
		System.out.println(src+"\t"+dest+"\t"+cst);
	}
	
	static void sortByCost(Edge e[], int n)
	{
		quickSort(e,0, n-1);		
		for(int i=0; i<n; i++)
		{
			e[i].displayEdge();
		}
	}
	
	
	static int partList(Edge e[], int min, int max) 
    { 
		int temp_src,temp_dest,temp_cst;
        int pivot = e[max].cst; 
        int i = (min - 1);
        for (int j = min; j < max; j++)
        { 
        	
            if (e[j].cst <= pivot)
            { 
                i++; 
                temp_cst = e[i].cst; 
                temp_src = e[i].src;
                temp_dest = e[i].dest;
                
                e[i].cst = e[j].cst; 
                e[i].src = e[j].src; 
                e[i].dest = e[j].dest; 
                
                e[j].cst = temp_cst; 
                e[j].src = temp_src;  
                e[j].dest = temp_dest; 
            } 
        } 
  
        temp_cst = e[i + 1].cst;
        temp_src = e[i + 1].src;
        temp_dest = e[i + 1].dest;
        
        e[i + 1].cst = e[max].cst; 
        e[i + 1].src = e[max].src; 
        e[i + 1].dest = e[max].dest; 
        
        e[max].cst = temp_cst; 
        e[max].src = temp_src; 
        e[max].dest = temp_dest; 
  
        return i + 1; 
    } 
	
	//sorting
	static void quickSort(Edge e[], int min, int max) 
    { 
		int part;
        if (min < max) { 
            part = partList(e, min, max); 
  
            // Recursively sort elements before 
            // partition and after partition 
            quickSort(e, min, part - 1); 
            quickSort(e, part + 1, max); 
        } 
    }
	
	//MST creation function
	static void createMST(Edge e[], int n, int v)
	{
		Edge []mst = new Edge[n-1];//the minimum spanning tree array 
		
		int parent[] = new int[n];//array to hold the parent edges
		for(int i = 0; i < n; i++)
			parent[i] = i;
		
		int i = 0, count = 0;
		
		while(count != n - 1 && i < n )
		{
			Edge currentEdge = e[i];
			//apply union find, check for cycles
			int parent_src = parentOf(currentEdge.src, parent);
			int parent_dest = parentOf(currentEdge.dest, parent);
			
			if(parent_src != parent_dest)
			{
				mst[count++] = currentEdge;
				parent[parent_src] = parent_dest;
			}
			i++;
		}
		System.out.println("The MST is -------------------:-\n"+v+"\t"+n);
		
		for(i = 0; i < n-1; i++)
		{
			mst[i].displayEdge();
		}
	}

	public static int parentOf(int v, int parent[]) 
	{
		
		if(parent[v] == v)
			return v;
		
		return parentOf(parent[v], parent);
	}
	
}//end of class Edge

public class MST_KruskalsAlgo extends Edge
{	
	
	public static void main(String[] args) throws IOException
	{
		int n, v, src, dest, cst;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		
		System.out.println("Enter the number of vertice");
		v = Integer.parseInt(br.readLine());//number of vertices
		System.out.println("Enter the number of edges");
		n = Integer.parseInt(br.readLine());//number of edges
		
		Edge []g1 = new Edge[n];
		
		//input of edges
		for(int i=0; i<n; i++)
		{
			System.out.println("Enter source");
			src = Integer.parseInt(br.readLine());
			System.out.println("Enter destination");
			dest = Integer.parseInt(br.readLine());
			System.out.println("Enter weight");
			cst = Integer.parseInt(br.readLine());
			g1[i] =	new Edge(src, dest, cst);
		}
		System.out.println("The Graph input is given as :-\n"+v+"\t"+n);
		
		for(int i=0; i<n; i++)
		{
			g1[i].displayEdge();
		}

		System.out.println("The Sorted Graph wrt to cost of edge is:-\n"+v+"\t"+n);
		sortByCost(g1, n);
		
		createMST(g1, n, v);
		
		//modifying the original input graph
		for(int i=0; i<n; i++)
		{
			g1[i].cst++;
		}
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("The Graph input after increasing the edge cost of each edge by 1"+v+"\t"+n);
		
		for(int i=0; i<n; i++)
		{
			g1[i].displayEdge();
		}
		
		System.out.println("The Sorted Graph wrt to cost of edge is:-\n"+v+"\t"+n);
		sortByCost(g1, n);
		createMST(g1, n, v);
	}//end of main
}

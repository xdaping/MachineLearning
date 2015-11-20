package km.methods1;


import java.util.Arrays;
public class KuhnMunkres {  
    
    private int maxN, n, lenX, lenY;  
    private int[][] weights;  
    private boolean[] visitX, visitY;  
    private int[] lx, ly;  
    private int[] slack;  
    public int[] match;  
      
    public KuhnMunkres( int maxN )  
    {  
        this.maxN = maxN;  
        visitX = new boolean[maxN];  
        visitY = new boolean[maxN];  
        lx = new int[maxN];  
        ly = new int[maxN];  
        slack = new int[maxN];  
        match = new int[maxN];  
    }  
      
    public int[][] getMaxBipartie( int weight[][])  
    {  
        if( !preProcess(weight) )  
        {  
            return null;  
        }  
        //initialize memo data for class  
        //initialize label X and Y  
        Arrays.fill(ly, 0);  
        Arrays.fill(lx, 0);  
        for( int i=0; i<n; i++ )  
        {  
            for( int j=0; j<n; j++ )  
            {  
                if( lx[i]<weights[i][j])  
                    lx[i] = weights[i][j];  
            }  
        }  
          
        //find a match for each X point  
        for( int u=0; u<n; u++ )  
        {  
            Arrays.fill(slack, 0x7fffffff);  
            while(true)  
            {  
                Arrays.fill(visitX, false);  
                Arrays.fill(visitY, false);  
                if( findPath(u) )   //if find it, go on to the next point  
                    break;  
                //otherwise update labels so that more edge will be added in  
                int inc = 0x7fffffff;  
                for( int v=0; v<n; v++ )  
                {  
                    if( !visitY[v] && slack[v] < inc )  
                        inc = slack[v];  
                }  
                for( int i=0; i<n; i++ )  
                {  
                    if( visitX[i] )  
                        lx[i] -= inc;  
                    if( visitY[i] )  
                        ly[i] += inc;  
                }  
            }  
        }  
       
        return matchResult();  
    }  
      
    public int[][] matchResult()  
    {  
        int len = Math.min(lenX, lenY);  
        int[][] res = new int[len][2];  
        int count=0;  
        for( int i=0; i<lenY; i++ )  
        {  
            if( match[i] >=0 && match[i]<lenX )  
            {  
                res[count][0] = match[i];  
                res[count++][1] = i;  
            }  
        }  
        return res;  
    }  
      
    private boolean preProcess( int[][] weight )  
    {  
        if( weight == null )  
            return false;  
        lenX = weight.length; lenY = weight[0].length;  
        if( lenX>maxN || lenY>maxN )  
            return false;  
        Arrays.fill(match, -1);  
        n = Math.max(lenX, lenY);  
        weights = new int[n][n];  
        for( int i=0; i<n; i++ )  
            Arrays.fill(weights[i], 0);  
        for( int i=0; i<lenX; i++ )  
            for( int j=0; j<lenY; j++ )  
                weights[i][j] = weight[i][j];  
        return true;  
    }  
      
    private boolean findPath( int u )  
    {  
        visitX[u] = true;  
        for( int v=0; v<n; v++ )  
        {  
            if( !visitY[v] )  
            {  
                int temp = lx[u]+ly[v]-weights[u][v];  
                if( temp == 0 )  
                {  
                    visitY[v] = true;  
                    if( match[v] == -1 || findPath(match[v]) )  
                    {  
                        match[v] = u;  
                        return true;  
                    }  
                }  
                else  
                    slack[v] = Math.min(slack[v], temp);  
            }  
        }  
        return false;  
    }

	public static int[][] runKMAlgorithm(int[][] data) {
		KuhnMunkres algorithm = new KuhnMunkres(Math.max(data.length,data[0].length));
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				data[i][j]=-data[i][j];
			}
		}
		algorithm.getMaxBipartie(data);		
		return algorithm.matchResult();
	}   
}  

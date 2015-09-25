package hungary.methods2;

public class DFS {

}
/*
//---------------------DFS---------------------------------
#include<iostream>
#include<memory.h>
using namespace std;

#define MAXN 10
int graph[MAXN][MAXN];
int match[MAXN];
int visitX[MAXN], visitY[MAXN];
int nx, ny;

bool findPath( int u )
{
	visitX[u] = 1;
	for( int v=0; v<ny; v++ )
	{
		if( !visitY[v] && graph[u][v] )
		{
			visitY[v] = 1;
			if( match[v] == -1 || findPath(match[v]) )
			{
				match[v] = u;
				return true;
			}
		}
	}
	return false;
}

int dfsHungarian()
{
	int res = 0;
	memset( match, -1, sizeof(match) );
	for( int i=0; i<nx; i++ )
	{
		memset( visitX, 0, sizeof(visitX) );
		memset( visitY, 0, sizeof(visitY) );
		if( findPath(i) )
			res++;
	}
	return res;
}
*/
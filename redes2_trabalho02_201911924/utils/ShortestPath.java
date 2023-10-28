package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 23/02/2023
* Ultima alteracao.: 05/03/2023
* Nome.............: ShortestPath
* Funcao...........: Classe que faz a busca pelo menor caminho
*************************************************************** */

public class ShortestPath {

  
  /* ***************************************************************
  * Metodo: shortestPath
  * Funcao: Encontrar o menor caminho entre os roteadores
  * Parametros: Uma matriz o vertice de origem e o vertice de destino
  * Retorno: Uma lista com o menor caminho
  *************************************************************** */
  public List<Integer> shortestPath(int[][] graph, int origin, int destination) {
    int lenght = graph.length;

    int[] distance = new int[lenght]; // Vetor de distancia
    boolean[] visited = new boolean[lenght]; // Vertices visitados
    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[origin] = 0; // Distancia pra si mesmo eh sempre 0

    List<Integer>[] shortestPaths = new List[lenght];
    for (int i = 0; i < lenght; i++) {
      shortestPaths[i] = new ArrayList<>();
      shortestPaths[i].add(origin);
    }

    for (int i = 0; i < lenght - 1; i++) { // Algoritmo de dijkstra
      
      int u = findMinimumDistance(distance, visited);
      visited[u] = true;

      for (int v = 0; v < lenght; v++) {
        if (!visited[v] && graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE
            && distance[u] + graph[u][v] < distance[v]) {
          distance[v] = distance[u] + graph[u][v];
          shortestPaths[v].clear();
          shortestPaths[v].addAll(shortestPaths[u]);
          shortestPaths[v].add(v);
        }
      }
    }
    return shortestPaths[destination]; // Caminho minimo
  }

  /* ***************************************************************
  * Metodo: findMinimumDistance
  * Funcao: Encontrar o vertice com menor distancia
  * Parametros: Um vetor de distancia e um vetor de booleano
  * Retorno: Um inteiro representando a distancia minima encontrada
  *************************************************************** */
  private int findMinimumDistance(int[] distance, boolean[] visited) {
    int minDistance = Integer.MAX_VALUE;
    int minVertex = -1;

    for (int i = 0; i < distance.length; i++) {
      if (!visited[i] && distance[i] < minDistance) {
        minDistance = distance[i];
        minVertex = i;
      }
    }

    return minVertex;
  }
}

package utils;

/* ***************************************************************
* Autor............: Lailson Santana Alves
* Matricula........: 201911924
* Inicio...........: 18/02/2023
* Ultima alteracao.: 19/02/2023
* Nome.............: Pacote
* Funcao...........: Simular um pacote
*************************************************************** */

public class Pacote {

  private int enderecoOrigem;
  private int enderecoDestino;
  private int ttl;

  public Pacote(int eO , int eD, int ttl){
    enderecoOrigem = eO;
    enderecoDestino = eD;
    this.ttl = ttl;
  }

  public int getEnderecoOrigem() {
    return enderecoOrigem;
  }

  public void setEnderecoOrigem(int enderecoOrigem) {
    this.enderecoOrigem = enderecoOrigem;
  }

  public int getEnderecoDestino() {
    return enderecoDestino;
  }

  public void setEnderecoDestino(int enderecoDestino) {
    this.enderecoDestino = enderecoDestino;
  }

  public int getTtl() {
    return ttl;
  }

  public void setTtl(int ttl) {
    this.ttl = ttl;
  }

  public void subtrairTTL(){
    this.ttl = ttl - 1;
  }
}

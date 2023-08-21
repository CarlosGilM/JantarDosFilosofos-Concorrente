/* ***************************************************************
* Autor............: Carlos Gil Martins da Silva
* Matricula........: 202110261
* Inicio...........: 06/05/2023
* Ultima alteracao.: 13/05/2023
* Nome.............: Thread Filosofo
* Funcao...........: Thread Filosofo controla a Execucao dos Filosofos
Sendo diferenciados pelo seu Id, controla as velocidades de cada acao
do filosofo e altera seus estagios
****************************************************************/

package thread;
import control.controllerGeral;

public class Filosofo extends Thread {
  private int speedCOMENDO = 5000; // Tempo Inicial de Comendo
  private int speedPENSANDO = 5000; // Tempo Inicial de Pensando
  private final int id; // Id do Filósofo

  public Filosofo(int id) { // Construtor da Classe com Identificador
    this.id = id;
}

  controllerGeral cG = new controllerGeral(); // Instanciando e Criando o Controller
  // Metodo Utilizado para Setar um Controlador em Comum em Todas Thread
  public void setControlador(controllerGeral controle) {
    this.cG = controle;
  }

  @Override
  public void run() { // Start RUN
    while (true) { // Repetir o Processo inumeras vezes
        pensando(); // Chama o Metodo Pensando
        try {
          cG.pegaGarfos(id); // Chama o Pega Garfos
          comer(); // Chama o Método Comendo
          cG.devoveGarfos(id); // Chama o Devolve Garfos
        } 
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        //cG.setPausado(id); // Seta Imagem de Pausado Encima do Filosofo
    } // Fim While
  } // Fim Run

// Metodo De Estado Pensante
public void pensando(){
 cG.setPensando(id); // Seta as Imagens de Pensando

 // Atualizacao real do tempo de Pensando
// Durante o Mesmo Ciclo de Pensando
 int auxTIME = 0;
 while(auxTIME < speedPENSANDO){
   try {
    Filosofo.sleep(1000);
    auxTIME += 1000;
  } 
  catch (InterruptedException e) {
    e.printStackTrace();}
  }
}

// Método de Estado Comendo
public void comer(){
 cG.setComendo(id); // Seta as Imagens de Comendo

// Atualizacao real do tempo de Comendo
// Durante o Mesmo Ciclo de Comendo
 int auxTIME = 0;
 while(auxTIME < speedCOMENDO){
   try {
    Filosofo.sleep(1000);
    auxTIME += 1000;
  } 
  catch (InterruptedException e) {
    e.printStackTrace();}
  }
 }


public void atualizaComendo( int valor){
this.speedCOMENDO = valor;
}
public void atualizaPensando( int valor){
this.speedPENSANDO = valor;
  }
}
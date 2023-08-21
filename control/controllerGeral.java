/* ***************************************************************
* Autor............: Carlos Gil Martins da Silva
* Matricula........: 202110261
* Inicio...........: 06/05/2023
* Ultima alteracao.: 13/05/2023
* Nome.............: Controlador do Filosofo
* Funcao...........: Controlador que controla tudo que tem no programa,
cria todas as threads com seus respectivos ID'S, starta todas elas
e controla toda parte gráfica com transições de imagens, Sliders de
controle de velocidade, pausa as threads e reinicia a execucao
gerencia por meio dos metodos descritos em aula os semaforos controlando
o problema da Concorrencia
****************************************************************/

package control;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import thread.*;

public class controllerGeral implements Initializable {
  @FXML private ImageView imgBackground;

  //Imagem de Garfos na Mão dos Filososo
  @FXML private ImageView comendo0;
  @FXML private ImageView comendo1;
  @FXML private ImageView comendo2;
  @FXML private ImageView comendo3;
  @FXML private ImageView comendo4;

  // Garfos da Mesa (Individuais)
  @FXML private ImageView garfo0;
  @FXML private ImageView garfo1;
  @FXML private ImageView garfo2;
  @FXML private ImageView garfo3;
  @FXML private ImageView garfo4;

  // Estado do filosofo (Rotulo da Cabeça)
  @FXML private ImageView estado0;
  @FXML private ImageView estado1;
  @FXML private ImageView estado2;
  @FXML private ImageView estado3;
  @FXML private ImageView estado4;

  @FXML private Slider sliderComendo0;
  @FXML private Slider sliderComendo1;
  @FXML private Slider sliderComendo2;
  @FXML private Slider sliderComendo3;
  @FXML private Slider sliderComendo4;

  @FXML private Slider sliderPensando0;
  @FXML private Slider sliderPensando1;
  @FXML private Slider sliderPensando2;
  @FXML private Slider sliderPensando3;
  @FXML private Slider sliderPensando4;

  @FXML private Button pausar0;
  @FXML private Button pausar1;
  @FXML private Button pausar2;
  @FXML private Button pausar3;
  @FXML private Button pausar4;
  @FXML private Button reiniciar;

  // Criação dos Semaforos
  private Semaphore mutex = new Semaphore(1);
  private Semaphore[] arraySemaforos = new Semaphore[5];
  private int[] estados = new int[5]; // Criação do Array de Estados
  private boolean[] flagAguardando = new boolean[5]; // Criação de Flags Para Setar o Estado de Aguardando

  // definindo os estados para o array de Estados
  private int pensando = 0;
  private int fome = 1;
  private int comendo = 2;

  // Instanciando Threads
  private Filosofo filosofo1;
  private Filosofo filosofo2;
  private Filosofo filosofo3;
  private Filosofo filosofo4;
  private Filosofo filosofo5;

  // Meotdo Que Reseta Todos Os Semaforos e Reinicia Todas as variaveis Para
  // Resetar o Programa
  // Tambem Para as Threads e Recria as Mesmas e Inicia Elas Novamente
  @FXML
  void Reiniciar(MouseEvent event) {
    // Parando as Threads
    filosofo1.stop();
    filosofo2.stop();
    filosofo3.stop();
    filosofo4.stop();
    filosofo5.stop();

    //Resetando os Semaforos
    mutex = new Semaphore(1);
    arraySemaforos = new Semaphore[5];
    estados = new int[5];
    flagAguardando = new boolean[5];

    //Dfeinindo o Array de Semaforos
    for (int i = 0; i < 5; i++) {
      arraySemaforos[i] = new Semaphore(pensando);
    }

    //Resetando os Valores Padroes dos Sliders, Imagens e Botões
    sliderComendo0.setValue(5);
    sliderComendo1.setValue(5);
    sliderComendo2.setValue(5);
    sliderComendo3.setValue(5);
    sliderComendo4.setValue(5);
    sliderPensando0.setValue(5);
    sliderPensando1.setValue(5);
    sliderPensando2.setValue(5);
    sliderPensando3.setValue(5);
    sliderPensando4.setValue(5);

    garfo0.setVisible(true);
    garfo1.setVisible(true);
    garfo2.setVisible(true);
    garfo3.setVisible(true);
    garfo4.setVisible(true);

    pausar0.setText("Pausar");
    pausar1.setText("Pausar");
    pausar2.setText("Pausar");
    pausar3.setText("Pausar");
    pausar4.setText("Pausar");

    //Criando os Novos Filofosos
    this.filosofo1 = new Filosofo(0);
    this.filosofo2 = new Filosofo(1);
    this.filosofo3 = new Filosofo(2);
    this.filosofo4 = new Filosofo(3);
    this.filosofo5 = new Filosofo(4);

    // Setando os Controladores
    filosofo1.setControlador(this);
    filosofo2.setControlador(this);
    filosofo3.setControlador(this);
    filosofo4.setControlador(this);
    filosofo5.setControlador(this);

    // Startando as Threads
    filosofo1.start();
    filosofo2.start();
    filosofo3.start();
    filosofo4.start();
    filosofo5.start();
  } // Fim Metodo Reiniciar

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // For Para a criação dos Semáforos e definição do valor inicial 0 (Pensando) em todos Eles
    for (int i = 0; i < 5; i++) {
      arraySemaforos[i] = new Semaphore(pensando);
    }

    // Criando as Classes com os Ids
    this.filosofo1 = new Filosofo(0);
    this.filosofo2 = new Filosofo(1);
    this.filosofo3 = new Filosofo(2);
    this.filosofo4 = new Filosofo(3);
    this.filosofo5 = new Filosofo(4);

    // Setando os Controladores
    filosofo1.setControlador(this);
    filosofo2.setControlador(this);
    filosofo3.setControlador(this);
    filosofo4.setControlador(this);
    filosofo5.setControlador(this);

    // Startando as Threads
    filosofo1.start();
    filosofo2.start();
    filosofo3.start();
    filosofo4.start();
    filosofo5.start();

    // Listeners Para Pegar e Atualizar o tempo Real o Valor dos Sliders de tempo de Comendo
    // É necessario um listener para cada Slider a fim de Pegar o valor do Mesmo em tempo Real
    sliderComendo0.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo1.atualizaComendo((int) sliderComendo0.getValue() * 1000);
      }
    });
    sliderComendo1.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo2.atualizaComendo((int) sliderComendo1.getValue() * 1000);
      }
    });
    sliderComendo2.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo3.atualizaComendo((int) sliderComendo2.getValue() * 1000);
      }
    });
    sliderComendo3.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo4.atualizaComendo((int) sliderComendo3.getValue() * 1000);
      }
    });
    sliderComendo4.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo5.atualizaComendo((int) sliderComendo4.getValue() * 1000);
      }
    });

    // Listeners Para Pegar e Atualizar o tempo Real o Valor dos Sliders de tempo de Pensando
    // É necessario um listener para cada Slider a fim de Pegar o valor do Mesmo em  tempo Real
    sliderPensando0.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo1.atualizaPensando((int) sliderPensando0.getValue() * 1000);
      }
    });

    sliderPensando1.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo2.atualizaPensando((int) sliderPensando1.getValue() * 1000);
      }
    });
    sliderPensando2.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo3.atualizaPensando((int) sliderPensando2.getValue() * 1000);
      }
    });
    sliderPensando3.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo4.atualizaPensando((int) sliderPensando3.getValue() * 1000);
      }
    });
    sliderPensando4.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo5.atualizaPensando((int) sliderPensando4.getValue() * 1000);
      }
    });
  } // Fim Initialize

  public void pegaGarfos(int i) throws InterruptedException {
    mutex.acquire(); // Inicio Região Critica
    estados[i] = fome; // Estado de Fome Para o Filósofo
    testaGarfos(i); // Tenta Pegar os 2 Garfos
    mutex.release(); // Fim Região Critica

    if (flagAguardando[i] == true) { // Flag Para Verificar Se O estado Foi Desbloqueado ou Não pelo TestaGarfos
      setAguardando(i); // Seta o Estado de Aguardando
    }

    arraySemaforos[i].acquire(); // Bloqueia se não pegar os 2 Garfos

  }

  public void devoveGarfos(int i) throws InterruptedException {
    mutex.acquire(); // Inicio Região Crítica
    estados[i] = pensando; // Libera os 2 Garfos
    returnIMGgarfos(i);
    testaGarfos((i - 1 + 5) % 5); // Testando Garfos da Esquerda
    testaGarfos((i + 1) % 5); // Testando Garfos da Direita
    mutex.release(); // Fim Região Crítica
  }

  public void testaGarfos(int i) {
    if (estados[i] == fome && estados[(i - 1 + 5) % 5] != comendo && estados[(i + 1) % 5] != comendo) {
      estados[i] = comendo; // Filósofo Pega os 2 Garfos
      arraySemaforos[i].release();
      flagAguardando[i] = false; // Define a Flag de Verificacao Como False
    } else {
      flagAguardando[i] = true; // Caso Não Pegue os 2 Garfos Define a Flag de Verificacao Como True
    }
  }

  //Seta Estado de Pensando na Cabeça do Filósofo
  public void setPensando(int id) {
    Platform.runLater(() -> {
      switch (id) {
        case 0:
          comendo0.setVisible(false);
          estado0.setImage(new Image("./assets/estadosF0/pensando.png"));
          break;
        case 1:
          comendo1.setVisible(false);
          estado1.setImage(new Image("./assets/estadosF1/pensando.png"));
          break;
        case 2:
          comendo2.setVisible(false);
          estado2.setImage(new Image("./assets/estadosF2/pensando.png"));
          break;
        case 3:
          comendo3.setVisible(false);
          estado3.setImage(new Image("./assets/estadosF3/pensando.png"));
          break;
        case 4:
          comendo4.setVisible(false);
          estado4.setImage(new Image("./assets/estadosF4/pensando.png"));
          break;
      }
    });
  }

  //Seta Estado de Comendo na Cabeça do Filósofo
  public void setComendo(int id) {
    Platform.runLater(() -> {
      switch (id) {
        case 0:
          estado0.setImage(new Image("./assets/estadosF0/comendo.png"));
          garfo0.setVisible(false);
          garfo1.setVisible(false);
          comendo0.setVisible(true);
          break;
        case 1:
          estado1.setImage(new Image("./assets/estadosF1/comendo.png"));
          garfo1.setVisible(false);
          garfo2.setVisible(false);
          comendo1.setVisible(true);
          break;
        case 2:
          estado2.setImage(new Image("./assets/estadosF2/comendo.png"));
          garfo2.setVisible(false);
          garfo3.setVisible(false);
          comendo2.setVisible(true);
          break;
        case 3:
          estado3.setImage(new Image("./assets/estadosF3/comendo.png"));
          garfo3.setVisible(false);
          garfo4.setVisible(false);
          comendo3.setVisible(true);
          break;
        case 4:
          estado4.setImage(new Image("./assets/estadosF4/comendo.png"));
          garfo4.setVisible(false);
          garfo0.setVisible(false);
          comendo4.setVisible(true);
          break;
      }
    });
  }

  //Seta Estado de Aguardando na Cabeça do Filósofo
  public void setAguardando(int id) {
    Platform.runLater(() -> {
      switch (id) {
        case 0:
          estado0.setImage(new Image("./assets/estadosF0/aguardando.png"));
          break;
        case 1:
          estado1.setImage(new Image("./assets/estadosF1/aguardando.png"));
          break;
        case 2:
          estado2.setImage(new Image("./assets/estadosF2/aguardando.png"));
          break;
        case 3:
          estado3.setImage(new Image("./assets/estadosF3/aguardando.png"));
          break;
        case 4:
          estado4.setImage(new Image("./assets/estadosF4/aguardando.png"));
          break;
      }
    });
  }

  //Retorna Padrão Garfos
  public void returnIMGgarfos(int id) {
    Platform.runLater(() -> {
      switch (id) {
        case 0:
          garfo0.setVisible(true);
          garfo1.setVisible(true);
          comendo0.setVisible(false);
          break;
        case 1:
          garfo1.setVisible(true);
          garfo2.setVisible(true);
          comendo1.setVisible(false);
          break;
        case 2:
          garfo2.setVisible(true);
          garfo3.setVisible(true);
          comendo2.setVisible(false);
          break;
        case 3:
          garfo3.setVisible(true);
          garfo4.setVisible(true);
          comendo3.setVisible(false);
          break;
        case 4:
          garfo4.setVisible(true);
          garfo0.setVisible(true);
          comendo4.setVisible(false);
          break;
      }
    });
  }

  //Seta Estado de Pausado na Cabeça do Filósofo
  public void setPausado(int id) {
    Platform.runLater(() -> {
      switch (id) {
        case 0:
          estado0.setImage(new Image("./assets/estadosF0/pausado.png"));
          break;
        case 1:
          estado1.setImage(new Image("./assets/estadosF1/pausado.png"));
          break;
        case 2:
          estado2.setImage(new Image("./assets/estadosF2/pausado.png"));
          break;
        case 3:
          estado3.setImage(new Image("./assets/estadosF3/pausado.png"));
          break;
        case 4:
          estado4.setImage(new Image("./assets/estadosF4/pausado.png"));
          break;
      }
    });
  }


  // Botões de Pausar Cada Filósofo Individualmente
  @FXML
  void clickPausar0(MouseEvent event) {
    if (pausar0.getText().equals("Pausar")) { // Verifica o Nome do Botão
      filosofo1.suspend(); // Suspende
      setPausado(0); // Altera a imagem
      pausar0.setText("Retomar"); // Troca o Nome do Botão
    } else {
      //Verifica o Estado Para Voltar ao que estava
      if (estados[0] == 0) {
        setPensando(0);
      } else if (estados[0] == 1) {
        setAguardando(0);
      } else {
        setComendo(0);
      }
      filosofo1.resume(); // Retorna a Thread
      pausar0.setText("Pausar"); //Altera o nome do Botão
    }
  }

  @FXML
  void clickPausar1(MouseEvent event) {
    if (pausar1.getText().equals("Pausar")) {
      filosofo2.suspend();
      setPausado(1);
      pausar1.setText("Retomar");
    } else {
      if (estados[1] == 0) {
        setPensando(1);
      } else if (estados[1] == 1) {
        setAguardando(1);
      } else {
        setComendo(1);
      }
      filosofo2.resume();
      pausar1.setText("Pausar");
    }
  }

  @FXML
  void clickPausar2(MouseEvent event) {
    if (pausar2.getText().equals("Pausar")) {
      filosofo3.suspend();
      setPausado(2);
      pausar2.setText("Retomar");
    } else {
      if (estados[2] == 0) {
        setPensando(2);
      } else if (estados[2] == 1) {
        setAguardando(2);
      } else {
        setComendo(2);
      }
      filosofo3.resume();
      pausar2.setText("Pausar");
    }
  }

  @FXML
  void clickPausar3(MouseEvent event) {
    if (pausar3.getText().equals("Pausar")) {
      filosofo4.suspend();
      setPausado(3);
      pausar3.setText("Retomar");
    } else {
      if (estados[3] == 0) {
        setPensando(3);
      } else if (estados[3] == 1) {
        setAguardando(3);
      } else {
        setComendo(3);
      }
      filosofo4.resume();
      pausar3.setText("Pausar");
    }
  }

  @FXML
  void clickPausar4(MouseEvent event) {
    if (pausar4.getText().equals("Pausar")) {
      filosofo5.suspend();
      setPausado(4);
      pausar4.setText("Retomar");
    } else {
      if (estados[4] == 0) {
        setPensando(4);
      } else if (estados[4] == 1) {
        setAguardando(4);
      } else {
        setComendo(4);
      }
      filosofo5.resume();
      pausar4.setText("Pausar");
    }
  }
} // The End

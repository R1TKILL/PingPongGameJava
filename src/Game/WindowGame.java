package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

//O canva sera util na parte gráfica do game e componentes.
//A interface Runnable é essencial para telas Gráficas, pois permite uso de threads e fps, bom para jogos.
//Para de fato começar a movimentar algo,implementamos KeyListenner, sendo necessário para isso.

public class WindowGame extends Canvas implements Runnable, KeyListener {
	public static int widthWindow = 900;
    public static int heightWindow = 600;

    public static JFrame frame;
    public static Player player;
    public static Enemy enemy;
    public static Ball ball;

    //Definindo as Dimensões da tela.
    //Também é necessario adicionar a interface KeyListener para ativar.
    
    public WindowGame(){
        this.setPreferredSize(new Dimension(widthWindow, heightWindow));
        this.addKeyListener(this); //O this porque é aqui mesmo que esta implementada.

        //Objects.
        player = new Player(20, 200); //Posição de onde vai começar na tela, não suas proporções.
        enemy = new Enemy(870, 200);
        ball = new Ball(450, 300);
    }

    public static void main(String args[]){
        WindowGame window = new WindowGame();

        frame = new JFrame();
        frame.setVisible(true);
        frame.add(window);
        //Isto faz com que a janela fique correta em relação ao canvas,que tá por cima com as formas.
        frame.pack();
        frame.setLocationRelativeTo(null);//Fazendo a janela iniciar no centro.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Senão a janela some mas não para.
        //Iniciando a thread criado em run, passando o objeto da classe que implementou o runnable.
        new Thread(window).start();
    }

    @Override
    public void run() {
        //Run é o Método onde fazemos essas implementações sobreescritas de runnable.
        //Passamos dentro de um while pra este método já iniciar em um loop, serve para uns movimentos.
        
        /*Está thread servirá para fps, rodando as funções de desenhar() e atualizar(),
         *neste caso, dividindo o segundo por 30, fazemos ela rodar trinta vezes por segundo.*/
        try {
        	while(true){
        		draw();
        		update();
        		Thread.sleep(1000/60);
        		}
        	} 
        catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    private void draw() {
        //Para evitar da tela ficar piscando na hora de redesenhar as formas nas posições.
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        //Aqui começamos de fato a desenhar o fundo da tela..
        Graphics g = bs.getDrawGraphics(); //Pega o bs para desenha de forma sem piscar.
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, widthWindow, heightWindow);//Retangulo preeenchido que cobrirá o fundo.

        //Rede de vôlei.
        int heightNetVoley = 20;
        for(int i = 0; i < 11; i++){
            g.setColor(Color.WHITE);
            g.fillRect(440, heightNetVoley, 20, 40);
            heightNetVoley+=60;
        }

        //Inicindo o desenho do Player e do Enemy.
        player.drawPlayer(g);
        enemy.drawEnemy(g);
        ball.drawBall(g);

        //Pontos do player.
        g.setColor(Color.BLUE);
		g.setFont(new Font("MV Boli", Font.BOLD, 55));
        if(Ball.pointsPlayer < 10){g.drawString("0"+Ball.pointsPlayer, 320, 80);}
		else{g.drawString(""+Ball.pointsPlayer, 340, 80);}

        //Pontos do inimigo.
        g.setColor(Color.RED);
		g.setFont(new Font("MV Boli", Font.BOLD, 55));
        if(Ball.pointsEnemy < 10){g.drawString("0"+Ball.pointsEnemy, 500, 80);}
		else{g.drawString(""+Ball.pointsEnemy, 480, 80);}

        bs.show();//Necessário exibir o buffer configurado com a redenização.
    }

    //Vai fica atualiznado pela thread neste método.
    public void update() throws InterruptedException{
        player.updatePlayerPosition();
        enemy.updateEnemyPosition();
        ball.updateBallPosition();
    }

    //Aqui de fato vamos aplicar na sobreescrita o player para mover-se, no que é gerado pelo KeyListenner.
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){player.up = true;}
        else if(e.getKeyCode() == KeyEvent.VK_S){player.down = true;}
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){player.up = false;}
        else if(e.getKeyCode() == KeyEvent.VK_S){player.down = false;}
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    public double x;
    public double y;
    public int widthBall;
    public int heightBall;
    public double directionBallX;
    public double directionBallY;
    public double speedBall = 5.5;
    public static int pointsPlayer = 0;
    public static int pointsEnemy = 0;
    int angle;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
        this.widthBall = 20;
        this.heightBall = 20;

        //Determinando como a bola irá andar.
        /*Primeiro vou definir o angulo da bola de forma aléatoria no inicio do jogo,
         * após isso vou utilizar-me de cosseno e seno, para poder fazer com que esta
         * bola possa se mover de forma Radiana, ou seja com mecânica de circulos, tudo
         * isso em relação ao ângulo.*/
        angle = new Random().nextInt(80);
        directionBallX = Math.cos(Math.toRadians(angle));//Resumindo, toRadians faz mover como circulo.
        directionBallY = Math.sin(Math.toRadians(angle));
    }

    public void updateBallPosition() throws InterruptedException{
        //Controle para não sair da tela e ricochetear de volta.
        
        /*Se o eixo das ordenadas(y) da bola, somado a sua direcional ordenada e multiplicado pela
         *velocidade da bola for maior ou igual ao tamanho da tela, ou seja se chocar na parte
         *inferior da tela, inverte a direcional das ordenadada bola ao contrario.
         *
         *Senão se a mesma condição na parte superior da tela, inverte o direcional da bola também.
        */
        if(y+(directionBallY*speedBall)+heightBall >= WindowGame.heightWindow){directionBallY *= -1;}
        else if(y+(directionBallY*speedBall)+heightBall < 20 ){directionBallY *= -1;}

        /*Para facilitar as colisões com o player e o enemy, vou mapea-los com rectangle, para checar
         * especificamente, mapear seus eixos somados a seus direcionais multiplicados pela velocidade
         * da bola e suas especificaçãoes: largura e altura.
        */
        Rectangle checkBall = new Rectangle
        ((int)(x+(directionBallX*speedBall)),(int)(y+(directionBallY*speedBall)),widthBall,heightBall);
    
        Rectangle checkPlayer = new Rectangle
        (WindowGame.player.x,WindowGame.player.y,WindowGame.player.widthPlayer,WindowGame.player.heightPlayer);
        
        Rectangle checkEnemy = new Rectangle
        ((int)WindowGame.enemy.x,(int)WindowGame.enemy.y,WindowGame.enemy.widthEnemy,WindowGame.enemy.heightEnemy);
        
        //Verificando de fato as collisões, como foram mapeadas agora fica simples.
        //Aqui posso fazer aumentar a velocidade também ou diretamente na bola.
        if(checkBall.intersects(checkPlayer)){
        	angle = new Random().nextInt(80);
        	directionBallX = Math.cos(Math.toRadians(angle));
            directionBallY = Math.sin(Math.toRadians(angle));
        	directionBallX*=1;
        	}
        else if(checkBall.intersects(checkEnemy)){
        	angle = new Random().nextInt(80);
        	directionBallX = Math.cos(Math.toRadians(angle));
            directionBallY = Math.sin(Math.toRadians(angle));
        	directionBallX*=-1;
        	}

        //Pontuação.
        if(x < 0){pointsEnemy+=1; Thread.sleep(3000); new WindowGame();}
        else if(x > 900){pointsPlayer+=1; Thread.sleep(3000); new WindowGame();}

        //Com isso a bola começa a se mover, pelo angulo escolhido.
        //Pois os eixos da bola se somam a sua direção multiplicada a sua velocidade.
        y += directionBallY * speedBall; 
        x += directionBallX * speedBall;
    }  
    
    public void drawBall(Graphics g){
        g.setColor(new Color(255, 255, 255));
        g.fillOval((int)x, (int)y, widthBall, heightBall);
    }
}

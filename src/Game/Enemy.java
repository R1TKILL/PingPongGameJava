package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    public double x;
    public double y;
    public int widthEnemy;
    public int heightEnemy;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
        this.widthEnemy = 10;
        this.heightEnemy = 170;
    }

    public void drawEnemy(Graphics g){
        g.setColor(new Color(255, 255, 255));
        g.fillRect((int)x, (int)y, widthEnemy, heightEnemy);
    }

    public void updateEnemyPosition(){
        /*Tem que fazer aconpanhar a bola, fazendo seu eixo das ordenadas ser o mesmo da bola,
         * mas só isso não atualiza, por isso podemos subtrair nosso própio y após para segui-la.
         * y += (WindowGame.ball.y - y), mas podemos deixar mais realista aplicando velocidades a 
         * critério do programador: y += (WindowGame.ball.y - y) * 0.5;*/
        y += (WindowGame.ball.y - y - 5) * 0.5;

        //Controle para não sair da tela.
        if(y + heightEnemy  > WindowGame.heightWindow){y = WindowGame.heightWindow - heightEnemy;}
        else if(y < 0){ y = 0;}
    }  
}
package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
    public boolean up;
    public boolean down;
    public int x;
    public int y;
    public int widthPlayer;
    public int heightPlayer;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        //Passando diretamente as proporções da raquete.
        this.widthPlayer = 10;
        this.heightPlayer = 170;
    }

    //Recebe o tipo gráfico para dar inicio ao desenho da forma.
    public void drawPlayer(Graphics g){
        g.setColor(new Color(255, 255, 255));
        g.fillRect(x, y, widthPlayer, heightPlayer);
    }

    //Movimentação do play.
    public void updatePlayerPosition(){
        if(up){y-=4;}
        else if(down){y+=4;}

        //Controle para não sair da tela.
        if(y + heightPlayer  > WindowGame.heightWindow){y = WindowGame.heightWindow - heightPlayer;}
        else if(y < 0){y = 0;}
    }
}
package myproject;
import javax.swing.*;//used for building GUI   , it contains Jpanel(inbuilt class)
import java.awt.*;  //abstract window toolkit
import java.awt.event.*;
import java.util.Random; //to spawn the food
import java.util.Arrays;
public class Panel extends JPanel implements ActionListener {
    static int width = 1200;
    static int height = 600;
    static int unit = 50;

    boolean flag = false;

    int score = 0;
    Timer timer;

    Random random;

    int fx, fy;
    int length = 2;
    
    char dir='R';


    int xsnake[] = new int[288];
    int ysnake[] = new int[288];


    Panel(){
        this.setPreferredSize(new Dimension(width, height)); //dimension of panel
        this.setBackground(Color.black);
        this.addKeyListener(new key());
        this.setFocusable(true);
        random = new Random();
        gamestart();
    }
    public void gamestart(){
        spawnfood();
        flag = true;
        timer = new Timer(160, this); //ActionListener will called after each 160ms
        timer.start();
    }
    public void spawnfood(){
        fx = random.nextInt(width/unit)*unit;  // here width/unit=24, so it will give a random number between 0 to 24.then multiply by unit to get the co-ordinate
        fy = random.nextInt(height/unit)*unit;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);   // it is an inbuilt function in the Jpanel class
        draw(graphic);
    }

    public void draw(Graphics graphic){
        if(flag){ // game is running
            //for the food particle
            graphic.setColor(Color.red);
            graphic.fillOval(fx, fy, 50, 50); //oval means elongated circle. but here dims are same.

            for(int i = 0; i < length; i++){
            	if(i!=0) {
                graphic.setColor(Color.green);
            	}
            	else 
            	{
            		graphic.setColor(Color.orange);	
            	}
                graphic.fillRect(xsnake[i], ysnake[i], 50, 50);
            }
            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphic.getFont().getSize());

        }
        else{
           gameover(graphic);
        }
    }
    public void gameover(Graphics graphic) {
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphic.getFont().getSize());
        
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        fme = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER", (width - fme.stringWidth("GAME OVER"))/2, height/2);
        
        graphic.setColor(Color.green);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay", (width - fme.stringWidth("Press R to Replay"))/2, 3*height/4);
    	
    }
    public void eat() {
    	if(fx==xsnake[0] && fy==ysnake[0]) {
    		length++;
    		score++;
    		spawnfood();
    	}
    }
     public void hit() {
    	 for(int i=length;i>0;i--) {
    		 if(xsnake[0]==xsnake[i] && ysnake[0]==ysnake[i]) {
    			 flag=false;
    		 }
    	 }
    	 if(xsnake[0]<0 || xsnake[0]>width || ysnake[0]<0 || ysnake[0]>height ) {
    		 flag=false;
    	 }
    	 if(flag==false) {
    		 timer.stop();
    	 }
     }
     
     public void move() {
    	 // updating the cordinates of the body except head
    	 for(int i=length-1;i>0;i--) {
    		 xsnake[i]=xsnake[i-1];
    		 ysnake[i]=ysnake[i-1];
    	 }
    	 // to update the head
    	 switch(dir) {
    	 case'U':
    		 ysnake[0]=ysnake[0]-unit;
    		 break;
    	 case'D':
    		 ysnake[0]=ysnake[0]+unit;
    		 break;
    	 case'R':
    		 xsnake[0]=xsnake[0]+unit;
    		 break;
    	 case'L':
    		 xsnake[0]=xsnake[0]-unit;
    		 break;
    	 }
    	 
     }
     
     public class key extends KeyAdapter{    //class in awt
    	 
    	 public void keyPressed(KeyEvent e) {
    		 switch(e.getKeyCode()) {
    		 case KeyEvent.VK_UP:
    			 if(dir!='D') {
    				 dir='U';
    			 }
    			 break;
    		 case KeyEvent.VK_DOWN:
    			 if(dir!='U') {
    				 dir='D';
    			 }
    			 break;
    		 case KeyEvent.VK_LEFT:
    			 if(dir!='R') {
    				 dir='L';
    			 }
    			 break;
    		 case KeyEvent.VK_RIGHT:
    			 if(dir!='L') {
    				 dir='R';
    			 }
    			 break;
    		 case KeyEvent.VK_R:
    			 if(flag==false) {
    				 score=0;
    				 length=3;
    				 dir='R';
    				 Arrays.fill(xsnake,0);
    				 Arrays.fill(ysnake,0);
    				 gamestart();
    			 }
    			 break;
    		 }
    	 }
    	 
     }



     public void actionPerformed(ActionEvent e){
    	 	if(flag) {
    	 		move();
    	 		hit();
    	 		eat();
    	 	}
    	 	repaint();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.golden.gamedev.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author student
 */
public class GameFrame extends Game {
    Block snake,food;
    Boolean gameOver = false;
    double snakeX,snakeY, foodX,foodY;
    int snakeDirection;
    ArrayList<Block> border = new ArrayList<Block>();
    ArrayList<Block> tail = new ArrayList<Block>();
    
    final double dimension = 16; //16 x 16
    @Override
    public void initResources() {
        snakeX = 0;
        snakeY= 0;
        snakeDirection = 2;
        foodX = 20;
        foodY = 20;
        snake = new Block(getImage("snakeblock.png"),snakeX * dimension,snakeY * dimension);
        food = new Block(getImage("food.png"),foodX * dimension,foodY * dimension);
//        for(int i=0; i<40;i++){
//            border.add(new Block(getImage("food.png"),i * dimension,1 * dimension));
//        }
        
        System.out.println("Test---------------");
        
        System.out.println(getDistance(new Block(getImage("snakeblock.png"),0,0),new Block(getImage("snakeblock.png"),6,8)));
        System.out.println("End of Test--------");
    }

    public void moveSnake(){
        switch(snakeDirection){
            case 1 : snakeY-=.25;break;
            case 2 : snakeX+=.25;break;
            case 3 : snakeY+=.25;break;
            case 4 : snakeX-=.25;break;
        }
//        switch(snakeDirection){
//            case 1 : snakeY-=16;break;
//            case 2 : snakeX+=16;break;
//            case 3 : snakeY+=16;break;
//            case 4 : snakeX-=16;break;
//        }
                
        snake.setX(snakeX*dimension);
        snake.setY(snakeY*dimension);
    }
    
    public void readInput(){
    	if(keyPressed(KeyEvent.VK_UP )&& snakeDirection != 3){
            snakeDirection = 1;
        }else if(keyPressed(KeyEvent.VK_RIGHT)&& snakeDirection != 4){
            snakeDirection = 2;
        }else if(keyPressed(KeyEvent.VK_DOWN)&& snakeDirection != 1){
            snakeDirection = 3;
        }else if(keyPressed(KeyEvent.VK_LEFT)&& snakeDirection != 2){
            snakeDirection = 4;
        }
    }
    
    public void resetFood(){
        int newX,newY;
        newX = (int)(Math.random()*100)%40;
        newY = (int)(Math.random()*100)%40;
        food.setX((double)newX * dimension);
        food.setY((double)newY * dimension);
    }
    
    public void showGameOver(Graphics2D gd){
    	if(keyPressed(KeyEvent.VK_ENTER )){
    		gameOver=false;
    		snakeX=20;
    		snakeY=20;
    		moveSnake();
    		tail.removeAll(tail);
    	}
    }
    
    public double getDistance(Block x, Block y){
    	double distance=0;    	
    	distance = Math.sqrt(Math.pow(x.getX()-y.getX(), 2)+Math.pow(x.getY()-y.getY(), 2));
    	return distance;
    }
    
    public void follow(Block follower, Block target){
    	if(getDistance(follower,target)>16){
    		if(follower.getX()>target.getX()){
    			follower.setX(follower.getX()-(dimension*.25));
    		}else{
    			follower.setX(follower.getX()+(dimension*.25));
    		}
    		if(follower.getY()>target.getY()){
    			follower.setY(follower.getY()-(dimension*.25));
    		}else{
    			follower.setY(follower.getY()+(dimension*.25));
    		}
    	}
    }
    @Override
    public void update(long l) {
        
    	// if collide with food
        if(Math.abs(snake.getX() - food.getX()) < dimension && Math.abs(snake.getY() - food.getY()) < dimension){
            System.out.println("collide");
            resetFood();
            tail.add(new Block(getImage("snakeblock.png"),snakeX * dimension,snakeY * dimension));
        }
        // if outside bounds
        if(snake.getX() > 624 || snake.getY() > 624 || snake.getX() < 0 || snake.getY() <0){
    		gameOver=true;
            //TODO: add restart screen
        }
        
        for(Block t: tail){
        	
        }

        if(!gameOver){
            readInput();
            moveSnake();
//            if(tail.size()>0){
//            	tail.get(0).setX(snake.getX());
//            	tail.get(0).setY(snake.getY());
//            }
//            for(int i=1;i < tail.size();i++){
//            	tail.get(i).setX(tail.get(i-1).getX());
//            	tail.get(i).setY(tail.get(i-1).getY());
//            }
            if(tail.size()>0){
            	follow(tail.get(0),snake);
            }
            for(int i=1;i < tail.size();i++){
            	follow(tail.get(i),tail.get(i-1));
            }
            
            checkCollisionSnake();
            
        }
        
        snake.update(l);
        food.update(l);
    }
    
    private void checkCollisionSnake() {
    	for(int i = 0; i< tail.size(); i++){
    		if(snake.getX() == tail.get(i).getX() && snake.getY() == tail.get(i).getY()){
    			gameOver = true;
    		}
    	}
		
	}

	@Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.gray);
        gd.fillRect(0, 0, getWidth(), getHeight());
        snake.render(gd);
        food.render(gd);
        for(int i=0;i <tail.size();i++){
            tail.get(i).render(gd);
        }
        
        if(gameOver){
        	fontManager.getFont("FPS Font").drawString(gd, "GAME OVER", 260, 260);
        	showGameOver(gd);
        }
    }
    
}

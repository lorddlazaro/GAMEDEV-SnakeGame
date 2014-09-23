/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.Timer;

/**
 *
 * @author student
 */
public class GameFrame extends Game {
    Block snake,food,powerup;
    int powerupType=0;
    Boolean gameOver = false;
    Boolean mainScreen;
    Boolean levelChosen=false;
    int powerUpActive=0;
    int level=1;
    int speed=1;
    double snakeX,snakeY, foodX,foodY;
    int snakeDirection;
    ArrayList<Block> border = new ArrayList<Block>();
    ArrayList<Block> tail = new ArrayList<Block>();
    ArrayList<Block> currentMaze;
    ArrayList<Block> maze1 = new ArrayList<Block>();
    ArrayList<Block> maze2 = new ArrayList<Block>();
    ArrayList<Block> maze3 = new ArrayList<Block>();
    ArrayList<Block> maze4 = new ArrayList<Block>();
    ArrayList<Block> maze5 = new ArrayList<Block>();
    int SPEED_LEVEL = 0;
    int MAZE_LEVEL = 1;
    Timer powerupTimer;
    Timer powerupDuration;
    Timer snakespeed;
    int gotFood=0;
    Block lastFood;
    
    
    final double dimension = 16; //16 x 16
    @Override
    public void initResources() {
    	mainScreen=true;
        snakeX = 0;
        snakeY= 0;
        snakeDirection = 2;
        foodX = 20;
        foodY = 20;
        snake = new Block(getImage("assets/snakeblock.png"),snakeX * dimension,snakeY * dimension);
        food = new Block(getImage("assets/food.png"),foodX * dimension,foodY * dimension);
        
//        for(int i=0; i<40;i++){
//            border.add(new Block(getImage("food.png"),i * dimension,1 * dimension));
//        }
        //MAZE 2
        for(int i = 100; i<180; i+=16){
        	maze2.add(new Block(getImage("assets/maze.png"),i,100));
        	maze2.add(new Block(getImage("assets/maze.png"),i,400));
        	maze2.add(new Block(getImage("assets/maze.png"),i+300,100));
        	maze2.add(new Block(getImage("assets/maze.png"),i+300,400));
        }
        
        //MAZE 3
        for(int i = 100; i<180; i+=16){
        	maze3.add(new Block(getImage("assets/maze.png"),100,i));
        	maze3.add(new Block(getImage("assets/maze.png"),400,i));
        	maze3.add(new Block(getImage("assets/maze.png"),100,i+300));
        	maze3.add(new Block(getImage("assets/maze.png"),400,i+300));
        }
        
        //MAZE 4
        for(int i = 50; i<500; i+=16){
        	maze4.add(new Block(getImage("assets/maze.png"),i,100));
        	maze4.add(new Block(getImage("assets/maze.png"),i,300));
        	maze4.add(new Block(getImage("assets/maze.png"),i,500));
        }
        
        //MAZE 5
        for(int i = 50; i<500; i+=16){
        	maze5.add(new Block(getImage("assets/maze.png"),100,i));
        	maze5.add(new Block(getImage("assets/maze.png"),300,i));
        	maze5.add(new Block(getImage("assets/maze.png"),500,i));
        }
        
        
        snakespeed = new Timer(50);
        powerupTimer = new Timer(10000);
    }

    public void moveSnake(){
        switch(snakeDirection){
            case 1 : snakeY-= 1*speed;break;
            case 2 : snakeX+= 1*speed;break;
            case 3 : snakeY+= 1*speed;break;
            case 4 : snakeX-= 1*speed;break;
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
    
    public void readInputRestartGame(){
    	if(keyPressed(KeyEvent.VK_ENTER )){
    		gameOver=false;
    		snakeX=20;
    		snakeY=20;
    		moveSnake();
    		tail.removeAll(tail);
			powerupTimer = new Timer(10000);
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
    			follower.setX(follower.getX()-(dimension*.25)*speed);
    		}else{
    			follower.setX(follower.getX()+(dimension*.25)*speed);
    		}
    		if(follower.getY()>target.getY()){
    			follower.setY(follower.getY()-(dimension*.25)*speed);
    		}else{
    			follower.setY(follower.getY()+(dimension*.25)*speed);
    		}
    	}
    }
    
    public void mainScreen(){
    	if(mainScreen==true  && keyPressed(KeyEvent.VK_ENTER )){
            mainScreen=false;
    	}else if(mainScreen==false && keyPressed(KeyEvent.VK_ENTER )){
            levelChosen=true;
    	}
    	if(keyPressed(KeyEvent.VK_UP)&&speed<3){
    		speed++;
    	}else if(keyPressed(KeyEvent.VK_DOWN) && speed>0){
    		speed--;
    	}
    	if(keyPressed(KeyEvent.VK_RIGHT)&&level<5){
    		level++;
    	}else if(keyPressed(KeyEvent.VK_LEFT) && level>1){
    		level--;
    	}
    	
    }
    @Override
    public void update(long l) {
        
    	// if collide with food
    	if(levelChosen==false){
    		mainScreen();
    	}
    	if(levelChosen==true){
            game(l);
    	}
    }
    
    public void game(long l){
    	//System.out.println(level);
    	switch(level){
    		case 1 : currentMaze = maze1;break;
    		case 2 : currentMaze = maze2;break;
    		case 3 : currentMaze = maze3;break;
    		case 4 : currentMaze = maze4;break;
    		case 5 : currentMaze = maze5;break;
    	}
    	if(powerupTimer.action(l)){
    		int newX,newY;
            newX = (int)(Math.random()*100)%40;
            newY = (int)(Math.random()*100)%40;
            if(powerupType == 1){
            	powerup = new Block(getImage("assets/magnet.png"),(double)newX * dimension,(double)newY * dimension);
            }
    		powerupType = (int)(1+((Math.random()*100)%1));
    		
    	}
    	//if collide food
    	
        // if outside bounds
        if(snake.getX() > 624 || snake.getY() > 624 || snake.getX() < 0 || snake.getY() <0){
    		gameOver=true;
    		powerUpActive = 0;
            //TODO: add restart screen
        }
        
        if(!gameOver){
        	readInput();
            
        	if(snakespeed.action(l)){
        		
        		
        		if(gotFood>0){
        			System.out.println("snake:"+snake.getX()+","+snake.getY());
        			System.out.println("lastFood:"+lastFood.getX()+","+lastFood.getY());
        			tail.add(new Block(getImage("assets/snaketail.png"),lastFood.getX(),lastFood.getY()));
        			gotFood--;
        		}else{
//        			if(tail.size()>0){
//                    	tail.get(0).setLocation(snake.getX(), snake.getY());
//                    }
                    for(int i=0;i < tail.size();i++){
                    	if(i==tail.size()-1){
                    		tail.get(i).setLocation(snake.getX(),snake.getY());
                    	}
                    	else{
                    		tail.get(i).setLocation(tail.get(i+1).getX(), tail.get(i+1).getY());
                    	}
                    	
                    }
        		}
        		moveSnake();
        		if(Math.abs(snake.getX() - food.getX()) < dimension && Math.abs(snake.getY() - food.getY()) < dimension){
                    System.out.println("collide");
                    System.out.println("food:"+food.getX()+","+food.getY());
                    //tail.add(new Block(getImage("assets/snakeblock.png"),food.getX(),food.getY()));
                    lastFood=food;
                    System.out.println("lastFood:"+lastFood.getX()+","+lastFood.getY());
                    //resetFood();
                    int newX,newY;
                    newX = (int)(Math.random()*100)%40;
                    newY = (int)(Math.random()*100)%40;
                    food = new Block(getImage("assets/food.png"),newX * dimension,newY * dimension);
                    gotFood+=1;
                    
//                    if(tail.size()==0){
//                    	switch(snakeDirection){
//        	                case 1 : tail.add(new Block(getImage("assets/snakeblock.png"),snake.getX(),snake.getY()+16));break;
//        	                case 2 : tail.add(new Block(getImage("assets/snakeblock.png"),snake.getX()-16,snake.getY()));break;
//        	                case 3 : tail.add(new Block(getImage("assets/snakeblock.png"),snake.getX(),snake.getY()-16));break;
//        	                case 4 : tail.add(new Block(getImage("assets/snakeblock.png"),snake.getX()+16,snake.getY()));break;
//                    	}
//                    }else{
//                    	Block lastTail = tail.get(tail.size()-1);
//                    	tail.add(new Block(getImage("assets/snakeblock.png"),lastTail.getX(),lastTail.getY()));
//                    }
                    
                }
                
                
                
//                if(gotFood){
//        			tail.add(new Block(getImage("assets/snakeblock.png"),food.getX(),food.getY()));
//        			gotFood=false;
//        		}
//                if(tail.size()>0){
//                	tail.get(0).setX(snake.getX());
//                	tail.get(0).setY(snake.getY());
//                }
//                for(int i=1;i < tail.size();i++){
//                	tail.get(i).setX(tail.get(i-1).getX());
//                	tail.get(i).setY(tail.get(i-1).getY());
//                }
//                if(tail.size()>0){
//                	follow(tail.get(0),snake);
//                }
//                for(int i=1;i < tail.size();i++){
//                	follow(tail.get(i),tail.get(i-1));
//                }
//                if(tail.size()>0){
//                	tail.get(0).setLocation(snake.getX(), snake.getY());
//                }
//                for(int i=1;i < tail.size();i++){
//                	follow(tail.get(i),tail.get(i-1));
//                	tail.get(i).setLocation(tail.get(i-1).getX(), tail.get(i-1).getY());
//                }
                
                checkCollisionSnake();
                checkCollisionMaze();
                checkCollisionPowerup();
                if(powerUpActive==1){
                	switch(powerupType){
                    case 1 : follow(food,snake);break;
                    }
                	
                	if(powerupDuration.action(l)){
                    	powerUpActive=0;
                    }
                }
        	}
            
        }
        else{
        	readInputRestartGame();
        }
        
        snake.update(l);
        food.update(l);
    }
    private void checkCollisionPowerup() {
    	if(powerup!=null){
			if(Math.abs(snake.getX() - powerup.getX())<dimension && Math.abs(snake.getY() - powerup.getY())<dimension){
				powerUpActive=1;
				powerup=null;
				powerupDuration = new Timer(5000);
			System.out.println("collide with powerup");
			}
    	}
		
	}
    private void checkCollisionSnake() {
    	for(int i = 1; i< tail.size(); i++){
    		if(Math.abs(snake.getX() - tail.get(i).getX())<dimension && Math.abs(snake.getY() - tail.get(i).getY())<dimension){
    			gameOver = true;
    			System.out.println("collide with snake tail#"+i);
    		}
    	}
		
	}
    
    private void checkCollisionMaze() {
    	for(int i = 0; i< currentMaze.size(); i++){
    		if(Math.abs(snake.getX() - currentMaze.get(i).getX())<dimension && Math.abs(snake.getY() - currentMaze.get(i).getY())<dimension){
    			gameOver = true;
    			System.out.println("collide with maze");
    		}
    	}
		
	}

	@Override
    public void render(Graphics2D gd) {
		//System.out.println(mainScreen);
		if(mainScreen){
			gd.setColor(Color.white);
	        gd.fillRect(0, 0, getWidth(), getHeight());
	        fontManager.getFont("FPS Font").drawString(gd, "SNAKE BY TIM AND LORDD", 195, 220);
	        fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO START", 200, 240);
	        
		}else if(levelChosen==false){
			gd.setColor(Color.white);
	        gd.fillRect(0, 0, getWidth(), getHeight());

        	fontManager.getFont("FPS Font").drawString(gd, "LEVEL SELECTION", 140, 160);
        	fontManager.getFont("FPS Font").drawString(gd, "CHOOSE LEVEL PRESS LEFT OR RIGHT", 140, 200);
        	fontManager.getFont("FPS Font").drawString(gd, "CHOOSE SPEED PRESS UP OR DOWN", 140, 260);
        	
        	fontManager.getFont("FPS Font").drawString(gd, "LEVEL "+level, 150, 230);
        	fontManager.getFont("FPS Font").drawString(gd, "SPEED "+speed, 150, 290);
        	fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO PLAY", 140, 330);
        	
        }
		else if(mainScreen==false){
			gd.setColor(Color.white);
	        gd.fillRect(0, 0, getWidth(), getHeight());
	        snake.render(gd);
	        food.render(gd);
	        for(int i=0;i <tail.size();i++){
	            tail.get(i).render(gd);
	        }
	        for(int i=0;i<currentMaze.size();i++){
	        	currentMaze.get(i).render(gd);
	        }
	        if(powerup!=null){
		        powerup.render(gd);
	        }
	        
	        if(gameOver){
	        	fontManager.getFont("FPS Font").drawString(gd, "GAME OVER", 260, 260);
	        	fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO RESTART", 260, 290);
    			powerUpActive=0;
	        }
		}
        
    }
    
}

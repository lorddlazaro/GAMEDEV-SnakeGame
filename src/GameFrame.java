/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

	ArrayList<Scorer> scorerList = new ArrayList<Scorer>();
    int SPEED_LEVEL = 0;
    int MAZE_LEVEL = 1;
    Timer powerupTimer;
    Timer powerupDuration;
    Timer snakespeed;
    int gotFood=0;
    Block lastFood;
    Boolean newLevel = false;
    Timer timeRemaining;
    int score;
    int scoreMultiplier=1;
    boolean startGame = false;
    boolean savedScore = false;
    final double dimension = 16; //16 x 16
    @Override
    public void initResources() {
    	
    	score=0;
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
        
        
        snakespeed = new Timer(100);
        powerupTimer = new Timer(10000);
        timeRemaining = new Timer(60000);
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
        } else if(keyPressed(KeyEvent.VK_ENTER) && level == 6){
	    	mainScreen = true;
			savedScore = false;
			saveScores();
        }
    	

//        if(keyPressed(KeyEvent.VK_RIGHT)){
//        	switch(snakeDirection){
//        		case 1 : snakeDirection = 2;break;
//        		case 2 : snakeDirection = 3;break;
//        		case 3 : snakeDirection = 4;break;
//        		case 4 : snakeDirection = 1;break;
//        	}
//        }else if(keyPressed(KeyEvent.VK_LEFT)){
//        	switch(snakeDirection){
//	    		case 1 : snakeDirection = 4;break;
//	    		case 2 : snakeDirection = 1;break;
//	    		case 3 : snakeDirection = 2;break;
//	    		case 4 : snakeDirection = 3;break;
//        	}
//        }
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
    		timeRemaining.refresh();
    		snakeX = 10;
            snakeY= 10;
    		moveSnake();
    		tail.removeAll(tail);
			powerupTimer = new Timer(10000);
			score=0;
			newLevel = false;
			savedScore = false;
			saveScores();
    	}
    }
    
    public void readHighscore(){
    	readFileText();    
    }
    
    private void readFileText() {
    	Scorer scorer = new Scorer();
        String fileName="src/score.txt";
//        try{
//	        FileReader inputFile = new FileReader(fileName);
//	        BufferedReader bufferReader = new BufferedReader(inputFile);
//	
//	        String line;
//	        String temp = "";
//	        while ((line = bufferReader.readLine()) != null){
//	        	temp = "";
//	        	scorer = new Scorer();
//	        	for(int i = 0; i<line.length(); i++){
//	        		if (i==line.length()-1){
//	        			temp+=line.charAt(line.length()-1);
//	        			scorer.setScore(Integer.parseInt(temp));
//	        		} else if(line.charAt(i) != ' '){
//	        			temp+=line.charAt(i);
//	        		} else if(line.charAt(i) == ' '){
//	        			scorer.setName(temp);
//	        			temp = "";
//	        		} 
//	        	}
//        		scorerList.add(scorer);
//
//	        }
//	        
//	        bufferReader.close();
//        }catch(Exception e){
//               e.printStackTrace();                   
//        }	
	}
    
    // Appends the result. It should overwrite the existing file.
    private void saveScores(){
    	readHighscore();
    	scorerList.add(new Scorer("HEY", 50));
//    	try {
//    		if(savedScore == false){
//    			FileWriter writer = new FileWriter(new File("src/score.txt"), false);
//                for(int i = 0; i<scorerList.size(); i++){
//                	System.out.println("INSIDE LOOP: "+scorerList.get(i).getName() + " " + scorerList.get(i).getScore());
//                    
//                	writer.write(scorerList.get(i).getName() + " " + scorerList.get(i).getScore());
//                	writer.write('\n');
//                }
//    	    	savedScore = true;
//        		writer.close();
//    		}
//        } catch (Exception e) {
//        	System.out.println("There was a problem:" + e);
//    	}
    }

	public double getDistance(Block x, Block y){
    	double distance=0;    	
    	distance = Math.sqrt(Math.pow(x.getX()-y.getX(), 2)+Math.pow(x.getY()-y.getY(), 2));
    	return distance;
    }
    
    public void follow(Block follower, Block target){
    	if(getDistance(follower,target)>16){
    		if(follower.getX()>target.getX()){
    			follower.setX(follower.getX()-dimension*(speed+1));
    		}else{
    			follower.setX(follower.getX()+dimension*(speed+1));
    		}
    		if(follower.getY()>target.getY()){
    			follower.setY(follower.getY()-dimension*(speed+1));
    		}else{
    			follower.setY(follower.getY()+dimension*(speed+1));
    		}
    	}
    }
    
    public void mainScreen(){
    	if(mainScreen==true  && keyPressed(KeyEvent.VK_ENTER )){
            mainScreen=false;
    	}else if(mainScreen==false && keyPressed(KeyEvent.VK_ENTER )){
            levelChosen=true;
            startGame = true;
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
    	
    	if(timeRemaining.action(l)){
    		gameOver=true;
    	}
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

            switch(powerupType){
            	case 1 : powerup = new Block(getImage("assets/magnet.png"),(double)newX * dimension,(double)newY * dimension);
            			break;
            	case 2 : powerup = new Block(getImage("assets/2x.png"),(double)newX * dimension,(double)newY * dimension);
    					break;
            	case 3 : powerup = new Block(getImage("assets/magnet.png"),(double)newX * dimension,(double)newY * dimension);
    					break;
            }
    		powerupType = (int)(1+((Math.random()*100)%2));
    		switch(powerupType){
	        	case 1 : powerup = new Block(getImage("assets/magnet.png"),(double)newX * dimension,(double)newY * dimension);
	        			break;
	        	case 2 : powerup = new Block(getImage("assets/2x.png"),(double)newX * dimension,(double)newY * dimension);
				break;
	        	case 3 : powerup = new Block(getImage("assets/magnet.png"),(double)newX * dimension,(double)newY * dimension);
				break;
    		}
    		
    	}
    	//if collide food
    	
        // if outside bounds
        if(snake.getX() > 624 || snake.getY() > 624 || snake.getX() < 0 || snake.getY() <0){
    		gameOver=true;
    		powerUpActive = 0;
            //TODO: add restart screen
        }
        
        if(!gameOver){
        	if(startGame){
        		playSound("assets/background.wav");
        		startGame = false;
        	}
        	if(powerUpActive==1){
            	switch(powerupType){
                case 1 : follow(food,snake);break;
                case 2 : scoreMultiplier=2;break;
                //case 3 : snakespeed.setDelay(100);break;
                }
            	
            	if(powerupDuration.action(l)){
                	powerUpActive=0;
                	scoreMultiplier=1;
                	snakespeed.setDelay(50);
                }
            }
        	readInput();
        	if(snakespeed.action(l)){
        		if(gotFood>0){

        			System.out.println("snake:"+snake.getX()+","+snake.getY());
        			System.out.println("lastFood:"+lastFood.getX()+","+lastFood.getY());
        			tail.add(new Block(getImage("assets/snaketail.png"),lastFood.getX(),lastFood.getY()));
        			gotFood--;
        		}else{
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
                    //System.out.println("collide");
                    //System.out.println("food:"+food.getX()+","+food.getY());
                    lastFood=food;
                    System.out.println("lastFood:"+lastFood.getX()+","+lastFood.getY());

                    //resetFood();
                    boolean sameCoordinates = false;
                    int newX,newY;
                    do{
	                    newX = (int)(Math.random()*100)%40;
	                    newY = (int)(Math.random()*100)%40;
	                    
	                    for(int i = 0; i<currentMaze.size();i++){
	                    	if(newX == currentMaze.get(i).getX() && newY == currentMaze.get(i).getY()){
	                    		sameCoordinates = true;
	                    		break;
	                    	} else {
	                    		sameCoordinates = false;
	                    	}
	                    }
                    } while(sameCoordinates == true);
                    food = new Block(getImage("assets/food.png"),newX * dimension,newY * dimension);
                    gotFood+=1;
                    score+=1*scoreMultiplier;
                }
                
                checkCollisionSnake();
                checkCollisionMaze();
                checkCollisionPowerup();
                
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
				powerupDuration = new Timer(3000);
			System.out.println("collide with powerup");
			System.out.println("powerupType:"+powerupType);
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
	        
	        //SHOW TIMER
	        long time = (60-(timeRemaining.getCurrentTick()/1000));
	        fontManager.getFont("FPS Font").drawString(gd, "TIME:"+time, 5, 10);
	        fontManager.getFont("FPS Font").drawString(gd, "SCORE:"+score, 5, 30);
	        fontManager.getFont("FPS Font").drawString(gd, "LEVEL: "+level, 250, 10);
	        
	        if(newLevel == false &&time == 0 && level<=5){
	        	level++;
	        	newLevel = true;
	        } 
	        
	        if(newLevel){
	        	showNewLevel(gd);
	        	readInputRestartGame();
	        } else if(gameOver){
	        	fontManager.getFont("FPS Font").drawString(gd, "GAME OVER", 260, 260);
	        	fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO RESTART", 260, 290);
    			powerUpActive=0;
	        }
		}
        
    }
	
	private void showNewLevel(Graphics2D gd) {
		gd.setColor(Color.white);
        gd.fillRect(0, 0, getWidth(), getHeight());

        if(level==6){
        	fontManager.getFont("FPS Font").drawString(gd, "CONGRATULATIONS!", 140, 160);
	    	fontManager.getFont("FPS Font").drawString(gd, "YOU FINISHED THE GAME!", 140, 200);
	    	fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO CONTINUE", 140, 220);
	    	readInput();
        } else{
	    	fontManager.getFont("FPS Font").drawString(gd, "LEVEL FINISHED", 140, 160);
	    	fontManager.getFont("FPS Font").drawString(gd, "PRESS ENTER TO CONTINUE", 140, 200);
        }
	}
    
}

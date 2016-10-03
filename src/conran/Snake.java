/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conran;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author zquangu112z
 */
public class Snake {

    private ArrayList<Point> mPoints = new ArrayList<>();
    int speed;
    int length;
    int portionSize;
    int extH = SnakeGame.sExtH;

    /**
     * Direction: 1: phai 2: len 3: trai 4: xuong
     */
    private int direction = 1;
    private int lastDirection = 1;

    public Snake() {
    }

    public Snake(int length, int portionSize, int speed, int frameW, int frameH) {
        this.speed = speed;
        this.length = length;
        this.portionSize = portionSize;

        //init snake body
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int scrW = (int) toolkit.getScreenSize().getWidth();
        int scrH = (int) toolkit.getScreenSize().getHeight();
        //int _left = (int) (scrW - frameW) / 2;
        //int _top = (int) (scrH - frameH) / 2;
        Random rand = new Random();
        
        //direction = rand.nextInt(3);
        
        //head
        int x = (((int) rand.nextInt(frameW)) / this.portionSize) * this.portionSize + SnakeGame.GAME_BORDER;
        if (x < SnakeGame.GAME_BORDER * 2 + this.length * this.portionSize|| x > frameW - 2 * SnakeGame.GAME_BORDER) {
            x = 2 * SnakeGame.GAME_BORDER + this.length * this.portionSize;
        }

        //truong hop duoc goi tu 1 JFrame
//        int y = (((int) rand.nextInt(frameH)) / this.portionSize) * this.portionSize + extH + SnakeGame.GAME_BORDER;
//        if (y < extH + 2 * SnakeGame.GAME_BORDER || y > frameH - 2 * SnakeGame.GAME_BORDER) {
//            y = extH + SnakeGame.GAME_BORDER * 2;
//        }
        
        //truong hop duoc goi tu 1 JPanel
        int y = (((int) rand.nextInt(frameH)) / this.portionSize) * this.portionSize+ SnakeGame.GAME_BORDER;
        if (y < 2 * SnakeGame.GAME_BORDER || y > frameH - 2 * SnakeGame.GAME_BORDER) {
            y = SnakeGame.GAME_BORDER * 2;
        }
        
        

        //body
        for (int i = 0; i < this.length; i++) {
            mPoints.add(new Point(x, y));
            x -= this.portionSize;
        }
    }

    public ArrayList<Point> getBody() {
        return mPoints;
    }

    public void LengthizeSnake() {
        this.length++;

    }

    public ArrayList<Point> getmPoints() {
        return mPoints;
    }

    public void setmPoints(ArrayList<Point> mPoints) {
        this.mPoints = mPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }

}

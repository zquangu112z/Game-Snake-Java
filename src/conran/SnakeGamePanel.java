/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conran;

import static conran.SnakeGame.sExtH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author zquangu112z
 */
public class SnakeGamePanel extends JPanel implements Runnable, KeyListener {

    //Dimension của khung chương trình
    private int mMainFrameSizeHeight = 500;
    private int mMainFrameSizeWidth = 500;
    private int mFrameMenuW = 300;

    public int scrW, scrH, left, top;
    public static final int GAME_BORDER = 30; // bội của mSnakePortionSize

    //titlebar
    private final String TITLE = "Snake Game";
    //độ cao của titlebar
    //public static int sExtH;

    //con rắn
    private final int mSnakeInitLength = 7;  //số đốt
    private int mSnakeSpeed = 1; //1 unit = mSnakePortionSize
    private int mSnakePortionSize = 10;  // chiều rộng mỗi đốt
    Snake mSnake;
    //than con ran;
    ArrayList<Point> mPoints;
    //con moi
    Point mApple;

    //delay time
    private int mDelayTime = 100;

    JLabel _JLabel;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SnakeGame();
    }

    public int getmMainFrameSizeHeight() {
        return mMainFrameSizeHeight;
    }

    public int getmMainFrameSizeWidth() {
        return mMainFrameSizeWidth;
    }

    public SnakeGamePanel() {
        //set Size cho contentPane thay vì JFrame để tránh trường hợp titlebar chiếm height        
        setSize(new Dimension(mMainFrameSizeWidth, mMainFrameSizeHeight));
        //pack();

        //lấy độ cao của titlebar
        Dimension actualSize = getSize();
        System.out.println("content panel" + actualSize.height);
        sExtH = getSize().height - actualSize.height;

//        //Đưa frame vào giữa màn hình        
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        scrW = (int) toolkit.getScreenSize().getWidth();
//        scrH = (int) toolkit.getScreenSize().getHeight();
//        left = (int) (scrW - mMainFrameSizeWidth) / 2;
//        top = (int) (scrH - mMainFrameSizeHeight) / 2;
        //System.out.println("left: " + left + " | top: " + top);
        setLocation(0,0);

        //tạo đối tượng Snake
        mSnake = new Snake(mSnakeInitLength, mSnakePortionSize, mSnakeSpeed, mMainFrameSizeWidth, mMainFrameSizeHeight);
        //than ran
        mPoints = mSnake.getBody();
        //tao doi tuong moi
        mApple = new Point();
        createNewApple();

        //setTitle(TITLE);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        _JLabel = new JLabel("jhasdkjf");
        _JLabel.setBounds(mMainFrameSizeWidth, 0, 100, 100);
        _JLabel.setForeground(Color.red);

        add(_JLabel);

        setLayout(null);

        setVisible(true);

        addKeyListener(this);

        Thread myThread = new Thread(this);
        myThread.start();

    }

    private void createNewApple() {
        Random rand = new Random();
        int _x, _y;
        do {
            _x = (((int) rand.nextInt(mMainFrameSizeWidth)) / this.mSnakePortionSize) * this.mSnakePortionSize + SnakeGame.GAME_BORDER;
            if (_x < SnakeGame.GAME_BORDER * 2 || _x > mMainFrameSizeWidth - 2 * SnakeGame.GAME_BORDER) {
                _x = 2 * SnakeGame.GAME_BORDER;
            }

            _y = (((int) rand.nextInt(mMainFrameSizeHeight)) / this.mSnakePortionSize) * this.mSnakePortionSize + SnakeGame.GAME_BORDER;
            if (_y < 2 * SnakeGame.GAME_BORDER || _y > mMainFrameSizeHeight - 2 * SnakeGame.GAME_BORDER) {
                _y = SnakeGame.GAME_BORDER * 2;
            }
            mApple.setX(_x);
            mApple.setY(_y);
        } while (mPoints.contains(mApple));

    }

    public void paint(Graphics g) {
        //super.paint(g);

        BufferedImage bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        // I used Graphics2D instead of Graphics here, because its more flexible and lets you do more things.
        Graphics2D g2 = (Graphics2D) bufferImage.getGraphics();

        g2.setColor(Color.white);
        g2.fillRect(0, 0 + sExtH, mMainFrameSizeWidth, mMainFrameSizeHeight);

        //ve khung 
        g2.setColor(Color.red);

        //tren
        g2.fillRect(0, 0 + sExtH, mMainFrameSizeWidth, GAME_BORDER);
        //trai
        g2.fillRect(0, 0 + sExtH, GAME_BORDER, mMainFrameSizeHeight);
        //duoi
        g2.fillRect(0, sExtH + mMainFrameSizeHeight - GAME_BORDER, mMainFrameSizeWidth, GAME_BORDER);
        //phai
        g2.fillRect(mMainFrameSizeWidth - GAME_BORDER, 0 + sExtH, GAME_BORDER, mMainFrameSizeHeight);
        //ve vien   
        g2.setColor(Color.black);
        g2.drawRect(0, 0 + sExtH, mMainFrameSizeWidth, GAME_BORDER);
        g2.drawRect(0, 0 + sExtH, GAME_BORDER, mMainFrameSizeHeight);
        g2.drawRect(0, sExtH + mMainFrameSizeHeight - GAME_BORDER, mMainFrameSizeWidth, GAME_BORDER);
        g2.drawRect(mMainFrameSizeWidth - GAME_BORDER, 0 + sExtH, GAME_BORDER, mMainFrameSizeHeight);

        //ve trai tao
        g2.setColor(Color.blue);
        //if (mApple != null) {
        g2.fillRect(mApple.getX(), mApple.getY(), mSnakePortionSize, mSnakePortionSize);

//        }
        //ve con ran
        if (mPoints == null) {
            return;
        }

        //ve dau ran
        g2.setColor(Color.red);
        g2.fillRect(mPoints.get(0).getX(), mPoints.get(0).getY(), mSnakePortionSize, mSnakePortionSize);
        //System.out.println("x: " + mPoints.get(0).getX() + " y: " + mPoints.get(0).getY());
        g2.setColor(Color.black);
        g2.drawRect(mPoints.get(0).getX(), mPoints.get(0).getY(), mSnakePortionSize, mSnakePortionSize);
        g2.setColor(Color.blue);
        //ve than ran
        for (int i = 1; i < mPoints.size(); i++) {
            //System.out.println("i= " + i);
            g2.fillRect(mPoints.get(i).getX(), mPoints.get(i).getY(), mSnakePortionSize, mSnakePortionSize);
            g2.setColor(Color.black);
            g2.drawRect(mPoints.get(i).getX(), mPoints.get(i).getY(), mSnakePortionSize, mSnakePortionSize);
            g2.setColor(Color.blue);
        }

        g.drawImage(bufferImage, 0, 0, null);

    }

    @Override
    public void run() {

        while (true) {
            try {
                move();
                //neu mSnake can trung body thi dung game
                if (checkHitBody()) {
                    break;
                };
                //neu mSnake an tao
                checkEatApple();

                //neu mSnake tong tuong thi dung game
                if (checkHitWall()) {
                    break;
                }
                repaint();
                Thread.sleep(mDelayTime);

                //TODO: tang chieu dai
            } catch (Exception e) {
                //TODO do something
                System.out.println("failed + " + e);
            }
        }
    }

    /**
     * con ran di chuyen
     */
    private void move() {
        if (mPoints == null) {
            return;
        }
        //body dich chuyen
        for (int i = mPoints.size() - 1; i > 0; i--) {
            mPoints.get(i).setX(mPoints.get(i - 1).getX());
            mPoints.get(i).setY(mPoints.get(i - 1).getY());
        }
        //dau dich chuyen
        switch (mSnake.getDirection()) {
            case 1: {//qua phai
                mPoints.get(0).setX(mPoints.get(0).getX() + mSnake.getPortionSize());
                break;
            }
            case 2: {//len tren
                mPoints.get(0).setY(mPoints.get(0).getY() - mSnake.getPortionSize());
                break;
            }
            case 3: {//qua trai
                mPoints.get(0).setX(mPoints.get(0).getX() - mSnake.getPortionSize());
                break;
            }
            case 4: {//xuong duoi
                mPoints.get(0).setY(mPoints.get(0).getY() + mSnake.getPortionSize());
                break;
            }
            default:
                break;
        }
    }

    /**
     *
     * @return true: snake hit wall -> game over false: repaint
     */
    private boolean checkHitWall() {
        if (mPoints.get(0).getX() < SnakeGame.GAME_BORDER || mPoints.get(0).getX() >= mMainFrameSizeWidth - SnakeGame.GAME_BORDER) {
            System.out.println("Game over");//TODO aleart dialog
            return true;
        }

        if (mPoints.get(0).getY() < SnakeGame.GAME_BORDER + SnakeGame.sExtH || mPoints.get(0).getY() >= mMainFrameSizeHeight - SnakeGame.GAME_BORDER + SnakeGame.sExtH) {
            System.out.println("Game over");//TODO aleart dialog
            return true;
        }

        return false;
    }

    /**
     * kiem tra mSnake co can trung body cua minh hay khong
     *
     * @return
     */
    private boolean checkHitBody() {
        //Point mHead = new Point(mPoints.get(0).getX() , mPoints.get(0).getY());
        int _xHead = mPoints.get(0).getX();
        int _yHead = mPoints.get(0).getY();
        for (int i = 1; i < mPoints.size(); i++) {
            if (mPoints.get(i).getX() == _xHead && mPoints.get(i).getY() == _yHead) {
                System.out.println("va cham voi than ran-----------------------------------------");
                return true;
            }
        }
        return false;
    }

    private boolean checkEatApple() {
        if (mApple.getX() == mPoints.get(0).getX() && mApple.getY() == mPoints.get(0).getY()) {
            //an moi
            createNewApple();
            lengthizeSnake();
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped= " + e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("keyPressed= " + e);
        switch (e.getKeyCode()) {
            case 38: {//len
                if (mPoints.get(0).getY() > mPoints.get(1).getY()) {
                    break;
                }
                mSnake.setDirection(2);
                break;
            }
            case 39: {//phai
                if (mPoints.get(0).getX() < mPoints.get(1).getX()) {
                    break;
                }
                mSnake.setDirection(1);
                break;
            }
            case 40: {//xuong
                if (mPoints.get(0).getY() < mPoints.get(1).getY()) {
                    break;
                }
                mSnake.setDirection(4);
                break;
            }
            case 37: {//trai
                if (mPoints.get(0).getX() > mPoints.get(1).getX()) {
                    break;
                }
                mSnake.setDirection(3);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("keyReleased= " + e);
    }

    /**
     * tang chieu dai con ran
     */
    private void lengthizeSnake() {
        System.out.println("---------old: " + mPoints.size());

        Point newPortion = new Point(mPoints.get(mPoints.size() - 1).getX(), mPoints.get(mPoints.size() - 1).getY());
        move();
        mPoints.add(newPortion);

        System.out.println("---------new: " + mPoints.size());
        move();
    }
}

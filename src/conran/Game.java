/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conran;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author zquangu112z
 */
public class Game extends JFrame {
    private int mMenuWidth = 200;
    private int scrW, scrH,left, top;
    public Game(){
        //setSize(1500,1000);
        
        setLayout(null);
        
        SnakeGamePanel _SnakeGamePanel =  new SnakeGamePanel();
        this.add(_SnakeGamePanel);
        
        setSize(_SnakeGamePanel.getmMainFrameSizeWidth() + mMenuWidth , _SnakeGamePanel.getmMainFrameSizeHeight() + 22);
        //Đưa frame vào giữa màn hình        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        scrW = (int) toolkit.getScreenSize().getWidth();
        scrH = (int) toolkit.getScreenSize().getHeight();
        left = (int) (scrW - _SnakeGamePanel.getmMainFrameSizeWidth()) / 2;
        top = (int) (scrH - _SnakeGamePanel.getmMainFrameSizeHeight()) / 2;
        //System.out.println("left: " + left + " | top: " + top);
        setLocation(left, top);
        
        _SnakeGamePanel.setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
        
    }
    public static void main(String[] args) {
        new Game();
    }
}

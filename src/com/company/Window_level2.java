package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Window_level2 extends JPanel {

    private Locatie[][] map = new Locatie[10][10];
    private Characters hero = new Hero(0,0);
    private Characters wumpus;
    private Characters hole;
    private Characters treasure;
    private Game game;

    private static final int size=10;
    private static final int GROUND=0, HERO=1, WUMPUS=5, TREASURE= 10, HOLE=2, WIND=3, SMELL=4, SHINE=9;

    private JFrame frame = null;
    private GridLayout layout = new GridLayout(10,10);
    private JPanel panel = new JPanel();

    public Window_level2(int width, int height, String title, Game game) {

        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        this.game=game;

        panel.setLayout(layout);

        ///desenez mapa jocului
        initiateMap();
        drawMap();

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public void clearMap() {
        Component[] componentList = panel.getComponents();

        for(Component c : componentList){

            if(c instanceof JLabel){

                panel.remove(c);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
    public void putCharacters()
    {

        ///punem caracterele random inafara de Hero care e pe pozitia 0 0
        map[0][0].setValue(HERO);

        Random rand = new Random();
        int x;
        int y;
        wumpus = new Wumpus(0,0,0);
        while(!wumpus.isSet())
        {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            if(map[x][y].isFree())
            {
                map[x][y].setValue(WUMPUS);
                wumpus.setLocatie(x,y);
                wumpus.setValue(WUMPUS);
                if (x > 0 && map[x-1][y].isFree())
                    map[x - 1][y].setValue(SMELL);
                if (x < 9 && map[x+1][y].isFree())
                    map[x + 1][y].setValue(SMELL);
                if (y > 0 && map[x][y-1].isFree())
                    map[x][y - 1].setValue(SMELL);
                if (y < 9 && map[x][y+1].isFree())
                    map[x][y + 1].setValue(SMELL);
            }
        }

        treasure = new Treasure(0,0,0);
        while(!treasure.isSet())
        {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            if(map[x][y].isFree()) {
                map[x][y].setValue(TREASURE);
                treasure.setLocatie(x, y);
                treasure.setValue(TREASURE);
                if (x > 0 && map[x-1][y].isFree())
                    map[x - 1][y].setValue(SHINE);
                if (x < 9 && map[x+1][y].isFree())
                    map[x + 1][y].setValue(SHINE);
                if (y > 0 && map[x][y-1].isFree())
                    map[x][y - 1].setValue(SHINE);
                if (y < 9 && map[x][y+1].isFree())
                    map[x][y + 1].setValue(SHINE);
            }
        }

        hole = new Hole(0,0,0);
        while(!hole.isSet())
        {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            if(map[x][y].isFree()) {
                map[x][y].setValue(HOLE);
                hole.setValue(HOLE);
                hole.setLocatie(x, y);
                if (x > 0 && map[x-1][y].isFree())
                    map[x - 1][y].setValue(WIND);
                if (x < 9 && map[x+1][y].isFree())
                    map[x + 1][y].setValue(WIND);
                if (y > 0 && map[x][y-1].isFree())
                    map[x][y - 1].setValue(WIND);
                if (y < 9 && map[x][y+1].isFree())
                    map[x][y + 1].setValue(WIND);
            }
        }
    }

    public void redrawElements()
    {
        int x;
        int y;
        if(hero.isLife()) {
             x = hero.getLocatie().getX();
             y = hero.getLocatie().getY();
             map[x][y].setValue(HERO);
        }
        //refacem mirosul Wumpusului
        if(wumpus.isLife()) {
            x = wumpus.getLocatie().getX();
            y = wumpus.getLocatie().getY();

            if (x > 0) {
                if (map[x - 1][y].getValue() == GROUND)
                    map[x - 1][y].setValue(SMELL);
            }
            if (x < 9)
                if (map[x + 1][y].getValue() == GROUND)
                    map[x + 1][y].setValue(SMELL);
            if (y > 0) {
                if (map[x][y - 1].getValue() == GROUND)
                    map[x][y - 1].setValue(SMELL);
            }
            if (y < 9)
                if (map[x][y + 1].getValue() == GROUND)
                    map[x][y + 1].setValue(SMELL);
        }
        ///refacem vantul de langa groapa
        x = hole.getLocatie().getX();
        y = hole.getLocatie().getY();
        map[x][y].setValue(HOLE);

        if(x>0)
        {
            if(map[x-1][y].getValue()==GROUND)
                map[x-1][y].setValue(WIND);
        }
        if(x<9)
            if(map[x+1][y].getValue()==GROUND)
                map[x+1][y].setValue(WIND);
        if(y>0)
        {
            if(map[x][y-1].getValue()==GROUND)
                map[x][y-1].setValue(WIND);
        }
        if(y<9)
            if(map[x][y+1].getValue()==GROUND)
                map[x][y+1].setValue(WIND);

        ///refacem luminile de langa comoara
        x = treasure.getLocatie().getX();
        y = treasure.getLocatie().getY();
        map[x][y].setValue(TREASURE);

        if(x>0)
        {
            if(map[x-1][y].getValue()==GROUND)
                map[x-1][y].setValue(SHINE);
        }
        if(x<9)
            if(map[x+1][y].getValue()==GROUND)
                map[x+1][y].setValue(SHINE);
        if(y>0)
        {
            if(map[x][y-1].getValue()==GROUND)
                map[x][y-1].setValue(SHINE);
        }
        if(y<9)
            if(map[x][y+1].getValue()==GROUND)
                map[x][y+1].setValue(SHINE);
    }
    public void initiateMap()
    {
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++)
                map[i][j]= new Locatie(i,j,GROUND,true);

        map[0][0].setVisible(true);
        map[0][1].setVisible(true);
        map[1][0].setVisible(true);
    }

    public void drawMap()
    {
        clearMap();
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++) {
                if (!map[i][j].isVisible()) {
                    panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\black.jpg")));
                } else {
                    if (map[i][j].getValue() == GROUND) {
                        panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\ground.jpg")));
                    } else {
                        if (map[i][j].getValue() == HERO)
                            panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\hero.jpg")));
                        else {
                            if (map[i][j].getValue() == WUMPUS)
                                panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\wumpus.jpg")));
                            else {
                                if (map[i][j].getValue() == TREASURE)
                                    panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\treasure.jpg")));
                                else {
                                    if (map[i][j].getValue() == HOLE)
                                        panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\hole.jpg")));
                                    else {
                                        if (map[i][j].getValue() == WIND)
                                            panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\wind.jpg")));
                                        else {
                                            if (map[i][j].getValue() == SMELL)
                                                panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\smell.jpg")));
                                            else
                                                panel.add(new JLabel(new ImageIcon("D:\\java\\Proiecte\\Wumpus\\src\\com\\company\\Images\\shine.jpg")));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean endTheGame()
    {
        if(hero.getLocatie().getX()==wumpus.getLocatie().getX())
            if(hero.getLocatie().getY()==wumpus.getLocatie().getY())
                return true;
        if(hero.getLocatie().getX()==treasure.getLocatie().getX())
            if(hero.getLocatie().getY()==treasure.getLocatie().getY())
                return true;
        if(hero.getLocatie().getX()==hole.getLocatie().getX())
            if(hero.getLocatie().getY()==hole.getLocatie().getY())
                return true;
        return false;
    }

    public void checkHeroPosition(String directie)
    {
        int x = hero.getLocatie().getX();
        int y = hero.getLocatie().getY();

        JFrame framePopup = new JFrame("Help message");
        framePopup.setPreferredSize(new Dimension(180, 150));
        framePopup.setMinimumSize(new Dimension(180, 150));
        framePopup.setMaximumSize(new Dimension(180, 150));

        framePopup.setResizable(false);
        framePopup.setLocationRelativeTo(null);
        JPanel panelPopup = new JPanel();
        JLabel labelPopup = null;
        JButton buttonPopup = new JButton("Ok");
        buttonPopup.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                framePopup.dispose();
            }
        });

        if(directie.equals("up"))
        {
            if(map[x-1][y].getValue()==WIND)
            {
                labelPopup = new JLabel("You are near the pit");
            }
            if(map[x-1][y].getValue()==SMELL)
            {
                labelPopup = new JLabel("The Wumpus is near you");
            }
            if(map[x-1][y].getValue()==SHINE)
            {
                labelPopup = new JLabel("The treasure is near you");
            }
        }
        else
        {
            if(directie.equals("down"))
            {
                if(map[x+1][y].getValue()==WIND)
                {
                    labelPopup = new JLabel("You are near the pit");
                }
                if(map[x+1][y].getValue()==SMELL)
                {
                    labelPopup = new JLabel("The Wumpus is near you");
                }
                if(map[x+1][y].getValue()==SHINE)
                {
                    labelPopup = new JLabel("The treasure is near you");
                }
            }
            else {
                if (directie.equals("left")) {
                    if (map[x][y - 1].getValue() == WIND) {
                        labelPopup = new JLabel("You are near the pit");
                    }
                    if (map[x][y - 1].getValue() == SMELL) {
                        labelPopup = new JLabel("The Wumpus is near you");
                    }
                    if (map[x][y - 1].getValue() == SHINE) {
                        labelPopup = new JLabel("The treasure is near you");
                    }
                } else {
                    if (map[x][y + 1].getValue() == WIND) {
                        labelPopup = new JLabel("You are near the pit");
                    }
                    if (map[x][y + 1].getValue() == SMELL) {
                        labelPopup = new JLabel("The Wumpus is near you");
                    }
                    if (map[x][y + 1].getValue() == SHINE) {
                        labelPopup = new JLabel("The treasure is near you");
                    }
                }
            }
        }
        if(labelPopup != null)
        {
            panelPopup.add(labelPopup);
            panelPopup.add(buttonPopup);
            framePopup.setContentPane(panelPopup);
            framePopup.setVisible(true);
        }
    }

    public void postMessegeEndTheGame()
    {
        boolean win = false;
        JFrame frameEndGame = new JFrame();
        frameEndGame.setPreferredSize(new Dimension(250, 150));
        frameEndGame.setMinimumSize(new Dimension(250, 150));
        frameEndGame.setMaximumSize(new Dimension(250, 150));

        frameEndGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEndGame.setResizable(false);
        frameEndGame.setLocationRelativeTo(null);

        JPanel panel1 = new JPanel();
        JLabel text = null;
        if(hero.getLocatie().getX()==wumpus.getLocatie().getX())
            if(hero.getLocatie().getY()==wumpus.getLocatie().getY()) {
                text = new JLabel("The Wumpus found you");
            }
        if(hero.getLocatie().getX()==treasure.getLocatie().getX())
            if(hero.getLocatie().getY()==treasure.getLocatie().getY()) {
                text = new JLabel("Conhratulations! You found the treasure!");
                win = true;
            }
        if(hero.getLocatie().getX()==hole.getLocatie().getX())
            if(hero.getLocatie().getY()==hole.getLocatie().getY()) {
                text = new JLabel("Sadly, you felt into the pit");
            }
        panel1.add(text);
        panel1.setLayout(new FlowLayout());
        JButton b1 = new JButton("Close the game");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        JButton b2 = new JButton("Go the the next level");
        b2.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Window_level3(500,500,"Level3",game);
                frameEndGame.dispose();
                frame.dispose();
                Window_level3 window3 = new Window_level3(500,500,"Level3",game);
                window3.putCharacters();
                window3.drawMap();
                window3.startTheGame();
            }
        });
        JButton b3 = new JButton("Try again");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frameEndGame.dispose();
                frame.dispose();

                Window_level2 level2 = new Window_level2(500,500,"Level2",game);
                level2.putCharacters();
                level2.drawMap();
                level2.startTheGame();
            }
        });

        panel1.add(b1);
        if(win)
            panel1.add(b2);
        else
            panel1.add(b3);

        frameEndGame.setContentPane(panel1);
        frameEndGame.setVisible(true);

    }

    public void heroShoot(String direction)
    {
        int x = hero.getLocatie().getX();
        int y= hero.getLocatie().getY();
        boolean hit = false;
        boolean wrongInput = false;
        int i=0;
        if(direction.equals("N") || direction.equals("n"))
        {
            for(i=x ;i>=0 && !hit;i--)
                if(map[i][y].getValue()==WUMPUS)
                    hit=true;
        }
        else {
            if (direction.equals("S") || direction.equals("s")) {
                for (i = x; i <= 9 && !hit; i++)
                    if (map[i][y].getValue() == WUMPUS)
                        hit = true;
            }
            else
            {
                if(direction.equals("W") || direction.equals("w"))
                {
                    for (i = y; i >= 0 && !hit; i--)
                        if (map[x][i].getValue() == WUMPUS)
                            hit = true;
                }
                else {
                    if (direction.equals("E") || direction.equals("e")) {
                        for (i = y; i <= 9 && !hit; i++) {
                            if (map[x][i].getValue() == WUMPUS)
                                hit = true;
                        }
                    } else
                        wrongInput = true;
                }
            }
        }

        if(wrongInput) {
            JFrame wrongInputFrame = new JFrame("Wrong input");
            wrongInputFrame.setPreferredSize(new Dimension(250, 100));
            wrongInputFrame.setMinimumSize(new Dimension(250, 100));
            wrongInputFrame.setMaximumSize(new Dimension(250, 100));

            JPanel wrongInputPanel = new JPanel();
            JLabel wrongInputLabel = new JLabel("Wrong input. Try again!");
            JButton wrongInputButton = new JButton("Ok");
            wrongInputButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    wrongInputFrame.dispose();
                }
            });

            wrongInputPanel.add(wrongInputLabel);
            wrongInputPanel.add(wrongInputButton);

            wrongInputFrame.setContentPane(wrongInputPanel);
            wrongInputFrame.setLocationRelativeTo(null);
            wrongInputFrame.setVisible(true);

        }
        else
        {
            JLabel hitLabel = null;
            if (hit) {
                x = wumpus.getLocatie().getX();
                y = wumpus.getLocatie().getY();
                wumpus.setLocatie(-1, -1);
                wumpus.setLife(false);
                map[x][y].setValue(GROUND);
                if (x > 0)
                    map[x - 1][y].setValue(GROUND);
                if (x < 9)
                    map[x + 1][y].setValue(GROUND);
                if (y > 0)
                    map[x][y - 1].setValue(GROUND);
                if (y < 9)
                    map[x][y + 1].setValue(GROUND);
                redrawElements();
                hitLabel = new JLabel("You hit the Wumpus");
            } else {
                hitLabel = new JLabel("You missed the Wumpus");
            }
            JFrame hitFrame = new JFrame("Hit");
            hitFrame.setPreferredSize(new Dimension(250, 100));
            hitFrame.setMinimumSize(new Dimension(250, 100));
            hitFrame.setMaximumSize(new Dimension(250, 100));

            JPanel hitPanel = new JPanel();
            JButton hitButton = new JButton("Ok");
            hitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    hitFrame.dispose();
                }
            });

            hitPanel.add(hitLabel);
            hitPanel.add(hitButton);

            hitFrame.setContentPane(hitPanel);
            hitFrame.setVisible(true);
            hero.setBullets(0);
            drawMap();
        }
    }

    public boolean checkWumpusMove(String direction)
    {
        int x = wumpus.getLocation().getX();
        int y = wumpus.getLocation().getY();
        if(direction.equals("n"))
        {
            if(x>0 && (map[x-1][y].getValue()==GROUND ||  map[x-1][y].getValue()==WIND || map[x-1][y].getValue()==SHINE || map[x-1][y].getValue()==SMELL) )
                return true;
            return false;
        }
        if(direction.equals("s"))
        {
            if(x<9 && (map[x+1][y].getValue()==GROUND ||  map[x+1][y].getValue()==WIND || map[x+1][y].getValue()==SHINE || map[x+1][y].getValue()==SMELL) )
                return true;
            return false;
        }
        if(direction.equals("e"))
        {
            if(y<9 && (map[x][y+1].getValue()==GROUND ||  map[x][y+1].getValue()==WIND || map[x][y+1].getValue()==SHINE || map[x][y+1].getValue()==SMELL) )
                return true;
            return false;
        }
        if(direction.equals("w"))
        {
            if(y>0 && (map[x][y-1].getValue()==GROUND ||  map[x][y-1].getValue()==WIND || map[x][y-1].getValue()==SHINE || map[x][y-1].getValue()==SMELL) )
                return true;
            return false;
        }
        return false;
    }

    public void clearExWumpus()
    {
        int x = wumpus.getLocatie().getX();
        int y = wumpus.getLocatie().getY();

        map[x][y].setValue(GROUND);
        if(x>0)
            map[x-1][y].setValue(GROUND);
        if(x<9)
            map[x+1][y].setValue(GROUND);
        if(y>0)
            map[x][y-1].setValue(GROUND);
        if(y<9)
            map[x][y+1].setValue(GROUND);

    }
    public void moveWumpus()
    {
        if(wumpus.isLife()) {
            int xh = hero.getLocatie().getX();
            int yh = hero.getLocatie().getY();
            int xw = wumpus.getLocation().getX();
            int yw = wumpus.getLocation().getY();

            Locatie wumpusPosition = wumpus.getLocatie();
            if (xh < xw) {
                if (checkWumpusMove("n")) {
                    clearExWumpus();
                    wumpusPosition.setX(wumpusPosition.getX() - 1);
                    map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                } else {
                    if (yh < yw) {
                        if (checkWumpusMove("w")) {
                            clearExWumpus();
                            wumpusPosition.setY(wumpusPosition.getY() - 1);
                            map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                        }
                    } else {
                        if (checkWumpusMove("e")) {
                            clearExWumpus();
                            wumpusPosition.setY(wumpusPosition.getY() + 1);
                            map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                        }
                    }
                }
            } else {
                if (xh > xw) {
                    if (checkWumpusMove("s")) {
                        clearExWumpus();
                        wumpusPosition.setX(wumpusPosition.getX() + 1);
                        map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                    } else {
                        if (yh < yw) {
                            if (checkWumpusMove("w")) {
                                clearExWumpus();
                                wumpusPosition.setY(wumpusPosition.getY() - 1);
                                map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                            } else {
                                clearExWumpus();
                                wumpusPosition.setY(wumpusPosition.getY() + 1);
                                map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                            }
                        }
                    }
                } else {
                    if (yh < yw) {
                        if (checkWumpusMove("w")) {
                            clearExWumpus();
                            wumpusPosition.setY(wumpusPosition.getY() - 1);
                            map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                        }
                    } else {
                        if (checkWumpusMove("e")) {
                            clearExWumpus();
                            wumpusPosition.setY(wumpusPosition.getY() + 1);
                            map[wumpusPosition.getX()][wumpusPosition.getY()].setValue(WUMPUS);
                        }
                    }
                }
            }
        }
        redrawElements();
        drawMap();
    }

    public void startTheGame(){
        frame.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        Locatie heroLocation = hero.getLocation();
                        if (heroLocation.getX() > 0) {
                            checkHeroPosition("up");
                            heroLocation.setX(heroLocation.getX() - 1);
                            map[heroLocation.getX()][heroLocation.getY()].setValue(HERO);
                            map[heroLocation.getX() + 1][heroLocation.getY()].setValue(GROUND);
                            map[heroLocation.getX()][heroLocation.getY()].setVisible(true);
                        }
                        if(endTheGame())
                        {
                            map[hero.getLocatie().getX()][hero.getLocatie().getY()].setValue(GROUND);
                            hero.setLife(false);
                            redrawElements();
                            drawMap();
                            postMessegeEndTheGame();
                        }
                        moveWumpus();
                        drawMap();
                        break;
                    case KeyEvent.VK_DOWN:
                        heroLocation = hero.getLocation();
                        if (heroLocation.getX() < 9) {
                            checkHeroPosition("down");
                            heroLocation.setX(heroLocation.getX() + 1);
                            map[heroLocation.getX()][heroLocation.getY()].setValue(HERO);
                            map[heroLocation.getX() - 1][heroLocation.getY()].setValue(GROUND);
                            map[heroLocation.getX()][heroLocation.getY()].setVisible(true);
                        }
                        if(endTheGame())
                        {
                            map[hero.getLocatie().getX()][hero.getLocatie().getY()].setValue(GROUND);
                            hero.setLife(false);
                            redrawElements();
                            drawMap();
                            postMessegeEndTheGame();
                        }
                        moveWumpus();
                        drawMap();
                        break;
                    case KeyEvent.VK_LEFT:
                        heroLocation = hero.getLocation();
                        if (heroLocation.getY() > 0) {
                            checkHeroPosition("left");
                            heroLocation.setY(heroLocation.getY() - 1);
                            map[heroLocation.getX()][heroLocation.getY()].setValue(HERO);
                            map[heroLocation.getX()][heroLocation.getY() + 1].setValue(GROUND);
                            map[heroLocation.getX()][heroLocation.getY()].setVisible(true);
                        }
                        if(endTheGame())
                        {
                            map[hero.getLocatie().getX()][hero.getLocatie().getY()].setValue(GROUND);
                            hero.setLife(false);
                            redrawElements();
                            drawMap();
                            postMessegeEndTheGame();
                        }
                        moveWumpus();
                        drawMap();
                        break;
                    case KeyEvent.VK_RIGHT:
                        heroLocation = hero.getLocation();
                        if (heroLocation.getY() < 9) {
                            checkHeroPosition("right");
                            heroLocation.setY(heroLocation.getY() + 1);
                            map[heroLocation.getX()][heroLocation.getY()].setValue(HERO);
                            map[heroLocation.getX()][heroLocation.getY() - 1].setValue(GROUND);
                            map[heroLocation.getX()][heroLocation.getY()].setVisible(true);
                        }
                        if(endTheGame())
                        {
                            map[hero.getLocatie().getX()][hero.getLocatie().getY()].setValue(GROUND);
                            hero.setLife(false);
                            redrawElements();
                            drawMap();
                            postMessegeEndTheGame();
                        }
                        moveWumpus();
                        drawMap();
                        break;
                    case KeyEvent.VK_S:
                        if(hero.getBullets()==1) {
                            redrawElements();
                            JFrame shootFrame = new JFrame("Aim&Shoot");
                            shootFrame.setPreferredSize(new Dimension(250, 150));
                            shootFrame.setMinimumSize(new Dimension(250, 150));
                            shootFrame.setMaximumSize(new Dimension(250, 150));

                            JPanel shootPanel = new JPanel();
                            JLabel shootLabel = new JLabel("Where to shoot? (N,S,E,W)");
                            JTextField shootText = new JTextField();
                            shootText.setMinimumSize(new Dimension(30, 30));
                            shootText.setPreferredSize(new Dimension(30, 30));
                            JButton shootButton = new JButton("Shoot");

                            shootButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    String cmd = shootText.getText();
                                    String trim = cmd.replaceAll("^\\s+", "");
                                    shootFrame.dispose();
                                    heroShoot(trim);
                                }
                            });

                            shootPanel.add(shootLabel);
                            shootPanel.add(shootText);
                            shootPanel.add(shootButton);
                            shootFrame.setContentPane(shootPanel);
                            shootFrame.setVisible(true);
                        }
                        else
                        {
                            JFrame noBulletsFrame = new JFrame("No Bullets");
                            noBulletsFrame.setPreferredSize(new Dimension(250, 100));
                            noBulletsFrame.setMinimumSize(new Dimension(250, 100));
                            noBulletsFrame.setMaximumSize(new Dimension(250, 100));

                            JPanel noBulletsPanel = new JPanel();
                            JLabel noBulletsLabel = new JLabel("No more bullets");
                            JButton noBulletsButton = new JButton("Ok");
                            noBulletsButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    noBulletsFrame.dispose();
                                }
                            });

                            noBulletsPanel.add(noBulletsLabel);
                            noBulletsLabel.add(noBulletsButton);

                            noBulletsFrame.setContentPane(noBulletsPanel);
                            noBulletsFrame.setVisible(true);
                        }
                        break;
                    default:
                        JFrame errorFrame = new JFrame("Error");
                        errorFrame.setPreferredSize(new Dimension(310, 100));
                        errorFrame.setMinimumSize(new Dimension(310, 100));
                        errorFrame.setMaximumSize(new Dimension(310, 100));
                        errorFrame.setLocationRelativeTo(null);

                        JPanel errorPanel = new JPanel();
                        JLabel errorLabel = new JLabel("You can use only the arroy keys and S for shooting");
                        JButton errorButton = new JButton("Ok");
                        errorButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                errorFrame.dispose();
                            }
                        });
                        errorPanel.add(errorLabel);
                        errorPanel.add(errorButton);

                        errorFrame.setContentPane(errorPanel);
                        errorFrame.setVisible(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }
}

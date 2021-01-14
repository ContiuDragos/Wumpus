package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Window extends JPanel {

    private Locatie[][] map = new Locatie[10][10];
    private Characters hero = new Hero(0,0);
    private Characters wumpus;
    private Characters hole;
    private Characters treasure;

    private static final int size=10;
    private static final int GROUND=0, HERO=1, WUMPUS=5, TREASURE= 10, HOLE=2, WIND=3, SMELL=4, SHINE=9;

    private JFrame frame = null;
    private GridLayout layout = new GridLayout(10,10);
    private JPanel panel = new JPanel();

    public Window(int width, int height, String title, Game game) {

        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);

        panel.setLayout(layout);

        ///desenez mapa jocului
        initiateMap();

        JFrame frameStartTheGame = new JFrame("Regulile jocului");
        frameStartTheGame.setPreferredSize(new Dimension(width, height));
        frameStartTheGame.setMinimumSize(new Dimension(width, height));
        frameStartTheGame.setMaximumSize(new Dimension(width, height));


        JPanel panelStartTheGame = new JPanel();
        panelStartTheGame.setLayout(new GridLayout(3,0));
        JLabel text1 = new JLabel("Regulile jocului");
        text1.setBorder(BorderFactory.createEmptyBorder(0, 200, 0,0));
        JLabel text2 = new JLabel("1. Te poti deplasa cu ajutorul sagetilor si sa tragi cu S");
        JLabel text3 = new JLabel("2. Dispui de o arma cu un singur glont");
        JLabel text4 = new JLabel("3. Scopul este sa gasesti comoara, sa nu intalnesti Wumpusul si sa nu cazi in groapa");
        JLabel text5 = new JLabel("4. Vezi doar unde te afli si pe unde ai mai fost");
        JLabel text6 = new JLabel("5. Vantul e langa groapa, mirosul langa Wumpus si luminile langa comoara");
        JButton buttonStartTheGame = new JButton("Ok, am inteles");
        buttonStartTheGame.setBounds(10,10,10,10);
        buttonStartTheGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frameStartTheGame.dispose();
                frame.setVisible(true);
            }
        });
        panelStartTheGame.add(text1);

        JPanel panelReguli = new JPanel();
        panelReguli.setLayout(new GridLayout(5,0));
        panelReguli.add(text2);
        panelReguli.add(text3);
        panelReguli.add(text4);
        panelReguli.add(text5);
        panelReguli.add(text6);

        panelStartTheGame.add(panelReguli);
        panelStartTheGame.add(buttonStartTheGame);
        frameStartTheGame.setContentPane(panelStartTheGame);
        frameStartTheGame.setVisible(true);
        frame.setContentPane(panel);
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
                map[i][j]= new Locatie(i,j,GROUND,false);

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

        JFrame framePopup = new JFrame("Mesaj de ajutor");
        framePopup.setPreferredSize(new Dimension(150, 150));
        framePopup.setMinimumSize(new Dimension(150, 150));
        framePopup.setMaximumSize(new Dimension(150, 150));

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
                labelPopup = new JLabel("Esti langa groapa");
            }
            if(map[x-1][y].getValue()==SMELL)
            {
                labelPopup = new JLabel("Wumpusul e langa tine");
            }
            if(map[x-1][y].getValue()==SHINE)
            {
                labelPopup = new JLabel("Comoara e langa tine");
            }
        }
        else
        {
            if(directie.equals("down"))
            {
                if(map[x+1][y].getValue()==WIND)
                {
                    labelPopup = new JLabel("Esti langa groapa");
                }
                if(map[x+1][y].getValue()==SMELL)
                {
                    labelPopup = new JLabel("Wumpusul e langa tine");
                }
                if(map[x+1][y].getValue()==SHINE)
                {
                    labelPopup = new JLabel("Comoara e langa tine");
                }
            }
            else {
                if (directie.equals("left")) {
                    if (map[x][y - 1].getValue() == WIND) {
                        labelPopup = new JLabel("Esti langa groapa");
                    }
                    if (map[x][y - 1].getValue() == SMELL) {
                        labelPopup = new JLabel("Wumpusul e langa tine");
                    }
                    if (map[x][y - 1].getValue() == SHINE) {
                        labelPopup = new JLabel("Comoara e langa tine");
                    }
                } else {
                    if (map[x][y + 1].getValue() == WIND) {
                        labelPopup = new JLabel("Esti langa groapa");
                    }
                    if (map[x][y + 1].getValue() == SMELL) {
                        labelPopup = new JLabel("Wumpusul e langa tine");
                    }
                    if (map[x][y + 1].getValue() == SHINE) {
                        labelPopup = new JLabel("Comoara e langa tine");
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
        JFrame frameEndGame = new JFrame();
        frameEndGame.setPreferredSize(new Dimension(250, 100));
        frameEndGame.setMinimumSize(new Dimension(250, 100));
        frameEndGame.setMaximumSize(new Dimension(250, 100));

        frameEndGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEndGame.setResizable(false);
        frameEndGame.setLocationRelativeTo(null);

        JPanel panel1 = new JPanel();
        JLabel text = null;
        if(hero.getLocatie().getX()==wumpus.getLocatie().getX())
            if(hero.getLocatie().getY()==wumpus.getLocatie().getY()) {
                text = new JLabel("Te-a mancat Wumpusul");
            }
        if(hero.getLocatie().getX()==treasure.getLocatie().getX())
            if(hero.getLocatie().getY()==treasure.getLocatie().getY()) {
                text = new JLabel("Felicitari ai gasit comoara");
            }
        if(hero.getLocatie().getX()==hole.getLocatie().getX())
            if(hero.getLocatie().getY()==hole.getLocatie().getY()) {
                text = new JLabel("Ai fost neatent si ai cazut in groapa");
            }
        panel1.add(text);
        panel1.setLayout(new FlowLayout());
        JButton b1 = new JButton("Inchide jocul");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        panel1.add(b1);

        frameEndGame.setContentPane(panel1);
        frameEndGame.setVisible(true);
        
    }

    public void heroShoot(String direction)
    {
        int x = hero.getLocatie().getX();
        int y= hero.getLocatie().getY();
        boolean hit = false;
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
                    else
                        if(direction.equals("E") || direction.equals("e")) {
                            for (i = y; i <= 9 && !hit; i++) {
                                if (map[x][i].getValue() == WUMPUS)
                                    hit = true;
                            }
                        }
                }
        }
        JLabel hitLabel = null;
        if(hit)
        {
            x = wumpus.getLocatie().getX();
            y = wumpus.getLocatie().getY();
            wumpus.setLocatie(-1,-1);
            wumpus.setLife(false);
            map[x][y].setValue(GROUND);
            if(x>0)
                map[x-1][y].setValue(GROUND);
            if(x<9)
                map[x+1][y].setValue(GROUND);
            if(y>0)
                map[x][y-1].setValue(GROUND);
            if(y<9)
                map[x][y+1].setValue(GROUND);
            redrawElements();
            hitLabel = new JLabel("Ai nimerit Wumpusul");
        }
        else
        {
            hitLabel = new JLabel("Ai ratat Wumpusul");
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
                        redrawElements();
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
                        redrawElements();
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
                        redrawElements();
                        drawMap();
                        break;
                    case KeyEvent.VK_RIGHT:
                        redrawElements();
                        heroLocation = hero.getLocation();
                        if (heroLocation.getY() < 9) {
                            checkHeroPosition("right");
                            heroLocation.setY(heroLocation.getY() + 1);
                            map[heroLocation.getX()][heroLocation.getY()].setValue(HERO);
                            map[heroLocation.getX()][heroLocation.getY() - 1].setValue(GROUND);
                            map[heroLocation.getX()][heroLocation.getY()].setVisible(true);
                        }
                        redrawElements();
                        drawMap();
                        break;
                    case KeyEvent.VK_S:
                        if(hero.getBullets()==1) {
                            redrawElements();
                            JFrame shootFrame = new JFrame("Aim&Shoot");
                            shootFrame.setPreferredSize(new Dimension(250, 100));
                            shootFrame.setMinimumSize(new Dimension(250, 100));
                            shootFrame.setMaximumSize(new Dimension(250, 100));

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
                            JLabel noBulletsLabel = new JLabel("Nu mai ai gloante");
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
                        errorFrame.setPreferredSize(new Dimension(250, 100));
                        errorFrame.setMinimumSize(new Dimension(250, 100));
                        errorFrame.setMaximumSize(new Dimension(250, 100));

                        JPanel errorPanel = new JPanel();
                        JLabel errorLabel = new JLabel("Poti folosi doar sagetile sau S pentru a trage");
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
                if(endTheGame())
                {
                    postMessegeEndTheGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }
}

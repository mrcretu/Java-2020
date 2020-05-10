package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Data;
import listener.BoardListener;

public class BoardCanvas extends Canvas {
    private final int MAP_WIDTH = 531;
    private static final long serialVersionUID = 1L;
    private final int MAP_HEIGHT = 531;
    String sourcePath = null;
    BufferedImage chessBoardImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, ColorSpace.TYPE_RGB);
    Graphics2D g = chessBoardImage.createGraphics();

    public BoardCanvas() {
        this.paintBoardImage();
        this.setSize(MAP_WIDTH, MAP_HEIGHT);
        this.addMouseListener(new BoardListener());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(chessBoardImage, 0, 0, null);
    }

    public void paintBoardImage() {
        this.paintBackground();
        this.paintChess();
    }

    public void paintBackground() {
        try {
            BufferedImage background = ImageIO.read(this.getClass().getResource("map.png"));
            g.drawImage(background, 0, 0, null);
        } catch (IOException e) {
            this.setBackground(new Color(210, 180, 140));
            g.setColor(Color.black);
            for (int i = 0; i < 15; i++) {
                g.drawLine((35 * i + 20), 20, (35 * i + 20), 510);
            }
            for (int i = 0; i < 15; i++) {
                g.drawLine(20, (35 * i + 20), 510, (35 * i + 20));
            }
            g.fillRect(122, 122, 7, 7);
            g.fillRect(402, 122, 7, 7);
            g.fillRect(122, 402, 7, 7);
            g.fillRect(402, 402, 7, 7);
            g.fillRect(262, 262, 7, 7);

            e.printStackTrace();
        }
    }

    public void paintChess() {
        BufferedImage black = null;
        BufferedImage white = null;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (Data.chessBoard[i][j] == Data.BLACK) {
                    try {
                        if (15 * j + i == Data.last) {
                            black = ImageIO.read(this.getClass().getResource("black2.png"));
                        } else {
                            black = ImageIO.read(this.getClass().getResource("black.png"));
                        }
                        g.drawImage(black, i * 35 + 4, j * 35 + 4, null);

                    } catch (IOException e) {
                        g.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);

                        e.printStackTrace();
                    }
                }
                else if (Data.chessBoard[i][j] == Data.WHITE) {
                    try {
                        if (15 * j + i == Data.last) {
                            white = ImageIO.read(this.getClass().getResource("white2.png"));
                        } else {
                            white = ImageIO.read(this.getClass().getResource("white.png"));
                        }
                        g.drawImage(white, i * 35 + 4, j * 35 + 4, null);
                    } catch (IOException e) {
                        g.setColor(Color.white);
                        g.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);
                        g.setColor(Color.black);

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getSourcePath() {
        if (sourcePath == null) {
            sourcePath = new File("").getAbsolutePath();
        }
        return sourcePath;
    }

    public int getMapWidth() {
        return MAP_WIDTH;
    }

    public int getMapHeitht() {
        return MAP_HEIGHT;
    }

}
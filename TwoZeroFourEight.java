import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * 2048 Game!
 * Slide the board around and merge identical squares in order to reach 2048!
 * @author - Austin Miller
 * 04-03-2025
 */
public class TwoZeroFourEight extends JFrame{
    private JPanel gamePanel;
    private static boolean gameOver;
    private static int[][] board;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TwoZeroFourEight visualizer = new TwoZeroFourEight();
            visualizer.setVisible(true);
        });
    }

    /**
     * Initializes the board and holds the action listeners
     */
    public TwoZeroFourEight() {
        setNew();

        setTitle("2048!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        gamePanel.setBackground(new Color(250, 248, 239)); // Match the board background
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setLocationRelativeTo(null);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    boolean moved = false;
                    // Shove cells to the bottom
                    for (int i = 0; i < 3; i++) {
                        for (int row = 3; row >= 1; row--) {
                            for (int col = 0; col < 4; col++) {
                                if (board[row][col] == 0 && board[row - 1][col] != 0) {
                                    board[row][col] = board[row - 1][col];
                                    board[row - 1][col] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    // Merge cells
                    for (int row = 3; row >= 1; row--) {
                        for (int col = 0; col < 4; col++) {
                            if (board[row][col] == board[row - 1][col] && board[row][col] != 0) {
                                board[row][col] *= 2;
                                board[row - 1][col] = 0;
                                moved = true;
                            }
                        }
                    }
                    // Shove cells to the bottom
                    for (int i = 0; i < 3; i++) {
                        for (int row = 3; row >= 1; row--) {
                            for (int col = 0; col < 4; col++) {
                                if (board[row][col] == 0 && board[row - 1][col] != 0) {
                                    board[row][col] = board[row - 1][col];
                                    board[row - 1][col] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    if (moved) {
                        addTile();
                    }
                    gamePanel.repaint();
                    checkForLoss();

                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    boolean moved = false;
                    // Shove cells to the top
                    for (int i = 0; i < 3; i++) {
                        for (int row = 0; row < 3; row++) {
                            for (int col = 0; col < 4; col++) {
                                if (board[row][col] == 0 && board[row + 1][col] != 0) {
                                    board[row][col] = board[row + 1][col];
                                    board[row + 1][col] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    // Merge cells
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 4; col++) {
                            if (board[row][col] == board[row + 1][col] && board[row][col] != 0) {
                                board[row][col] *= 2;
                                board[row + 1][col] = 0;
                                moved = true;
                            }
                        }
                    }
                    for (int i = 0; i < 3; i++) {
                        for (int row = 0; row < 3; row++) {
                            for (int col = 0; col < 4; col++) {
                                if (board[row][col] == 0 && board[row + 1][col] != 0) {
                                    board[row][col] = board[row + 1][col];
                                    board[row + 1][col] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    if (moved) {
                        addTile();
                    }
                    gamePanel.repaint();
                    checkForLoss();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    boolean moved = false;
                    // Shove cells to the left
                    for (int i = 0; i < 3; i++) {
                        for (int col = 0; col < 3; col++) {
                            for (int row = 0; row < 4; row++) {
                                if (board[row][col] == 0 && board[row][col + 1] != 0) {
                                    board[row][col] = board[row][col + 1];
                                    board[row][col + 1] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    // Merge cells
                    for (int col = 0; col < 3; col++) {
                        for (int row = 0; row < 4; row++) {
                            if (board[row][col] == board[row][col + 1] && board[row][col] != 0) {
                                board[row][col] *= 2;
                                board[row][col + 1] = 0;
                                moved = true;
                            }
                        }
                    }
                    // Shove cells to the left
                    for (int i = 0; i < 3; i++) {
                        for (int col = 0; col < 3; col++) {
                            for (int row = 0; row < 4; row++) {
                                if (board[row][col] == 0 && board[row][col + 1] != 0) {
                                    board[row][col] = board[row][col + 1];
                                    board[row][col + 1] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    if (moved) {
                        addTile();
                    }
                    gamePanel.repaint();
                    checkForLoss();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    boolean moved = false;
                    // Shove cells to the right
                    for (int i = 0; i < 3; i++) {
                        for (int col = 3; col > 0; col--) {
                            for (int row = 0; row < 4; row++) {
                                if (board[row][col] == 0 && board[row][col - 1] != 0) {
                                    board[row][col] = board[row][col - 1];
                                    board[row][col - 1] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    // Merge cells
                    for (int col = 3; col > 0; col--) {
                        for (int row = 0; row < 4; row++) {
                            if (board[row][col] == board[row][col - 1] && board[row][col] != 0) {
                                board[row][col] *= 2;
                                board[row][col - 1] = 0;
                                moved = true;
                            }
                        }
                    }
                    // Shove cells to the right
                    for (int i = 0; i < 3; i++) {
                        for (int col = 3; col > 0; col--) {
                            for (int row = 0; row < 4; row++) {
                                if (board[row][col] == 0 && board[row][col - 1] != 0) {
                                    board[row][col] = board[row][col - 1];
                                    board[row][col - 1] = 0;
                                    moved = true;
                                }
                            }
                        }
                    }
                    if (moved) {
                        addTile();
                    }
                    gamePanel.repaint();
                    checkForLoss();
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (gameOver) {
                        setNew();
                        gamePanel.repaint();
                    }
                }
            }
        });
    }

    /**
     * If no empty cells or possible merges, return. Otherwise return false.
     */
    public void checkForLoss() {
        // Check for empty cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return;
                }
            }
        }

        // Check for possible horizontal merges
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == board[i][j+1]) {
                    return;
                }
            }
        }

        // Check for possible vertical merges
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i+1][j]) {
                    return;
                }
            }
        }

        gameOver = true;
        gamePanel.repaint();
    }

    /**
     * Resets the board
     */
    public void setNew() {
        gameOver = false;
        board = new int[4][4];
        addTile();
        addTile();
    }

    /**
     * Adds a 2 or a 4 to a random empty tile
     */
    public void addTile() {
        int zeroCnt = 0;
        int tileVal = (((int) (Math.random() * 2) + 1) == 1 ? 2 : 4);

        for (int[] row: board) {
            for (int val: row) {
                if (val == 0) { zeroCnt++; }
            }
        }

        if (zeroCnt == 0) return;
        int tilePlace = ((int) (Math.random() * zeroCnt) + 1);
        zeroCnt = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    zeroCnt++;
                    if (zeroCnt == tilePlace) {
                        board[i][j] = tileVal;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Paints a new board
     * @param g - graphics
     */
    private void drawBoard(Graphics g) {
        int panelWidth = gamePanel.getWidth();
        int panelHeight = gamePanel.getHeight();

        BufferedImage bufferedImage = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRect(0, 0, panelWidth, panelHeight);

        int margin = 15;
        int boardSize = Math.min(panelWidth, panelHeight) - 2 * margin;
        int cellSize = (boardSize - 5 * margin) / 4;

        int startX = (panelWidth - boardSize) / 2;
        int startY = (panelHeight - boardSize) / 2;

        g2d.setColor(new Color(50, 50, 50));
        g2d.fillRoundRect(startX, startY, boardSize, boardSize, 15, 15);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int x = startX + margin * (col + 1) + cellSize * col;
                int y = startY + margin * (row + 1) + cellSize * row;

                int value = board[row][col];

                g2d.setColor(getCellColor(value));
                g2d.fillRoundRect(x, y, cellSize, cellSize, 8, 8);

                if (value != 0) {
                    drawTileValue(g2d, value, x, y, cellSize);
                }
            }
        }

        // Draw game over screen if needed
        if (gameOver) {
            // Semi-transparent overlay
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            // Game over text
            g2d.setColor(new Color(119, 110, 101));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 60));
            String gameOverText = "Game Over";
            FontMetrics fmGameOver = g2d.getFontMetrics();
            int gameOverWidth = fmGameOver.stringWidth(gameOverText);
            g2d.drawString(gameOverText, (panelWidth - gameOverWidth) / 2, panelHeight / 2 - 30);

            // Instructions to restart
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 20));
            String restartText = "Press 'R' to restart";
            FontMetrics fmRestart = g2d.getFontMetrics();  // Get new metrics for new font
            int restartWidth = fmRestart.stringWidth(restartText);
            g2d.drawString(restartText, (panelWidth - restartWidth) / 2, panelHeight / 2 + 30);
        }

        g.drawImage(bufferedImage, 0, 0, null);
        g2d.dispose();
    }

    /**
     * Gets the appropriate background color for a cell based on its value
     */
    private Color getCellColor(int value) {
        switch (value) {
            case 0:    return Color.white;
            case 2:
            case 4:
            case 8:
                return Color.lightGray;
            case 16:
            case 32:
            case 64:
                return Color.gray;
            case 128:
            case 256:
            case 512:
            case 1024:
                return Color.darkGray;
            case 2048: return Color.black;
            default:   return Color.red;
        }
    }

    /**
     * Draws the tile value with properly sized font based on the number of digits
     */
    private void drawTileValue(Graphics2D g2d, int value, int x, int y, int cellSize) {
        String valueStr = String.valueOf(value);

        int fontSize;
        if (value < 100) {
            fontSize = cellSize / 2;
        } else if (value < 1000) {
            fontSize = cellSize / 2 - 6;
        } else {
            fontSize = cellSize / 2 - 12;
        }

        Font font = new Font("SansSerif", Font.BOLD, fontSize);
        g2d.setFont(font);

        g2d.setColor(new Color(50, 50, 50));

        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(valueStr);
        int textHeight = metrics.getHeight();
        int textX = x + (cellSize - textWidth) / 2;
        int textY = y + (cellSize - textHeight) / 2 + metrics.getAscent();

        g2d.drawString(valueStr, textX, textY);
    }
}

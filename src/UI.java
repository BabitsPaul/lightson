import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UI
    implements PropertyChangeListener
{
    private static final int INIT_SIZE = 5;

    private Board board;

    private JPanel boardUI;

    private JButton[] lights;

    private ImageIcon onIcon, offIcon;

    public UI()
    {
        board = new Board(INIT_SIZE);
        board.addPropertyChangeListener(Board.PROPERTY_CELL, this);

        createIcons();
    }

    private void createIcons()
    {
        BufferedImage onImage = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = onImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 128, 128);
        g.setColor(Color.yellow);
        g.drawOval(32, 32, 64, 64);
        for(double d = 0; d < Math.PI * 2; d += Math.PI / 10)
            g.drawLine(64, 64, (int) (Math.sin(d) * 64), (int) (Math.cos(d) * 64));
        g.dispose();
        onIcon = new ImageIcon(onImage);

        BufferedImage offImage = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
        g = offImage.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 128, 128);
        g.dispose();
        offIcon = new ImageIcon(offImage);
    }

    public void createUI() {
        SwingUtilities.invokeLater(()->{
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

            header.add(new JLabel("Boardsize: "));
            JSlider boardSize = new JSlider(1, 30, INIT_SIZE);
            boardSize.setPaintLabels(true);
            boardSize.addChangeListener(e->updateBoardSize(boardSize.getValue()));
            header.add(boardSize);

            JButton newGame = new JButton("New Game");
            header.add(newGame);
            newGame.addActionListener(e->newGame());

            JButton solve = new JButton("Solve");
            header.add(solve);
            solve.addActionListener(e->solve());

            boardUI = new JPanel();
            boardUI.setPreferredSize(new Dimension(400, 400));
            updateBoardSize(INIT_SIZE);

            JFrame frame = new JFrame("LightsOn");
            frame.setLayout(new BorderLayout());
            frame.add(header, BorderLayout.NORTH);
            frame.add(boardUI, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            board.randomize();
        });
    }

    private void updateBoardSize(int boardSize)
    {
        boardUI.removeAll();
        boardUI.setLayout(new GridLayout(boardSize, boardSize));
        lights = new JButton[boardSize * boardSize];

        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
            {
                JButton light = new JButton(offIcon);
                int x = i, y = j;
                light.addActionListener(e->board.trigger(x, y));
                boardUI.add(light);
                lights[x * boardSize + y] = light;
            }
    }

    private void newGame()
    {
        board.randomize();
    }

    private void solve()
    {

    }

    public void won()
    {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case "cell":
                int index = ((IndexedPropertyChangeEvent) evt).getIndex();
                boolean on = (Boolean) evt.getNewValue();

                SwingUtilities.invokeLater(()->{
                    lights[index].setIcon(on ? onIcon : offIcon);
                    lights[index].revalidate();
                    lights[index].repaint();
                });
                break;
            default:
                throw new RuntimeException("Invalid property-name");
        }
    }
}

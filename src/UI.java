import javax.swing.*;
import java.awt.*;

public class UI {
    private Board board;

    private JPanel boardUI;

    public void createUI() {
        SwingUtilities.invokeLater(()->{
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

            header.add(new JLabel("Boardsize: "));
            JSlider boardSize = new JSlider(1, 30, 5);
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


            JFrame frame = new JFrame("LightsOn");
            frame.setLayout(new BorderLayout());
            frame.add(header, BorderLayout.NORTH);
            frame.add(boardUI, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private void updateBoardSize(int boardSize)
    {

    }

    private void newGame()
    {

    }

    private void solve()
    {

    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{

    private JPanel main;
    private JTextField timerPresetsTextField;
    private JButton a55_5_Button;
    private JButton a45_15_Button;
    private JButton a25_5_Button;
    private JProgressBar TimerBar;
    private JTextField customTimerTextField;
    private JTextField workTimeTextField;
    private JTextField pauseTimeTextField;
    private JTextField WorkTimeText;
    private JTextField PauseTimeText;
    private JButton Start;
    private JButton Pause;
    private JButton Reset;
    private JLabel mainPanel;
    private JTextField TimerStatusText;

    public Main(){
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        Main mainPane = new Main();
        mainPane.setContentPane(mainPane.main);
        mainPane.setTitle("Pomodoro Timer");
        mainPane.setSize(1600, 960);
        mainPane.setVisible(true);
        mainPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

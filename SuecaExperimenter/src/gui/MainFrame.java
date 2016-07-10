package gui;

//import gameManager.ExperimentsManager;
//import gameManager.ExperimentsManagerGUI;
//import gameManager.GameManager;
import gameManager.ExperimentsManagerGUI;
import gameManager.GameManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements ExperimentsManagerGUI {

    private GameManager gameManager;
    private String[] algorithmNames = {"Random Algorithm", "Alpha-beta"};
    private JButton runButton = new JButton("Run");
    private JTextField upperTeamRoundsAhead = new JTextField("1");
    private JTextField upperTeamHands = new JTextField("10");
    private JComboBox upperTeamAlgorithms = new JComboBox(algorithmNames);

    private JTextField downTeamRoundsAhead = new JTextField("1");
    private JTextField downTeamHands = new JTextField("10");
    private JComboBox downTeamAlgorithms = new JComboBox(algorithmNames);

    private JTextField numGames = new JTextField("10");

    private JTextArea resultsArea = new JTextArea(15, 50);

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Sueca Experimenter");

        //UPPER TEAM
        JPanel upperTeamPanel = new JPanel();
        upperTeamPanel.setLayout(new BoxLayout(upperTeamPanel, BoxLayout.Y_AXIS));
        upperTeamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Upper Team"),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        JPanel upperPlayerAlgorithmPanel = new JPanel(new FlowLayout());
        upperPlayerAlgorithmPanel.add(new JLabel("Algorithm: "));
        upperPlayerAlgorithmPanel.add(upperTeamAlgorithms);
        upperTeamAlgorithms.setSelectedIndex(0);
        upperTeamPanel.add(upperPlayerAlgorithmPanel);

        JPanel upperPlayerDepthPanel = new JPanel(new FlowLayout());
        upperPlayerDepthPanel.add(new JLabel("Max. rounds ahead: "));
        upperTeamRoundsAhead.setColumns(4);
        upperPlayerDepthPanel.add(upperTeamRoundsAhead);
        upperTeamPanel.add(upperPlayerDepthPanel);

        JPanel upperPlayerHandsPanel = new JPanel(new FlowLayout());
        upperPlayerHandsPanel.add(new JLabel("Max. hands: "));
        upperTeamHands.setColumns(4);
        upperPlayerHandsPanel.add(upperTeamHands);
        upperTeamPanel.add(upperPlayerHandsPanel);

        //DOWN TEAM
        JPanel downTeamPanel = new JPanel();
        downTeamPanel.setLayout(new BoxLayout(downTeamPanel, BoxLayout.Y_AXIS));
        downTeamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Down Team"),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        JPanel downPlayerAlgorithmPanel = new JPanel(new FlowLayout());
        downPlayerAlgorithmPanel.add(new JLabel("Algorithm: "));
        downPlayerAlgorithmPanel.add(downTeamAlgorithms);
        downTeamAlgorithms.setSelectedIndex(0);
        downTeamPanel.add(downPlayerAlgorithmPanel);

        JPanel downPlayerDepthPanel = new JPanel(new FlowLayout());
        downPlayerDepthPanel.add(new JLabel("Max. rounds ahead: "));
        downTeamRoundsAhead.setColumns(4);
        downPlayerDepthPanel.add(downTeamRoundsAhead);
        downTeamPanel.add(downPlayerDepthPanel);

        JPanel downPlayerHandsPanel = new JPanel(new FlowLayout());
        downPlayerHandsPanel.add(new JLabel("Max. hands: "));
        downTeamHands.setColumns(4);
        downPlayerHandsPanel.add(downTeamHands);
        downTeamPanel.add(downPlayerHandsPanel);

        //GAMES
        JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        dealerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Experiments: "),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        JPanel numExperimentsPanel = new JPanel(new FlowLayout());
        numExperimentsPanel.add(new JLabel("Number of games: "));
        numGames.setColumns(4);
        numExperimentsPanel.add(numGames);
        dealerPanel.add(numExperimentsPanel);

        runButton.addActionListener(new RunButton_actionAdapter(this));
        dealerPanel.add(runButton);

        //CONFIGURATION PANEL
        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new BoxLayout(configurationPanel, BoxLayout.Y_AXIS));

        configurationPanel.add(upperTeamPanel);
        configurationPanel.add(downTeamPanel);
        configurationPanel.add(dealerPanel);

        //RESULTS PANEL
        JPanel resultsPanel = new JPanel();
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Results: "),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        resultsPanel.add(resultsArea);

        //GLOBAL STRUTCURE
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel globalPanel = new JPanel(new FlowLayout());
        globalPanel.add(configurationPanel);
        globalPanel.add(resultsPanel);
        contentPane.add(globalPanel);

        gameManager = new GameManager(this);

        pack();
    }

    public void jRunButton_actionPerformed(ActionEvent e) {
        runButton.setEnabled(false);
        gameManager.setUpperTeamConfiguration(Integer.parseInt(upperTeamRoundsAhead.getText()), Integer.parseInt(upperTeamHands.getText()), (upperTeamAlgorithms.getSelectedIndex() == 0) ? GameManager.RANDOM_ALGORITHM : GameManager.ALPHA_BETA);
        gameManager.setDownTeamConfiguration(Integer.parseInt(downTeamRoundsAhead.getText()), Integer.parseInt(downTeamHands.getText()), (downTeamAlgorithms.getSelectedIndex() == 0) ? GameManager.RANDOM_ALGORITHM : GameManager.ALPHA_BETA);
        gameManager.run(Integer.parseInt(numGames.getText()));
    }

    @Override
    public void showResults(String winner) {
        resultsArea.setText(winner);
        runButton.setEnabled(true);
    }
}

class RunButton_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    RunButton_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jRunButton_actionPerformed(e);
    }
}

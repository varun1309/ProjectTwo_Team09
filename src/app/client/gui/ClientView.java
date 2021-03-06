package app.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import app.client.controller.ClientSocketController;
import app.client.model.ClientCommonData;
import app.client.model.LogConstants;

/**
 * This class ClientView contains methods to initialize the UI components and
 * their listeners.
 * 
 * @author Varun Srivastav, Adhiraj Tikku
 * @version 1.0
 * @see 2018-02-23
 * 
 */
public class ClientView extends JFrame {

	private static final long serialVersionUID = 7062671298597794421L;
	private static final Color PINK = new Color(242, 208, 238);
	private static final Color LIGHTGREY = new Color(245, 245, 245);
	private static final Color BLUE = new Color(222, 235, 252);

	private static final Font FONT = new Font("Courier New", Font.BOLD, 17);

	private static final int DEFAULT_CLIENT_WIDTH = 800;
	private static final int DEFAULT_CLIENT_HEIGHT = 600;

	private static final String START_STOP_BUTTON_LABEL = "Start / Stop";
	private static final String HIGHEST_VALUE_LABEL = "Highest value:";
	private static final String LOWEST_VALUE_LABEL = "Lowest value:";
	private static final String AVERAGE_LABEL = "Average:";
	private static final String CHANNEL_LABEL = "Channels:";
	private static final String FREQUENCY_LABEL = "Frequency (Hz):";
	private static final String GRAPH_TITLE = "Graph";
	private static final String CONSOLE_TITLE = "Console";
	private static final String SETTINGS_TITLE = "Settings";

	private JButton buttonToggle;
	private Graph graphPanel;
	private JTextField frequencyNumber;
	private JComboBox<String> channelChoice;

	private ClientSocketController clientSocketController;

	/**
	 * Initializes and creates the Client window.
	 * 
	 * @param None
	 */
	public ClientView() {
		clientSocketController = new ClientSocketController();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Client");
		setMinimumSize(
				new Dimension(DEFAULT_CLIENT_WIDTH, DEFAULT_CLIENT_HEIGHT));
		setLayout(new BorderLayout(8, 8));
		setBackground(BLUE);
		createToolBar();
		createMainBody();
		createConsole();
		setVisible(true);
	}

	private void createToolBar() {
		JToolBar mainToolbar = new JToolBar();
		mainToolbar.setBackground(LIGHTGREY);
		mainToolbar.setBorder(new EmptyBorder(8, 8, 8, 8));
		mainToolbar.setFloatable(false);
		mainToolbar.add(Box.createHorizontalGlue());
		buttonToggle = new JButton(START_STOP_BUTTON_LABEL);
		buttonToggle.setFont(FONT);
		buttonToggle.setBorder(new LineBorder(Color.BLACK));
		mainToolbar.add(buttonToggle);

		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				invokeButtonListener(e);
			}
		});
		add(mainToolbar, BorderLayout.PAGE_START);
	}

	private void createMainBody() {
		JPanel mainBody = new JPanel();

		mainBody.setLayout(new BorderLayout());
		mainBody.setOpaque(false);
		mainBody.setBorder(new EmptyBorder(8, 8, 8, 8));

		JPanel panelBuffer = new JPanel(new GridLayout(1, 2, 8, 8));
		panelBuffer.setBackground(LIGHTGREY);
		panelBuffer.setBorder(BorderFactory.createLineBorder(Color.black));

		panelBuffer.add(generateGraphView(), BorderLayout.LINE_START);

		panelBuffer.add(generateSideView(), BorderLayout.LINE_END);

		mainBody.add(panelBuffer, BorderLayout.CENTER);
		add(mainBody, BorderLayout.CENTER);
	}

	private JPanel generateSideView() {
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(5, 2, 8, 8));
		sidePanel.setBorder(new TitledBorder(null, SETTINGS_TITLE,
				TitledBorder.LEADING, TitledBorder.TOP, FONT, null));
		sidePanel.setOpaque(false);

		JLabel highestValue = new JLabel(HIGHEST_VALUE_LABEL);
		highestValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		highestValue.setBackground(BLUE);
		highestValue.setHorizontalAlignment(SwingConstants.CENTER);
		highestValue.setOpaque(true);
		sidePanel.add(highestValue);

		JTextField highestNumber = new JTextField();
		highestNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		highestNumber.setHorizontalAlignment(SwingConstants.CENTER);
		highestNumber.setBackground(PINK);
		highestNumber.setEditable(false);
		sidePanel.add(highestNumber);
		ClientCommonData.getInstance().setMaxField(highestNumber);

		JLabel lowestValue = new JLabel(LOWEST_VALUE_LABEL);
		lowestValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lowestValue.setBackground(PINK);
		lowestValue.setHorizontalAlignment(SwingConstants.CENTER);
		lowestValue.setOpaque(true);
		sidePanel.add(lowestValue);

		JTextField lowestNumber = new JTextField();
		lowestNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lowestNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lowestNumber.setBackground(BLUE);
		lowestNumber.setEditable(false);
		sidePanel.add(lowestNumber);
		ClientCommonData.getInstance().setMinField(lowestNumber);

		JLabel averageLabel = new JLabel(AVERAGE_LABEL);
		averageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		averageLabel.setBackground(BLUE);
		averageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		averageLabel.setOpaque(true);
		sidePanel.add(averageLabel);

		JTextField averageNumber = new JTextField();
		averageNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		averageNumber.setHorizontalAlignment(SwingConstants.CENTER);
		averageNumber.setBackground(PINK);
		averageNumber.setEditable(false);
		sidePanel.add(averageNumber);
		ClientCommonData.getInstance().setAverageValue(averageNumber);

		JLabel channelLabel = new JLabel(CHANNEL_LABEL);
		channelLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		channelLabel.setBackground(PINK);
		channelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		channelLabel.setOpaque(true);
		sidePanel.add(channelLabel);

		String[] channelList = new String[] { "1", "2", "3", "4", "5" };
		channelChoice = new JComboBox<String>(channelList);
		channelChoice.setVisible(true);
		channelChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		channelChoice.setBackground(BLUE);
		ClientCommonData.getInstance()
				.setChannels(Integer.parseInt(channelList[0]));
		channelChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				invokeChannelListener(channelChoice, e);
			}
		});
		sidePanel.add(channelChoice);

		JLabel freqLabel = new JLabel(FREQUENCY_LABEL);
		freqLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		freqLabel.setBackground(BLUE);
		freqLabel.setBounds(560, 325, 85, 60);
		freqLabel.setHorizontalAlignment(SwingConstants.CENTER);
		freqLabel.setOpaque(true);
		sidePanel.add(freqLabel);

		frequencyNumber = new JTextField();
		frequencyNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		frequencyNumber.setHorizontalAlignment(SwingConstants.CENTER);
		frequencyNumber.setBackground(PINK);
		frequencyNumber.getDocument()
				.addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateFrequencyValue(frequencyNumber);
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						updateFrequencyValue(frequencyNumber);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						updateFrequencyValue(frequencyNumber);
					}

				});
		sidePanel.add(frequencyNumber);
		return sidePanel;
	}

	private JPanel generateGraphView() {
		graphPanel = new Graph();
		graphPanel.setLayout(new BorderLayout());
		graphPanel.setOpaque(false);
		graphPanel.setBorder(new TitledBorder(null, GRAPH_TITLE,
				TitledBorder.LEADING, TitledBorder.TOP, FONT, null));
		clientSocketController.startGraph(graphPanel);

		return graphPanel;
	}

	private void createConsole() {
		JTextArea consoleView = new JTextArea(6, 30);
		consoleView.setEditable(false);
		consoleView.setBackground(LIGHTGREY);
		consoleView.setFont(new Font("Courier New", Font.PLAIN, 15));
		ClientCommonData.getInstance().setConsoleArea(consoleView);

		JScrollPane consolePane = new JScrollPane(consoleView);
		consolePane.setBorder(new TitledBorder(null, CONSOLE_TITLE,
				TitledBorder.LEADING, TitledBorder.TOP, FONT, null));
		consolePane.setBackground(LIGHTGREY);
		consolePane.setBounds(10, 445, 758, 50);
		add(consolePane, BorderLayout.PAGE_END);
	}

	private void invokeButtonListener(ActionEvent e) {

		ClientCommonData.getInstance().logInfo(
				"Channels: " + ClientCommonData.getInstance().getChannels());

		if (ClientCommonData.getInstance().isStarted()) {
			ClientCommonData.getInstance().logInfo(LogConstants.STOPCLIENT);
			ClientCommonData.getInstance().setStarted(false);
			buttonToggle.setBackground(PINK);
			clientSocketController.stopServer();
			clientSocketController.stopGraph();
			ClientCommonData.getInstance().logInfo(LogConstants.STOPCLIENT);
		} else {
			if (isFrequencyValid()
					&& Integer.parseInt(frequencyNumber.getText()) > 0) {
				ClientCommonData.getInstance().setStarted(true);
				ClientCommonData.getInstance()
						.logInfo(LogConstants.STARTCLIENT);
				clientSocketController.startServer(
						ClientCommonData.getInstance().getChannels());
				clientSocketController.startGraph(graphPanel);
				buttonToggle.setBackground(BLUE);
			} else {
				ClientCommonData.getInstance()
						.logInfo(LogConstants.FREQUENCYERROR);
			}
		}
	}

	private void invokeChannelListener(JComboBox<String> channelChoice,
			ActionEvent e) {
		String channelValue = (String) channelChoice.getSelectedItem();
		ClientCommonData.getInstance()
				.setChannels(Integer.parseInt(channelValue));
	}

	private void updateFrequencyValue(JTextField freqNumber) {
		if (freqNumber.getText() != null && freqNumber.getText() != "") {
			if (!freqNumber.getText().matches(".*[a-z].*")
					&& !freqNumber.getText().equals("")) {
				int freq = Integer.parseInt(freqNumber.getText());
				ClientCommonData.getInstance().setFrequency(freq);
			} else {
				ClientCommonData.getInstance().setFrequency(1);
			}
		}
	}
	
	/**
	 * Check if the frequency entered by the user is valid.
	 * 
	 * @return True or False
	 */
	public boolean isFrequencyValid() {
		try {
			if(Integer.parseInt(frequencyNumber.getText()) > 0)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

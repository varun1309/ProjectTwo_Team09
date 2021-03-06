package app.server;

import java.net.ServerSocket;
import java.net.Socket;

import app.server.ServerNetworkThread;
import app.server.ServerView;

/**
 * Centralized server controller to start/stop the server and to send stream of
 * data to the client
 * 
 * @author Nelson Tran
 * @author Ganesh Kumar
 * @version 1.0
 */

public class ServerController {

	private ServerNetworkThread networkThread;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ServerOptions options;
	private static ServerView view;
	private boolean status;
	private int port;
	
       /**
	* Constructor to set port and ServerView object
	* 
	* @param port - port number
	* @param view - object for ServerView
	*/
	public ServerController(int port, ServerView view) {
		this.status = false;
		this.port = port;
		this.view = view;
	}
	
	/**
	 * Method to start the server and establish a socket connection
	 * 
	 * @param none
	 */
	public void startServer() {
		view.log("Info: The server has been started.");
		view.setStatus(true);
		this.networkThread = new ServerNetworkThread(this.serverSocket,
				this.clientSocket, this.port, this);
		this.networkThread.start();
	}
	
	/**
	 * Method to get the ServerView object  
	 * 
	 * @param none
	 */
	public static ServerView getView() {
		return view;
	}

		
       /**
	* Method to stop the server and close the connection
	* 
	* @param none
	*/
	public void stopServer() {
		view.log("Info: The server has been stopped.");
		view.setStatus(false);

		if (networkThread != null) {
			networkThread.interrupt();
			networkThread.closeConnection();
		}
	}

	/**
	 * Event handler for the Start/Stop button. If the server has not been
	 * started, request for the server options (lowest value, highest value, and
	 * frequency) and start the server. Otherwise, stop the server.
	 * 
	 * @param None
	 */
	public void toggleButtonClickHandler() {
		if (!this.status) {
			try {
				this.options = view.getOptions();
				if (this.options != null) {
					this.status = true;
					startServer();
				}
			} catch (NumberFormatException e) {
				ServerException.printErrorMessage("NumberFormatException");
				this.status = false;
			} catch (Exception e) {
				ServerException.printErrorMessage(e.getMessage());
				this.status = false;
			}
		} else {
			this.status = false;
			stopServer();
		}
	}
	
	/**
	 * 
	 * Returns the options for server.
	 * @return ServerOptions - Options for server
	 */
	public ServerOptions getOptions() {
		return this.options;
	}

}

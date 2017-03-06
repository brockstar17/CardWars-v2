package com.github.brockstar17;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.github.brockstar17.gui.SelectionFrame;
import com.github.brockstar17.util.BoardSpaces;
import com.github.brockstar17.util.ConnectionUtils;
import com.github.brockstar17.util.ImageUtils;

public class CardWars extends JFrame implements Runnable, MouseMotionListener, MouseListener
{

	private static final long serialVersionUID = 1L;

	private String ip = "localhost";
	private int port = 22222;

	private final int WIDTH = 500;
	private final int HEIGHT = 510;
	public final int BOARDWIDTH = 1000;
	public final int BOARDHEIGHT = 790;

	public static int cellW, cellH;
	public static int cardSpaceX, cardSpaceY;
	protected int mx, my;

	private Thread thread;

	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	private int boardX, boardY;
	private int errors;

	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;

	private ServerSocket serverSocket;

	private boolean isServer = true;

	protected static BufferedImage startText;
	protected static BufferedImage textSelect;
	public static BufferedImage marbleBoard;
	public static BufferedImage marbleSelection;

	protected static BufferedImage highlight;
	protected static BufferedImage oppHl, yourHl;

	public static BufferedImage brighty;
	public static BufferedImage gears;

	protected enum gameType
	{
		HOST, CLIENT, LOCAL
	}

	protected gameType gt;

	private boolean unableToCommunicateWithOpponent;
	public static boolean yourTurn = false;
	private boolean accepted;

	private StartPanel sp;
	private GamePanel gp;

	public CardWars()
	{

		super("Card Wars");

		ip = ConnectionUtils.getLocalIP();
		System.out.println(ip);

		loadImages();

		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);

		addMouseListener(this);
		addMouseMotionListener(this);

		Container c = getContentPane();
		sp = new StartPanel(this);
		c.add(sp);

		BoardSpaces.initCorners();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

	}

	public void run() {
		while(true)
		{
			tick();

			if(!accepted)
			{
				listenForServerRequest();
			}

		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		CardWars cardWars = new CardWars();
	}

	private void loadImages() {
		try
		{
			startText = ImageIO.read(getClass().getResourceAsStream("/StartText.png"));
			textSelect = ImageIO.read(getClass().getResourceAsStream("/TextSelect.png"));
			marbleBoard = ImageIO.read(getClass().getResourceAsStream("/marbleBoard.png"));
			marbleSelection = ImageIO.read(getClass().getResourceAsStream("/marbleSelection.png"));

			highlight = ImageIO.read(getClass().getResourceAsStream("/highlight.png"));
			oppHl = ImageIO.read(getClass().getResourceAsStream("/turnHl.png"));
			yourHl = ImageIO.read(getClass().getResourceAsStream("/yourHl.png"));

			brighty = ImageIO.read(getClass().getResourceAsStream("/cards/brighty.png"));
			gears = ImageIO.read(getClass().getResourceAsStream("/cards/gears.png"));

			int cw = brighty.getWidth(), ch = brighty.getHeight();

			highlight = ImageUtils.scale(highlight, ImageUtils.calcWidth(highlight.getHeight(), screen.getHeight() * .2165, highlight.getWidth()), (int) (screen.getHeight() * .216));
			oppHl = ImageUtils.scale(oppHl, highlight.getWidth(), highlight.getHeight());
			yourHl = ImageUtils.scale(yourHl, highlight.getWidth(), highlight.getHeight());

			cellW = highlight.getWidth();
			cellH = highlight.getHeight();

			cardSpaceY = (int) (cellH * .01 / 2);
			cardSpaceX = (int) (cellW * .15);

			brighty = ImageUtils.scale(brighty, ImageUtils.calcWidth(ch, cellH - (cellH * .01), cw), (int) (cellH - (cellH * .01)));
			gears = ImageUtils.scale(gears, brighty.getWidth(), brighty.getHeight());

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		handleClick();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

		repaint();
	}

	protected void startGame() {

		gp = new GamePanel(this);

		switch(gt)
		{
		case HOST:
			// start the host game

			port = Integer.parseInt(JOptionPane.showInputDialog("Enter the port number"));
			while(port < 1 || port > 65535)
			{

				port = Integer.parseInt(JOptionPane.showInputDialog("The port you entered was invalid, please input another port"));
			}

			initializeServer();
			this.remove(sp);
			this.setTitle("Card Wars Player 1");

			yourTurn = true;

			setSize(marbleBoard.getWidth() + 5, marbleBoard.getHeight() + 29);
			this.setLocationRelativeTo(null);
			this.add(gp);
			repaint();
			break;

		case CLIENT:
			// connect to a host
			ip = JOptionPane.showInputDialog("Enter the IP address:");

			port = Integer.parseInt(JOptionPane.showInputDialog("Enter the port number"));
			while(port < 1 || port > 65535)
			{

				port = Integer.parseInt(JOptionPane.showInputDialog("The port you entered was invalid, please input another port"));
			}

			if(connect())
			{
				this.remove(sp);
				this.setTitle("Card Wars Player 2");

				yourTurn = false;

				setSize(marbleBoard.getWidth() + 5, marbleBoard.getHeight() + 40);
				this.setLocationRelativeTo(null);
				this.add(gp);
				repaint();
			}
			break;

		default:
			// play againts ai

			break;

		}
	}

	private void initializeServer() {
		try
		{
			System.out.println("Starting up the server");
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private boolean connect() {
		try
		{
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(sp, "Unable to connect to the address: " + ip + ":" + port);
			return false;
		}
		JOptionPane.showMessageDialog(sp, "Successfully connected to the server with address: " + ip + ":" + port);

		return true;
	}

	private void tick() {
		if(errors >= 10)
			unableToCommunicateWithOpponent = true;

		if(!yourTurn && !unableToCommunicateWithOpponent)
		{

		}
	}

	private void handleClick() {
		int cell = BoardSpaces.getCell(mx, my);

		if(cell == -1)
			return;

		if(CardWars.yourTurn)
		{
			if(cell == 15)
			{
				this.setEnabled(false);
				new SelectionFrame(this);
			}
		}
	}

	private void listenForServerRequest() {
		Socket socket = null;
		try
		{
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

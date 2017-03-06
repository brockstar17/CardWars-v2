package com.github.brockstar17.gui;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.github.brockstar17.CardWars;

@SuppressWarnings("serial")
public class SelectionFrame extends JDialog implements MouseListener, WindowListener{

	private CardWars cw;
	
	public SelectionFrame(CardWars cw)
	{
		this.cw = cw;
		
		setTitle("Select A Card");
		
		Container c = getContentPane();
		c.add(new SelectionPanel()); 

		c.addMouseListener(this);
		
		addWindowListener(this);

		setSize((int) (cw.getWidth()*.66 + cw.getWidth()*.009), (int) (cw.getHeight()*.66 + cw.getHeight()*.017));
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
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
		this.dispose();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.cw.setVisible(true);
		this.cw.setEnabled(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
		
	}

}

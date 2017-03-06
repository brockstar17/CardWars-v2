package com.github.brockstar17;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.github.brockstar17.CardWars.gameType;

@SuppressWarnings("serial")
public class StartPanel extends JPanel implements MouseMotionListener, MouseListener
{
	
	private int my;
	
	
	private CardWars cw;

	public StartPanel(CardWars cw)
	{
		this.cw = cw;
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(cw.startText, 0, 0, null);
		
		if(my < cw.getHeight()/3)
		{
			g.drawImage(cw.textSelect, 90, (int) (cw.getHeight() * 0), null);
		}
		else if(my > cw.getHeight()/3 && my < cw.getHeight() * 2 / 3)
		{
			g.drawImage(cw.textSelect, 90, (int) (cw.getHeight() * .25), null);
		}
		else
		{
			g.drawImage(cw.textSelect, 90, (int) (cw.getHeight()*.5), null);
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		my = e.getY();
		
		repaint();
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
		if(my < cw.getHeight()/3)
		{
			cw.gt = gameType.HOST;
			
		}
		else if(my > cw.getHeight()/3 && my < cw.getHeight() * 3 / 7)
		{
			cw.gt = gameType.CLIENT;
		}
		else
		{
			cw.gt = gameType.LOCAL;
		}
		
		cw.startGame();
	}
}

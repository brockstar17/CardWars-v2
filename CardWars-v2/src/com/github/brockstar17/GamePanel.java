package com.github.brockstar17;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.brockstar17.util.BoardSpaces;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private CardWars cw;
	public static PlayingCard[] pCards = new PlayingCard[20];
	public static PlayingCard[] oCards = new PlayingCard[20];
	
	public static boolean cellHighCard;
	
	
	public static int clicked;
	
	public GamePanel(CardWars cw)
	{
		this.cw = cw;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(CardWars.marbleBoard, 0, 0, null);
		
		
		
		if(CardWars.yourTurn)
		{

			g.drawImage(CardWars.yourHl, BoardSpaces.getCellX(15), BoardSpaces.getCellY(15), null);
		}
		else
		{
			g.drawImage(CardWars.oppHl, BoardSpaces.getCellX(4), BoardSpaces.getCellY(4), null);
		}
		
		drawDeck(g);
		
		highlight(cw.mx, cw.my, g);

	}
	
	
	
	private void highlight(int x, int y, Graphics g){
		int cell = BoardSpaces.getCell(x, y);
		/*
		if(CardWars.yourTurn)
		{
			if(cellHighCard && cell != -1)
			{
				BufferedImage[] suit = getSuitArray(pCards[cell].getSuit());

				if(suit != null)
				{
					g.drawImage(suit[pCards[cell].getValue() - 1], BoardSpaces.getCellX(cell) + cardSpaceX, BoardSpaces.getCellY(cell) + cardSpaceY, null);

				}

			}
			
			
		}*/
		
		if(cell != -1 && cell != 4 && cell != 15)
		{
			g.drawImage(CardWars.highlight, BoardSpaces.getCellX(cell), BoardSpaces.getCellY(cell), null);
			
		}
	}

	private void drawDeck(Graphics g) {
		g.drawImage(CardWars.gears, BoardSpaces.getCellX(15) + CardWars.cardSpaceX, BoardSpaces.getCellY(15) + CardWars.cardSpaceY, null);
		g.drawImage(CardWars.brighty, BoardSpaces.getCellX(4) + CardWars.cardSpaceX, BoardSpaces.getCellY(4) + CardWars.cardSpaceY, null);
	}
	
	
	
}

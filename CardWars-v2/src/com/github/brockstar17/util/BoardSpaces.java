package com.github.brockstar17.util;

import com.github.brockstar17.CardWars;
import com.github.brockstar17.GamePanel;
import com.github.brockstar17.PlayingCard;

public class BoardSpaces
{

	private static int[] cornX = new int[20];
	private static int[] cornY = new int[20];
	
	private static double screenX = CardWars.screen.getWidth();
	private static double screenY = CardWars.screen.getHeight();
	
	

	public static void initCorners() {
		for(int i = 0; i < cornX.length; i++)
		{
			if(i <= 4)
			{
				cornX[i] = (int) (screenX * .01) + (int) (screenX * i * .18485);
				cornY[i] = (int) (screenY * .012);
			}
			else if(i <= 9)
			{
				cornX[i] = (int) (screenX * .01) + (int) (screenX * (i - 5) * .18485);
				cornY[i] = (int) (screenY * .24);
			}
			else if(i <= 14)
			{
				cornX[i] = (int) (screenX * .01) + (int) (screenX * (i - 10) * .18485);
				cornY[i] = (int) (screenY * .467);
			}
			else
			{
				cornX[i] = (int) (screenX * .01) + (int) (screenX * (i - 15) * .18485);
				cornY[i] = (int) (screenY * .695);
			}
		}
	}

	public static int getCellX(int i) {
		return cornX[i];
	}

	public static int getCellY(int i) {
		return cornY[i];
	}

	
	public static int getCell(int x, int y) {
		if(CardWars.yourTurn)
		{
			for(int i = 0; i < cornX.length; i++)
			{
				if(x > cornX[i] && x < cornX[i] + CardWars.cellW && y > cornY[i] && y < cornY[i] + CardWars.cellH)
				{
					PlayingCard card = GamePanel.pCards[i];
					if(card != null)
					{
						setCellHighCard(true);
						return i;
					}
					else
					{
						setCellHighCard(false);
						return i;
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < cornX.length; i++)
			{
				if(x > cornX[i] && x < cornX[i] + CardWars.cellW && y > cornY[i] && y < cornY[i] + CardWars.cellH)
				{
					PlayingCard card = GamePanel.oCards[i];
					if(card != null)
					{
						setCellHighCard(true);
						return i;
					}
					else
					{
						setCellHighCard(false);
						return i;
					}
				}
			}
		}
		return -1;
	}
	

	public static boolean hasCard(int cell) {
		if(GamePanel.pCards[cell] == null && GamePanel.oCards[cell] == null)
		{
			return false;
		}
		return true;
	}

	private static void setCellHighCard(boolean hasCard) {
		//GamePanel.cellHighCard = hasCard;
	}

	public static boolean hasOtherCard(int cell) {
		if(cell != 1 && cell != 2 && cell != 3 && cell != 9 && cell != 14)
		{
			if(GamePanel.oCards[cell] == null)
			{
				return false;
			}
			return true;
		}
		else
		{
			if(GamePanel.pCards[cell] == null)
			{
				return false;
			}
			return true;
		}
	}
}

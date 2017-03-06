package com.github.brockstar17;

import java.io.Serializable;

/*
 * Defines the object of the player's card
 */

@SuppressWarnings("serial")
public class PlayingCard implements Serializable
{
	private int x; // x pos of top left corner
	private int y; // y pos of top left corner
	private int w; // x pos of right edge of card
	private int l; // y pos of the bottom of card
	private String suit; // the suit of the card
	private int value; // the value of the card
	private int count;
	private boolean hasAttacked;

	public PlayingCard(int px, int py, int sx, int sy, String suit, int value)
	{
		this.x = px;
		this.y = py;
		this.w = sx;
		this.l = sy;
		this.suit = suit.toLowerCase();
		this.value = value;
		this.count = initCount(this.value);
		this.hasAttacked = false;
	}

	// returns the x pos of this card
	public int getX() {
		return x;
	}

	// returns the y loc of this card
	public int getY() {
		return y;
	}

	// returns the x pos of the right edge of card
	public int getW() {
		return w;
	}

	// returns the y pos of the bottom edge of card
	public int getL() {
		return l;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getSuit() {
		return this.suit;
	}

	public int getValue() {
		return this.value;
	}

	public String getName() {
		String name;
		switch(this.value)
		{
		case 1:
			name = "ace of ";
			break;
		case 2:
			name = "two of ";
			break;
		case 3:
			name = "three of ";
			break;
		case 4:
			name = "four of ";
			break;
		case 5:
			name = "five of ";
			break;
		case 6:
			name = "six of ";
			break;
		case 7:
			name = "seven of ";
			break;
		case 8:
			name = "eight of ";
			break;
		case 9:
			name = "nine of ";
			break;
		case 10:
			name = "ten of ";
			break;
		case 11:
			name = "jack of ";
			break;
		case 12:
			name = "queen of ";
			break;
		default:
			name = "king of ";
			break;
		}

		return name + suit;
	}

	private int initCount(int v) {
		if(v == 1)
		{
			return 1;
		}
		else if(v == 11 || v == 12 || v == 13)
		{
			return 2;
		}
		else
		{
			return 3;
		}
	}

	public void decrMoveCount() {

		this.count--;
	}

	public int getMoveCount() {
		return this.count;
	}

	public boolean getAttacked() {
		return this.hasAttacked;
	}

	public void setAttacked() {
		this.hasAttacked = true;
	}
}

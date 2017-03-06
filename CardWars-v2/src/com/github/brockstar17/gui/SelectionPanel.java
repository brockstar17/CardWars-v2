package com.github.brockstar17.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.brockstar17.CardWars;


@SuppressWarnings("serial")
public class SelectionPanel extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(CardWars.marbleSelection, 0, 0, null);
	}
}

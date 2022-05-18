package rpsclassroom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

//runs and interprets the UI

@SuppressWarnings("deprecation")
public class Player implements Observer, KeyListener {
	protected String name;
	protected Room currentRoom;
	protected Item heldItem;
	protected String validCommands = "The valid commands are [go/enter/exit <room number OR direction (up, right, down, left)>], [get], [drop <item name>], [settings], and [QUIT].";
	protected int level;
	protected int hp;
	protected double speed = 1;
	protected JTextArea textOut;
	protected JPanel controls;
	protected JFrame window = new JFrame("RPSClassroom");
	
	public void play(String name, Room start, int mode) {
		if (mode == 1) {
			this.name = name;
			currentRoom = start;		
			// beginning the creation of the window
			try {
				window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/" + currentRoom.id() + ".jpg")))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			window.setLayout(new GridBagLayout());	
			controls = new JPanel();
			controls.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			// generating the buttons
			ImageIcon upButtonImage = new ImageIcon("buttons/UpButton.png");
			ImageIcon leftButtonImage = new ImageIcon("buttons/LeftButton.png");
			ImageIcon rightButtonImage = new ImageIcon("buttons/RightButton.png");
			ImageIcon downButtonImage = new ImageIcon("buttons/DownButton.png");
			JButton upButton = new JButton(upButtonImage);
			JButton leftButton = new JButton(leftButtonImage);
			JButton rightButton = new JButton(rightButtonImage);
			JButton downButton = new JButton(downButtonImage);
			transButton(upButton);
			transButton(rightButton);
			transButton(downButton);
			transButton(leftButton);
			
			// adding the buttons
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 1;
			c.gridy = 0;
			controls.add(upButton, c);
			c.gridx = 0;
			c.gridy = 1;
			controls.add(leftButton, c);
			c.gridx = 2;
			c.gridy = 1;
			controls.add(rightButton, c);
			c.gridx = 1;
			c.gridy = 1;
			controls.add(downButton, c);
			controls.setPreferredSize(new Dimension(170, 106));
			
			// adding the rest of the buttons
			JButton aButton = new JButton(new ImageIcon("buttons/AButton.png"));
			JButton bButton = new JButton(new ImageIcon("buttons/BButton.png"));
			
			aButton.setPreferredSize(new Dimension(30, 40));
			bButton.setPreferredSize(new Dimension(30, 40));
			
			aButton.addActionListener(e -> pickUp());
			bButton.addActionListener(e -> drop());
			
			transButton(aButton);
			transButton(bButton);
			
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			controls.add(aButton, c);
			c.gridx = 2;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			controls.add(bButton, c);
			
			// adding control window
			c.insets = new Insets(15, 15, 15, 15);
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			controls.setOpaque(false);
			window.add(controls, c);
			
			// creating the bottom right display
			textOut = new JTextArea("");
			textOut.setLineWrap(true);
			textOut.setWrapStyleWord(true);
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			textOut.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			textOut.setEditable(false);
			textOut.setPreferredSize(new Dimension(350, 400));
			c.gridx = 1;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			window.add(textOut, c);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0;
			c.weighty = 3.5;
			c.anchor = GridBagConstraints.CENTER;
			window.add(javax.swing.Box.createGlue(), c);
			c.weighty = 0;
			
			// giving buttons actions
			upButton.addActionListener(e -> move(1));
			leftButton.addActionListener(e -> move(2));
			rightButton.addActionListener(e -> move(3));
			downButton.addActionListener(e -> move(4));
			
			// finalizing window creation
			window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			window.setUndecorated(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setLocation(0, 0);
			window.setFocusable(true);
			window.addKeyListener(this);
			
			// making all objects not focusable so that keyboard works after every click
			textOut.setFocusable(false);
			
			// getting the player going
			start.checkIn(this);
			window.setVisible(true);
			read();	
		} else {
			this.name = name;
			currentRoom = start;		
			// beginning the creation of the window
			try {
				window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/" + currentRoom.id() + ".jpg")))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			window.setLayout(new GridBagLayout());	
			controls = new JPanel();
			controls.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			// generating the buttons
			ImageIcon upButtonImage = new ImageIcon("buttons/UpButton.png");
			ImageIcon leftButtonImage = new ImageIcon("buttons/LeftButton.png");
			ImageIcon rightButtonImage = new ImageIcon("buttons/RightButton.png");
			ImageIcon downButtonImage = new ImageIcon("buttons/DownButton.png");
			JButton upButton = new JButton(upButtonImage);
			JButton leftButton = new JButton(leftButtonImage);
			JButton rightButton = new JButton(rightButtonImage);
			JButton downButton = new JButton(downButtonImage);
			transButton(upButton);
			transButton(rightButton);
			transButton(downButton);
			transButton(leftButton);
			
			// adding the buttons
			c.insets = new Insets(1, 1, 1, 1);
			c.ipadx = 1;
			c.ipady = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 1;
			c.gridy = 0;
			controls.add(upButton, c);
			c.gridx = 0;
			c.gridy = 1;
			controls.add(leftButton, c);
			c.gridx = 2;
			c.gridy = 1;
			controls.add(rightButton, c);
			c.gridx = 1;
			c.gridy = 1;
			controls.add(downButton, c);
			controls.setPreferredSize(new Dimension(170, 106));
			
			// adding the rest of the buttons
			JButton aButton = new JButton(new ImageIcon("buttons/AButton.png"));
			JButton bButton = new JButton(new ImageIcon("buttons/BButton.png"));
			
			aButton.setPreferredSize(new Dimension(30, 40));
			bButton.setPreferredSize(new Dimension(30, 40));
			
			aButton.addActionListener(e -> pickUp());
			bButton.addActionListener(e -> drop());
			
			transButton(aButton);
			transButton(bButton);
			
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			controls.add(aButton, c);
			c.gridx = 2;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			controls.add(bButton, c);
			
			// adding control window
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			controls.setOpaque(false);
			window.add(controls, c);
			
			// creating the bottom right display
			textOut = new JTextArea("");
			textOut.setLineWrap(true);
			textOut.setWrapStyleWord(true);
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			textOut.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			textOut.setEditable(false);
			textOut.setPreferredSize(new Dimension(350, 400));
			c.gridx = 1;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			window.add(textOut, c);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0;
			c.weighty = 3.5;
			c.anchor = GridBagConstraints.CENTER;
			window.add(javax.swing.Box.createGlue(), c);
			c.weighty = 0;
			
			// giving buttons actions
			upButton.addActionListener(e -> move(1));
			leftButton.addActionListener(e -> move(2));
			rightButton.addActionListener(e -> move(3));
			downButton.addActionListener(e -> move(4));
			
			// finalizing window creation
			window.setMinimumSize(new Dimension(760, 1000));
			window.setMaximumSize(new Dimension(760, 1080));
			window.setPreferredSize(new Dimension(760, 1080));
			window.setSize(new Dimension(760, 1080));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setFocusable(true);
			window.addKeyListener(this);
			
			// making all objects not focusable so that keyboard works after every click
			textOut.setFocusable(false);
			
			// getting the player going
			start.checkIn(this);
			window.setVisible(true);
			read();	
		}
	}
	
	public void redraw() {
		try {
			window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/" + currentRoom.id() + ".jpg")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		window.remove(controls);
		window.remove(textOut);
		window.add(controls);
		window.add(textOut);
	}

	public void giveError() {
		textOut.setText("You seem to have spun in a circle. Are you sure you meant to do that?");
		readLater(1.5);
	}
	
	public void pickUp() {
		if (currentRoom.item() != null) {
			if (heldItem == null) {
				heldItem = currentRoom.item();
				textOut.setText("You picked up " + currentRoom.item().name() + "!");
				currentRoom.clear();
				readLater(.8);
			} else {
				textOut.setText("Huh? You're already holding something, Silly!");
				readLater(.8);
			}
		} else {
			textOut.setText("Huh? Were you trying to grab something? There's nothing there, Silly!");
			readLater(.8);
		}
	}
	
	public void drop() {	
		if (heldItem != null) {	
			if (currentRoom.item() == null) {
				currentRoom.setItem(heldItem);
				textOut.setText(heldItem.name() + " dropped!");
				heldItem = null;
				readLater(.8);
			} else {
				textOut.setText("Huh? You can't put that here, Silly, there's something already on the floor!");
				readLater(1.1);
			}			
		} else {
			if (currentRoom.getOtherPlayers(this).size() == 0) {
				textOut.setText("Huh? You don't have anything to drop AND there's no one to fight, Silly!");
				readLater(.8);
			} else {
				setEngaged(true);
				for (Player p : currentRoom.getOtherPlayers(this)) {
					p.setEngaged(true);
				}
				fight(currentRoom.getOtherPlayers(this), textOut);
				chill(3);
				for (Player p : currentRoom.getOtherPlayers(this)) {
					p.setEngaged(false);
				}
				setEngaged(false);
			}
		}
	}		

	public void setEngaged(boolean b) {
		if (b) {
			textOut.setText("Fight in progress:\n\n");
			textOut.repaint();
			textOut.update(textOut.getGraphics());
		} else {
			read();
		}
	}
	
	public Player fight(ArrayList<Player> players, JTextArea textOut) {
		if (textOut.getLineCount() > 19) {
			textOut.setText("Continuing...\n\n");
		}
		int i = players.size();
		switch (i) {
			case 1: {
				int rps1 = this.getInput();
				String choice = "nothing?";
				switch (rps1) {
				case 1: choice = "Rock"; break;
				case 2: choice = "Paper"; break;
				case 3: choice = "Scissors"; break;
				}
				textOut.append(this + " chose " + choice + "...\n");
				textOut.repaint();
				textOut.update(textOut.getGraphics());
				int rps2 = players.get(0).getInput();
				choice = "nothing?";
				switch (rps2) {
				case 1: choice = "Rock"; break;
				case 2: choice = "Paper"; break;
				case 3: choice = "Scissors"; break;
				}
				textOut.append(players.get(0) + " chose " + choice + "...\n");
				textOut.repaint();
				textOut.update(textOut.getGraphics());
				chill(1);
				if (rps1 != rps2) { 
					switch (rps1 + rps2) {
					case 3:
					case 5: if (rps1 > rps2) {
						textOut.append(this + " beat " + players.get(0) + "!\n\n");
						textOut.repaint();
						textOut.update(textOut.getGraphics());
						return this;
					} else {
						textOut.append(players.get(0) + " beat " + this + "!\n\n");
						textOut.repaint();
						textOut.update(textOut.getGraphics());
						return players.get(0);
					}
					case 4: if (rps1 < rps2) {
						textOut.append(this + " beat " + players.get(0) + "!\n\n");
						textOut.repaint();
						textOut.update(textOut.getGraphics());
						return this;
					} else {
						textOut.append(players.get(0) + " beat " + this + "!\n\n");
						textOut.repaint();
						textOut.update(textOut.getGraphics());
						return players.get(0);
					}
					}
				} else {
					textOut.append(this + " and " + players.get(0) + " tied! Go again!\n\n");
					textOut.repaint();
					textOut.update(textOut.getGraphics());
					return fight(players, textOut);
				} break;
			} case 2: {
				ArrayList<Player> r1 = new ArrayList<Player>();
				r1.add(players.get(1));
				ArrayList<Player> r2 = new ArrayList<Player>();
				r2.add(players.get(0));
				return fight(r1, textOut).fight(r2, textOut);
			} case 3: {
				ArrayList<Player> r1 = new ArrayList<Player>();
				r1.add(players.get(1));
				ArrayList<Player> r2 = new ArrayList<Player>();
				r2.add(players.get(2));
				Player w1 = this.fight(r2, textOut);
				ArrayList<Player> w2 = new ArrayList<Player>();
				w2.add(players.get(0).fight(r1, textOut));
				return w1.fight(w2, textOut);
			} 
		}
		return null;
	}
	
	public int getInput() {
		Object[] options = { "Rock", "Paper", "Scissors" };
		int i = JOptionPane.showOptionDialog(null, "Make your choice!", "Rock, Paper, Scissors!", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (i != 0 && i != 1 && i != 2 && i != 3) {
			chill(.1);
			i = getInput() - 1;
		}
		return (int) (i + 1);
	}

	public boolean move(int dir) {
		Room nextRoom = currentRoom.exits().get(dir);
		if (nextRoom != null) {
			if (!nextRoom.atCap()) {
				if (nextRoom.itemRequirement() == null) {
					currentRoom.checkOut(this);
					nextRoom.checkIn(this);
					currentRoom = nextRoom;
					read();
					return true;
				} else {
					if (heldItem != null && heldItem.isKey() && hasKey(nextRoom)) {
						nextRoom.setItem(heldItem);
						textOut.setText(nextRoom.name() + " unlocked!");
						heldItem = null;
						currentRoom.checkOut(this);
						nextRoom.checkIn(this);
						currentRoom = nextRoom;
						readLater(.8);
						return true;
					} else {
						textOut.setText("You don't have the necessary key to enter this room! You need " + nextRoom.itemRequirement().name() + ".");
						readLater(1);
						return false;
					}
				}
			} else {
				textOut.setText("There's too many people in that room for you to enter!");
				readLater(1);
				return false;
			}
		} else {
			giveError();
			return false;
		}
	}
	
	
	
	public boolean hasKey(Room room) {
		if (heldItem == room.itemRequirement()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void read() {
		redraw();
		textOut.setText("" + currentRoom.description() + "\n");
		textOut.append("\nThere are exits to:\n");
		for (int i = 1; i < currentRoom.exits().size(); i++) {
			String dir = "";
			switch (i) {
			case 1:
				dir = "Up: ";
				break;
			case 2:
				dir = "Left: ";
				break;
			case 3:
				dir = "Right: ";
				break;
			case 4:
				dir = "Down: ";
				break;
			}
			try {
				textOut.append(dir + currentRoom.exits().get(i).name() + "\n");
			} catch (Exception e) {
				
			}
		}
		ArrayList<Player> ps = currentRoom.getOtherPlayers(this);
		if (ps.size() != 0) {
			textOut.append("\nOther people in the room with you:\n");
			for (int i = 0; i < ps.size(); i++) {
				textOut.append(ps.get(i).name + "\n");
			}
		} else {
			textOut.append("\nYou don't see anyone else in here.\n");
		}
		if (currentRoom.item() == null) {
			textOut.append("\nThere aren't any items in here.\n");
		} else {
			textOut.append("\nYou see " + currentRoom.item().name() + " in here!\n");
		}
		if (heldItem != null) {
			textOut.append("\nYou are holding: " + heldItem.name() + "\n" + heldItem.description() + "\n");
		} else {
			textOut.append("\nYou aren't holding anything.\n");
		}
		textOut.append("\nWhat would you like to do?\n");
	}
	
	public void readLater(double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chill(time);
				read();
			}
		});
	}	
	
	public void appendLater(JTextArea textOut, double time, String s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chill(time);
				textOut.append(s);
			}
		});
	}
	
	private void quit() {
		currentRoom.shutdown();
	}
	
	public void transButton(JButton egg) {
		egg.setOpaque(false);
		egg.setContentAreaFilled(false);
		egg.setBorderPainted(false);
		egg.setFocusable(false);
	}

	public void update(Observable o, Object arg) {
		if (arg != null) {
		} else {
			readLater(0);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_A: {move(2); break;}
			case KeyEvent.VK_S: {move(4); break;}
			case KeyEvent.VK_D: {move(3); break;}
			case KeyEvent.VK_W: {move(1); break;}
			case KeyEvent.VK_Q: {pickUp(); break;}
			case KeyEvent.VK_E: {drop(); break;}
			case KeyEvent.VK_ESCAPE: {
				Object[] options = { "Yes, quit.", "No, cancel." };
				if (JOptionPane.showOptionDialog(null, "Are you sure you would like to quit?", "Quit?", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]) == 0) 
				{
					quit();
					window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				}
				break;
			}
		}
	}
	
	private String name() {
		return name;
	}
	
	public void chill(double t) {
		int s = (int)(t * 1000 * speed);
		try {
			Thread.sleep(s);
		} catch(Exception e) {					
		}
	}
	
	public String toString() {
		return this.name;
	}
	
}
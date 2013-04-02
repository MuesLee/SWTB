package de.fh.swt.schiffeversenken.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.fh.swt.schiffeversenken.controller.Spielleiter;

public class MainFrame extends JFrame
{

	private JMenuBar jMenuBar = new JMenuBar();
	private JPanel jpanel = new JPanel();
	private JLabel jLabel = new JLabel();
	private Spielleiter spielleiter;

	public MainFrame(Spielleiter spielleiter)
	{
		super("Cellular Automaton");
		this.spielleiter = spielleiter;
		configureFrame();
		//configurePanel();
		configureLabel();
		configureMenu();
		configurePane();
		//add(jpanel, BorderLayout.CENTER);
		pack();
	}

	private void configurePane()
	{
		SchiffComponent schiffComp = new SchiffComponent(spielleiter, new Dimension(400, 400));
		getContentPane().add(schiffComp);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jMenuBar, BorderLayout.PAGE_START);
		getContentPane().add(jLabel, BorderLayout.LINE_END);
		schiffComp.setLocation(10, 50);
	}

	//	private void configurePanel()
	//	{
	//		jpanel.setVisible(true);
	//		Dimension size = new Dimension(50, 50);
	//		jpanel.setSize(size);
	//		jpanel.setPreferredSize(size);
	//		jpanel.setMaximumSize(size);
	//		jpanel.setBackground(Color.BLACK);
	//		jpanel.add(new SchiffComponent(spielleiter.getSeekarte(), new Dimension(500, 500)));
	//	}

	private void configureLabel()
	{
		Dimension size = new Dimension(50, 400);
		jLabel.setBackground(Color.gray);
		jLabel.setSize(size);
		jLabel.setPreferredSize(size);
		jLabel.setMaximumSize(size);
		JTextArea area = new JTextArea();
		area.setText(spielleiter.getAktiverSpieler().getSchussliste().toString());
		area.setVisible(true);
		jLabel.setVisible(true);
	}

	private void configureFrame()
	{
		Dimension size = new Dimension(600, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Schiffe versenken");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setResizable(false);
		setVisible(true);
	}

	private void configureMenu()
	{
		final MainFrame frame = this;
		JMenu jMenu = new JMenu("Datei");
		JMenuItem menuItemStart = new JMenuItem("Start");
		menuItemStart.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(frame, "Geht noch nicht");
			}
		});
		JMenuItem menuItemBeenden = new JMenuItem("Beenden");
		menuItemBeenden.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		jMenu.add(menuItemStart);
		jMenu.add(menuItemBeenden);
		jMenuBar.add(jMenu);
		jMenuBar.setVisible(true);
	}

}

package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import de.fh.swt.schiffeversenken.controller.AudioController;

public class OptionFrame extends JFrame
{

	private JCheckBox checkBoxMusicSwitch;
	private JButton buttonConfirm;
	private JButton buttonCancel;
	private MainFrame mainframe;

	public OptionFrame(MainFrame mainframe)
	{
		this.mainframe = mainframe;
		configure();
	}

	private void configure()
	{
		configureFrame();
		configureCheckBoxes();
		configureButtons();

		add(checkBoxMusicSwitch);
		add(buttonConfirm);
		add(buttonCancel);
	}

	private void configureButtons()
	{
		buttonCancel = new JButton(Messages.getString("OptionFrame.ButtonCancel")); //$NON-NLS-1$
		buttonConfirm = new JButton(Messages.getString("OptionFrame.ButtonConfirm")); //$NON-NLS-1$
		buttonCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		buttonConfirm.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				confirmChanges();
				setVisible(false);
			}
		});
	}

	private void confirmChanges()
	{
		AudioController audioController = mainframe.getGameManager().getAudioController();
		if (checkBoxMusicSwitch.isSelected())
		{
			if (!audioController.sequencerIsRunning())
			{
				audioController.startBackgroundMusic();
			}
		}
		if (!checkBoxMusicSwitch.isSelected())
		{
			audioController.stopBackgroundMusic();
		}
	}

	private void configureFrame()
	{
		setTitle(Messages.getString("OptionFrame.FrameTitle")); //$NON-NLS-1$
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		double screenSizeFactor = 0.25;
		Dimension size = GUIHelper.getProperSizeRelativeToScreensize(screenSizeFactor);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void configureCheckBoxes()
	{
		checkBoxMusicSwitch = new JCheckBox(Messages.getString("OptionFrame.checkBoxMusicSwitch")); //$NON-NLS-1$
		checkBoxMusicSwitch.setSelected(true);
	}

}

package de.fh.swt.schiffeversenken.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class AudioController implements Runnable
{
	private Properties prop;
	private Sequencer sequencer;
	private Map<String, AudioClip> clips = new HashMap<String, AudioClip>();

	public AudioController()
	{
		prop = new Properties();
		try
		{
			prop.load(ClassLoader.getSystemResourceAsStream("sounds.properties"));
		}
		catch (IOException e)
		{
			System.out.println("Properties konnte nicht geladen werden");
		}
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		startBackgroundMusic();
	}

	public void startBackgroundMusic()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			InputStream midiFile = ClassLoader.getSystemResourceAsStream(prop.getProperty("backgroundMusic"));
			sequencer.setSequence(MidiSystem.getSequence(midiFile));
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		}
		catch (MidiUnavailableException e2)
		{
			System.out.println("MidiUnavailableException");
		}
		catch (InvalidMidiDataException e)
		{
			System.out.println("InvalidMidiDataException");
		}
		catch (IOException e)
		{
			System.out.println("IOException");
		}

	}

	public void stopBackgroundMusic()
	{
		sequencer.stop();
	}

	public void playSound(String soundName)
	{
		AudioClip sound = null;

		if (!clips.containsKey(soundName))
		{
			try
			{
				File file = new File(prop.getProperty(soundName));
				clips.put(soundName, Applet.newAudioClip(file.toURI().toURL()));
			}
			catch (MalformedURLException e)
			{
				System.out.println("sup?!");
				return;
			}
		}

		sound = clips.get(soundName);
		sound.play();
	}

}

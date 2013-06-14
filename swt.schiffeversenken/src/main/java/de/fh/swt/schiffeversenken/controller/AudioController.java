package de.fh.swt.schiffeversenken.controller;



import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import de.fh.swt.schiffeversenken.data.SoundFile;

public class AudioController implements Runnable
{

	private Sequencer sequencer;
	private Map<SoundFile, AudioClip> clips = new HashMap<SoundFile, AudioClip>();

	public AudioController()
	{
		new Thread(this).start();

	}

	@Override
	public void run()
	{
		initAudioClips();
		startBackgroundMusic();
	}

	public void startBackgroundMusic()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			InputStream midiFile = ClassLoader.getSystemResourceAsStream(SoundFile.backgroundMusic.toString());
			sequencer.setSequence(MidiSystem.getSequence(midiFile));
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

	private void initAudioClips()
	{

	}

	public void playSound(SoundFile soundFile)
	{
		AudioClip sound = null;

		if (clips.containsKey(soundFile))
		{
			sound = clips.get(soundFile);
		}
		else
		{
			try
			{
				URL url = null;
				url = new URL(soundFile.toString());
				clips.put(soundFile, Applet.newAudioClip(url));
			}
			catch (MalformedURLException e)
			{
				return;
			}
		}
		sound.play();
	}

	public void playLoop(SoundFile soundFile)
	{
		AudioClip sound = null;

		if (clips.containsKey(soundFile))
		{
			sound = clips.get(soundFile);
		}
		else
		{
			try
			{
				URL url = null;
				url = new URL(soundFile.toString());
				clips.put(soundFile, Applet.newAudioClip(url));
			}
			catch (MalformedURLException e)
			{
				return;
			}
		}
		sound.loop();
	}
}

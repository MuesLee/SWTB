package de.fh.swt.schiffeversenken.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController
{
	private Properties prop;
	private Sequencer sequencer;
	private Map<String, File> clips = new HashMap<String, File>();

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
	}

	public boolean sequencerIsRunning()
	{
		if (sequencer == null)
		{
			return false;
		}
		return sequencer.isRunning();
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
		sequencer.close();
	}

	public synchronized void playSound(final String soundName)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					playClip(soundName);
				}
				catch (IOException e)
				{

				}
				catch (UnsupportedAudioFileException e)
				{
				}
				catch (LineUnavailableException e)

				{

				}
				catch (InterruptedException e)
				{
				}
			}

			private File getFile(String soundName)
			{

				if (!clips.containsKey(soundName))
				{
					File file = new File(prop.getProperty(soundName));
					clips.put(soundName, file);
				}
				return clips.get(soundName);
			}

			private void playClip(String soundName) throws IOException, UnsupportedAudioFileException,
				LineUnavailableException, InterruptedException
			{
				class AudioListener implements LineListener
				{
					private boolean done = false;

					@Override
					public synchronized void update(LineEvent event)
					{
						Type eventType = event.getType();
						if ((eventType == Type.STOP) || (eventType == Type.CLOSE))
						{
							done = true;
							notifyAll();
						}
					}

					public synchronized void waitUntilDone() throws InterruptedException
					{
						while (!done)
						{
							wait();
						}
					}
				}
				AudioListener listener = new AudioListener();
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(ClassLoader
					.getSystemResourceAsStream(prop.getProperty(soundName)));
				try
				{
					Clip clip = AudioSystem.getClip();
					clip.addLineListener(listener);
					clip.open(audioInputStream);
					try
					{
						clip.start();
						listener.waitUntilDone();
					}
					finally
					{
						clip.close();
					}
				}
				finally
				{
					audioInputStream.close();
				}
			}
		}).start();
	}
}

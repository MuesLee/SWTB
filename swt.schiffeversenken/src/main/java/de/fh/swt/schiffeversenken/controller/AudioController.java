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
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController implements Runnable
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
		new Thread(this).start();
	}

	@Override
	public void run()
	{

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

	public void playSound(String soundName)
	{
		try
		{
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem.getAudioInputStream(getFile(soundName));
			AudioFormat format = audioInputStream.getFormat();
			//ALAW/ULAW samples in PCM konvertieren
			if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
				|| ((format.getEncoding() == AudioFormat.Encoding.ALAW)))
			{
				System.out.println("encoding");
				AudioFormat tmp = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
					format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
					format.getFrameRate(), true);
				audioInputStream = AudioSystem.getAudioInputStream(tmp, audioInputStream);
				format = tmp;
			}
			int size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
			byte[] audio = new byte[size];
			DataLine.Info info = new DataLine.Info(Clip.class, format, size);
			audioInputStream.read(audio, 0, size);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(format, audio, 0, size);
			clip.start();

		}
		catch (UnsupportedAudioFileException e)
		{
			System.out.println("SCHEISS FILE!");
		}
		catch (IOException e)
		{

		}
		catch (LineUnavailableException e)
		{
			System.out.println("KEINE LINE MEHR DA");
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
}

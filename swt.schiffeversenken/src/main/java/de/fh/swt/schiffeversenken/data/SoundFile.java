package de.fh.swt.schiffeversenken.data;


public enum SoundFile {
	backgroundMusic("backgroundMusic.mid"), hit("shot_hit.wav"), nohit("shot_nohit.wav"), congratulations(
		"congratz.wav"), destroyed("terminated.wav");

	private String path;

	private SoundFile(String path)

	{
		this.path = path;
	}

	@Override
	public String toString()
	{
		return this.path;
	}
}

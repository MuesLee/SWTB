package de.fh.swt.schiffeversenken.data;

public enum SoundFile {
	backgroundMusic("backgroundMusic.mid"), hit("src/main/resources/shot_hit.wav"), nohit(
		"src/main/resources/shot_nohit.wav"), congratulations("src/main/resources/congratz.wav"), destroyed(
		"src/main/resources/terminated.wav");

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

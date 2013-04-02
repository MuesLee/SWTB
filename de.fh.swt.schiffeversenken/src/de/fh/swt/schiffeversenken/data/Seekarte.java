package de.fh.swt.schiffeversenken.data;

public class Seekarte
{

	private Schiffsteil[][] koordinaten;
	private int groesse;

	public Seekarte(int groesse)
	{
		this.groesse = groesse;
		initiere();
	}

	private void initiere()
	{

		this.koordinaten = new Schiffsteil[groesse][groesse];
	}

	public Schiffsteil[][] getKoordinaten()
	{
		return koordinaten;
	}

	public Schiffsteil getSchiffsteil(int x, int y)
	{
		if ((x >= 0) && (y >= 0) && (x < groesse) && (y < groesse))
		{
			return koordinaten[x][y];
		}

		else
		{
			return null;
		}
	}

	public void setKoordinaten(Schiffsteil[][] koordinaten)
	{
		this.koordinaten = koordinaten;
	}

	public int getGroesse()
	{
		return groesse;
	}

	public void setGroesse(int groesse)
	{
		this.groesse = groesse;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < koordinaten.length; i++)
		{
			for (int o = 0; o < koordinaten.length; o++)
			{
				Schiffsteil s = koordinaten[i][o];
				if (s != null)
				{
					if (s.isIntakt())
					{
						sb.append("O");
					}
					else
					{
						sb.append("X");
					}
				}
				else
				{
					sb.append(" ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}

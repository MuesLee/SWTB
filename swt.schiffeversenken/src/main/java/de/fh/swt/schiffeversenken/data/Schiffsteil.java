package de.fh.swt.schiffeversenken.data;

public class Schiffsteil
{

	private boolean intakt = true;

	public boolean isIntakt()
	{
		return intakt;
	}

	public void setIntakt(boolean istIntakt)
	{
		this.intakt = istIntakt;
	}

	public Schiff getSchiff()
	{
		return schiff;
	}

	private Schiff schiff;

	public Schiffsteil(Schiff schiff)
	{
		this.schiff = schiff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (intakt ? 1231 : 1237);
		result = prime * result + ((schiff == null) ? 0 : schiff.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schiffsteil other = (Schiffsteil) obj;
		if (intakt != other.intakt)
			return false;
		if (schiff == null) {
			if (other.schiff != null)
				return false;
		} else if (!schiff.equals(other.schiff))
			return false;
		return true;
	}

}


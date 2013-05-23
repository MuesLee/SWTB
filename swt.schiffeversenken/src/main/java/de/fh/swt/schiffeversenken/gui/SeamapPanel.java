package de.fh.swt.schiffeversenken.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.fh.swt.schiffeversenken.controller.GameManager;
import de.fh.swt.schiffeversenken.data.Coords;
import de.fh.swt.schiffeversenken.data.HitType;

public class SeamapPanel extends JPanel implements Observer
{

	private static final long serialVersionUID = 1L;
	private GameManager gameManager;
	private int seamapSize = 12;
	private ShipPlacementFrame shipPlacementFrame;
	private int playerID;
	private GridBagLayout gridLayout;
	private int spaceBetweenCells = 1;
	private boolean isClickable = false;

	//setzt alle nötigen Komponenten für das SeamapPanel
	public SeamapPanel(int playerID, GameManager gameManager, Dimension size)
	{
		this.gameManager = gameManager;
		this.playerID = playerID;
		this.gameManager.addObserver(this);
		seamapSize = gameManager.getActivePlayer().getSeamap().getSize();
		configure(size);
		setVisible(true);
	}

	//Konfigurieren des SeamapPanels
	private void configure(Dimension size)
	{
		gridLayout = new GridBagLayout();
		setLayout(gridLayout);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		fillGrid();
	}

	//füllt das Grid der Seamap
	private void fillGrid()
	{
		for (int i = 0; i < seamapSize; i++)
		{
			for (int j = 0; j < seamapSize; j++)
			{
				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = i;
				gridBagConstraints.gridy = j;
				gridBagConstraints.gridheight = 1;
				gridBagConstraints.gridwidth = 1;
				gridBagConstraints.weightx =1;
				gridBagConstraints.weighty =1;
				gridBagConstraints.insets = new Insets(spaceBetweenCells, spaceBetweenCells, spaceBetweenCells,
					spaceBetweenCells);
				gridBagConstraints.fill = GridBagConstraints.BOTH;
				add(new ShipPartCanvas(this, new Coords(i, j), calculateCellSize()), gridBagConstraints);
			}
		}
	}

	//Kalkuliert die Größe der einzelnen Zellen des Grids
	public Dimension calculateCellSize()
	{
		double tWidth = getWidth() / seamapSize;
		double tHeight = getHeight() / seamapSize;

		if (tHeight < 1)
		{
			tHeight = 1;
		}
		if (tWidth < 1)
		{
			tWidth = 1;
		}

		double cellSize = (Math.min(tHeight, tWidth)) - spaceBetweenCells*2;

		return new Dimension((int) cellSize, (int) cellSize);
	}

	//gibt ShipPlacementFrame zurück
	public ShipPlacementFrame getShipPlacementFrame()
	{
		return shipPlacementFrame;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		GUIStatusCode code = (GUIStatusCode) arg;

		if (code != null)
		{
			switch (code)
			{
				case DataHasChanged:
					repaint();
				break;
				case ItsPlayerOnesTurnNow:
				case ItsPlayerTwosTurnNow:
					checkItsClickable(code);
				break;
			}
		}

	}

	//prüfen, ob das angeklickte Feld auch zur GUI des aktiven Spielers gehört
	private void checkItsClickable(GUIStatusCode code)
	{
		if (((code == GUIStatusCode.ItsPlayerOnesTurnNow) && (playerID == 1))
			|| ((code == GUIStatusCode.ItsPlayerTwosTurnNow) && (playerID == 2)))
		{
			isClickable = true;
			JOptionPane.showMessageDialog(this, Messages.getString("SeamapPanel.InfoTextPlayersTurnPart1") + playerID + Messages.getString("SeamapPanel.InfoTextPlayersTurnPart2")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			isClickable = false;
		}
	}

	
	public boolean isClickable()
	{
		return isClickable;
	}

	//gibt den HitType des aktuellen Schusses zurück
	public HitType handleShot(Coords coords)
	{
		return gameManager.handleShot(coords);
	}
}

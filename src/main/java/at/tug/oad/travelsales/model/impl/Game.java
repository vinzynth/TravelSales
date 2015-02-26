package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948 21.11.2014 - 00:01:11
 * 
 */
public final class Game implements IGame {

	private int id;
	private String name;
	private String logoPath;
	private IUser creator;
	private List<ILevel> levels;
	private boolean visible;
	private Date createDate;

	@SuppressWarnings("unused")
	private Game() {}

	/**
	 * @param name
	 * @param logoPath
	 * @param creator
	 * @param visible
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if name is null or a empty string or too long <br>
	 * 				- if logoPath is null or a empty string or too long <br>
	 * 				- if creator is null
	 * @throws TravelSalesException ({@link TSExceptionType#INVALID_FILE}) if
	 * 				given logo file couldnt accessed
	 */
	public Game(final String name, final String logoPath, final IUser creator, final boolean visible) {	
		if(isNull(name))
			throw new IllegalArgumentException("Parameter name is null");
		if(isNull(logoPath))
			throw new IllegalArgumentException("Parameter logoPath is null");
		if(isNull(creator))
			throw new IllegalArgumentException("Parameter creator is null");
		
		if(name.isEmpty())
			throw new IllegalArgumentException("Parameter name is a empty string");
//		if(logoPath.isEmpty())
//			throw new IllegalArgumentException("Parameter logoPath is a empty string");

		if(name.length() >= 255)
			throw new IllegalArgumentException("Parameter name is too long");
		
		File f;
		if(!(f = new File(logoPath)).canRead() || f.isDirectory())
			this.logoPath = "";
//			try {
//				throw new FileNotFoundException("Given logo file does not exist: " + f.getAbsolutePath());
//			} catch (FileNotFoundException e) {
//				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
//			}
		else
			try {
				String s = f.getCanonicalPath();
				if(s.length() >= 255)
					throw new IllegalArgumentException("Parameter logoPath is too long");
				this.logoPath = s;
			} catch (Exception e) {
				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
			}
		
		this.createDate = new Date();
		this.levels = new ArrayList<>();
		this.name = name;
		this.creator = creator;
		this.visible = visible;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.utils.database.IPersistable#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.chrl.nutils.interfaces.INestedCollection#getNestedCollection()
	 */
	@Override
	public List<ILevel> getNestedCollection() {
		return levels;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#getCreator()
	 */
	@Override
	public IUser getCreator() {
		return creator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#setName(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#isVisible()
	 */
	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#setVisible(boolean)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setVisible(boolean visibility) {
		this.visible = visibility;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#getLogoPath()
	 */
	@Override
	public String getLogoPath() {
		return logoPath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.model.IGame#setLogoPath(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(50 + this.size()*2500);
		sb.append(super.toString() + " | ID: " + this.id + " | " + this.name + " | " + this.creator.getAccountName() + " | #Levels: " + this.size() + " | Logo: " + this.logoPath).append(System.lineSeparator());
		sb.append("Levels:");
		for (ILevel ilevel : levels)
			sb.append(System.lineSeparator()).append(ilevel.toString());
		sb.append(System.lineSeparator());
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IGame#getCreateDate()
	 */
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setCreator(IUser creator) {
		this.creator = creator;
	}

	@SuppressWarnings("unused")
	private void setLevels(List<ILevel> levels) {
		this.levels = levels;
	}
	
	@SuppressWarnings("unused")
	private List<ILevel> getLevels() {
		return levels;
	}

	@SuppressWarnings("unused")
	private void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

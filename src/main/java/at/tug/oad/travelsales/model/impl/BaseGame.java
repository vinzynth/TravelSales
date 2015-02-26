package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGameConstraint;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:02:00
 * 
 */
public final class BaseGame implements IBaseGame {

	private int id;
	private List<IPoint> points;
	private List<IGameConstraint> constraints;
	private IUser creator;
	private String name;
	private String figurePath;
	private Date createDate;
	
	@SuppressWarnings("unused")
	private BaseGame() {}
	
	/**
	 * @param creator
	 * @param name
	 * @param figurePath
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if name is null or a empty string <br>
	 * 				- if figurePaht is null or a empty string <br>
	 * 				- if creator is null
	 * @throws TravelSalesException ({@link TSExceptionType#INVALID_FILE}) if
	 * 				given figure file couldnt accessed
	 */
	public BaseGame(final String name, final IUser creator, final String figurePath){
		if(isNull(name))
			throw new IllegalArgumentException("Parameter name is null");
		if(isNull(figurePath))
			throw new IllegalArgumentException("Parameter figurePath is null");
		if(isNull(creator))
			throw new IllegalArgumentException("Parameter creator is null");
		
		if(name.isEmpty())
			throw new IllegalArgumentException("Parameter name is a empty string");
//		if(figurePath.isEmpty())
//			throw new IllegalArgumentException("Parameter figurePath is a empty string");
		
		if(name.length() >= 255)
			throw new IllegalArgumentException("Parameter name is too long");
		
		
		File f;
		if(!(f = new File(figurePath)).canRead() || f.isDirectory())
			this.figurePath = "";
//			try {
//				throw new FileNotFoundException("Given logo file does not exist");
//			} catch (FileNotFoundException e) {
//				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
//			}
		else
			try {
				String s = f.getCanonicalPath();
				if(s.length() >= 255)
					throw new IllegalArgumentException("Parameter figurePath is too long");
				this.figurePath = s;
			} catch (IOException e) {
				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
			}
		
		this.createDate = new Date();
		this.points = new ArrayList<>(30);
		this.constraints = new ArrayList<>();
		
		this.creator = creator;
		this.name = name;
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.utils.database.IPersistable#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 * @see at.chrl.nutils.interfaces.INestedCollection#getNestedCollection()
	 */
	@Override
	public List<IPoint> getNestedCollection() {
		return points;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#getCreator()
	 */
	@Override
	public IUser getCreator() {
		return creator;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#setName(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#getFigurePath()
	 */
	@Override
	public String getFigurePath() {
		return figurePath;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#setFigurePath(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setFigurePath(String figurePath) {
		this.figurePath = figurePath;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#getGameConstraints()
	 */
	@Override
	public List<IGameConstraint> getGameConstraints() {
		return constraints;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append(super.toString() + " | ID: " + this.id + " | Name: " + this.name + " | Creator: " + this.creator.getAccountName() + " | #Points: " + this.size() + " | #Constraints: " + this.constraints.size() + " | Background figure: " + this.figurePath);
		sb.append(System.lineSeparator());
		sb.append("Points:");
		sb.append(System.lineSeparator());
		for (IPoint point : this.getNestedCollection())
			sb.append(point.toString()).append(System.lineSeparator());
		sb.append("Game Constraints:");
		sb.append(System.lineSeparator());
		for (IGameConstraint constrains : this.getGameConstraints())
			sb.append(constrains.toString()).append(System.lineSeparator());
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IBaseGame#getCreateDate()
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
	private void setPoints(List<IPoint> points) {
		this.points = points;
	}

	@SuppressWarnings("unused")
	private void setConstraints(List<IGameConstraint> constraints) {
		this.constraints = constraints;
	}

	@SuppressWarnings("unused")
	private void setCreator(IUser creator) {
		this.creator = (User) creator;
	}

	@SuppressWarnings("unused")
	private void setCreateDate(Date creationDate) {
		this.createDate = creationDate;
	}

	public List<IPoint> getPoints() {
		return points;
	}

	public List<IGameConstraint> getConstraints() {
		return constraints;
	}
}

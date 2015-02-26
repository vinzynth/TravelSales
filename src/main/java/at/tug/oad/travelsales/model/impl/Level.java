package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:01:37
 * 
 */
public final class Level implements ILevel {

	private int id;
	private List<IBaseGame> baseGames;
	private IUser creator;
	private String name;
	private Date createDate;

	@SuppressWarnings("unused")
	private Level() {}
	
	/**
	 * @param name
	 * @param creator
	 * 
	 * @throws IllegalArgumentException <br>
	 * 			- if parameter name is null or a empty string or too long<br>
	 * 			- if parameter creator is null
	 */
	public Level(final String name, final IUser creator) {
		if(isNull(name))
			throw new IllegalArgumentException("Parameter name is null");
		if(isNull(creator))
			throw new IllegalArgumentException("Parameter creator is null");
		
		if(name.isEmpty())
			throw new IllegalArgumentException("Parameter name is a empty string");

		if(name.length() >= 255)
			throw new IllegalArgumentException("Parameter name is too long");
		
		this.createDate = new Date();
		this.baseGames = new ArrayList<IBaseGame>();
		this.name = name;
		this.creator = creator;
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
	public List<IBaseGame> getNestedCollection() {
		return baseGames;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILevel#getCreator()
	 */
	@Override
	public IUser getCreator() {
		return creator;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILevel#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILevel#setName(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(50 + this.size()*500);
		sb.append(super.toString() + " | ID: " + this.id + " | Name: " + this.name + " | #BaseGames: " + this.size()).append(System.lineSeparator());
		sb.append("Base Games:");
		for (IBaseGame iBaseGame : baseGames)
			sb.append(System.lineSeparator()).append(iBaseGame.toString());
		sb.append(System.lineSeparator());
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILevel#getCreateDate()
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
	private void setBaseGames(List<IBaseGame> baseGames) {
		this.baseGames = baseGames;
	}

	@SuppressWarnings("unused")
	private void setCreator(IUser creator) {
		this.creator = creator;
	}

	@SuppressWarnings("unused")
	private void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@SuppressWarnings("unused")
	private List<IBaseGame> getBaseGames() {
		return baseGames;
	}
}

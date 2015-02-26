package at.tug.oad.travelsales.utils.database;

import java.util.Collections;
import java.util.List;

import com.bravestone.hibernate.configuration.JPAConfig;

/**
 * @author Leopold Christian - 1331948
 * 24.11.2014 - 00:38:30
 * 
 */
public class TSHibernateConfig extends JPAConfig{

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return Collections.emptyList();
	}

	private TSHibernateConfig() {}

	/**
	 * Singleton Holder class for type: HibernateJPAConfig
	 */
	private static final class SingletonHolder {
		private static final TSHibernateConfig instance = new TSHibernateConfig();
	}

	/**
	 * Returns single instance for this type
	 *
	 * @returns
	 *		Singleton instance for type HibernateJPAConfig
	 */
	public static final TSHibernateConfig getInstance() {
		return SingletonHolder.instance;
	}
}

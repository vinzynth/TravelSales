package at.tug.oad.travelsales.utils.database;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import at.chrl.callbacks.EnhancedObject;
import at.chrl.nutils.CollectionUtils;

import com.bravestone.hibernate.HibernateService;

/**
 * @author Leopold Christian - 1331948
 * 24.11.2014 - 02:19:43
 * 
 */
public class HibernateSession implements AutoCloseable{

	final EntityManager manager;
	
	final Map<EnhancedObject, Set<PersistCallback>> registeredCallbacks;
	
	public HibernateSession() {
		this.registeredCallbacks = CollectionUtils.newMap();
		manager = HibernateService.getInstance().getEntityManager(TSHibernateConfig.getInstance());
	}

	/**
	 * @return the manager
	 */
	public EntityManager getManager() {
		return manager;
	}
	
	public <T extends IPersistable> T getSynced(Class<T> clazz, int id){
		return addCallback(manager.find(clazz, id));
	}
	
	public void delete(IPersistable obj){
		if(obj.getId() <= 0)
			return;
		
		try {
			if(!manager.getTransaction().isActive())
				manager.getTransaction().begin();
			manager.remove(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Persists this object to database
	 * 
	 * @return true if peristance process was successfull
	 */
	public <T extends IPersistable> T sync(T obj){
		if(!manager.contains(obj))
			try {
				if(!manager.getTransaction().isActive())
					manager.getTransaction().begin();
				if(obj.getId() > 0)
					manager.merge(obj);
				else
					manager.persist(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		addCallback(obj);
		return obj;
	};
	
	private <T extends IPersistable> T addCallback(T obj){
		if(Objects.isNull(obj))
			return null;
		EnhancedObject eo;
		try {
			 eo = ((EnhancedObject)obj);			
		} catch (ClassCastException e) {
			return obj;
		}
		PersistCallback persistCallback = new PersistCallback(manager);
		if(!registeredCallbacks.containsKey(eo))
			registeredCallbacks.put(eo, CollectionUtils.newSet());
		registeredCallbacks.get(eo).add(persistCallback);
		eo.addCallback(persistCallback);
		return obj;
	};
	
	public Criteria createCriteria(Class<?> clazz){
		Session ses = this.getManager().unwrap(Session.class);
		return ses.createCriteria(clazz);
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		if(manager.getTransaction().isActive())
			manager.getTransaction().commit();
		if(manager.isOpen())
			manager.close();
		
		for (Entry<EnhancedObject, Set<PersistCallback>> ie : registeredCallbacks.entrySet())
			for (PersistCallback registeredCallback : ie.getValue())
				ie.getKey().removeCallback(registeredCallback);
	}
	
	public static <T extends IPersistable> void edit(T persistable, Consumer<T> consumer){
		try (HibernateSession session = new HibernateSession()) {
			session.sync(persistable);
			consumer.accept(persistable);
		} catch (Exception e) {
			throw new RuntimeException("Internal Hibernate Error: " + e.getMessage(), e);
		}
	}
}

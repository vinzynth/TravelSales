package at.tug.oad.travelsales.utils.database;

import javax.persistence.EntityManager;

import at.chrl.callbacks.Callback;
import at.chrl.callbacks.CallbackResult;

/**
 * @author Leopold Christian - 1331948
 * 24.11.2014 - 01:13:44
 * 
 */
public class PersistCallback implements Callback<IPersistable> {
	
	private EntityManager entityManager;

	public PersistCallback(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public CallbackResult<Object> beforeCall(IPersistable obj, Object[] args){
		if(!entityManager.isOpen() || entityManager.contains(obj))
			return CallbackResult.newContinue();
//		System.out.println("b call");
		try {
			if(!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			if(obj.getId() > 0)
				entityManager.merge(obj);
			else
				entityManager.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CallbackResult.newContinue();
	}
	
	@Override
	public CallbackResult<Object> afterCall(IPersistable obj, Object[] args, Object methodResult){
		if(!entityManager.isOpen())
			return CallbackResult.newContinue();
//		System.out.println("a call");
		try {
			if(!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CallbackResult.newContinue();
	}
}

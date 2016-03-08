package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Emls;
import iscas.util.HibernateSessionFactory;

public class EmlsDao {

	public synchronized void saveOrUpdate(Emls eml) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(eml);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Emls getEmlsById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emls eml = null;
		eml = (Emls) session.get(Emls.class, id);
		HibernateSessionFactory.closeSession();
		return eml;
	}

	@SuppressWarnings("unchecked")
	public Emls getEmlsByMD5(String fileMD5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emls eml = null;
		Criteria c = session.createCriteria(Emls.class);
		c.add(Restrictions.eq("filemd5", fileMD5));
		List<Emls> list = c.list();
		if (list != null && list.size() > 0)
			eml = list.get(0);
		return eml;
	}

	@SuppressWarnings("unchecked")
	public List<Emls> getEmlsUndo() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emls.class);
		c.add(Restrictions.eq("status", 0));
		c.addOrder(Order.asc("id"));
		c.setFirstResult(0);
		c.setMaxResults(10000);
		List<Emls> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Emls> getAllEmls() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emls.class);
		c.addOrder(Order.asc("id"));
		List<Emls> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emls attch = getEmlsById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

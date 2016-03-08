package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.VenronFilelist;
import iscas.util.HibernateSessionFactory;

public class VenronFilelistDao {

	public synchronized void saveOrUpdate(VenronFilelist venronFilelist) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(venronFilelist);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public VenronFilelist getVenronFilelistById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronFilelist venronFilelist = null;
		venronFilelist = (VenronFilelist) session.get(VenronFilelist.class, id);
		HibernateSessionFactory.closeSession();
		return venronFilelist;
	}

	@SuppressWarnings("unchecked")
	public VenronFilelist getVenronFilelistByMD5(String md5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronFilelist venronFilelist = null;
		Criteria c = session.createCriteria(VenronFilelist.class);
		c.add(Restrictions.eq("md5", md5));
		List<VenronFilelist> list = c.list();
		if (list != null && list.size() > 0)
			venronFilelist = list.get(0);
		return venronFilelist;
	}

	@SuppressWarnings("unchecked")
	public List<VenronFilelist> getVenronFilelistbyGroupId(String groupId) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(VenronFilelist.class);
		c.add(Restrictions.eq("groupId", groupId));
		c.addOrder(Order.asc("fileOrderInGroup"));
		List<VenronFilelist> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<VenronFilelist> getAllVenronFilelist() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(VenronFilelist.class);
		c.addOrder(Order.asc("id"));
		List<VenronFilelist> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronFilelist attch = getVenronFilelistById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

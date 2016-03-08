package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.VenronGroup;
import iscas.util.HibernateSessionFactory;

public class VenronGroupDao {

	public synchronized void saveOrUpdate(VenronGroup venronGroup) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(venronGroup);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public VenronGroup getVenronGroupById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronGroup venronGroup = null;
		venronGroup = (VenronGroup) session.get(VenronGroup.class, id);
		HibernateSessionFactory.closeSession();
		return venronGroup;
	}

	@SuppressWarnings("unchecked")
	public VenronGroup getVenronGroupByGroupOrder(String GroupOrder) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronGroup venronGroup = null;
		Criteria c = session.createCriteria(VenronGroup.class);
		c.add(Restrictions.eq("groupOrder", GroupOrder));
		List<VenronGroup> list = c.list();
		if (list != null && list.size() > 0)
			venronGroup = list.get(0);
		return venronGroup;
	}
	@SuppressWarnings("unchecked")
	public VenronGroup getVenronGroupByGroupName(String GroupName) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronGroup venronGroup = null;
		Criteria c = session.createCriteria(VenronGroup.class);
		c.add(Restrictions.eq("groupName", GroupName));
		List<VenronGroup> list = c.list();
		if (list != null && list.size() > 0)
			venronGroup = list.get(0);
		return venronGroup;
	}



	@SuppressWarnings("unchecked")
	public List<VenronGroup> getAllVenronGroup() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(VenronGroup.class);
		c.addOrder(Order.asc("id"));
		List<VenronGroup> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		VenronGroup attch = getVenronGroupById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Email;
import iscas.util.HibernateSessionFactory;

public class EmailDao {

	public EmailDao() {

	}

	public synchronized void saveOrUpdate(Email Email) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(Email);
		transaction.commit();
		HibernateSessionFactory.currentSession();
	}

	public synchronized void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Email temp = getEmailById(id);
		if (temp != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(temp);
			transaction.commit();
		}
		HibernateSessionFactory.currentSession();
	}

	public Email getEmailById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Email Email = null;
		Email = (Email) session.get(Email.class, id);
		HibernateSessionFactory.currentSession();
		return Email;
	}

	@SuppressWarnings("unchecked")
	public List<Email> getEmailsByIds(Integer ids[]) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Email.class);
		c.add(Restrictions.in("id", ids));
		List<Email> list = c.list();
		HibernateSessionFactory.currentSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Email> getEmailsBymd5s(String md5s[]) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Email.class);
		c.add(Restrictions.in("filemd5", md5s));
		List<Email> list = c.list();
		HibernateSessionFactory.currentSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public Email getEmailBymd5(String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Email result = null;
		Criteria c = session.createCriteria(Email.class);
		c.add(Restrictions.eq("filemd5", filemd5));
		c.addOrder(Order.asc("senddate"));
		List<Email> list = c.list();
		if (list != null && list.size() > 0)
			result = list.get(0);
		HibernateSessionFactory.currentSession();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Email> getAllEmail() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Email.class);
		c.addOrder(Order.asc("id"));
		List<Email> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

}

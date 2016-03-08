package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Emaildetail;
import iscas.util.HibernateSessionFactory;

public class EmaildetailDao {

	public EmaildetailDao() {

	}

	public synchronized void saveOrUpdate(Emaildetail Emaildetail) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(Emaildetail);
		transaction.commit();
		HibernateSessionFactory.currentSession();
	}

	

	public synchronized void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emaildetail temp = getEmaildetailById(id);
		if (temp != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(temp);
			transaction.commit();
		}
		HibernateSessionFactory.currentSession();
	}



	public Emaildetail getEmaildetailById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emaildetail Emaildetail = null;
		Emaildetail = (Emaildetail) session.get(Emaildetail.class, id);
		HibernateSessionFactory.currentSession();
		return Emaildetail;
	}


	@SuppressWarnings("unchecked")
	public Emaildetail getEmaildetailByEmlmd5(String emailmd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emaildetail result = null;
		Criteria c = session.createCriteria(Emaildetail.class);
		c.add(Restrictions.eq("filemd5",emailmd5 ));
		List<Emaildetail> list = c.list();
		if (list != null && list.size() > 0)
			result = list.get(0);
		HibernateSessionFactory.currentSession();
		return result;
	}



	@SuppressWarnings("unchecked")
	public List<Emaildetail> getEmaildetailHasAttachment() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emaildetail.class);	
		c.add(Restrictions.gt("attachmentnum", 0));	
		c.addOrder(Order.asc("id"));	
		List<Emaildetail> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}


}

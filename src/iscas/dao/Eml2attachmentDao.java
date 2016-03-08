package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Eml2attachment;
import iscas.util.HibernateSessionFactory;

public class Eml2attachmentDao {
	
	public synchronized void saveOrUpdate(Eml2attachment eml2attachment) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(eml2attachment);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}
	@SuppressWarnings("unchecked")
	public Eml2attachment getEml2AttachMap(String emlmd5,String attachmentmd5,String fileName) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Eml2attachment.class);	
		c.add(Restrictions.eq("emlmd5", emlmd5));
		c.add(Restrictions.eq("attachmentmd5", attachmentmd5));
		c.add(Restrictions.eq("attachmentname", fileName));
		List<Eml2attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		if(list!=null&&list.size()>=1)
			return list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Eml2attachment> getEml2AttachMapByAttachNameandmd5(String attachmentname,String attachmentmd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Eml2attachment.class);
		c.add(Restrictions.eq("attachmentname", attachmentname));
		c.add(Restrictions.eq("attachmentmd5", attachmentmd5));
		List<Eml2attachment> list = c.list();
		HibernateSessionFactory.closeSession();	
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Eml2attachment> getEml2AttachMapByEmlmd5(String emlmd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Eml2attachment.class);	
		c.add(Restrictions.eq("emlmd5", emlmd5));	
		List<Eml2attachment> list = c.list();
		HibernateSessionFactory.closeSession();	
		return list;
	}
	

	
	
}

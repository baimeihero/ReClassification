package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Filelist;


import iscas.util.HibernateSessionFactory;

public class FileListDao {

	public synchronized void saveOrUpdate(Filelist label) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(label);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Filelist getFilelistById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Filelist venronFilelist = null;
		venronFilelist = (Filelist) session.get(Filelist.class, id);
		HibernateSessionFactory.closeSession();
		return venronFilelist;
	}

	@SuppressWarnings("unchecked")
	public Filelist getFilelistByFileMD5(String fileMd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Filelist.class);
		c.add(Restrictions.eq("fileMd5", fileMd5));
		List<Filelist> list = c.list();
		return list.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<Filelist> getFilelistUndoMD5s() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Filelist.class);
		c.add(Restrictions.eq("status", 0));	
		c.addOrder(Order.asc("id"));
		List<Filelist> list = c.list();
		return list;
	}


	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Filelist attch = getFilelistById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

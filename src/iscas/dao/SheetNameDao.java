package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Sheetdetail;


import iscas.util.HibernateSessionFactory;

public class SheetNameDao {

	public synchronized void saveOrUpdate(Sheetdetail sheetname) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(sheetname);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Sheetdetail getSheetnameById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Sheetdetail venronSheetname = null;
		venronSheetname = (Sheetdetail) session.get(Sheetdetail.class, id);
		HibernateSessionFactory.closeSession();
		return venronSheetname;
	}

	@SuppressWarnings("unchecked")
	public List<Sheetdetail> getSheetnameByFileMD5(String fileMD5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Sheetdetail.class);
		c.add(Restrictions.eq("fileMD5", fileMD5));
		List<Sheetdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Sheetdetail> getAllSheetnames() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Sheetdetail.class);	
		List<Sheetdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}


	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Sheetdetail attch = getSheetnameById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}
	
	
	public List<Object[]> queryBySql(String sql) {    
		Session session = null;
		session = HibernateSessionFactory.currentSession();
        List<Object[]> list = session.createSQLQuery(sql).list();    
    	HibernateSessionFactory.closeSession();
        return list;    
    }    

}

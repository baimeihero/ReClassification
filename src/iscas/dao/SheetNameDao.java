package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Sheetname;


import iscas.util.HibernateSessionFactory;

public class SheetNameDao {

	public synchronized void saveOrUpdate(Sheetname sheetname) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(sheetname);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Sheetname getSheetnameById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Sheetname venronSheetname = null;
		venronSheetname = (Sheetname) session.get(Sheetname.class, id);
		HibernateSessionFactory.closeSession();
		return venronSheetname;
	}

	@SuppressWarnings("unchecked")
	public List<Sheetname> getSheetnameByFileMD5(String fileMD5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Sheetname.class);
		c.add(Restrictions.eq("fileMD5", fileMD5));
		List<Sheetname> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Sheetname> getAllSheetnames() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Sheetname.class);	
		List<Sheetname> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}


	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Sheetname attch = getSheetnameById(id);
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

package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Metrics;


import iscas.util.HibernateSessionFactory;

public class MetricsDao {

	public synchronized void saveOrUpdate(Metrics metrics) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(metrics);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Metrics getMetricsById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Metrics venronMetrics = null;
		venronMetrics = (Metrics) session.get(Metrics.class, id);
		HibernateSessionFactory.closeSession();
		return venronMetrics;
	}

	@SuppressWarnings("unchecked")
	public Metrics getMetricsByFileMD5(String fileMd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Metrics.class);
		c.add(Restrictions.eq("fileMd5", fileMd5));
		List<Metrics> list = c.list();
		return list.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<Metrics> getMetricsUndoMD5s() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Metrics.class);
		c.add(Restrictions.eq("status", 0));	
		c.addOrder(Order.asc("id"));
		List<Metrics> list = c.list();
		return list;
	}
	
	public List queryBySql(String sql) {    
		Session session = null;
		session = HibernateSessionFactory.currentSession();
        List<Object[]> list = session.createSQLQuery(sql).list();    
    	HibernateSessionFactory.closeSession();
        return list;    
    }    

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Metrics attch = getMetricsById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

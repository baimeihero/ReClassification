package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Labelinfo;

import iscas.util.HibernateSessionFactory;

public class LabelInfoDao {

	public synchronized void saveOrUpdate(Labelinfo label) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(label);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Labelinfo getLabelinfoById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Labelinfo venronFilelist = null;
		venronFilelist = (Labelinfo) session.get(Labelinfo.class, id);
		HibernateSessionFactory.closeSession();
		return venronFilelist;
	}

	@SuppressWarnings("unchecked")
	public List<Labelinfo> getLabelinfoByFileMD5(String fileMd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Labelinfo venronFilelist = null;
		Criteria c = session.createCriteria(Labelinfo.class);
		c.add(Restrictions.eq("fileMd5", fileMd5));
		c.addOrder(Order.asc("sheetIndex"));
		List<Labelinfo> list = c.list();
		return list;
	}


	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Labelinfo attch = getLabelinfoById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}

}

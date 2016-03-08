package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Emailaddress;
import iscas.util.HibernateSessionFactory;

/**
 * @ClassName: EmailaddressDao
 * @Description: 提供访问Attachement数据库表的接�?
 * @author BaimeiHero
 * @date 2015�?�?1�?下午4:21:46
 * 
 */
public class EmailAddressDao {

	public EmailAddressDao() {

	}

	public synchronized void saveOrUpdate(Emailaddress emailaddress) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(emailaddress);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Emailaddress getEmailaddressById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emailaddress emailaddress = null;
		emailaddress = (Emailaddress) session.get(Emailaddress.class, id);
		HibernateSessionFactory.closeSession();
		return emailaddress;
	}

	@SuppressWarnings("unchecked")
	public List<Emailaddress> getEmailaddresssByFileMD5(String fileName,String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emailaddress.class);
		c.add(Restrictions.eq("fileName", fileName));
		c.add(Restrictions.eq("filemd5", filemd5));
		c.addOrder(Order.asc("id"));
		List<Emailaddress> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	/**
	 * @Title:
	 * getEmailaddressFormulaUndo @Description:配合分析电子表格中的公式�?由于会失�?使用�?��标志位防止重复工�?@param @return @return
	 * List<Emailaddress> @throws
	 */
	

	public List<Emailaddress> getEmailaddresssByFileMd5AndType(String fileName,String filemd5,String addressType) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emailaddress.class);
		c.add(Restrictions.eq("fileName", fileName));
		c.add(Restrictions.eq("filemd5", filemd5));
		c.add(Restrictions.eq("addressType",addressType));
		c.addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<Emailaddress> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}



	@SuppressWarnings("unchecked")
	public List<Emailaddress> getEmailaddresssbyAddress(String Address) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emailaddress.class);
		c.add(Restrictions.eq("emailAddress", Address));
		c.addOrder(Order.desc("id"));
		List<Emailaddress> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Emailaddress> getEmailaddresssByMd5(String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emailaddress.class);
		c.add(Restrictions.eq("filemd5", filemd5));
		List<Emailaddress> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Emailaddress attch = getEmailaddressById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}



	@SuppressWarnings("unchecked")
	public List<Emailaddress> getEmailaddresss() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Emailaddress.class);	
		List<Emailaddress> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	

	public static void main(String[] args) {
		EmailAddressDao dao = new EmailAddressDao();
		Emailaddress detail = new Emailaddress();
		
		dao.saveOrUpdate(detail);
		System.out.println("hello java");

	}
}

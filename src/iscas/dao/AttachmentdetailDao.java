package iscas.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Attachmentdetail;
import iscas.util.DatabaseManager;
import iscas.util.HibernateSessionFactory;

/**
 * @ClassName: AttachmentdetailDao
 * @Description: 提供访问Attachement数据库表的接�?
 * @author BaimeiHero
 * @date 2015�?�?1�?下午4:21:46
 * 
 */
public class AttachmentdetailDao {

	public AttachmentdetailDao() {

	}

	public synchronized void saveOrUpdate(Attachmentdetail attachmentdetail) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(attachmentdetail);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}
	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		 Transaction tran =    session.beginTransaction();  
		    String hql = "DELETE from Attachmentdetail  WHERE id = ?";  
		 
		    Query q = session.createQuery(hql);  
		    q.setInteger(0, id);  
		    //执行更新语句  
		    q.executeUpdate();  
		    //提交事务  
		    tran.commit();  
		HibernateSessionFactory.closeSession();
	}

	public Attachmentdetail getAttachmentdetailById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Attachmentdetail attachmentdetaildetail = null;
		attachmentdetaildetail = (Attachmentdetail) session.get(Attachmentdetail.class, id);
		HibernateSessionFactory.closeSession();
		return attachmentdetaildetail;
	}

	@SuppressWarnings("unchecked")
	public List<Attachmentdetail> getAttachmentdetails(String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachmentdetail.class);	
		c.add(Restrictions.eq("filemd5", filemd5));
		c.addOrder(Order.asc("sheetposition"));
		List<Attachmentdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Attachmentdetail> getALLAttachmentdetails() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachmentdetail.class);	
		c.addOrder(Order.asc("id"));
		List<Attachmentdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public int getAttachmentSheetNum(String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachmentdetail.class);	
		c.add(Restrictions.eq("filemd5", filemd5));
		c.addOrder(Order.asc("sheetposition"));
		List<Attachmentdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		if(list==null)
			return 0;
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public Attachmentdetail getAttachmentdetail(String filemd5, String sheetName) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachmentdetail.class);	
		c.add(Restrictions.eq("filemd5", filemd5));
		c.add(Restrictions.eq("sheetname", sheetName));
		List<Attachmentdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		if (list != null && list.size() >= 1)
			return list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Attachmentdetail getAttachmentdetail(String filemd5, int posision) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachmentdetail.class);
		c.add(Restrictions.eq("filemd5", filemd5));
		c.add(Restrictions.eq("sheetposition", posision));
		List<Attachmentdetail> list = c.list();
		HibernateSessionFactory.closeSession();
		if (list != null && list.size() >= 1)
			return list.get(0);
		return null;
	}

	public static void main(String[] args) {
	
		List<Attachmentdetail> details=DatabaseManager.getALLAttachmentdetails();
		List<String> exsists=new ArrayList<String>();
		List<Integer> ids=new ArrayList<Integer>();
		for(int i=0;i<details.size();i++){
			System.out.println(i);
			Attachmentdetail detail=details.get(i);
			String temp=detail.getFilemd5()+"_"+detail.getSheetname()+"_"+detail.getSheetposition();
			if(exsists.contains(temp)){
				ids.add(detail.getId());
			}else{
				exsists.add(temp);
			}			
		}
		int i=1;
		
		for(Integer id:ids){
			System.out.println(i+"/"+ids.size());
			
			DatabaseManager.deleteAttachmentdetail(id);
			i++;
		}
		System.exit(0);;
	}
}

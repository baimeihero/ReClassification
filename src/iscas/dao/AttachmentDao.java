package iscas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import iscas.bean.Attachment;
import iscas.util.HibernateSessionFactory;

/**
 * @ClassName: AttachmentDao
 * @Description: 提供访问Attachement数据库表的接�?
 * @author BaimeiHero
 * @date 2015�?�?1�?下午4:21:46
 * 
 */
public class AttachmentDao {

	public AttachmentDao() {

	}

	public synchronized void saveOrUpdate(Attachment attachment) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(attachment);
		transaction.commit();
		HibernateSessionFactory.closeSession();
	}

	public Attachment getAttachmentById(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Attachment attachment = null;
		attachment = (Attachment) session.get(Attachment.class, id);
		HibernateSessionFactory.closeSession();
		return attachment;
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByIds(Integer ids[]) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.in("id", ids));
		c.addOrder(Order.asc("senddate"));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	/**
	 * @Title:
	 * getAttachmentFormulaUndo @Description:配合分析电子表格中的公式�?由于会失�?使用�?��标志位防止重复工�?@param @return @return
	 * List<Attachment> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentFormulaUndo() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.eq("hasformula", -12));
		c.addOrder(Order.asc("id"));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public List<Attachment> getAttachmentsByNames(String attachmentName[]) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.in("filename", attachmentName));
		c.add(Restrictions.gt("hasformula", -1));
		c.addOrder(Order.asc("senddate"));
		@SuppressWarnings("unchecked")
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}



	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByName(String attachmentName) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.like("filename", attachmentName));
		c.add(Restrictions.gt("hasformula", -1));
		c.addOrder(Order.desc("senddate"));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByMd5(String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.eq("filemd5", filemd5));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public Attachment getAttachmentByNameAndMd5(String attachmentName, String filemd5) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.eq("filename", attachmentName));
		c.add(Restrictions.eq("filemd5", filemd5));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		if (list != null && list.size() >= 1)
			return list.get(0);
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAllAttachments() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public List<Attachment> getAllAttachmentsCanBeAnalysis() {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.gt("hasformula", -1));
		@SuppressWarnings("unchecked")
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public void delete(int id) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Attachment attch = getAttachmentById(id);
		if (attch != null) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.delete(attch);
			transaction.commit();
		}
		HibernateSessionFactory.closeSession();
	}



	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByNamePrex(String namePrex) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.like("filename", namePrex, MatchMode.START));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByNameLike(String nameLike) {
		Session session = null;
		session = HibernateSessionFactory.currentSession();
		Criteria c = session.createCriteria(Attachment.class);
		c.add(Restrictions.like("filename", nameLike, MatchMode.ANYWHERE));
		List<Attachment> list = c.list();
		HibernateSessionFactory.closeSession();
		return list;
	}
	public List<String> getAllMD5(){
		Session session = null;
		session = HibernateSessionFactory.currentSession();	   
	List<String> list = session.createQuery( "select distinct(filemd5) from Attachment  order by id asc").list();
		HibernateSessionFactory.closeSession();
		return list;
	}

	public static void main(String[] args) {
		AttachmentDao dao = new AttachmentDao();
		Attachment detail = new Attachment();
		detail.setFilesize(1);
		dao.saveOrUpdate(detail);
		System.out.println("hello java");

	}
}

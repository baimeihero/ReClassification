package iscas.util;

import java.util.ArrayList;
import java.util.List;

import iscas.bean.Attachment;
import iscas.bean.Attachmentdetail;
import iscas.bean.Email;
import iscas.bean.Emailaddress;
import iscas.bean.Emaildetail;
import iscas.bean.Eml2attachment;
import iscas.bean.Emls;
import iscas.bean.Filelist;
import iscas.bean.Labelinfo;
import iscas.bean.Metrics;
import iscas.bean.Sheetname;
import iscas.bean.VenronFilelist;
import iscas.bean.VenronGroup;
import iscas.dao.AttachmentDao;
import iscas.dao.AttachmentdetailDao;
import iscas.dao.EmailAddressDao;
import iscas.dao.EmailDao;
import iscas.dao.EmaildetailDao;
import iscas.dao.Eml2attachmentDao;
import iscas.dao.EmlsDao;
import iscas.dao.FileListDao;
import iscas.dao.LabelInfoDao;
import iscas.dao.MetricsDao;
import iscas.dao.SheetNameDao;
import iscas.dao.VenronFilelistDao;
import iscas.dao.VenronGroupDao;

public class DatabaseManager {
	// =====================EmaildetailDao===================================
	public static boolean saveOrUpdateEmaildetail(Emaildetail detail) {
		if (detail == null)
			return false;
		EmaildetailDao detailDao = new EmaildetailDao();
		detailDao.saveOrUpdate(detail);
		return true;
	}

	public static boolean deleteEmaildetail(int id) {
		EmaildetailDao detailDao = new EmaildetailDao();
		detailDao.delete(id);
		return true;
	}

	public static Emaildetail getEmaildetailById(int id) {
		EmaildetailDao detailDao = new EmaildetailDao();
		return detailDao.getEmaildetailById(id);
	}

	public static Emaildetail getEmaildetailByEmlmd5(String emlmd5) {
		EmaildetailDao detailDao = new EmaildetailDao();
		return detailDao.getEmaildetailByEmlmd5(emlmd5);
	}

	public static List<Emaildetail> getEmaildetailHasAttachment() {
		EmaildetailDao detailDao = new EmaildetailDao();
		return detailDao.getEmaildetailHasAttachment();
	}

	// =====================EmailDao===================================
	public static boolean saveOrUpdateEmail(Email email) {
		if (email == null)
			return false;
		EmailDao detailDao = new EmailDao();
		detailDao.saveOrUpdate(email);
		return true;
	}

	public static boolean deleteEmail(int id) {
		EmailDao detailDao = new EmailDao();
		detailDao.delete(id);
		return true;
	}

	public static Email getEmailById(int id) {
		EmailDao detailDao = new EmailDao();
		return detailDao.getEmailById(id);
	}

	public static List<Email> getEmailsByIds(Integer ids[]) {
		EmailDao detailDao = new EmailDao();
		return detailDao.getEmailsByIds(ids);
	}

	public static Email getEmailBymd5(String emlmd5) {
		EmailDao detailDao = new EmailDao();
		return detailDao.getEmailBymd5(emlmd5);
	}

	public static List<Email> getAllEmail() {
		EmailDao detailDao = new EmailDao();
		return detailDao.getAllEmail();
	}

	public static List<Email> getEmailByAttachmentNameAndMd5(String attachmentname, String attachmentmd5) {
		List<Email> emals = new ArrayList<Email>();
		List<Eml2attachment> attacments = getEml2AttachMapByAttachNameandmd5(attachmentname, attachmentmd5);
		if (attacments == null || attacments.size() == 0)
			return emals;

		for (int i = 0; i < attacments.size(); i++) {
			Eml2attachment temp = attacments.get(i);
			Email temail = getEmailBymd5(temp.getEmlmd5());
			emals.add(temail);
		}
		return emals;
	}

	public static List<Email> getEmailByAttachmentName(String names[]) {
		List<Email> emals = new ArrayList<Email>();
		List<Attachment> attachments = getAttachmentsByNames(names);
		if (attachments == null || attachments.size() == 0)
			return emals;
		for (int i = 0; i < attachments.size(); i++) {
			Attachment tattachment = attachments.get(i);
			List<Eml2attachment> maps = getEml2AttachMapByAttachNameandmd5(tattachment.getFilename(),
					tattachment.getFilemd5());
			if (maps == null || maps.size() == 0)
				return emals;
			for (int k = 0; k < maps.size(); k++) {
				Eml2attachment temp = maps.get(k);
				Email temail = getEmailBymd5(temp.getEmlmd5());
				if (!emals.contains(temail))
					emals.add(temail);
			}
		}
		return emals;
	}

	// ===============================AttachmentDao===========================

	public static List<String> getAllAttachmentMD5(){
		AttachmentDao attacmentDao = new AttachmentDao();
	
		return attacmentDao.getAllMD5();
	}
	public static List<Attachment> getAttachmentsByName(String filename) {
		AttachmentDao attacmentDao = new AttachmentDao();
		List<Attachment> list = attacmentDao.getAttachmentsByName(filename);
		return list;
	}

	public static List<Attachment> getAttachmentsByNames(String filenames[]) {
		AttachmentDao attacmentDao = new AttachmentDao();
		List<Attachment> list = attacmentDao.getAttachmentsByNames(filenames);
		return list;
	}

	public static Attachment getAttachmentById(int id) {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAttachmentById(id);
	}

	public static List<Attachment> getAttachmentsByIds(Integer id[]) {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAttachmentsByIds(id);
	}

	public static boolean saveOrUpdateAttachment(Attachment attachment) {
		if (attachment == null)
			return false;
		AttachmentDao attacmentDao = new AttachmentDao();
		attacmentDao.saveOrUpdate(attachment);
		return true;
	}

	public static boolean saveOrUpdateAttachments(Attachment attachments) {
		if (attachments == null)
			return false;
		AttachmentDao attacmentDao = new AttachmentDao();
		attacmentDao.saveOrUpdate(attachments);
		return true;
	}

	public static boolean deleteAttachmentsByid(int id) {
		AttachmentDao attacmentDao = new AttachmentDao();
		attacmentDao.delete(id);
		return true;
	}

	public static List<Attachment> getAttachmentFormulaUndo() {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAttachmentFormulaUndo();

	}

	public static List<Attachment> getAllAttachments() {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAllAttachments();

	}

	public static List<Attachment> getAllAttachmentsCanBeAnalysis() {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAllAttachmentsCanBeAnalysis();

	}

	public static List<Attachment> getAttachmentByMd5(String filemd5) {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAttachmentsByMd5(filemd5);
	}

	public static Attachment getAttachmentByNameAndMd5(String fileName, String filemd5) {
		AttachmentDao attacmentDao = new AttachmentDao();
		return attacmentDao.getAttachmentByNameAndMd5(fileName, filemd5);
	}

	// ==========================EmlsDao==============================
	public static List<Emls> getEmlsUndo() {
		EmlsDao dao = new EmlsDao();
		List<Emls> emls = dao.getEmlsUndo();
		return emls;
	}

	public static List<Emls> getAllEmls() {
		EmlsDao dao = new EmlsDao();
		List<Emls> emls = dao.getAllEmls();
		return emls;
	}

	public static Emls getEmlsByMD5(String fileMD5) {
		EmlsDao dao = new EmlsDao();
		Emls emls = dao.getEmlsByMD5(fileMD5);
		return emls;
	}

	public static boolean saveOrUpdateEml(Emls eml) {
		EmlsDao dao = new EmlsDao();
		dao.saveOrUpdate(eml);
		return true;
	}

	// =============================Eml2attachmentDao=================

	public static boolean saveOrUpdateEml2Attachmetnt(Eml2attachment eml2attachment) {
		Eml2attachmentDao dao = new Eml2attachmentDao();
		dao.saveOrUpdate(eml2attachment);
		return true;

	}

	public static Eml2attachment getEml2AttachMap(String emlmd5, String attachmentmd5, String fileName) {
		Eml2attachmentDao dao = new Eml2attachmentDao();
		return dao.getEml2AttachMap(emlmd5, attachmentmd5, fileName);
	}

	public static List<Eml2attachment> getEml2AttachMapByEmlmd5(String emlmd5) {
		Eml2attachmentDao dao = new Eml2attachmentDao();
		return dao.getEml2AttachMapByEmlmd5(emlmd5);
	}

	public static List<Eml2attachment> getEml2AttachMapByAttachNameandmd5(String attachmentname, String attachmentmd5) {
		Eml2attachmentDao dao = new Eml2attachmentDao();
		return dao.getEml2AttachMapByAttachNameandmd5(attachmentname, attachmentmd5);
	}

	// =============================AttachmentdetailDao=================
	public static boolean saveOrUpdateAttachmentdetail(Attachmentdetail attachmentdetail) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		dao.saveOrUpdate(attachmentdetail);
		return true;
	}

	public static Attachmentdetail getAttachmentdetail(String filemd5, String sheetName) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getAttachmentdetail(filemd5, sheetName);

	}

	public static Attachmentdetail getAttachmentdetail(String filemd5, int posision) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getAttachmentdetail(filemd5, posision);

	}

	public static Attachmentdetail getAttachmentdetailById(int id) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getAttachmentdetailById(id);

	}

	public static List<Attachmentdetail> getAttachmentdetails(String filemd5) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getAttachmentdetails(filemd5);
	}

	public static List<Attachmentdetail> getALLAttachmentdetails() {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getALLAttachmentdetails();
	}

	public static void deleteAttachmentdetail(int id) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		dao.delete(id);
	}

	public static int getAttachmentSheetNum(String filemd5) {
		AttachmentdetailDao dao = new AttachmentdetailDao();
		return dao.getAttachmentSheetNum(filemd5);
	}

	// =============================VenronGroupDao=================
	public static void saveOrUpdateVenronGroup(VenronGroup venronGroup) {
		VenronGroupDao dao = new VenronGroupDao();
		dao.saveOrUpdate(venronGroup);
	}

	public static List<VenronGroup> getAllVenronGroup() {
		VenronGroupDao dao = new VenronGroupDao();
		return dao.getAllVenronGroup();
	}

	public static VenronGroup getVenronGroupByGroupName(String GroupName) {
		VenronGroupDao dao = new VenronGroupDao();
		return dao.getVenronGroupByGroupName(GroupName);
	}

	public static VenronGroup getVenronGroupByGroupOrder(String GroupOrder) {
		VenronGroupDao dao = new VenronGroupDao();
		return dao.getVenronGroupByGroupOrder(GroupOrder);
	}

	// =============================VenronGroupDao=================
	public static void saveOrUpdateVenronFilelist(VenronFilelist venronFilelist) {
		VenronFilelistDao dao = new VenronFilelistDao();
		dao.saveOrUpdate(venronFilelist);
	}

	public static VenronFilelist getVenronFilelistById(int id) {
		VenronFilelistDao dao = new VenronFilelistDao();
		return dao.getVenronFilelistById(id);
	}

	public static VenronFilelist getVenronFilelistByMD5(String md5) {
		VenronFilelistDao dao = new VenronFilelistDao();
		return dao.getVenronFilelistByMD5(md5);
	}

	public static List<VenronFilelist> getVenronFilelistbyGroupId(String groupId) {
		VenronFilelistDao dao = new VenronFilelistDao();
		return dao.getVenronFilelistbyGroupId(groupId);

	}

	public static List<VenronFilelist> getAllVenronFilelist() {
		VenronFilelistDao dao = new VenronFilelistDao();
		return dao.getAllVenronFilelist();
	}

	// =============================EmailAddressDao=================
	public static void saveOrUpdateEmailaddress(Emailaddress emailaddress) {
		EmailAddressDao dao = new EmailAddressDao();
		dao.saveOrUpdate(emailaddress);
	}

	public static Emailaddress getEmailaddressById(int id) {
		EmailAddressDao dao = new EmailAddressDao();
		return dao.getEmailaddressById(id);
	}
	public static List<Emailaddress> getEmailaddresssByFileMD5(String fileName,String filemd5) {
		EmailAddressDao dao = new EmailAddressDao();
		return dao.getEmailaddresssByFileMD5(fileName, filemd5);
	}
	public static List<Emailaddress> getEmailaddresssByFileMd5AndType(String fileName,String filemd5,String addressType) {
		EmailAddressDao dao = new EmailAddressDao();
		return dao.getEmailaddresssByFileMd5AndType(fileName, filemd5, addressType);
	}
	public static List<Emailaddress> getEmailaddresssbyAddress(String Address) {
		EmailAddressDao dao = new EmailAddressDao();
		return dao.getEmailaddresssbyAddress(Address);
	}
	
	// =============================LabelInfoDao=================
	public static void saveOrUpdateLabelinfo(Labelinfo label) {
		LabelInfoDao dao=new LabelInfoDao();
		dao.saveOrUpdate(label);
	}
	public static   List<Labelinfo> getLabelinfoByFileMD5(String fileMd5) {
		LabelInfoDao dao=new LabelInfoDao();
		return dao.getLabelinfoByFileMD5(fileMd5);
	}
	public static   List<Labelinfo> getAllLabelinfo() {
		LabelInfoDao dao=new LabelInfoDao();
		return dao.getAllLabelinfo();
	}
	// ==============================FileListDao=================
	public static void saveOrUpdateFileList(Filelist filelist) {
		FileListDao dao=new FileListDao();
		dao.saveOrUpdate(filelist);
	}
	public static   List<Filelist> getFilelistUndoMD5s() {
		FileListDao dao=new FileListDao();
		return dao.getFilelistUndoMD5s();
	}
	public static  Filelist getFilelistByFileMD5(String fileMd5) {
		FileListDao dao=new FileListDao();
		return dao.getFilelistByFileMD5(fileMd5);
	}
	public static  List<String>  getFilelistCanBeAnalysis() {
		FileListDao dao=new FileListDao();
		return dao.queryBySql(SQLStatements.Query_FileList_CanBeAnalysis);
	}
	// ==============================SheetName=================
	public static void saveOrUpdateSheetName(Sheetname sheetname) {
		SheetNameDao dao=new SheetNameDao();
		dao.saveOrUpdate(sheetname);
	}
	
	
	public static  List<Sheetname> getSheetnameByFileMD5(String fileMD5) {
		SheetNameDao dao=new SheetNameDao();
		return dao.getSheetnameByFileMD5(fileMD5);
	}
	
	public static List<Sheetname> getAllSheetnames() {
		SheetNameDao dao=new SheetNameDao();
		return dao.getAllSheetnames();
	}
	// ==============================MetricDao=================
	public static void saveOrUpdateMetrics(Metrics metrics) {
		MetricsDao dao=new MetricsDao();
		dao.saveOrUpdate(metrics);
	}
}

package main.java.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.entity.NhanVien;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;


public class NhanVienDAO {
	
	private SessionFactory sessionFactory;

	public NhanVienDAO() {
		sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}
	
	public List<NhanVien> lay20NhanVienGanDay() {
		List<NhanVien> list = new ArrayList<NhanVien>();
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			list = session.createNativeQuery("select top 20 * from NhanVien order by maNhanVien desc", NhanVien.class).getResultList();
			
			transaction.commit();
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return list;
	}
	
	public NhanVien getNhanVien(String manv) {
		NhanVien nv = new NhanVien();
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			nv = session.find(NhanVien.class, manv);
			
			transaction.commit();
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return nv;
	}
	
	public boolean addNhanVien(NhanVien nv) {
		
		boolean flag = false;
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			session.save(nv);
			transaction.commit();
			
			flag = true;
			
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return flag;
	}

	public boolean deleteNhanVien(String maNV) {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			String sqlQuery = "delete from NhanVien where maNhanVien = :x";
			session.createNativeQuery(sqlQuery).setParameter("x", maNV).executeUpdate();

			transaction.commit();
			flag = true;
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean updateNhanVien(NhanVien nv) {
		
		boolean flag = false;
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			session.update(nv);

			transaction.commit();
			
			flag = true;
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	public List<NhanVien> findNhanVien(String tennv, String diachi, String email, String sdt, LocalDate ngayvaolam, int loainv){
		
		List<NhanVien> list = new ArrayList<NhanVien>();
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		
		if(tennv.trim().equals(""))
			tennv = null;
		if(diachi.trim().equals(""))
			diachi = null;
		if(email.trim().equals(""))
			email = null;
		if(sdt.trim().equals(""))
			sdt = null;
		
		String query = "select * from NhanVien ";
		
		if(tennv != null || diachi != null || email !=null || sdt !=null || ngayvaolam != null  || loainv != 2)
			query += "where ";
		
		if(tennv != null)
			query += "tenNhanVien like :ten ";
		if(diachi != null) {
			if(tennv == null)
				query += "diaChi like :dc ";
			else
				query += "and diaChi like :dc ";
		}
		if(email != null) {
			if(tennv == null && diachi == null)
				query += "email like :em ";
			else
				query += "and email like :em ";
		}
		if(sdt != null) {
			if(tennv == null && diachi == null && email == null)
				query += "soDienThoai = :sdt ";
			else
				query += "and soDienThoai = :sdt ";
		}
		if(ngayvaolam != null) {
			if(tennv == null && diachi == null && email == null && sdt == null)
				query += "ngayVaoLam = :ngayvaolam ";
			else
				query += "and ngayVaoLam = :ngayvaolam ";
		}
		if(loainv != 2) {
			if(tennv == null && diachi == null && email == null && sdt == null && ngayvaolam == null)
				query += "loaiNhanVien = :loainhanvien ";
			else
				query += "and loaiNhanVien = :loainhanvien ";
		}
		
		try {
			
			NativeQuery<NhanVien> nativeQuery = session.createNativeQuery(query, NhanVien.class);
			if(tennv != null)
				nativeQuery.setParameter("ten", "%" + tennv + "%");
			if(diachi != null)
				nativeQuery.setParameter("dc", "%" + diachi + "%");
			if(email != null)
				nativeQuery.setParameter("em", "%" + email + "%");
			if(sdt != null)
				nativeQuery.setParameter("sdt", sdt);
			if(ngayvaolam != null)
				nativeQuery.setParameter("ngayvaolam", ngayvaolam);
			if(loainv != 2)
				nativeQuery.setParameter("loainhanvien", loainv);
			
			list = nativeQuery.getResultList();
			
			transaction.commit();
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return list;
	}
}

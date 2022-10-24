package main.java.dao;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.entity.HoatChat;
import main.java.entity.NhaCungCap;
import main.java.entity.PhanLoai;
import main.java.entity.Thuoc;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;


public class ThuocDAO {
	private SessionFactory sessionFactory;

	public ThuocDAO() {
		super();
		this.sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	public List<Thuoc> lay20ThuocGanDay() {
		List<Thuoc> list = new ArrayList<Thuoc>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			list = session.createNativeQuery("select top 20 * from Thuoc order by maThuoc desc", Thuoc.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}
	public boolean themThuoc(Thuoc thuoc) {		
		boolean flag = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.save(thuoc);
			transaction.commit();

			flag = true;


		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	public  boolean xoaThuoc(String maThuoc) {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			String sqlQuery = "update Thuoc set trangThai = 0 where maThuoc = :x";
			session.createNativeQuery(sqlQuery).setParameter("x", maThuoc).executeUpdate();

			transaction.commit();
			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}
	public boolean capNhatThuoc(Thuoc thuoc) {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.update(thuoc);

			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}
	public List<Thuoc> timThuoc(String tenThuoc, String quyCachDongGoi, String donViTinh, String nuocSanXuat, String dangBaoChe, PhanLoai phanLoai, int trangThai) {
		List<Thuoc> result = new ArrayList<Thuoc>();

		if(tenThuoc.trim().equals(""))
			tenThuoc = null;
		if(quyCachDongGoi.trim().equals(""))
			quyCachDongGoi = null;
		if(donViTinh.trim().equals(""))
			donViTinh = null;
		if(nuocSanXuat.trim().equals(""))
			nuocSanXuat = null;
		if(dangBaoChe.trim().equals(""))
			dangBaoChe = null;

		String query = "select * from Thuoc ";

		if(tenThuoc != null || quyCachDongGoi != null || donViTinh !=null || nuocSanXuat !=null || dangBaoChe != null || phanLoai != null || trangThai != -1) {
			query += "where ";

			if(tenThuoc != null)
				query += "tenThuoc like :ten ";
			if(quyCachDongGoi != null) {
				if(tenThuoc == null)
					query += "quyCachDongGoi like :qcdg ";
				else
					query += "and quyCachDongGoi like :qcdg ";
			}
			if(donViTinh != null) {
				if(tenThuoc == null && quyCachDongGoi == null)
					query += "donViTinh like :dvt ";
				else
					query += "and donViTinh like :dvt ";
			}
			if(nuocSanXuat != null) {
				if(tenThuoc == null && quyCachDongGoi == null && donViTinh == null)
					query += "nuocSanXuat like :nxs ";
				else
					query += "and nuocSanXuat like :nxs ";
			}
			if(dangBaoChe != null) {
				if(tenThuoc == null && quyCachDongGoi == null && donViTinh == null && nuocSanXuat == null)
					query += "dangBaoChe like :dbc ";
				else
					query += "and dangBaoChe like :dbc ";
			}
			if(phanLoai != null) {
				if(tenThuoc == null && quyCachDongGoi == null && donViTinh == null && nuocSanXuat == null && dangBaoChe == null)
					query += "phanLoai = :phanLoai ";
				else
					query += "and phanLoai = :phanLoai ";
			}
			if(trangThai != -1) {
				if(tenThuoc == null && quyCachDongGoi == null && donViTinh == null && nuocSanXuat == null && phanLoai == null && dangBaoChe == null)
					query += "trangThai = " + (trangThai == 1 ? "1" : "0") + " ";
				else
					query += "and trangThai = " + (trangThai == 1 ? "1" : "0") + " ";
			}

		}

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			NativeQuery<Thuoc> nativeQuery = session.createNativeQuery(query, Thuoc.class);
			if(tenThuoc != null)
				nativeQuery.setParameter("ten", "%" + tenThuoc + "%");
			if(quyCachDongGoi != null)
				nativeQuery.setParameter("qcdg", "%" + quyCachDongGoi + "%");
			if(donViTinh != null)
				nativeQuery.setParameter("dvt", "%" + donViTinh + "%");
			if(nuocSanXuat != null)
				nativeQuery.setParameter("nxs", nuocSanXuat);
			if(dangBaoChe != null)
				nativeQuery.setParameter("dbc", dangBaoChe);
			if(phanLoai != null)
				nativeQuery.setParameter("phanLoai", phanLoai == PhanLoai.THUOC_BAN_THEO_DON ? 0 : 1);

			result = nativeQuery.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	public List<String> getDSDonViTinh() {
		List<String> list = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			List<?> resultList = session.createNativeQuery("select donViTinh from Thuoc where donViTinh not like '' group by donViTinh").getResultList();
			for(Object item : resultList) {
				list.add((String)item);
			}

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	public List<String> getDSDangBaoChe() {
		List<String> list = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<?> resultList = session.createNativeQuery("select dangBaoChe from Thuoc where dangBaoChe not like '' group by dangBaoChe").getResultList();
			for(Object item : resultList) {
				list.add((String)item);
			}

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	public List<String> getDSNuocSanXuat() {
		List<String> list = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<?> resultList = session.createNativeQuery("select nuocSanXuat from Thuoc where nuocSanXuat not like '' group by nuocSanXuat").getResultList();
			for(Object item : resultList) {
				list.add((String)item);
			}

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	public boolean nhapThuocTuExcel(File file) {
		boolean result = false;

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();

		try {
			FileInputStream excelFile = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();

			NhaCungCap nhaCungCap = new NhaCungCap();

			Row tenNCCRow = iterator.next();
			nhaCungCap.setTenNCC(tenNCCRow.getCell(1).getStringCellValue());

			Row dcNCCRow = iterator.next();
			nhaCungCap.setDiaChi(dcNCCRow.getCell(1).getStringCellValue());

			Row faxNCCRow = iterator.next();
			nhaCungCap.setSoFax(faxNCCRow.getCell(1).getStringCellValue());

			List<NhaCungCap> resultList = session.createNativeQuery("select * from NhaCungCap where tenNCC = :ten and diaChi = :dc and soFax = :fax", NhaCungCap.class)
					.setParameter("ten", nhaCungCap.getTenNCC())
					.setParameter("dc", nhaCungCap.getDiaChi())
					.setParameter("fax", nhaCungCap.getSoFax())
					.getResultList();

			if(resultList.size() == 0)
				session.save(nhaCungCap);
			else
				nhaCungCap = resultList.get(0);

			iterator.next();
			iterator.next();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Thuoc thuoc = new Thuoc();

				thuoc.setTenThuoc(currentRow.getCell(2).getStringCellValue());
				thuoc.setQuyCachDongGoi(currentRow.getCell(3).getStringCellValue());
				thuoc.setDonViTinh(currentRow.getCell(4).getStringCellValue());
				thuoc.setDonGia(currentRow.getCell(5).getNumericCellValue());
				thuoc.setPhanLoai(currentRow.getCell(6).getNumericCellValue() == 0 ? PhanLoai.THUOC_BAN_THEO_DON : PhanLoai.THUOC_KHONG_THEO_DON);
				thuoc.setNuocSanXuat(currentRow.getCell(7).getStringCellValue());
				thuoc.setDangBaoChe(currentRow.getCell(8).getStringCellValue());
				thuoc.setNhaCungCap(nhaCungCap);
				thuoc.setTrangThai(true);
				thuoc.setHinhAnh(null);
				thuoc.setNhaCungCap(nhaCungCap);

				String[] hc = currentRow.getCell(9).getStringCellValue().split(";");
				String[] nd = currentRow.getCell(10).getStringCellValue().split(";");

				for(int i = 0; i < hc.length; i++) {
					HoatChat hoatChat = new HoatChat(hc[i], nd[i]);
					thuoc.themHoatChat(hoatChat);
				}

				session.save(thuoc);
			}

			transaction.commit();
			result = true;
			workbook.close();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return result;
	}

	public Object[] thongKeThuoc(boolean isHetHan, boolean isNgungBan, boolean isChuaBanLanNao) {
		Object[] result = new Object[4];

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			int soHetHan = 0;
			int soNgungBan = 0;
			int soChuaBanLanNao = 0;

			if(isHetHan)
				soHetHan = (int)session.createNativeQuery("select COUNT(maThuoc) from Thuoc where maThuoc in (select maThuoc from LoThuoc where hanSuDung <= getdate())").getSingleResult();

			if(isNgungBan)
				soNgungBan = (int)session.createNativeQuery("select COUNT(maThuoc) from Thuoc where trangThai = 0").getSingleResult();

			if(isChuaBanLanNao)
				soChuaBanLanNao = (int)session.createNativeQuery("select COUNT(maThuoc) from Thuoc where maThuoc not in (select maThuoc from CTHoaDon)").getSingleResult();

			String query = "select * from Thuoc where ";
			if(isHetHan)
				query += "maThuoc in (select maThuoc from LoThuoc where hanSuDung <= getdate()) ";

			if(isNgungBan) {
				if(isHetHan)
					query += "or trangThai = 0 ";
				else
					query += "trangThai = 0 ";

			}
			if(isChuaBanLanNao) {
				if(isHetHan || isNgungBan)
					query += "or maThuoc not in (select maThuoc from CTHoaDon)";
				else
					query += "maThuoc not in (select maThuoc from CTHoaDon)";
			}

			List<Thuoc> resultList = session.createNativeQuery(query, Thuoc.class).getResultList();

			result[0] = soHetHan;
			result[1] = soNgungBan;
			result[2] = soChuaBanLanNao;
			result[3] = resultList;

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
		}

		return result;
	}
}

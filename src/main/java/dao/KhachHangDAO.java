package main.java.dao;

import main.java.entity.KhachHang;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SessionFactory sessionFactory;

    public KhachHangDAO() {
        sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
    }

    public List<KhachHang> getlist() {
        List<KhachHang> list = new ArrayList<KhachHang>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            list = session.createNativeQuery("select * from KhachHang", KhachHang.class).getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }

    public List<KhachHang> lay20KhachHangGanDay() {
        List<KhachHang> list = new ArrayList<KhachHang>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            list = session.createNativeQuery("select top 20 * from KhachHang order by maKhachHang desc", KhachHang.class).getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }

    public boolean addKhachHang(KhachHang kh) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(kh);
            transaction.commit();

            flag = true;


        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }

    public boolean deleteKhachHang(String maKH) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            String sqlQuery = "delete from KhachHang where maKhachHang = :x";
            session.createNativeQuery(sqlQuery).setParameter("x", maKH).executeUpdate();

            transaction.commit();
            flag = true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }

    public boolean updateKhachHang(KhachHang kh) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.update(kh);

            transaction.commit();

            flag = true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }

    public List<KhachHang> findKhachHang(KhachHang kh) {

        List<KhachHang> list = new ArrayList<KhachHang>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        if (kh.getMaKhachHang().trim().equals(""))
            kh.setMaKhachHang(null);
        if (kh.getTenKhachHang().trim().equals(""))
            kh.setTenKhachHang(null);
        if (kh.getDiaChi().trim().equals(""))
            kh.setDiaChi(null);
        if (kh.getEmail().trim().equals(""))
            kh.setEmail(null);
        if (kh.getSoDienThoai().trim().equals(""))
            kh.setSoDienThoai(null);

        String query = "select * from KhachHang ";

        if (kh.getMaKhachHang() != null || kh.getTenKhachHang() != null || kh.getNgaySinh() != null || kh.getDiaChi() != null
                || kh.getEmail() != null || kh.getSoDienThoai() != null)
            query += "where ";

        if (kh.getMaKhachHang() != null)
            query += "maKhachHang like :ma";
        if (kh.getTenKhachHang() != null)
            if (kh.getMaKhachHang() == null)
                query += "tenKhachHang like :ten";
            else
                query += "and tenKhachHang like :ten";
        if (kh.getDiaChi() != null) {
            if (kh.getMaKhachHang() == null && kh.getTenKhachHang() == null)
                query += "diaChi like :dc ";
            else
                query += "and diaChi like :dc ";
        }
        if (kh.getEmail() != null) {
            if (kh.getMaKhachHang() == null && kh.getTenKhachHang() == null && kh.getDiaChi() == null)
                query += "email like :em ";
            else
                query += "and email like :em ";
        }
        if (kh.getSoDienThoai() != null) {
            if (kh.getMaKhachHang() == null && kh.getTenKhachHang() == null && kh.getDiaChi() == null && kh.getEmail() == null)
                query += "soDienThoai = :sdt ";
            else
                query += "and soDienThoai = :sdt ";
        }
        if (kh.getNgaySinh() != null) {
            if (kh.getMaKhachHang() == null && kh.getTenKhachHang() == null && kh.getDiaChi() == null
                    && kh.getEmail() == null && kh.getSoDienThoai() == null)
                query += "ngaySinh = :ngaysinh ";
            else
                query += "and ngaySinh = :ngaysinh ";
        }
        if (kh.isGioiTinh() == false || kh.isGioiTinh() == true) {
            if (kh.getNgaySinh() != null) {
                if (kh.getMaKhachHang() == null && kh.getTenKhachHang() == null && kh.getDiaChi() == null
                        && kh.getEmail() == null && kh.getSoDienThoai() == null && kh.getNgaySinh() == null)
                    query += "gioiTinh = :gioitinh ";
                else
                    query += "and gioiTinh = :gioitinh ";
            }
        }

        try {

            NativeQuery<KhachHang> nativeQuery = session.createNativeQuery(query, KhachHang.class);
            if (kh.getMaKhachHang() != null)
                nativeQuery.setParameter("ma", "%" + kh.getMaKhachHang() + "%");
            if (kh.getTenKhachHang() != null)
                nativeQuery.setParameter("ten", "%" + kh.getTenKhachHang() + "%");
            if (kh.getDiaChi() != null)
                nativeQuery.setParameter("dc", "%" + kh.getDiaChi() + "%");
            if (kh.getEmail() != null)
                nativeQuery.setParameter("em", "%" + kh.getEmail() + "%");
            if (kh.getSoDienThoai() != null)
                nativeQuery.setParameter("sdt", kh.getSoDienThoai());
            if (kh.getNgaySinh() != null)
                nativeQuery.setParameter("ngaysinh", kh.getNgaySinh());
            if (kh.isGioiTinh() == false || kh.isGioiTinh() == true)
                nativeQuery.setParameter("gioitinh", kh.isGioiTinh());

            list = nativeQuery.getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }
}


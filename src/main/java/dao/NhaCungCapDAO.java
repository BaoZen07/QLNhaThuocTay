package main.java.dao;

import main.java.entity.NhaCungCap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO{
    private SessionFactory sessionFactory;
    public NhaCungCapDAO() {
        sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
    }

    public List<NhaCungCap> getlist() {
        List<NhaCungCap> list = new ArrayList<NhaCungCap>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            list = session.createNativeQuery("select * from NhaCungCap", NhaCungCap.class).getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }
    public List<NhaCungCap> lay20NhaCungCapGanDay() {
        List<NhaCungCap> list = new ArrayList<NhaCungCap>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            list = session.createNativeQuery("select top 20 * from NhaCungCap order by maNCC desc", NhaCungCap.class).getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }
    public boolean addNhaCungCap(NhaCungCap ncc) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(ncc);
            transaction.commit();

            flag = true;


        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }
    public boolean deleteNhaCungCap(String maNCC) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            String sqlQuery = "delete from NhaCungCap where maNCC = :x";
            session.createNativeQuery(sqlQuery).setParameter("x", maNCC).executeUpdate();

            transaction.commit();
            flag = true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }
    public boolean updateNhaCungCap(NhaCungCap ncc) {

        boolean flag = false;

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.update(ncc);

            transaction.commit();

            flag = true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }
    public List<NhaCungCap> findNhaCungCap(NhaCungCap ncc){

        List<NhaCungCap> list = new ArrayList<NhaCungCap>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        if(ncc.getMaNCC().trim().equals(""))
            ncc.setMaNCC(null);
        if(ncc.getTenNCC().trim().equals(""))
            ncc.setTenNCC(null);
        if(ncc.getDiaChi().trim().equals(""))
            ncc.setDiaChi(null);
        if(ncc.getSoFax().trim().equals(""))
            ncc.setSoFax(null);

        String query = "select * from NhaCungCap ";

        if(ncc.getMaNCC() !=null || ncc.getTenNCC() != null || ncc.getDiaChi() != null || ncc.getSoFax() !=null )
            query += "where ";

        if(ncc.getMaNCC() != null)
            query += "maNCC like :ma ";
        if(ncc.getTenNCC() != null) {
            if (ncc.getMaNCC() == null)
                query += "tenNCC like :ten ";
            else
                query += "and tenNCC like :ten ";
        }
        if(ncc.getDiaChi() != null) {
            if (ncc.getMaNCC() == null && ncc.getTenNCC() == null)
                query += "diaChi like :dc ";
            else
                query += "and diaChi like :dc ";
        }
        if(ncc.getSoFax() != null){
            if(ncc.getMaNCC() == null && ncc.getTenNCC() == null && ncc.getDiaChi() == null)
                query += "soFax like :sofax";
            else
                query += "and soFax like :sofax";
        }

        try {

            NativeQuery<NhaCungCap> nativeQuery = session.createNativeQuery(query, NhaCungCap.class);
            if(ncc.getMaNCC() != null)
                nativeQuery.setParameter("ma", "%" + ncc.getMaNCC() + "%");
            if(ncc.getTenNCC() != null)
                nativeQuery.setParameter("ten", "%" + ncc.getTenNCC() + "%");
            if(ncc.getDiaChi() != null)
                nativeQuery.setParameter("dc", "%" + ncc.getDiaChi() + "%");
            if(ncc.getSoFax() != null)
                nativeQuery.setParameter("sofax", "%" + ncc.getSoFax() + "%");

            list = nativeQuery.getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }
}


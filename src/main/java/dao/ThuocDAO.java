package main.java.dao;

import main.java.entity.Thuoc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class ThuocDAO {
    private SessionFactory sessionFactory;

    public ThuocDAO() {
        sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
    }

    public List<Thuoc> getlist() {
        List<Thuoc> list = new ArrayList<Thuoc>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            list = session.createNativeQuery("select * from Thuoc", Thuoc.class).getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
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

    public boolean addThuoc(Thuoc thuoc) {

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

    public boolean deleteThuoc(String maThuoc) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        try {
            String sqlQuery = "delete from Thuoc where maThuoc = :x";
            session.createNativeQuery(sqlQuery).setParameter("x", maThuoc).executeUpdate();

            transaction.commit();
            flag = true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return flag;
    }

    public boolean updateThuoc(Thuoc thuoc) {

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

    public List<Thuoc> findThuoc(Thuoc thuoc) {

        List<Thuoc> list = new ArrayList<Thuoc>();

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        if (thuoc.getMaThuoc().trim().equals(""))
            thuoc.setMaThuoc(null);
        if (thuoc.getTenThuoc().trim().equals(""))
            thuoc.setTenThuoc(null);

        String query = "select * from Thuoc ";

        if (thuoc.getMaThuoc() != null || thuoc.getTenThuoc() != null)
            query += "where ";

        if (thuoc.getMaThuoc() != null)
            query += "maThuoc like :ma ";
        if (thuoc.getTenThuoc() != null) {
            if (thuoc.getMaThuoc() == null)
                query += "tenThuoc like :ten ";
            else
                query += "and tenThuoc like :ten ";
        }

        try {

            NativeQuery<Thuoc> nativeQuery = session.createNativeQuery(query, Thuoc.class);
            if (thuoc.getMaThuoc() != null)
                nativeQuery.setParameter("ma", "%" + thuoc.getMaThuoc() + "%");
            if (thuoc.getTenThuoc() != null)
                nativeQuery.setParameter("ten", "%" + thuoc.getTenThuoc() + "%");

            list = nativeQuery.getResultList();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }
}


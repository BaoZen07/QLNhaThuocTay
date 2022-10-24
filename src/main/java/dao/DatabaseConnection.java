package main.java.dao;

import main.java.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseConnection {
	private SessionFactory sessionFactory;
	private static DatabaseConnection databaseConnection;
	
	private DatabaseConnection() {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml")
				.build();
		
		Metadata metadata = new MetadataSources(registry)
			.addAnnotatedClass(NhaCungCap.class)
			.addAnnotatedClass(KhachHang.class)
			.addAnnotatedClass(NhanVien.class)
			.addAnnotatedClass(Thuoc.class)
			.addAnnotatedClass(HoaDon.class)
			.addAnnotatedClass(CTHoaDon.class)
			.addAnnotatedClass(CTHoaDon_PK.class)
			.addAnnotatedClass(HoatChat.class)
			.addAnnotatedClass(LoThuoc.class)
			.getMetadataBuilder()
			.build();
		
		this.sessionFactory = metadata.getSessionFactoryBuilder().build();
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static DatabaseConnection getInstance() {
		if(databaseConnection == null)
			databaseConnection = new DatabaseConnection();
		
		return databaseConnection;
	}
}

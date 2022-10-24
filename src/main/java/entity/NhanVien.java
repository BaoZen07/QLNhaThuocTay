package main.java.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class NhanVien {
	@Id
	@GeneratedValue(generator = "generator_nhanVienId")
	@GenericGenerator(name = "generator_nhanVienId", strategy = "main.java.entity.id_generator.NhanVienStringGeneratorId")
	private String maNhanVien;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String tenNhanVien;
	private LocalDate ngaySinh;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String diaChi;
	private boolean gioiTinh;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String email;

	@Column(columnDefinition = "nvarchar(255)")
	private String soDienThoai;
	private LocalDate ngayVaoLam;
	private int loaiNhanVien;

	@Column(columnDefinition = "nvarchar(255)")
	private String matKhau;
	
	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NhanVien(String tenNhanVien, LocalDate ngaySinh, String diaChi, boolean gioiTinh,
			String email, String soDienThoai, LocalDate ngayVaoLam, int loaiNhanVien) {
		this.tenNhanVien = tenNhanVien;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.ngayVaoLam = ngayVaoLam;
		this.loaiNhanVien = loaiNhanVien;
		this.matKhau = "111111";
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public LocalDate getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(LocalDate ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public int getLoaiNhanVien() {
		return loaiNhanVien;
	}

	public void setLoaiNhanVien(int loaiNhanVien) {
		this.loaiNhanVien = loaiNhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", ngaySinh=" + ngaySinh
				+ ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + ", email=" + email + ", soDienThoai=" + soDienThoai
				+ ", ngayVaoLam=" + ngayVaoLam + ", loaiNhanVien=" + loaiNhanVien + "]";
	}

	
	
	
}

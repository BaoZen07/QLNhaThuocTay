package main.java.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(CTHoaDon_PK.class)
public class CTHoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "maHoaDon")
	private HoaDon hoaDon;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "maThuoc")
	private Thuoc thuoc;
	private int soLuong;
	private double donGia;

	@Column(columnDefinition = "nvarchar(255)")
	private String donViTinh;
	
	public CTHoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CTHoaDon(HoaDon hoaDon, Thuoc thuoc) {
		super();
		this.hoaDon = hoaDon;
		this.thuoc = thuoc;
	}

	public CTHoaDon(HoaDon hoaDon, Thuoc thuoc, int soLuong, double donGia, String donViTinh) {
		super();
		this.hoaDon = hoaDon;
		this.thuoc = thuoc;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.donViTinh = donViTinh;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	@Override
	public String toString() {
		return "CTHoaDon [hoaDon=" + hoaDon + ", thuoc=" + thuoc + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ ", donViTinh=" + donViTinh + "]";
	}
	
	public double tinhThanhTien() {
		return soLuong * donGia;
	}
	
}

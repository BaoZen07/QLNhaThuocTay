package main.java.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class LoThuoc {
	@Id
	@GeneratedValue(generator = "generator_loThuocId")
	@GenericGenerator(name = "generator_loThuocId", strategy = "main.java.entity.id_generator.LoThuocStringGeneratorId")
	private String maLoThuoc;
	private int soLuong;
	private LocalDate hanSuDung;
	
	@ManyToOne
	@JoinColumn(name = "maThuoc")
	private Thuoc thuoc;

	public LoThuoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoThuoc(String maLoThuoc) {
		super();
		this.maLoThuoc = maLoThuoc;
	}

	public LoThuoc(String maLoThuoc, int soLuong, LocalDate hanSuDung, Thuoc thuoc) {
		super();
		this.maLoThuoc = maLoThuoc;
		this.soLuong = soLuong;
		this.hanSuDung = hanSuDung;
		this.thuoc = thuoc;
	}

	public String getMaLoThuoc() {
		return maLoThuoc;
	}

	public void setMaLoThuoc(String maLoThuoc) {
		this.maLoThuoc = maLoThuoc;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public LocalDate getHanSuDung() {
		return hanSuDung;
	}

	public void setHanSuDung(LocalDate hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	@Override
	public String toString() {
		return "LoThuoc [maLoThuoc=" + maLoThuoc + ", soLuong=" + soLuong + ", hanSuDung=" + hanSuDung + ", thuoc="
				+ thuoc + "]";
	}
	
	
}

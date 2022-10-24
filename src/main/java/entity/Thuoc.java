package main.java.entity;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Thuoc {
	@Id
	@GeneratedValue(generator = "generator_thuocId")
	@GenericGenerator(name = "generator_thuocId", strategy = "main.java.entity.id_generator.ThuocStringGeneratorId")
	private String maThuoc;
	
	@Column(columnDefinition = "ntext")
	private String tenThuoc;
	
	@ManyToOne
	@JoinColumn(name = "maNCC")
	private NhaCungCap nhaCungCap;
	
	@Column(columnDefinition = "ntext")
	private String quyCachDongGoi;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String donViTinh;
	private double donGia;
	
	@Enumerated
	private PhanLoai phanLoai;
	
	@ElementCollection
	@CollectionTable(name = "HoatChat", 
		joinColumns = @JoinColumn(name = "maThuoc"))
	@JoinColumn
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<HoatChat> dsHoatChat;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String nuocSanXuat;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String dangBaoChe;
	
	private Blob hinhAnh;
	
	@OneToMany(mappedBy = "thuoc")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<LoThuoc> dsLoThuoc;
	private boolean trangThai;
	
	public Thuoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Thuoc(String tenThuoc, NhaCungCap nhaCungCap, String quyCachDongGoi, String donViTinh,
			double donGia, PhanLoai phanLoai, List<HoatChat> dsHoatChat, String nuocSanXuat, String dangBaoChe,
			Blob hinhAnh, List<LoThuoc> dsLoThuoc, boolean trangThai) {
		super();
	
		this.tenThuoc = tenThuoc;
		this.nhaCungCap = nhaCungCap;
		this.quyCachDongGoi = quyCachDongGoi;
		this.donViTinh = donViTinh;
		this.donGia = donGia;
		this.phanLoai = phanLoai;
		this.dsHoatChat = dsHoatChat;
		this.nuocSanXuat = nuocSanXuat;
		this.dangBaoChe = dangBaoChe;
		this.hinhAnh = hinhAnh;
		this.dsLoThuoc = dsLoThuoc;
		this.trangThai = trangThai;
	}

	public String getMaThuoc() {
		return maThuoc;
	}

	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}

	public String getTenThuoc() {
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public String getQuyCachDongGoi() {
		return quyCachDongGoi;
	}

	public void setQuyCachDongGoi(String quyCachDongGoi) {
		this.quyCachDongGoi = quyCachDongGoi;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public PhanLoai getPhanLoai() {
		return phanLoai;
	}

	public void setPhanLoai(PhanLoai phanLoai) {
		this.phanLoai = phanLoai;
	}

	public List<HoatChat> getDsHoatChat() {
		return dsHoatChat;
	}

	public void setDsHoatChat(List<HoatChat> dsHoatChat) {
		this.dsHoatChat = dsHoatChat;
	}

	public String getNuocSanXuat() {
		return nuocSanXuat;
	}

	public void setNuocSanXuat(String nuocSanXuat) {
		this.nuocSanXuat = nuocSanXuat;
	}

	public String getDangBaoChe() {
		return dangBaoChe;
	}

	public void setDangBaoChe(String dangBaoChe) {
		this.dangBaoChe = dangBaoChe;
	}

	public Blob getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(Blob hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public List<LoThuoc> getDsLoThuoc() {
		return dsLoThuoc;
	}

	public void setDsLoThuoc(List<LoThuoc> dsLoThuoc) {
		this.dsLoThuoc = dsLoThuoc;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", quyCachDongGoi=" + quyCachDongGoi
				+ ", donViTinh=" + donViTinh + ", donGia=" + donGia + ", phanLoai=" + phanLoai + ", nuocSanXuat="
				+ nuocSanXuat + ", dangBaoChe=" + dangBaoChe + ", hinhAnh=" + hinhAnh + ", trangThai=" + trangThai
				+ "]";
	}

	/**
	 * Kiểm tra số lượng thuốc còn lại
	 * @param soLuong
	 * @return -1 nếu còn đủ thuốc
	 * @return số thuốc còn lại nếu không đủ
	 */
	public int kiemTraSoLuong(int soLuong) {
		if(dsLoThuoc == null)
			return 0;
		
		int sum = 0;
		for(LoThuoc item : dsLoThuoc) {
			if(item.getHanSuDung().isAfter(LocalDate.now())) {
				sum += item.getSoLuong();
			}
		}
		
		if(sum < soLuong)
			return sum;
		
		return -1;
	}
	
	public int kiemTraSoLuong() {
		if(dsLoThuoc == null)
			return 0;
		
		int sum = 0;
		for(LoThuoc item : dsLoThuoc) {
			if(item.getHanSuDung().isAfter(LocalDate.now())) {
				sum += item.getSoLuong();
			}
		}
		
		return sum;
	}
	
	public void giamSoLuong(int soLuong) {
		for(LoThuoc item : dsLoThuoc) {
			if(item.getHanSuDung().isAfter(LocalDate.now())) {
				if(item.getSoLuong() >= soLuong) {
					item.setSoLuong(item.getSoLuong() - soLuong);
					return;
				}
				else {
					soLuong -= item.getSoLuong();
					item.setSoLuong(0);
				}
			}
		}
	}
	
	public boolean themHoatChat(HoatChat hoatChat) {
		if(dsHoatChat == null)
			dsHoatChat = new ArrayList<HoatChat>();
		for(HoatChat hc : dsHoatChat)
			if(hc.getTenHoatChat().equalsIgnoreCase(hoatChat.getTenHoatChat()))
				return false;
		
		dsHoatChat.add(hoatChat);
		
		return true;
	}
	
	public boolean themLoThuoc(LoThuoc loThuoc) {
		if(dsLoThuoc == null)
			dsLoThuoc = new ArrayList<LoThuoc>();
		for(LoThuoc lt :dsLoThuoc)
			if(lt.getMaLoThuoc().equalsIgnoreCase(loThuoc.getMaLoThuoc()))
				return false;
		loThuoc.setMaLoThuoc("Lo" + new Date().getTime());
		
		dsLoThuoc.add(loThuoc);
		return true;
	}

}

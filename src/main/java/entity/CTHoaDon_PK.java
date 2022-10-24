package main.java.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CTHoaDon_PK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hoaDon;
	private String thuoc;

	public CTHoaDon_PK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CTHoaDon_PK(String hoaDon, String thuoc) {
		super();
		this.hoaDon = hoaDon;
		this.thuoc = thuoc;
	}

	public String getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(String hoaDon) {
		this.hoaDon = hoaDon;
	}

	public String getThuoc() {
		return thuoc;
	}

	public void setThuoc(String thuoc) {
		this.thuoc = thuoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hoaDon == null) ? 0 : hoaDon.hashCode());
		result = prime * result + ((thuoc == null) ? 0 : thuoc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTHoaDon_PK other = (CTHoaDon_PK) obj;
		if (hoaDon == null) {
			if (other.hoaDon != null)
				return false;
		} else if (!hoaDon.equals(other.hoaDon))
			return false;
		if (thuoc == null) {
			if (other.thuoc != null)
				return false;
		} else if (!thuoc.equals(other.thuoc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CTHoaDon_PK [hoaDon=" + hoaDon + ", thuoc=" + thuoc + "]";
	}
	
}

package main.java.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HoatChat {

	@Column(columnDefinition = "nvarchar(255)")
	private String tenHoatChat;

	@Column(columnDefinition = "nvarchar(255)")
	private String hamLuong;

	public HoatChat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HoatChat(String tenHoatChat, String hamLuong) {
		super();
		this.tenHoatChat = tenHoatChat;
		this.hamLuong = hamLuong;
	}

	public String getTenHoatChat() {
		return tenHoatChat;
	}

	public void setTenHoatChat(String tenHoatChat) {
		this.tenHoatChat = tenHoatChat;
	}

	public String getHamLuong() {
		return hamLuong;
	}

	public void setHamLuong(String hamLuong) {
		this.hamLuong = hamLuong;
	}

	@Override
	public String toString() {
		return "HoatChat [tenHoatChat=" + tenHoatChat + ", hamLuong=" + hamLuong
				+ "]";
	}
	
	
}

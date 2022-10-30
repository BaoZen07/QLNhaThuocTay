package main.java.bus;

import main.java.dao.KhachHangDAO;
import main.java.entity.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangBUS {
    private List<KhachHang> danhSachKH = new ArrayList<>();
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private static String lastInsertId = "";
    public KhachHangBUS() {
        readList();
    }
    public void readList() {
        danhSachKH = khachHangDAO.getlist();
    }
    public List<KhachHang> lay20KhachHangGanDay() {
        return  khachHangDAO.lay20KhachHangGanDay();
    }
    public static void setLastInsertId(String lastInsertId) {
        KhachHangBUS.lastInsertId = lastInsertId;
    }
    private Boolean checkData(KhachHang kh) {
        if(kh.getMaKhachHang()!=null || kh.getTenKhachHang()!=null || kh.getNgaySinh() !=null || kh.getEmail()!=null ||
                kh.getDiaChi()!=null || kh.getSoDienThoai() !=null) {
            for (KhachHang index : danhSachKH) {
                if (index.getMaKhachHang().equals(kh.getMaKhachHang())) {
                    return  false;
                }
            }
            return true;
        } else {
            return  false;
        }
    }
    private boolean checkUnique(KhachHang kh) {
        if(kh.getMaKhachHang()!=null || kh.getTenKhachHang()!=null || kh.getNgaySinh() !=null || kh.getEmail()!=null ||
                kh.getDiaChi()!=null || kh.getSoDienThoai() !=null) {
            for (KhachHang index : danhSachKH) {
                if (index.getMaKhachHang().equals(kh.getMaKhachHang())) {
                    return true;
                }
            }
            return false;
        } else
        {
            return false;
        }
    }
    public boolean addKhachHang(KhachHang kh)
    {
        if(checkData(kh)==true){
            if(khachHangDAO.addKhachHang(kh))
                return true;
        }
        return false;
    }
    public boolean updateKhachHang(KhachHang kh)
    {
        if(checkUnique(kh)==true) {
            if(khachHangDAO.updateKhachHang(kh))
                return  true;
        }
        return  false;
    }
    public boolean deleteKhachHang(String maKH)
    {
        if(maKH!=null) {
            for (int i=0; i<danhSachKH.size();i++) {
                if (maKH.equals(danhSachKH.get(i).getMaKhachHang())) {
                    if(khachHangDAO.deleteKhachHang(maKH))
                        return  true;
                }
            }
        }
        return  false;
    }
    public List<KhachHang> findKhachHang(KhachHang kh){
        return khachHangDAO.findKhachHang(kh);
    }
}


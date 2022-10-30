package main.java.bus;

import main.java.dao.ThuocDAO;
import main.java.entity.NhaCungCap;
import main.java.entity.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class ThuocBUS {
    private List<Thuoc> danhSachThuoc = new ArrayList<Thuoc>();
    private ThuocDAO thuocDAO = new ThuocDAO();
    private static String lastInsertId = "";

    public ThuocBUS() {
        readList();
    }

    public void readList() {
        danhSachThuoc = thuocDAO.getlist();
    }

    public List<Thuoc> lay20ThuocGanDay() {
        return thuocDAO.lay20ThuocGanDay();
    }

    public static void setLastInsertId(String lastInsertId) {
        ThuocBUS.lastInsertId = lastInsertId;
    }

    private Boolean checkData(Thuoc thuoc) {
        if (thuoc.getMaThuoc() != null || thuoc.getTenThuoc() != null || thuoc.getNhaCungCap() != null
                || thuoc.getQuyCachDongGoi() != null || thuoc.getDonViTinh() != null || thuoc.getDonGia() != 0
                || thuoc.getDangBaoChe() != null || thuoc.getNuocSanXuat() != null || thuoc.getPhanLoai() != null
                || thuoc.getHinhAnh() != null) {
            for (Thuoc index : danhSachThuoc) {
                if (index.getMaThuoc().equals(thuoc.getMaThuoc())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUnique(Thuoc thuoc) {
        if (thuoc.getMaThuoc() != null || thuoc.getTenThuoc() != null || thuoc.getNhaCungCap() != null
                || thuoc.getQuyCachDongGoi() != null || thuoc.getDonViTinh() != null || thuoc.getDonGia() != 0
                || thuoc.getDangBaoChe() != null || thuoc.getNuocSanXuat() != null || thuoc.getPhanLoai() != null
                || thuoc.getHinhAnh() != null) {
            for (Thuoc index : danhSachThuoc) {
                if (index.getMaThuoc().equals(thuoc.getMaThuoc())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean addThuoc(Thuoc thuoc) {
        if (checkData(thuoc) == true) {
            if (thuocDAO.addThuoc(thuoc))
                return true;
        }
        return false;
    }

    public boolean updateThuoc(Thuoc thuoc) {
        if (checkUnique(thuoc) == true) {
            if (thuocDAO.updateThuoc(thuoc))
                return true;
        }
        return false;
    }

    public boolean deleteThuoc(String maT) {
        if (maT != null) {
            for (int i = 0; i < danhSachThuoc.size(); i++) {
                if (maT.equals(danhSachThuoc.get(i).getMaThuoc())) {
                    if (thuocDAO.deleteThuoc(maT))
                        return true;
                }
            }
        }
        return false;
    }

    public List<Thuoc> findThuoc(Thuoc thuoc) {
        return thuocDAO.findThuoc(thuoc);
    }
}

package main.java.bus;

import main.java.dao.NhaCungCapDAO;
import main.java.entity.NhaCungCap;
import main.java.entity.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCapBUS {
    private List<NhaCungCap> danhSachNCC = new ArrayList<NhaCungCap>();
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private static String lastInsertId = "";
    public NhaCungCapBUS() {
        readList();
    }
    public void readList() {
        danhSachNCC = nhaCungCapDAO.getlist();
    }
    public List<NhaCungCap> lay20NhaCungCapGanDay() {
        return  nhaCungCapDAO.lay20NhaCungCapGanDay();
    }
    public static void setLastInsertId(String lastInsertId) {
        NhaCungCapBUS.lastInsertId = lastInsertId;
    }
    private Boolean checkData(NhaCungCap ncc) {
        if(ncc.getMaNCC()!=null || ncc.getTenNCC()!=null || ncc.getDiaChi()!= null || ncc.getSoFax()!=null) {
            for (NhaCungCap index : danhSachNCC) {
                if (index.getMaNCC().equals(ncc.getMaNCC())) {
                    return  false;
                }
            }
            return true;
        } else {
            return  false;
        }
    }
    private boolean checkUnique(NhaCungCap ncc) {
        if(ncc.getMaNCC()!=null || ncc.getTenNCC()!=null || ncc.getDiaChi()!= null || ncc.getSoFax()!=null) {
            for (NhaCungCap index : danhSachNCC) {
                if (index.getMaNCC().equals(ncc.getMaNCC())) {
                    return true;
                }
            }
            return false;
        } else
        {
            return false;
        }
    }
    public boolean addNhaCungCap(NhaCungCap ncc)
    {
        if(checkData(ncc)==true){
            if(nhaCungCapDAO.addNhaCungCap(ncc))
                return  true;
        }
        return false;
    }
    public boolean updateNhaCungCap(NhaCungCap ncc)
    {
        if(checkUnique(ncc)==true) {
            if(nhaCungCapDAO.updateNhaCungCap(ncc))
                return true;
        }
        return false;
    }
    public boolean deleteNhaCungCap(String maNCC)
    {
        if(maNCC!=null) {
            for (int i=0; i<danhSachNCC.size();i++) {
                if (maNCC.equals(danhSachNCC.get(i).getMaNCC())) {
                    if(nhaCungCapDAO.deleteNhaCungCap(maNCC))
                        return  true;
                }
            }

        }
        return  false;
    }
    public List<NhaCungCap> findNhaCungCap(NhaCungCap ncc) {
        return nhaCungCapDAO.findNhaCungCap(ncc);
    }
}

package main.java.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import main.java.dao.ThuocDAO;
import main.java.entity.*;
import net.java.balloontip.BalloonTip;

public class QuanLyThuocPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMa;
	private JTextField txtTen;
	private JTextArea txtQCDG;
	private JComboBox<String> cbbDVT;
	private JTextField txtDG;
	private JComboBox<String> cbbNuocSX;
	private JComboBox<String> cbbDangBaoChe;
	private ColoredButton btnTimKiem;
	private ColoredButton btnThem;
	private ColoredButton btnSua;
	private List<Thuoc> dsKetQua;
	private DefaultTableModel hoatChatModel;
	private CustomTable tableHoatChat;
	private DefaultTableModel loThuocModel;
	private CustomTable tableLoThuoc;
	private JTextField txtTenHC;
	private JDateChooser dateHSD;
	private JTextField txtHamLuong;
	private JTextField txtMaLo;
	private JTextField txtSL;
	private ColoredButton btnLuuHC;
	private ColoredButton btnXoaHC;
	private ColoredButton btnLuuLT;
	private ColoredButton btnXoaLT;
	private ColoredButton btnXoa;
	private JTextField txtNCC;
	private ColoredButton btnChonNCC;
	private DefaultComboBoxModel<String> phanLoaiModel;
	private JComboBox<String> cbbPhanLoai;
	private ChonNhaCungCapDialog chonNhaCungCapDialog;
	private ThuocDAO thuocDAO;
	private NhaCungCap nhaCungCap;
	private List<HoatChat> dsHoatChat;
	private List<LoThuoc> dsLoThuoc;
	private JPanel pnlCapNhat;
	private JPanel pnlTimKiem;
	private JTabbedPane tabbedPane;

	private ColoredButton btnXoaTrangCapNhat;
	private JTextField txtTenTK;
	private JTextArea txtQCDGTK;
	private JComboBox<String> cbbDVTTK;
	private JComboBox<String> cbbNuocSXTK;
	private JComboBox<String> cbbDangBaoCheTK;
	private ColoredButton btnQuayLaiTK;
	private DefaultTableModel modelTimKiem;
	private CustomTable tableTimKiem;
	private ColoredButton btnQuayLai;
	private ColoredButton btnXoaTrangTimKiem;
	private JTextField txtHinhAnh;
	private ColoredButton btnChonHinhAnh;
	private static DefaultComboBoxModel<String> phanLoaiModelTK;
	private JComboBox<String> cbbPhanLoaiTK;
	private Thuoc selectedThuoc;
	private ColoredButton btnSuaTK;
	private MainFrame mainFrame;
	private JLabel lbPage;
	private ColoredButton btnHome;
	private ColoredButton btnEnd;
	private ColoredButton btnBefore;
	private ColoredButton btnNext;
	private int currentIndex = 0;
	private static DefaultComboBoxModel<String> donViTinhModel;
	private static DefaultComboBoxModel<String> nuocSXModel;
	private static DefaultComboBoxModel<String> dangBaoCheModel;
	private ColoredButton btnNhapTuFile;
	private BalloonTip ballTenThuoc;
	private BalloonTip ballQCGD;
	private BalloonTip ballDVT;
	private BalloonTip ballDG;
	private BalloonTip ballNuocSX;
	private BalloonTip ballBaoChe;
	private BalloonTip ballNCC;
	private DefaultComboBoxModel<String> donViTinhTKModel;
	private DefaultComboBoxModel<String> nuocSXTKModel;
	private DefaultComboBoxModel<String> dangBCTKModel;

	public QuanLyThuocPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLookAndFeel();
		setOpaque(true);
		setBackground(Color.white);

		setLayout(new BorderLayout());

		pnlCapNhat = new JPanel(new BorderLayout()); pnlCapNhat.setOpaque(false);
		pnlTimKiem = new JPanel(new BorderLayout()); pnlTimKiem.setOpaque(false);
		addNorthCapNhat();
		addCenterCapNhat();
		addSouthCapNhat();
		addCenterTimKiem();
		addNorthTimKiem();
		addCenterTimKiem();

		tabbedPane = new JTabbedPane();		
		tabbedPane.addTab("Tìm kiếm", pnlTimKiem);
		tabbedPane.addTab("Cập nhật thông tin thuốc", pnlCapNhat);
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
		this.add(tabbedPane);

		dangBaoCheModel.addElement("");
		donViTinhModel.addElement("");
		nuocSXModel.addElement("");
		
		dangBCTKModel.addElement("");
		donViTinhTKModel.addElement("");
		nuocSXTKModel.addElement("");

		addEvent();


		dsKetQua = new ArrayList<Thuoc>();


		getAllComponents(this).forEach(item -> {
			item.addKeyListener(new KeyAdapter() {
				private boolean isCtrlPressed = false;

				@Override
				public void keyPressed(KeyEvent e) {
					if(isCtrlPressed) {
						//Nhấn phím N khi đang giữ phím Ctrl
						if(e.getKeyCode() == KeyEvent.VK_N)
							btnThem.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_X)
							btnXoa.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_LEFT)
							btnQuayLai.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_E) {
							btnXoaTrangCapNhat.doClick();
							btnXoaTrangTimKiem.doClick();
						} else if(e.getKeyCode() == KeyEvent.VK_F)
							btnTimKiem.doClick();

					} else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
						isCtrlPressed = true;
					else 
						isCtrlPressed = false;
				}

				@Override
				public void keyReleased(KeyEvent e) {
					isCtrlPressed = false;
				}
			});
		});
		thuocDAO = new ThuocDAO();
		loadData();

	}

	public static DefaultComboBoxModel<String> getDonViTinhModel() {
		return donViTinhModel;
	}

	public static DefaultComboBoxModel<String> getNuocSXModel() {
		return nuocSXModel;
	}

	public static DefaultComboBoxModel<String> getDangBaoCheModel() {
		return dangBaoCheModel;
	}

	public Component getDefaultFocusComponent() {
		return txtTenTK;
	}

	public List<Component> getAllComponents(Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	private void loadData() {
		dsKetQua = thuocDAO.lay20ThuocGanDay();

		taiDuLieuLenBang(dsKetQua, 0);

		for(String item : thuocDAO.getDSDangBaoChe()) {
			dangBaoCheModel.addElement(item);
			dangBCTKModel.addElement(item);
		}

		for(String item : thuocDAO.getDSDonViTinh()) {
			donViTinhModel.addElement(item);
			donViTinhTKModel.addElement(item);
		}

		for(String item : thuocDAO.getDSNuocSanXuat()) {
			nuocSXModel.addElement(item);
			nuocSXTKModel.addElement(item);
		}

	}

	private void taiDuLieuLenBang(List<Thuoc> dsThuoc, int minIndex) {
		if(dsThuoc.size() == 0) {
			modelTimKiem.setRowCount(0);
			lbPage.setText("Trang " + 1 + " trong " + 1 + " trang.");
			return;
		}

		if(minIndex >= dsThuoc.size() || minIndex < 0)
			return;

		lbPage.setText("Trang " + (minIndex / 20 + 1) + " trong " + ((dsThuoc.size() - 1) / 20 + 1) + " trang.");

		modelTimKiem.setRowCount(0);
		modelTimKiem.getDataVector().removeAllElements();
		modelTimKiem.fireTableDataChanged();
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);

		for(int i = minIndex; i < minIndex + 20; i++) {
			if(i >= dsThuoc.size())
				break;
			Thuoc thuoc = dsThuoc.get(i);
			SwingUtilities.invokeLater(() -> {
				String ha = "";
				byte[] b = null;
				if(thuoc.getHinhAnh() == null)
					ha =  "Images/image.png";
				else {
					try {
						Blob blob = thuoc.getHinhAnh();

						b = blob.getBytes(1, (int) blob.length());

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} 
				modelTimKiem.addRow(new Object[] {ha != "" ? ha : b , thuoc.getMaThuoc(),thuoc.getTenThuoc(), thuoc.getNhaCungCap() != null ? thuoc.getNhaCungCap().getTenNCC() : null, thuoc.getQuyCachDongGoi(),
						thuoc.getDangBaoChe(), thuoc.getDonViTinh(), thuoc.getDonGia(), thuoc.getPhanLoai() == PhanLoai.THUOC_BAN_THEO_DON ? "Thuốc bán theo đơn" : "Thuốc không theo đơn", thuoc.getNuocSanXuat(), thuoc.isTrangThai() ? "Đang bán" : "Ngừng bán" });

			});
		}

		currentIndex  = minIndex;

	}

	private void addEvent() {
		this.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("ancestor")) {
					tabbedPane.setSelectedIndex(0);
				}
			}
		});
		
		txtTenTK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timThuoc();
			}
		});
		txtQCDGTK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timThuoc();
			}
		});
		cbbDVTTK.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				timThuoc();
		});
		cbbDangBaoCheTK.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				timThuoc();
		});
		cbbNuocSXTK.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				timThuoc();
		});
		cbbPhanLoaiTK.addItemListener(e -> {
			timThuoc();
		});

		btnNhapTuFile.addActionListener(e -> {			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Excel file (*.xls)", "xls"));

			int selection = fileChooser.showOpenDialog(QuanLyThuocPanel.this);

			if(selection == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if(thuocDAO.nhapThuocTuExcel(selectedFile))
					UIConstant.showInfo(QuanLyThuocPanel.this, "Nhập từ file excel thành công!");
				else
					UIConstant.showError(QuanLyThuocPanel.this, "Nhập từ file excel không thành công!");
			}
		});

		txtTen.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtTen.getText().isEmpty())
					ballTenThuoc.setVisible(true);
				else
					ballTenThuoc.setVisible(false);
			}
		});

		txtQCDG.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtQCDG.getText().isEmpty())
					ballQCGD.setVisible(true);
				else
					ballQCGD.setVisible(false);
			}
		});

		cbbDVT.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (cbbDVT.getEditor().getItem().toString().trim().trim().isEmpty())
					ballDVT.setVisible(true);
				else
					ballDVT.setVisible(false);
			}
		});

		txtDG.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					double donGia = Double.parseDouble(txtDG.getText());
					if(donGia < 0) {
						ballDG.setVisible(true);
					}
					ballDG.setVisible(false);
				}
				catch (Exception ex) {
					ballDG.setVisible(true);
				}
			}
		});

		cbbNuocSX.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(cbbNuocSX.getEditor().getItem().toString().trim().trim().isEmpty()) 
					ballNuocSX.setVisible(true);
				else
					ballNuocSX.setVisible(false);
			}
		});

		cbbDangBaoChe.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(cbbDangBaoChe.getEditor().getItem().toString().trim().trim().isEmpty()) 
					ballBaoChe.setVisible(true);
				else
					ballBaoChe.setVisible(false);
			}
		});

		btnHome.addActionListener(e -> {
			if(dsKetQua != null) {
				taiDuLieuLenBang(dsKetQua, 0);
			}
		});

		btnEnd.addActionListener(e -> {
			if(dsKetQua != null) {
				taiDuLieuLenBang(dsKetQua, dsKetQua.size() - dsKetQua.size() % 20);
			}
		});

		btnBefore.addActionListener(e -> {
			if(dsKetQua != null) {
				taiDuLieuLenBang(dsKetQua, currentIndex  - 20);
			}
		});

		btnNext.addActionListener(e -> {
			if(dsKetQua != null) {
				taiDuLieuLenBang(dsKetQua, currentIndex + 20);
			}
		});

		btnSuaTK.addActionListener((e) -> {
			int row = tableTimKiem.getSelectedRow();
			if(row == -1) {
				UIConstant.showWarning(QuanLyThuocPanel.this, "Bạn chưa chọn thuốc để cập nhật");
				return;
			}

			row += currentIndex;

			Thuoc th = dsKetQua.get(row);

			txtMa.setText(th.getMaThuoc());
			txtTen.setText(th.getTenThuoc());
			txtQCDG.setText(th.getQuyCachDongGoi());
			txtNCC.setText(th.getNhaCungCap() != null ? th.getNhaCungCap().getTenNCC() : "");
			txtDG.setText(th.getDonGia()+"");
			cbbPhanLoai.setSelectedIndex(th.getPhanLoai()  == PhanLoai.THUOC_BAN_THEO_DON ? 0 : 1);
			cbbNuocSX.setSelectedItem(th.getNuocSanXuat());
			cbbDangBaoChe.setSelectedItem(th.getDangBaoChe());
			cbbDVT.setSelectedItem(th.getDonViTinh());
			nhaCungCap = th.getNhaCungCap();

			hoatChatModel.setRowCount(0);
			loThuocModel.setRowCount(0);

			if(dsKetQua.get(row).getDsHoatChat() != null) {
				dsHoatChat = dsKetQua.get(row).getDsHoatChat();
				for(HoatChat item : dsKetQua.get(row).getDsHoatChat()) {
					hoatChatModel.addRow(new Object[] {item.getTenHoatChat(),item.getHamLuong()});
				}
			}
			if(dsKetQua.get(row).getDsLoThuoc() != null) {
				dsLoThuoc = dsKetQua.get(row).getDsLoThuoc();
				for(LoThuoc item : dsKetQua.get(row).getDsLoThuoc()) {
					loThuocModel.addRow(new Object[] {item.getMaLoThuoc(),item.getSoLuong(),item.getHanSuDung(),item.getThuoc()});
				}
			}

			tabbedPane.setSelectedComponent(pnlCapNhat);
		});

		tableTimKiem.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableTimKiem.getSelectedRow() != -1)
					selectedThuoc = dsKetQua.get(tableTimKiem.getSelectedRow() + currentIndex);
			}
		});

		tableHoatChat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableHoatChat.getSelectedRow() != -1) {
					HoatChat hoatChat = dsHoatChat.get(tableHoatChat.getSelectedRow());
					txtTenHC.setText(hoatChat.getTenHoatChat());
					txtHamLuong.setText(hoatChat.getHamLuong());
				}
			}
		});

		tableLoThuoc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableLoThuoc.getSelectedRow() != -1) {
					LoThuoc loThuoc = dsLoThuoc.get(tableLoThuoc.getSelectedRow());
					txtMaLo.setText(loThuoc.getMaLoThuoc());
					txtSL.setText(loThuoc.getSoLuong() + "");
					dateHSD.setDate(Date.from(loThuoc.getHanSuDung().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				}
			}
		});

		btnChonNCC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chonNhaCungCapDialog == null)
					chonNhaCungCapDialog = new ChonNhaCungCapDialog(QuanLyThuocPanel.this);

				chonNhaCungCapDialog.setVisible(true);
			}
		});

		btnChonHinhAnh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Images file (*.png,*.jpg)", "png", "jpg"));
				int selection = fileChooser.showOpenDialog(QuanLyThuocPanel.this);

				if(selection == JFileChooser.OPEN_DIALOG) {
					File selectedFile = fileChooser.getSelectedFile();
					if (!selectedFile.exists()) {
						UIConstant.showWarning(QuanLyThuocPanel.this, "File không tồn tại!");
						return ;
					} else {
						txtHinhAnh.setText(selectedFile.getAbsolutePath());
					}

				}
			}
		});
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(validDataThuoc()) {
					String tenThuoc = txtTen.getText().trim();
					String quyCachDongGoi = txtQCDG.getText().trim();

					String donViTinh = "";
					if(cbbDVT.getSelectedIndex() != -1)
						donViTinh = cbbDVT.getSelectedItem().toString();
					else 
						donViTinh = cbbDVT.getEditor().getItem().toString().trim();

					Double donGia = Double.parseDouble(txtDG.getText());
					PhanLoai phanLoai = cbbPhanLoai.getSelectedIndex() == 0 ? PhanLoai.THUOC_BAN_THEO_DON:PhanLoai.THUOC_KHONG_THEO_DON;

					String nuocSanXuat = "";
					if(cbbNuocSX.getSelectedIndex() != -1)
						nuocSanXuat = cbbNuocSX.getSelectedItem().toString();
					else 
						nuocSanXuat = cbbNuocSX.getEditor().getItem().toString().trim();

					String dangBaoChe = "";
					if(cbbDangBaoChe.getSelectedIndex() != -1)
						dangBaoChe = cbbDangBaoChe.getSelectedItem().toString();
					else 
						dangBaoChe = cbbDangBaoChe.getEditor().getItem().toString().trim();

					Blob hinhAnh = null;
					if(txtHinhAnh.getText() != "") {
						try {
							FileInputStream inputStream = new FileInputStream(txtHinhAnh.getText());
							hinhAnh = new SerialBlob(Files.readAllBytes(new File(txtHinhAnh.getText().trim()).toPath()));
							inputStream.close();
						} catch (SQLException | IOException e1) {
							e1.printStackTrace();
						}
					}

					boolean trangThai = true;
					Thuoc thuoc = new Thuoc(tenThuoc, nhaCungCap, quyCachDongGoi == "" ? null : quyCachDongGoi, donViTinh == "" ? null : donViTinh, 
							donGia, phanLoai, dsHoatChat, nuocSanXuat == "" ? null : nuocSanXuat, 
									dangBaoChe == "" ? null : dangBaoChe, hinhAnh, dsLoThuoc, trangThai);

					if(thuocDAO.themThuoc(thuoc)) {
						UIConstant.showInfo(QuanLyThuocPanel.this,"Thêm thành công!");
						dsKetQua.add(thuoc);
						if (donViTinh != null) {
							donViTinhModel.addElement(donViTinh);
							donViTinhTKModel.addElement(donViTinh);
						}
						if (nuocSanXuat != null) {
							nuocSXModel.addElement(nuocSanXuat);
							nuocSXTKModel.addElement(nuocSanXuat);
						}
						if (dangBaoChe != null) {
							dangBaoCheModel.addElement(dangBaoChe);
							dangBCTKModel.addElement(dangBaoChe);
						}
					}
					else
						UIConstant.showInfo(QuanLyThuocPanel.this,"Thuốc đã tồn tại!");
				}	
			}
		});
		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedThuoc == null) {
					UIConstant.showInfo(QuanLyThuocPanel.this, "Chưa chọn thuốc");
				} else {
					int click = JOptionPane.showConfirmDialog(QuanLyThuocPanel.this, "Bạn có chắc chăn muốn chuyển trạng thái sang ngừng bán không?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("Images//warning.png"));
					if(click == JOptionPane.YES_OPTION) {
						if(thuocDAO.xoaThuoc(modelTimKiem.getValueAt(tableTimKiem.getSelectedRow(), 1).toString().trim())) {
							UIConstant.showInfo(QuanLyThuocPanel.this, "Đã chuyển trạng thái sang ngừng bán!");
							int ind = dsKetQua.indexOf(selectedThuoc);

							selectedThuoc.setTrangThai(false);
							dsKetQua.set(ind, selectedThuoc);
							modelTimKiem.setValueAt("Ngừng bán", tableTimKiem.getSelectedRow(), 10);

						} else
							UIConstant.showInfo(QuanLyThuocPanel.this, "Chuyển trạng thái thất bại!");
					}
				}
			}
		});

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(validDataThuoc()) {
					int row = tableTimKiem.getSelectedRow();
					if(txtMa.getText().trim().isEmpty()) {
						UIConstant.showWarning(QuanLyThuocPanel.this, "Bạn chưa chọn thuốc để cập nhật");
					}
					else {
						row += currentIndex;
						String maThuoc = txtMa.getText().trim();
						String tenThuoc = txtTen.getText().trim();
						String quyCachDongGoi = txtQCDG.getText().trim();

						String donViTinh = null;
						if(cbbDVT.getSelectedIndex() != -1)
							donViTinh = cbbDVT.getSelectedItem().toString();
						else 
							if(cbbDVT.getEditor().getItem().toString().trim() != "")
								donViTinh = cbbDVT.getEditor().getItem().toString().trim();

						Double donGia = Double.parseDouble(txtDG.getText());
						PhanLoai phanLoai = cbbPhanLoai.getSelectedIndex() == 0 ? PhanLoai.THUOC_BAN_THEO_DON:PhanLoai.THUOC_KHONG_THEO_DON;

						String nuocSanXuat = null;
						if(cbbNuocSX.getSelectedIndex() != -1)
							nuocSanXuat = cbbNuocSX.getSelectedItem().toString();
						else 
							if(cbbNuocSX.getEditor().getItem().toString().trim() != "")
								nuocSanXuat = cbbNuocSX.getEditor().getItem().toString().trim();

						String dangBaoChe = null;
						if(cbbDangBaoChe.getSelectedIndex() != -1)
							dangBaoChe = cbbDangBaoChe.getSelectedItem().toString();
						else 
							if(cbbDangBaoChe.getEditor().getItem().toString().trim() != "")
								dangBaoChe = cbbDangBaoChe.getEditor().getItem().toString().trim();

						Blob hinhAnh = null;
						if(txtHinhAnh.getText() != null) {
							try {
								FileInputStream inputStream = new FileInputStream(txtHinhAnh.getText());
								hinhAnh = new SerialBlob(Files.readAllBytes(new File(txtHinhAnh.getText().trim()).toPath()));
								inputStream.close();
							} catch (SQLException | IOException e1) {
								e1.printStackTrace();
							}
						}
						boolean trangThai = true;
						Thuoc thuoc = new Thuoc(tenThuoc, nhaCungCap, quyCachDongGoi, donViTinh, donGia, 
								phanLoai, dsHoatChat, nuocSanXuat, dangBaoChe, hinhAnh, dsLoThuoc, trangThai);
						thuoc.setMaThuoc(maThuoc);

						thuoc.setDsHoatChat(dsKetQua.get(row).getDsHoatChat());
						thuoc.setDsLoThuoc(dsKetQua.get(row).getDsLoThuoc());

						if(thuocDAO.capNhatThuoc(thuoc)) {
							UIConstant.showInfo(QuanLyThuocPanel.this, "Cập nhật thành công");
							dsKetQua.set(row + currentIndex, thuoc);
						} else {
							UIConstant.showWarning(QuanLyThuocPanel.this, "Cập nhật không thành công");
						}
					}
				}}
		});

		btnXoaTrangCapNhat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtMa.setText("Tự động tạo");
				txtTen.setText("");
				txtQCDG.setText("");
				cbbDVT.setSelectedIndex(0);
				cbbNuocSX.setSelectedIndex(0);
				cbbDangBaoChe.setSelectedIndex(0);
				txtDG.setText("");
				txtNCC.setText("");
				txtHinhAnh.setText("");
				txtTenHC.setText("");
				txtHamLuong.setText("");
				txtSL.setText("");
				dateHSD.setDate(null);
				txtHinhAnh.setText("");
				hoatChatModel.setRowCount(0);
				loThuocModel.setRowCount(0);
				selectedThuoc = null;
				nhaCungCap = null;

				ballTenThuoc.setVisible(false);
				ballDVT.setVisible(false);
				ballNCC.setVisible(false);
				ballNuocSX.setVisible(false);
				ballQCGD.setVisible(false);
				ballBaoChe.setVisible(false);
				ballDG.setVisible(false);
			}
		});
		btnXoaTrangTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtTenTK.setText("");
				txtQCDGTK.setText("");
				cbbDVTTK.setSelectedIndex(0);
				cbbNuocSXTK.setSelectedIndex(0);
				cbbDangBaoCheTK.setSelectedIndex(0);
				cbbPhanLoai.setSelectedIndex(0);

			}
		});
		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timThuoc();
			}
		});
		btnLuuHC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedThuoc == null) {
					UIConstant.showInfo(QuanLyThuocPanel.this, "Chưa chọn thuốc!");
					return;
				}

				if(validDataHoatChat()) {
					String tenHoatChat = txtTenHC.getText().trim();
					String hamLuong = txtHamLuong.getText().trim();

					HoatChat hc = new HoatChat(tenHoatChat, hamLuong);

					if(selectedThuoc.themHoatChat(hc)) {
						hoatChatModel.addRow(new Object[] {
								tenHoatChat, hamLuong
						});
						UIConstant.showInfo(QuanLyThuocPanel.this, "Lưu hoạt chất thành công");
					} else {
						for(int i = 0; i < selectedThuoc.getDsHoatChat().size(); i++) {
							if(selectedThuoc.getDsHoatChat().get(i).getTenHoatChat().equals(tenHoatChat)) {
								selectedThuoc.getDsHoatChat().set(i, hc);
								hoatChatModel.setValueAt(hc.getHamLuong(), i, 1);
								UIConstant.showInfo(QuanLyThuocPanel.this, "Lưu hoạt chất thành công");
								return;
							}
						}
						UIConstant.showWarning(QuanLyThuocPanel.this, "Lưu hoạt chất không thành công");
					}

				}}
		});
		btnLuuLT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedThuoc == null) {
					UIConstant.showInfo(QuanLyThuocPanel.this, "Chưa chọn thuốc!");
					return;
				}

				if(validDataLoThuoc()) {
					String maThuoc = txtMaLo.getText().trim();
					int soLuong = Integer.parseInt(txtSL.getText().trim());
					LocalDate hanSuDung = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateHSD.getDate()));

					LoThuoc lt = new LoThuoc(maThuoc,soLuong, hanSuDung, selectedThuoc);

					if(selectedThuoc.themLoThuoc(lt)) {
						selectedThuoc.getDsLoThuoc().add(lt);
						loThuocModel.addRow(new Object[] {
								lt.getMaLoThuoc(), lt.getSoLuong(), lt.getHanSuDung()
						});
						UIConstant.showInfo(QuanLyThuocPanel.this, "Lưu lô thuốc thành công");
					} else {
						for(int i = 0; i < selectedThuoc.getDsLoThuoc().size(); i++) {
							if(selectedThuoc.getDsLoThuoc().get(i).getMaLoThuoc().equals(lt.getMaLoThuoc())) {
								selectedThuoc.getDsLoThuoc().set(i, lt);
								loThuocModel.setValueAt(soLuong, i, 1);
								loThuocModel.setValueAt(hanSuDung.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), i, 2);
								UIConstant.showInfo(QuanLyThuocPanel.this, "Lưu lô thuốc thành công");
								return;
							}
						}
						UIConstant.showWarning(QuanLyThuocPanel.this, "Lưu lô thuốc không thành công");
					}

				}}
		});
		btnXoaHC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableHoatChat.getSelectedRow();
				if (row == -1) {
					UIConstant.showInfo(QuanLyThuocPanel.this, "Bạn chưa chọn hoạt chất để xóa");
					return;
				} else {
					int click = JOptionPane.showConfirmDialog(QuanLyThuocPanel.this,
							"Bạn có chắc chăn muốn xóa không ?", "Cảnh báo", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, new ImageIcon("Images//warning.png"));
					if (click == JOptionPane.YES_OPTION) {
						if (selectedThuoc.getDsHoatChat().remove(selectedThuoc.getDsHoatChat().get(click))) {
							UIConstant.showInfo(QuanLyThuocPanel.this, "Xóa thành công");
							hoatChatModel.removeRow(row);
						} else
							UIConstant.showInfo(QuanLyThuocPanel.this, "Xóa thất bại");
					} else
						return;
				}
			}
		});
		btnXoaLT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableLoThuoc.getSelectedRow();
				if (row == -1) {
					UIConstant.showInfo(QuanLyThuocPanel.this, "Bạn chưa chọn lô thuốc để xóa");
					return;
				} else {
					int click = JOptionPane.showConfirmDialog(QuanLyThuocPanel.this,
							"Bạn có chắc chăn muốn xóa không ?", "Cảnh báo", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, new ImageIcon("Images//warning.png"));
					if (click == JOptionPane.YES_OPTION) {
						if (selectedThuoc.getDsLoThuoc().remove(selectedThuoc.getDsLoThuoc().get(click))) {
							UIConstant.showInfo(QuanLyThuocPanel.this, "Xóa thành công");
							loThuocModel.removeRow(row);
						} else
							UIConstant.showInfo(QuanLyThuocPanel.this, "Xóa thất bại");
					} else
						return;
				}
			}
		});

		btnQuayLai.addActionListener((e) -> {
			mainFrame.changeCenter(mainFrame.getTrangChuPanel());
		});
		btnQuayLaiTK.addActionListener((e) -> {
			mainFrame.changeCenter(mainFrame.getTrangChuPanel());
		});
	}

	private void timThuoc() {
		String tenThuoc = txtTenTK.getText().trim();
		String quyCachDongGoi = txtQCDGTK.getText().trim();
		String donViTinh = cbbDVTTK.getSelectedItem().toString();
		String nuocSanXuat = cbbNuocSXTK.getSelectedItem().toString();
		String dangBaoChe = cbbDangBaoCheTK.getSelectedItem().toString();

		PhanLoai phanLoai = null;
		if(cbbPhanLoaiTK.getSelectedIndex() == 1)
			phanLoai = PhanLoai.THUOC_BAN_THEO_DON;
		if(cbbPhanLoaiTK.getSelectedIndex() == 2)
			phanLoai = PhanLoai.THUOC_KHONG_THEO_DON;

		dsKetQua = thuocDAO.timThuoc(tenThuoc, quyCachDongGoi, donViTinh, nuocSanXuat, dangBaoChe, phanLoai, -1);

		taiDuLieuLenBang(dsKetQua, 0);
	}

	private boolean validDataHoatChat() {
		String tenHoatChat = txtTenHC.getText().trim();
		String hamLuong = txtHamLuong.getText().trim();
		if (tenHoatChat.isEmpty()) {
			UIConstant.showWarning(QuanLyThuocPanel.this, "Tên hoạt chất không được rỗng");
			return false;
		}
		else if (hamLuong.isEmpty()) {
			UIConstant.showWarning(QuanLyThuocPanel.this, "Hàm lượng không được rỗng");
			return false;
		}
		return true;
	}

	private boolean validDataLoThuoc() {

		int soLuong = 0;
		try {

			soLuong = Integer.parseInt(txtSL.getText());
		}
		catch (Exception e) {
			UIConstant.showError(this, "Số lượng phải nhập số");
			return false;
		}

		Date date = dateHSD.getDate();
		if(date == null) {
			UIConstant.showError(this, "Hạn sử dụng không hợp lệ hoặc bị bỏ trống!");
			return false;
		}

		if (soLuong<0) {
			UIConstant.showWarning(QuanLyThuocPanel.this, "Số lượng phải lớn hơn 0 !!");
			return false;
		}

		return true;
	}

	private boolean validDataThuoc() {
		String tenThuoc = txtTen.getText().trim();
		String quyCachDongGoi = txtQCDG.getText().trim();
		String donViTinh = cbbDVT.getEditor().getItem().toString().trim();
		String nuocSanXuat = cbbNuocSX.getEditor().getItem().toString().trim();
		String dangBaoChe = cbbDangBaoChe.getEditor().getItem().toString().trim();

		Double donGia = 0.0;

		if (tenThuoc.isEmpty()) {
			ballTenThuoc.setVisible(true);

			return false;
		}else
			ballTenThuoc.setVisible(false);

		if (quyCachDongGoi.isEmpty()) {
			ballQCGD.setVisible(true);

			return false;
		}else
			ballQCGD.setVisible(false);

		if(donViTinh.isEmpty()) {
			ballDVT.setVisible(true);

			return false;
		}else
			ballDVT.setVisible(false);

		try {

			donGia = Double.parseDouble(txtDG.getText());
			if(donGia < 0) {
				ballDG.setVisible(true);
				return false;
			}
			ballDG.setVisible(false);
		}
		catch (Exception e) {
			ballDG.setVisible(true);

			return false;
		}

		if(nuocSanXuat.isEmpty()) {
			ballNuocSX.setVisible(true);

			return false;
		}else
			ballNuocSX.setVisible(false);

		if(dangBaoChe.isEmpty()) {
			ballBaoChe.setVisible(true);

			return false;
		}else
			ballBaoChe.setVisible(false);

		if(nhaCungCap == null) {
			ballNCC.setVisible(true);

			return false;
		}else
			ballNCC.setVisible(false);

		return true;
	}


	private void addNorthTimKiem() {
		Box boxM = Box.createHorizontalBox();
		Box boxNorth = Box.createVerticalBox();
		boxM.add(Box.createHorizontalStrut(5));
		boxM.add(boxNorth);
		boxM.add(Box.createHorizontalStrut(5));
		this.add(boxM, BorderLayout.NORTH);
		JLabel lbTenThuoc = new JLabel("Tên thuốc");
		JLabel lbQuyCachDongGoi = new JLabel("Quy cách đóng gói");
		JLabel lbDVT = new JLabel("Đơn vị tính");
		JLabel lbPhanLoai = new JLabel("Phân loại", JLabel.RIGHT);
		JLabel lbNuocSX = new JLabel("Nước sản xuất", JLabel.RIGHT);
		JLabel lbDangBaoChe = new JLabel("Dạng bào chế");

		lbTenThuoc.setFont(UIConstant.NORMAL_FONT);		lbTenThuoc.setPreferredSize(new Dimension(120,20));
		lbQuyCachDongGoi.setFont(UIConstant.NORMAL_FONT);		lbQuyCachDongGoi.setPreferredSize(new Dimension(120,20));
		lbDVT.setFont(UIConstant.NORMAL_FONT);		lbDVT.setPreferredSize(new Dimension(120,20));
		lbPhanLoai.setFont(UIConstant.NORMAL_FONT);		lbPhanLoai.setPreferredSize(new Dimension(60,20));
		lbNuocSX.setFont(UIConstant.NORMAL_FONT);		lbNuocSX.setPreferredSize(new Dimension(120,20));
		lbDangBaoChe.setFont(UIConstant.NORMAL_FONT);		lbDangBaoChe.setPreferredSize(new Dimension(120,20));

		txtTenTK = new JTextField(30); txtTenTK.setFont(UIConstant.NORMAL_FONT);
		txtQCDGTK = new JTextArea(); txtQCDGTK.setFont(UIConstant.NORMAL_FONT); 
		txtQCDGTK.setBorder(BorderFactory.createLineBorder(UIConstant.LINE_COLOR));
		txtQCDGTK.setLineWrap(true);
		txtQCDGTK.setWrapStyleWord(true);

		cbbDVTTK = new JComboBox<String>(); cbbDVTTK.setFont(UIConstant.NORMAL_FONT);
		donViTinhTKModel = new DefaultComboBoxModel<String>();
		cbbDVTTK.setModel(donViTinhTKModel);

		cbbNuocSXTK = new JComboBox<String>(); cbbNuocSXTK.setFont(UIConstant.NORMAL_FONT);
		nuocSXTKModel = new DefaultComboBoxModel<String>();
		cbbNuocSXTK.setModel(nuocSXTKModel);

		cbbDangBaoCheTK = new JComboBox<String>(); cbbDangBaoCheTK.setFont(UIConstant.NORMAL_FONT);
		dangBCTKModel = new DefaultComboBoxModel<String>();
		cbbDangBaoCheTK.setModel(dangBCTKModel);

		phanLoaiModelTK = new DefaultComboBoxModel<String>();
		phanLoaiModelTK.addElement("Tất cả");
		phanLoaiModelTK.addElement("Có kê đơn");
		phanLoaiModelTK.addElement("Không kê đơn");
		cbbPhanLoaiTK = new JComboBox<String>(phanLoaiModelTK); cbbPhanLoaiTK.setFont(UIConstant.NORMAL_FONT);

		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createHorizontalBox();

		boxNorth.add(Box.createVerticalStrut(10));
		boxNorth.add(box1);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box2);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box3);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box4);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box5);
		boxNorth.add(Box.createVerticalStrut(5));


		box1.add(lbTenThuoc); box1.add(Box.createHorizontalStrut(5));
		box1.add(txtTenTK); box1.add(Box.createHorizontalStrut(5));

		box2.add(lbQuyCachDongGoi); box2.add(Box.createHorizontalStrut(5));
		box2.add(txtQCDGTK); box2.add(Box.createHorizontalStrut(5));

		box3.add(lbDVT); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbDVTTK); box3.add(Box.createHorizontalStrut(5));
		box3.add(lbNuocSX); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbNuocSXTK); box3.add(Box.createHorizontalStrut(5));
		box3.add(lbPhanLoai); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbPhanLoaiTK); box3.add(Box.createHorizontalStrut(5));

		box4.add(lbDangBaoChe); box4.add(Box.createHorizontalStrut(5));
		box4.add(cbbDangBaoCheTK); box4.add(Box.createHorizontalStrut(5));

		btnTimKiem = new ColoredButton("Tìm", new ImageIcon("Images/search.png")); btnTimKiem.setFont(UIConstant.NORMAL_FONT);
		btnXoaTrangTimKiem = new ColoredButton("Xóa rỗng", new ImageIcon("Images//empty.png"));
		btnTimKiem.setBackground(UIConstant.PRIMARY_COLOR);
		btnXoaTrangTimKiem.setBackground(UIConstant.DANGER_COLOR);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(Box.createVerticalStrut(50));
		boxButton.add(btnTimKiem);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnXoaTrangTimKiem);
		boxButton.add(Box.createVerticalStrut(50));
		boxButton.add(Box.createHorizontalGlue());

		boxNorth.add(boxButton,BorderLayout.SOUTH);

		pnlTimKiem.add(boxM, BorderLayout.NORTH);

	}

	private void addNorthCapNhat() {
		Box boxM = Box.createHorizontalBox();
		Box boxNorth = Box.createVerticalBox();
		boxM.add(Box.createHorizontalStrut(5));
		boxM.add(boxNorth);
		boxM.add(Box.createHorizontalStrut(5));
		this.add(boxM, BorderLayout.NORTH);

		//	JLabel lbQL = new JLabel("Quản lý thuốc");
		JLabel lbMaThuoc = new JLabel("Mã thuốc"); 
		JLabel lbTenThuoc = new JLabel("Tên thuốc", JLabel.RIGHT);
		JLabel lbNhaCungCap = new JLabel("Nhà cung cấp");
		JLabel lbQuyCachDongGoi = new JLabel("Quy cách đóng gói");
		JLabel lbDVT = new JLabel("Đơn vị tính");
		JLabel lbDG = new JLabel("Đơn giá", JLabel.RIGHT);
		JLabel lbPhanLoai = new JLabel("Phân loại", JLabel.RIGHT);
		JLabel lbNuocSX = new JLabel("Nước sản xuất", JLabel.RIGHT);
		JLabel lbDangBaoChe = new JLabel("Dạng bào chế");
		JLabel lbHinhAnh = new JLabel("Hình ảnh");
		JLabel lbTrangThai = new JLabel("Trạng thái");
		JLabel lbNCC = new JLabel("Nhà cung cấp");
		JLabel lbDSHC = new JLabel("Danh sách hoạt chất");
		JLabel lbDSLT = new JLabel("Danh sách lô thuốc");
		JLabel lbVND = new JLabel("(VND)"); 

		lbMaThuoc.setFont(UIConstant.NORMAL_FONT);		lbMaThuoc.setPreferredSize(new Dimension(120,20));
		lbTenThuoc.setFont(UIConstant.NORMAL_FONT);		lbTenThuoc.setPreferredSize(new Dimension(80,20));
		lbNhaCungCap.setFont(UIConstant.NORMAL_FONT);	lbNhaCungCap.setPreferredSize(new Dimension(120,20));
		lbQuyCachDongGoi.setFont(UIConstant.NORMAL_FONT);		lbQuyCachDongGoi.setPreferredSize(new Dimension(120,20));
		lbDVT.setFont(UIConstant.NORMAL_FONT);		lbDVT.setPreferredSize(new Dimension(120,20));
		lbDG.setFont(UIConstant.NORMAL_FONT);		lbDG.setPreferredSize(new Dimension(60,20));
		lbPhanLoai.setFont(UIConstant.NORMAL_FONT);		lbPhanLoai.setPreferredSize(new Dimension(60,20));
		lbNuocSX.setFont(UIConstant.NORMAL_FONT);		lbNuocSX.setPreferredSize(new Dimension(100,20));
		lbDangBaoChe.setFont(UIConstant.NORMAL_FONT);		lbDangBaoChe.setPreferredSize(new Dimension(120,20));
		lbHinhAnh.setFont(UIConstant.NORMAL_FONT);		lbHinhAnh.setPreferredSize(new Dimension(120,20));
		lbTrangThai.setFont(UIConstant.NORMAL_FONT);		lbTrangThai.setPreferredSize(new Dimension(120,20));
		lbNCC.setFont(UIConstant.NORMAL_FONT);		lbNCC.setPreferredSize(new Dimension(120,20));
		lbDSHC.setFont(UIConstant.NORMAL_FONT);		lbDSHC.setPreferredSize(new Dimension(120,20));
		lbDSLT.setFont(UIConstant.NORMAL_FONT);		lbDSLT.setPreferredSize(new Dimension(120,20));
		lbVND.setFont(UIConstant.NORMAL_FONT);


		txtMa = new JTextField("Tự động tạo"); txtMa.setFont(UIConstant.NORMAL_FONT);  txtMa.setEnabled(false);
		txtTen = new JTextField(30); txtTen.setFont(UIConstant.NORMAL_FONT);
		txtQCDG = new JTextArea(); txtQCDG.setFont(UIConstant.NORMAL_FONT); 
		txtQCDG.setBorder(BorderFactory.createLineBorder(UIConstant.LINE_COLOR));
		txtQCDG.setLineWrap(true);
		txtQCDG.setWrapStyleWord(true);

		cbbDVT = new JComboBox<String>(); cbbDVT.setFont(UIConstant.NORMAL_FONT);
		cbbDVT.setEditable(true);
		donViTinhModel = new DefaultComboBoxModel<String>();
		cbbDVT.setModel(donViTinhModel);

		txtDG = new JTextField(); txtDG.setFont(UIConstant.NORMAL_FONT);

		cbbNuocSX = new JComboBox<String>(); cbbNuocSX.setFont(UIConstant.NORMAL_FONT);
		cbbNuocSX.setEditable(true);
		nuocSXModel = new DefaultComboBoxModel<String>();
		cbbNuocSX.setModel(nuocSXModel);

		cbbDangBaoChe = new JComboBox<String>(); cbbDangBaoChe.setFont(UIConstant.NORMAL_FONT);
		cbbDangBaoChe.setEditable(true);
		dangBaoCheModel = new DefaultComboBoxModel<String>();
		cbbDangBaoChe.setModel(dangBaoCheModel);

		txtHinhAnh = new JTextField();  txtHinhAnh.setFont(UIConstant.NORMAL_FONT);

		phanLoaiModel = new DefaultComboBoxModel<String>();
		phanLoaiModel.addElement("Có kê đơn");
		phanLoaiModel.addElement("Không kê đơn");
		cbbPhanLoai = new JComboBox<String>(phanLoaiModel); cbbPhanLoai.setFont(UIConstant.NORMAL_FONT);

		txtNCC = new JTextField(); txtNCC.setFont(UIConstant.NORMAL_FONT);
		btnChonNCC = new ColoredButton("Chọn nhà cung cấp"); btnChonNCC.setBackground(UIConstant.PRIMARY_COLOR);
		btnChonHinhAnh = new ColoredButton("Chọn hình ảnh"); btnChonHinhAnh.setBackground(UIConstant.PRIMARY_COLOR);
		btnChonHinhAnh.setPreferredSize(btnChonNCC.getPreferredSize());
		txtNCC.setEditable(false);
		txtHinhAnh.setEditable(false);

		ballTenThuoc = new BalloonTip(txtTen, "Tên thuốc không được rỗng!"); ballTenThuoc.setVisible(false); ballTenThuoc.setCloseButton(null);
		ballQCGD = new BalloonTip(txtQCDG, "Quy cách đóng gói thuốc không được rỗng!"); ballQCGD.setVisible(false); ballQCGD.setCloseButton(null);
		ballDVT = new BalloonTip(cbbDVT, "Đơn vị tính không được rỗng"); ballDVT.setVisible(false); ballDVT.setCloseButton(null);
		ballDG = new BalloonTip(txtDG, "Đơn giá phải là số và lớn hơn 0"); ballDG.setVisible(false); ballDG.setCloseButton(null);
		ballNuocSX = new BalloonTip(cbbNuocSX, "Nước sản xuất không được rỗng"); ballNuocSX.setVisible(false); ballNuocSX.setCloseButton(null);
		ballBaoChe = new BalloonTip(cbbDangBaoChe, "Dạng bào chế không được rỗng"); ballBaoChe.setVisible(false); ballBaoChe.setCloseButton(null);
		ballNCC = new BalloonTip(txtNCC, "Phải chọn nhà cung cấp!"); ballNCC.setVisible(false); ballNCC.setCloseButton(null);

		Box box0 = Box.createHorizontalBox();
		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createHorizontalBox();
		Box box6 = Box.createHorizontalBox();
		box0.add(Box.createHorizontalGlue());

		boxNorth.add(box0);
		boxNorth.add(Box.createVerticalStrut(10));
		boxNorth.add(box1);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box2);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box3);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box4);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box5);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box6);
		boxNorth.add(Box.createVerticalStrut(5));

		box1.add(lbMaThuoc); box1.add(Box.createHorizontalStrut(5));
		box1.add(txtMa); box1.add(Box.createHorizontalStrut(5));
		box1.add(lbTenThuoc); box1.add(Box.createHorizontalStrut(5));
		box1.add(txtTen); box1.add(Box.createHorizontalStrut(5));

		box2.add(lbQuyCachDongGoi); box2.add(Box.createHorizontalStrut(5));

		box2.add(txtQCDG); box2.add(Box.createHorizontalStrut(5));

		box3.add(lbDVT); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbDVT); box3.add(Box.createHorizontalStrut(5));
		box3.add(lbDG); box3.add(Box.createHorizontalStrut(5));
		box3.add(txtDG); box3.add(Box.createHorizontalStrut(2));
		box3.add(lbVND); box3.add(Box.createHorizontalStrut(5));
		box3.add(lbNuocSX); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbNuocSX); box3.add(Box.createHorizontalStrut(5));

		box4.add(lbDangBaoChe); box4.add(Box.createHorizontalStrut(5));
		box4.add(cbbDangBaoChe); box4.add(Box.createHorizontalStrut(5));
		box4.add(lbPhanLoai); box4.add(Box.createHorizontalStrut(5));
		box4.add(cbbPhanLoai); box4.add(Box.createHorizontalStrut(5));

		box5.add(lbNCC); box5.add(Box.createHorizontalStrut(5));
		box5.add(txtNCC); box5.add(Box.createHorizontalStrut(5));
		box5.add(btnChonNCC); box5.add(Box.createHorizontalStrut(5));

		box6.add(lbHinhAnh); box6.add(Box.createHorizontalStrut(5));
		box6.add(txtHinhAnh); box6.add(Box.createHorizontalStrut(5));
		box6.add(btnChonHinhAnh); box6.add(Box.createHorizontalStrut(5));

		pnlCapNhat.add(boxM,BorderLayout.NORTH);
	}

	private void addSouthCapNhat() {
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setOpaque(false);
		pnlCapNhat.add(pnlSouth, BorderLayout.SOUTH);

		btnNhapTuFile = new ColoredButton("Nhập thuốc từ file Excel", new ImageIcon("Images/add.png")); btnNhapTuFile.setFont(UIConstant.NORMAL_FONT);
		btnThem = new ColoredButton("Thêm thuốc", new ImageIcon("Images/add.png")); btnThem.setFont(UIConstant.NORMAL_FONT);
		btnSua = new ColoredButton("Sửa thông tin", new ImageIcon("Images/modify.png")); btnSua.setFont(UIConstant.NORMAL_FONT);
		btnXoaTrangCapNhat = new ColoredButton("Xóa rỗng", new ImageIcon("Images//empty.png"));
		btnQuayLai = new ColoredButton("Quay lại", new ImageIcon("Images//back.png"));

		btnSua.setBackground(UIConstant.WARNING_COLOR);
		btnNhapTuFile.setBackground(UIConstant.PRIMARY_COLOR);
		btnThem.setBackground(UIConstant.PRIMARY_COLOR);
		btnXoaTrangCapNhat.setBackground(UIConstant.DANGER_COLOR);
		btnQuayLai.setBackground(UIConstant.PRIMARY_COLOR);
		btnQuayLai.setFont(UIConstant.NORMAL_FONT);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(Box.createVerticalStrut(50));
		boxButton.add(btnThem);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnSua);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnNhapTuFile);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnXoaTrangCapNhat);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnQuayLai);
		boxButton.add(Box.createVerticalStrut(50));
		boxButton.add(Box.createHorizontalGlue());

		pnlSouth.add(boxButton, BorderLayout.SOUTH);
	}
	private void addCenterTimKiem() {
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		pnlCenter.setOpaque(false);

		this.add(pnlCenter, BorderLayout.CENTER);

		btnSuaTK = new ColoredButton("Sửa thông tin", new ImageIcon("Images/modify.png")); btnSua.setFont(UIConstant.NORMAL_FONT);
		btnXoa = new ColoredButton("Xoá", new ImageIcon("Images/delete.png")); btnXoa.setFont(UIConstant.NORMAL_FONT);
		btnQuayLaiTK = new ColoredButton("Quay lại", new ImageIcon("Images//back.png"));

		btnQuayLaiTK.setBackground(UIConstant.PRIMARY_COLOR);
		btnQuayLaiTK.setFont(UIConstant.NORMAL_FONT);

		btnXoa.setBackground(UIConstant.DANGER_COLOR);
		btnSuaTK.setBackground(UIConstant.WARNING_COLOR);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(Box.createVerticalStrut(10));
		boxButton.add(btnSuaTK);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnXoa);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnQuayLaiTK);
		boxButton.add(Box.createVerticalStrut(10));
		boxButton.add(Box.createHorizontalGlue());

		tableTimKiem = new CustomTable();
		tableTimKiem.setModel(modelTimKiem = new DefaultTableModel(new String[] {"Hình ảnh", "Mã thuốc", 
				"Tên thuốc", "Nhà cung cấp", "Đóng gói", "Dạng bào chế", "Đơn vị tính", 
				"Đơn giá", "Phân loại", "Nước sản xuất", "Trạng thái"}, 0));
		tableTimKiem.getColumn("Hình ảnh").setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if(column == 0) {
					if(value instanceof String)
						return new ImageItem(new ImageIcon(value.toString()));
					else
						return new ImageItem((byte[])value);
				}

				return (Component) value;

			}
		});
		tableTimKiem.setRowHeight(60);
		tableTimKiem.getColumn("Hình ảnh").setMaxWidth(80);
		tableTimKiem.getColumn("Hình ảnh").setMinWidth(80);
		tableTimKiem.setFont(UIConstant.NORMAL_FONT);

		JScrollPane scroll = new JScrollPane(tableTimKiem);
		scroll.getViewport().setBackground(Color.white);
		scroll.setBorder(BorderFactory.createTitledBorder("Danh sách thuốc"));

		lbPage = new JLabel("Trang 1 trong 1 trang."); lbPage.setFont(UIConstant.NORMAL_FONT);

		btnHome = new ColoredButton(new ImageIcon("Images/double_left.png")); btnHome.setBackground(UIConstant.LINE_COLOR);
		btnEnd = new ColoredButton(new ImageIcon("Images/double_right.png")); btnEnd.setBackground(UIConstant.LINE_COLOR);
		btnBefore = new ColoredButton(new ImageIcon("Images/left.png")); btnBefore.setBackground(UIConstant.LINE_COLOR);
		btnNext = new ColoredButton(new ImageIcon("Images/right.png")); btnNext.setBackground(UIConstant.LINE_COLOR);

		btnHome.setToolTipText("Trang đầu");
		btnEnd.setToolTipText("Trang cuối");
		btnBefore.setToolTipText("Trang trước");
		btnNext.setToolTipText("Trang kế tiếp");

		Box boxPage = Box.createHorizontalBox();
		boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnHome); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnBefore); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnNext); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnEnd); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(Box.createHorizontalGlue());
		boxPage.add(lbPage); boxPage.add(Box.createHorizontalStrut(5));

		pnlCenter.add(scroll);
		pnlCenter.add(boxPage);
		pnlCenter.add(boxButton);
		pnlTimKiem.add(pnlCenter,BorderLayout.CENTER);
	}

	private void addCenterCapNhat() {
		JPanel pnlList = new JPanel(new GridLayout(1, 2));
		pnlList.setOpaque(false);
		pnlList.setPreferredSize(new Dimension(100, 250));
		pnlCapNhat.add(pnlList, BorderLayout.CENTER);

		JPanel pnlHoatChat = new JPanel(new BorderLayout()); pnlHoatChat.setOpaque(false); pnlHoatChat.setOpaque(false);
		pnlHoatChat.setBorder(BorderFactory.createTitledBorder("Hoạt chất"));
		JPanel pnlLoThuoc = new JPanel(new BorderLayout()); pnlLoThuoc.setOpaque(false); pnlLoThuoc.setOpaque(false);
		pnlLoThuoc.setBorder(BorderFactory.createTitledBorder("Lô thuốc"));

		pnlList.add(pnlHoatChat);
		pnlList.add(pnlLoThuoc);

		JLabel lbTenHC = new JLabel("Tên hoạt chất"); lbTenHC.setFont(UIConstant.NORMAL_FONT);
		JLabel lbHamLuong = new JLabel("Hàm lượng"); lbHamLuong.setFont(UIConstant.NORMAL_FONT);
		JLabel lbMaLo = new JLabel("Mã lô thuốc"); lbMaLo.setFont(UIConstant.NORMAL_FONT);
		JLabel lbSoLuong = new JLabel("Số lượng"); lbSoLuong.setFont(UIConstant.NORMAL_FONT);
		JLabel lbHSD = new JLabel("Hạn sử dụng"); lbHSD.setFont(UIConstant.NORMAL_FONT);

		lbTenHC.setPreferredSize(new Dimension(100, 20));
		lbHamLuong.setPreferredSize(new Dimension(100, 20));
		lbMaLo.setPreferredSize(new Dimension(100, 20));
		lbSoLuong.setPreferredSize(new Dimension(100, 20));
		lbHSD.setPreferredSize(new Dimension(100, 20));

		txtTenHC = new JTextField(); txtTenHC.setFont(UIConstant.NORMAL_FONT);
		txtHamLuong = new JTextField(); txtHamLuong.setFont(UIConstant.NORMAL_FONT);
		txtMaLo = new JTextField(); txtMaLo.setFont(UIConstant.NORMAL_FONT);
		txtMaLo.setEnabled(false);
		txtSL = new JTextField(); txtSL.setFont(UIConstant.NORMAL_FONT);
		dateHSD = new JDateChooser(new Date()); dateHSD.setFont(UIConstant.NORMAL_FONT); dateHSD.setDateFormatString("dd-MM-yyyy");

		btnLuuHC = new ColoredButton("Lưu hoạt chất", new ImageIcon("Images/add.png")); btnLuuHC.setBackground(UIConstant.PRIMARY_COLOR);
		btnXoaHC = new ColoredButton("Xóa hoạt chất", new ImageIcon("Images/delete.png")); btnXoaHC.setBackground(UIConstant.DANGER_COLOR);
		btnLuuLT = new ColoredButton("Lưu lô thuốc", new ImageIcon("Images/add.png")); btnLuuLT.setBackground(UIConstant.PRIMARY_COLOR);
		btnXoaLT = new ColoredButton("Xóa lô thuốc", new ImageIcon("Images/delete.png")); btnXoaLT.setBackground(UIConstant.DANGER_COLOR);

		Box boxButtonHC = Box.createVerticalBox();
		Box boxButtonLT = Box.createVerticalBox();

		Box boxHC = Box.createVerticalBox();
		Box boxLT = Box.createVerticalBox();

		Box boxHC1 = Box.createHorizontalBox();
		boxHC1.add(Box.createHorizontalStrut(5)); boxHC1.add(lbTenHC); boxHC1.add(txtTenHC); boxHC1.add(Box.createHorizontalStrut(5));
		Box boxHC2 = Box.createHorizontalBox();
		boxHC2.add(Box.createHorizontalStrut(5)); boxHC2.add(lbHamLuong); boxHC2.add(txtHamLuong); boxHC2.add(Box.createHorizontalStrut(5));
		Box boxHC3 = Box.createHorizontalBox();
		boxHC3.add(Box.createHorizontalGlue()); boxHC3.add(btnLuuHC); boxHC3.add(Box.createHorizontalGlue()); boxHC3.add(btnXoaHC); boxHC3.add(Box.createHorizontalGlue());
		boxHC.add(Box.createVerticalStrut(5));
		boxHC.add(boxHC1);
		boxHC.add(Box.createVerticalStrut(5));
		boxHC.add(boxHC2);
		boxHC.add(Box.createVerticalStrut(5));
		boxHC.add(boxHC3);
		boxHC.add(Box.createVerticalStrut(5));

		Box boxLT1 = Box.createHorizontalBox();
		boxLT1.add(Box.createHorizontalStrut(5)); boxLT1.add(lbMaLo); boxLT1.add(txtMaLo); boxLT1.add(Box.createHorizontalStrut(5));
		Box boxLT2 = Box.createHorizontalBox();
		boxLT2.add(Box.createHorizontalStrut(5)); boxLT2.add(lbSoLuong); boxLT2.add(txtSL); boxLT2.add(Box.createHorizontalStrut(3)); 
		boxLT2.add(lbHSD); boxLT2.add(dateHSD); boxLT2.add(Box.createHorizontalStrut(5));
		Box boxLT3 = Box.createHorizontalBox();
		boxLT3.add(Box.createHorizontalGlue()); boxLT3.add(btnLuuLT); boxLT3.add(Box.createHorizontalGlue()); boxLT3.add(btnXoaLT); boxLT3.add(Box.createHorizontalGlue());
		boxLT.add(Box.createVerticalStrut(5));
		boxLT.add(boxLT1);
		boxLT.add(Box.createVerticalStrut(5));
		boxLT.add(boxLT2);
		boxLT.add(Box.createVerticalStrut(5));
		boxLT.add(boxLT3);
		boxLT.add(Box.createVerticalStrut(5));

		tableHoatChat = new CustomTable(hoatChatModel = new DefaultTableModel(new String[] {"Tên hoạt chất", "Hàm lượng"}, 0));

		JScrollPane scrollHC = new JScrollPane(tableHoatChat);
		scrollHC.getViewport().setBackground(Color.white);
		scrollHC.setBorder(BorderFactory.createTitledBorder("Danh sách hoạt chất"));

		tableLoThuoc = new CustomTable(loThuocModel = new DefaultTableModel(new String[] {"Mã lô", "Số lượng", "Hạn sử dụng"}, 0));

		JScrollPane scrollLT = new JScrollPane(tableLoThuoc);
		scrollLT.getViewport().setBackground(Color.white);
		scrollLT.setBorder(BorderFactory.createTitledBorder("Danh sách lô thuốc"));

		pnlHoatChat.add(boxHC, BorderLayout.NORTH);
		pnlLoThuoc.add(boxLT, BorderLayout.NORTH);

		pnlHoatChat.add(boxButtonHC, BorderLayout.EAST);
		pnlLoThuoc.add(boxButtonLT, BorderLayout.EAST);

		pnlHoatChat.add(scrollHC, BorderLayout.CENTER);
		pnlLoThuoc.add(scrollLT, BorderLayout.CENTER);

	}

	public void setNhaCungCapDaChon(NhaCungCap nhaCungCap) 
	{
		this.nhaCungCap = nhaCungCap;
		txtNCC.setText(nhaCungCap.getTenNCC());
	}

	private void setLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals("Windows")) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
					break;

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}

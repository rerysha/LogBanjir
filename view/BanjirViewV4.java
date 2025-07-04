package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.BanjirController;
import model.Banjir;
import java.awt.*;

public class BanjirViewV4 extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField tfLokasi, tfTanggal, tfKedalaman;
    private JTable table;
    private DefaultTableModel tableModel;
    private BanjirController controller = new BanjirController();

    public BanjirViewV4() {
        setTitle("Log Banjir - Aplikasi Peringatan Bencana");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 0, 10));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(new Color(0x1565C0));

        JLabel appTitle = new JLabel("🌊 LogBanjir", SwingConstants.CENTER);
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sidebar.add(appTitle);

        // Menu Buttons
        String[] menuItems = {"Dashboard", "Laporan", "Pengaturan", "Logout"};
        for (String menu : menuItems) {
            JButton btn = new JButton(menu);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0x1976D2));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));

            btn.addActionListener(e -> {
                if (menu.equals("Logout")) {
                    System.exit(0);
                } else {
                    cardLayout.show(cardPanel, menu);
                }
            });

            sidebar.add(btn);
        }
        add(sidebar, BorderLayout.WEST);

        // CardLayout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // ========== DASHBOARD ==========
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input Banjir"));

        tfLokasi = new JTextField();
        tfTanggal = new JTextField();
        tfKedalaman = new JTextField();

        formPanel.add(new JLabel("Lokasi:"));
        formPanel.add(tfLokasi);
        formPanel.add(new JLabel("Tanggal:"));
        formPanel.add(tfTanggal);
        formPanel.add(new JLabel("Kedalaman (cm):"));
        formPanel.add(tfKedalaman);

        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");

        formPanel.add(btnAdd);
        formPanel.add(btnUpdate);
        dashboardPanel.add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Lokasi", "Tanggal", "Kedalaman"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Banjir"));
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnDelete);
        dashboardPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Table Events
        btnAdd.addActionListener(e -> {
            try {
                String lokasi = tfLokasi.getText();
                String tanggal = tfTanggal.getText();
                int kedalaman = Integer.parseInt(tfKedalaman.getText());
                controller.tambahData(new Banjir(lokasi, tanggal, kedalaman));
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    String lokasi = tfLokasi.getText();
                    String tanggal = tfTanggal.getText();
                    int kedalaman = Integer.parseInt(tfKedalaman.getText());
                    controller.updateData(row, new Banjir(lokasi, tanggal, kedalaman));
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Input tidak valid!");
                }
            }
        });

        btnDelete.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row >= 0) {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.hapusData(row);
            refreshTable();
            // Kosongkan input setelah hapus
            tfLokasi.setText("");
            tfTanggal.setText("");
            tfKedalaman.setText("");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih baris data terlebih dahulu sebelum menghapus.");
    }
});


        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfLokasi.setText(tableModel.getValueAt(row, 0).toString());
                tfTanggal.setText(tableModel.getValueAt(row, 1).toString());
                tfKedalaman.setText(tableModel.getValueAt(row, 2).toString());
            }
        });

        // ========== LAPORAN ==========
        JPanel laporanPanel = new JPanel(new BorderLayout());
        JLabel lblLaporan = new JLabel("📈 Laporan Banjir akan ditampilkan di sini.", SwingConstants.CENTER);
        lblLaporan.setFont(new Font("SansSerif", Font.PLAIN, 16));
        laporanPanel.add(lblLaporan, BorderLayout.CENTER);
        

        // ========== PENGATURAN ==========
        JPanel pengaturanPanel = new JPanel(new BorderLayout());
        JLabel lblSetting = new JLabel("⚙️ Pengaturan aplikasi akan muncul di sini.", SwingConstants.CENTER);
        lblSetting.setFont(new Font("SansSerif", Font.PLAIN, 16));
        pengaturanPanel.add(lblSetting, BorderLayout.CENTER);

        // Add Panels to Card Panel
        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(laporanPanel, "Laporan");
        cardPanel.add(pengaturanPanel, "Pengaturan");

        add(cardPanel, BorderLayout.CENTER);

        // Show Dashboard by default
        cardLayout.show(cardPanel, "Dashboard");
        refreshTable();
        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Banjir b : controller.getAllData()) {
            tableModel.addRow(new Object[]{b.getLokasi(), b.getTanggal(), b.getKedalaman()});
        }
    }
}

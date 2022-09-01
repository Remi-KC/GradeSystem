package tw.com.herpestesurva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GradeSystem extends JFrame implements ActionListener {

    private JTextField nameText; // 小文字輸入框
    private JTextField chineseText;
    private JTextField mathText;
    private JTextField englishText;
    private JButton addBtn;
    private JButton deleteBtn;
    private JTextArea outputArea; // 長文字輸入框
    private JButton newBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton clearBtn;
    private JCheckBox appendCbx;

    private Font font1;
    private Font font2;
    private Font font3;
    private Font font4;

    private final int WIDTH = 750;
    private final int HEIGHT = 600;
    private final String TITLE = "學生成績輸入系統";

    private String fileName;

    GradeSystem() {

        font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        font2 = new Font(Font.MONOSPACED, Font.PLAIN, 24);
        font3 = new Font(Font.MONOSPACED, Font.PLAIN, 28);
        font4 = new Font(Font.MONOSPACED, Font.BOLD, 20);

        // 畫面置中
        java.awt.Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screen.width - WIDTH) / 2, (screen.height - HEIGHT) / 2, WIDTH, HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JFrame佈局樣式
        setLayout(null);
        // 左邊樣式佈局
        initLeftPanel();
        // 右邊樣式佈局
        initRightPanel();

    }

    private void initLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBounds(10, 10, (int) (WIDTH * 0.35), HEIGHT - 60);
        // panel.setBackground(Color.ORANGE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "輸入"));

        add(panel);

        // 增加Label
        panel.add(getLabel("姓名", font4));
        nameText = gTextField("請輸入姓名", 14, font2);
        nameText.setText("Remi");
        panel.add(nameText);

        panel.add(getLabel("國文", font4));
        chineseText = gTextField("請輸入國文分數", 14, font2);
        chineseText.setText("88");
        panel.add(chineseText);

        panel.add(getLabel("英文", font4));
        englishText = gTextField("請輸入英文分數", 14, font2);
        englishText.setText("100");
        panel.add(englishText);

        panel.add(getLabel("數學", font4));
        mathText = gTextField("請輸入數學分數", 14, font2);
        mathText.setText("100");
        panel.add(mathText);

        addBtn = new JButton("增加");
        addBtn.setFont(font3);
        addBtn.addActionListener(this);
        panel.add(addBtn);

        deleteBtn = new JButton("清空");
        deleteBtn.setFont(font3);
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

    }

    private void initRightPanel() {
        JPanel panel = new JPanel(); // JPanel用完就可以丟掉所以可以重複使用
        panel.setBounds(10 + (int) (WIDTH * 0.35), 10, (int) (WIDTH * 0.63), HEIGHT - 120);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "資料"));
        add(panel);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 20, (int) (WIDTH * 0.6), HEIGHT - 150);
        outputArea.setFont(font4);

        JScrollPane scrollPane = new JScrollPane(outputArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 20, (int) (WIDTH * 0.6), HEIGHT - 150);
        panel.add(scrollPane);

        panel = new JPanel();
        panel.setBounds(10 + (int) (WIDTH * 0.35), HEIGHT - 110, (int) (WIDTH * 0.63), 80);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(panel);

        newBtn = new JButton("新檔");
        newBtn.setFont(font2);
        newBtn.addActionListener(this);
        panel.add(newBtn);

        saveBtn = new JButton("儲存");
        saveBtn.setFont(font2);
        saveBtn.addActionListener(this);
        panel.add(saveBtn);

        loadBtn = new JButton("讀取");
        loadBtn.setFont(font2);
        loadBtn.addActionListener(this);
        panel.add(loadBtn);

        clearBtn = new JButton("清除");
        clearBtn.setFont(font2);
        clearBtn.addActionListener(this);
        panel.add(clearBtn);

        appendCbx = new JCheckBox("附加");
        appendCbx.setFont(font1);
        appendCbx.setSelected(true);
        panel.add(appendCbx);

    }

    private JLabel getLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.setFont(font);
        }

        return label;
    }

    private JTextField gTextField(String toolTipText, int columns, Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setColumns(columns);
        field.setToolTipText(toolTipText);
        field.setHorizontalAlignment(JTextField.CENTER);

        return field;
    }

    // 檢查是否是數值
    private boolean isNumber(String text) {
        try {
            Double.valueOf(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 建立新檔
    private void newFile() {
        FileDialog fd = new FileDialog(this, "選擇儲存位置", FileDialog.SAVE);
        fd.setVisible(true);

        String path = fd.getDirectory();
        String filename = fd.getFile();

        try {
            if (new File(path, filename).createNewFile()) {
                JOptionPane.showMessageDialog(null, filename + "建立成功");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "檔案建立失敗!");
            System.out.println(e);
        }

    }

    //
    private void open() {
        FileDialog fd = new FileDialog(this, "選擇檔案", FileDialog.LOAD);
        fd.setDirectory("");
        fd.setFile("*.csv");
        fd.setVisible(true);

        String filename = fd.getFile();
        if (filename == null) {
            JOptionPane.showMessageDialog(null, "讀取檔案失敗!");
        } else {
            fileName = fd.getDirectory() + fd.getFile();
            this.setTitle(TITLE + " 編修檔案:" + fd.getFile());
            load(fileName);
        }
    }

    private void load(String filename) {
        File file = new File(filename);
        FileReader fileReader = null;
        BufferedReader br = null;

        if (file.exists()) {
            try {
                fileReader = new FileReader(filename);
                br = new BufferedReader(fileReader);
                String line;
                StringBuilder sb = new StringBuilder();
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (++count == 1) {
                        continue;
                    }
                    sb.append(line).append("\n");
                }
                outputArea.setText(sb.toString());

            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "讀取檔案失敗!");
            } finally {

                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }

                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }

    }

    // 儲存檔案
    private void save(String filename, String text, boolean append) {
        FileWriter fileWriter = null;
        // 新增檔案偵測
        File file = new File(filename);
        boolean exits = file.exists();

        try {
            fileWriter = new FileWriter(filename, append);
            // 不附加or檔案不存在狀態.寫入表頭
            if (!append || !exits) {
                fileWriter.write("姓名,國文,英文,數學\n");
            }
            fileWriter.write(text);
            JOptionPane.showMessageDialog(null, "寫入資料成功!");
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "寫入資料失敗!");
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    // 清空輸入區
    private void clearJTextField() {
        nameText.setText("");
        chineseText.setText("");
        englishText.setText("");
        mathText.setText("");
    }

    public void run(boolean visible) {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newBtn) {
            newFile();
            return;
        }

        if (e.getSource() == loadBtn) {
            open();
            return;
        }

        if (e.getSource() == saveBtn) {
            if (fileName == null) {
                JOptionPane.showMessageDialog(null, "請先讀取檔案");
                return;
            }

            String text = outputArea.getText();
            if (text.equals("")) {
                JOptionPane.showMessageDialog(null, "目前資料為空，請先輸入資料");
                return;
            }

            save(fileName, text, appendCbx.isSelected());
            return;
        }

        if (e.getSource() == clearBtn) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "確定清除所有文字嗎?", "警告", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                outputArea.setText("");
            }
            return;

        }

        if (e.getSource() == deleteBtn) {
            clearJTextField();
            return;
        }

        if (e.getSource() == addBtn) { // 利用物件記憶體位置相等做判斷
            // System.out.println("增加資料!");
            JTextField[] fields = { nameText, chineseText, mathText, englishText };// 重點步驟
            // boolean add = true;
            // 檢查輸入是否為空
            for (JTextField field : fields) {
                if (field.getText().equals("")) { // .getText()會清空後寫入
                    JOptionPane.showMessageDialog(null, "資料輸入不能為空");
                    // add = false;
                    // break;
                    return;
                }
            }
            // 檢查輸入數值是否正確
            for (int i = 1; i < fields.length; i++) {
                if (!isNumber(fields[i].getText())) {
                    JOptionPane.showMessageDialog(null, "分數輸入有誤");
                    // add = false;
                    // break;
                    return;
                }

            }
            // 輸出到outputArea
            StringBuilder sb = new StringBuilder();
            for (JTextField field : fields) {
                sb.append(field.getText()).append(",");
            }
            // 移除最後逗點並加上換行符號
            sb.setLength(sb.length() - 1);
            sb.append("\n");
            outputArea.setText(outputArea.getText() + sb.toString());// 原本存在的資料再加上新增的資料一起顯示
            // 清空輸入區
            // clearJTextField();
        }

    }

}

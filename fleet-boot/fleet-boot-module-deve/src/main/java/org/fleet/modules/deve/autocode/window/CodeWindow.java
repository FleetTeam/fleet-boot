package org.fleet.modules.deve.autocode.window;

import org.fleet.modules.deve.autocode.database.DbReadTableUtil;
import org.fleet.modules.deve.autocode.generate.impl.CodeGenerateOne;
import org.fleet.modules.deve.autocode.generate.pojo.TableVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodeWindow
        extends JFrame {
    private static final long b = -5324160085184088010L;
    private static String entityPackage = "test";
    private static String entityName = "TestEntity";
    private static String tableName = "t00_company";
    private static String ftlDescription = "分公司";
    private static int fieldRowNum = 1;
    private static String primaryKeyPolicy = "uuid";
    private static String sequenceCode = "";

    String[] planets = new String[]{"uuid", "identity", "sequence"};

    public CodeWindow() {
        JPanel jp = new JPanel();
        setContentPane(jp);
        jp.setLayout(new GridLayout(14, 2));

        JLabel infolbl = new JLabel("提示:");
        JLabel showlbl = new JLabel();
        JLabel packagebl = new JLabel("包名（小写）：");
        JTextField packagefld = new JTextField();
        JLabel entitylbl = new JLabel("实体类名（首字母大写）：");
        JTextField entityfld = new JTextField();
        JLabel tablejbl = new JLabel("表名：");
        JTextField tablefld = new JTextField(20);

        JLabel tablekeyjbl = new JLabel("主键生成策略：");
        JComboBox<String> tablekeyfld = new JComboBox<>(this.planets);

        tablekeyfld.setEnabled(false);
        JLabel sequence_lb = new JLabel("主键SEQUENCE：(oracle序列名)");
        JTextField sequence_fld = new JTextField(20);

        JLabel titlelbl = new JLabel("功能描述：");
        JTextField titlefld = new JTextField();

        JLabel fieldRowNumlbl = new JLabel("行字段数目：");
        JTextField fieldRowNumfld = new JTextField();
        fieldRowNumfld.setText(fieldRowNum + "");

        ButtonGroup bg = new ButtonGroup();
        JRadioButton tableStlye = new JRadioButton("抽屉风格表单");
        tableStlye.setSelected(true);
        JRadioButton skipStlye = new JRadioButton("弹窗风格表单");
        bg.add(tableStlye);
        bg.add(skipStlye);

        JCheckBox controlButton = new JCheckBox("Control");
        controlButton.setSelected(true);
        JCheckBox vueButton = new JCheckBox("Vue");
        vueButton.setSelected(true);
        JCheckBox serviceIButton = new JCheckBox("Service");
        serviceIButton.setSelected(true);
        JCheckBox mapperButton = new JCheckBox("Mapper.xml");
        mapperButton.setSelected(true);
        JCheckBox daoButton = new JCheckBox("Dao");
        daoButton.setSelected(true);
        JCheckBox entityButtong = new JCheckBox("Entity");
        entityButtong.setSelected(true);

        jp.add(infolbl);
        jp.add(showlbl);
        jp.add(packagebl);
        jp.add(packagefld);
        jp.add(entitylbl);
        jp.add(entityfld);
        jp.add(tablejbl);
        jp.add(tablefld);

        jp.add(tablekeyjbl);
        jp.add(tablekeyfld);

        jp.add(sequence_lb);
        jp.add(sequence_fld);

        jp.add(titlelbl);
        jp.add(titlefld);
        jp.add(fieldRowNumlbl);
        jp.add(fieldRowNumfld);

        jp.add(controlButton);
        jp.add(vueButton);
        jp.add(serviceIButton);
        jp.add(mapperButton);
        jp.add(daoButton);
        jp.add(entityButtong);

        jp.add(tableStlye);
        jp.add(skipStlye);

        JButton confirmbtn = new JButton("生成");
        confirmbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(packagefld.getText())) {
                    entityPackage = packagefld.getText();
                } else {
                    showlbl.setForeground(Color.red);
                    showlbl.setText("包名不能为空！");
                    return;
                }
                if (!"".equals(entityfld.getText())) {
                    entityName = entityfld.getText();
                } else {
                    showlbl.setForeground(Color.red);
                    showlbl.setText("实体类名不能为空！");
                    return;
                }
                if (!"".equals(titlefld.getText())) {
                    ftlDescription = titlefld.getText();
                } else {
                    showlbl.setForeground(Color.red);
                    showlbl.setText("描述不能为空！");
                    return;
                }
                if (!"".equals(tablefld.getText())) {
                    tableName = tablefld.getText();
                } else {
                    showlbl.setForeground(Color.red);
                    showlbl.setText("表名不能为空！");

                    return;
                }

                primaryKeyPolicy = (String) tablekeyfld.getSelectedItem();

                if (primaryKeyPolicy.equals("sequence")) {
                    if (!"".equals(sequence_fld.getText())) {
                        sequenceCode = sequence_fld.getText();
                    } else {
                        showlbl.setForeground(Color.red);
                        showlbl.setText("主键生成策略为sequence时，序列号不能为空！");
                        return;
                    }
                }

                try {
                    boolean bool = DbReadTableUtil.checkTableExist(tableName);
                    if (bool) {

                        TableVo tableVo = new TableVo();
                        tableVo.setTableName(tableName);
                        tableVo.setPrimaryKeyPolicy(primaryKeyPolicy);
                        tableVo.setEntityPackage(entityPackage);
                        tableVo.setEntityName(entityName);
                        tableVo.setFieldRowNum(fieldRowNum);
                        tableVo.setSequenceCode(sequenceCode);
                        tableVo.setFtlDescription(ftlDescription);
                        (new CodeGenerateOne(tableVo)).generateCodeFile(null);
                        showlbl.setForeground(Color.red);
                        showlbl.setText("成功生成增删改查->功能：" + ftlDescription);
                    } else {
                        showlbl.setForeground(Color.red);
                        showlbl.setText("表[" + tableName + "] 在数据库中，不存在");
                        System.err.println(" ERROR ：   表 [ " + tableName + " ] 在数据库中，不存数据源配置是否配置正确、表名是否填写正确~ ");
                    }

                } catch (Exception exception) {
                    showlbl.setForeground(Color.red);
                    showlbl.setText(exception.getMessage());
                }
            }
        });

        JButton extbtn = new JButton("退出");
        extbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        jp.add(confirmbtn);
        jp.add(extbtn);

        setTitle("Fleet代码生成器[单表模型]");
        setVisible(true);
        setDefaultCloseOperation(3);
        setSize(new Dimension(600, 400));
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        try {
            (new CodeWindow()).pack();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}


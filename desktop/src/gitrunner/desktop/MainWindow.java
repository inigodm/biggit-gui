package gitrunner.desktop;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import gitrunner.desktop.utils.JTextAreOutputStream;
import inigo.gitgui.git.Git;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;

public class MainWindow {
    private Git git;
    private OutputStream logOs;

    public MainWindow(Git git) {
        this.git = git;
        initComponents();
        logOs = new JTextAreOutputStream(taLog);
        git.setOutStream(logOs);
    }

    private void mnExitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void mnCloneActionPerformed(ActionEvent e) {
        String res = git.status();
        try {
            logOs.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        JFrame panel1 = new JFrame();
        panel3 = new JPanel();
        menuMain = new JMenuBar();
        mnuFile = new JMenu();
        mnClone = new JMenuItem();
        mnOpen = new JMenuItem();
        mnCreate = new JMenuItem();
        mnExit = new JMenuItem();
        panel5 = new JPanel();
        splitPane2 = new JSplitPane();
        panel4 = new JPanel();
        splitPane1 = new JSplitPane();
        panel2 = new JPanel();
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        taLog = new JTextArea();
        gitTree = new JScrollPane();
        taGitTree = new JTextArea();
        toolBar1 = new JToolBar();

        //======== panel1 ========
        {
          panel1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          panel1.setTitle("GitGui");
          Container panel1ContentPane = panel1.getContentPane();
          panel1ContentPane.setLayout(new BoxLayout(panel1ContentPane, BoxLayout.Y_AXIS));

          //======== panel3 ========
          {
            panel3.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
            0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
            . BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
            red) ,panel3. getBorder( )) ); panel3. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
            beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

            //======== menuMain ========
            {
              menuMain.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
              menuMain.setMaximumSize(null);

              //======== mnuFile ========
              {
                mnuFile.setText("File");
                mnuFile.setAlignmentX(0.0F);
                mnuFile.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                mnuFile.setHorizontalAlignment(SwingConstants.RIGHT);
                mnuFile.setHorizontalTextPosition(SwingConstants.RIGHT);

                //---- mnClone ----
                mnClone.setText("Clone repository");
                mnClone.addActionListener(e -> mnCloneActionPerformed(e));
                mnuFile.add(mnClone);

                //---- mnOpen ----
                mnOpen.setText("Open repository");
                mnuFile.add(mnOpen);

                //---- mnCreate ----
                mnCreate.setText("Create Repository");
                mnuFile.add(mnCreate);
                mnuFile.addSeparator();

                //---- mnExit ----
                mnExit.setText("Exit GitGUI");
                mnExit.addActionListener(e -> mnExitActionPerformed(e));
                mnuFile.add(mnExit);
              }
              menuMain.add(mnuFile);
            }
            panel3.add(menuMain);
          }
          panel1ContentPane.add(panel3);

          //======== panel5 ========
          {
            panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));

            //======== splitPane2 ========
            {
              splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
              splitPane2.setContinuousLayout(true);
              splitPane2.setOneTouchExpandable(true);

              //======== panel4 ========
              {
                panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

                //======== splitPane1 ========
                {
                  splitPane1.setContinuousLayout(true);
                  splitPane1.setOneTouchExpandable(true);
                  splitPane1.setDoubleBuffered(true);
                  splitPane1.setAutoscrolls(true);
                  splitPane1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                  splitPane1.setPreferredSize(new Dimension(89, 58));
                }
                panel4.add(splitPane1);
              }
              splitPane2.setTopComponent(panel4);

              //======== panel2 ========
              {
                panel2.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));

                //======== tabbedPane1 ========
                {

                  //======== scrollPane1 ========
                  {

                    //---- taLog ----
                    taLog.setLineWrap(true);
                    taLog.setWrapStyleWord(true);
                    taLog.setEditable(false);
                    scrollPane1.setViewportView(taLog);
                  }
                  tabbedPane1.addTab("Log", scrollPane1);

                  //======== gitTree ========
                  {

                    //---- taGitTree ----
                    taGitTree.setLineWrap(true);
                    taGitTree.setWrapStyleWord(true);
                    taGitTree.setEditable(false);
                    gitTree.setViewportView(taGitTree);
                  }
                  tabbedPane1.addTab("Tree", gitTree);
                }
                panel2.add(tabbedPane1, new GridConstraints(2, 0, 1, 2,
                  GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                  GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                  GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                  null, null, null));
              }
              splitPane2.setBottomComponent(panel2);
            }
            panel5.add(splitPane2);
          }
          panel1ContentPane.add(panel5);
          panel1ContentPane.add(toolBar1);
          panel1.setSize(800, 735);
          panel1.setLocationRelativeTo(panel1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        panel1.setExtendedState(Frame.MAXIMIZED_BOTH);
        panel1.show();
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel3;
    private JMenuBar menuMain;
    private JMenu mnuFile;
    private JMenuItem mnClone;
    private JMenuItem mnOpen;
    private JMenuItem mnCreate;
    private JMenuItem mnExit;
    private JPanel panel5;
    private JSplitPane splitPane2;
    private JPanel panel4;
    private JSplitPane splitPane1;
    private JPanel panel2;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JTextArea taLog;
    private JScrollPane gitTree;
    private JTextArea taGitTree;
    private JToolBar toolBar1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}

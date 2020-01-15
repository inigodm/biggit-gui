package git.desktop;


import com.intellij.uiDesigner.core.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import git.desktop.utils.JTextAreOutputStream;
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
        git.status();
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
        panel4 = new JPanel();
        panel2 = new JPanel();
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        taLog = new JTextArea();

        //======== panel1 ========
        {
            panel1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            panel1.setTitle("GitGui");
            Container panel1ContentPane = panel1.getContentPane();
            panel1ContentPane.setLayout(new BoxLayout(panel1ContentPane, BoxLayout.Y_AXIS));

            //======== panel3 ========
            {
                panel3.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
                ( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border
                . TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
                . Color. red) ,panel3. getBorder( )) ); panel3. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
                propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
                ; }} );
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

            //======== panel4 ========
            {
                panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
            }
            panel1ContentPane.add(panel4);

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
                }
                panel2.add(tabbedPane1, new GridConstraints(2, 0, 1, 2,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            panel1ContentPane.add(panel2);
            panel1.setSize(640, 600);
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
    private JPanel panel4;
    private JPanel panel2;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JTextArea taLog;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}

/*
 * @(#)Main.java   13/09/22
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
Released under the FreeBSD  license 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
 *
 *
 *
 */


package persvcs;

//~--- non-JDK imports --------------------------------------------------------

import com.xzq.osc.JocTable;

import javaxt.io.Directory;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static java.beans.Beans.isDesignTime;

import static java.util.Collections.emptyList;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILE_FILTER_CHANGED_PROPERTY;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 12:29 PM
 * Original Project: PersVcs
 */
public class Main extends JFrame {

    /** Field description */
    public static final String CONTENT_SELECTOR = "*Content*.*";
    private static final String VERSION_QUERY = "SELECT e FROM VersiontablePers e";
    private static JocTable jt = new JocTable();
    private static JLabel lblMd5, lblVersion, lblFileName, lblEditTime, lblRepo, lblSrcLocation;
    private static JMenuBar menuBar;
    private static JMenu mnFile;
    private static JMenuItem mntmExit;
    private static JList repoList;
    private static JFrame frame;
    private static JTableBinding jTableBinding;
    private static EntityManager entityManager;
    private static BindingGroup bindingGroup;
    private static Query versionQuery;
    private static List<VersiontablePers> versionList;
    private static JScrollPane js, jsl;
    private static JButton btnMonitor, btnClearDb, btnRefresh;
    private static JFileChooser jfc;

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        createBaseStructure();
        loadDirChooser();
        buildGui();
    }

    private static void createBaseStructure() {
        Directory di = new Directory(AppVars.getRepoLocation());

        di.create();
        di = new Directory(AppVars.getBackupPath());
        di.create();
        di = new Directory(AppVars.getTempPath());
        di.create();
    }

    private static void buildGui() {
        frame = new JFrame("Developer Versioning System");
        buildScrollPane();
        buildButtons();
        buildLabels();
        buildMenu();
        bindTable();
        repoList = new JList<String>();
        setUpJlist();
        jsl = new JScrollPane();
        jsl.setViewportView(repoList);
        jsl.setBounds(5, 345, 390, 280);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setBounds(400, 100, 975, 650);
        frame.setPreferredSize(new Dimension(975, 680));
        frame.setResizable(false);
        jt.setPreferredSize(new Dimension(955, 300));
        frame.getContentPane().add(js);
        frame.getContentPane().add(btnRefresh);
        frame.getContentPane().add(btnClearDb);
        frame.getContentPane().add(btnMonitor);
        frame.getContentPane().add(jsl);
        frame.getContentPane().add(lblVersion);
        frame.getContentPane().add(lblFileName);
        frame.getContentPane().add(lblMd5);
        frame.getContentPane().add(lblEditTime);
        frame.getContentPane().add(lblRepo);
        frame.getContentPane().add(lblSrcLocation);
        frame.pack();
        frame.setVisible(true);
        destroyDb();
        rebuildTableView();
    }

    private static void buildButtons() {
        btnRefresh = new JButton("Refresh Database");
        btnRefresh.setBounds(785, 590, 180, 26);
        btnRefresh.setToolTipText("Click to refresh data");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rebuildTableView();
            }
        });
        btnClearDb = new JButton("Clear Database Values");
        btnClearDb.setBounds(595, 590, 180, 26);
        btnClearDb.setToolTipText("Click to clear Database Values");
        btnClearDb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroyDb();
                clearList();
                clearLabels();
            }
        });
        btnMonitor = new JButton("Choose Folder to Monitor");
        btnMonitor.setBounds(405, 590, 180, 26);
        btnMonitor.setToolTipText("Choose a folder to Version files in");
        btnMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDirChooser();
            }
        });
    }

    private static void loadDirChooser() {
        Executor exec = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                jfc = new JFileChooser();
                jfc.setFileSelectionMode(DIRECTORIES_ONLY);
                jfc.setDialogTitle("Choose folder to monitor");
                jfc.showOpenDialog(null);

                if (jfc.getSelectedFile().isDirectory()) {
                    WatchDir wd = new WatchDir();

                    wd.WatchDir(jfc.getSelectedFile().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No folder was chosen to monitor");
                }
            }
        };

        exec.execute(runnable);
    }

    private static void buildMenu() {
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        mnFile = new JMenu("File");
        menuBar.add(mnFile);
        mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        mnFile.add(mntmExit);
        activateMouseListener();
    }

    private static void activateMouseListener() {
        jt.addMouseListener(new MouseAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int tmpRow = jt.getSelectedRow();

                if (tmpRow > -1) {
                    String repoPath = (String) jt.getModel().getValueAt(tmpRow, 4);

                    setLabelValues(repoPath);
                    rebuildVersionList(repoPath);
                }
            }
        });
    }

    private static void rebuildVersionList(String repoPath) {
        Directory di = new Directory(repoPath);
        List repo = di.getChildren(true, CONTENT_SELECTOR, true);

        frame.getContentPane().remove(jsl);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
        repoList = new JList(repo.toArray());
        setUpJlist();
        jsl = new JScrollPane();
        jsl.setViewportView(repoList);
        jsl.setBounds(5, 345, 390, 280);
        frame.getContentPane().add(jsl);
        frame.pack();
    }

    private static void setLabelValues(String repoPath) {
        File fvc = new File(new StringBuilder().append(repoPath).append(AppVars.getVersionControlFile()).toString());
        VersionControlFile vcf = SaveExtractVersionControl.extractVersionControl(fvc);

        lblMd5.setText("MD5 Hash: " + vcf.getMd5Hash());
        lblFileName.setText("File Name: " + vcf.getSrcFileName());
        lblEditTime.setText("Last Edit Time: " + vcf.getEditTime().toString());
        lblRepo.setText("Repository Path: " + vcf.getRepoFileLocation());
        lblSrcLocation.setText("Src Location: " + vcf.getSrcFileLocation());
        lblVersion.setText("Version: " + String.valueOf(vcf.getCurrentVersion()));
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
        frame.pack();
    }

    private static void clearLabels(){
        lblMd5.setText("MD5 Hash: ");
        lblFileName.setText("File Name: ");
        lblEditTime.setText("Last Edit Time: ");
        lblRepo.setText("Repository Path: ");
        lblSrcLocation.setText("Src Location: ");
        lblVersion.setText("Version: ");
    }

    private static void buildLabels() {
        lblMd5 = new JLabel("MD5 Hash: ");
        lblMd5.setBounds(400, 350, 400, 24);
        lblFileName = new JLabel("File Name: ");
        lblFileName.setBounds(400, 380, 400, 24);
        lblEditTime = new JLabel("Last Edit Time: ");
        lblEditTime.setBounds(400, 410, 400, 24);
        lblRepo = new JLabel("Repository Path: ");
        lblRepo.setBounds(400, 440, 600, 24);
        lblSrcLocation = new JLabel("Src Location: ");
        lblSrcLocation.setBounds(400, 470, 600, 24);
        lblVersion = new JLabel("Version: ");
        lblVersion.setBounds(400, 500, 400, 24);
    }

    private static void setUpJlist() {
        repoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        repoList.setLayoutOrientation(JList.VERTICAL);
        repoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (repoList.getSelectedValue() != null) {
                    String select = repoList.getSelectedValue().toString();

                    ContentExtractor.extractContent(select);
                }
            }
        });
    }

    private static void clearList() {
        frame.getContentPane().remove(jsl);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
        repoList = new JList();
        jsl = new JScrollPane();
        jsl.setViewportView(repoList);
        jsl.setBounds(5, 345, 390, 280);
        frame.getContentPane().add(jsl);
        frame.pack();

    }

    private static void destroyDb() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(AppVars.getPersistenceUnit());
        JpaControl vjc = new JpaControl(emf);
        int tmpCnt = vjc.getVersionCount();

        tmpCnt++;

        for (int i = 1; i < tmpCnt; i++) {
            try {
                vjc.destroy(i);
            } catch (NonexistentEntityException e) {
                e.printStackTrace();
            }
        }

        rebuildTableView();
    }

    private static void buildScrollPane() {
        js = new JScrollPane();
        js.setViewportView(jt);
        js.setBounds(5, 20, 960, 320);
        js.setPreferredSize(new Dimension(960, 320));
    }

    private static void rebuildTableView() {
        frame.getContentPane().remove(js);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
        bindTable();
        buildScrollPane();
        frame.getContentPane().add(js);
    }

    private static void bindTable() {
        bindingGroup = new BindingGroup();
        entityManager = (isDesignTime()
                         ? null
                         : Persistence.createEntityManagerFactory(AppVars.getPersistenceUnit()).createEntityManager());
        versionQuery = (isDesignTime() ? null : entityManager.createQuery(VERSION_QUERY));
        versionList = (isDesignTime() ? emptyList() : versionQuery.getResultList());
        jTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, versionList, jt);

        JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${id}"));

        columnBinding.setColumnName("File ID");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${filename}"));
        columnBinding.setColumnName("Monitored File");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${location}"));
        columnBinding.setColumnName("File Location");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${version}"));
        columnBinding.setColumnName("Version");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${repository}"));
        columnBinding.setColumnName("Repository");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jt.setRowSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jt.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jt.getColumnModel().getColumn(0).setMinWidth(70);
        jt.getColumnModel().getColumn(0).setPreferredWidth(70);
        jt.getColumnModel().getColumn(0).setMaxWidth(70);
        jt.getColumnModel().getColumn(1).setMinWidth(200);
        jt.getColumnModel().getColumn(1).setPreferredWidth(200);
        jt.getColumnModel().getColumn(1).setMaxWidth(200);
        jt.getColumnModel().getColumn(2).setMinWidth(320);
        jt.getColumnModel().getColumn(2).setPreferredWidth(320);
        jt.getColumnModel().getColumn(2).setMaxWidth(320);
        jt.getColumnModel().getColumn(3).setMinWidth(70);
        jt.getColumnModel().getColumn(3).setPreferredWidth(70);
        jt.getColumnModel().getColumn(3).setMaxWidth(70);
        jt.getColumnModel().getColumn(4).setMinWidth(300);
        jt.getColumnModel().getColumn(4).setPreferredWidth(300);
        jt.getColumnModel().getColumn(4).setMaxWidth(300);
        jt.setEditable(false);
    }
}


//~ Formatted in DD Std on 13/09/22

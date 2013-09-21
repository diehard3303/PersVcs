/*
 * @(#)Main.java   13/09/21
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

import javaxt.io.Directory;

//~--- JDK imports ------------------------------------------------------------

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.DIRECTORY_CHANGED_PROPERTY;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 12:29 PM
 * Original Project: PersVcs
 */
public class Main {
    private static JFileChooser jfc;

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        Directory di = new Directory(AppVars.getRepoLocation());

        di.create();
        di = new Directory(AppVars.getBackupPath());
        di.create();
        di = new Directory(AppVars.getTempPath());
        di.create();

        Executor exec = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                jfc = new JFileChooser();
                jfc.setFileSelectionMode(DIRECTORIES_ONLY);
                jfc.setDialogTitle("Choose folder to monitor");
                jfc.showOpenDialog(null);

                if (jfc.getSelectedFile().isDirectory()) {
                    WatchDir.WatchDir(jfc.getSelectedFile().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No folder was chosen to monitor");
                }
            }
        };

        exec.execute(runnable);
    }
}


//~ Formatted in DD Std on 13/09/21

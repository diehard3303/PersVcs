/*
 * @(#)GetFileListing.java   13/09/24
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

import java.util.List;

import javax.swing.JOptionPane;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 11:57 AM
 * Original Project: PersVcs
 */
public class GetFileListing {

    /** Field description */
    public static final String SLASH = "\\";
    private String dirPath;

    /**
     * Method description
     *
     *
     * @param dirPath
     *
     * @return   file listing all
     */
    public List<String> getFileListing(String dirPath) {
        this.dirPath = dirPath + SLASH;

        List<String> files = null;

        CheckPreExisting.checkPreExistingFiles(this.dirPath);

        if (!AppVars.isExistingExists()) {
            Directory directory = new Directory(this.dirPath);

            /* Return a list of all files found in the current directory */
            files = directory.getChildren(true, "*.*", true);
            JOptionPane.showMessageDialog(null, "File listing complete, Click Refresh Database");
        }

        return files;
    }
}


//~ Formatted in DD Std on 13/09/24

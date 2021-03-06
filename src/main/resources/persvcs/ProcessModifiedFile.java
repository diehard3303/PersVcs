/*
 * @(#)ProcessModifiedFile.java   13/09/25
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

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/25/13
 * Time: 2:16 PM
 * Original Project: PersVcs
 */
public class ProcessModifiedFile {

    /**
     * Method description
     *
     *
     * @param filePath
     */
    public static void processModFile(String filePath) {
        String folder = FindFolders.findFolderPath(new javaxt.io.File(filePath).getName());
        String wrkPath = new StringBuilder().append(AppVars.getRepoLocation()).append(folder).append(
                             AppVars.getVersionControlFile()).toString();

        if (new java.io.File(wrkPath).exists()) {
            VersionControlFile vc = SaveExtractVersionControl.extractVersionControl(new java.io.File(wrkPath));
            String mHash = Hasher.md5FastHash(new java.io.File(filePath));

            if (!vc.getMd5Hash().equals(mHash)) {
                Date d = new Date();
                int ver = vc.getCurrentVersion();
                vc.setMd5Hash(mHash);
                vc.setEditTime(d);
                vc.setSrcFileName(new javaxt.io.File(filePath).getName());
                vc.setRepoFileLocation(AppVars.getRepoLocation() + folder + "//");
                vc.setCurrentVersion(ver++);
                vc.setSrcFileLocation(filePath);
            }
        }
    }
}


//~ Formatted in DD Std on 13/09/25

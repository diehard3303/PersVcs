/*
 * @(#)CheckPreExisting.java   13/09/24
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import static persvcs.AppVars.getRepoLocation;
import static persvcs.AppVars.getSearchFolderFile;
import static persvcs.AppVars.getVersionControlFile;
import static persvcs.AppVars.setExistingExists;

//~--- JDK imports ------------------------------------------------------------

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/24/13
 * Time: 10:20 AM
 * Original Project: PersVcs
 */
public class CheckPreExisting {

    /** Field description */
    public static final String SLASH = "/";

    /**
     * Method description
     *
     *
     * @param path
     */
    public static void checkPreExistingFiles(String path) {
        String xPath = path.substring(3, path.length() - 1);
        String wrkPath =
            new StringBuilder().append(getRepoLocation()).append(xPath).append(getSearchFolderFile()).toString();

        if (new File(wrkPath).exists()) {
            SearchFolder sf = deserializeSearch(wrkPath);

            for (Iterator<String> iterator = sf.getSearchFolder().iterator(); iterator.hasNext(); ) {
                String f = iterator.next();
                File fg = new File(f + SLASH + getVersionControlFile());

                if (fg.exists()) {
                    VersionControlFile vcf = SaveExtractVersionControl.extractVersionControl(fg);
                    CreateEntity ce = new CreateEntity();

                    ce.createEntity(vcf.getSrcFileLocation(), vcf.getSrcFileName(), vcf.getRepoFileLocation(),
                            vcf.getCurrentVersion());
                    setExistingExists(true);
                } else {
                    setExistingExists(false);

                    break;
                }
            }
        } else {
            setExistingExists(false);
        }
    }

    private static SearchFolder deserializeSearch(String filePath) {
        SearchFolder sf;

        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            sf = (SearchFolder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();

            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();

            return null;
        }

        return sf;
    }
}


//~ Formatted in DD Std on 13/09/24

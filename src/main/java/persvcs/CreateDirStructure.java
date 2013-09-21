/*
 * @(#)CreateDirStructure.java   13/09/21
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
import javaxt.io.File;

//~--- JDK imports ------------------------------------------------------------

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 12:03 PM
 * Original Project: PersVcs
 */
public class CreateDirStructure {

    /** Field description */
    public static final String UnderScore = "_";

    /**
     * Method description
     *
     *
     * @param dirList
     */
    public static void createDirectories(List<String> dirList) {
        String oldName = "";
        ArrayList<String> result = new ArrayList<String>(dirList.size());

        for (Object f : dirList) {
            if (f != null) {
                String srcPath = new File(f.toString()).getDirectory().toString();
                String srcFileName = new File(f.toString()).getName();
                javaxt.io.File gff = new javaxt.io.File(f.toString());
                String fileNameOnly = gff.getName(false);

                if (fileNameOnly.equals(oldName)) {
                    fileNameOnly = fileNameOnly + UnderScore + gff.getExtension();

                    Directory di = new Directory(AppVars.getRepoLocation() + fileNameOnly);

                    di.create();
                    ConfigureVersionControl.configVersionControl(srcFileName, srcPath + "\\", fileNameOnly);
                } else {
                    Directory di = new Directory(AppVars.getRepoLocation() + fileNameOnly);

                    di.create();
                    ConfigureVersionControl.configVersionControl(srcFileName, srcPath + "\\", fileNameOnly);
                }

                oldName = fileNameOnly;
                result.add(fileNameOnly);
            }
        }
    }
}


//~ Formatted in DD Std on 13/09/21

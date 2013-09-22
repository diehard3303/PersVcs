/*
 * @(#)FindFolders.java   13/09/21
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

import com.thoughtworks.xstream.XStream;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 7:48 PM
 * Original Project: PersVcs
 */
public class FindFolders {

    /**
     * Method description
     *
     *
     * @param fileName
     *
     * @return folder path
     */
    public static String findFolderPath(String fileName) {
        String folder;
        ArrayList<String> folders = extractFolders();
        ArrayList<String> folderPath = extractFolderPath();

        folder = folders.contains(fileName) ? folderPath.get(folders.indexOf(fileName)) : null;

        return folder;
    }

    /**
     * Method description
     *
     *
     *
     * @return  Created Folder Paths object
     */
    private static CreatedFolderPaths extractFolderPath() {
        XStream xs = new XStream();
        CreatedFolderPaths cfp = (CreatedFolderPaths) xs.fromXML(
                                     new StringBuilder().append(AppVars.getRepoLocation()).append(
                                         AppVars.getCreatedFolderPaths()).toString());

        return (CreatedFolderPaths) cfp.getFolderPaths();
    }

    /**
     * Method description
     *
     * @return  Created Folder Paths object
     */
    private static CreatedFolders extractFolders() {
        XStream xs = new XStream();
        CreatedFolders cf = (CreatedFolders) xs.fromXML(
                                new StringBuilder().append(AppVars.getRepoLocation()).append(
                                    AppVars.getCreatedFolders()).toString());

        return (CreatedFolders) cf.getFoldersCreated();
    }
}


//~ Formatted in DD Std on 13/09/21

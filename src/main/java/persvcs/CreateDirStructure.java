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

import static persvcs.ConfigureVersionControl.configVersionControl;

import static persvcs.ContentSerializer.serializeContent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
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

    /** Field description */
    public static final String SLASH = "\\";

    /**
     * Method description
     *
     *
     * @param dirList
     */
    public static void createDirectories(List<String> dirList) {
        String oldName = "";
        ArrayList<String> fileName = new ArrayList<String>(dirList.size());
        ArrayList<String> folderPath = new ArrayList<String>(dirList.size());

        for (Iterator<String> iterator = dirList.iterator(); iterator.hasNext(); ) {
            Object f = iterator.next();

            if (f != null) {
                String srcPath = new File(f.toString()).getDirectory().toString();
                String srcFileName = new File(f.toString()).getName();
                File gff = new File(f.toString());
                String fileNameOnly = gff.getName(false);

                if (fileNameOnly.equals(oldName)) {
                    fileNameOnly = fileNameOnly + UnderScore + gff.getExtension();

                    Directory di = new Directory(AppVars.getRepoLocation() + fileNameOnly);

                    di.create();
                    fileName.add(srcFileName);
                    folderPath.add(AppVars.getRepoLocation() + fileNameOnly);
                    configVersionControl(srcFileName, srcPath + SLASH, fileNameOnly);
                    serializeContent(srcPath + srcFileName, fileNameOnly);

                    java.io.File fe = new java.io.File(
                                          new StringBuilder().append(AppVars.getRepoLocation()).append(
                                              fileNameOnly).append(SLASH).append(
                                              AppVars.getVersionControlFile()).toString());
                    int ver = SaveExtractVersionControl.extractVersion(fe);

                    CreateEntity.createEntity(f.toString(), srcFileName, AppVars.getRepoLocation() + fileNameOnly, ver);
                } else {
                    Directory di = new Directory(AppVars.getRepoLocation() + fileNameOnly);

                    di.create();
                    fileName.add(srcFileName);
                    folderPath.add(AppVars.getRepoLocation() + fileNameOnly);
                    configVersionControl(srcFileName, srcPath + SLASH, fileNameOnly);
                    serializeContent(f.toString(), fileNameOnly);

                    java.io.File fe = new java.io.File(
                                          new StringBuilder().append(AppVars.getRepoLocation()).append(
                                              fileNameOnly).append(SLASH).append(
                                              AppVars.getVersionControlFile()).toString());
                    int ver = SaveExtractVersionControl.extractVersion(fe);

                    CreateEntity.createEntity(f.toString(), srcFileName, AppVars.getRepoLocation() + fileNameOnly, ver);
                }

                oldName = fileNameOnly;
            }
        }

        SerializeFolderPaths(fileName, folderPath);
    }

    private static void SerializeFolderPaths(ArrayList<String> fileName, ArrayList<String> folderPath) {
        CreatedFolders cf = new CreatedFolders();
        CreatedFolderPaths cfp = new CreatedFolderPaths();

        cf.setFoldersCreated(fileName);
        cfp.setFolderPaths(folderPath);

        Serializer sr = new Serializer();

        sr.serializeObject(AppVars.getRepoLocation() + AppVars.getCreatedFolders(), cf);
        sr = new Serializer();
        sr.serializeObject(AppVars.getRepoLocation() + AppVars.getCreatedFolderPaths(), cfp);
    }
}


//~ Formatted in DD Std on 13/09/21

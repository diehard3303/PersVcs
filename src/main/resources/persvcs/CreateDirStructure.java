/*
 * @(#)CreateDirStructure.java   13/09/24
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javaxt.io.Directory;
import javaxt.io.File;

import static persvcs.AppVars.getCreatedFolderPaths;
import static persvcs.AppVars.getCreatedFolders;
import static persvcs.AppVars.getFileNameFull;
import static persvcs.AppVars.getRepoLocation;
import static persvcs.AppVars.getSearchFolderFile;
import static persvcs.AppVars.getVersionControlFile;
import static persvcs.ConfigureVersionControl.configVersionControl;
import static persvcs.ContentSerializer.serializeContent;

//~--- JDK imports ------------------------------------------------------------

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
     * @param dPath
     */
    public static void createDirectories(List<String> dirList, String dPath) {
        ArrayList<String> fileName = new ArrayList<String>(dirList.size());
        ArrayList<String> folderPath = new ArrayList<String>(dirList.size());
        ArrayList<String> SearchArchive = new ArrayList<String>(dirList.size());
        String fileNameOnly = dPath.substring(3, dPath.length());

        for (Iterator<String> iterator = dirList.iterator(); iterator.hasNext(); ) {
            Object f = iterator.next();

            if (f != null) {
                String srcPath = new File(f.toString()).getDirectory().toString();
                String srcFileName = getFileNameFull(f.toString());
                UUID folderName = UUID.randomUUID();
                Directory di = new Directory(getRepoLocation() + folderName);

                di.create();
                fileName.add(srcFileName);
                folderPath.add(getRepoLocation() + folderName);
                SearchArchive.add(getRepoLocation() + folderName);
                configVersionControl(srcFileName, srcPath + SLASH, folderName.toString());
                serializeContent(srcPath + srcFileName, folderName.toString());

                java.io.File fe = new java.io.File(
                                      new StringBuilder().append(getRepoLocation()).append(folderName).append(
                                          SLASH).append(getVersionControlFile()).toString());
                int ver = SaveExtractVersionControl.extractVersion(fe);

                CreateEntity.createEntity(f.toString(), srcFileName, getRepoLocation() + folderName, ver);
            }
        }

        SerializeFolderPaths(fileName, folderPath);
        SerializeSearchFolderPaths(SearchArchive, fileNameOnly);
    }

    private static void SerializeSearchFolderPaths(ArrayList<String> fPath, String filePrefix) {
        SearchFolder sf = new SearchFolder();

        sf.setSearchfolder(fPath);

        Serializer sr = new Serializer();

        sr.serializeArrayListSearchFolder(getRepoLocation() + filePrefix + getSearchFolderFile(), sf);
    }

    private static void SerializeFolderPaths(ArrayList<String> fileName, ArrayList<String> folderPath) {
        CreatedFolders cf = new CreatedFolders();
        CreatedFolderPaths cfp = new CreatedFolderPaths();

        cf.setFoldersCreated(fileName);
        cfp.setFolderPaths(folderPath);

        Serializer sr = new Serializer();

        sr.serializeObject(getRepoLocation() + getCreatedFolders(), cf);
        sr = new Serializer();
        sr.serializeObject(getRepoLocation() + getCreatedFolderPaths(), cfp);
    }
}


//~ Formatted in DD Std on 13/09/24

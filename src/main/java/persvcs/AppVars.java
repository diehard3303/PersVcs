/*
 * @(#)AppVars.java   13/09/21
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

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 12:13 PM
 * Original Project: PersVcs
 */
public class AppVars {
    private static final String REPO_PATH = "/home/repository/";
    private static final String TEMP_PATH = "/home/temp/";
    private static final String BACKUP_PATH = "/home/backup/";
    private static final String VERSION_CONTROL_FILE = "Version_Control.xml";
    private static final String CREATED_FOLDERS = "Created_Folders.dat";
    private static final String CREATED_FOLDER_PATHS = "Created_Folder_Paths.dat";
    private static final String PERSISTENCE_UNIT = "persvcs";
    private static final String CONTENT_VER = "Content_Ver_";
    private static final String CONTENT_VER_EXT = "_.xml";

    /**
     * Method description
     *
     *
     * @return persistence instance name
     */
    public static String getPersistenceUnit() {
        return PERSISTENCE_UNIT;
    }

    /**
     * Method description
     *
     *
     * @return created folder path file name
     */
    public static String getCreatedFolderPaths() {
        return CREATED_FOLDER_PATHS;
    }

    /**
     * Method description
     *
     *
     * @return  filename for created folders listing
     */
    public static String getCreatedFolders() {
        return CREATED_FOLDERS;
    }

    /**
     * Method description
     *
     *
     * @return  temp dir path
     */
    public static String getTempPath() {
        return TEMP_PATH;
    }

    /**
     * Method description
     *
     *
     * @return  backup dir path
     */
    public static String getBackupPath() {
        return BACKUP_PATH;
    }

    /**
     * Method description
     *
     *
     * @return  version control file name
     */
    public static String getVersionControlFile() {
        return VERSION_CONTROL_FILE;
    }

    /**
     * Method description
     *
     *
     * @return  repoPath
     */
    public static String getRepoLocation() {
        return REPO_PATH;
    }

    /**
     * Method description
     *
     *
     * @return content file prefix
     */
    public static String getContentVer() {
        return CONTENT_VER;
    }

    /**
     * Method description
     *
     *
     * @return  content file ext
     */
    public static String getContentVerExt() {
        return CONTENT_VER_EXT;
    }
}


//~ Formatted in DD Std on 13/09/21

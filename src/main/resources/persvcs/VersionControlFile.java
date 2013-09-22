/*
 * @(#)VersionControlFile.java   13/09/21
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

import java.io.Serializable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 11:42 AM
 * Original Project: PersVcs
 */
public class VersionControlFile implements Serializable {
    private String md5Hash;
    private Date editTime;
    private String srcFileName;
    private String srcFileLocation;
    private String repoFileLocation;
    private int currentVersion;

    /**
     * Method description
     *
     *
     * @return   md5Hash
     */
    public String getMd5Hash() {
        return md5Hash;
    }

    /**
     * Method description
     *
     *
     * @param md5Hash
     */
    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    /**
     * Method description
     *
     *
     * @return  editTime
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * Method description
     *
     *
     * @param editTime
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * Method description
     *
     *
     * @return  srcFileName
     */
    public String getSrcFileName() {
        return srcFileName;
    }

    /**
     * Method description
     *
     *
     * @param srcFileName
     */
    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    /**
     * Method description
     *
     *
     * @return   srcFileLocation
     */
    public String getSrcFileLocation() {
        return srcFileLocation;
    }

    /**
     * Method description
     *
     *
     * @param srcFileLocation
     */
    public void setSrcFileLocation(String srcFileLocation) {
        this.srcFileLocation = srcFileLocation;
    }

    /**
     * Method description
     *
     *
     * @return  repoFileLocation
     */
    public String getRepoFileLocation() {
        return repoFileLocation;
    }

    /**
     * Method description
     *
     *
     * @param repoFileLocation
     */
    public void setRepoFileLocation(String repoFileLocation) {
        this.repoFileLocation = repoFileLocation;
    }

    /**
     * Method description
     *
     *
     * @return    currentVersion
     */
    public int getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Method description
     *
     *
     * @param currentVersion
     */
    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }
}


//~ Formatted in DD Std on 13/09/21

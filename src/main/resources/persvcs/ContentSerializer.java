/*
 * @(#)ContentSerializer.java   13/09/21
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

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 3:25 PM
 * Original Project: PersVcs
 */
public class ContentSerializer {

    /** Field description */
    public static final String SLASH = "//";

    /**
     * Method description
     *
     *
     * @param srcFilePath
     * @param fileNameOnly
     */
    public static void serializeContent(String srcFilePath, String fileNameOnly) {
        VersionFileContent vfc = new VersionFileContent();
        File fc = new File(
                      new StringBuilder().append(AppVars.getRepoLocation()).append(fileNameOnly).append(SLASH).append(
                          AppVars.getVersionControlFile()).toString());
        VersionControlFile vcf = SaveExtractVersionControl.extractVersionControl(fc);
        File fv = new File(
                      new StringBuilder().append(AppVars.getRepoLocation()).append(fileNameOnly).append(SLASH).append(
                      AppVars.getVersionControlFile()).toString());
        File fContent = new File(srcFilePath);
        String srcFileName = fContent.getName();

        vfc.setCurrentVersion(SaveExtractVersionControl.extractVersion(fv));
        vfc.setMd5Hash(SaveExtractVersionControl.extractMD5Hash(fv));
        vfc.setSrcFileLocation(vcf.getSrcFileLocation());
        vfc.setRepoFileLocation(vcf.getRepoFileLocation());
        vfc.setSrcFileName(srcFileName);
        vfc.setEditTime(vcf.getEditTime());
        vfc.setRevisionComment("Latest Update");
        vfc.setSrcContent(ReadWrite.readToByte(fContent));

        Serializer sr = new Serializer();

        sr.serializeObject(
            new StringBuilder().append(AppVars.getRepoLocation()).append(fileNameOnly).append(SLASH).append(
                AppVars.getContentVer()).append(vcf.getCurrentVersion()).append(
                AppVars.getContentVerExt()).toString(), vfc);
    }
}


//~ Formatted in DD Std on 13/09/21

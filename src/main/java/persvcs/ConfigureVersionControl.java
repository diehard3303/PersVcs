/*
 * @(#)ConfigureVersionControl.java   13/09/21
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
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 12:21 PM
 * Original Project: PersVcs
 */
public class ConfigureVersionControl {

    /**
     * Method description
     *
     *
     * @param srcfileName
     * @param srcFilePath
     * @param srcNameNoX
     */
    public static void configVersionControl(String srcfileName, String srcFilePath, String srcNameNoX) {
        Date d = new Date();
        VersionControlFile vc = new VersionControlFile();
        File fi = new File(srcFilePath + srcfileName);

        vc.setMd5Hash(Hasher.md5FastHash(fi));
        vc.setRepoFileLocation(AppVars.getRepoLocation() + srcNameNoX + "/");
        vc.setSrcFileLocation(srcFilePath + srcfileName);
        vc.setSrcFileName(srcfileName);
        vc.setEditTime(d);

        java.io.File f = new java.io.File(AppVars.getRepoLocation() + srcNameNoX + AppVars.getVersionControlFile());

        if (f.exists()) {
            if (!vc.getMd5Hash().equals(extractMD5Hash(f))) {
                int i = extractVersion(f);
                i++;
                vc.setCurrentVersion(i);
            } else {
                vc.setCurrentVersion(extractVersion(f));
            }
        } else {
            vc.setCurrentVersion(1);
        }

         saveVersion(AppVars.getRepoLocation() + srcNameNoX + "/" + AppVars.getVersionControlFile(), vc);
    }

   static void saveVersion(String xmlPath, Object version) {
        XStream xs = new XStream();
        String xml = "";
        byte[] bytes;
        java.io.FileOutputStream fos = null;

        try {
            xml = xs.toXML(version);
            fos = new java.io.FileOutputStream(xmlPath);
            bytes = xml.getBytes("UTF-8");
            fos.write(bytes);
        } catch (Exception e) {
            System.err.println("Error in XML Write: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param xmlFile
     *
     * @return   stored version number
     */
    public static int extractVersion(File xmlFile) {
        XStream xs = new XStream();
        VersionControlFile vs = (VersionControlFile) xs.fromXML(xmlFile);

        return vs.getCurrentVersion();
    }

    /**
     * Method description
     *
     *
     * @param xmlFile
     *
     * @return   stored md5 hash
     */
    public static String extractMD5Hash(File xmlFile) {
        XStream xs = new XStream();
        VersionControlFile vs = (VersionControlFile) xs.fromXML(xmlFile);

        return vs.getMd5Hash();
    }
}


//~ Formatted in DD Std on 13/09/21

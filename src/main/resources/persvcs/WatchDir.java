/*
 * @(#)WatchDir.java   13/09/25
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

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 11:52 AM
 * Original Project: PersVcs
 */
public class WatchDir {
    private static String dirPath;

    /**
     * Method description
     *
     *
     * @param dirPath
     */
    public static void WatchDir(String dirPath) {
        WatchDir.dirPath = dirPath;

        Directory directory = getDirectoryInfo();
        java.util.List events = null;

        try {
            events = directory.getEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            Directory.Event event;

            synchronized (events) {
                while (events.isEmpty()) {
                    try {
                        events.wait();
                    } catch (InterruptedException e) {}
                }

                event = (Directory.Event) events.remove(0);

                if ((event.getEventID() == event.MODIFY) && event.toString().contains("Modify")
                        && new java.io.File(event.getFile()).exists()) {
                    ProcessModifiedFile.processModFile(event.getFile());
                }

                if ((event.getEventID() == event.CREATE) && event.toString().contains("Create")) {}

                if ((event.getEventID() == event.DELETE) && event.toString().contains("Delete")) {}
            }

            if (event != null) {
                System.out.println(event.toString());
            }
        }
    }

    private static Directory getDirectoryInfo() {
        Directory directory = new Directory(dirPath);
        GetFileListing gfl = new GetFileListing();
        List<String> fileList = gfl.getFileListing(dirPath);

        if (!AppVars.isExistingExists()) {
            CreateDirStructure.createDirectories(fileList, dirPath);
        }

        return directory;
    }
}


//~ Formatted in DD Std on 13/09/25

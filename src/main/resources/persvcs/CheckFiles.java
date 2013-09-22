/*
 * @(#)CheckFiles.java   13/09/21
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

import org.apache.commons.io.FileUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 8:15 PM
 * Original Project: PersVcs
 */
public class CheckFiles {
    private List<String> textlines = null;
    private List<String> imageMime = null;
    private List<String> illegalExt = null;

    /**
     * Method description
     *
     *
     */
    private void convertMime() {
        try {
            setTextlines(FileUtils.readLines(new File(AppVars.getRepoLocation() + "textMime.txt"), "utf-8"));
            setImageMime(FileUtils.readLines(new File(AppVars.getRepoLocation() + "imageMime.txt"), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertIllegalExt() {
        try {
            setIllegalExt(FileUtils.readLines(new File(AppVars.getRepoLocation() + "illegalExtensions.txt"), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method description
     *
     *
     * @return   txt mime list
     */
    public List<String> getTextlines() {
        convertMime();

        return textlines;
    }

    /**
     * Method description
     *
     *
     * @param textlines
     */
    private void setTextlines(List<String> textlines) {
        this.textlines = textlines;
    }

    /**
     * Method description
     *
     *
     * @return   img mime list
     */
    public List<String> getImageMime() {
        convertMime();

        return imageMime;
    }

    /**
     * Method description
     *
     *
     * @param imageMime
     */
    private void setImageMime(List<String> imageMime) {
        this.imageMime = imageMime;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<String> getIllegalExt() {
        convertIllegalExt();

        return illegalExt;
    }

    /**
     * Method description
     *
     *
     * @param illegalExt
     */
    private void setIllegalExt(List<String> illegalExt) {
        this.illegalExt = illegalExt;
    }
}


//~ Formatted in DD Std on 13/09/21

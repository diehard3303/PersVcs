/*
 * @(#)VersiontablePers.java   13/09/21
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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 10:58 AM
 * Original Project: PersVcs
 */
@Table(
    name = "VERSIONTABLE",
    schema = "APP",
    catalog = ""
)
@Entity
public class VersiontablePers {
    private int id;
    private String filename;
    private String location;
    private String repository;
    private int version;

    /**
     * Method description
     *
     *
     * @return
     */
    @Column(
        name = "ID",
        nullable = false,
        insertable = true,
        updatable = true,
        length = 10,
        precision = 0
    )
    @Id
    public int getId() {
        return id;
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Column(
        name = "FILENAME",
        nullable = false,
        insertable = true,
        updatable = true,
        length = 500,
        precision = 0
    )
    @Basic
    public String getFilename() {
        return filename;
    }

    /**
     * Method description
     *
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Column(
        name = "LOCATION",
        nullable = false,
        insertable = true,
        updatable = true,
        length = 500,
        precision = 0
    )
    @Basic
    public String getLocation() {
        return location;
    }

    /**
     * Method description
     *
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Column(
        name = "REPOSITORY",
        nullable = false,
        insertable = true,
        updatable = true,
        length = 500,
        precision = 0
    )
    @Basic
    public String getRepository() {
        return repository;
    }

    /**
     * Method description
     *
     *
     * @param repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Column(
        name = "VERSION",
        nullable = false,
        insertable = true,
        updatable = true,
        length = 10,
        precision = 0
    )
    @Basic
    public int getVersion() {
        return version;
    }

    /**
     * Method description
     *
     *
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Method description
     *
     *
     * @param o
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        VersiontablePers that = (VersiontablePers) o;

        if (id != that.id) {
            return false;
        }

        if (version != that.version) {
            return false;
        }

        if ((filename != null) ? !filename.equals(that.filename) : that.filename != null) {
            return false;
        }

        if ((location != null) ? !location.equals(that.location) : that.location != null) {
            return false;
        }

        if ((repository != null) ? !repository.equals(that.repository) : that.repository != null) {
            return false;
        }

        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + ((filename != null) ? filename.hashCode() : 0);
        result = 31 * result + ((location != null) ? location.hashCode() : 0);
        result = 31 * result + ((repository != null) ? repository.hashCode() : 0);
        result = 31 * result + version;

        return result;
    }
}


//~ Formatted in DD Std on 13/09/21

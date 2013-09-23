/*
 * @(#)JpaControl.java   13/09/21
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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 11:05 AM
 * Original Project: PersVcs
 */
public class JpaControl implements Serializable {
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf = null;

    /**
     * Constructs ...
     *
     *
     * @param emf
     */
    public JpaControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Method description
     *
     *
     * @param entry
     *
     * @throws Exception
     * @throws PreexistingEntityException
     */
    public void create(VersiontablePers entry) throws Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entry);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVersions(entry.getId()) != null) {
                throw new PreexistingEntityException("Version Table " + entry + " already exists.", ex);
            }

            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param entry
     *
     * @throws Exception
     * @throws NonexistentEntityException
     */
    public void edit(VersiontablePers entry) throws Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            entry = em.merge(entry);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if ((msg == null) || (msg.length() == 0)) {
                Integer id = entry.getId();

                if (findVersions(id) == null) {
                    throw new NonexistentEntityException("The entry with id " + id + " no longer exists.");
                }
            }

            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param id
     *
     * @throws NonexistentEntityException
     */
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            VersiontablePers entry;

            try {
                entry = em.getReference(VersiontablePers.class, id);
                entry.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entry with id " + id + " no longer exists.", enfe);
            }

            em.remove(entry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<VersiontablePers> findEntryEntities() {
        return findVersionEntities(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    private List<VersiontablePers> findVersionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();

            cq.select(cq.from(VersiontablePers.class));

            Query q = em.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Method description
     *
     *
     * @param id
     *
     * @return
     */
    public VersiontablePers findVersions(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(VersiontablePers.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getVersionCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<VersiontablePers> rt = cq.from(VersiontablePers.class);

            cq.select(em.getCriteriaBuilder().count(rt));

            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}


//~ Formatted in DD Std on 13/09/21

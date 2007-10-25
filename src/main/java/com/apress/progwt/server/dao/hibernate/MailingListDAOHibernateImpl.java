package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.dao.MailingListDAO;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.web.domain.MailingListCommand;

public class MailingListDAOHibernateImpl extends HibernateDaoSupport
        implements MailingListDAO {

    public void createEntry(MailingListCommand comm) {
        MailingListEntry e = new MailingListEntry(comm);
        getHibernateTemplate().save(e);
    }

    public List<MailingListEntry> getMailingList() {
        return getHibernateTemplate().find(
                "from MailingListEntry entry where sentEmailOk = false");

        // DetachedCriteria crit =
        // DetachedCriteria.forClass(MailingListEntry.class)
        // .setFetchMode("signedUpUser", FetchMode.JOIN)
        // .setFetchMode("inviter", FetchMode.JOIN);
        //
        // return getHibernateTemplate().findByCriteria(crit);

    }

    /**
     * NOTE: remember save() return the identifier, saveOrUpdate modifies
     * obj
     */
    public MailingListEntry createEntry(String email, User inviter) {
        MailingListEntry e = new MailingListEntry();
        e.setEmail(email);
        e.setInviter(inviter);
        getHibernateTemplate().saveOrUpdate(e);
        return e;
    }

    public void save(MailingListEntry invitation) {
        getHibernateTemplate().saveOrUpdate(invitation);
    }

    public MailingListEntry getEntryForKey(String randomkey) {
        return (MailingListEntry) DataAccessUtils
                .uniqueResult(getHibernateTemplate()
                        .findByNamedParam(
                                "from MailingListEntry where randomkey = :randomkey",
                                "randomkey", randomkey));
    }

    public MailingListEntry getEntryById(Long id) {
        return (MailingListEntry) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByNamedParam(
                        "from MailingListEntry where id = :id", "id", id));
    }

}

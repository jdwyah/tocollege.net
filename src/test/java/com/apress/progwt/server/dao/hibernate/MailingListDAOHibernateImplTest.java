package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import com.apress.progwt.server.dao.MailingListDAO;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.web.domain.MailingListCommand;

public class MailingListDAOHibernateImplTest extends
        AbstractHibernateTransactionalTest {

    private static final String A = "dfnsdiojio@dmfsdf.coim";
    private static final String B = "sd38242.432234.23eu8d";

    private MailingListDAO mailingListDAO;

    public void setMailingListDAO(MailingListDAO mailingListDAO) {
        this.mailingListDAO = mailingListDAO;
    }

    public void testCreateEntry() {

        MailingListCommand comm = new MailingListCommand();
        comm.setEmail(A);
        comm.setHost(B);
        comm.setReferer(null);

        mailingListDAO.createEntry(comm);

        List<MailingListEntry> list = mailingListDAO.getMailingList();

        System.out.println("list.size " + list.size());

        boolean found = false;
        for (MailingListEntry entry : list) {
            if (A.equals(entry.getEmail()) && B.equals(entry.getHost())
                    && entry.getReferer() == null) {
                found = true;
            }
        }

        assertTrue(found);

    }

}

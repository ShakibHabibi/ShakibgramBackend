package db.dao.daoImpl;

import db.HibernateHelper;
import db.dao.ContactDao;
import model.ContactDetail;
import model.ContactInfo;
import model.UserInfo;
import model.response.GetContactResponse;
import model.response.Response;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ContactDaoImpl implements ContactDao {
    @Override
    public Response addContact(long userId, String username) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", userId);
            updateQuery.executeUpdate();
            Query query = session.createQuery("from UserInfo where userName = :username");
            query.setParameter("username", username);
            List list = query.list();
            if (list == null || list.size() == 0) {
                session.getTransaction().commit();
                return new Response(false, "نام کاربری وجود ندارد");
            } else {
                Query checkContactQuery = session.createQuery("from ContactInfo where userId= :userId and otherUserId=:otherUserId");
                checkContactQuery.setParameter("userId", userId);
                checkContactQuery.setParameter("otherUserId", ((UserInfo) list.get(0)).getId());
                List checkContactList = checkContactQuery.list();
                if (checkContactList == null || checkContactList.size() == 0) {
                    ContactInfo userContactInfo = new ContactInfo(userId, ((UserInfo) list.get(0)).getId());
                    session.save(userContactInfo);
                    session.getTransaction().commit();
                    return new Response(true, "مخاطب اضافه شد");
                } else {
                    session.getTransaction().commit();
                    return new Response(false, "مخاطب در لیست شما وجود دارد");
                }
            }
        } catch (Exception e) {
            return new Response(false, "ایراد در افزودن مخاطب");
        }
    }

    @Override
    public GetContactResponse getContact(long userId) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", userId);
            updateQuery.executeUpdate();
            Query query = session.createQuery("select ci.id,ui.id,ui.name " +
                    "from ContactInfo ci " +
                    "inner join UserInfo ui on ui.id= (" +
                    "case when ci.userId= :userId then ci.otherUserId else ci.userId end)" +
                    "where ci.userId= :userId");
            query.setParameter("userId", userId);
            List list = query.list();
            session.getTransaction().commit();
            if (list == null || list.size() == 0) {
                return new GetContactResponse(false, "مخاطبی وجود ندارد", null);
            } else {
                List<ContactDetail> contactDetails = new ArrayList<>();
                for (Object object : list) {
                    Object[] result = (Object[]) object;
                    contactDetails.add(new ContactDetail((Long) result[0], (Long) result[1], (String) result[2]));
                }
                return new GetContactResponse(true, null, contactDetails);
            }
        } catch (Exception e) {
            return new GetContactResponse(false, "ایراد در دریافت لیست مخاطبان", null);
        }
    }
}

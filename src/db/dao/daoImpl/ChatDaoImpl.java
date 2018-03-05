package db.dao.daoImpl;

import db.HibernateHelper;
import db.dao.ChatDao;
import model.*;
import model.response.CreateChatResponse;
import model.response.GetChatsResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatDaoImpl implements ChatDao {

    @Override
    public GetChatsResponse getChats(long userId) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", userId);
            updateQuery.executeUpdate();
            Query query = session.createNativeQuery("SELECT ci.id,ui.name,msg.message,msg.createDate " +
                    "FROM ChatInfo ci " +
                    "LEFT JOIN (SELECT MessageInfo.ChatId,MessageInfo.CreateDate,MessageInfo.Message FROM MessageInfo INNER JOIN " +
                    "(SELECT MessageInfo.ChatId,MAX(MessageInfo.ID) AS LastMessage FROM MessageInfo GROUP BY MessageInfo.ChatId)tmp " +
                    "ON MessageInfo.ID=tmp.LastMessage) msg ON msg.ChatId=ci.ID " +
                    "INNER JOIN UserInfo ui ON ui.id= (" +
                    "CASE WHEN ci.userId= :userId THEN ci.otherUserId ELSE ci.userId END)" +
                    "WHERE (ci.userId = :userId OR ci.otherUserId= :userId )AND msg.message IS NOT NULL " +
                    "GROUP BY ci.id,ui.name,msg.message,msg.createDate ORDER BY msg.createDate DESC");
            query.setParameter("userId", userId);
            List list = query.list();
            session.getTransaction().commit();
            if (list == null || list.size() == 0) {
                return new GetChatsResponse(false, "اتاق گفتگویی موجود نیست", null);
            } else {
                List<ChatDetail> chatDetails = new ArrayList<>();
                for (Object object : list) {
                    Object[] result = (Object[]) object;
                    chatDetails.add(new ChatDetail(((Integer) result[0]).longValue(), (String) result[1], (String) result[2], (Date) result[3]));
                }
                if (chatDetails.size() == 0) {
                    return new GetChatsResponse(false, "اتاق گفتگویی موجود نیست", null);
                } else {
                    return new GetChatsResponse(true, null, chatDetails);
                }
            }
        } catch (Exception e) {
            return new GetChatsResponse(false, "ایراد در دریافت اطلاعات اتاق گفتگو", null);
        }
    }

    @Override
    public List<ChatItem> getAllChats() {
        final Session session = HibernateHelper.getSession();
        session.beginTransaction();
        Query query = session.createQuery("select ui.name as username,uinf.name as othername,ci.id from ChatInfo ci " +
                "inner join UserInfo ui on ci.userId=ui.id " +
                "inner join UserInfo uinf on ci.otherUserId=uinf.id ");
        List list = query.list();
        session.getTransaction().commit();
        List<ChatItem> chatItems = new ArrayList<>();
        for (Object object : list) {
            Object[] result = (Object[]) object;
            chatItems.add(new ChatItem(((Long) result[2]), (String) result[0], (String) result[1]));
        }
        return chatItems;
    }


    @Override
    public CreateChatResponse createChat(long userId, long otherUserId) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", userId);
            updateQuery.executeUpdate();
            Query query = session.createQuery("select ci.id,ui.name,mi.message,mi.createDate " +
                    "from ChatInfo ci " +
                    "left join MessageInfo mi on ci.id=mi.chatId " +
                    "inner join UserInfo ui on ui.id= (" +
                    "case when ci.userId= :userId then ci.otherUserId else ci.userId end)" +
                    "where (ci.userId = :userId and ci.otherUserId = :otherUserId)" +
                    "or (ci.userId= :otherUserId and ci.otherUserId= :userId)");
            query.setParameter("userId", userId);
            query.setParameter("otherUserId", otherUserId);
            List list = query.list();
            if (list == null || list.size() == 0) {
                ChatInfo userChatInfo = new ChatInfo(userId, otherUserId);
                session.save(userChatInfo);
                Query getUserQuery = session.createQuery("from UserInfo where id = :id");
                getUserQuery.setParameter("id", otherUserId);
                List userList = getUserQuery.list();
                UserInfo userInfo = (UserInfo) userList.get(0);
                session.getTransaction().commit();
                return new CreateChatResponse(true, null, new ChatDetail(userChatInfo.getId(), userInfo.getName(),
                        null, null));
            } else {
                Object[] objects = (Object[]) list.get(list.size() - 1);
                return new CreateChatResponse(true, null,
                        new ChatDetail((Long) objects[0], (String) objects[1], (String) objects[2], (Date) objects[3]));
            }
        } catch (Exception e) {
            return new CreateChatResponse(false, "ایراد در ایجاد اتاق گفتگو", null);
        }
    }
}

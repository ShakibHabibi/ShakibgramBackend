package db.dao.daoImpl;

import db.HibernateHelper;
import db.dao.MessageDao;
import model.*;
import model.Enum.MessageStatus;
import model.response.GetChatMessagesResponse;
import model.response.Response;
import org.hibernate.Session;
import org.hibernate.query.Query;
import push.PostSocketController;
import utils.Util;

import java.util.Date;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    @Override
    public Response sendMessage(MessageSocket messageSocket) {
        try {
            MessageInfo messageInfo = new MessageInfo(messageSocket.getSenderUserId(), messageSocket.getMessage(), new Date(),
                    MessageStatus.POSTED.getMessageStatus(), messageSocket.getChatId(), messageSocket.getSenderName());
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", messageSocket.getSenderUserId());
            updateQuery.executeUpdate();
            session.save(messageInfo);
            Query query = session.createQuery("from ChatInfo where id = :chatId");
            query.setParameter("chatId", messageSocket.getChatId());
            List list = query.list();
            session.getTransaction().commit();
            ChatInfo chatInfo = ((ChatInfo) list.get(0));
            for (int i = 0; i < 2; i++) {
                PostSocketController postSocketController = new PostSocketController(
                        String.valueOf(i == 0 ? messageSocket.getSenderUserId() : (messageSocket.getSenderUserId() ==
                                chatInfo.getUserId() ? chatInfo.getOtherUserId() : chatInfo.getUserId())),
                        i == 0 ? new MessageUpdate(messageSocket.getId(), messageInfo.getId(),
                                MessageStatus.POSTED.getMessageStatus()) : messageInfo,
                        i == 0 ? "updateMessage " : "message ");
                postSocketController.start();
            }
            return new Response(true, "پیام ارسال شد");
        } catch (Exception e) {
            PostSocketController postSocketController = new PostSocketController(
                    String.valueOf(messageSocket.getSenderUserId()), new MessageUpdate(messageSocket.getId(), -1,
                    MessageStatus.FAILED.getMessageStatus()), "updateMessage ");
            postSocketController.start();
            return new Response(false, "ایراد در ارسال پیام");
        }
    }

    @Override
    public GetChatMessagesResponse getChatMessages(long chatId, long lastMessageId, long userId) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery("update UserInfo set lastActivityDate = :lastActivityDate where id = :id");
            updateQuery.setParameter("lastActivityDate", new Date());
            updateQuery.setParameter("id", userId);
            updateQuery.executeUpdate();
            Query query;
            if (lastMessageId != 0L) {
                query = session.createQuery("from MessageInfo where chatId = :chatId and id< :lastMessageId order by createDate desc ");
                query.setParameter("lastMessageId", lastMessageId);
            } else {
                query = session.createQuery("from MessageInfo where chatId = :chatId  order by createDate desc ");
            }
            query.setMaxResults(50);
            query.setParameter("chatId", chatId);
            List list = query.list();
            if (list != null && list.size() > 0) {
                list = Util.upsideList(list);
                Query chatQuery = session.createQuery("from ChatInfo where id = :chatId");
                chatQuery.setParameter("chatId", chatId);
                List chatList = chatQuery.list();
                ChatInfo chatInfo = ((ChatInfo) chatList.get(0));
                long otherUserId = userId == chatInfo.getUserId() ? chatInfo.getOtherUserId() : chatInfo.getUserId();
                UpdateStatusRange updateStatusRange = new UpdateStatusRange(((MessageInfo) list.get(0)).getId(),
                        ((MessageInfo) list.get(list.size() - 1)).getId());
                Query updateStatusQuery = session.createQuery("update MessageInfo set status= :status " +
                        "where chatId = :chatId and senderUserId= :senderUserId and " +
                        "id<= :lastMessageId and id>= :firstMessageId and status!= :status");
                updateStatusQuery.setParameter("firstMessageId", ((MessageInfo) list.get(0)).getId());
                updateStatusQuery.setParameter("lastMessageId", ((MessageInfo) list.get(list.size() - 1)).getId());
                updateStatusQuery.setParameter("chatId", chatId);
                updateStatusQuery.setParameter("senderUserId", otherUserId);
                updateStatusQuery.setParameter("status", MessageStatus.SEEN.getMessageStatus());
                updateStatusQuery.executeUpdate();
                PostSocketController postSocketController = new PostSocketController(String.valueOf(otherUserId),
                        updateStatusRange, "updateStatusRange ");
                postSocketController.start();
            }
            session.getTransaction().commit();
            return new GetChatMessagesResponse(true, null, lastMessageId != 0, list);
        } catch (Exception e) {
            return new GetChatMessagesResponse(false, "مشکل در دریافت پیام ها", false, null);
        }
    }

    @Override
    public void updateStatus(UpdateStatus updateStatus) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("update MessageInfo set status = :status where chatId = :chatId and id = :id");
            query.setParameter("chatId", updateStatus.getChatId());
            query.setParameter("id", updateStatus.getMessageId());
            query.setParameter("status", updateStatus.getStatus());
            query.executeUpdate();
            PostSocketController postSocketController = new PostSocketController(
                    String.valueOf(updateStatus.getSenderUserId())
                    , updateStatus, "updateStatus ");
            postSocketController.start();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.woorin.catudy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MemberMapper;
import org.woorin.catudy.model.ChattingUser;

public interface ChattingService {
	public void onOpen(Session session);
	public void onMessage(String msg, Session session) throws Exception;
	public void onClose(Session session);
	public void newWaiting(int room_no, int member_no, String password, String member_nick);
	public ChattingUser checkWaiting(ChattingUser u);
	public void enterChatting(ChattingUser u, Session session);
	public void leaveChatting(Session session);
	public String oneChat(Session session, String msg);
}

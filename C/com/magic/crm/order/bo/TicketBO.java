package com.magic.crm.order.bo;

import com.magic.crm.util.DateUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;

import com.magic.utils.StringUtil;
import com.magic.utils.Arith;
import com.magic.utils.MD5;
import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.order.dao.OrderGiftsDAO;
import com.magic.crm.order.dao.TicketDAO;
import com.magic.crm.order.dao.GiftTicketUseDAO;
import com.magic.crm.order.entity.Ticket;
import com.magic.crm.order.entity.OneTicket;
import com.magic.crm.order.entity.GiftTicketUse;
import com.magic.crm.order.entity.TicketMoney;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.order.entity.OrderGifts;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.promotion.entity.MbrGiftUseGroup;
import com.magic.crm.promotion.entity.Mbr_gift_item;
import com.magic.crm.promotion.entity.Mbr_gift_item_mst;
import com.magic.crm.promotion.dao.Mbr_gift_itemDAO;
import com.magic.crm.promotion.dao.MbrGiftUseGroupDAO;
import com.magic.crm.promotion.entity.Mbr_gift_money_by_order;
import com.magic.crm.promotion.dao.Mbr_gift_money_by_orderDAO;

/**
 * ��ȯҵ����
 * 
 * @author user
 * 
 */
public class TicketBO {

	/** ���ݿ����Ӷ��� * */
	private Connection conn = null;

	private TicketDAO ticketDao = null;

	private MbrGiftUseGroupDAO mbr_gift_use_groupDao = null;

	private Mbr_gift_itemDAO mbr_gift_itemDao = null;

	private Mbr_gift_money_by_orderDAO mbr_gift_money_by_orderDao = null;

	

	private MemberDAO memberDao = null;
	
	private GiftTicketUseDAO giftTicketUseDAO = null;
	
	private MemberGetAwardDAO member_awardDao = null;
	
	private OrderGiftsDAO order_giftsDAO = null;
	
	
	public TicketBO(Connection conn) {
		this();
		this.conn = conn;
		
	}
	
	public TicketBO() {
		
		ticketDao = new TicketDAO();
		mbr_gift_itemDao = new Mbr_gift_itemDAO();
		mbr_gift_use_groupDao = new MbrGiftUseGroupDAO();
		mbr_gift_money_by_orderDao = new Mbr_gift_money_by_orderDAO();
		
		memberDao = new MemberDAO();
		giftTicketUseDAO = new GiftTicketUseDAO();
		member_awardDao = new MemberGetAwardDAO();
		order_giftsDAO = new OrderGiftsDAO();
	}

	/**
	 * ��������
	 * 
	 * @param conn
	 */
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * ������ȯ
	 * 1.���־û���ȯɾ�������ش�����
	 * 2.���ڴ��е���Ч����ȯ���³־û���
	 * @param sessionData
	 * @throws Exception
	 */
	public void insertTicketUse (OrderForm data) throws Exception {

		
		//ɾ����ȯʹ�ü�¼��������ȯʹ�����
		order_giftsDAO.deleteByOrderId(conn, data.getOrderId());
		
		//�ڴ���������ȯ(�п�����ʧЧ��)
		List inputTickets = data.getCart().getTickets();
		
		Iterator iter = inputTickets.iterator();
		
		if (inputTickets != null && inputTickets.size() > 0) {
			
			//���������ȯ
			OrderGifts og = new OrderGifts();
			while (iter.hasNext()) {
				TicketMoney item = (TicketMoney) iter.next();
				
				//���ǵ���ȯ��״̬
				if (item.getUseStatus() == -1 || item.getMoney() == 0) {
					continue;
				}
				int type = getTicketTypeByNumber(item
						.getTicketCode());
				// ����ҳ���������ȯ�ŵõ���ȯ����
				OneTicket ticket = item.getTicket();
				//TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

				// ������ȯʹ�ñ�
				og.setOrderId(data.getOrderId());
				og.setGiftNumber(item.getTicketCode());
				//og.setDisAmt(getDisAmtByTicketNumber(data, item.getTicketCode()));
				og.setDisAmt(item.getMoney());
				og.setAward_id(item.getMbAward().getID());
				order_giftsDAO.insert(conn, og);

				// ������ȯʹ����ʷ��
				MbrGetAwardDAO2.useGiftNumber(conn,item.getMbAward());
				
			}

		}
	}
	
	/**
	 * �����ȯ���� session�е�����
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public boolean checkMainTick(OrderForm data) throws Exception {

		
		List inputTickets = data.getCart().getTickets();
		//���������ȯ
		TicketMoney item = (TicketMoney) inputTickets.get(inputTickets.size()-1);
			
		// ����ҳ���������ȯ�ŵõ���ȯ����
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, item
					.getTicketCode());
		if (ticket == null) {
			throw new Exception("�Ҳ�����ȯ");
				
		}
		// �жϿ�ʼ���ڽ�������
		java.util.Date order_date =
		DateUtil.getDate(data.getCart().getOrder().getCreateDate(),"yyyy-MM-dd HH:mm:ss") ; //modified by user 2007-04-13
		if (order_date == null) { //���û�ж������ڣ�ȡϵͳʱ��
			order_date = new java.util.Date();
		}
			
		java.util.Date begin_date = DateUtil.getDate(ticket.getTicket().getStartDate(),"yyyy-MM-dd HH:mm:ss");
		java.util.Date end_date = DateUtil.getDate(ticket.getTicket().getEndDate(),"yyyy-MM-dd HH:mm:ss");
		if (order_date.after(end_date) || order_date.before(begin_date)) {
				
			throw new Exception("�������ڲ�����");
				
		}

		//�ж�ע������
		int member_id = MemberDAO.getMemberID(conn, data.getCart().getMember().getCARD_ID());//modified by user 2007-04-13
		Member memberInfo = memberDao.DetailMembers(conn, member_id + "");
			
		// �ж��Ƿ�ͻ�Ա����ҹ�
		if (ticket.getTicket().getIsMemberLevel() != -1
					&& ticket.getTicket().getIsMemberLevel() > memberInfo.getLEVEL_ID()) {
			throw new Exception("��Ա���𲻷���");
				
		}
			
		// ���ϻ�Ա
		boolean old_member = data.getCart().getMember().isOldMember();
		if (old_member) { // �ϻ�Ա
			if (ticket.getTicket().getIsOldMember() != 1) { // �ϻ�Ա������
					throw new Exception("�ϻ�Ա������");
			} else { //�ϻ�Ա����
					
			}
		} else { // �»�Ա
			if (ticket.getTicket().getIsNewMember() != 1) { // �»�Ա������
				throw new Exception("�»�Ա������");
					
			} else { //�»�Ա����
					
				//��Աע������
				java.util.Date member_date = DateUtil.getDate(memberInfo
							.getCREATE_DATE(), "yyyy-MM-dd HH:mm:ss");
				if (ticket.getTicket().getMemberEndDate() != null && ticket.getTicket().getMemberStartDate() != null) {
					if (member_date.after(DateUtil.getDate(ticket.getTicket().getMemberEndDate(),"yyyy-MM-dd HH:mm:ss"))
								|| member_date.before( DateUtil.getDate(ticket.getTicket().getMemberStartDate(), "yyyy-MM-dd HH:mm:ss") )) {
						throw new Exception("ע�����ڲ�����");
					}
				}
					
					
			}
		}

		// �Ƿ�����ʹ��
		if (ticket.getTicket().getIsWeb() == 1) {
			throw new Exception("ֻ������ʹ��");
		}

		// ������С���
		if (data.getCart().getNotGiftMoney() < ticket.getTicket().getOrderMoney()) {
			throw new Exception("�����϶�����С���");
		}
		return true;
	}

	/**
	 * ������ȯ�����ַ�������ȯ����
	 * 
	 * @param conn
	 * @param giftNumber
	 * @return
	 * @throws SQLException
	 */
	public static int getTicketTypeByNumber(String giftNumber) throws Exception {

		if (giftNumber == null || giftNumber.length() == 0)
			throw new java.lang.IllegalArgumentException("��������Ϊ�հ����ϴ�");

		char firstChar = giftNumber.charAt(0);
		//boolean isNum = StringUtil.isNum(String.valueOf(firstChar));
		//if (isNum) {
		//	return 3;// ���˿�
		//} else {
			switch (firstChar) {
			case 'P':// ������ȯ
				return 4;
			case 'E':// ˽����ȯ
				return 5;
			case 'N':// ��ͨ��ȯ
				return 2;
			default:// �����ȯ
				return 1;

			}
		//}

	}

	/**
	 * ��mbr_gift_use_group�е���Ч��¼������� ����list example: 4*E000001*3*10000
	 * 
	 * @param list
	 * @throws Exception
	 */
	public List getGiftUseGroupList() throws Exception {
		List list  = new ArrayList();
		Collection coll = mbr_gift_use_groupDao.getAllValidRecords(this.conn);
		Iterator iter = coll.iterator();
		
		StringBuffer mulStr = new StringBuffer();
		String temp = null;
		int i = 0;
		while (iter.hasNext()) {

			MbrGiftUseGroup info = (MbrGiftUseGroup) iter.next();
			String group = info.getGroupNO();
			int type = info.getGiftType();
			String number = info.getGiftNumber();

			// ��һ����¼
			if (i == 0) {
				temp = group;
			}

			if (group.equals(temp)) {
				mulStr.append(type);
				mulStr.append("*");
				mulStr.append(number);
				mulStr.append("*");
			} else { // ����¼����һ����
				temp = group;
				//System.out.println(""+mulStr.substring(0, mulStr.length()-1));
				list.add(mulStr.substring(0, mulStr.length()-1));// ���������list
				mulStr.delete(0, mulStr.length()); // ��մ�
				mulStr.append(type);
				mulStr.append("*");
				mulStr.append(number);
				mulStr.append("*");
			}

			// ����ǲ������һ����¼
			if (i == MbrGiftUseGroupDAO.getGiftUseGroupRowCount(conn) - 1) {
				list.add(mulStr.substring(0, mulStr.length()-1));// �����һ�������list

			}
			
			i++;
		}
		return list;
	}

	/**
	 * �����ȯ�� ʹ�ó��ϣ�������ȯͬʱʹ�� ��ȯ���ݴ�session��ȡ����
	 * 
	 * @param conn
	 * @param data
	 * @return false-��������ȯ��
	 * @throws Exception
	 */
	public boolean checkGiftUseGroup(OrderForm data) throws Exception {

		//������ȯ
		List arrTicket = data.getCart().getTickets();
		String inputStr = null;
		
		List list = getGiftUseGroupList();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {// ������ȯ�����ñ�
			String listStr = (String) iter.next();
			System.out.println(listStr);
			boolean flag = true;
			for (int i = 0; i < arrTicket.size(); i++) { // ���������ÿ����ȯ
		
				TicketMoney item = (TicketMoney) (arrTicket.get(i));
				// ��ȯ�����
				//int type = getTicketTypeByNumber(item.getTicketCode());
				int type = Integer.parseInt(item.getTicketType());
				inputStr = type+"";
				inputStr += "*";
				if (type == 3 ) {//���˿�
					inputStr+= "any";
					
				} else {
					//inputStr += item.getTicketCode();
					inputStr += item.getTicketHeader();
				}
				
				if (listStr.indexOf(inputStr) == -1) {
					flag = false;
					break;
				}
			
			}

			if (flag) {// ���ҳ�����е���ȯ�����������ȯ��
				return true;
			}
			
		}
		
		return false;
	}

	/**
	 * ��Ʒ����(ֻ����ȯ���д��жϣ����˿������������ж�)
	 * 
	 * @param data
	 * @return 0-ok
	 * @return -2-�б�ѡ��Ʒû�з���
	 
	 * @throws Exception
	 */
	public int checkGiftItem(OrderForm data) throws Exception {

		//������ȯ
		List ticketMoney = data.getCart().getTickets();

		// ������Ʒ
		List items = data.getCart().getItems();
		
		//�õ��ո��������ȯ
		TicketMoney item = (TicketMoney) ticketMoney.get(ticketMoney.size()-1);

		// ����ҳ���������ȯ�ŵõ���ȯ����
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

		// ��Ʒ�鿪��
		if (ticket.getTicket().getProductGroupId() == -1) {
			return 0;
		}

		// �õ���ȯ���ڵĲ�Ʒ��
		int groupId = ticket.getTicket().getProductGroupId();

		// �õ���Ʒ��ͷ��
		Mbr_gift_item_mst mst = Mbr_gift_itemDAO.getHeadByPK(conn, groupId);
		if (mst == null) {
			throw new java.lang.Exception("����ȯ��Ҫ������Ʒ��");
		}

		//�õ���Ʒ������
		int itemGroupType = mst.getItemgroup_type();
		
		int count = 0;
		if (itemGroupType == 0) { //��Ʒ��� 
		
			// �õ���Ʒ����ϸ
			Collection dtl = Mbr_gift_itemDAO.getLinesByFK(conn, groupId);
			int flag = 0;
			//��Ʒ�������
			for (int j = 0; j < dtl.size(); j++) { // ������Ʒ����ϸ
				Mbr_gift_item gift_item = (Mbr_gift_item) ((List) dtl).get(j);
				
				for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
					ItemInfo item_info = (ItemInfo) items.get(k);
					//if (item_info.getItemId() == gift_item.getItem_id()) {
					//	count++; // ��ͬ��Ʒ��
					//}
				}
				if (gift_item.getIs_must() == 1) {
					//���ÿһ�����빺��Ĳ�Ʒʱ���������ô˱��
					flag = 0;
					for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
						ItemInfo item_info = (ItemInfo) items.get(k);
						
						//if (item_info.getItemId() == gift_item.getItem_id()) {
						//	flag = 1;//��������
						//	break;
						//}
					}
					//����һ�������Ʒ���ҽ����flag
					if (flag == 0) { //������
						return -302;
					}
				}
				
			}
			System.out.println("****�����˹涨�Ĳ�Ʒ��:"+count);
			if ( count < mst.getMin_item_count()) {
					
				return -301;
			}
			
			
		} else { //���ݲ�Ʒ���ͣ�����Ҫ�жϲ�Ʒ�飬ֻҪ�жϹ��ﳵ��������Ʒ�������Ƿ�һ�£������Ƿ���ϼ��ɣ�
			
			for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
				ItemInfo item_info = (ItemInfo) items.get(k);
				
				int item_type = 0;//ProductDAO.getItemTypeByPk(this.conn,item_info.getItemId());
				
				if (item_type == itemGroupType) {
					count += item_info.getItemQty();
					if (count >= mst.getMin_item_count()) {
						break;
					}
				}
				
				if (k == items.size() - 1 && count < mst.getMin_item_count()) {
					return -303;
				}
				
			}
			
			/**
			int temp;
			for (temp = 1; temp <=6; temp++) { //1-6�ֱ�����������
				//���ÿ����Ʒ���͵������ǣ����뽫����������
				count = 0;
				for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
					ItemInfo item_info = (ItemInfo) items.get(k);
					
					int item_type = ProductDAO.getItemTypeByPk(this.conn,item_info.getItemId());
					
					if (item_type == temp) {
						count ++;
					}
					
				}
				if (count >= mst.getMin_item_count()) {//һ�� �������������Ĳ�Ʒ�����ڹ涨����Ŀ
					
					break;
				}
			}
			//�������е����Ͷ�����������
			if (temp == 7 && count < mst.getMin_item_count()) {
				return -303;
			}
			*/
			
		}
		return 0;

	}

	/**
	 * ���ݶ����������ȯ���ý��
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public double checkGiftMoneyByOrder(OrderForm data, Ticket ticket)
			throws Exception {

		Collection coll = mbr_gift_money_by_orderDao.findByGiftNumber(
				this.conn, ticket.getGiftNumber());
		double no_gift_money = data.getCart().getNotGiftMoney();
		double dis_amt = -1;
		while (coll.iterator().hasNext()) {
			Mbr_gift_money_by_order info = (Mbr_gift_money_by_order) coll
					.iterator().next();
			if (no_gift_money >= info.getOrder_require()) {
				dis_amt = info.getDis_amt();
				break;
			}
		}
		return dis_amt;
	}

	/**
	 * ͨ����Ա��������ȯ���ý��
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public double checkGiftMoneyByLevel(OrderForm data, Ticket ticket)
			throws Exception {

		Collection coll = mbr_gift_money_by_orderDao.findByGiftNumberLevel(
				this.conn, ticket.getGiftNumber(), data.getCart().getMember().getLEVEL_ID());
		double no_gift_money = data.getCart().getNotGiftMoney();
		double dis_amt = -1;
		while (coll.iterator().hasNext()) {
			Mbr_gift_money_by_order info = (Mbr_gift_money_by_order) coll
					.iterator().next();
			if (no_gift_money >= info.getOrder_require()) {
				dis_amt = info.getDis_amt();
				break;
			}
		}
		return dis_amt;
	}
	
	/**
	 * ��ȯ���ǰ
	 * @param data
	 * @return 0 - ����
	 * @throws Exception
	 */
	public int checkTicket(OrderForm data,TicketMoney newItem) throws Exception {
		if (data.getOtherGiftNumber() == null || data.getOtherGiftNumber().length() == 0) {
			return -102;
		}
		
		//������ȯ�ŵõ���ǰ��ȯ����
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, data.getOtherGiftNumber());
		if(ticket == null) {
			return -100;
		}
		newItem.setTicket(ticket);
		
		//��ȯ����
		if (ticket.getIsNeedPass() == 1) {
			if (data.getOtherGiftPassword() == null) {
				return -103;
			}
			if (!ticket.getPass().equalsIgnoreCase(new MD5().getMD5ofStr(data.getOtherGiftPassword()))) {
				return -103;
			}
			//if (!ticket.getPass().equalsIgnoreCase(data.getOtherGiftPassword())) {
				//return -103;
			//}
		}
		
		
		//�ڴ���������ȯ
		List inputTickets = data.getCart().getTickets();
		Iterator iter = inputTickets.iterator(); 
		
		MemberAWARD ma = null;
		
		// �жϱ������Ƿ�ʹ���˴���ȯ������Ѿ�ʹ���˸���ȯ���޸Ķ�������
		// ��ô����ʹ�õ���ȯidȡ����Ӧ��Ϣ�����������ȯ��ȥȡһ�����ϵ���Ϣ
		OrderGifts og = order_giftsDAO.getRecordByTicket(conn, data.getOtherGiftNumber(), data.getOrderId());
		if (og == null) { 
			ma = member_awardDao.getAvailableTicket(conn, data.getOtherGiftNumber(), data.getMbId());
			if (ma == null) {
				//����ǹ�����ȯ��ȯ���������
				if(ticket.getTicket().getGiftType() == 4 ) {
					ma = member_awardDao.insertMemberTicket(conn,data.getOtherGiftNumber(), data.getMbId());
				} else if (ticket.getTicket().getGiftType() == 6 ) {
					if ( TicketDAO.checkIfused(conn, data.getOtherGiftNumber()) == 1) {
						return -99;
					} else {
						ma = member_awardDao.insertMemberTicket(conn,data.getOtherGiftNumber(), data.getMbId());	
					}
				} else {
					return -96; 
				}
			}
			
			
		} else {
			ma = member_awardDao.getTicketByAwardId(conn,og.getAward_id());
		}
		
		newItem.setMbAward(ma);
		
		//�����ȯ�Ƿ��ù����ڴ����Ƿ���ڸ���ȯ��
		while (iter.hasNext()) {
			TicketMoney item = (TicketMoney)iter.next();
			
			if (item.getTicketCode().equals(data.getOtherGiftNumber())) {
				return -99;
			}
			
		}
		
		//�Ѿ�ʹ�ö��ٴ� //����ʹ���˶��ٴ�
		GiftTicketUse temp = new GiftTicketUse();
		temp.setMemberId(data.getMbId());
		temp.setTicketNum(data.getOtherGiftNumber());
		int usedTotal = giftTicketUseDAO.getTotalUseOfTicket(conn, temp);
		int person_used_total = giftTicketUseDAO.getPersonUseOfTicket(conn, temp);
		
		if (og != null) {//�������Ѿ�ʹ����
			person_used_total --;
			usedTotal --;
		}
		
		//��������ʹ�ô���
		if (usedTotal >= ticket.getTicket().getAmount()) {
			return -98;
		}
		
		//�����˸���ʹ�ô���
		if (person_used_total >= ticket.getTicket().getPersonNum()) {
			return -97;
		}
		
		//�������Ƿ�������ȯ����  ������ȯ��˽�а���ȯ�ԷǴ���Ʒ����,˽�в�����ȯ���ܲ�Ʒ��
		if(ticket.getTicket().getGiftType() == 6) {
			if ( data.getCart().getTotalMoney() < ticket.getTicket().getOrderMoney()) {
				return -95;
			}
		} else {
			if ( data.getCart().getNotGiftMoney() < ticket.getTicket().getOrderMoney()) {
				return -95;
			}
		}
		
		return 0;
	}
	
	/**
	 * ������ȯ��Ϊ0��״̬��Ϊ��Ч
	 * @param data
	 * @throws Exception
	 */
	public void setTicketMoneyZero(OrderForm data) throws Exception {
		List inputTickets = data.getCart().getTickets();
		Iterator iter = inputTickets.iterator();
		while (iter.hasNext()) {
			TicketMoney item = (TicketMoney)iter.next();
			if (!item.getTicketType().equals("3")) {
				item.setMoney(0);
				item.setUseStatus(-1);
			}
			
		}
	}
	
	public void resetTicket(TicketMoney item) throws Exception {
		if ( item == null )
			return;
		item.setMoney(0.00);
		item.setUseStatus(-1);
		item.setItemType(-1);
		item.setItemTypeMoney(0);
		item.setDisType(1);
		item.setIsDiscount("N");
	}
	
	/**
	 * �����ύ���������¼۲���ȯ
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public void reCheckTicket(OrderForm data) throws Exception {
		
		List inputTickets = data.getCart().getTickets();
		if (inputTickets.size() == 0) {
			return;
		}
		
		while (inputTickets.size()>0) {
			
			TicketMoney item = (TicketMoney)(inputTickets.get(inputTickets.size() - 1)); 
			int type = getTicketTypeByNumber( item.getTicketCode() );
			try {
				//�ж���ȯ�����ϵ�����
				if (type != 3) {
					if (!checkMainTick(data)) {
						resetTicket(item);
						continue;
					
					}
					//��Ʒ���ж�
					//if (checkGiftItem(data) != 0) { // ����д���
					//	resetTicket(item);
					//	continue;
						
					//}
					
					//���ý��
					if (checkTicketMoney(data) != 0) {
						resetTicket(item);
						continue;
						
					}
				} 
				//ȥ�����һ��
				
			}catch(Exception ex) {
				resetTicket(item);
				continue;
			} finally {
				inputTickets.remove(inputTickets.size() - 1);
			}
			
		}
		
		
	}
	
	/**
	 * ������ȯ���
	 * @return
	 */
	public int checkTicketMoney(OrderForm data) throws Exception {
		
		List inputTickets = data.getCart().getTickets();
		double ticket_money = 0;
		
		//�õ��ո��������ȯ
		TicketMoney item = (TicketMoney) inputTickets.get(inputTickets.size()-1);
		
		int ticket_type = TicketBO.getTicketTypeByNumber(item.getTicketCode());
		
		if (ticket_type == 3) { // ���˿�
			;
		} else if (ticket_type == 1 || ticket_type == 2 || ticket_type == 4
				|| ticket_type == 5) {// �����ȯ����ͨ��ȯ��������ȯ��˽����ȯ
			OneTicket ticket = item.getTicket(); 
			//TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

			if (ticket.getTicket().getIsMoneyForOrder() == 0) {// ���Ͷ������ҹ�

				if (ticket.getTicket().getIsMemberLevel() == -1) {// ���ͻ�Ա����ҹ�
					ticket_money = ticket.getTicket().getGiftMoney(); // ȡ��ȯĬ�ϼ۸�
					item.setMoney(ticket_money);
					item.setUseStatus(0);
					
				} else { // �ͻ�Ա����ҹ�
					if (data.getCart().getMember().getLEVEL_ID() >= ticket.getTicket().getIsMemberLevel()) {// �жϻ�Ա�����Ƿ����
						item.setMoney(ticket.getTicket().getGiftMoney());
						item.setUseStatus(0);
						
					} else {
						return -6;
					}
				}
			} else { // �Ͷ������ҹ�
				Collection gift_money = null;
				if (ticket.getTicket().getIsMemberLevel() == -1) { // ���ͻ�Ա����ҹ�
					gift_money = mbr_gift_money_by_orderDao
							.findByGiftNumber(conn, item.getTicketHeader());
					if (gift_money.size() == 0) {
						
						return -5;
					}
					ticket_money = getDisAmt(gift_money, data, item);
					if (ticket_money == 0) {
						return -7;
					}
					
					item.setMoney(ticket_money);
					item.setUseStatus(0);
					
				} else {// �ͻ�Ա����ҹ�
					if (data.getCart().getMember().getLEVEL_ID() >= ticket.getTicket().getIsMemberLevel()) {
						gift_money = mbr_gift_money_by_orderDao
								.findByGiftNumberLevel(conn, item
										.getTicketHeader(), data.getCart().getMember().getLEVEL_ID());
						
						if (gift_money.size() == 0) {
							
							return -5;
						}
						ticket_money = getDisAmt(gift_money, data, item);
						if (ticket_money == 0) {
							return -7;
						}

						
						item.setMoney(ticket_money);
						item.setUseStatus(0);
						
					} else {
						// ticket_money = 0;
						
						return -6;
					}

				}
			}

		} else {
			throw new java.lang.IllegalArgumentException("û����������ȯ����");
		}
		return 0;
	}
	
	/**
	 * ���������action������
	 * 
	 * @param data
	 * @throws Exception
	 */
	public int checkTicket(OrderForm data) throws Exception {

		int rtn = 0;
		List inputTickets = data.getCart().getTickets();

		// �ж���ȯ�����ϵ�����
		if (!checkMainTick(data)) {
			return -1;
		}

		// ��ȯ���жϣ���Ҫ������ȯһ���飩
		if (inputTickets.size() > 1) { // ����Ƕ�����ȯ�ж��ǲ���������ȯ����
			if (!checkGiftUseGroup(data)) {
				return -2;
			}
		}

		// ��Ʒ���ж�
		int item_result = checkGiftItem(data);
		if (item_result != 0) { // ����д���
			return item_result;
		}
		
		//���õ��ý��
		rtn = checkTicketMoney(data);
		
		return rtn;
	}

	/**
	 * �õ��ֿ۽��
	 * modified by user 2007-06-22
	 * @param coll
	 * @return
	 */
	private double getDisAmt(Collection coll, OrderForm data, TicketMoney ticket) throws Exception {
		
		
		if (coll==null || coll.size() == 0) {
			throw new Exception("ϵͳ�����˺Ͷ������ü���ҹ�����û�����ù����������Ա��ϵ!");
		} 
		
		Mbr_gift_money_by_order first = (Mbr_gift_money_by_order)((List)coll).get(0);
		
		//����ֵ(ʵ�ʵ��ý��)
		double ticketMoney = 0;
		
		//�����з�����Ʒ���Ҫ��Ľ��
		double total_money=0;
		
		//��
		int itemgroup_type = -1;
		
		//�������������Ʒ
		List items = data.getCart().getItems();
		
		if (first.getItem_group_id() == -1) { //�ֿ۽���������Ʒ��
			if (first.getDis_type() == 1) {//��һ������������ԣ�
				ticket.setDisType(1);
				total_money = data.getCart().getNotGiftMoney();
			} else { //�ۼƶ���(�������)
				ticket.setDisType(2);
				total_money = OrderDAO.calcFinishedOrderMoneyByPeriod(conn, first.getBegin_date(), first.getEnd_date(), data.getMbId()); 
			}
			
		} else { //�ֿ�ѡ���˲�Ʒ��
			
			//��ģʽ�������ۼƶ������ԣ��ֿ۽������Ϊ0��
			if (first.getDis_type() == 1) { //��һ����������ԣ�
				ticket.setDisType(1);
				//�õ���Ʒ��ͷ��
				Mbr_gift_item_mst mst = Mbr_gift_itemDAO.getHeadByPK(conn, first.getItem_group_id());
				if (mst == null) {
					throw new java.lang.Exception("����ȯ��Ҫ������Ʒ��");
				}
				
				
				//ѡ�����
				itemgroup_type = mst.getItemgroup_type();
				
				if (itemgroup_type == 0) { //��һ��Ʒ
					//�õ���Ʒ����ϸ
					Collection dtl = Mbr_gift_itemDAO.getLinesByFK(conn, first.getItem_group_id());
					if (dtl == null || dtl.size()==0) {
						throw new java.lang.Exception("����ȯ����Ҫ���ò�Ʒ��ϸ");
					}
					for (int j = 0; j < dtl.size(); j ++) {
						Mbr_gift_item gift_item = (Mbr_gift_item) ((List) dtl).get(j);
						for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
							ItemInfo item = (ItemInfo)items.get(k);
							//if (gift_item.getItem_id() == item.getItemId()) {
							//	total_money += item.getItemMoney();
							//	break;
							//}
						}
					}
					//�����������صֿ۽��
					
				} else { //��Ʒ����
					/**
					 * ��Ʒ����������7��������Ʒ�������ж�
					 */
					if (itemgroup_type <= 6) {
						for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
							ItemInfo item = (ItemInfo)items.get(k);
							int item_type = 0;//ProductDAO.getItemTypeByPk(this.conn,item.getItemId());
							if (item_type == itemgroup_type) { //����һ�²�����ý��
								total_money += item.getItemMoney();
							}
						}
					}
					if (itemgroup_type == 8) { //������Ʒ��
						for (int k = 0; k < items.size(); k++) {// ����������Ʒ��ϸ
							ItemInfo item = (ItemInfo)items.get(k);
							int item_type = 0;//ProductDAO.getItemTypeByPk(this.conn,item.getItemId());
							if (item_type == 2 || item_type == 3 || item_type == 5) { //����һ�²�����ý��
								total_money += item.getItemMoney();
							}
						}
					}
					
					//�����������صֿ۽��
				}
			} else {//�ۼƶ���������ԣ�
				//ticket.setDisType(2);
				//total_money = 0d;
				throw new Exception("��ȯ�����������������Ա��ϵ!(�������ü����������ԡ��ۼƶ������ԣ���ʱ��δ��ͨ)");
			}
			
			
		}
		ticket.setItemType(itemgroup_type);
		ticket.setItemTypeMoney(total_money);
		
		//���еĶ������ü������
		Iterator iter = coll.iterator();
		
		//�������еĶ������ü������ֱ�����ҵ�һ�����������Ĺ���������ý����򷵻�0��
		while (iter.hasNext()) {
			
			//���еĵ��ü���
			Mbr_gift_money_by_order info = (Mbr_gift_money_by_order) iter
					.next();
				
			if (total_money >= info.getOrder_require()) {//�ҵ�����Ӧ�ĵ��ù���
				if (info.getIs_discount().equals("N")) { //���ۿ۷�ʽ
					ticketMoney = info.getDis_amt();
					ticket.setIsDiscount("N");
				} else {//�ۿ۷�ʽ
					ticketMoney = Arith.roundX(info.getDis_amt() * total_money, 1);//����С��ȥ����
					ticket.setIsDiscount("Y");
				}
				
				break;
			}
			
			/**
			//���ݼ��������total_money���������ܹ����õĽ�����������ݵֿ۲�����������ֿ۵Ľ��
			if (info.getDis_type() == 1) {//��һ�������
				ticket.setDisType(1);
				if (total_money >= info.getOrder_require()) {//�ҵ�����Ӧ�ĵ��ù���
					if (info.getIs_discount().equals("N")) { //���ۿ۷�ʽ
						ticketMoney = info.getDis_amt();
						ticket.setIsDiscount("N");
						ticket.setItemType(itemgroup_type);
						ticket.setItemTypeMoney(total_money);
					} else {//�ۿ۷�ʽ
						ticketMoney = Arith.round(info.getDis_amt() * total_money, 1);
						ticket.setIsDiscount("Y");
						ticket.setItemType(itemgroup_type);
						ticket.setItemTypeMoney(total_money);
					}
					break;
				}
			}else {
				ticket.setDisType(2);
				//�õ�ĳ��ʱ���ڶ�������ܺͣ���ɵĶ�����
				
				if (total_money >= info.getOrder_require()) { //�ҵ�����Ӧ�ĵ��ù���
					if (info.getIs_discount().equals("N")) {//���ۿ۷�ʽ
						ticketMoney = info.getDis_amt();
						ticket.setIsDiscount("N");
					} else {//�ۿ۷�ʽ
						ticketMoney = Arith.round(info.getDis_amt() * total_money, 1);
						ticket.setIsDiscount("Y");
					}
					break;
				}
				
			}
			*/
		}
		return ticketMoney;
	}

	/**
	 * ���ڴ��еõ���Ӧ��ȯ�ĵֿ۽��
	 * ע�⣺�ۼƶ�������һ������ʱδ����
	 * @param moneyTicket
	 * @param ticketNumber
	 * @return
	 */
	public double getDisAmtByTicketNumber(OrderForm data, String ticketNumber)
			throws Exception {
		Iterator iter = data.getCart().getTickets().iterator();
		while (iter.hasNext()) {
			TicketMoney ticket = (TicketMoney) iter.next();
			if (ticketNumber.equals(ticket.getTicketCode())) {
				//if (ticket.getIsDiscount().equals("N")) {
					return ticket.getMoney();
				///} else {
					//return Arith.round(ticket.getMoney() * data.getCart().getNotGiftMoney(), 1);
				//}
				
			}
		}
		return -1;
	}
	
	/**
	 * ��OrderGiftsת����TicketMoney����
	 * @param coll
	 * @throws Exception
	 */
	public static Collection changeTicketMoneyList(Collection coll) throws Exception {
		Collection newColl = new ArrayList();
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			OrderGifts og = (OrderGifts)iter.next();
			TicketMoney tm = new TicketMoney();
			tm.setTicketCode(og.getGiftNumber());
			int type = getTicketTypeByNumber(og.getGiftNumber());
			tm.setTicketType(type+"");
			tm.setMoney(og.getDisAmt());
			newColl.add(tm);
		}
		coll.clear();
		return newColl;
	}
	/**
	 * ��OrderGiftsת����TicketMoney����
	 * @param coll
	 * @throws Exception
	 */
	public  void changeTicketMoneyList2(List coll) throws Exception {
		int len = coll.size();
		for (int i = 0; i < len; i ++) {
			OrderGifts og = (OrderGifts)(coll.get(0));
			TicketMoney newItem = new TicketMoney();
			MemberAWARD award = new MemberAWARD();
			newItem.setMbAward(award);
			newItem.getMbAward().setID((int)og.getAward_id());
			newItem.setTicketCode(og.getGiftNumber());
			OneTicket ticket = TicketDAO.getTicketByNumber2(conn, og.getGiftNumber());
			newItem.setTicket(ticket);
			newItem.setTicketHeader(ticket.getTicket().getGiftNumber());
			newItem.setTicketCode(og.getGiftNumber());
			newItem.setTicketType(String.valueOf(ticket.getTicket().getGiftType()));
			newItem.setMoney(newItem.getTicket().getTicket().getGiftMoney());
			newItem.setItemTypeMoney(newItem.getTicket().getTicket().getOrderMoney());
			
			
			
			coll.add(newItem);
			coll.remove(0);
		}
		
	}
	/**
	 * ȡ����ȯ
	 * @param data
	 * @throws Exception
	 */
	public void cancelTicket(OrderForm order) throws Exception {
		
		Collection coll = order_giftsDAO.getRecordsByOrderId(conn, order.getOrderId());
		Iterator iter = coll.iterator();
		int i ;
		while (iter.hasNext()) {
			OrderGifts og = (OrderGifts) iter.next();
			int type = getTicketTypeByNumber(og.getGiftNumber());
			if (type == 3) { //���˿�
				
			} else { //������ȯ
				i = giftTicketUseDAO.addUseNumber(conn, order.getMbId(), og.getGiftNumber());
				if (i != 1) {
					throw new Exception("������ȯʧ��");
				}
				//�����˽����ȯ�������ݴ��״̬
				if (type == 1 || type == 5) {
					MemberGetAwardDAO.resetTicketStatus(conn, order.getMbId(), og.getGiftNumber());
				}
			}
		}
	}
	
	/**
	 * �жϵ�ǰ��ȯ�Ƿ��Ѿ����ڴ���
	 * @param tickets<ticketMoney>
	 * @param number
	 * @return
	 */
	public static boolean isInMemory (Collection tickets, String number) throws Exception {
		
		Iterator iter = null;
		iter = tickets.iterator();
		while (iter.hasNext()) {
			TicketMoney item = (TicketMoney)iter.next();
			if (number.equals(item.getTicketCode())) {
				return true;
			}
			//��������˿������Ƿ��Ѿ��ù���
			if (getTicketTypeByNumber(number) == 3 && item.getTicketType().equals("3")) { //���˿�
				return true;
			}
		}
		return false;
	}
}

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
 * 礼券业务类
 * 
 * @author user
 * 
 */
public class TicketBO {

	/** 数据库连接对象 * */
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
	 * 设置连接
	 * 
	 * @param conn
	 */
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 插入礼券
	 * 1.将持久化礼券删除，返回次数。
	 * 2.将内存中的有效的礼券重新持久化。
	 * @param sessionData
	 * @throws Exception
	 */
	public void insertTicketUse (OrderForm data) throws Exception {

		
		//删除礼券使用记录，更新礼券使用情况
		order_giftsDAO.deleteByOrderId(conn, data.getOrderId());
		
		//内存中所有礼券(有可能是失效的)
		List inputTickets = data.getCart().getTickets();
		
		Iterator iter = inputTickets.iterator();
		
		if (inputTickets != null && inputTickets.size() > 0) {
			
			//重新添加礼券
			OrderGifts og = new OrderGifts();
			while (iter.hasNext()) {
				TicketMoney item = (TicketMoney) iter.next();
				
				//考虑到礼券的状态
				if (item.getUseStatus() == -1 || item.getMoney() == 0) {
					continue;
				}
				int type = getTicketTypeByNumber(item
						.getTicketCode());
				// 根据页面输入的礼券号得到礼券对象
				OneTicket ticket = item.getTicket();
				//TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

				// 插入礼券使用表
				og.setOrderId(data.getOrderId());
				og.setGiftNumber(item.getTicketCode());
				//og.setDisAmt(getDisAmtByTicketNumber(data, item.getTicketCode()));
				og.setDisAmt(item.getMoney());
				og.setAward_id(item.getMbAward().getID());
				order_giftsDAO.insert(conn, og);

				// 更新礼券使用历史表
				MbrGetAwardDAO2.useGiftNumber(conn,item.getMbAward());
				
			}

		}
	}
	
	/**
	 * 检查礼券主表 session中的数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public boolean checkMainTick(OrderForm data) throws Exception {

		
		List inputTickets = data.getCart().getTickets();
		//刚输入的礼券
		TicketMoney item = (TicketMoney) inputTickets.get(inputTickets.size()-1);
			
		// 根据页面输入的礼券号得到礼券对象
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, item
					.getTicketCode());
		if (ticket == null) {
			throw new Exception("找不到礼券");
				
		}
		// 判断开始日期结束日期
		java.util.Date order_date =
		DateUtil.getDate(data.getCart().getOrder().getCreateDate(),"yyyy-MM-dd HH:mm:ss") ; //modified by user 2007-04-13
		if (order_date == null) { //如果没有订单日期，取系统时间
			order_date = new java.util.Date();
		}
			
		java.util.Date begin_date = DateUtil.getDate(ticket.getTicket().getStartDate(),"yyyy-MM-dd HH:mm:ss");
		java.util.Date end_date = DateUtil.getDate(ticket.getTicket().getEndDate(),"yyyy-MM-dd HH:mm:ss");
		if (order_date.after(end_date) || order_date.before(begin_date)) {
				
			throw new Exception("订单日期不符合");
				
		}

		//判断注册日期
		int member_id = MemberDAO.getMemberID(conn, data.getCart().getMember().getCARD_ID());//modified by user 2007-04-13
		Member memberInfo = memberDao.DetailMembers(conn, member_id + "");
			
		// 判断是否和会员级别挂钩
		if (ticket.getTicket().getIsMemberLevel() != -1
					&& ticket.getTicket().getIsMemberLevel() > memberInfo.getLEVEL_ID()) {
			throw new Exception("会员级别不符合");
				
		}
			
		// 新老会员
		boolean old_member = data.getCart().getMember().isOldMember();
		if (old_member) { // 老会员
			if (ticket.getTicket().getIsOldMember() != 1) { // 老会员不能用
					throw new Exception("老会员不能用");
			} else { //老会员能用
					
			}
		} else { // 新会员
			if (ticket.getTicket().getIsNewMember() != 1) { // 新会员不能用
				throw new Exception("新会员不能用");
					
			} else { //新会员能用
					
				//会员注册日期
				java.util.Date member_date = DateUtil.getDate(memberInfo
							.getCREATE_DATE(), "yyyy-MM-dd HH:mm:ss");
				if (ticket.getTicket().getMemberEndDate() != null && ticket.getTicket().getMemberStartDate() != null) {
					if (member_date.after(DateUtil.getDate(ticket.getTicket().getMemberEndDate(),"yyyy-MM-dd HH:mm:ss"))
								|| member_date.before( DateUtil.getDate(ticket.getTicket().getMemberStartDate(), "yyyy-MM-dd HH:mm:ss") )) {
						throw new Exception("注册日期不符合");
					}
				}
					
					
			}
		}

		// 是否网上使用
		if (ticket.getTicket().getIsWeb() == 1) {
			throw new Exception("只能网上使用");
		}

		// 订单最小金额
		if (data.getCart().getNotGiftMoney() < ticket.getTicket().getOrderMoney()) {
			throw new Exception("不符合订单最小金额");
		}
		return true;
	}

	/**
	 * 根据礼券号首字符决定礼券类型
	 * 
	 * @param conn
	 * @param giftNumber
	 * @return
	 * @throws SQLException
	 */
	public static int getTicketTypeByNumber(String giftNumber) throws Exception {

		if (giftNumber == null || giftNumber.length() == 0)
			throw new java.lang.IllegalArgumentException("参数不能为空啊，老大");

		char firstChar = giftNumber.charAt(0);
		//boolean isNum = StringUtil.isNum(String.valueOf(firstChar));
		//if (isNum) {
		//	return 3;// 幸运卡
		//} else {
			switch (firstChar) {
			case 'P':// 公共礼券
				return 4;
			case 'E':// 私有礼券
				return 5;
			case 'N':// 普通礼券
				return 2;
			default:// 入会礼券
				return 1;

			}
		//}

	}

	/**
	 * 将mbr_gift_use_group中的有效记录按组归类 放入list example: 4*E000001*3*10000
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

			// 第一条记录
			if (i == 0) {
				temp = group;
			}

			if (group.equals(temp)) {
				mulStr.append(type);
				mulStr.append("*");
				mulStr.append(number);
				mulStr.append("*");
			} else { // 本记录是下一个组
				temp = group;
				//System.out.println(""+mulStr.substring(0, mulStr.length()-1));
				list.add(mulStr.substring(0, mulStr.length()-1));// 讲本组放入list
				mulStr.delete(0, mulStr.length()); // 清空串
				mulStr.append(type);
				mulStr.append("*");
				mulStr.append(number);
				mulStr.append("*");
			}

			// 检查是不是最后一条记录
			if (i == MbrGiftUseGroupDAO.getGiftUseGroupRowCount(conn) - 1) {
				list.add(mulStr.substring(0, mulStr.length()-1));// 将最后一个组加入list

			}
			
			i++;
		}
		return list;
	}

	/**
	 * 检查礼券组 使用场合：多张礼券同时使用 礼券数据从session中取出来
	 * 
	 * @param conn
	 * @param data
	 * @return false-不符合礼券组
	 * @throws Exception
	 */
	public boolean checkGiftUseGroup(OrderForm data) throws Exception {

		//所有礼券
		List arrTicket = data.getCart().getTickets();
		String inputStr = null;
		
		List list = getGiftUseGroupList();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {// 遍历礼券组设置表
			String listStr = (String) iter.next();
			System.out.println(listStr);
			boolean flag = true;
			for (int i = 0; i < arrTicket.size(); i++) { // 遍历输入的每张礼券
		
				TicketMoney item = (TicketMoney) (arrTicket.get(i));
				// 礼券组策略
				//int type = getTicketTypeByNumber(item.getTicketCode());
				int type = Integer.parseInt(item.getTicketType());
				inputStr = type+"";
				inputStr += "*";
				if (type == 3 ) {//幸运卡
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

			if (flag) {// 如果页面所有的礼券都符合这个礼券组
				return true;
			}
			
		}
		
		return false;
	}

	/**
	 * 产品组检查(只有礼券在有此判断，幸运卡不进入这里判断)
	 * 
	 * @param data
	 * @return 0-ok
	 * @return -2-有必选产品没有符合
	 
	 * @throws Exception
	 */
	public int checkGiftItem(OrderForm data) throws Exception {

		//所有礼券
		List ticketMoney = data.getCart().getTickets();

		// 正常产品
		List items = data.getCart().getItems();
		
		//得到刚刚输入的礼券
		TicketMoney item = (TicketMoney) ticketMoney.get(ticketMoney.size()-1);

		// 根据页面输入的礼券号得到礼券对象
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

		// 产品组开关
		if (ticket.getTicket().getProductGroupId() == -1) {
			return 0;
		}

		// 得到礼券所在的产品组
		int groupId = ticket.getTicket().getProductGroupId();

		// 得到产品组头部
		Mbr_gift_item_mst mst = Mbr_gift_itemDAO.getHeadByPK(conn, groupId);
		if (mst == null) {
			throw new java.lang.Exception("该礼券需要设置礼品组");
		}

		//得到产品组类型
		int itemGroupType = mst.getItemgroup_type();
		
		int count = 0;
		if (itemGroupType == 0) { //单品混合 
		
			// 得到产品组明细
			Collection dtl = Mbr_gift_itemDAO.getLinesByFK(conn, groupId);
			int flag = 0;
			//产品数量检测
			for (int j = 0; j < dtl.size(); j++) { // 遍历产品组明细
				Mbr_gift_item gift_item = (Mbr_gift_item) ((List) dtl).get(j);
				
				for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
					ItemInfo item_info = (ItemInfo) items.get(k);
					//if (item_info.getItemId() == gift_item.getItem_id()) {
					//	count++; // 相同产品数
					//}
				}
				if (gift_item.getIs_must() == 1) {
					//检测每一个必须购买的产品时，都将重置此标记
					flag = 0;
					for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
						ItemInfo item_info = (ItemInfo) items.get(k);
						
						//if (item_info.getItemId() == gift_item.getItem_id()) {
						//	flag = 1;//本条符合
						//	break;
						//}
					}
					//检测第一个必须产品查找结果的flag
					if (flag == 0) { //不符合
						return -302;
					}
				}
				
			}
			System.out.println("****购买了规定的产品数:"+count);
			if ( count < mst.getMin_item_count()) {
					
				return -301;
			}
			
			
		} else { //根据产品类型（不需要判断产品组，只要判断购物车中正常产品的类型是否一致，个数是否符合即可）
			
			for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
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
			for (temp = 1; temp <=6; temp++) { //1-6分别是六大类型
				//检测每个产品类型的数量是，必须将计数器重置
				count = 0;
				for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
					ItemInfo item_info = (ItemInfo) items.get(k);
					
					int item_type = ProductDAO.getItemTypeByPk(this.conn,item_info.getItemId());
					
					if (item_type == temp) {
						count ++;
					}
					
				}
				if (count >= mst.getMin_item_count()) {//一旦 如果订单包含组的产品数大于规定的数目
					
					break;
				}
			}
			//找完所有的类型都不符合条件
			if (temp == 7 && count < mst.getMin_item_count()) {
				return -303;
			}
			*/
			
		}
		return 0;

	}

	/**
	 * 根据订单金额检查礼券抵用金额
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
	 * 通过会员级别检查礼券抵用金额
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
	 * 礼券检查前
	 * @param data
	 * @return 0 - 正常
	 * @throws Exception
	 */
	public int checkTicket(OrderForm data,TicketMoney newItem) throws Exception {
		if (data.getOtherGiftNumber() == null || data.getOtherGiftNumber().length() == 0) {
			return -102;
		}
		
		//根据礼券号得到当前礼券对象
		OneTicket ticket = TicketDAO.getTicketByNumber2(conn, data.getOtherGiftNumber());
		if(ticket == null) {
			return -100;
		}
		newItem.setTicket(ticket);
		
		//礼券密码
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
		
		
		//内存中所有礼券
		List inputTickets = data.getCart().getTickets();
		Iterator iter = inputTickets.iterator(); 
		
		MemberAWARD ma = null;
		
		// 判断本订单是否使用了此礼券，如果已经使用了该礼券（修改订单），
		// 那么根据使用的礼券id取出对应信息，否则根据礼券号去取一条符合的信息
		OrderGifts og = order_giftsDAO.getRecordByTicket(conn, data.getOtherGiftNumber(), data.getOrderId());
		if (og == null) { 
			ma = member_awardDao.getAvailableTicket(conn, data.getOtherGiftNumber(), data.getMbId());
			if (ma == null) {
				//如果是公有礼券礼券表加入数据
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
		
		//检测礼券是否用过（内存中是否存在该礼券）
		while (iter.hasNext()) {
			TicketMoney item = (TicketMoney)iter.next();
			
			if (item.getTicketCode().equals(data.getOtherGiftNumber())) {
				return -99;
			}
			
		}
		
		//已经使用多少次 //个人使用了多少次
		GiftTicketUse temp = new GiftTicketUse();
		temp.setMemberId(data.getMbId());
		temp.setTicketNum(data.getOtherGiftNumber());
		int usedTotal = giftTicketUseDAO.getTotalUseOfTicket(conn, temp);
		int person_used_total = giftTicketUseDAO.getPersonUseOfTicket(conn, temp);
		
		if (og != null) {//本订单已经使用了
			person_used_total --;
			usedTotal --;
		}
		
		//超过了总使用次数
		if (usedTotal >= ticket.getTicket().getAmount()) {
			return -98;
		}
		
		//超过了个人使用次数
		if (person_used_total >= ticket.getTicket().getPersonNum()) {
			return -97;
		}
		
		//购买金额是否满足礼券条件  公有礼券和私有绑定礼券以非促销品金额计,私有不绑定礼券以总产品计
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
	 * 设置礼券置为0，状态设为无效
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
	 * 订单提交的事情重新价差礼券
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
				//判断礼券主表上的条件
				if (type != 3) {
					if (!checkMainTick(data)) {
						resetTicket(item);
						continue;
					
					}
					//产品组判断
					//if (checkGiftItem(data) != 0) { // 检测有错误
					//	resetTicket(item);
					//	continue;
						
					//}
					
					//设置金额
					if (checkTicketMoney(data) != 0) {
						resetTicket(item);
						continue;
						
					}
				} 
				//去掉最后一个
				
			}catch(Exception ex) {
				resetTicket(item);
				continue;
			} finally {
				inputTickets.remove(inputTickets.size() - 1);
			}
			
		}
		
		
	}
	
	/**
	 * 设置礼券金额
	 * @return
	 */
	public int checkTicketMoney(OrderForm data) throws Exception {
		
		List inputTickets = data.getCart().getTickets();
		double ticket_money = 0;
		
		//得到刚刚输入的礼券
		TicketMoney item = (TicketMoney) inputTickets.get(inputTickets.size()-1);
		
		int ticket_type = TicketBO.getTicketTypeByNumber(item.getTicketCode());
		
		if (ticket_type == 3) { // 幸运卡
			;
		} else if (ticket_type == 1 || ticket_type == 2 || ticket_type == 4
				|| ticket_type == 5) {// 入会礼券、普通礼券、公共礼券、私有礼券
			OneTicket ticket = item.getTicket(); 
			//TicketDAO.getTicketByNumber2(conn, item.getTicketCode());

			if (ticket.getTicket().getIsMoneyForOrder() == 0) {// 不和订单金额挂钩

				if (ticket.getTicket().getIsMemberLevel() == -1) {// 不和会员级别挂钩
					ticket_money = ticket.getTicket().getGiftMoney(); // 取礼券默认价格
					item.setMoney(ticket_money);
					item.setUseStatus(0);
					
				} else { // 和会员级别挂钩
					if (data.getCart().getMember().getLEVEL_ID() >= ticket.getTicket().getIsMemberLevel()) {// 判断会员级别是否符合
						item.setMoney(ticket.getTicket().getGiftMoney());
						item.setUseStatus(0);
						
					} else {
						return -6;
					}
				}
			} else { // 和订单金额挂钩
				Collection gift_money = null;
				if (ticket.getTicket().getIsMemberLevel() == -1) { // 不和会员级别挂钩
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
					
				} else {// 和会员级别挂钩
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
			throw new java.lang.IllegalArgumentException("没有这这种礼券类型");
		}
		return 0;
	}
	
	/**
	 * 这个方法由action来调用
	 * 
	 * @param data
	 * @throws Exception
	 */
	public int checkTicket(OrderForm data) throws Exception {

		int rtn = 0;
		List inputTickets = data.getCart().getTickets();

		// 判断礼券主表上的条件
		if (!checkMainTick(data)) {
			return -1;
		}

		// 礼券组判断（需要所有礼券一起检查）
		if (inputTickets.size() > 1) { // 如果是多张礼券判断是不是属于礼券组中
			if (!checkGiftUseGroup(data)) {
				return -2;
			}
		}

		// 产品组判断
		int item_result = checkGiftItem(data);
		if (item_result != 0) { // 检测有错误
			return item_result;
		}
		
		//设置抵用金额
		rtn = checkTicketMoney(data);
		
		return rtn;
	}

	/**
	 * 得到抵扣金额
	 * modified by user 2007-06-22
	 * @param coll
	 * @return
	 */
	private double getDisAmt(Collection coll, OrderForm data, TicketMoney ticket) throws Exception {
		
		
		if (coll==null || coll.size() == 0) {
			throw new Exception("系统设置了和订单抵用级别挂钩，但没有设置规则，请与管理员联系!");
		} 
		
		Mbr_gift_money_by_order first = (Mbr_gift_money_by_order)((List)coll).get(0);
		
		//返回值(实际抵用金额)
		double ticketMoney = 0;
		
		//订单中符合礼品组的要求的金额
		double total_money=0;
		
		//组
		int itemgroup_type = -1;
		
		//所有正常购物产品
		List items = data.getCart().getItems();
		
		if (first.getItem_group_id() == -1) { //抵扣金额不关联到产品组
			if (first.getDis_type() == 1) {//单一订单金额（非组策略）
				ticket.setDisType(1);
				total_money = data.getCart().getNotGiftMoney();
			} else { //累计订单(非组策略)
				ticket.setDisType(2);
				total_money = OrderDAO.calcFinishedOrderMoneyByPeriod(conn, first.getBegin_date(), first.getEnd_date(), data.getMbId()); 
			}
			
		} else { //抵扣选择了产品组
			
			//组模式不存在累计订单策略（抵扣金额设置为0）
			if (first.getDis_type() == 1) { //单一订单（组策略）
				ticket.setDisType(1);
				//得到产品组头部
				Mbr_gift_item_mst mst = Mbr_gift_itemDAO.getHeadByPK(conn, first.getItem_group_id());
				if (mst == null) {
					throw new java.lang.Exception("该礼券需要设置礼品组");
				}
				
				
				//选择的组
				itemgroup_type = mst.getItemgroup_type();
				
				if (itemgroup_type == 0) { //单一产品
					//得到产品组明细
					Collection dtl = Mbr_gift_itemDAO.getLinesByFK(conn, first.getItem_group_id());
					if (dtl == null || dtl.size()==0) {
						throw new java.lang.Exception("该礼券组需要设置产品明细");
					}
					for (int j = 0; j < dtl.size(); j ++) {
						Mbr_gift_item gift_item = (Mbr_gift_item) ((List) dtl).get(j);
						for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
							ItemInfo item = (ItemInfo)items.get(k);
							//if (gift_item.getItem_id() == item.getItemId()) {
							//	total_money += item.getItemMoney();
							//	break;
							//}
						}
					}
					//以上算出了相关抵扣金额
					
				} else { //产品类型
					/**
					 * 产品类型增加了7：音像礼品组特殊判断
					 */
					if (itemgroup_type <= 6) {
						for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
							ItemInfo item = (ItemInfo)items.get(k);
							int item_type = 0;//ProductDAO.getItemTypeByPk(this.conn,item.getItemId());
							if (item_type == itemgroup_type) { //类型一致才算地用金额
								total_money += item.getItemMoney();
							}
						}
					}
					if (itemgroup_type == 8) { //音像、礼品组
						for (int k = 0; k < items.size(); k++) {// 遍历订单产品明细
							ItemInfo item = (ItemInfo)items.get(k);
							int item_type = 0;//ProductDAO.getItemTypeByPk(this.conn,item.getItemId());
							if (item_type == 2 || item_type == 3 || item_type == 5) { //类型一致才算地用金额
								total_money += item.getItemMoney();
							}
						}
					}
					
					//以上算出了相关抵扣金额
				}
			} else {//累计订单（组策略）
				//ticket.setDisType(2);
				//total_money = 0d;
				throw new Exception("礼券设置有问题请与管理员联系!(订单抵用级别采用组策略、累计订单策略，暂时还未开通)");
			}
			
			
		}
		ticket.setItemType(itemgroup_type);
		ticket.setItemTypeMoney(total_money);
		
		//所有的订单抵用级别规则
		Iterator iter = coll.iterator();
		
		//遍历所有的订单抵用级别规则（直到能找到一条符合条件的规则，算出抵用金额，否则返回0）
		while (iter.hasNext()) {
			
			//所有的地用级别
			Mbr_gift_money_by_order info = (Mbr_gift_money_by_order) iter
					.next();
				
			if (total_money >= info.getOrder_require()) {//找到了相应的抵用规则
				if (info.getIs_discount().equals("N")) { //非折扣方式
					ticketMoney = info.getDis_amt();
					ticket.setIsDiscount("N");
				} else {//折扣方式
					ticketMoney = Arith.roundX(info.getDis_amt() * total_money, 1);//产生小数去掉分
					ticket.setIsDiscount("Y");
				}
				
				break;
			}
			
			/**
			//根据计算出来的total_money（订单中能够地用的金额数），根据抵扣策略来计算出抵扣的金额
			if (info.getDis_type() == 1) {//单一订单金额
				ticket.setDisType(1);
				if (total_money >= info.getOrder_require()) {//找到了相应的抵用规则
					if (info.getIs_discount().equals("N")) { //非折扣方式
						ticketMoney = info.getDis_amt();
						ticket.setIsDiscount("N");
						ticket.setItemType(itemgroup_type);
						ticket.setItemTypeMoney(total_money);
					} else {//折扣方式
						ticketMoney = Arith.round(info.getDis_amt() * total_money, 1);
						ticket.setIsDiscount("Y");
						ticket.setItemType(itemgroup_type);
						ticket.setItemTypeMoney(total_money);
					}
					break;
				}
			}else {
				ticket.setDisType(2);
				//得到某段时间内订单金额总和（完成的订单）
				
				if (total_money >= info.getOrder_require()) { //找到了相应的抵用规则
					if (info.getIs_discount().equals("N")) {//非折扣方式
						ticketMoney = info.getDis_amt();
						ticket.setIsDiscount("N");
					} else {//折扣方式
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
	 * 从内存中得到相应礼券的抵扣金额
	 * 注意：累计订单、单一订单暂时未考虑
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
	 * 将OrderGifts转换成TicketMoney类型
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
	 * 将OrderGifts转换成TicketMoney类型
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
	 * 取消礼券
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
			if (type == 3) { //幸运卡
				
			} else { //其他礼券
				i = giftTicketUseDAO.addUseNumber(conn, order.getMbId(), og.getGiftNumber());
				if (i != 1) {
					throw new Exception("更新礼券失败");
				}
				//如果是私有礼券，更新暂存架状态
				if (type == 1 || type == 5) {
					MemberGetAwardDAO.resetTicketStatus(conn, order.getMbId(), og.getGiftNumber());
				}
			}
		}
	}
	
	/**
	 * 判断当前礼券是否已经在内存中
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
			//如果是幸运卡看看是否已经用过了
			if (getTicketTypeByNumber(number) == 3 && item.getTicketType().equals("3")) { //幸运卡
				return true;
			}
		}
		return false;
	}
}

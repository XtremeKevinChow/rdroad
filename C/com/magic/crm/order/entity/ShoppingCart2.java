/*
 * Created on 2007-03-02
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.magic.utils.Arith;
import com.magic.crm.member.entity.Member;
import com.magic.crm.order.entity.DeliveryInfo;
import com.magic.crm.order.entity.OtherInfo;
import com.magic.crm.order.entity.Order;
import com.magic.crm.promotion.entity.Prom_Item;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;
/**
 * @author user
 * 购物车对象，包含购物车的基本操作如:添加、删除、更改数量、清空购物车
 * 
 *             ------------- items(正常购物产品)
 *             |
 * 	           ------------- gifts(促销、积分、入会、暂存架等礼品)
 * 				|
 * 				------------ gifts2(选中的多少元任选几件礼品)
 *             | 
 *             -------------- tickets(礼券)
 *             | 
 *ShoppingCart2--------------- member(会员及帐户信息)
 *             |
 *             --------------- order (部分订单信息)
 *             |
 *             --------------- deliveryInfo(发送信息)
 *             |
 *             ---------------- otherInfo(其他信息) 
 *             |
 *             ---------------- AllGifts (促销礼品列表 新老会员普通促销礼品)
 *             |
 *             ---------------- allGifts2(多少元任选几件促销礼品)
 *             
 *  
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShoppingCart2 implements java.io.Serializable {
	
	private static final long serialVersionUID = -2007030200000000001L;
	
	/** 购物车中的正常销售产品信息（List <ItemInfo>） **/
	private List items = new ArrayList();
	
	/** 购物车中的礼品信息（List <ItemInfo>） **/
	private List gifts = new ArrayList();
	
	public List getGifts2() {
		return gifts2;
	}

	public void setGifts2(List gifts2) {
		this.gifts2 = gifts2;
	}
	private int setGroupId=0;
	
	public void addGift2(ItemInfo ii) {
		gifts2.add(0,ii);
		//gift2ActQty++;
	}
	public void addGift2Set(ArrayList iis) {
		gifts2.addAll(0,iis);
		setGroupId++;
	}
	public void addGiftSet(ArrayList iis) {
		gifts.addAll(0,iis);
		setGroupId++;
	}
	public void addItemSet(ArrayList iis) {
		items.addAll(0,iis);
		setGroupId++;
	}
	public int getSetGroupId(){
		return setGroupId;
	}
	public void setSetGroupId(int giftGroupId){
		this.setGroupId = giftGroupId;
	}
	
	public int getGift2ActQty(int groupId) {
		int ret = 0;
		int gift_id = -1;
		Iterator it = gifts2.iterator();
		while(it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if(ii.getGroupId() == groupId) {
				if (!"".equals(ii.getSet_code())){
					if(ii.getSet_group_id() != gift_id) {
						ret++;
						gift_id = ii.getSet_group_id();
					}
				} else {
					ret++;
				}
				
			}
		}
		
		return ret;
	}
	
	public void setGift2Price(int groupId, double price, int qty ) {
		setGift2Price(groupId,price,qty,0);
	}
	
	public void setGift2Price(int groupId, double price,int qty, int first) {
		int count = -1, setGroup = -1;
		Iterator it = gifts2.iterator();
		while(it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getGroupId() == groupId) {
					if ("".equals(ii.getSet_code())) {
						count++;
						if ( count>= first && count< qty + first) {
							ii.setItemPrice(price);
						}
						
					} else {
						int setGroupId = ii.getSet_group_id();
						if (setGroupId != setGroup ) {
							setGroup = setGroupId;
							count++;
						}
						if ( count>= first && count< qty + first) {
							ii.setSet_price(price);
							ii.setItemPrice(ii.getItemSilverPrice()/ii.getSetSilverPrice() * ii.getSet_price());
						}
						
					}
				
				
			}
				
		}
		
	}
	
	/** 购物车中的礼品信息2(针对多少元买几件)（List <ItemInfo>） **/
	private List gifts2 = new ArrayList();
	
	/** 购物车中使用的礼券信息（List <TicketMoney>） **/
	private List tickets = new ArrayList();
	
	/** 所有的促销礼品信息（List <ItemInfo>） **/
	private List allGifts = new ArrayList();
	
	/** 多少元任选几件促销 */
	private List allGifts2 = new ArrayList();
	
	public List getAllGifts2() {
		return allGifts2;
	}
	
	public Proms2 getAllGift2Prom(int groupId) {
		Iterator it = allGifts2.iterator();
		while (it.hasNext()) {
			Proms2 pm = (Proms2) it.next();
			if (pm.getId() == groupId) {
				return pm;
			}
		}
		return null;
	}

	public void setAllGifts2(List allGifts2) {
		this.allGifts2 = allGifts2;
	}

	/** 会员信息 **/
	private Member member = new Member();
	
	/** 订单头信息 **/
	private Order order = new Order();
	
	/** 发送信息 **/
	private DeliveryInfo deliveryInfo = new DeliveryInfo();
	
	/** 其他信息 **/
	private OtherInfo otherInfo = new OtherInfo();
	
	/** 招募促销列表 **/
	private List activeMsc = new ArrayList();
	
	/** 包装类型 **/
	private int packageType =1;
	/** 包装费 **/
	private double packageFee = 0.0;
	
	/**
	 * @return the packageFee
	 */
	public double getPackageFee() {
		return packageFee;
	}

	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(double packageFee) {
		this.packageFee = packageFee;
	}

	/**
	 * @return the activeMsc
	 */
	public List getActiveMsc() {
		return activeMsc;
	}

	/**
	 * @param activeMsc the activeMsc to set
	 */
	public void setActiveMsc(List activeMsc) {
		this.activeMsc = activeMsc;
	}

	/**
	 * 空构造函数
	 */
	public ShoppingCart2 () {	
	}
	
	public List getGifts() {
		return gifts;
	}

	public void setGifts(List gifts) {
		this.gifts = gifts;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public List getTickets() {
		return tickets;
	}

	public void setTickets(List tickets) {
		this.tickets = tickets;
	}
	
	public List getAllGifts() {
		return allGifts;
	}

	public void setAllGifts(List allGifts) {
		this.allGifts = allGifts;
	}
	
	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public OtherInfo getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(OtherInfo otherInfo) {
		this.otherInfo = otherInfo;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 清空购物篮包括产品、礼品、礼券
	 */
	public void clearShoppingCart() {
		items.clear();
		gifts.clear();
		tickets.clear();
		gifts2.clear();
	}
	
	/**
	 * 增加正常产品到购物车
	 * @param item
	 */
	public void addItem(ItemInfo item) {
		items.add(item);
	}
	
	/**
	 * 更新购物车中指定的正常产品数量
	 * @param nIndex
	 * @param quantity
	 */
	public void updateItemQty(int nIndex, int quantity)  {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		
		ItemInfo updatedItem = (ItemInfo)items.get(nIndex);
		updatedItem.setItemQty(quantity);
		
	}
	/**
	 * 更新指定产品
	 * @param item
	 */
	public void updateItem(ItemInfo item) {
		;
	}
	/**
	 * 从购物车删除正常产品
	 * @param nIndex
	 */
	public void removeItem(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		items.remove(nIndex);
	}
	
	/**
	 * 从购物车删除D区所有产品包括礼品
	 */
	public void removeAllItemsDE() {
		
		for (int i = 0; i < items.size(); i++) {
			ItemInfo ii = (ItemInfo) items.get(i);
			if (ii.getSellTypeId() == -1 ) {
				i --;
				items.remove(ii);
			}
		}
	}
	
	/**
	 * 增加礼品到购物车
	 * @param gift
	 */
	public void addGift(ItemInfo gift) {
		gifts.add(gift);
	}
	/**
	 * 增加指定位置的礼品到购物车
	 * @param gift
	 */
	public void addGift(int nIndex, ItemInfo gift) {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		gifts.add(nIndex, gift);
	}
	/**
	 * 从购物车删除礼品
	 * @param nIndex
	 */
	public void removeGift(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		gifts.remove(nIndex);
	}
	
	/**
	 * 将购物车中序号为nIndex的礼品替换为newGift
	 * @param nIndex
	 * @param newGift
	 */
	public void changeGift(int nIndex, ItemInfo newGift) {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		removeGift(nIndex);
		addGift(nIndex, newGift);
	}
	
	/**
	 * 将礼券加入购物车
	 * @param ticket
	 */
	public void useTicket(TicketMoney ticket) {
		tickets.add(ticket);
	}
	
	/**
	 * 从购物车中删除指定礼券
	 * @param nIndex
	 */
	public void unuseTicket(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("无效索引");
		tickets.remove(nIndex);
	}
	
	/**
	 * 得到指定的礼品的在购物车中的序号
	 * @param awardId
	 * @param sellTypeId
	 * @return
	 */
	public int getGiftIndex(long awardId, int sellTypeId) {
		for (int i = 0; i < gifts.size(); i++) {
			ItemInfo ii = (ItemInfo) gifts.get(i);
			if (ii.getAwardId() == awardId && ii.getSellTypeId() == sellTypeId) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 得到指定的礼品的在促销礼品中的序号
	 * @param itemCode
	 * @return
	 */
	public int getAllGiftIndex(String itemCode) {
		for (int i = 0; i < allGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) allGifts.get(i);
			if (ii.getItemCode().equals(itemCode) && ii.isValid()) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 得到指定的礼品的在促销礼品中的序号
	 * 如果该礼品存在于多个级别中,取最便宜的那个
	 * @param itemCode
	 * @return
	 */
	public int getLowerGiftIndex(String itemCode) {
		int index = -1;
		double itemPrice = 0x7fffffff;
		for (int i = 0; i < allGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) allGifts.get(i);
			if (ii.getItemCode().equals(itemCode) 
				&& ii.isValid()
				&& ii.getAddy() < itemPrice) {
				itemPrice = ii.getAddy();
				index = i;
			}
		}
		return index;
	}
	/**
	 * 得到指定的礼品的在促销礼品中的序号
	 * @param itemCode
	 * @return
	 */
	public int getAllGiftIndex(long awardId, int sellTypeId) {
		for (int i = 0; i < allGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) allGifts.get(i);
			if (ii.getAwardId() == awardId && ii.getSellTypeId() == sellTypeId) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 得到指定的正常产品的在购物车中的序号
	 * 通过产品来查找
	 * @param itemId
	 * @return
	 */
	public int getItemIndex(int itemId) {
		for (int i = 0; i < items.size(); i++) {
			ItemInfo ii = (ItemInfo) items.get(i);
			//if (ii.getItemId() == itemId)
				return i;
		}

		return -1;
	}
	/**
	 * new
	 * 得到指定的正常产品的在购物车中的序号
	 * 通过货号、销售类型、区来查找
	 * @param itemId
	 * @return
	 */
	public int getItemIndex(int itemId, int sellType, String sectionType) {
		for (int i = 0; i < items.size(); i++) {
			ItemInfo ii = (ItemInfo) items.get(i);
			//if (ii.getItemId() == itemId && ii.getSellTypeId() == sellType && ii.getSectionType().equals(sectionType))
				return i;
		}

		return -1;
	}
	
	
	/**
	 * @deprecated
	 * 得到指定的礼券在购物车中的序号(未启用)
	 * @param ticketCode
	 * @param ticketType
	 * @return
	 */
	public int getTicketIndex(String ticketCode, String ticketType) {
		for (int i = 0; i < tickets.size(); i++) {
			TicketMoney ii = (TicketMoney) tickets.get(i);
			if (ticketCode.equals(ii.getTicketCode()) && ticketType.equals(ii.getTicketType()))
				return i;
		}

		return -1;
	}
	
	/**
	 * 得到指定的礼券在购物车中的序号
	 * @param ticketCode
	 * @param ticketType
	 * @return
	 */
	public int getTicketIndex(String ticketCode) {
		for (int i = 0; i < tickets.size(); i++) {
			TicketMoney ii = (TicketMoney) tickets.get(i);
			if (ticketCode.equals(ii.getTicketCode()))
				return i;
		}

		return -1;
	}
	
	/**
	 * 检查给定的正常产品是否已经存在于购物篮中
	 * modified by user 2008-05-12:增加判断条件销售类型
	 * @param temp
	 * @return
	 */
	public boolean existItems(ItemInfo temp) {
		for (int i = 0; i < items.size(); i++) {
			if (((ItemInfo) items.get(i)).getSku_id() == temp.getSku_id()
			&& ((ItemInfo) items.get(i)).getSellTypeId() == temp.getSellTypeId()		
			) {
				return true;
			}
		}

		return false;
	}
	/**
	 * 检查给定的正常产品是否已经存在于购物篮中
	 * @param temp
	 * @return
	 */
	public boolean existGifts(ItemInfo temp) {
		for (int i = 0; i < gifts.size(); i++) {
			//if (((ItemInfo) gifts.get(i)).getItemId() == temp.getItemId()) {
			//	return true;
			//}
		}

		return false;
	}
	/**
	 * 检查给定的兑换礼品是否已经存在于购物篮中
	 * @param itemId
	 * @return
	 */
	public boolean existExpGift(int itemId) {
		for (int i = 0; i < gifts.size(); i++) {
			ItemInfo ii = (ItemInfo) gifts.get(i);
			//if (ii.getItemId() == itemId && ii.getSellTypeId() == 6) {
			//	return true;
			//}
		}

		return false;
	}
	
	/**
	 * 购买商品的总金额减少时，自动去除购物金额线低于现有产品的礼品
	 * @return true:有礼品被去除 false:无礼品被去除
	 * @throws Exception
	 */
	public boolean removeReject() {
		boolean blResult = false;
		for (int i = gifts.size()-1; i>=0 ;  i--) {
			ItemInfo gift = (ItemInfo) gifts.get(i);
			//如果正常销售的产品金额未达到促销礼品的消费档次，将它删除掉
			if ( getNotGiftMoney() < gift.getFloorMoney() 
			        && gift.getSellTypeId() == 4) {
				gifts.remove(i);
				blResult = true;
			}
		}
		
		return blResult;
	}
	
	
	
	/**
	 * 重置所有促销礼品的状态
	 */
	public void resetAllGift() {   
		
		//step 1 重新根据定单金额设置按扭是否有效
	    Iterator it = allGifts.iterator();//所有促销礼品
			while(it.hasNext()) {	// selected gift loop
				ItemInfo gift = (ItemInfo)it.next();
				switch (gift.getFlag()) {// 判断产品是全场促销还是分类促销还是产品组促销
				case 1:
					if (getNotGiftMoney() >= gift.getFloorMoney()){ // all money of normal products >= the promotion price of gift
					//if(getNormalSaleMoney() >= gift.getFloorMoney()) {
						gift.setValid(true);					       					        
					}else {	
						gift.setValid(false);
					}
					break;
				case 2:
					if (getCateItemMoney(gift.getGroupId()) >= gift.getFloorMoney()){ // all money of normal products >= the promotion price of gift
						gift.setValid(true);					       					        
					}else {	
						gift.setValid(false);
					}
					break;
				case 3:
					if (getGroupItemMoney(gift.getProm_items()) >= gift.getFloorMoney()){ // all money of normal products >= the promotion price of gift
						gift.setValid(true);					       					        
					}else {	
						gift.setValid(false);
					}
					break;
				default:
					
				}
				//for (int i = 0; i < items.size(); i++) {// normal products loop
					 //返回选择组的产品对应的礼品
					
					
				//}
			}			
		
		//step 2 根据是否选择该礼品设置是否有效
		it = allGifts.iterator();
		while(it.hasNext()) {
			ItemInfo item = (ItemInfo)it.next();
			item.setSelected(false);
		}
		List selGift = new ArrayList();
		it = gifts.iterator();
		while(it.hasNext()) {
			ItemInfo item = (ItemInfo) it.next();
			Iterator it2 = null; 
			it2 = allGifts.iterator();
			while(it2.hasNext()) {
				ItemInfo item2 = (ItemInfo) it2.next();
				if(item.getAwardId() == item2.getAwardId()
				&&item.getSellTypeId() == item2.getSellTypeId()) {
					item2.setSelected(true);
					selGift.add(item2);
				}
			}
		}
		
		//step 3 将选中的礼品的所在组的所有礼品设置disabled(仅对老会员,新会员可以多选的)
		it = selGift.iterator();
		while(it.hasNext()) {
			ItemInfo item = (ItemInfo)it.next();
			Iterator it2 = allGifts.iterator();
			while(it2.hasNext()) {
				ItemInfo item2 = (ItemInfo) it2.next();
				if(item.getFloorMoney() == item2.getFloorMoney()
						&&item.getGift_group_id() == item2.getGift_group_id()&& item.getCatalog().equals("老会员促销")) {
					item2.setValid(false);
				}
			}
		}
	}
	
	

	/**
	 * 判断是否可选的促销礼品
	 * @param queryItemCode
	 * @return
	 */
	public boolean isValidPromotionGift(String queryItemCode) {
		
		for (int i = 0; i < allGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) allGifts.get(i);

			if (ii.getItemCode().equals(queryItemCode) && ii.isValid() && !ii.isSelected()) {
				
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否促销礼品
	 * @param queryItemCode
	 * @return
	 */
	public boolean isPromotionGift(String queryItemCode) {
		
		for (int i = 0; i < allGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) allGifts.get(i);
			if (ii.getItemCode().equals(queryItemCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 购物篮中总的购物总件数
	 * @return
	 */
	public long getTotalQty() {
		return getGiftQty() + getNotGiftQty();
	}
	/**
	 * 购物篮中总的购物金额
	 * @return
	 */
	public double getTotalMoney() {
		return Arith.round(Arith.add((float)getNotGiftMoney(), (float)getGiftMoney()), 2);
	}
	
	/**
	 * 购物篮中的非礼品总价
	 * @return
	 */
	public double getNotGiftMoney() {
		double totalMoney = 0.0;
		// 累加购买产品（非礼品）的应付金额
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				totalMoney += ii.getItemMoney();
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物篮中的正价产品总价
	 * @return 总价
	 */
	public double getNormalSaleMoney() {
		double totalMoney = 0.0;
		// 累加购买正价产品总价
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if(ii.getIsDiscount()==0) {
					totalMoney += ii.getItemMoney();
				}
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物篮中的非礼品数量
	 * @return
	 */
	public long getNotGiftQty() {
		long qty = 0L;
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				qty += ii.getItemQty();
			}
		}
		return qty;
	}
	
	/**
	 * 购物篮中的正价产品数量
	 * @return 数量
	 */
	public double getNormalSaleQty() {
		int qty = 0;
		// 累加购买正价产品数量
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if(ii.getSellTypeId()==0) {
					qty += ii.getItemQty();
				}
			}
		}

		return qty;
	}
	
	/**
	 * 购物蓝中正常销售某类产品的金额
	 * @param group_id
	 * @return
	 */
	public double getCateItemMoney(int group_id) {
		double totalMoney = 0.0;
		// 累加购买产品（非礼品）的应付金额
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if(ii.getItem_category()==group_id) {
					totalMoney += ii.getItemMoney();
				}
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物蓝中正常销售满足某产品组的金额
	 * @param group_id
	 * @return
	 */
	public double getGroupItemMoney(ArrayList proms) {
		double totalMoney = 0.0;
		// 累加购买产品（非礼品）的应付金额
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				
				for(int i=0;i<proms.size();i++) {
					Prom_Item pt = (Prom_Item)proms.get(i);
					if(ii.getItemCode().equals(pt.getItemcode()) || ii.getSet_code().equals(pt.getItemcode())) {
						totalMoney += ii.getItemMoney();
					}
				}
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物篮中的礼品总价
	 * @return
	 */
	public double getGiftMoney() {
		double totalMoney = 0.0;
		// 累加购买礼品的应付金额
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				totalMoney += ii.getItemMoney();
			}
		}
		if (gifts2 != null || gifts2.size() > 0) {
			Iterator it = gifts2.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				totalMoney += ii.getItemMoney();
			}
		}

		return Arith.round(totalMoney,2);
	}
	
	/**
	 * 购物篮中的礼品数量
	 * @return
	 */
	public long getGiftQty() {
		long qty = 0L;
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				qty += ii.getItemQty();
			}
		}
		if (gifts2 != null || gifts2.size() > 0) {
			Iterator it = gifts2.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				qty += ii.getItemQty();
			}
		}
		return qty;
	}
	
	/**
	 * 购物篮中的礼券抵扣
	 * @return
	 */
	public double getTicketKill() {
		double kill = 0;
		Iterator iter = tickets.iterator();
		while (iter.hasNext()) {
			TicketMoney ticket = (TicketMoney)iter.next();
			kill += ticket.getMoney();
		}
		return Math.abs(Arith.round(kill, 2));
		
	}
	
	/**
	 * 判断是否包含入会礼品
	 * @return
	 */
	public boolean existRecruitGift() {
		Iterator iter = gifts.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getSellTypeId() == 17) { //入会送礼
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断购物篮中是否有永久缺货产品
	 * @return true:有; false:无
	 */
	public boolean isCartOOS() {
		Iterator iter = null; 
		iter = items.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("永久缺货")
					||ii.getStockStatusName().equals("暂时缺货")
					) {
				return true;
			}
		}
		iter = gifts.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("永久缺货")
					||ii.getStockStatusName().equals("暂时缺货")) {
				return true;
			}
		}
		iter = gifts2.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("永久缺货")
					||ii.getStockStatusName().equals("暂时缺货")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 订单应付（购物总金额 - 礼券抵扣 + 发送费 + 包装费 - 付款方式折扣金额）
	 * @return
	 */
	public double getPayable() {
		return Arith.round(getTotalMoney() - getTicketKill() - getDiscount_fee() + deliveryInfo.getDeliveryFee() + getPackageFee(), 2);

	}
	
	/**
	 * 帐户抵扣
	 * @return
	 */
	public double getOrderUse() {
		double usefulMoney = member.getUsefulMoney();
		return usefulMoney < 0 ? usefulMoney : Math.min(usefulMoney, getPayable());
	}
	
	public double getUsefulMoney() {
		return member.getUsefulMoney();
	}
	/**
	 * 订单欠款（订单应付 - 帐户抵扣）
	 * @return
	 */
	public double getOrderOwe() {
		return Arith.round(getPayable() - getOrderUse(), 2);
	}
	
	
	/*public boolean isCommitment() {
		// 非礼品
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (ii.isCommitment())
					return true;
			}
		}

		// 礼品
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (ii.isCommitment())
					return true;
			}
		}
		return false;
	}*/
	
	/**
	 * 判断是否预售订单
	 * 一种是产品上标记为预售产品，另外一种是特殊货号
	 * @return
	 */
	public boolean isPreSellOrder() {
		//非礼品
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				//if (ii.is_pre_sell == 1)
				if(ii.getStockStatusName().equals("虚拟库存"))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否有暂存架礼品
	 * @return
	 */
	public boolean hasAwardGifts() {
		//礼品
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (ii.getAwardId() > 0 && ii.getSellTypeId() != 4)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否有非暂存架礼品
	 * @return
	 */
	public boolean hasNomalGifts() {
		//礼品
		if (gifts != null || gifts.size() > 0) {
			Iterator it = gifts.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (ii.getAwardId() <= 0 || (ii.getSellTypeId() == 4))
					return true;
			}
		}
		return false;
	}
	/**
	 * 判断产品是否会员卡
	 * @param itemCode
	 * @return
	 */
	public boolean isCard(String itemCode) {
		
		if (itemCode == null) {
			return false;
		} else {
			return ( itemCode.equals("100000") || 
			itemCode.equals("100001") ||
			itemCode.equals("100002") ||
			itemCode.equals("100003") ||
			itemCode.equals("100004") ||
			itemCode.equals("100005") );
		}
	}
	/**
	 * 加载入会礼品
	 * @param sessionRecruit
	 */
	public void loadRecuritGifts(MemberSessionRecruitGifts sessionRecruit) {
		Iterator it = gifts.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == 17) { //入会送礼
				//Recruit_Activity_PriceList product = sessionRecruit.getGiftByItemId(ii.getItemId());
				//sessionRecruit.getSeletedRecruitGifs().add(product);//加入sessionRecruit
			}
			
		}
	}
	/**
	 * 加载打折产品
	 *
	 */
	public void loadRecruitDiscount(MemberSessionRecruitGifts sessionRecruit) {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1) { //入会折扣
				//Recruit_Activity_PriceList product = sessionRecruit.getGiftByItemId(ii.getItemId());
				//sessionRecruit.getSeletedRecruitGifs().add(product);//加入sessionRecruit
			}
			
		}
	}
	
	/**
	 * 判断购物车中是否有打折产品
	 *
	 */
	public boolean isRecruitProductInCart() {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1 ) { //入会折扣
				return true;
				
			}
		}
		return false;
	}
	
	/**
	 * 判断购物车中是否有打折产品(modify order)
	 *
	 */
	public boolean isRecruitProductInCart_M() {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1) { //入会折扣
				return true;
				
			}
		}
		return false;
	}
	
	
	public void replaceItem(int nIndex, ItemInfo newItem) {
		items.remove(nIndex);
		items.add(nIndex,newItem );
	}
	
	/**
	 * 得到打套D区产品
	 * @return
	 */
	public List getRecruitProductInDSection() {
		List list = new ArrayList();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1 && ii.getSectionType().equals("D")) { //入会折扣
				list.add(ii);
			}
		}
		return list;
	}
	
	/**
	 * 计算打套区D区价格
	 * @param list_D
	 * @return
	 */
	public double getRecruitProductAmtInDSection(List list_D) {
		
		Iterator it = list_D.iterator();
		double totalAmt = 0;
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			totalAmt += Arith.round((ii.getItemPrice() * ii.getItemQty()), 2);
		}
		return totalAmt;
	}

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	
	public double getDiscount_fee() {
		return Arith.round(getTotalMoney()* (1 - otherInfo.getPaydiscount()),2);
	}
	
}

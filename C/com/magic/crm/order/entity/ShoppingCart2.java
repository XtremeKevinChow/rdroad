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
 * ���ﳵ���󣬰������ﳵ�Ļ���������:��ӡ�ɾ����������������չ��ﳵ
 * 
 *             ------------- items(���������Ʒ)
 *             |
 * 	           ------------- gifts(���������֡���ᡢ�ݴ�ܵ���Ʒ)
 * 				|
 * 				------------ gifts2(ѡ�еĶ���Ԫ��ѡ������Ʒ)
 *             | 
 *             -------------- tickets(��ȯ)
 *             | 
 *ShoppingCart2--------------- member(��Ա���ʻ���Ϣ)
 *             |
 *             --------------- order (���ֶ�����Ϣ)
 *             |
 *             --------------- deliveryInfo(������Ϣ)
 *             |
 *             ---------------- otherInfo(������Ϣ) 
 *             |
 *             ---------------- AllGifts (������Ʒ�б� ���ϻ�Ա��ͨ������Ʒ)
 *             |
 *             ---------------- allGifts2(����Ԫ��ѡ����������Ʒ)
 *             
 *  
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShoppingCart2 implements java.io.Serializable {
	
	private static final long serialVersionUID = -2007030200000000001L;
	
	/** ���ﳵ�е��������۲�Ʒ��Ϣ��List <ItemInfo>�� **/
	private List items = new ArrayList();
	
	/** ���ﳵ�е���Ʒ��Ϣ��List <ItemInfo>�� **/
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
	
	/** ���ﳵ�е���Ʒ��Ϣ2(��Զ���Ԫ�򼸼�)��List <ItemInfo>�� **/
	private List gifts2 = new ArrayList();
	
	/** ���ﳵ��ʹ�õ���ȯ��Ϣ��List <TicketMoney>�� **/
	private List tickets = new ArrayList();
	
	/** ���еĴ�����Ʒ��Ϣ��List <ItemInfo>�� **/
	private List allGifts = new ArrayList();
	
	/** ����Ԫ��ѡ�������� */
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

	/** ��Ա��Ϣ **/
	private Member member = new Member();
	
	/** ����ͷ��Ϣ **/
	private Order order = new Order();
	
	/** ������Ϣ **/
	private DeliveryInfo deliveryInfo = new DeliveryInfo();
	
	/** ������Ϣ **/
	private OtherInfo otherInfo = new OtherInfo();
	
	/** ��ļ�����б� **/
	private List activeMsc = new ArrayList();
	
	/** ��װ���� **/
	private int packageType =1;
	/** ��װ�� **/
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
	 * �չ��캯��
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
	 * ��չ�����������Ʒ����Ʒ����ȯ
	 */
	public void clearShoppingCart() {
		items.clear();
		gifts.clear();
		tickets.clear();
		gifts2.clear();
	}
	
	/**
	 * ����������Ʒ�����ﳵ
	 * @param item
	 */
	public void addItem(ItemInfo item) {
		items.add(item);
	}
	
	/**
	 * ���¹��ﳵ��ָ����������Ʒ����
	 * @param nIndex
	 * @param quantity
	 */
	public void updateItemQty(int nIndex, int quantity)  {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		
		ItemInfo updatedItem = (ItemInfo)items.get(nIndex);
		updatedItem.setItemQty(quantity);
		
	}
	/**
	 * ����ָ����Ʒ
	 * @param item
	 */
	public void updateItem(ItemInfo item) {
		;
	}
	/**
	 * �ӹ��ﳵɾ��������Ʒ
	 * @param nIndex
	 */
	public void removeItem(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		items.remove(nIndex);
	}
	
	/**
	 * �ӹ��ﳵɾ��D�����в�Ʒ������Ʒ
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
	 * ������Ʒ�����ﳵ
	 * @param gift
	 */
	public void addGift(ItemInfo gift) {
		gifts.add(gift);
	}
	/**
	 * ����ָ��λ�õ���Ʒ�����ﳵ
	 * @param gift
	 */
	public void addGift(int nIndex, ItemInfo gift) {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		gifts.add(nIndex, gift);
	}
	/**
	 * �ӹ��ﳵɾ����Ʒ
	 * @param nIndex
	 */
	public void removeGift(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		gifts.remove(nIndex);
	}
	
	/**
	 * �����ﳵ�����ΪnIndex����Ʒ�滻ΪnewGift
	 * @param nIndex
	 * @param newGift
	 */
	public void changeGift(int nIndex, ItemInfo newGift) {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		removeGift(nIndex);
		addGift(nIndex, newGift);
	}
	
	/**
	 * ����ȯ���빺�ﳵ
	 * @param ticket
	 */
	public void useTicket(TicketMoney ticket) {
		tickets.add(ticket);
	}
	
	/**
	 * �ӹ��ﳵ��ɾ��ָ����ȯ
	 * @param nIndex
	 */
	public void unuseTicket(int nIndex) {
		if (nIndex < 0)
			throw new IllegalArgumentException("��Ч����");
		tickets.remove(nIndex);
	}
	
	/**
	 * �õ�ָ������Ʒ���ڹ��ﳵ�е����
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
	 * �õ�ָ������Ʒ���ڴ�����Ʒ�е����
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
	 * �õ�ָ������Ʒ���ڴ�����Ʒ�е����
	 * �������Ʒ�����ڶ��������,ȡ����˵��Ǹ�
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
	 * �õ�ָ������Ʒ���ڴ�����Ʒ�е����
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
	 * �õ�ָ����������Ʒ���ڹ��ﳵ�е����
	 * ͨ����Ʒ������
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
	 * �õ�ָ����������Ʒ���ڹ��ﳵ�е����
	 * ͨ�����š��������͡���������
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
	 * �õ�ָ������ȯ�ڹ��ﳵ�е����(δ����)
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
	 * �õ�ָ������ȯ�ڹ��ﳵ�е����
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
	 * ��������������Ʒ�Ƿ��Ѿ������ڹ�������
	 * modified by user 2008-05-12:�����ж�������������
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
	 * ��������������Ʒ�Ƿ��Ѿ������ڹ�������
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
	 * �������Ķһ���Ʒ�Ƿ��Ѿ������ڹ�������
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
	 * ������Ʒ���ܽ�����ʱ���Զ�ȥ���������ߵ������в�Ʒ����Ʒ
	 * @return true:����Ʒ��ȥ�� false:����Ʒ��ȥ��
	 * @throws Exception
	 */
	public boolean removeReject() {
		boolean blResult = false;
		for (int i = gifts.size()-1; i>=0 ;  i--) {
			ItemInfo gift = (ItemInfo) gifts.get(i);
			//����������۵Ĳ�Ʒ���δ�ﵽ������Ʒ�����ѵ��Σ�����ɾ����
			if ( getNotGiftMoney() < gift.getFloorMoney() 
			        && gift.getSellTypeId() == 4) {
				gifts.remove(i);
				blResult = true;
			}
		}
		
		return blResult;
	}
	
	
	
	/**
	 * �������д�����Ʒ��״̬
	 */
	public void resetAllGift() {   
		
		//step 1 ���¸��ݶ���������ð�Ť�Ƿ���Ч
	    Iterator it = allGifts.iterator();//���д�����Ʒ
			while(it.hasNext()) {	// selected gift loop
				ItemInfo gift = (ItemInfo)it.next();
				switch (gift.getFlag()) {// �жϲ�Ʒ��ȫ���������Ƿ���������ǲ�Ʒ�����
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
					 //����ѡ����Ĳ�Ʒ��Ӧ����Ʒ
					
					
				//}
			}			
		
		//step 2 �����Ƿ�ѡ�����Ʒ�����Ƿ���Ч
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
		
		//step 3 ��ѡ�е���Ʒ���������������Ʒ����disabled(�����ϻ�Ա,�»�Ա���Զ�ѡ��)
		it = selGift.iterator();
		while(it.hasNext()) {
			ItemInfo item = (ItemInfo)it.next();
			Iterator it2 = allGifts.iterator();
			while(it2.hasNext()) {
				ItemInfo item2 = (ItemInfo) it2.next();
				if(item.getFloorMoney() == item2.getFloorMoney()
						&&item.getGift_group_id() == item2.getGift_group_id()&& item.getCatalog().equals("�ϻ�Ա����")) {
					item2.setValid(false);
				}
			}
		}
	}
	
	

	/**
	 * �ж��Ƿ��ѡ�Ĵ�����Ʒ
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
	 * �ж��Ƿ������Ʒ
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
	 * ���������ܵĹ����ܼ���
	 * @return
	 */
	public long getTotalQty() {
		return getGiftQty() + getNotGiftQty();
	}
	/**
	 * ���������ܵĹ�����
	 * @return
	 */
	public double getTotalMoney() {
		return Arith.round(Arith.add((float)getNotGiftMoney(), (float)getGiftMoney()), 2);
	}
	
	/**
	 * �������еķ���Ʒ�ܼ�
	 * @return
	 */
	public double getNotGiftMoney() {
		double totalMoney = 0.0;
		// �ۼӹ����Ʒ������Ʒ����Ӧ�����
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
	 * �������е����۲�Ʒ�ܼ�
	 * @return �ܼ�
	 */
	public double getNormalSaleMoney() {
		double totalMoney = 0.0;
		// �ۼӹ������۲�Ʒ�ܼ�
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
	 * �������еķ���Ʒ����
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
	 * �������е����۲�Ʒ����
	 * @return ����
	 */
	public double getNormalSaleQty() {
		int qty = 0;
		// �ۼӹ������۲�Ʒ����
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
	 * ����������������ĳ���Ʒ�Ľ��
	 * @param group_id
	 * @return
	 */
	public double getCateItemMoney(int group_id) {
		double totalMoney = 0.0;
		// �ۼӹ����Ʒ������Ʒ����Ӧ�����
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
	 * ��������������������ĳ��Ʒ��Ľ��
	 * @param group_id
	 * @return
	 */
	public double getGroupItemMoney(ArrayList proms) {
		double totalMoney = 0.0;
		// �ۼӹ����Ʒ������Ʒ����Ӧ�����
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
	 * �������е���Ʒ�ܼ�
	 * @return
	 */
	public double getGiftMoney() {
		double totalMoney = 0.0;
		// �ۼӹ�����Ʒ��Ӧ�����
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
	 * �������е���Ʒ����
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
	 * �������е���ȯ�ֿ�
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
	 * �ж��Ƿ���������Ʒ
	 * @return
	 */
	public boolean existRecruitGift() {
		Iterator iter = gifts.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getSellTypeId() == 17) { //�������
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �жϹ��������Ƿ�������ȱ����Ʒ
	 * @return true:��; false:��
	 */
	public boolean isCartOOS() {
		Iterator iter = null; 
		iter = items.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("����ȱ��")
					||ii.getStockStatusName().equals("��ʱȱ��")
					) {
				return true;
			}
		}
		iter = gifts.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("����ȱ��")
					||ii.getStockStatusName().equals("��ʱȱ��")) {
				return true;
			}
		}
		iter = gifts2.iterator();
		while (iter.hasNext()) {
			ItemInfo ii =  (ItemInfo)iter.next();
			if (ii.getStockStatusName().equals("����ȱ��")
					||ii.getStockStatusName().equals("��ʱȱ��")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ����Ӧ���������ܽ�� - ��ȯ�ֿ� + ���ͷ� + ��װ�� - ���ʽ�ۿ۽�
	 * @return
	 */
	public double getPayable() {
		return Arith.round(getTotalMoney() - getTicketKill() - getDiscount_fee() + deliveryInfo.getDeliveryFee() + getPackageFee(), 2);

	}
	
	/**
	 * �ʻ��ֿ�
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
	 * ����Ƿ�����Ӧ�� - �ʻ��ֿۣ�
	 * @return
	 */
	public double getOrderOwe() {
		return Arith.round(getPayable() - getOrderUse(), 2);
	}
	
	
	/*public boolean isCommitment() {
		// ����Ʒ
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				if (ii.isCommitment())
					return true;
			}
		}

		// ��Ʒ
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
	 * �ж��Ƿ�Ԥ�۶���
	 * һ���ǲ�Ʒ�ϱ��ΪԤ�۲�Ʒ������һ�����������
	 * @return
	 */
	public boolean isPreSellOrder() {
		//����Ʒ
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				//if (ii.is_pre_sell == 1)
				if(ii.getStockStatusName().equals("������"))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ����ݴ����Ʒ
	 * @return
	 */
	public boolean hasAwardGifts() {
		//��Ʒ
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
	 * �ж��Ƿ��з��ݴ����Ʒ
	 * @return
	 */
	public boolean hasNomalGifts() {
		//��Ʒ
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
	 * �жϲ�Ʒ�Ƿ��Ա��
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
	 * ���������Ʒ
	 * @param sessionRecruit
	 */
	public void loadRecuritGifts(MemberSessionRecruitGifts sessionRecruit) {
		Iterator it = gifts.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == 17) { //�������
				//Recruit_Activity_PriceList product = sessionRecruit.getGiftByItemId(ii.getItemId());
				//sessionRecruit.getSeletedRecruitGifs().add(product);//����sessionRecruit
			}
			
		}
	}
	/**
	 * ���ش��۲�Ʒ
	 *
	 */
	public void loadRecruitDiscount(MemberSessionRecruitGifts sessionRecruit) {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1) { //����ۿ�
				//Recruit_Activity_PriceList product = sessionRecruit.getGiftByItemId(ii.getItemId());
				//sessionRecruit.getSeletedRecruitGifs().add(product);//����sessionRecruit
			}
			
		}
	}
	
	/**
	 * �жϹ��ﳵ���Ƿ��д��۲�Ʒ
	 *
	 */
	public boolean isRecruitProductInCart() {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1 ) { //����ۿ�
				return true;
				
			}
		}
		return false;
	}
	
	/**
	 * �жϹ��ﳵ���Ƿ��д��۲�Ʒ(modify order)
	 *
	 */
	public boolean isRecruitProductInCart_M() {
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1) { //����ۿ�
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
	 * �õ�����D����Ʒ
	 * @return
	 */
	public List getRecruitProductInDSection() {
		List list = new ArrayList();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo ii = (ItemInfo) it.next();
			if (ii.getSellTypeId() == -1 && ii.getSectionType().equals("D")) { //����ۿ�
				list.add(ii);
			}
		}
		return list;
	}
	
	/**
	 * ���������D���۸�
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

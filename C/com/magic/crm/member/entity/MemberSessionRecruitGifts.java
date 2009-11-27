package com.magic.crm.member.entity;

import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
public class MemberSessionRecruitGifts {
	
	/** ��Ա�����ڱ���������Ļ�Ա�� **/
	private int memberId = 0;
	
	/** ������ļ��Ʒ **/
	protected Recruit_Activity allRecruitGifts = new Recruit_Activity();
	
	/** ��ѡ��ƷList<Recruit_Activity_PriceList> **/
	protected List seletedRecruitGifs = new ArrayList();

	public Recruit_Activity getAllRecruitGifts() {
		return allRecruitGifts;
	}

	public void setAllRecruitGifts(Recruit_Activity allRecruitGifts) {
		this.allRecruitGifts = allRecruitGifts;
	}

	public List getSeletedRecruitGifs() {
		return seletedRecruitGifs;
	}

	public void setSeletedRecruitGifs(List seletedRecruitGifs) {
		this.seletedRecruitGifs = seletedRecruitGifs;
	}
	
	/**
	 * ͨ��recruit_activity_pricelistס���õ�allRecruitGifts����Ӧ�Ĳ�Ʒ
	 * @param productId
	 * @return
	 */
	public Recruit_Activity_PriceList getGiftById(int productId) {
		
		Iterator it1 = allRecruitGifts.getSectionsList().iterator();
		while (it1.hasNext()) {
			Recruit_Activity_Section section = (Recruit_Activity_Section)it1.next();
			Iterator it2 = section.getProductsList().iterator();
			while (it2.hasNext()) {
				Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it2.next();
				if (product.getId() == productId) {
					return product;
				}
			}
		}
		return null;
		
	}
	
	/**
	 * ͨ����Ʒid�õ�allRecruitGifts�еĲ�Ʒ
	 * @param itemId
	 * @return
	 */
	public Recruit_Activity_PriceList getGiftByItemId(int itemId) {
		Iterator it1 = allRecruitGifts.getSectionsList().iterator();
		while (it1.hasNext()) {
			Recruit_Activity_Section section = (Recruit_Activity_Section)it1.next();
			Iterator it2 = section.getProductsList().iterator();
			while (it2.hasNext()) {
				Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it2.next();
				if (product.getItemId() == itemId) {
					return product;
				}
			}
		}
		return null;
		
	}
	
	public void addGift(Recruit_Activity_PriceList gift) {
		if (gift != null) {
			seletedRecruitGifs.add(gift);
		}
	}
	
	public void removeGift(Recruit_Activity_PriceList gift) {
		seletedRecruitGifs.remove(gift);
	}
	
	public void removeGift(int nIndex) {
		if (nIndex >= 0) {
			seletedRecruitGifs.remove(nIndex);
		}
	}
	
	public int getSeletedGiftIndex(int productId) {
		int nIndex = -1;
		for (int i = 0; i < seletedRecruitGifs.size(); i ++ ) {
			
			Recruit_Activity_PriceList selectedGift = (Recruit_Activity_PriceList)seletedRecruitGifs.get(i);
			if (selectedGift.getId() == productId) {
				nIndex = i;
				break;
			}
		}
		return nIndex;
	}
	
	/**
	 * 0-����;-1-�����������.-2С��С��
	 * @return
	 */
	public int checkAllSelectedGifts() {
		
		//���в�Ʒ
		int count = 0;
		//��ÿ�����ж�
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		while (sectionIt.hasNext()) {//������
			count = 0;//��һ����ʱ���ü�����
			Recruit_Activity_Section section = (Recruit_Activity_Section)sectionIt.next(); 
			Iterator it = this.seletedRecruitGifs.iterator();
			while (it.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
				if (gift.getSectionId() == section.getId()) { //ѡ�����Ʒ�������
					count ++;
				}
			}
			
			if (count < section.getMinGoods()) {
				
				return -2;
			}
			
			if (count > section.getMaxGoods())  {
				if (section.getType().equals("C")) {//����������
					return -3;
				} else {
					return -1;
				}
				
			}
			/**
			if (count >= section.getMaxGoods() && count <= section.getMinGoods()) {
				this.changeTempStatus(section);//����allgifts����Ʒ״̬
			}
			*/
		}
		
		return 0;
	}
	
	/**
	 * ɾ����ʱ����
	 *
	 */
	public void removeTemp() {
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0) {//��ʱ����
				//removeGift(gift);
				it.remove();
			}
		}
	}
	
	/**
	 * ��ֵ���е���Ʒ�б�״̬
	 *
	 */
	public void resetAllGiftsStatus() {
		
		//��ÿ�����ж�
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		int count = 0;
		while (sectionIt.hasNext()) {//������
			count = 0;
			Recruit_Activity_Section section = (Recruit_Activity_Section)sectionIt.next(); 
			int maxgoods = section.getMaxGoods();
			Iterator selectedIt = this.seletedRecruitGifs.iterator();
			while (selectedIt.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)selectedIt.next();
				if (gift.getSectionId() == section.getId()) {
					count ++;
				}
			}
			if (count >= maxgoods) { //�ﵽ����
				section.disabledAll();
			} else {
				if(this.seletedRecruitGifs.isEmpty()) {
					section.releaseAll();
				} else {
					section.releasedAllUnchecked();
				}
				
			}
		}
	}
	
	/**
	 * ��ĳ������Ʒ��ʱ״̬��������״̬
	 *
	 */
	public void changeTempStatus(Recruit_Activity_Section section) {
		
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0 && gift.getSectionId() == section.getId()) {//����ʱ�������ó�����
				gift.setTemp(1);
				gift.setChecked(1);
				gift.setDisabled(1);
			}
		}
		
	}
	
	/**
	 * ����ʱ״̬��������״̬
	 *
	 */
	public void changeTempStatus() {
		
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0) {//����ʱ�������ó�����
				gift.setTemp(1);
				gift.setChecked(1);
				gift.setDisabled(1);
			}
		}
		
	}
	/**
	 * ���ó���ʱ״̬
	 * @param deletedId
	 */
	public void resetTempStatus(int deletedId) {
		//��ÿ�����ж�
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		while (sectionIt.hasNext()) {//������
			Recruit_Activity_Section section = (Recruit_Activity_Section)sectionIt.next(); 
			Iterator it = section.getProductsList().iterator();//���в�Ʒ
			while (it.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
				if (gift.getId() == deletedId) { //ѡ�����Ʒ�������
					gift.setTemp(0);
					gift.setChecked(0);
					gift.setDisabled(0);
					break;
				}
			}
		}
		
	}
	
	public void clearSeletedGifts() {
		this.seletedRecruitGifs.clear();
	}
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
}

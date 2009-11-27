package com.magic.crm.member.entity;

import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
public class MemberSessionRecruitGifts {
	
	/** 会员（用于保存新增后的会员） **/
	private int memberId = 0;
	
	/** 所有招募礼品 **/
	protected Recruit_Activity allRecruitGifts = new Recruit_Activity();
	
	/** 已选礼品List<Recruit_Activity_PriceList> **/
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
	 * 通过recruit_activity_pricelist住键得到allRecruitGifts中相应的产品
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
	 * 通过产品id得到allRecruitGifts中的产品
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
	 * 0-正常;-1-超过区最大数.-2小最小数
	 * @return
	 */
	public int checkAllSelectedGifts() {
		
		//区中产品
		int count = 0;
		//对每个区判断
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		while (sectionIt.hasNext()) {//各个区
			count = 0;//下一个区时重置计数器
			Recruit_Activity_Section section = (Recruit_Activity_Section)sectionIt.next(); 
			Iterator it = this.seletedRecruitGifs.iterator();
			while (it.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
				if (gift.getSectionId() == section.getId()) { //选择的礼品在这个区
					count ++;
				}
			}
			
			if (count < section.getMinGoods()) {
				
				return -2;
			}
			
			if (count > section.getMaxGoods())  {
				if (section.getType().equals("C")) {//打折区过多
					return -3;
				} else {
					return -1;
				}
				
			}
			/**
			if (count >= section.getMaxGoods() && count <= section.getMinGoods()) {
				this.changeTempStatus(section);//设置allgifts中礼品状态
			}
			*/
		}
		
		return 0;
	}
	
	/**
	 * 删除零时对象
	 *
	 */
	public void removeTemp() {
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0) {//临时对象
				//removeGift(gift);
				it.remove();
			}
		}
	}
	
	/**
	 * 重值所有的礼品列表状态
	 *
	 */
	public void resetAllGiftsStatus() {
		
		//对每个区判断
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		int count = 0;
		while (sectionIt.hasNext()) {//各个区
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
			if (count >= maxgoods) { //达到上限
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
	 * 将某个区产品临时状态设置正常状态
	 *
	 */
	public void changeTempStatus(Recruit_Activity_Section section) {
		
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0 && gift.getSectionId() == section.getId()) {//将临时对象设置成正常
				gift.setTemp(1);
				gift.setChecked(1);
				gift.setDisabled(1);
			}
		}
		
	}
	
	/**
	 * 将临时状态设置正常状态
	 *
	 */
	public void changeTempStatus() {
		
		Iterator it = seletedRecruitGifs.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
			if (gift.getTemp() == 0) {//将临时对象设置成正常
				gift.setTemp(1);
				gift.setChecked(1);
				gift.setDisabled(1);
			}
		}
		
	}
	/**
	 * 设置成临时状态
	 * @param deletedId
	 */
	public void resetTempStatus(int deletedId) {
		//对每个区判断
		Iterator sectionIt = this.allRecruitGifts.getSectionsList().iterator();
		while (sectionIt.hasNext()) {//各个区
			Recruit_Activity_Section section = (Recruit_Activity_Section)sectionIt.next(); 
			Iterator it = section.getProductsList().iterator();//艘有产品
			while (it.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next();
				if (gift.getId() == deletedId) { //选择的礼品在这个区
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

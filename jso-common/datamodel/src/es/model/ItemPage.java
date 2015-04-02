package es.model;

import easyshop.model.Item;


public class ItemPage extends OriginalPage {
	private Item item;
	private int itemActionType=-1;
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getItemActionType() {
		return itemActionType;
	}

	public void setItemActionType(int itemActionType) {
		this.itemActionType = itemActionType;
	}

}

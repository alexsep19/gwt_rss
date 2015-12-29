package gx.server.domain;

import jpaRss.Item;
import jpaRss.Mail;

import com.google.web.bindery.requestfactory.shared.Locator;

public class ItemLoc extends Locator<Item, Integer>{

	@Override
	public Item create(Class<? extends Item> clazz) {
		// TODO Auto-generated method stub
		return new Item();
	}

	@Override
	public Item find(Class<? extends Item> clazz, Integer id) {
		return (Item) Dao.findObject(clazz, id);
	}

	@Override
	public Class<Item> getDomainType() {
		// TODO Auto-generated method stub
		return Item.class;
	}

	@Override
	public Integer getId(Item domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Item domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}

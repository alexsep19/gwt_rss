package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import rolo.Role;
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
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Item) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

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

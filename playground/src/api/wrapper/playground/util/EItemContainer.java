package api.wrapper.playground.util;

import api.provider.ExtraProvider;
import api.wrapper.playground.EObjects;
import api.wrapper.playground.model.EItem;
import org.osbot.rs07.Bot;
import org.osbot.rs07.api.model.Item;

import java.util.ArrayList;
import java.util.List;

public interface EItemContainer {

    Bot getBot();

    default ExtraProvider getExtraProvider() {
        return ((EObjects) getBot().getMethods().objects).extraProvider;
    }

    default Item[] getItems(Item[] rawItems) {
        final List<Item> itemList = new ArrayList<>();
        Item[] items = null;

        if (rawItems != null) {
            for (Item rawItem : rawItems) {
                if (rawItem != null && rawItem.getId() != -1 && rawItem.getId() != 6512) {
                    itemList.add(new EItem(rawItem.getOwner(), rawItem.getId(), rawItem.getAmount()));
                }
            }

            items = itemList.toArray(new Item[0]);
        }

        return items;
    }

}

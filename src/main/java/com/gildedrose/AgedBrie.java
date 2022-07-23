package com.gildedrose;

public class AgedBrie extends InventoryItem {
    public AgedBrie(Item item) {
        super(item);
    }

    @Override
    void update() {
        increaseQuality();
        decreaseSellIn();
        if (item.sellIn < DAYS_TO_SELL_ITEM_0) increaseQuality();
    }
}

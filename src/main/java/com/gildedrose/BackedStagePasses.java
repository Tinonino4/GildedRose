package com.gildedrose;

public class BackedStagePasses extends InventoryItem {
    public BackedStagePasses(Item item) {
        super(item);
    }

    @Override
    void update() {
        increaseQuality();
        if (item.sellIn < DAYS_TO_SELL_ITEM_11) increaseQuality();
        if (item.sellIn < DAYS_TO_SELL_ITEM_6) increaseQuality();
        decreaseSellIn();
        if (item.sellIn < DAYS_TO_SELL_ITEM_0) item.quality = 0;
    }
}

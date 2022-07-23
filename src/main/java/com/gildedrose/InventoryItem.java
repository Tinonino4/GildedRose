package com.gildedrose;

public class InventoryItem {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    public static final int DAYS_TO_SELL_ITEM_11 = 11;
    public static final int DAYS_TO_SELL_ITEM_6 = 6;
    public static final int DAYS_TO_SELL_ITEM_0 = 0;
    protected final Item item;

    protected InventoryItem(Item item) {
        this.item = item;
    }

    public static InventoryItem createInventoryItem(Item item) {
        if (item.name.equals(SULFURAS)) return new Sulfuras(item);
        if (item.name.equals(AGED_BRIE)) return new AgedBrie(item);
        if (item.name.equals(BACKSTAGE_PASSES)) return new BackedStagePasses(item);
        return new InventoryItem(item);
    }

    void update() {
        decreaseQuality();
        decreaseSellIn();
        if (item.sellIn < DAYS_TO_SELL_ITEM_0) decreaseQuality();
    }

    void decreaseSellIn() {
        item.sellIn = item.sellIn - 1;
    }

    void decreaseQuality() {
        if (item.quality > MIN_QUALITY) {
            item.quality = item.quality - 1;
        }
    }

    void increaseQuality() {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }
}

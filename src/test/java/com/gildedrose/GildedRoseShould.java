package com.gildedrose;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseShould {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    public static final int DAYS_TO_SELL_ITEM_11 = 11;
    public static final int DAYS_TO_SELL_ITEM_6 = 6;
    public static final int DAYS_TO_SELL_ITEM_0 = 0;

    private Item[] arrayWith(Item item) {
        return new Item[]{item};
    }

    @Test
    public void gildedRoseApprovalTest() {
        CombinationApprovals.verifyAllCombinations(
                this::updateItem,
                new String[] {"anyitem", AGED_BRIE, BACKSTAGE_PASSES, SULFURAS},
                new Integer[] {0, 11, 6},
                new Integer[] {0, 48, 50}
        );
    }

    private Item updateItem(String name, int sellIn, int quality) {
        Item item = new ItemBuilder()
                .setName(name)
                .setSellIn(sellIn)
                .setQuality(quality)
                .createItem();

        new GildedRose(arrayWith(item)).updateQuality();

        return item;
    }

    @Test
    void decrements_sellIn_and_quaility_each_day() {
        Item anyItem = new ItemBuilder()
                .setName("anyItem")
                .setSellIn(1)
                .setQuality(1)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.sellIn).isEqualTo(0);
        assertThat(anyItem.quality).isEqualTo(0);
    }

    @Test
    void decrements_twice_as_fast_when_sellIn_has_passed() {
        Item anyItem = new ItemBuilder()
                .setName("anyItem")
                .setSellIn(0)
                .setQuality(2)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(0);
    }

    @Test
    void dont_decrements_quality_down_0() {
        Item anyItem = new ItemBuilder()
                .setName("anyItem")
                .setSellIn(0)
                .setQuality(0)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(0);
    }

    @Test
    void aged_brie_increases_quality_the_older_it_gets() {
        Item anyItem = new ItemBuilder()
                .setName(AGED_BRIE)
                .setSellIn(1)
                .setQuality(1)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(2);
    }

    @Test
    void dont_increase_quality_over_50() {
        Item anyItem = new ItemBuilder()
                .setName(AGED_BRIE)
                .setSellIn(1)
                .setQuality(50)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(50);
    }

    @Test
    void sulfuras_dont_decrease_quality_either_sellIn() {
        Item anyItem = new ItemBuilder()
                .setName(SULFURAS)
                .setSellIn(1)
                .setQuality(80)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(80);
        assertThat(anyItem.sellIn).isEqualTo(1);
    }

    @Test
    void backstage_passes_increase_quality_by_1_when_sellIn_more_than_10() {
        Item anyItem = new ItemBuilder()
                .setName(BACKSTAGE_PASSES)
                .setSellIn(11)
                .setQuality(1)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(2);
    }

    @Test
    void backstage_passes_increase_quality_by_2_when_sellIn_between_10_and_6() {
        Item anyItem = new ItemBuilder()
                .setName(BACKSTAGE_PASSES)
                .setSellIn(10)
                .setQuality(1)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(3);
    }

    @Test
    void backstage_passes_increase_quality_by_2_when_sellIn_5_or_less() {
        Item anyItem = new ItemBuilder()
                .setName(BACKSTAGE_PASSES)
                .setSellIn(5)
                .setQuality(1)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(4);
    }

    @Test
    void backstage_passes_quality_drops_to_0_after_concert() {
        Item anyItem = new ItemBuilder()
                .setName(BACKSTAGE_PASSES)
                .setSellIn(0)
                .setQuality(10)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(0);
    }

    @Test
    void aged_brie_increase_quality_by_2_when_sellIn_less_than_0_if_quality_less_than_max() {
        Item anyItem = new ItemBuilder()
                .setName(AGED_BRIE)
                .setSellIn(-1)
                .setQuality(48)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(50);
    }

    @Test
    void aged_brie_no_increase_quality_by_1_when_sellIn_less_than_0_if_quality_is_max() {
        Item anyItem = new ItemBuilder()
                .setName(AGED_BRIE)
                .setSellIn(-1)
                .setQuality(50)
                .createItem();

        GildedRose app = new GildedRose(arrayWith(anyItem));
        app.updateQuality();

        assertThat(anyItem.quality).isEqualTo(50);
    }

    @Test
    void toString_item() {
        Item anyItem = new ItemBuilder()
                .setName(AGED_BRIE)
                .setSellIn(-1)
                .setQuality(50)
                .createItem();

        String item = anyItem.toString();

        assertThat(item).isEqualTo(anyItem.toString());
    }
}

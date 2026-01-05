package com.alvy.inventory.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyItemTest {

    private SupplyItem item;

    @BeforeEach
    void setUp() {
        item = new SupplyItem();
        item.setName("Pencils");
        item.setQuantity(100);
    }

    @Test
    void testGetQuantityAvailable_withNoHolds() {
        assertEquals(100, item.getQuantityAvailable());
    }

    @Test
    void testGetQuantityAvailable_withHolds() {
        item.holdQuantity(30);
        assertEquals(70, item.getQuantityAvailable());
    }

    @Test
    void testHoldQuantity_success() {
        item.holdQuantity(50);

        assertEquals(50, item.getQuantityOnHold());
        assertEquals(50, item.getQuantityAvailable());
        assertEquals(100, item.getQuantity());
    }

    @Test
    void testHoldQuantity_multipleHolds() {
        item.holdQuantity(30);
        item.holdQuantity(20);

        assertEquals(50, item.getQuantityOnHold());
        assertEquals(50, item.getQuantityAvailable());
    }

    @Test
    void testHoldQuantity_insufficientQuantity() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.holdQuantity(150);
        });

        assertTrue(exception.getMessage().contains("Insufficient available quantity"));
        assertTrue(exception.getMessage().contains("Available: 100"));
        assertTrue(exception.getMessage().contains("Requested: 150"));
    }

    @Test
    void testHoldQuantity_exactlyAvailableAmount() {
        item.holdQuantity(100);

        assertEquals(100, item.getQuantityOnHold());
        assertEquals(0, item.getQuantityAvailable());
    }

    @Test
    void testHoldQuantity_afterPartialHold() {
        item.holdQuantity(60);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.holdQuantity(50);
        });

        assertTrue(exception.getMessage().contains("Available: 40"));
    }

    @Test
    void testReleaseHold_success() {
        item.holdQuantity(50);
        item.releaseHold(30);

        assertEquals(20, item.getQuantityOnHold());
        assertEquals(80, item.getQuantityAvailable());
        assertEquals(100, item.getQuantity());
    }

    @Test
    void testReleaseHold_all() {
        item.holdQuantity(50);
        item.releaseHold(50);

        assertEquals(0, item.getQuantityOnHold());
        assertEquals(100, item.getQuantityAvailable());
    }

    @Test
    void testReleaseHold_moreThanHeld() {
        item.holdQuantity(30);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.releaseHold(50);
        });

        assertTrue(exception.getMessage().contains("Cannot release more than currently on hold"));
    }

    @Test
    void testReleaseHold_withNoHolds() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.releaseHold(10);
        });

        assertTrue(exception.getMessage().contains("Cannot release more than currently on hold"));
    }

    @Test
    void testFulfillHold_success() {
        item.holdQuantity(30);
        item.fulfillHold(30);

        assertEquals(70, item.getQuantity());
        assertEquals(0, item.getQuantityOnHold());
        assertEquals(70, item.getQuantityAvailable());
    }

    @Test
    void testFulfillHold_partial() {
        item.holdQuantity(50);
        item.fulfillHold(20);

        assertEquals(80, item.getQuantity());
        assertEquals(30, item.getQuantityOnHold());
        assertEquals(50, item.getQuantityAvailable());
    }

    @Test
    void testFulfillHold_moreThanHeld() {
        item.holdQuantity(20);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.fulfillHold(30);
        });

        assertTrue(exception.getMessage().contains("Cannot fulfill more than currently on hold"));
    }

    @Test
    void testFulfillHold_withNoHolds() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.fulfillHold(10);
        });

        assertTrue(exception.getMessage().contains("Cannot fulfill more than currently on hold"));
    }

    @Test
    void testUpdateQuantity_addPositiveAmount() {
        item.updateQuantity(50);

        assertEquals(150, item.getQuantity());
        assertEquals(150, item.getQuantityAvailable());
    }

    @Test
    void testUpdateQuantity_addNegativeAmount() {
        item.updateQuantity(-20);

        assertEquals(80, item.getQuantity());
        assertEquals(80, item.getQuantityAvailable());
    }

    @Test
    void testUpdateQuantity_withExistingHolds() {
        item.holdQuantity(30);
        item.updateQuantity(50);

        assertEquals(150, item.getQuantity());
        assertEquals(30, item.getQuantityOnHold());
        assertEquals(120, item.getQuantityAvailable());
    }

    @Test
    void testComplexScenario_holdFulfillAndReplenish() {
        assertEquals(100, item.getQuantity());

        item.holdQuantity(40);
        assertEquals(60, item.getQuantityAvailable());

        item.fulfillHold(25);
        assertEquals(75, item.getQuantity());
        assertEquals(15, item.getQuantityOnHold());
        assertEquals(60, item.getQuantityAvailable());

        item.releaseHold(15);
        assertEquals(75, item.getQuantity());
        assertEquals(0, item.getQuantityOnHold());
        assertEquals(75, item.getQuantityAvailable());

        item.updateQuantity(50);
        assertEquals(125, item.getQuantity());
        assertEquals(125, item.getQuantityAvailable());
    }
}

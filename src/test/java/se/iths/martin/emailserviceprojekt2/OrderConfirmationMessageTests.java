package se.iths.martin.emailserviceprojekt2;

import org.junit.jupiter.api.Test;
import se.iths.martin.emailserviceprojekt2.model.Order;
import se.iths.martin.emailserviceprojekt2.model.OrderItem;
import se.iths.martin.emailserviceprojekt2.subscriber.MessageSubscriber;
import se.iths.martin.springmessenger.service.MessageService;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderConfirmationMessageTests {
    // Kontrollerar att mejltextens valuta matchar betalningen utan att skicka något mejl.
    @Test
    void confirmationUsesUsdForTotalAndItems() {
        var sender = mock(MessageService.class);
        var subscriber = new MessageSubscriber(sender);
        var item = new OrderItem();
        item.setName("Product");
        item.setPrice(new BigDecimal("12.50"));
        item.setQuantity(2);
        var order = new Order();
        order.setTotalPrice(new BigDecimal("25.00"));
        order.setItems(List.of(item));

        var message = subscriber.orderToMessage(order);
        assertTrue(message.contains("25.00 USD"));
        assertTrue(message.contains("12.50 USD/st"));
        assertFalse(message.contains("SEK"));
        verifyNoInteractions(sender);
    }
}

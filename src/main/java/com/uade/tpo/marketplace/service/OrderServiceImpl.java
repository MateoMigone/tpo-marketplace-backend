package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.order.OrderDetailRequest;
import com.uade.tpo.marketplace.controller.order.OrderDetailResponse;
import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.controller.order.OrderResponse;
import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderDetail;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.NoStockAvailableException;
import com.uade.tpo.marketplace.repository.GameRepository;
import com.uade.tpo.marketplace.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Añadido para posible uso futuro, aunque no es estrictamente necesario aquí.

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // ----------------------------------------------------------------------
    // NUEVA IMPLEMENTACIÓN PARA EL ADMINISTRADOR
    // ----------------------------------------------------------------------
    @Override
    public List<Order> findAllOrders() {
        // orderRepository.findAll() retorna un Iterable<Order>. Lo convertimos a List.
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return orders;
    }
    // ----------------------------------------------------------------------

    @Override
    public java.util.List<OrderResponse> getOrdersForUser(User user) {
        java.util.List<com.uade.tpo.marketplace.entity.Order> orders = orderRepository.findByUserIdOrderByDateDesc(user.getId());
        java.util.List<OrderResponse> responses = new java.util.ArrayList<>();

        for (com.uade.tpo.marketplace.entity.Order order : orders) {
            OrderResponse resp = new OrderResponse();
            resp.setId(order.getId());
            resp.setEmail(order.getUser().getEmail());
            resp.setDate(order.getDate());
            resp.setTotalPrice(order.getTotalPrice());

            java.util.List<OrderDetailResponse> details = new java.util.ArrayList<>();
            for (com.uade.tpo.marketplace.entity.OrderDetail od : order.getOrderDetails()) {
                OrderDetailResponse d = new OrderDetailResponse();
                Integer rawGameId = od.getGame() != null ? od.getGame().getId() : null;
                d.setGameId(rawGameId != null ? rawGameId.longValue() : null);
                d.setQuantity(od.getQuantity());
                d.setUnitPrice(od.getUnitPrice());
                details.add(d);
            }
            resp.setOrderDetailResponses(details);
            responses.add(resp);
        }

        return responses;
    }

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public OrderResponse createOrder(User user, OrderRequest orderRequest) throws NoStockAvailableException {
        // ... (Tu lógica existente para createOrder) ...
        LocalDateTime dateTime = LocalDateTime.now();

        Order order = new Order();
        order.setUser(user);
        order.setDate(dateTime);

        List<OrderDetailRequest> itemList = orderRequest.getItemList();
        List<OrderDetailResponse> itemListResponse = new ArrayList<OrderDetailResponse>();
        Double totalPrice = 0.00;

        for (OrderDetailRequest itemDetail : itemList) {
            Long gameId = itemDetail.getGameId();
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + itemDetail.getGameId()));

            Integer stock = game.getStock();
            Integer quantity = itemDetail.getQuantity();
            if (quantity > stock){
                throw new NoStockAvailableException();
            }

            game.setStock(stock - quantity);
            gameRepository.save(game);

            Double finalPrice = game.getFinalPrice();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGame(game);
            orderDetail.setQuantity(quantity);
            orderDetail.setUnitPrice(finalPrice);
            order.addOrderDetail(orderDetail);

            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setGameId(gameId);
            orderDetailResponse.setQuantity(quantity);
            orderDetailResponse.setUnitPrice(finalPrice);
            itemListResponse.add(orderDetailResponse);

            totalPrice += quantity * finalPrice;
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setEmail(user.getEmail());
        orderResponse.setDate(dateTime);
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setOrderDetailResponses(itemListResponse);

        return orderResponse;
    }
}

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
import com.uade.tpo.marketplace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(User user, OrderRequest orderRequest) throws NoStockAvailableException {
        LocalDateTime dateTime = LocalDateTime.now();
        Order order = new Order();
        order.setUser(user);
        order.setDate(dateTime);
        order.setAddress(orderRequest.getAddress());

        List<OrderDetailRequest> itemList = orderRequest.getItemList();
        List<OrderDetailResponse> itemListResponse = new ArrayList<>();
        Double totalPrice = 0.00;

        for (OrderDetailRequest itemDetail : itemList) {
            Long gameId = itemDetail.getGameId();
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + itemDetail.getGameId()));

            Integer stock = game.getStock();
            Integer quantity = itemDetail.getQuantity();
            if (quantity > stock) {
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
        orderResponse.setAddress(orderRequest.getAddress());
        orderResponse.setOrderDetailResponses(itemListResponse);

        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrdersByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        List<Order> orders = orderRepository.findByUser(user);
        
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }
    
    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setEmail(order.getUser().getEmail());
        response.setDate(order.getDate());
        response.setTotalPrice(order.getTotalPrice());
        response.setAddress(order.getAddress());
        
        // CORRECCIÓN FINAL: Se cambió getOrderDetail() a getOrderDetails() (plural)
        List<OrderDetailResponse> detailResponses = order.getOrderDetails().stream()
                .map(detail -> {
                    OrderDetailResponse detailResponse = new OrderDetailResponse();
                    detailResponse.setGameId(detail.getGame().getId());
                    detailResponse.setQuantity(detail.getQuantity());
                    detailResponse.setUnitPrice(detail.getUnitPrice());
                    return detailResponse;
                })
                .collect(Collectors.toList());
        
        response.setOrderDetailResponses(detailResponses);
        return response;
    }
}

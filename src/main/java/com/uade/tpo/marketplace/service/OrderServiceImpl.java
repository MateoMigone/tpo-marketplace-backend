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


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public OrderResponse createOrder(User user, OrderRequest orderRequest) throws NoStockAvailableException {

        LocalDateTime dateTime = LocalDateTime.now();

        Order order = new Order();
        order.setUser(user);
        order.setDate(dateTime);
        order.setAddress(orderRequest.getAddress());

        List<OrderDetailRequest> itemList = orderRequest.getItemList();
        List<OrderDetailResponse> itemListResponse = new ArrayList<OrderDetailResponse>();
        Double totalPrice = 0.00;

        for (OrderDetailRequest itemDetail : itemList) {
            Game game = gameRepository.findById(itemDetail.getGameId())
                    .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + itemDetail.getGameId()));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGame(game);
            orderDetail.setQuantity(itemDetail.getQuantity());
            orderDetail.setUnitPrice(game.getPrice());
            order.addOrderDetail(orderDetail);

            totalPrice += itemDetail.getQuantity()*game.getPrice();

            if (itemDetail.getQuantity() > game.getStock()){
                throw new NoStockAvailableException();
            }
            game.setStock(game.getStock()-itemDetail.getQuantity());
            gameRepository.save(game);

            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setGameId(itemDetail.getGameId());
            orderDetailResponse.setQuantity(itemDetail.getQuantity());
            orderDetailResponse.setUnitPrice(game.getPrice());
            itemListResponse.add(orderDetailResponse);
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
}

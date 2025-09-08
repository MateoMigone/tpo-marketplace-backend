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
        // Creamos un objeto fecha con la fecha actual al momento de generar el nuevo pedido
        LocalDateTime dateTime = LocalDateTime.now();

        // Creamos objeto order para cargarlo con la info
        Order order = new Order();
        order.setUser(user);
        order.setDate(dateTime);
        order.setAddress(orderRequest.getAddress());

        // Creamos una lista con los items del pedido
        List<OrderDetailRequest> itemList = orderRequest.getItemList();
        // Creamos una lista vacía para guardar los items a ser devueltos en la order response
        List<OrderDetailResponse> itemListResponse = new ArrayList<OrderDetailResponse>();
        // Inicializamos variable para calcular precio total del pedido
        Double totalPrice = 0.00;

        // Recorremos los items del pedido
        for (OrderDetailRequest itemDetail : itemList) {
            // Buscamos el juego dentro del item por su id
            Long gameId = itemDetail.getGameId();
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + itemDetail.getGameId()));

            // Chequeamos que haya stock suficiente para ese juego
            Integer stock = game.getStock();
            Integer quantity = itemDetail.getQuantity();
            if (quantity > stock){
                throw new NoStockAvailableException();
            }

            // Le restamos la cantidad comprada al stock y actualizamos el juego
            game.setStock(stock - quantity);
            gameRepository.save(game);

            Double finalPrice = game.getFinalPrice();

            // Creamos un objeto OrderDetail para cargarle la informacion y lo cargamos en el objeto order
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGame(game);
            orderDetail.setQuantity(quantity);
            orderDetail.setUnitPrice(finalPrice);
            order.addOrderDetail(orderDetail);

            // Creamos un objeto OrderDetailResponse para cargarle la info y lo agregamos a la lista orderDetailResponse
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setGameId(gameId);
            orderDetailResponse.setQuantity(quantity);
            orderDetailResponse.setUnitPrice(finalPrice);
            itemListResponse.add(orderDetailResponse);

            // Iteramos para ir calculando precio total
            totalPrice += quantity * finalPrice;
        }

        // Terminamos de cargar el objeto order con el precio total calculado y lo guardamos
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Creamos el objeto OrderResponse el cual sera devuelto como respuesta y lo cargamos con la info
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

package com.example.todoappdeel3.services;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.example.todoappdeel3.Enums.UserRole;
import com.example.todoappdeel3.dto.CartComponentsDTO;
import com.example.todoappdeel3.dto.PlaceOrderDTO;
import com.example.todoappdeel3.models.CartComponents;
import com.example.todoappdeel3.models.Order;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.repository.CartComponentsRepository;
import com.example.todoappdeel3.repository.CartComponentsRepository;
import com.example.todoappdeel3.repository.OrderRepository;
import com.example.todoappdeel3.repository.ProductRepository;
import com.example.todoappdeel3.repository.CartComponentsRepository;
import com.example.todoappdeel3.repository.UserRepository;
import com.example.todoappdeel3.models.CustomUser;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userDAO;
    private final OrderRepository orderRepository;
    private final CartComponentsRepository CartComponentsRepository;

    private final ProductRepository productRepository;


    @Autowired
    public UserService(UserRepository userDAO, OrderRepository orderRepository, CartComponentsRepository CartComponentsRepository, ProductRepository productRepository) {
        this.userDAO = userDAO;
        this.orderRepository = orderRepository;
        this.CartComponentsRepository = CartComponentsRepository;
        this.productRepository = productRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userDAO.findByEmail(email);

        return new User(
                email,
                customUser.getPassword(),
                Collections.singleton((new SimpleGrantedAuthority(customUser.getUserRole().name()))));

    }

    @PostConstruct
    public void createAdmin() {
        CustomUser adminUser = userDAO.findByUserRole(UserRole.ADMIN);

        if (adminUser == null) {
            CustomUser customUser = new CustomUser();
            customUser.setUserRole(UserRole.ADMIN);
            customUser.setEmail("admin@real.com");
            customUser.setPassword(new BCryptPasswordEncoder().encode("Admin123!"));
            userDAO.save(customUser);

        }


    }


    public ResponseEntity<?> addProductToCart(CartComponentsDTO CartComponentsDTO) {
        Optional<Order> pendingOrder = orderRepository.findByUserIdAndOrderStatus(CartComponentsDTO.getUserid(), OrderStatus.Pending);
        if (pendingOrder.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart doesnt exists"
            );
        }
        Optional<CartComponents> optionalCartComponents = CartComponentsRepository.findByUserIdAndProductIdAndOrderId(CartComponentsDTO.getUserid(), CartComponentsDTO.getProductid(), pendingOrder.get().getId());
        if (optionalCartComponents.isPresent()) {
            Optional<Product> optionalProduct = productRepository.findById(CartComponentsDTO.getProductid());
            Order pendingOrderFix = pendingOrder.get();
            CartComponents cartItem = optionalCartComponents.get();
            Product product = optionalProduct.get();
            cartItem.setQuantity(optionalCartComponents.get().getQuantity() + CartComponentsDTO.getQuantity());
            cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
            pendingOrderFix.setPrice(pendingOrderFix.getPrice() + (product.getPrice() * CartComponentsDTO.getQuantity()));
            CartComponentsRepository.save(cartItem);
            orderRepository.save(pendingOrderFix);
            Order newOrder = orderRepository.findOrderById(pendingOrderFix.getId()).get();
            return ResponseEntity.ok(newOrder);
        } else {
            Order pendingOrderFix = pendingOrder.get();
            Optional<Product> optionalProduct = productRepository.findById(CartComponentsDTO.getProductid());
            Optional<CustomUser> optionalCustomUser = userDAO.findById(CartComponentsDTO.getUserid());
            if (optionalProduct.isPresent() && optionalCustomUser.isPresent()) {
                Product product = optionalProduct.get();
                CartComponents CartComponents = new CartComponents();
                CartComponents.setProduct(product);
                CartComponents.setImageUrl(product.getImageUrl());
                CartComponents.setProductName(product.getName());
                CartComponents.setUser(optionalCustomUser.get());
                CartComponents.setQuantity(CartComponentsDTO.quantity);
                CartComponents.setOrder(pendingOrderFix);
                CartComponents.setPrice(product.getPrice() * CartComponentsDTO.quantity);
                CartComponents updatedshopping = CartComponentsRepository.save(CartComponents);
                pendingOrderFix.setPrice(pendingOrderFix.getPrice() + CartComponents.getPrice());
                pendingOrderFix.getCartComponents().add(CartComponents);
                orderRepository.save(pendingOrderFix);
                CartComponentsDTO updatedCartComponentsDTO = new CartComponentsDTO();
                updatedCartComponentsDTO.setId(updatedshopping.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartComponentsDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user or product not found");
            }
        }
    }
//    public ResponseEntity<List<CartComponents>> getCartbyuserid(Long id) {
//        Optional<CartComponents> cart = this.CartComponentsRepository.findByUserId(id);
//
//        if (cart.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "No cart found with that id"
//            );
//        }
//
//        return ResponseEntity.ok(cart.stream().toList());
//
//    }


//    public ResponseEntity<List<CartComponents>> getitemsbyorderId(Long id) {
//        Optional<CartComponents> CartComponents = this.CartComponentsRepository.findCartComponentsByOrderId(id);
//
//        if (CartComponents.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "No cart found with that id"
//            );
//        }
//
//        return ResponseEntity.ok(CartComponents.stream().toList());
//
//    }

    public ResponseEntity<Order> getCartByUserId(Long userid) {
        Optional<Order> order = orderRepository.findByUserIdAndOrderStatus(userid, OrderStatus.Pending);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No order found with that user"
            );
        }
//        List<CartComponentsDTO> CartComponentsorder = order.get().getCartComponents().stream().map(CartComponents::getcartItemDTO).toList();
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setCart(CartComponentsorder);
//        orderDTO.setAmmount(order.get().getPrice());
//        orderDTO.setOrderStatus(order.get().getOrderStatus());
//        orderDTO.setId(order.get().getId());
//        orderDTO.setUser(order.get().getUser());
        return ResponseEntity.ok(order.get());
    }


    @Transactional

    public ResponseEntity<String> deleteOrderCartComponentsBycartimtemId(CartComponentsDTO CartComponentsDTO) {

        Optional<Order> pendingOrder = orderRepository.findByUserIdAndOrderStatus(CartComponentsDTO.getUserid(), OrderStatus.Pending);
        if (pendingOrder.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart doesnt exists"
            );

        }
        Optional<CartComponents> optionalCartComponents = CartComponentsRepository.findByUserIdAndProductIdAndOrderId(CartComponentsDTO.getUserid(), CartComponentsDTO.getProductid(), pendingOrder.get().getId());
        if (optionalCartComponents.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart doesnt exists"
            );

        }
        Order exisstingOrder = pendingOrder.get();
        CartComponents CartComponents= optionalCartComponents.get();
        exisstingOrder.setPrice(exisstingOrder.getPrice() - CartComponents.getPrice());
        CartComponentsRepository.delete(CartComponents);
        orderRepository.save(exisstingOrder);
        return ResponseEntity.ok("product deleted");


    }

    public ResponseEntity<String> minusQuantity(Long userid, Long productId) {

        Optional<Order> pendingOrder = orderRepository.findByUserIdAndOrderStatus(userid, OrderStatus.Pending);
        if (pendingOrder.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart doesnt exists"
            );

        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<CartComponents> optionalCartComponents = CartComponentsRepository.findByUserIdAndProductIdAndOrderId(userid, productId, pendingOrder.get().getId());
        if (optionalCartComponents.isEmpty() || optionalProduct.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart or product doesnt exists"
            );
        }
        Order pendingOrderFix = pendingOrder.get();
        CartComponents CartComponents = optionalCartComponents.get();
        Product product = optionalProduct.get();
        CartComponents.setQuantity(optionalCartComponents.get().getQuantity() - 1);
        CartComponents.setPrice(CartComponents.getQuantity() * product.getPrice());
        pendingOrderFix.setPrice(pendingOrderFix.getPrice() - product.getPrice());
        CartComponentsRepository.save(CartComponents);
        orderRepository.save(pendingOrderFix);
        return ResponseEntity.ok("product quantity is modified");
    }

    public ResponseEntity<String> addQuantity(Long userid, Long productId) {

        Optional<Order> pendingOrder = orderRepository.findByUserIdAndOrderStatus(userid, OrderStatus.Pending);
        if (pendingOrder.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart doesnt exists"
            );

        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<CartComponents> optionalCartComponents = CartComponentsRepository.findByUserIdAndProductIdAndOrderId(userid, productId, pendingOrder.get().getId());
        if (optionalCartComponents.isEmpty() || optionalProduct.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart or product doesnt exists"
            );
        }
        Order pendingOrderFix = pendingOrder.get();
        CartComponents CartComponents = optionalCartComponents.get();
        Product product = optionalProduct.get();
        CartComponents.setQuantity(optionalCartComponents.get().getQuantity() + 1);
        CartComponents.setPrice(CartComponents.getQuantity() * product.getPrice());
        pendingOrderFix.setPrice(pendingOrderFix.getPrice() + product.getPrice());
        CartComponentsRepository.save(CartComponents);
        orderRepository.save(pendingOrderFix);
        return ResponseEntity.ok("product quantity is modified");
    }

    public ResponseEntity<String> findOrderById(Long orderId, PlaceOrderDTO placeOrderDTO) {

        Optional<Order> existingOrder = orderRepository.findOrderById(orderId);
        if (existingOrder.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "cart or product doesnt exists"
            );}

        Order realExistingOrder = existingOrder.get();
        realExistingOrder.setReview(placeOrderDTO.review);
        realExistingOrder.setPayment(placeOrderDTO.payment);
        realExistingOrder.setOrderStatus(OrderStatus.Placed);
        realExistingOrder.setReceiveDate(placeOrderDTO.receiveDate);
        Order order = new Order();
        order.setUser(realExistingOrder.getUser());
        order.setOrderStatus(OrderStatus.Pending);
        order.setPrice(0.0);
        orderRepository.save(realExistingOrder);
        orderRepository.save(order);

        return ResponseEntity.ok("Order has been placed");

    }

    public ResponseEntity<Order> getPlacedOrderByUserId(Long userid, Long orderid) {
        Optional<Order> order = orderRepository.findByUserIdAndOrderStatusAndId(userid, OrderStatus.Placed, orderid);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No order found with that user"
            );
        }
//        List<CartComponentsDTO> CartComponentsorder = order.get().getCartComponents().stream().map(CartComponents::getcartItemDTO).toList();
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setCart(CartComponentsorder);
//        orderDTO.setAmmount(order.get().getPrice());
//        orderDTO.setOrderStatus(order.get().getOrderStatus());
//        orderDTO.setId(order.get().getId());
//        orderDTO.setUser(order.get().getUser());
        return ResponseEntity.ok(order.get());
    }

    public ResponseEntity<?> getAllPlacedOrdersByUserId(Long userid) {
        Optional<List<Order>> placedorders = orderRepository.findOrdersByUserIdAndOrderStatus(userid, OrderStatus.Placed);
        if (placedorders.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No order found with that user"
            );
        }
//        List<CartComponentsDTO> CartComponentsorder = order.get().getCartComponents().stream().map(CartComponents::getcartItemDTO).toList();
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setCart(CartComponentsorder);
//        orderDTO.setAmmount(order.get().getPrice());
//        orderDTO.setOrderStatus(order.get().getOrderStatus());
//        orderDTO.setId(order.get().getId());
//        orderDTO.setUser(order.get().getUser());
        return ResponseEntity.ok(placedorders.get());
    }

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        if (allOrders.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No orders have been placed"
            );
        }

        return ResponseEntity.ok(allOrders);


    }

    public ResponseEntity<String> deleteUserById(Long userid) {
        Optional<CustomUser> user = userDAO.findCustomUserById(userid);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User doesnt exists"
            );}

        userDAO.delete(user.get());

        return ResponseEntity.ok("user has been deleted");

    }

}
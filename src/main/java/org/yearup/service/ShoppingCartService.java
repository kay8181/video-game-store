package org.yearup.service;

import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import org.springframework.data.web.config.SpringDataWebSettings;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final SpringDataWebSettings springDataWebSettings;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, SpringDataWebSettings springDataWebSettings)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.springDataWebSettings = springDataWebSettings;
    }

    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart shoppingCart = new ShoppingCart();
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        for (CartItem item : cartItems) {
            int productId = item.getProductId();
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(productService.getById(productId));
            shoppingCartItem.setQuantity(item.getQuantity()); // Getting quantity or else it will always be 1
            shoppingCart.add(shoppingCartItem);
        }

        return shoppingCart;
    }


    // add additional methods here

    public ShoppingCart addToCart(ShoppingCart cart, int productId, int userId)
    {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        CartItem existingItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProduct(product);

        if (existingItem != null) {
            int existingQuantity = existingItem.getQuantity();
            int newQuantity = existingQuantity + 1;
            existingItem.setQuantity(newQuantity);
            shoppingCartItem.setQuantity(newQuantity);
            shoppingCartRepository.save(existingItem);

        } else {
            CartItem newItem = new CartItem();
            newItem.setCartItemId(0);
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            shoppingCartItem.setQuantity(1);
            shoppingCartRepository.save(newItem);
        }

        cart.add(shoppingCartItem);

        return cart;
    }

    public ShoppingCart updateItemInCart(int productId, ShoppingCart shoppingCart, int quantity) {
        ShoppingCart cart = shoppingCart;
        int id = productId;
        ShoppingCartItem item = cart.get(productId);
        item.setQuantity(quantity);

        return cart;
    }

    @Transactional
    public void deleteCart(int userId) {

        shoppingCartRepository.deleteByUserId(userId);

    }
}


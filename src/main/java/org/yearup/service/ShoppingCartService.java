package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
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
            shoppingCart.add(shoppingCartItem);
        }

        return shoppingCart;
    }


    // add additional methods here

    public ShoppingCart addToCart(ShoppingCart shoppingCart, int productId)
    {
        ShoppingCart cart = shoppingCart;
        int id = productId;
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProduct(productService.getById(productId));
        shoppingCart.add(shoppingCartItem);

        return cart;
    }

    public ShoppingCart updateItemInCart(int productId, ShoppingCart shoppingCart, int quantity) {
        ShoppingCart cart = shoppingCart;
        int id = productId;
        ShoppingCartItem item = cart.get(productId);
        item.setQuantity(quantity);

        return cart;
    }

    public void deleteCart(int userId) {

        shoppingCartRepository.deleteByUserId(userId);

    }
}

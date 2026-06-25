package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.*;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private ProductService productService;


    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService, ProductService productService ) {
        this.shoppingCartService = shoppingCartService;
         this.userService = userService;
        this.productService = productService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal)
    {
        // get the currently logged in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use the shoppingCartService to get all items in the cart and return the cart
        return shoppingCartService.getByUserId(userId);
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created
    @PostMapping("/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart addToCart(Principal principal, @PathVariable int id, @RequestBody ShoppingCart shoppingCart)
    {
        shoppingCart = getCart(principal);
        if (productService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return shoppingCartService.addToCart(shoppingCart, id);
        //return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
    @PutMapping("/cart/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart updateItemInCart(Principal principal, @PathVariable int id, @RequestBody ShoppingCartItem shoppingCartItem, ShoppingCart shoppingCart, int quantity)
    {
        shoppingCart = getCart(principal);
        if (productService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (shoppingCart.get(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ShoppingCart cart =  shoppingCartService.updateItemInCart(id, shoppingCart, quantity);
        shoppingCartItem =  cart.get(id);

        return cart;
    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)
    @DeleteMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart deleteCart(Principal principal, @RequestBody ShoppingCart shoppingCart)
    {
        shoppingCart = getCart(principal);
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        shoppingCartService.deleteCart(userId);

        return shoppingCart;
    }

}

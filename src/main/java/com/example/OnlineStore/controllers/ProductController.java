package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Cart;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repo.CartRepository;
import com.example.OnlineStore.repo.ProductRepository;
import com.example.OnlineStore.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final AddProductService addProductService;
    private final AddToCartService addToCartService;
    private final ViewProductService viewProductService;
    private final SortingService sortingService;

    public ProductController(ProductRepository productRepository, CartRepository cartRepository, AddProductService addProductService, AddToCartService addToCartService, ViewProductService viewProductService, SortingService sortingService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.addProductService = addProductService;
        this.addToCartService = addToCartService;
        this.viewProductService = viewProductService;
        this.sortingService = sortingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        String keyword = null;
        return listByPage(model, 1, "name", "asc", keyword);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
        Page<Product> page = sortingService.listAll(currentPage, sortField, sortDir, keyword);
        int total = page.getNumberOfElements();
        int totalPages = page.getTotalPages();
        List<Product> productList = page.getContent();
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productList", productList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "main";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam String description, @RequestParam int price, @RequestParam String category, Model model) {
        addProductService.addProduct(name, description, price, category);
        return "redirect:/";
    }

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable(value = "id") int id, Model model) {
        viewProductService.viewProduct(id, model);
        return "productDetails";
    }

    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable(value = "id") int id, Model model) {
        if(!productRepository.existsById(id)) {
            return "redirect:/product";
        }
        viewProductService.viewProduct(id, model);
        return "productEdit";
    }

    @PostMapping("/product/{id}/edit")
    public String updateProduct(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String description, @RequestParam int price, @RequestParam String category, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        productRepository.save(product);
        return "redirect:/";
    }

    @PostMapping("/product/{id}/remove")
    public String removeProduct(@PathVariable(value = "id") int id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/";
    }

    @GetMapping("/product/{id}/addToCart")
    public String addProductToCart(@PathVariable(value = "id") int id, Model model) {
        if(!productRepository.existsById(id)) {
            return "redirect:/product";
        }
        viewProductService.viewProduct(id, model);
        return "addToCart";
    }

    @PostMapping("/product/{id}/addToCart")
    public String addToCart(@PathVariable(value = "id") int id, @RequestParam int quantity, @RequestParam String category, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        addToCartService.addToCart(product, quantity, category);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        List<Cart> cartList = cartRepository.findAll();
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    @PostMapping("/cart/{id}/remove")
    public String removeFromCart(@PathVariable(value = "id") int id, Model model) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cartRepository.delete(cart);
        return "redirect:/cart";
    }
}

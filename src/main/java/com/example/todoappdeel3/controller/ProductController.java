package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins ={"http://localhost:4200", "http://s1149822.student.inf-hsleiden.nl:19822"})
@RequestMapping("/products")
public class ProductController {

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(this.productDAO.getAllProducts());
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam Long categoryId){

        return ResponseEntity.ok(this.productDAO.getAllProductsByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        this.productDAO.createProduct(productDTO);
        return ResponseEntity.ok("Created a product");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        this.productDAO.updateProduct(productDTO, id);

        return ResponseEntity.ok("Updated product with id" + id);
    }

    @PutMapping("check/{id}")
    public ResponseEntity<String> checkProduct(@PathVariable Long id){
        this.productDAO.checkProduct(id);

        return ResponseEntity.ok("Product checked with id " + id);
    }

    @PutMapping("uncheck/{id}")
    public ResponseEntity<String> uncheckProduct(@PathVariable Long id){
        this.productDAO.uncheckProduct(id);

        return ResponseEntity.ok("Product uncheck with id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        this.productDAO.deleteById(id);

        return ResponseEntity.ok("Product deleted with id " + id);
    }
}
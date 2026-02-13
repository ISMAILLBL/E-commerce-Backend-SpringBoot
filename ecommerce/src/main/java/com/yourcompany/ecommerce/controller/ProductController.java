package com.yourcompany.ecommerce.controller;

import com.yourcompany.ecommerce.model.Product;
import com.yourcompany.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    /* ────────────────────────────
       GET : liste complète
       ──────────────────────────── */
    @GetMapping
    public List<Product> list() {
        return repo.findAll();
    }

    /* ────────────────────────────
       GET : un produit par ID
       ──────────────────────────── */
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /* ────────────────────────────
       POST : créer un produit
       ────────────────────────────
       ➜  Dé-commente @PreAuthorize si tu ajoutes
           une sécurité « ADMIN » plus tard.
    */
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = repo.save(product);
        // renvoie 201 + URI Location : /api/products/{id}
        return ResponseEntity
                .created(URI.create("/api/products/" + saved.getId()))
                .body(saved);
    }
}

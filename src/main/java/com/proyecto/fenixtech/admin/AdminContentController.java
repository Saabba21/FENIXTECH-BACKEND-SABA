package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.model.Categories;
import com.proyecto.fenixtech.model.Comments;
import com.proyecto.fenixtech.model.Posts;
import com.proyecto.fenixtech.model.Products;
import com.proyecto.fenixtech.model.Subcategories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/content")
@PreAuthorize("hasRole('ADMIN')")
public class AdminContentController {

    @Autowired
    private AdminContentService adminContentService;

    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        return ResponseEntity.ok(adminContentService.findAllCategories());
    }

    // Categorías y Subcategorías
    @PostMapping("/categories")
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminContentService.saveCategory(category));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Integer id, @RequestBody Categories category) {
        return ResponseEntity.ok(adminContentService.updateCategory(id, category));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        adminContentService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subcategories")
    public ResponseEntity<List<Subcategories>> getAllSubcategories() {
        return ResponseEntity.ok(adminContentService.findAllSubcategories());
    }

    @PostMapping("/subcategories")
    public ResponseEntity<Subcategories> createSubcategory(@RequestBody com.proyecto.fenixtech.dto.SubcategoriesRequestDTO subcategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminContentService.saveSubcategory(subcategory));
    }

    @PutMapping("/subcategories/{id}")
    public ResponseEntity<Subcategories> updateSubcategory(@PathVariable Integer id, @RequestBody com.proyecto.fenixtech.dto.SubcategoriesRequestDTO subcategory) {
        return ResponseEntity.ok(adminContentService.updateSubcategory(id, subcategory));
    }

    @DeleteMapping("/subcategories/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Integer id) {
        adminContentService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts() {
        return ResponseEntity.ok(adminContentService.findAllProducts());
    }

    // Moderación (Productos, Posts, Comentarios)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        adminContentService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/posts")
    public ResponseEntity<List<Posts>> getAllPosts() {
        return ResponseEntity.ok(adminContentService.findAllPosts());
    }
    
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        adminContentService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> getAllComments() {
        return ResponseEntity.ok(adminContentService.findAllComments());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        adminContentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
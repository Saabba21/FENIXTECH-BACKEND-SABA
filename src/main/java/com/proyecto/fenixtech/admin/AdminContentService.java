package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.model.Categories;
import com.proyecto.fenixtech.model.Comments;
import com.proyecto.fenixtech.model.Posts;
import com.proyecto.fenixtech.model.Products;
import com.proyecto.fenixtech.model.Subcategories;
import com.proyecto.fenixtech.model.Proposals;
import com.proyecto.fenixtech.model.enums.ProposalStatus;
import com.proyecto.fenixtech.exception.ResourceNotFoundException;
import com.proyecto.fenixtech.repository.CategoriesRepository;
import com.proyecto.fenixtech.repository.SubcategoriesRepository;
import com.proyecto.fenixtech.repository.ProductsRepository;
import com.proyecto.fenixtech.repository.PostsRepository;
import com.proyecto.fenixtech.repository.CommentsRepository;
import com.proyecto.fenixtech.repository.ProposalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminContentService {

    @Autowired private CategoriesRepository categoriesRepository;
    @Autowired private SubcategoriesRepository subcategoriesRepository;
    @Autowired private ProductsRepository productsRepository;
    @Autowired private PostsRepository postsRepository;
    @Autowired private CommentsRepository commentsRepository;
    @Autowired private ProposalsRepository proposalsRepository;

    public Categories saveCategory(Categories category) { return categoriesRepository.save(category); }

    public List<Categories> findAllCategories() { return categoriesRepository.findAll(); }

    public Categories updateCategory(Integer id, Categories details) {
        Categories cat = categoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        cat.setName(details.getName());
        return categoriesRepository.save(cat);
    }

    public void deleteCategory(Integer id) { categoriesRepository.deleteById(id); }
    
    public List<Subcategories> findAllSubcategories() { return subcategoriesRepository.findAll(); }

    public Subcategories saveSubcategory(Subcategories subcategory) { return subcategoriesRepository.save(subcategory); }

    public Subcategories updateSubcategory(Integer id, Subcategories details) {
        Subcategories sub = subcategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada con ID: " + id));
        sub.setName(details.getName());
        sub.setCategory(details.getCategory());
        return subcategoriesRepository.save(sub);
    }

    public void deleteSubcategory(Integer id) { subcategoriesRepository.deleteById(id); }

    public List<Products> findAllProducts() { return productsRepository.findAll(); }

    public void deleteProduct(Integer id) { productsRepository.deleteById(id); }
    
    public List<Posts> findAllPosts() { return postsRepository.findAll(); }

    public void deletePost(Integer id) { postsRepository.deleteById(id); }
    
    public List<Comments> findAllComments() { return commentsRepository.findAll(); }

    public void deleteComment(Integer id) { commentsRepository.deleteById(id); }

    public List<Proposals> findAllProposals() { return proposalsRepository.findAll(); }

    public void updateProposalStatus(Integer id, String status) {
        Proposals p = proposalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propuesta no encontrada con ID: " + id));
        try {
            p.setStatus(ProposalStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de propuesta no válido: " + status);
        }
        proposalsRepository.save(p);
    }

    public void deleteProposal(Integer id) { proposalsRepository.deleteById(id); }
}
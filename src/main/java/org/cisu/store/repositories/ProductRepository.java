package org.cisu.store.repositories;

import org.cisu.store.dtos.ProductSummary;
import org.cisu.store.dtos.ProductSummaryDTO;
import org.cisu.store.entities.Category;
import org.cisu.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    // select * from products where name = ?
//    List<Product> findByName(String name);
//
//    // select * from products where name like ?
//    List<Product> findByNameLike(String name);
//
//
//    List<Product> findByNameNotLike(String name);
//    List<Product> findByNameContaining(String name);
//
//    List<Product> findByNameContains(String name);
//    List<Product> findByNameStartingWith(String name);
//    List<Product> findByNameEndingWith(String name);
//
//    List<Product> findByNameEndingWithIgnoreCase(String name);
//
//
//    // Numbers
//    List<Product> findByPrice(BigDecimal price);
//
//    List<Product> findByPriceGreaterThan(BigDecimal price);
//    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
//
//
//    // null
//    List<Product> findByDescriptionNull(BigDecimal price);
//    List<Product> findByDescriptionNotNull(BigDecimal price);
//
//    // Multiple conditions
//    List<Product> findByDescriptionNullAndNameNull();
//
//    // Sort (OrderBy)
//    List<Product> findByNameOrderByPriceAsc(String name);
//
//    // Limit (Top/First)
//    List<Product> findTop5ByNameOrderByPriceAsc(String name);
//    List<Product> findFirst5ByNameOrderByPriceAsc(String name);


    // Find products whose prices are in a given range and sort by name
    // findByPriceBetweenOrderByName
    // SQL
    @Query(value = "select * from store.products p where p.price between :min and :max order by p.name", nativeQuery = true)
    List<Product> findProductsSQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
    // JPQL
//    @Query("select p from Product p where p.price between :min and :max order by p.name")
    @Query("select p from Product p join p.category where p.price between :min and :max order by p.name")
    List<Product> findProductsJPQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // with Procedure
    @Procedure("findProductsByPrice")
    List<Product> findProductsProcedure(BigDecimal min, BigDecimal max);


    @Query("select count(*) from Product p where p.price between :min and :max")
    long contProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // for update scenarios we need to add @Modifying
    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category = :categoryId")
    void updatePriceByCategory(BigDecimal newPrice, Byte categoryId);


    @Query("select new org.cisu.store.dtos.ProductSummaryDTO(p.id, p.name)  from Product p where p.category = :category")
    List<ProductSummaryDTO> findByCategory(@Param("category") Category category);
}
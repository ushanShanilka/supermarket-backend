package com.supermarket.service.impl;

import com.supermarket.dto.Paginated.PaginatedDTO;
import com.supermarket.entity.Product;
import com.supermarket.entity.ProductHasImage;
import com.supermarket.entity.ProductHasProductSize;
import com.supermarket.entity.Status;
import com.supermarket.excepton.EntryNotFoundException;
import com.supermarket.repository.ProductHasImageRepo;
import com.supermarket.repository.ProductHasProductSizeRepo;
import com.supermarket.repository.ProductRepo;
import com.supermarket.repository.StatusRepo;
import com.supermarket.service.ProductService;
import com.supermarket.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductHasImageRepo productHasImageRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    ProductHasProductSizeRepo productHasProductSizeRepo;

    @Override
    public PaginatedDTO searchProduct (String value) {
        List<Product> products = productRepo.searchProduct(value);
        long count = productRepo.searchProductCount(value);
        PaginatedDTO paginatedDTO = new PaginatedDTO();

        Status active = statusRepo.findById(StatusId.ACTIVE);

        List<Map<String, Object>> maps = new ArrayList<>();
        for (Product product :products) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("rating", product.getRating());
            obj.put("price", String.format("%.2f", product.getPrice()));


            List<ProductHasImage> images = productHasImageRepo.findByProductIdAndStatusId(product, active);
            if (!Objects.equals(images, null)){
                obj.put("image", images.get(0).getImageId().getUrl());
            }

            maps.add(obj);
        }
        paginatedDTO.setList(maps);
        paginatedDTO.setCount(count);
        return paginatedDTO;
    }

    @Override
    public PaginatedDTO getRandomProducts () {
        List<Product> randomProduct = productRepo.getRandomProduct();

        PaginatedDTO paginatedDTO = new PaginatedDTO();

        Status active = statusRepo.findById(StatusId.ACTIVE);

        List<Map<String, Object>> maps = new ArrayList<>();
        for (Product product :randomProduct) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("rating", product.getRating());
            obj.put("price", String.format("%.2f", product.getPrice()));


            List<ProductHasImage> images = productHasImageRepo.findByProductIdAndStatusId(product, active);
            if (!Objects.equals(images, null)){
                obj.put("image", images.get(0).getImageId().getUrl());
            }

            maps.add(obj);
        }
        paginatedDTO.setList(maps);
        return paginatedDTO;
    }

    @Override
    public Map<String, Object> getProduct (Long id) {
        Status active = statusRepo.findById(StatusId.ACTIVE);

        Product product = productRepo.findByIdAndStatusId(id, active);
        if (!Objects.equals(product, null)){
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("description", product.getDescriptions());
            obj.put("price", String.format("%.2f", product.getPrice()));


            List<ProductHasImage> images = productHasImageRepo.findByProductIdAndStatusId(product, active);
            List<Map<String, Object>> maps = new ArrayList<>();
            for (ProductHasImage image :images) {
                Map<String, Object> img = new HashMap<>();
                img.put("url", image.getImageId().getUrl());
                maps.add(img);
            }

            System.out.println(product.getId());
            ProductHasProductSize sizes = productHasProductSizeRepo.findByProductIdAndStatusId(product, active);
            if (!Objects.equals(sizes, null)){
                obj.put("size", sizes.getProductSizeId().getSize());
            }

            obj.put("images", maps);
            return obj;
        }
        throw new EntryNotFoundException("product now found");
    }
}

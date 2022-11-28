package com.supermarket.api;

import com.supermarket.dto.Paginated.PaginatedDTO;
import com.supermarket.service.ProductService;
import com.supermarket.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(path = "/search", params = "value")
    public ResponseEntity<StandardResponse> getAllProduct(@RequestParam String value){
        PaginatedDTO paginatedDTO = productService.searchProduct(value);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",paginatedDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/rand")
    public ResponseEntity<StandardResponse> getRandomProduct(){
        PaginatedDTO paginatedDTO = productService.getRandomProducts();
        return new ResponseEntity<>(
                new StandardResponse(200,"success",paginatedDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getProduct(@PathVariable Long id){
        Map<String, Object> product = productService.getProduct(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",product),
                HttpStatus.CREATED
        );
    }
}

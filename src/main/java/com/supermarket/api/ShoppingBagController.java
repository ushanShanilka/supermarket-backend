package com.supermarket.api;

import com.supermarket.dto.ShoppingBagDTO;
import com.supermarket.service.ShoppingBagService;
import com.supermarket.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
@RestController
@RequestMapping("/api/v1/shopping/bags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShoppingBagController {

    @Autowired
    ShoppingBagService shoppingBagService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveShoppingBag(@RequestHeader("Authorization") String AUTHORIZATION_HEADER,
                                                            @RequestBody ShoppingBagDTO dto){
        String s = shoppingBagService.saveShoppingBag(AUTHORIZATION_HEADER, dto);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getShoppingBag(@RequestHeader("Authorization") String AUTHORIZATION_HEADER){
        List<Map<String, Object>> allShoppingBagItems = shoppingBagService.getAllShoppingBagItems(AUTHORIZATION_HEADER);
        return new ResponseEntity<>(
                new StandardResponse(200,"success",allShoppingBagItems),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> removeProductFromShoppingBag(@RequestHeader("Authorization") String AUTHORIZATION_HEADER,
                                                                         @PathVariable Long id){
        String s = shoppingBagService.removeProductFromShoppingBag(AUTHORIZATION_HEADER, id);
        return new ResponseEntity<>(
                new StandardResponse(200,s,null),
                HttpStatus.CREATED
        );
    }


}

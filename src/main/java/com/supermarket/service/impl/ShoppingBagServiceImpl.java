package com.supermarket.service.impl;

import com.supermarket.dto.ShoppingBagDTO;
import com.supermarket.entity.*;
import com.supermarket.excepton.EntryNotFoundException;
import com.supermarket.repository.*;
import com.supermarket.service.ShoppingBagService;
import com.supermarket.util.ShoppingBagStatusId;
import com.supermarket.util.StatusId;
import com.supermarket.util.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
@Service
public class ShoppingBagServiceImpl implements ShoppingBagService {

    @Autowired
    ShoppingBagRepo shoppingBagRepo;
    @Autowired
    TokenChecker tokenChecker;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    ShoppingBagStatusRepo shoppingBagStatusRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ShoppingBagHasProductRepo shoppingBagHasProductRepo;
    @Autowired
    ProductHasImageRepo productHasImageRepo;


    @Override
    public String saveShoppingBag (String AUTHORIZATION_HEADER, ShoppingBagDTO dto) {
        UserLoginCredential userLoginCredential = tokenChecker.userTokenChecker(AUTHORIZATION_HEADER);
        if (!Objects.equals(userLoginCredential, null)){
            Status active = statusRepo.findById(StatusId.ACTIVE);
            ShoppingBagStatus shoppingBgaStatus = shoppingBagStatusRepo.findById(ShoppingBagStatusId.ACTIVE);

            Date date = new Date();

            Product product = productRepo.findByIdAndStatusId(dto.getProductId(), active);
            if (Objects.equals(product, null)){
                throw new EntryNotFoundException("product not found");
            }

            ShoppingBag shoppingBag;
            shoppingBag = shoppingBagRepo.findByUserIdAndShoppingBagStatusIdAndStatusId(userLoginCredential.getUserId(), shoppingBgaStatus, active);
            if (Objects.equals(shoppingBag, null)){
                shoppingBag = new ShoppingBag(
                        null,
                        date,
                        date,
                        null,
                        active,
                        userLoginCredential.getUserId(),
                        shoppingBgaStatus
                );
                shoppingBag = shoppingBagRepo.save(shoppingBag);
            }

            ShoppingBagHasProduct shoppingBagHasProduct = shoppingBagHasProductRepo.findByProductIdAndShoppingBagIdAndStatusId(product, shoppingBag, active);
            if (Objects.equals(shoppingBagHasProduct, null)){
                ShoppingBagHasProduct sho = new ShoppingBagHasProduct(
                        null,
                        date,
                        date,
                        dto.getQuantity(),
                        product,
                        shoppingBag,
                        active
                );
                shoppingBagHasProductRepo.save(sho);
                return "success";
            }
            shoppingBagHasProduct.setQuantity((short) (shoppingBagHasProduct.getQuantity() + dto.getQuantity()));
            shoppingBagHasProduct.setUpdatedAt(date);
            shoppingBagHasProductRepo.save(shoppingBagHasProduct);

            return "success";
        }
        throw new EntryNotFoundException("invalid user");
    }

    @Override
    public List<Map<String, Object>> getAllShoppingBagItems (String AUTHORIZATION_HEADER) {
        UserLoginCredential userLoginCredential = tokenChecker.userTokenChecker(AUTHORIZATION_HEADER);
        if (!Objects.equals(userLoginCredential, null)) {
            Status active = statusRepo.findById(StatusId.ACTIVE);
            ShoppingBagStatus shoppingBgaStatus = shoppingBagStatusRepo.findById(ShoppingBagStatusId.ACTIVE);

            ShoppingBag shoppingBag = shoppingBagRepo.findByUserIdAndShoppingBagStatusIdAndStatusId(userLoginCredential.getUserId(), shoppingBgaStatus, active);
            List<Map<String, Object>> outList = new ArrayList<>();
            if (!Objects.equals(shoppingBag, null)){
                List<ShoppingBagHasProduct> shoppingBagHasProducts = shoppingBagHasProductRepo.findAllByShoppingBagIdAndStatusId(shoppingBag, active);

                for (ShoppingBagHasProduct product: shoppingBagHasProducts){
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("id", product.getId());
                    obj.put("name", product.getProductId().getName());
                    obj.put("description", product.getProductId().getDescriptions());
                    obj.put("price", String.format("%.2f", product.getProductId().getPrice()));
                    obj.put("qty", product.getQuantity());

                    List<ProductHasImage> images = productHasImageRepo.findByProductIdAndStatusId(product.getProductId(), active);
                    if (!Objects.equals(images, null)){
                        obj.put("image", images.get(0).getImageId().getUrl());
                    }
                    outList.add(obj);
                }
            }
            return outList;
        }
        throw new EntryNotFoundException("invalid user");
    }

    @Override
    public String removeProductFromShoppingBag (String AUTHORIZATION_HEADER, Long id) {
        UserLoginCredential userLoginCredential = tokenChecker.userTokenChecker(AUTHORIZATION_HEADER);
        if (!Objects.equals(userLoginCredential, null)) {
            Status active = statusRepo.findById(StatusId.ACTIVE);
            ShoppingBagStatus shoppingBgaStatus = shoppingBagStatusRepo.findById(ShoppingBagStatusId.ACTIVE);

            ShoppingBag shoppingBag = shoppingBagRepo.findByUserIdAndShoppingBagStatusIdAndStatusId(userLoginCredential.getUserId(), shoppingBgaStatus, active);
            if (!Objects.equals(shoppingBag,null)){
                ShoppingBagHasProduct shoppingBagHasProduct = shoppingBagHasProductRepo.findByIdAndShoppingBagIdAndStatusId(id, shoppingBag, active);
                if (!Objects.equals(shoppingBagHasProduct,null)){
                    Status delete = statusRepo.findById(StatusId.DELETE);
                    Date date = new Date();
                    shoppingBagHasProduct.setStatusId(delete);
                    shoppingBagHasProduct.setUpdatedAt(date);
                    shoppingBagHasProductRepo.save(shoppingBagHasProduct);
                    return "success";
                }
            }
            throw new EntryNotFoundException("something went wrong");
        }
        throw new EntryNotFoundException("invalid user");
    }
}

package com.restaurant_service.controller;

import com.restaurant_service.DTO.RestaurantDocumentRequestDTO;
import com.restaurant_service.DTO.RestaurantDocumentResponseDTO;
import com.restaurant_service.service.RestaurantDocumentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/document")
@Slf4j
@Validated
public class RestaurantDocumentController
{

    @Autowired
    private RestaurantDocumentService restaurantDocumentService;


    //Upload document
    @PostMapping("/upload")
    public ResponseEntity<RestaurantDocumentResponseDTO> uploadDocument(@Valid @RequestBody RestaurantDocumentRequestDTO request)
    {
        RestaurantDocumentResponseDTO restaurantDocumentDetails = restaurantDocumentService.uploadDocument(request);
        return ResponseEntity.ok(restaurantDocumentDetails);
    }

    //Get restaurant documents
    @PostMapping("/documentList")
    public ResponseEntity<List<RestaurantDocumentResponseDTO>> getDocuments(@RequestParam Long restaurantId)
    {
        List<RestaurantDocumentResponseDTO> list = restaurantDocumentService.getDocuments(restaurantId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/downloadRestaurantDocumentListPdf")
    public void downloadRestaurantDocumentListPdf(HttpServletResponse response, @RequestParam Long restaurantId) throws Exception
    {
        restaurantDocumentService.generateRestaurantDocumentListPdf(response, restaurantId);
    }


}

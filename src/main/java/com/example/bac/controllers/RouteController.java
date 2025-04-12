package com.example.bac.controllers;

import com.example.bac.entities.OptimalRouteResponse;
import com.example.bac.services.RouteOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteOptimizationService routeOptimizationService;

    @GetMapping("/optimize/{camionId}")
    public ResponseEntity<OptimalRouteResponse> optimizeRoute(@PathVariable Long camionId) {
        OptimalRouteResponse response = routeOptimizationService.optimizeRoute(camionId);
        return ResponseEntity.ok(response);
    }
}
package team.youngcha.domain.car.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.car.dto.FindCarDetailsResponse;
import team.youngcha.domain.car.service.CarService;

@RequestMapping("/cars")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}/details")
    public ResponseEntity<SuccessResponse<FindCarDetailsResponse>> findCarDetails(@PathVariable Long id) {
        FindCarDetailsResponse carDetails = carService.findDetails(id);
        SuccessResponse<FindCarDetailsResponse> successResponse = new SuccessResponse<>(carDetails);
        return ResponseEntity.ok(successResponse);
    }

}

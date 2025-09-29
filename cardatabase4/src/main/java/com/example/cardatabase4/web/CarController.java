package com.example.cardatabase4.web;

import com.example.cardatabase4.domain.Car;
import com.example.cardatabase4.service.CarService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // 1. 모든 자동차 정보 조회 (GET /api/cars)
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.getCars();
    }

    // 2. 차량 한 대 추가 (POST /api/cars)
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.addCar(car);

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    // 3. 차량 한 대 조회 (GET /api/cars/{id})
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(car -> ResponseEntity.ok().body(car))      // 차량 정보가 있으면 200 ok를 반환할 것
                .orElse(ResponseEntity.notFound().build());         // 없으면 404 notFound를 return 시킬 것
        // return 전체의 반환 타입은 ResponseEntity가 됨
    }

    // 4. 차량 한 대 삭제
    // 특정 아이디를 받을때는 PathVariable. DeleteMapping에서 받은 id의 자료형이 뭔지 모르기 때문에 PathVariable 로 Long id 형으로 바꿔 줌
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        if(carService.deleteCar(id)) {
            return ResponseEntity.noContent().build();  // 삭제 성공 시 204 No Content return
        } else {
            return ResponseEntity.notFound().build();   // 대상이 없으면 404 Not Found
        }
    }

    // 5. 차량 한 대 수정
    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carService.updateCar(id, carDetails)
                .map(updateCar -> ResponseEntity.ok().body(updateCar))      // 수정 성공 시에는 200 OK
                .orElse(ResponseEntity.notFound().build());                     // 수정할 차량 id 검색 실패 시 404 Not Found
    }
}

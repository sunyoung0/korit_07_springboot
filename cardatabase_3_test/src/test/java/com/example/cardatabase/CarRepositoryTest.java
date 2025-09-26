package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("차량 저장 메서드 테스트")
    void saveCar() {
        // given - 제반 준비 사항
        // Car Entity 를 확인해봤을 때 field로 Owner를 요구하기 때문에
        // 얘부터 먼저 만들고 -> ownerRepository에 저장
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);

        // 그리고 Car 객체를 만들겁니다.
        Car car = new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner);

        // when - 테스트 실행
        // 저장이 됐는가를 확인하기 위한 부분
        carRepository.save(car);

        // then - 그 결과가 어떠할지
        assertThat(carRepository.findById(car.getId())).isPresent();        // 이건 그냥 결과값이 하나
        assertThat(carRepository.findById(car.getId()).get().getBrand()).isEqualTo("Ford");
    }

    @Test
    @DisplayName("차량 삭제 메서드 테스트")
    void deleteCar() {
        // given -> Owner 객체 생성 / 저장 -> Car 객체 생성 / 저장
        Owner owner = new Owner("test", "Kim");
        ownerRepository.save(owner);

        Car car = new Car("Hyundai", "Genesis", "Black", "ASDFGG", 2020, 8234500, owner);
        carRepository.save(car);

        // when - 삭제
        carRepository.deleteAll();
//        carRepository.deleteById(car.getId());

        // then - 삭제가 올바르게 되었는지 검증하는 assetThat() 구문
        assertThat(carRepository.count()).isEqualTo(0);

    }

    @Test
    @DisplayName("브랜드별 차량 조회 메서드 테스트")
    void findByBrandShouldReturnCar() {
        // given - car 데이터 저장
        Owner owner = new Owner("test", "Kim");
        ownerRepository.save(owner);

        carRepository.save(new Car("Hyundai", "Genesis", "Black", "ASDFGG", 2020, 8234500, owner));
        carRepository.save(new Car("Hyundai", "Genesis", "White", "ASDFGG", 2020, 8234500, owner));
        carRepository.save(new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner));
        carRepository.save(new Car("Ford", "Mustang", "White", "ABCDEF", 2021, 567890, owner));

        // when - 특정 브랜드 조회
        //carRepository.findByBrand("브랜드명")의 자료형은 list
        List<Car> brands = carRepository.findByBrand("Ford");

        // then - 조회가 가능한지 검증
        // list 내부의 element의 자료형이 Car 객체일테니까,
        // 그 객체의 getBrand()의 결과값이 우리가 findByBrand()의 argument로 쓴 값과 동일한지 체크할 수 있겠네요.
//        assertThat(brands.get(0).getBrand()).isEqualTo("Ford");

        // 혹은, 현재 Ford 차량을 두대 만들었으니까 size()의 결과값이 2겠죠.
         assertThat(brands.size()).isEqualTo(2);
    }



}

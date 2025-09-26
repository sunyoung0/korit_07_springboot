package com.example.cardatabase;

import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void saveOwner() {
        // H2 인메모리 상황이기 때문에 DB에 아무런 Owner가 없습니다.
        ownerRepository.save(new Owner("칠백", "김"));
        assertThat(ownerRepository.findByFirstName("칠백").isPresent()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트 : ")
    void deleteOwners() {
        // 그러면 일단 객체 생성을 하나 해서 repository에 저장할겁니다.
        ownerRepository.save(new Owner("팔백", "박"));
        // 삭제 method 호출
        ownerRepository.deleteAll();
        // 삭제가 완료되었는지를 체크하는 assertThat()문이 필수적으로 요구됩니다.
        assertThat(ownerRepository.count()).isEqualTo(0);
    }
}

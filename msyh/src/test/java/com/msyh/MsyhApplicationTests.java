package com.msyh;

import com.msyh.enttity.DTO.PersonDTO;
import com.msyh.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MsyhApplicationTests {


    @Autowired
    private PersonMapper personMapper;

    @Test
    void contextLoads() {
        List<PersonDTO> em=personMapper.selectList(null);
        em.forEach(System.out::println);
    }
}

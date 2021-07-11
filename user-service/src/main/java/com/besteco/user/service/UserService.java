package com.besteco.user.service;

import com.besteco.user.VO.Department;
import com.besteco.user.VO.ResponseTemplateVO;
import com.besteco.user.entity.User;
import com.besteco.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    @Autowired
    private RestTemplate restTemplate;

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId); //Burada useri bulduk diğer projeden departmanı cekmek lazım
        // . Bunun için ana app de restful service yazıcaz
        Department department =
                restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),
                Department.class);//url ve class type yazılır
        vo.setDepartment(department);
        vo.setUser(user);
        return vo;
    }
}

package com.example.demo.Repository;

import com.example.demo.Entity.CustomResponse;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//标明操作的类和其主键类型
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findUserByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);

    User findUserByCode(String code);
}

package com.codingapi.db.mysql.repository;

import com.codingapi.db.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


}

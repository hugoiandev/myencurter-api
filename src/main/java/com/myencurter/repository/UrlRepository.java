package com.myencurter.repository;

import com.myencurter.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, String> {

    List<Url> findAllByUserId(Long user_id);
}

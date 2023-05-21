package com.company.maven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.maven.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}

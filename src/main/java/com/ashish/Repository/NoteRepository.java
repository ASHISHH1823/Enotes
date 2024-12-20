package com.ashish.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ashish.Entity.Notes;

public interface NoteRepository extends JpaRepository<Notes, Integer>{

	Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);

}

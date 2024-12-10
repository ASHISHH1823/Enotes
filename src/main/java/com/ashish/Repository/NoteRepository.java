package com.ashish.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashish.Entity.Notes;

public interface NoteRepository extends JpaRepository<Notes, Integer>{

}

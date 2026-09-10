package com.documentapproval.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles") 
@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class Role {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String name; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

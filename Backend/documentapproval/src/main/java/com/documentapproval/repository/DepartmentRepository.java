package com.documentapproval.repository;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;
	import com.documentapproval.entity.Department;

	@Repository
	public interface DepartmentRepository extends JpaRepository<Department, Long> {
	    
}



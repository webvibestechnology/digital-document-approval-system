package com.documentapproval.repository;

	import java.util.List;
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;
	import com.documentapproval.entity.Notification;
	import com.documentapproval.entity.User;

	@Repository
	public interface NotificationRepository extends JpaRepository<Notification, Long> {
	    
	    List<Notification> findByUserAndIsReadFalse(User user);
	    
	    List<Notification> findByUser(User user);
	}



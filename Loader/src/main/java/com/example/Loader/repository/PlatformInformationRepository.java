package com.example.Loader.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Loader.model.PlatformInformation;
import com.example.Loader.model.Provider;


public interface PlatformInformationRepository extends JpaRepository<PlatformInformation, Integer> {
	void deleteByProvider(Provider provider);
	boolean existsByProviderAndFileName(Provider provider, String fileName);


}
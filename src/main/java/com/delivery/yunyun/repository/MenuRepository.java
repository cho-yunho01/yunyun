package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findAllByStore_StoreId(Long storeId);

    Menu findAllByStore_StoreIdAndName(Long storeId, String menuName);
}

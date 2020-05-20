package priv.patrick.springBucks.service;

import priv.patrick.springBucks.pojo.Coffee;

import java.util.List;

public interface CoffeeService {
    int save(Coffee coffee);

    Coffee findById(Long id);

    List<Coffee> findByName(String name);

    List<Coffee> selectAllCoffee();
}

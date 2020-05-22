package priv.patrick.springBucks.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import priv.patrick.springBucks.mapper.CoffeeMapper;
import priv.patrick.springBucks.pojo.Coffee;
import priv.patrick.springBucks.service.CoffeeService;

import java.util.List;

@Service("coffeeService")
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeServiceImpl implements CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;
//    @Autowired
//    private RedisTemplate<String,Coffee> redisTemplate;

    @Override
    public int save(Coffee coffee) {
        int save = coffeeMapper.save(coffee);
        log.info(coffee.toString());
        return save;
    }

    @Override
    public Coffee findById(Long id) {
//        HashOperations<String, String , Coffee> s = redisTemplate.opsForHash();
        return coffeeMapper.findById(id);
    }

    @Override
    public List<Coffee> findByName(String name) {
        PageHelper.startPage(2,3);
        //返回page类型
        List<Coffee> list = coffeeMapper.findByName(name);
        log.info(list.toString());
        PageHelper.clearPage();
        return list;
    }

    @Override
    @Cacheable
    public List<Coffee> selectAllCoffee() {
        return coffeeMapper.selectAllCoffee();
    }

    @CacheEvict
    public void reloadCoffee(){
    }
}

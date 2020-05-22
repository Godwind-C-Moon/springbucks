package priv.patrick.springBucks;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import priv.patrick.springBucks.pojo.Coffee;
import priv.patrick.springBucks.service.impl.CoffeeServiceImpl;

import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "priv.patrick.springBucks.mapper")
@Slf4j
//@EnableCaching(proxyTargetClass = true)
public class SpringBucksApplication {
//	@Autowired
//	private MongoTemplate mongoTemplate;
	@Autowired
	CoffeeServiceImpl coffeeService;
//	@Autowired
//	private JedisPoolConfig jedisPoolConfig;
//	@Autowired
//	private JedisPool jedisPool;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

//	@Bean
//	@ConfigurationProperties("redis")
//	public JedisPoolConfig jedisPoolConfig(){
//		return new JedisPoolConfig();
//	}
//
//	@Bean
//	public JedisPool jedisPool(@Value("${redis.host}") String host){
//		return new JedisPool(jedisPoolConfig,host);
//	}

	//cache

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		List<Coffee> coffees = coffeeService.selectAllCoffee();
//		for(int i=0 ;i<10 ;i++){
//			coffeeService.selectAllCoffee();
//		}
////		coffeeService.reloadCoffee();
//		Thread.sleep(6000);
//		log.info("reload");
//		coffeeService.selectAllCoffee();
//	}

	//mongodb

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		Coffee coffee= Coffee.builder().id(67L).name("capu").createTime(new Date()).updateTime(new Date()).build();
//		Coffee save = mongoTemplate.save(coffee);
//		List<Coffee> coffees = mongoTemplate.find(Query.query(Criteria.where("name").is("capu")), Coffee.class);
//		//example
//		coffees.forEach(r-> System.out.println(r));
//	}

	//jedis

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		log.info(jedisPoolConfig.toString());
//
//		try(Jedis jedis=jedisPool.getResource()) {
//			this.coffeeService.selectAllCoffee().forEach(r->{
//				jedis.hset("springbucks-menu",
//						r.getName(),
//						String.valueOf(r.getPrice()));
//			});
//
//			Map<String, String> map = jedis.hgetAll("springbucks-menu");
//			log.info("Menu:{}",map);
//		} catch (Exception e){
//			log.error(e.toString());
//		}
//	}
}

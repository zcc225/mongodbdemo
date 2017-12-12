package mongodbdemobyzcc;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation.ExecutableUpdate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zccpro.mongo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db.xml")
public class MongoTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	/**
	 * 插入操作
	 */
	@Test
	public void test1(){
		User user = new User();
		user.setAge(10);
		user.setName("test1");
		mongoTemplate.insert(user);
	}
	/**
	 * 查询
	 */
	@Test
	public void test2(){
		User user = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("name").is("test")), User.class);
		System.out.println(user.getAge()+"|"+user.getName());
	}
	
	/**
	 * 修改
	 */
	@Test
	public void test3(){
		UpdateResult updateFirst = mongoTemplate.updateFirst(  
                new Query(Criteria.where("name").is("test")),   
                Update.update("age", "12"), User.class);
		System.out.println(updateFirst.getModifiedCount());
	}
	
	/**
	 * 删除
	 */
	@Test
	public void test4(){
		DeleteResult remove = mongoTemplate.remove(new Query(Criteria.where("name").is("test1")), User.class);
		System.out.println(remove.getDeletedCount());
	}
	
}

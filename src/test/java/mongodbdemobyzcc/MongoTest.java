package mongodbdemobyzcc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.zccpro.mongo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db.xml")
public class MongoTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	GridFsTemplate gridTemplate;
	
	@Autowired
	GridFsOperations gridFsOperations ;
	
	@Test
	public void test44(){
	}
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
		WriteResult updateFirst = mongoTemplate.updateFirst(  
                new Query(Criteria.where("name").is("test")),   
                Update.update("age", "13"), User.class);
		System.out.println();
	}
	
	/**
	 * 删除
	 */
	@Test
	public void test4(){
		WriteResult remove = mongoTemplate.remove(new Query(Criteria.where("name").is("test1")), User.class);
		System.out.println();
	}
	
	
	// 存文件
    @Test
    /**
     * 上传文件
     * @author yinjihuan
     * @throws Exception
     */
    public  void uploadFile() throws Exception {
        File file = new File("C:\\Users\\jhs\\Desktop\\springbootwebdemo.jar");
        InputStream content = new FileInputStream(file);
        //存储文件的额外信息，比如用户ID,后面要查询某个用户的所有文件时就可以直接查询
        DBObject metadata = new BasicDBObject("userId", "1001");
         GridFSFile gridFSFile = gridTemplate.store(content, file.getName(), "image/png", metadata);
        String fileId = gridFSFile.getId().toString();
        System.out.println(fileId);
    }

    /**
     * 根据文件ID查询文件
     * @author yinjihuan
     * @param fileId
     * @return
     * @throws Exception
     */
    @Test
    public  void getFile() throws Exception {
    	GridFSDBFile gridFSDBFile = gridTemplate.findOne(Query.query(Criteria.where("_id").is("5a3093de9aed901cc0cac91b")));
    	InputStream inputStream = gridFSDBFile.getInputStream();
    	System.out.println("aa");
    	gridFSDBFile.writeTo(new File("C:\\Users\\jhs\\Desktop\\springbootwebdemo111.jar"));
    }
    // 所有文件
    @Test
    public void readFilesFromGridFs() {
        GridFsResource[] txtFiles = gridTemplate.getResources("*");
        for (GridFsResource txtFile : txtFiles) {
            System.out.println(txtFile.getFilename());
        }
    }
	
}

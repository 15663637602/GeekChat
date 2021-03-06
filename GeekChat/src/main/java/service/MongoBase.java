package service;

import java.util.List;
import java.util.Map;

import message.GetOneResult;
import po.User;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 10:48:43 PM
 */
public interface MongoBase<T> {  
    public void insert(T object,String collectionName);    
    public T findOne(Map<String,Object> params,String collectionName);    

    //查找所有  
//    public List<T> findAll(Map<String,Object> params,String collectionName);    
//    //修改  
//    public void update(Map<String,Object> params,String collectionName);   
//    //创建集合  
//    public void createCollection(String collectionName);  
//    //根据条件删除  
//    public void remove(Map<String,Object> params,String collectionName);  
    //更改收到结果状态

    public void returnresult(GetOneResult result);
      
}  

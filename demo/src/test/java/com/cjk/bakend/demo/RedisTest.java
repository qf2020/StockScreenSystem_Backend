package com.cjk.bakend.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.utils.RedisUtils;

import jakarta.annotation.Resource;

@SpringBootTest
 
public class RedisTest {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testStringRedisTemplate(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("age", "20");
        String age = stringStringValueOperations.get("age");
        System.out.println(age);
    }

//     @Test
//     void testOpsForValue(){
//         SimpleDateFormat format = new SimpleDateFormat();
//         User user = User.builder().name("andrew").password("123456")
//                 .email("123@qq.com")
//                 .phoneNumber("13719180727")
//                 .salt("123456789")
//                 .status(1)
//                 .createTime(format.format(DateUtil.now()))
//                 .build();

//         List<User> list = new ArrayList<>();
//         list.add(user);
// //        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

//         redisUtils.set("user", list);
//         Object user1 = redisUtils.get("user");
//         List<User> list1 = (List<User>) redisUtils.get("user");
//         System.out.println(user1);
//         System.out.println(list1);
//     }

//     @Test
//     void testOpsForValueMulti(){
//         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         User user1 = User.builder().name("andrew").password("123456")
//                 .email("123@qq.com")
//                 .phoneNumber("13719180727")
//                 .salt("123456789")
//                 .status(1)
//                 .createTime(format.format(DateUtil.now()))
//                 .build();

//         User user2 = User.builder().name("andrew1").password("123456")
//                 .email("123@qq.com")
//                 .phoneNumber("13719180727")
//                 .salt("123456789")
//                 .status(1)
//                 .createTime(format.format(DateUtil.now()))
//                 .build();

//         HashMap<String,User> hashMap = new HashMap<>();
//         hashMap.put("user1",user1);
//         hashMap.put("user2",user2);

// //        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

//         redisUtils.multiSet(hashMap);
//         ArrayList<String> keys = new ArrayList<>();
//         keys.add("user1");
//         keys.add("user2");
//         List<User> list1 = (List<User>) redisUtils.multiGet(keys);
//         list1.forEach(System.out::println);

//         redisUtils.del("user1");
//         List<User> list2 = (List<User>) redisUtils.multiGet(keys);
//         list2.forEach(System.out::println);
//     }

    /**
     * 操作hash
     */
    @Test
    public void testHash() {

        /**
         * 添加的第一条数据
         * redis的key
         * hash的key
         * hash的值
         */
        redisUtils.hset("userall", "name", "zhangsan");

        /**
         * 获取一条数据
         * 第一个参数redis的key
         * 第二个参数hash的key
         */
        Object object = redisUtils.hget("userall", "name");
        System.out.println(object);


        //添加多条数据
        //存多条数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", "14");
        map.put("sex", "nan");
        redisUtils.hmset("userall1", map);

        //获取多条数据
        //把key装入结合中
        ArrayList<String> keys = new ArrayList<>();
        keys.add("name");
        keys.add("age");
        keys.add("sex");
        //拿取多条数据
        List user = (List) redisUtils.hMultiGet("userall1", keys);
        user.forEach(System.out::println);

        //获取hash类型的所有数据
        Map<Object, Object> entries = redisUtils.hmget("userall1");
        entries.forEach((key, value) -> System.out.println(key + "-->" + value));

        //hash自带的删除
        redisUtils.hdel("userall", "name");

        //获取hash类型的所有数据
        Map<Object, Object> entries1 = redisUtils.hmget("userall");
        entries1.forEach((key, value) -> System.out.println(key + "-->" + value));

    }

    /**
     * 操作list
     */
    @Test
    public void testList() {
        redisUtils.del("students");
        //左添加
        redisUtils.lSet("students", "张三");
        //第一个参数redis的key 第二个参数被左添加的数据
        redisUtils.lPivotSet("students", "张三", "钱二");
        redisUtils.lSet("students", "李四");

        //右添加
        redisUtils.lSet("students", "牛牛");

        //获取数据
        List students = redisUtils.lGet("students", 0, -1);
        students.forEach(System.out::println);

        //获取总条数
        Long size = redisUtils.lGetListSize("students");
        System.out.println(size);

        //删除
        redisUtils.lRemove("students", 1, "张三");

        //左弹出 删除右边第一个
        redisUtils.lPop("students");

        //右弹出 删除右边第一个
        redisUtils.lPop("students");
    }

    /**
     * 操作set
     */
    @Test
    public void testSet() {
        //添加数据
        String[] arr = new String[]{"aaa", "bbb", "ccc","aaa", "ddd"};

        redisUtils.sSet("letters", "111", "33", "334");
        redisUtils.sSet("letters", arr);

        //获取数据
        Set letters = redisUtils.sGet("letters");
        letters.forEach(System.out::println);

        //删除
        redisUtils.setRemove("letters", "aaa", "111");
    }

    /**
     * 操作sorted set
     */

    @Test
    public void testSortedSet() {

        //添加数据
        ZSetOperations.TypedTuple<Object> objectTypedTuple = new DefaultTypedTuple<>("zhangsan", 7D);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<>("lisi", 2D);
        ZSetOperations.TypedTuple<Object> objectTypedTuple3 = new DefaultTypedTuple<>("wangwu", 9D);
        ZSetOperations.TypedTuple<Object> objectTypedTuple4 = new DefaultTypedTuple<>("zhaoliu", 3D);

        HashSet<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        tuples.add(objectTypedTuple);
        tuples.add(objectTypedTuple2);
        tuples.add(objectTypedTuple3);
        tuples.add(objectTypedTuple4);
        redisUtils.zSSet("score", tuples);

        //zSetOperations.add("name","niuniu",7D);

        //获取数据
        Set set = redisUtils.zSGet("score", 0, -1);
        set.forEach(System.out::println);

        //总条数
        Long size = redisUtils.zSGetSetSize("score");
        System.out.println(size);

        //删除数据
        redisUtils.zSetRemove("score", "zhangsan", "lisi");
    }

    /**
     * 获取所有的key
     */
    @Test
    public void testAllKeys() {
        Set keys = redisUtils.getPattern("*");
        keys.forEach(System.out::println);
    }

    /**
     * 失效时间
     */
    @Test
    public void testExpire() {

        //方法一 添加key的时候设置失效时间
        redisUtils.set("code", "test", 30);

        //方法二 给已经存在的key设置失效时间
        redisUtils.set("address", "添加时未设置失效时间");
        redisUtils.expire("address", 30);

        //查看失效时间
        Long code = redisUtils.getExpire("code");
        System.out.println(code);

        Long time = redisUtils.getExpire("address");
        System.out.println(time);
    }

}

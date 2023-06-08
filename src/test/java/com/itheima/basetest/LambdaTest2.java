package com.itheima.basetest;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LambdaTest2 {
    public static void main(String[] args) {
        Integer[] integers ={18,42,33,999,32,42};
      /*  Arrays.sort(integers, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2; //升序
            }
        });*/

        Arrays.sort(integers, (Integer o1,Integer o2) -> {
             return o1 - o2;
            }
        );
        System.out.println(Arrays.toString(integers));

        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1,"张三","张无忌","李元霸","钱立人","程为民");
        list1.stream()
                .filter(s->s.startsWith("张"))
                .forEach(s -> System.out.println(s));
        list1.stream().skip(3).forEach(s -> System.out.println(s));
        System.out.println("================");
        list1.stream().limit(4).forEach(s -> System.out.println(s));
        System.out.println("================");

        Map<String,Integer> map = new HashMap<>();
        map.put("zhangsan",1);
        map.put("lisi",2);
        map.put("wangwu",3);
        map.put("zhaoliu",4);
        map.put("wangqi",5);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        entrySet.stream().forEach(s-> System.out.println(s));

        String[] str ={"张天一","李世明","朱元璋","嬴政","李绮华"};
        Arrays.stream(str).forEach(s-> System.out.println(s));

        Stream.of("1",2,"张三").forEach(s-> System.out.println(s));

    }

}

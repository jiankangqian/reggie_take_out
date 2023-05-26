package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，如果分类绑定了菜品或者套餐，则无法删除
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        //select * from dish where category_id = ids;
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId,ids);
        int count1 = dishService.count(dishQueryWrapper);

        if(count1 > 0){
            //抛出业务异常
            throw new CustomException("该分类下已经关联菜品，无法删除");

        }
        LambdaQueryWrapper<Setmeal> setmealQueryWraaper = new LambdaQueryWrapper<>();
        setmealQueryWraaper.eq(Setmeal::getCategoryId,ids);
        int count2 = setmealService.count();
        if(count2 > 0){
            throw new CustomException("该分类下已经关联套餐，无法删除");
        }
        //可以正常删除
        super.removeById(ids);
    }
}

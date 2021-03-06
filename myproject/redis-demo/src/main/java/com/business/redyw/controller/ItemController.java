package com.business.redyw.controller;


import com.business.redyw.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private static final String prefix = "/cache/pass";
    @Autowired
    private ItemService iItemService;

    @RequestMapping(value = prefix + "/info/{itemcode}")
    public Map<String, Object> getItem(@PathVariable String itemcode) {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "成功");
        try {
            resMap.put("data", iItemService.getItemInfo(itemcode));
        } catch (IOException e) {
            resMap.put("code", -1);
            resMap.put("msg", "失败" + e.getMessage());
        }
        return resMap;
    }
}


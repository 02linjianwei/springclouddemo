package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {
    /**
     * 发红包算法
     * @param totalAmout 红包总金额
     * @param totalPeopleNum 总人数
     * @return
     */
    public static List<Integer> divideRedPackage(Integer totalAmout,Integer totalPeopleNum) {
        List<Integer> amoutList = new ArrayList<>();
        if (totalAmout > 0 && totalPeopleNum > 0) {
            Integer restAmount = totalAmout;
            Integer restPeopleNum = totalPeopleNum;
            Random random = new Random();
            for (int i = 0; i < totalPeopleNum - 1; i++) {
                int amout = random.nextInt(restAmount/restPeopleNum*2-1) +1;
                restAmount -=amout;
                restPeopleNum--;
                amoutList.add(amout);
            }
            amoutList.add(restAmount);
        }
        return amoutList;
    }
}

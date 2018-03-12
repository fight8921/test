package com;

import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {

        // 根据时差返回想应范围的值one

        // 222222

        // 111111

        test();
    }
    /**
     * @Description:根据时差返回想应范围的值
     * @author gaoqiyi date: 2017年12月4日 下午3:09:52
     */
    public static void test()
    {
        Integer[] minutesLmt = { 30, 10, 60, -1, 1440 };
        Arrays.sort(minutesLmt);
        for (int i = 0; i < minutesLmt.length; i++)
        {
            System.out.print(minutesLmt[i] + ",");
        }
        System.out.println("\n/////////////");
        long s = 40;// 时间差
        long time = -1;// 返回时间差
        for (int i = 0; i < minutesLmt.length; i++)
        {

            int index = 0;
            if (i == minutesLmt.length - 1)
            {
                index = minutesLmt.length - 1;
            }
            else
            {
                index = i + 1;
            }
            System.out.println(i + ": " + minutesLmt[i] + " < " + s + " <= " + minutesLmt[index]);
            if (s > minutesLmt[i] && s <= minutesLmt[index])
            {
                time = minutesLmt[index];
            }
        }
        System.out.println("*******");
        System.out.println(time);
    }
}

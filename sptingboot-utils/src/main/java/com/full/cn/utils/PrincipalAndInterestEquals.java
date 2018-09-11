package com.full.cn.utils;


/**
 * @author moafmoar
 * @date 2018-09-11 
 * @param 
 * @return
 */
public class PrincipalAndInterestEquals {
    public static void main(String[] args) {
        double invest = 10000;     //贷款本金
        double yearRate = 0.155;   //年利率
        double monthRate = yearRate/12;   //月利率

//      int year = 15;     //还款年数
        int month = 6;  //还款月数

        System.out.println("本金-->"+invest+"   年利率--->"+yearRate*100+"%"+"  期限--->"+month+"个月");
        System.out.println("--------------------------------------------");

        // 每月本息金额  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
        double monthIncome = (invest* monthRate * Math.pow(1+monthRate,month))/(Math.pow(1+monthRate,month)-1);
        System.out.println("每月本息金额 : " + monthIncome);
        System.out.println("---------------------------------------------------");

        // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
        double monthCapital = 0;
        for(int i=1;i<month+1;i++){
            monthCapital = (invest* monthRate * (Math.pow((1+monthRate),i-1)))/(Math.pow(1+monthRate,month)-1);
            System.out.println("第" + i + "月本金： " + monthCapital);
        }
        System.out.println("---------------------------------------------------");


        // 每月利息  = 剩余本金 x 贷款月利率
        double monthInterest = 0;
        double capital = invest;
        double tmpCapital = 0;
        double totalInterest = 0;
        for(int i=1;i<month+1;i++){
            capital = capital - tmpCapital;
            monthInterest = capital * monthRate;
            tmpCapital = (invest* monthRate * (Math.pow((1+monthRate),i-1)))/(Math.pow(1+monthRate,month)-1);
            System.out.println("第" + i + "月利息： " + monthInterest);
            totalInterest = totalInterest + monthInterest;
        }

        System.out.println("-------------------------------------------------");
        System.out.println("利息：--->"+totalInterest);

        System.out.println("-------------------------------------------------");
        System.out.println("年利率：--->"+totalInterest/month*12/invest*100+"%");


    }

    /**
     * 获取每月本息金额
     * 计算方式
     * 每月本息金额  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
     * @param invest  本金
     * @param yearRate 年利率
     * @param month   还款月
     * @return  每月本息金额
     */
    public double getMonthIncome(double invest, double yearRate,int month){
        double monthRate = yearRate/12;   //月利率
        double monthIncome = (invest* monthRate * Math.pow(1+monthRate,month))/(Math.pow(1+monthRate,month)-1);
        return monthIncome;
    }
}

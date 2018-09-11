package com.full.cn.utils;


import love.cq.domain.Forest;
import love.cq.domain.Value;
import love.cq.library.Library;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.List;

/**
 * 中文分词词典
 *  @author  moafmoar
 * @createData 2017-12-01
 *
 */
public class Ansj {


    public static void main(String[] args) throws Exception {

        // 构造一个用户词典
        Forest forest = Library.makeForest("E:\\workspalce\\gitosc\\springboot-learning-example\\sptingboot-utils\\src\\main\\resources\\library\\userLibrary.dic");
        forest = new Forest();

        // 增加新词,中间按照'\t'隔开
        UserDefineLibrary.insertWord("ansj中文分词", "userDefine", 1000);
        List<Term> terms = ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("增加新词例子:" + terms);

        System.out.println("BASE");
        System.out.println(BaseAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!"));
        System.out.println("TO");
        System.out.println(ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!"));
        System.out.println("DIC");
       // System.out.println(DicAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!",forest));
        System.out.println("INDEX");
        System.out.println(IndexAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!"));
        System.out.println("NLP");
        System.out.println(NlpAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!"));



        // 删除词语,只能删除.用户自定义的词典.
        UserDefineLibrary.removeWord("ansj中文分词");
        terms = ToAnalysis.parse("我觉得ansj中文分词是一个不错的系统!我是王婆!");
        System.out.println("删除用户自定义词典例子:" + terms);

        // 歧义词
       /* Value value = new Value("济南下车", "济南", "n", "下车", "v");
        System.out.println(ToAnalysis.parse("我经济南下车到广州.中国经济南下势头迅猛!"));
        Library.insertWord(UserDefineLibrary.ambiguityForest, value);
        System.out.println(ToAnalysis.parse("我经济南下车到广州.中国经济南下势头迅猛!"));*/

        // 多用户词典
        String str ="神探夏洛克这部电影作者.是一个dota迷";
        System.out.println(ToAnalysis.parse(str));
        // 两个词汇 神探夏洛克 douta迷
        Forest dic1 = new Forest();
        Library.insertWord(dic1, new Value("神探夏洛克", "define", "1000"));
        Forest dic2 = new Forest();
        Library.insertWord(dic2, new Value("dota迷", "define", "1000"));
        System.out.println(ToAnalysis.parse(str, dic1, dic2));
    }
}

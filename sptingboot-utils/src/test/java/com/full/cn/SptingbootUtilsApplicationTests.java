package com.full.cn;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class SptingbootUtilsApplicationTests {

	@Test
	public void contextLoads() {
		ActiveXComponent xl = new ActiveXComponent("PowerPoint.Application");
		Dispatch xlo = (Dispatch)(xl.getObject());
		try {
			System.out.println("version="+xl.getProperty("Version"));
			System.out.println("version="+Dispatch.get(xlo, "Version"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			xl.invoke("Quit", new Variant[] {});
		}
	}

	/**
	 * 从数组中获取重复的数据被统计重复出现的次数
	 */
	@Test
	public void getNum(){

		String [] arry = {"1","2","3","1","1","2","5","2","6","6","4","4","3","3","4"};
		Map<String,Integer> map = new HashMap<String, Integer>();
		for(int i =0 ;i<arry.length;i++){
			if(null!= map.get(arry[i])){
				map.put(arry[i], map.get(arry[i])+1); //value+1
			}else{
				map.put(arry[i],1);
			}
		}

		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String  key  =  entry.getKey().toString();
			int  value  =  Integer.parseInt(entry.getValue().toString());
			System.out.println("key is :"+key+"---value :"+value);
		}
	}

}

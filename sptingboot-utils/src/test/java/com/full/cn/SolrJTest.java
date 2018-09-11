package com.full.cn;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by full on 2017/7/5.
 */
public class SolrJTest {
    private static final String DEFAULT_URL = "http://10.68.68.120:8080/solr/collection1";
    //private HttpSolrServer server;
    private SolrClient server;

    @Before
    public void init() {
        server = new HttpSolrClient(DEFAULT_URL);

        //server = new HttpSolrServer(DEFAULT_URL);

        //server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
        //server.setConnectionTimeout(5000); // 5 seconds to establish TCP

        // Setting the XML response parser is only required for cross
        // version compatibility and only when one side is 1.4.1 or
        // earlier and the other side is 3.1 or later.
        // server.setParser(new XMLResponseParser()); // binary parser is used
        // by default

        // The following settings are provided here for completeness.
        // They will not normally be required, and should only be used
        // after consulting javadocs to know whether they are truly required.
       // server.setSoTimeout(1000); // socket read timeout
       // server.setDefaultMaxConnectionsPerHost(100);
       // server.setMaxTotalConnections(100);
       // server.setFollowRedirects(false); // defaults to false

        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
       // server.setAllowCompression(true);

        // SolrJ lets you upload content in XML and Binary format.
        // The default is set to be XML.
        // Use the following to upload using Binary format.
        // This is the same format which SolrJ uses to fetch results, and can
        // greatly improve performance as it reduces XML marshalling overhead.
        // Note -- be sure you have also enabled the
        // "BinaryUpdateRequestHandler" in your solrconfig.xml for example like:
        // <requestHandler name="/update/javabin"
        // class="solr.BinaryUpdateRequestHandler" />
        // server.setRequestWriter(new BinaryRequestWriter());
    }

    @After
    public void destory() {
        server = null;
        System.runFinalization();
        System.gc();
    }

    /**
     * 获取查询结果，直接打印输出
     * @param query
     */
    public void query(String query) {
        SolrQuery params = new SolrQuery(query);
        params.set("rows", 5);
        QueryResponse response = null;
        try {
            response = server.query(params);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        SolrDocumentList list = response.getResults();
        System.out.println("总计：" + list.getNumFound() + "条，本批次:" + list.size() + "条");
        for (int i = 0; i < list.size(); i++) {
            SolrDocument doc = list.get(i);
            System.out.println(doc.get("webTitle"));
        }
    }

    @Test
    public void addDoc() {
        // 创建doc文档
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("objectId", 0);
        doc.addField("webTitle", "测试标题");
        doc.addField("webTime", new java.util.Date());
        doc.addField("webContent", "这是一条测试内容");
        try {
            // 添加一个doc文档
            UpdateResponse response = server.add(doc);
            // commit后才保存到索引库
            server.commit();

            // 输出统计信息
            System.out.println("Query Time：" + response.getQTime());
            System.out.println("Elapsed Time：" + response.getElapsedTime());
            System.out.println("Status：" + response.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------");

        query("测试");

        System.out.println("--------------------------");
    }

    @Test
    public void addDocs() {
        String[] title = new String[] { "IK Analyzer介绍", "观前街介绍", "服务业情况",
                "人大动态", "高技能" };
        String[] content = new String[] {
                "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。",
                "观前街实际上就是玄妙观前面的那条街，卫道观前当然也有一个观，那就是卫道",
                "服务业集聚区加快建设。全市完成全社会固定资产投资5265亿元，比上年增长17%",
                "为了提高加快立法质量和实效，市人大常委会还首次开展了立法后评估工作，对《苏州市公路条例》",
                "继续位居动态全国地级市首位。2012年新增高技能人才7.6万人，其中新培养技师、高级技师4600人" };

        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 0; i < title.length; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("objectId", (i + 1));
            doc.addField("webTitle", title[i]);
            doc.addField("webContent", content[i]);
            doc.addField("webTime", new java.util.Date());

            docs.add(doc);
        }

        try {
            UpdateResponse response = server.add(docs);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        query("webTitle:介绍");

        System.out.println("--------------------------");
    }

    @Test
    public void querySimple() {
        // 这是一个简单查询

        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "加快 开展");
        params.set("q.op", "AND");
        params.set("start", 0);
        params.set("rows", 3);
        params.set("fl", "*,score");
        try {
            QueryResponse response = server.query(params);
            SolrDocumentList list = response.getResults();
            System.out.println("总计：" + list.getNumFound() + "条，本批次:" + list.size() + "条");
            for (int i = 0; i < list.size(); i++) {
                SolrDocument doc = list.get(i);
                System.out.println(doc.get("webTitle"));
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------");
    }

    @Test
    public void queryCase() {
        // 这是一个稍复杂点的查询

        SolrQuery params = new SolrQuery("苏州");
//        params.set("q.op", "OR");
        params.set("start", 0);
        params.set("rows", 4);
//        params.set("fl", "*,score");
//        params.setIncludeScore(true);
//        params.set("sort", "webTime desc");

        params.setHighlight(true); // 开启高亮组件
        params.addHighlightField("webTitle");// 高亮字段
        params.addHighlightField("webContent");// 高亮字段
//        params.set("hl.useFastVectorHighlighter", "true");
        params.set("hl.fragsize", "200");
//        params.setHighlightSimplePre("<SPAN class=\"red\">");// 高亮关键字前缀；
//        params.setHighlightSimplePost("</SPAN>");// 高亮关键字后缀
        params.setHighlightSnippets(2); //结果分片数，默认为1

        try {
            QueryResponse response = server.query(params);

            // 输出查询结果集
            SolrDocumentList list = response.getResults();
            System.out.println("总计：" + list.getNumFound() + "条，本批次:" + list.size() + "条");
            for (int i = 0; i < list.size(); i++) {
                SolrDocument doc = list.get(i);
                System.out.println("webTitle:"+doc.get("webTitle"));
            }

            // 第一种：常用遍历Map方法；
            Map<String, Map<String, List<String>>> map = response.getHighlighting();
            Iterator<String> iterator = map.keySet().iterator();
            while(iterator.hasNext()) {
                String keyname = (String) iterator.next();
                Map<String, List<String>> keyvalue = map.get(keyname);
                System.out.println("objectId：" + keyname);

                // 第二种：JDK1.5之后的新遍历Map方法。
                for (Map.Entry<String, List<String>> entry : keyvalue.entrySet()) {
                    String subkeyname = entry.getKey().toString();
                    List<String> subkeyvalue = entry.getValue();

                    System.out.print(subkeyname + ":\n");
                    for(String str: subkeyvalue) {
                        System.out.print(str);
                    }
                    System.out.println();
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------");
    }
}

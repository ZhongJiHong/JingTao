package com.jt.web.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestLucene {
	/*
	@Test
	public void create() throws IOException {

		// 指定索引存储的目录路径
		Directory directory = FSDirectory.open(new File("./index"));

		// 创建英语标准分词器
		// Analyzer analyzer = new StandardAnalyzer();
		// Analyzer analyzer = new ChineseAnalyzer();
		Analyzer analyzer = new IKAnalyzer();

		// 指定Lucene版本以及分词组件
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		IndexWriter writer = new IndexWriter(directory, config);

		Document doc1 = new Document();
		doc1.add(new TextField("title", "Thinking in java", Store.YES));
		doc1.add(new TextField("desc", "学习Java的入门书籍", Store.YES));

		Document doc2 = new Document();
		doc2.add(new TextField("title", "Thinking in C++", Store.YES));
		doc2.add(new TextField("desc", "学习C++的必备教材", Store.YES));

		writer.addDocument(doc1);
		writer.addDocument(doc2);

		writer.close();
	}
	*/
	
	/*
	@Test
	public void search() throws IOException {

		Directory directory = FSDirectory.open(new File("./index"));

		IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));

		TermQuery query = new TermQuery(new Term("desc", "教材"));

		TopDocs docs = searcher.search(query, 20);

		for (ScoreDoc sd : docs.scoreDocs) {

			float score = sd.score;
			Document doc = searcher.doc(sd.doc);

			System.out.println("文档的分:" + score + "标题:" + doc.get("title") + "描述:" + doc.get("desc"));
		}
		directory.close();
	}
	*/
}

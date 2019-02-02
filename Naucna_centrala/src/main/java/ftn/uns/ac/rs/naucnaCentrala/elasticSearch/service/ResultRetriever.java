package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service;
/*
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.DocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.RequiredHighlight;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ResultData;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;

@Service
public class ResultRetriever {

	@Autowired
	private IUPaperRepository repository;

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	public ResultRetriever(){
	}
	
	public List<ResultData> getResultsWithHighlight(org.elasticsearch.index.query.QueryBuilder query) {
		
		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(query)
				.withHighlightFields(
						new HighlightBuilder.Field("text"), 
						new HighlightBuilder.Field("title"), 
						new HighlightBuilder.Field("author"),
						new HighlightBuilder.Field("keywords"), 
						new HighlightBuilder.Field("languageName"))
				.build();
		
		Page<PaperIndexUnit> booksEntities = elasticsearchTemplate.queryForPage(sq, PaperIndexUnit.class, new SearchResultMapper() {

			@SuppressWarnings("unchecked")
			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse arg0, Class<T> arg1, Pageable arg2) {
				List<PaperIndexUnit> books = new ArrayList<PaperIndexUnit>();
				for(SearchHit searchHit : arg0.getHits()){
					if(arg0.getHits().getHits().length <= 0){
						return null;
					}

					PaperIndexUnit resultData = new PaperIndexUnit();
					resultData.setFilename((String) searchHit.getSource().get("filename"));
					resultData.setTitle((String) searchHit.getSource().get("title"));
					resultData.setAuthor((String) searchHit.getSource().get("author"));
					resultData.setKeywords((String) searchHit.getSource().get("keywords"));
					resultData.setPublicationYear((Integer) searchHit.getSource().get("publicationYear"));
					resultData.setMime((String) searchHit.getSource().get("mime"));
					resultData.setLanguageName((String) searchHit.getSource().get("languageName"));
					resultData.setCategoryName((String) searchHit.getSource().get("categoryName"));
					resultData.setText((String) searchHit.getSource().get("text"));
					
					
					if(searchHit.getHighlightFields() != null){
						StringBuilder highlights = new StringBuilder("...");
						
						if(searchHit.getHighlightFields().get("text") != null){
							Text [] text = searchHit.getHighlightFields().get("text").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("author") != null){
							Text [] text = searchHit.getHighlightFields().get("author").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("title") != null){
							Text [] text = searchHit.getHighlightFields().get("title").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("keywords") != null){
							Text [] text = searchHit.getHighlightFields().get("keywords").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						resultData.setHightlight(highlights.toString());
					}
					books.add(resultData);

				}

				if(books.size() > 0){
					return new AggregatedPageImpl<T>((List<T>) books);
				}

				return null;
			}
		});
		List<PaperIndexUnit> response = new ArrayList<>();
		if(booksEntities != null){
			
			for (PaperIndexUnit index : booksEntities) {
				ResultData resultData = new ResultData();
				resultData.setFilename(index.getFilename());
				resultData.setTitle(index.getTitle());
				resultData.setAuthor(index.getAuthor());
				resultData.setKeywords(index.getKeywords());
				resultData.setMime("PDF");
				resultData.setPublicationYear(index.getPublicationYear());
				resultData.setLanguageName(index.getLanguageName());
				resultData.setCategoryName(index.getCategoryName());
				resultData.setHighlight(index.getHightlight());
				resultData.setOriginalText(index.getOriginalText());
				resultData.setText(index.getText());
				response.add(resultData);
			}
		}

		return response;	
	}
	
	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}

		List<ResultData> results = new ArrayList<ResultData>();

		for (PaperIndexUnit indexUnit : repository.search(query)) {
			results.add(new ResultData(indexUnit.getFilename(), indexUnit.getTitle(), indexUnit.getAuthor(), indexUnit.getKeywords(), indexUnit.getPublicationYear(), indexUnit.getMime(), indexUnit.getLanguageName(), indexUnit.getCategoryName(), indexUnit.getHightlight(), indexUnit.getOriginalText(), indexUnit.getText()));
		}

		return results;
	}

	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PdfDocumentHandler();
		}else{
			return null;
		}
	}
}
*/
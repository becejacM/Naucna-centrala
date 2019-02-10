package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.elasticsearch.search.highlight.HighlightBuilder;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.DocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.AutorIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.RequiredHighlight;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ResultData;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.repository.IUPaperRepository;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchHitDTO;

@Service
public class ResultRetriever {

	@Autowired
	private IUPaperRepository repository;

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	public ResultRetriever(){
	}
	
	private String extractHighlightedText(HighlightField highlightField) {
        return Arrays.stream(highlightField.getFragments())
                .map(t -> t.string() + "...")
                .collect(Collectors.joining());
    }
	
	public List<SearchHitDTO> getResultsWithHighlight(org.elasticsearch.index.query.QueryBuilder query) {
		
		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(query)
				.withHighlightFields(new Field("text"))
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
					/*resultData.setFilename((String) searchHit.getSource().get("filename"));
					resultData.setNaslovRada((String) searchHit.getSource().get("naslovRada"));
					resultData.setKeywords((String) searchHit.getSource().get("keywords"));
					resultData.setNazivCasopisa((String) searchHit.getSource().get("nazivCasopisa"));
					resultData.setText((String) searchHit.getSource().get("text"));
					resultData.setAutori((Collection<AutorIndexUnit>) searchHit.getSource().get("autori"));
					resultData.setApstrakt((String) searchHit.getSource().get("apstrakt"));
					resultData.setDostupnost((String) searchHit.getSource().get("dostupnost"));
					resultData.setOblast((String) searchHit.getSource().get("oblast"));*/
					if(searchHit.getHighlightFields() != null){
						StringBuilder highlights = new StringBuilder("...");
						
						if(searchHit.getHighlightFields().get("text") != null){
							Text [] text = searchHit.getHighlightFields().get("text").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("naslovRada") != null){
							Text [] text = searchHit.getHighlightFields().get("naslovRada").fragments();
							for (Text t : text) {
								highlights.append(t.toString());
								highlights.append("...");
							}	
						}
						if(searchHit.getHighlightFields().get("nazivCasopisa") != null){
							Text [] text = searchHit.getHighlightFields().get("nazivCasopisa").fragments();
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
		List<SearchHitDTO> response = new ArrayList<>();
		if(booksEntities != null){
			
			for (PaperIndexUnit index : booksEntities) {
				SearchHitDTO resultData = new SearchHitDTO(index);
				
				response.add(resultData);
			}
		}
		/*List<SearchHitDTO> result = new ArrayList<>();

		Iterator<SearchHit> iterator = ((SearchResponse) sq).getHits().iterator();
        Gson gson = new GsonBuilder().create();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            String searchSource = searchHit.getSourceAsString();
            if (searchSource != null) {
                SearchHitDTO searchHitDTO = gson.fromJson(searchSource, SearchHitDTO.class);
                searchHit.getHighlightFields().values().forEach(field -> {
                    searchHitDTO.setText(extractHighlightedText(field));
                });
                result.add(searchHitDTO);
            }
        }
        return result;*/
		return response;	
	}
	
	public List<SearchHitDTO> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}

		List<SearchHitDTO> results = new ArrayList<SearchHitDTO>();

		for (PaperIndexUnit indexUnit : repository.search(query)) {
			results.add(new SearchHitDTO(indexUnit.getFilename(),indexUnit.getNazivCasopisa(), indexUnit.getNaslovRada(), indexUnit.getApstrakt(), indexUnit.getKeywords(), indexUnit.getAutori(), indexUnit.getOblast(), indexUnit.getText(), indexUnit.getHightlight(), indexUnit.getDostupnost()));
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

package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.AdvancedQuery;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.SearchType;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.SimpleQuery;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service.ESPaperService;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service.QueryBuilder;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.PaperDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.SearchHitDTO;

@RestController
@RequestMapping("/search")
public class ElasticSearchController {

	@Autowired
	ESPaperService esPaperService;
	

    @PostMapping("")
    public ResponseEntity save(@RequestBody PaperDTO paperDTO) {
        Paper paper = new Paper(paperDTO);
        esPaperService.save(paper);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity update(@RequestBody PaperDTO paperDTO) {
        Paper paper = new Paper(paperDTO);
        esPaperService.update(paper);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity uploadEBook(@RequestParam("file")MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();

        Paper paper = null;
        File storedFile = esPaperService.storeFile(multipartFile);
        paper = esPaperService.getMetadata(storedFile);
        return new ResponseEntity<>(paper, HttpStatus.OK);
    }
	@GetMapping("")
    public ResponseEntity getAll() {
        List<Paper> articleList  = esPaperService.findAll();
        List<PaperDTO> articleDTOList = new ArrayList<PaperDTO>();
        for (Paper a : articleList) {
        	System.out.println(a.getFilename());
        	PaperDTO adto = new PaperDTO(a);
			articleDTOList.add(adto);
        	System.out.println(adto.getName());

		}

        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }
	
	@PostMapping("search")
    public ResponseEntity search(@RequestBody SearchDTO searchDTO) {
        List<SearchHitDTO> eBookDTOList = esPaperService.search2(searchDTO);
        return new ResponseEntity<>(eBookDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}/download")
    public ResponseEntity downloadFile(@PathVariable long id, HttpServletRequest request) {
        Optional<Resource> resource = esPaperService.loadBookAsResource(id);
        String contentType="application/pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.get().getFilename() + "\"")
                .body(resource.get());
    }

    @DeleteMapping("{filename:.+}")
    public ResponseEntity deleteFile(@PathVariable String filename) throws IOException {
    	esPaperService.deleteFileFromStorage(filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	@PostMapping(value="/search/term", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {	
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}

	@PostMapping(value="/search/fuzzy", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}

	@PostMapping(value="/search/prefix", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}

	@PostMapping(value="/search/range", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}

	@PostMapping(value="/search/phrase", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}

	@PostMapping(value="/search/boolean", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
		
		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());

		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
			builder.must(query1);
			builder.must(query2);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
			builder.should(query1);
			builder.should(query2);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
			builder.must(query1);
			builder.mustNot(query2);
		}
		System.out.println(query1);
		System.out.println(query2);

		List<SearchHitDTO> results = esPaperService.search(builder);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);

	}

	@PostMapping(value="/search/queryParser", consumes="application/json")
	public ResponseEntity<List<SearchHitDTO>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {		
		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<SearchHitDTO> results = esPaperService.search(query);
		return new ResponseEntity<List<SearchHitDTO>>(results, HttpStatus.OK);
	}


}

package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.SearchType;

public class QueryBuilder {

	private static int maxEdits = 1;

	public static int getMaxEdits() {
		return maxEdits;
	}

	public static void setMaxEdits(int maxEdits) {
		QueryBuilder.maxEdits = maxEdits;
	}

	public static org.elasticsearch.index.query.QueryBuilder buildQuery(SearchType queryType, String field,
			String value) throws IllegalArgumentException, ParseException {
		String errorMessage = "";
		if (field == null || field.equals("")) {
			errorMessage += "Field not specified";
		}
		if (value == null) {
			if (!errorMessage.equals(""))
				errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if (!errorMessage.equals("")) {
			throw new IllegalArgumentException(errorMessage);
		}

		org.elasticsearch.index.query.QueryBuilder retVal = null;
		if (queryType.equals(SearchType.regular)) {
			if (field.equals("naucnaOblast.ime")) {
				System.out.println("evo meeee");
				retVal = QueryBuilders.nestedQuery("naucnaOblast", boolQuery().should(matchQuery("naucnaOblast.ime", value)),
						ScoreMode.Avg);
			}
			else if (field.equals("imeAutora")) {
				retVal = QueryBuilders.nestedQuery("autori", boolQuery().should(matchQuery("autori.imeAutora", value)),
						ScoreMode.Avg);
			} else if (field.equals("prezimeAutora")) {
				retVal = QueryBuilders.nestedQuery("autori",
						boolQuery().should(matchQuery("autori.prezimeAutora", value)), ScoreMode.Avg);
			} else {
				retVal = QueryBuilders.termQuery(field, value);
			}
		} else if (queryType.equals(SearchType.fuzzy)) {
			retVal = QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.fromEdits(maxEdits));
		} else if (queryType.equals(SearchType.prefix)) {
			retVal = QueryBuilders.prefixQuery(field, value);
		} else if (queryType.equals(SearchType.range)) {
			String[] values = value.split(" ");
			retVal = QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
		} else {
			if (field.equals("imeAutora")) {
				retVal = QueryBuilders.nestedQuery("autori", boolQuery().should(matchPhraseQuery("autori.imeAutora", value)),
						ScoreMode.Avg);
			} else if (field.equals("prezimeAutora")) {
				retVal = QueryBuilders.nestedQuery("autori",
						boolQuery().should(matchPhraseQuery("autori.prezimeAutora", value)), ScoreMode.Avg);
			} else {
				retVal = QueryBuilders.matchPhraseQuery(field, value);
			}
		}

		return retVal;
	}

}

package com.aushev.autoriasearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ads {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        private SearchResult search_result;

        public SearchResult getSearch_result() {
            return search_result;
        }

        public void setSearch_result(SearchResult search_result) {
            this.search_result = search_result;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchResult {

        private List<String> ids;
        private int count;

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

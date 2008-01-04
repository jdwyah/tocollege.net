package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.dto.SearchResult;

public interface SearchService {

    List<SearchResult> search(String searchString);

    List<String> searchForSchool(String searchString);

    List<String> searchForSchool(String searchStringP, int start,
            int max_num_hits);
}

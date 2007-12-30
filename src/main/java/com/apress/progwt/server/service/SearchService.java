package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.dto.SearchResult;

public interface SearchService {

    List<SearchResult> search(String searchString);

    List<School> searchForSchool(final String searchString);

}

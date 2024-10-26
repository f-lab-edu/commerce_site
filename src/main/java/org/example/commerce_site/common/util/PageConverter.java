package org.example.commerce_site.common.util;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageConverter {
	public static <T> Page<T> getPage(List<T> content, Pageable pageable) {
		if (content == null || pageable == null) {
			return new PageImpl<>(Collections.emptyList(), pageable != null ? pageable : Pageable.unpaged(), 0);
		}

		int start = (int)pageable.getOffset();

		if (start >= content.size()) {
			return new PageImpl<>(Collections.emptyList(), pageable, content.size());
		}

		int end = Math.min((start + pageable.getPageSize()), content.size());

		return new PageImpl<>(content.subList(start, end), pageable, content.size());
	}
}

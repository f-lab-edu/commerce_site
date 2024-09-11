package org.example.commerce_site.common.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageConverter {
	public static <T> Page<T> getPage(List<T> content, Pageable pageable) {
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), content.size());

		return new PageImpl<>(content.subList(start, end), pageable, content.size());
	}
}
